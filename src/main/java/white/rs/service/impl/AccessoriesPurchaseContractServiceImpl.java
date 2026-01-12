package white.rs.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.domain.FileResource;
import white.rs.domain.GuestTableImport;
import white.rs.domain.TableImport;
import white.rs.service.AccessoriesPurchaseContractService;
import white.rs.mapper.AccessoriesPurchaseContractMapper;
import org.springframework.stereotype.Service;
import white.rs.service.GuestTableImportService;
import white.rs.service.TableImportService;
import white.rs.service.UsersService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Administrator
 * @description 针对表【accessories_purchase_contract(洗标吊牌信息表（支持批量导入，多季度管理）)】的数据库操作Service实现
 * @createDate 2025-12-10 10:59:57
 */

@Service
public class AccessoriesPurchaseContractServiceImpl extends ServiceImpl<AccessoriesPurchaseContractMapper, AccessoriesPurchaseContract> implements AccessoriesPurchaseContractService {
    @Autowired
    private MinioServiceImpl minioService;
    @Autowired
    private AccessoriesPurchaseContractMapper accessoriesPurchaseContractMapper;
    @Autowired
    private FileResourceServiceImpl fileResourceService;
    @Autowired
    private TableImportService tableImportService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private GuestTableImportService guestTableImportService;

    private static final Logger logger = LoggerFactory.getLogger(AccessoriesPurchaseContractServiceImpl.class);

    // 添加辅助方法用于获取单元格值
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // 添加日期解析方法
    private Date parseDateFromString(String dateStr) {
        try {
            // 可以根据实际需要添加不同的日期格式解析
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
            } catch (Exception ex) {
                logger.error("日期解析失败: " + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public boolean save(AccessoriesPurchaseContract accessoriesPurchaseContract) {
        // 如果accessoriesPurchaseContract的sku为空或者颜色为空，使用uuid生成sc_id
        String scId;
        if (accessoriesPurchaseContract.getSku() == null || accessoriesPurchaseContract.getColor() == null) {
            accessoriesPurchaseContract.setScId(UUID.randomUUID().toString());
        } else {
            scId = accessoriesPurchaseContract.getSku() + "_" + accessoriesPurchaseContract.getColor();
            accessoriesPurchaseContract.setScId(scId);
        }
        return super.save(accessoriesPurchaseContract);

    }

    @Override
    // 重写父类方法，用于添加文件并更新辅料采购合同信息
    public WhiteResponse addFile(MultipartFile file, AccessoriesPurchaseContract acc) {
        try {
            // 1.使用minioService先把file上传，获得file_url
            // 2.设置acc中的image_url为file_url
            acc.setImageUrl(minioService.uploadFile(file, "1", 1L, 1L).getData().toString());
            // 3.使用accessoriesPurchaseContractMapper来更新 acc
            accessoriesPurchaseContractMapper.updateById(acc);
            return WhiteResponse.success();
        } catch (Exception e) {
            logger.error("添加文件失败: " + e.getMessage());
            return WhiteResponse.fail();
        }
    }

    @Override
    // 更新文件的方法，接收一个文件和一个辅料采购合同对象作为参数
    public WhiteResponse updateFile(MultipartFile file, AccessoriesPurchaseContract acc) {
        try {
            // 1.先取出acc中的image_url
            // 2.使用fileResourceService查询image_url对应的file_key
            QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("file_url", acc.getImageUrl());
            String fileKey = fileResourceService.getOne(queryWrapper).getFileKey();
            // 3.使用minioService更新
            minioService.updateFile(fileKey, file);
            // 5.使用accessoriesPurchaseContractMapper来更新 acc
            accessoriesPurchaseContractMapper.updateById(acc);
            return WhiteResponse.success();
        } catch (Exception e) {
            logger.error("更新文件失败: " + e.getMessage());
            return WhiteResponse.fail();
        }
    }

    @Override
    // 重写 deleteFile 方法，用于删除文件
    public WhiteResponse deleteFile(Long id) {
        try {
            // 1.使用accessoriesPurchaseContractMapper来查询 id 对应的 acc
            AccessoriesPurchaseContract acc = accessoriesPurchaseContractMapper.selectById(id);
            // 2.使用fileResourceService查询acc中的image_url对应的file_key
            QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("file_url", acc.getImageUrl());
            FileResource fileResource = fileResourceService.getOne(queryWrapper);
            if (fileResource == null) {
                accessoriesPurchaseContractMapper.deleteById(id);
                return WhiteResponse.success();
            }
            String fileKey = fileResource.getFileKey();
            // 3.使用minioService删除
            minioService.deleteFile(fileKey);
            // 4.使用accessoriesPurchaseContractMapper来删除 acc
            accessoriesPurchaseContractMapper.deleteById(id);
            // 5.使用fileResourceService删除 file_key 对应的 file_resource
            fileResourceService.remove(queryWrapper);
            return WhiteResponse.success();

        } catch (Exception e) {
            logger.error("删除文件失败: " + e.getMessage());
            return WhiteResponse.fail();
        }

    }


    /**
     * 获取当前用户的角色编码列表
     */
    private List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            // 根据用户名获取用户ID
            white.rs.domain.Users user = usersService.getByUsername(username);
            if (user != null) {
                return usersService.getRoleCodesByUserId(user.getId());
            } else {
                logger.warn("用户不存在");
            }
        } else {
            logger.warn("未获取到用户信息");
        }
        return new ArrayList<>();
    }

    /**
     * 检查当前用户是否拥有指定角色
     */
    private boolean hasRole(String roleCode) {
        List<String> roles = getCurrentUserRoles();
        logger.info("当前用户角色：" + roles);
        return roles.contains(roleCode);
    }

    /**
     * 根据用户角色获取允许导入的字段映射
     */
    private Set<String> getAllowedFieldsByRole() {
        Set<String> allowedFields = new HashSet<>();

        // 检查当前用户角色
        if (hasRole("3294") || hasRole("5293")) {
            // 3294和5293角色可以导入所有字段
            allowedFields.addAll(Arrays.asList(
                    "图片", "货号", "颜色", "品牌", "英文品名", "大面材料", "里衬材质",
                    "洗标颜色", "洗标种类", "工厂", "地址", "跟单", "数量", "洗标单价",
                    "洗标总价", "吊牌单价", "吊牌总价", "洗标优先级", "洗标状态",
                    "洗标确认时间", "洗标出货时间", "洗标快递单号", "吊牌优先级",
                    "吊牌状态", "吊牌确认时间", "吊牌出货时间", "吊牌快递单号",
                    "季度", "洗标实际出货数量", "吊牌实际出货数量", "备注"
            ));
        } else {
            // 6666及其他角色只能导入特定字段，但必须包含用于识别记录的字段
            allowedFields.addAll(Arrays.asList(
                    "货号", "颜色", "洗标单价", "吊牌单价", "洗标状态", "洗标实际出货数量",
                    "洗标出货时间", "洗标快递单号", "吊牌状态", "吊牌出货时间",
                    "吊牌实际出货数量", "吊牌快递单号", "备注"
            ));
        }

        return allowedFields;
    }

    /**
     * 根据用户角色获取导出字段列表
     */
    private List<String> getExportFieldsByRole() {
        List<String> exportFields = new ArrayList<>();

        // 检查当前用户角色
        // 如果用户拥有3294或5293角色，则可以导出所有字段
        List<String> userRoles = getCurrentUserRoles();

        if (userRoles.contains("3294") || userRoles.contains("5293")) {
            // 3294和5293角色可以导出所有字段
            exportFields.addAll(Arrays.asList(
                    "季度", "图片", "货号", "颜色", "品牌", "英文品名", "大面材料", "里衬材质",
                    "洗标颜色", "洗标种类", "工厂", "地址", "跟单", "数量", "洗标实际出货数量", "吊牌实际出货数量", "洗标单价",
                    "洗标总价", "吊牌单价", "吊牌总价", "洗标优先级", "洗标状态", "洗标确认时间",
                    "洗标出货时间", "洗标快递单号", "吊牌优先级", "吊牌状态",
                    "吊牌确认时间", "吊牌出货时间", "吊牌快递单号"
            ));
        } else {
            // 6666及其他角色只能导出特定字段，按指定顺序排列
            exportFields.addAll(Arrays.asList(
                    "货号", "颜色", "品牌", "洗标颜色", "洗标种类", "工厂", "地址", "跟单", "数量",
                    "洗标实际出货数量", "吊牌实际出货数量", "洗标单价", "洗标总价", "吊牌单价", "吊牌总价",
                    "洗标优先级", "洗标状态", "洗标确认时间", "洗标出货时间", "洗标快递单号",
                    "吊牌优先级", "吊牌状态", "吊牌确认时间", "吊牌出货时间", "吊牌快递单号",
                    "英文品名", "大面材料", "里衬材质"
            ));
        }

        return exportFields;
    }

    @Override
    public WhiteResponse importExcel(MultipartFile file, Long importId, Integer guestId) {
        try {
            List<AccessoriesPurchaseContract> list = new ArrayList<>();

            List<String> userRoles = getCurrentUserRoles();

            Workbook workbook;
            String fileName = file.getOriginalFilename();
            if (fileName != null && fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                workbook = new HSSFWorkbook(file.getInputStream());
            }

            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头行
            Row headerRow = sheet.getRow(0);
            int lastCellNum = headerRow.getLastCellNum();

            // 获取当前用户允许导入的字段
            Set<String> allowedFields = getAllowedFieldsByRole();

            // 创建列名映射，只包含允许的字段
            Map<String, Integer> fieldMap = new HashMap<>();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerName = cell.getStringCellValue().trim();
                    // 只有在允许字段列表中的才添加到映射中
                    if (allowedFields.contains(headerName)) {
                        fieldMap.put(headerName, i);
                    }
                }
            }


            // 提取图片信息（仅支持.xlsx格式）
            Map<String, String> imageMap = new HashMap<>();
            if (workbook instanceof XSSFWorkbook && sheet instanceof XSSFSheet) {
                XSSFSheet xssfSheet = (XSSFSheet) sheet;
                extractImagesFromSheet(xssfSheet, imageMap);
            }

            // 解析数据行
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {  // 从第2行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null) continue;

                AccessoriesPurchaseContract contract = new AccessoriesPurchaseContract();
                // 根据表头映射填充数据
                if (userRoles.contains("3294") || userRoles.contains("5293")) {
                    if (fieldMap.containsKey("图片")) {
                        Cell cell = row.getCell(fieldMap.get("图片"));
                        if (cell != null) {
                            String cellRef = cell.getRowIndex() + "," + cell.getColumnIndex();
                            // 如果该单元格有图片，则上传到MinIO并获取URL
                            if (imageMap.containsKey(cellRef)) {
                                String imageUrl = imageMap.get(cellRef);
                                contract.setImageUrl(imageUrl);
                            } else {
                                contract.setImageUrl(getCellValueAsString(cell));
                            }
                        }
                    }



                    if (fieldMap.containsKey("品牌")) {
                        Cell cell = row.getCell(fieldMap.get("品牌"));
                        if (cell != null) {
                            contract.setBrand(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("英文品名")) {
                        Cell cell = row.getCell(fieldMap.get("英文品名"));
                        if (cell != null) {
                            contract.setNameEn(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("大面材料")) {
                        Cell cell = row.getCell(fieldMap.get("大面材料"));
                        if (cell != null) {
                            contract.setMaterialMain(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("里衬材质")) {
                        Cell cell = row.getCell(fieldMap.get("里衬材质"));
                        if (cell != null) {
                            contract.setMaterialLining(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("洗标颜色")) {
                        Cell cell = row.getCell(fieldMap.get("洗标颜色"));
                        if (cell != null) {
                            contract.setWashLabelColor(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("洗标种类")) {
                        Cell cell = row.getCell(fieldMap.get("洗标种类"));
                        if (cell != null) {
                            contract.setWashLabelType(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("工厂")) {
                        Cell cell = row.getCell(fieldMap.get("工厂"));
                        if (cell != null) {
                            contract.setFactory(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("地址")) {
                        Cell cell = row.getCell(fieldMap.get("地址"));
                        if (cell != null) {
                            contract.setAddress(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("跟单")) {
                        Cell cell = row.getCell(fieldMap.get("跟单"));
                        if (cell != null) {
                            contract.setFollower(getCellValueAsString(cell));
                        }
                    }

                    if (fieldMap.containsKey("数量")) {
                        Cell cell = row.getCell(fieldMap.get("数量"));
                        if (cell != null) {
                            try {
                                contract.setQuantity((int) cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                contract.setQuantity(Integer.valueOf(getCellValueAsString(cell)));
                            }
                        }
                    }

                    if (fieldMap.containsKey("洗标总价")) {
                        Cell cell = row.getCell(fieldMap.get("洗标总价"));
                        if (cell != null) {
                            try {
                                contract.setWashTotalPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                            } catch (IllegalStateException e) {
                                contract.setWashTotalPrice(new BigDecimal(getCellValueAsString(cell)));
                            }
                        }
                    }

                    if (fieldMap.containsKey("吊牌总价")) {
                        Cell cell = row.getCell(fieldMap.get("吊牌总价"));
                        if (cell != null) {
                            try {
                                contract.setTagTotalPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                            } catch (IllegalStateException e) {
                                contract.setTagTotalPrice(new BigDecimal(getCellValueAsString(cell)));
                            }
                        }
                    }

                    if (fieldMap.containsKey("洗标优先级")) {
                        Cell cell = row.getCell(fieldMap.get("洗标优先级"));
                        if (cell != null) {
                            try {
                                contract.setWashPriority((int) cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                contract.setWashPriority(Integer.valueOf(getCellValueAsString(cell)));
                            }
                        }
                    }



                    if (fieldMap.containsKey("吊牌优先级")) {
                        Cell cell = row.getCell(fieldMap.get("吊牌优先级"));
                        if (cell != null) {
                            try {
                                contract.setTagPriority((int) cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                contract.setTagPriority(Integer.valueOf(getCellValueAsString(cell)));
                            }
                        }
                    }



                    if (fieldMap.containsKey("季度")) {
                        Cell cell = row.getCell(fieldMap.get("季度"));
                        if (cell != null) {
                            contract.setQuarter(getCellValueAsString(cell));
                        }
                    }
                }

                if (fieldMap.containsKey("洗标确认时间")) {
                    Cell cell = row.getCell(fieldMap.get("洗标确认时间"));
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                            contract.setWashConfirmTime(cell.getDateCellValue());
                        } else {
                            contract.setWashConfirmTime(parseDateFromString(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("吊牌确认时间")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌确认时间"));
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                            contract.setTagConfirmTime(cell.getDateCellValue());
                        } else {
                            contract.setTagConfirmTime(parseDateFromString(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("洗标单价")) {
                    Cell cell = row.getCell(fieldMap.get("洗标单价"));
                    if (cell != null) {
                        try {
                            contract.setWashUnitPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        } catch (IllegalStateException e) {
                            contract.setWashUnitPrice(new BigDecimal(getCellValueAsString(cell)));
                        }
                    }
                }


                if (fieldMap.containsKey("吊牌单价")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌单价"));
                    if (cell != null) {
                        try {
                            contract.setTagUnitPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        } catch (IllegalStateException e) {
                            contract.setTagUnitPrice(new BigDecimal(getCellValueAsString(cell)));
                        }
                    }
                }


                if (fieldMap.containsKey("洗标状态")) {
                    Cell cell = row.getCell(fieldMap.get("洗标状态"));
                    if (cell != null) {
                        String statusDesc = getCellValueAsString(cell);
                        contract.setWashStatus(convertStatusToNumber(statusDesc));
                    }
                }


                if (fieldMap.containsKey("洗标实际出货数量")) {
                    Cell cell = row.getCell(fieldMap.get("洗标实际出货数量"));
                    if (cell != null) {
                        try {
                            contract.setWashShipQuantity((int) cell.getNumericCellValue());
                        } catch (IllegalStateException e) {
                            contract.setWashShipQuantity(Integer.valueOf(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("洗标出货时间")) {
                    Cell cell = row.getCell(fieldMap.get("洗标出货时间"));
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                            contract.setWashShipTime(cell.getDateCellValue());
                        } else {
                            contract.setWashShipTime(parseDateFromString(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("洗标快递单号")) {
                    Cell cell = row.getCell(fieldMap.get("洗标快递单号"));
                    if (cell != null) {
                        contract.setWashExpressNo(getCellValueAsString(cell));
                    }
                }


                if (fieldMap.containsKey("吊牌状态")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌状态"));
                    if (cell != null) {
                        String statusDesc = getCellValueAsString(cell);
                        contract.setTagStatus(convertStatusToNumber(statusDesc));
                    }
                }


                if (fieldMap.containsKey("吊牌出货时间")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌出货时间"));
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                            contract.setTagShipTime(cell.getDateCellValue());
                        } else {
                            contract.setTagShipTime(parseDateFromString(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("吊牌实际出货数量")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌实际出货数量"));
                    if (cell != null) {
                        try {
                            contract.setTagShipQuantity((int) cell.getNumericCellValue());
                        } catch (IllegalStateException e) {
                            contract.setTagShipQuantity(Integer.valueOf(getCellValueAsString(cell)));
                        }
                    }
                }

                if (fieldMap.containsKey("货号")) {
                    Cell cell = row.getCell(fieldMap.get("货号"));
                    if (cell != null) {
                        contract.setSku(getCellValueAsString(cell));
                    }
                }

                if (fieldMap.containsKey("颜色")) {
                    Cell cell = row.getCell(fieldMap.get("颜色"));
                    if (cell != null) {
                        contract.setColor(getCellValueAsString(cell));
                    }
                }

                if (fieldMap.containsKey("吊牌快递单号")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌快递单号"));
                    if (cell != null) {
                        contract.setTagExpressNo(getCellValueAsString(cell));
                    }
                }


                if (fieldMap.containsKey("备注")) {
                    Cell cell = row.getCell(fieldMap.get("备注"));
                    if (cell != null) {
                        contract.setRemark(getCellValueAsString(cell));
                    }
                }


                // 添加导入批次ID
                contract.setImportId(importId);
                list.add(contract);
            }
            // 将导入批次id写入数据库中，便于前端查看已有的批次
            TableImport tableImport = new TableImport();
            tableImport.setId(importId);
            if (tableImportService.getById(importId) == null) {
                tableImportService.save(tableImport);
            }
            workbook.close();

            for (AccessoriesPurchaseContract item : list) {

                // ① 根据 SKU + 颜色 生成 sc_id 如不存在 则用uuid生成
                String scId;
                if (item.getSku() == null || item.getColor() == null) {
                    item.setScId(UUID.randomUUID().toString());
                    scId = item.getScId();
                } else {
                    scId = item.getSku() + "_" + item.getColor();
                    item.setScId(scId);
                }


                // ② 查询数据库是否已存在该 sc_id 且 import_id 相同
                AccessoriesPurchaseContract exist = this.lambdaQuery()
                        .eq(AccessoriesPurchaseContract::getScId, scId)
                        .eq(AccessoriesPurchaseContract::getImportId, Long.valueOf(importId))
                        .one();

                if (exist != null) {
                    // ③ 已存在 → 执行更新（根据导入的字段更新，避免未导入字段被置为null）
                    AccessoriesPurchaseContract updateItem = new AccessoriesPurchaseContract();
                    boolean hasUpdate = false;

                    // 只更新从Excel导入的字段，避免将未导入的字段设为null
                    if (item.getSku() != null) {
                        updateItem.setSku(item.getSku());
                        hasUpdate = true;
                    }
                    if (item.getColor() != null) {
                        updateItem.setColor(item.getColor());
                        hasUpdate = true;
                    }
                    if (item.getBrand() != null) {
                        updateItem.setBrand(item.getBrand());
                        hasUpdate = true;
                    }
                    if (item.getNameEn() != null) {
                        updateItem.setNameEn(item.getNameEn());
                        hasUpdate = true;
                    }
                    if (item.getMaterialMain() != null) {
                        updateItem.setMaterialMain(item.getMaterialMain());
                        hasUpdate = true;
                    }
                    if (item.getMaterialLining() != null) {
                        updateItem.setMaterialLining(item.getMaterialLining());
                        hasUpdate = true;
                    }
                    if (item.getWashLabelColor() != null) {
                        updateItem.setWashLabelColor(item.getWashLabelColor());
                        hasUpdate = true;
                    }
                    if (item.getWashLabelType() != null) {
                        updateItem.setWashLabelType(item.getWashLabelType());
                        hasUpdate = true;
                    }
                    if (item.getFactory() != null) {
                        updateItem.setFactory(item.getFactory());
                        hasUpdate = true;
                    }
                    if (item.getAddress() != null) {
                        updateItem.setAddress(item.getAddress());
                        hasUpdate = true;
                    }
                    if (item.getFollower() != null) {
                        updateItem.setFollower(item.getFollower());
                        hasUpdate = true;
                    }
                    if (item.getQuantity() != null) {
                        updateItem.setQuantity(item.getQuantity());
                        hasUpdate = true;
                    }
                    if (item.getWashUnitPrice() != null) {
                        updateItem.setWashUnitPrice(item.getWashUnitPrice());
                        hasUpdate = true;
                    }
                    if (item.getWashTotalPrice() != null) {
                        updateItem.setWashTotalPrice(item.getWashTotalPrice());
                        hasUpdate = true;
                    }
                    if (item.getTagUnitPrice() != null) {
                        updateItem.setTagUnitPrice(item.getTagUnitPrice());
                        hasUpdate = true;
                    }
                    if (item.getTagTotalPrice() != null) {
                        updateItem.setTagTotalPrice(item.getTagTotalPrice());
                        hasUpdate = true;
                    }
                    if (item.getWashPriority() != null) {
                        updateItem.setWashPriority(item.getWashPriority());
                        hasUpdate = true;
                    }
                    if (item.getWashStatus() != null) {
                        updateItem.setWashStatus(item.getWashStatus());
                        hasUpdate = true;
                    }
                    if (item.getWashConfirmTime() != null) {
                        updateItem.setWashConfirmTime(item.getWashConfirmTime());
                        hasUpdate = true;
                    }
                    if (item.getWashShipTime() != null) {
                        updateItem.setWashShipTime(item.getWashShipTime());
                        hasUpdate = true;
                    }
                    if (item.getWashShipQuantity() != null) {
                        updateItem.setWashShipQuantity(item.getWashShipQuantity());
                        hasUpdate = true;
                    }
                    if (item.getWashExpressNo() != null) {
                        updateItem.setWashExpressNo(item.getWashExpressNo());
                        hasUpdate = true;
                    }
                    if (item.getTagPriority() != null) {
                        updateItem.setTagPriority(item.getTagPriority());
                        hasUpdate = true;
                    }
                    if (item.getTagStatus() != null) {
                        updateItem.setTagStatus(item.getTagStatus());
                        hasUpdate = true;
                    }
                    if (item.getTagConfirmTime() != null) {
                        updateItem.setTagConfirmTime(item.getTagConfirmTime());
                        hasUpdate = true;
                    }
                    if (item.getTagShipTime() != null) {
                        updateItem.setTagShipTime(item.getTagShipTime());
                        hasUpdate = true;
                    }
                    if (item.getTagShipQuantity() != null) {
                        updateItem.setTagShipQuantity(item.getTagShipQuantity());
                        hasUpdate = true;
                    }
                    if (item.getTagExpressNo() != null) {
                        updateItem.setTagExpressNo(item.getTagExpressNo());
                        hasUpdate = true;
                    }
                    if (item.getQuarter() != null) {
                        updateItem.setQuarter(item.getQuarter());
                        hasUpdate = true;
                    }
                    if (item.getImageUrl() != null) {
                        updateItem.setImageUrl(item.getImageUrl());
                        hasUpdate = true;
                    }
                    if (item.getRemark() != null) {
                        updateItem.setRemark(item.getRemark());
                        hasUpdate = true;
                    }

                    // 只有当存在要更新的字段时才执行更新
                    if (hasUpdate) {
                        updateItem.setScId(scId); // 确保scId一致
                        this.lambdaUpdate()
                                .eq(AccessoriesPurchaseContract::getScId, scId)
                                .update(updateItem);
                    }
                } else {
                    // ④ 不存在 → 新增
                    this.save(item);
                }
            }
            // 如果客人id存在，则添加关联关系
            if (guestId != null) {
                GuestTableImport guestTableImport = new GuestTableImport();
                guestTableImport.setGuestId(guestId);
                guestTableImport.setImportId(Math.toIntExact(importId));
                this.guestTableImportService.addGuestTableImport(guestTableImport);
            }
            return WhiteResponse.success("导入完成，共 " + list.size() + " 条（自动处理新增与更新）");

        } catch (Exception e) {
            logger.error("导入失败: " + e.getMessage(), e);
            return WhiteResponse.fail("导入失败: " + e.getMessage());
        }
    }

    @Override
    public void exportExcel(Map<String, Object> body, HttpServletResponse response) {
        // 从请求体中获取参数
        String keyword = (String) body.get("keyword");
        // 获取排序字段
        String sortBy = (String) body.get("sortBy");
        // 获取排序参数
        String sortOrder = (String) body.get("sortOrder");
        Object exportIdsObj = body.get("exportIds");
        String importIds;

        // 处理 exportIds 可能是 ArrayList、String 或 Number 的情况
        if (exportIdsObj instanceof String) {
            importIds = (String) exportIdsObj;
        } else if (exportIdsObj instanceof List) {
            // 如果是 List，则将其转换为 JSON 字符串
            importIds = JSON.toJSONString(exportIdsObj);
        } else if (exportIdsObj instanceof Number) {
            // 如果是单个数字，构造一个包含该数字的数组字符串
            importIds = "[" + exportIdsObj.toString() + "]";
        } else {
            importIds = exportIdsObj != null ? exportIdsObj.toString() : null;
        }

        // 将importIds参数转换为Long列表
        List<Long> importIdList = new ArrayList<>();
        if (importIds != null && !importIds.isEmpty()) {
            try {
                importIdList = JSON.parseArray(importIds, Long.class);
            } catch (Exception e) {
                // 如果解析失败，尝试处理单个数字的情况
                try {
                    Long singleId = JSON.parseObject(importIds, Long.class);
                    importIdList.add(singleId);
                } catch (Exception ex) {
                    // 忽略异常，使用空列表
                    logger.error("解析 importIds 失败: " + e.getMessage(), e);
                }
            }
        }

        // 构建查询条件
        QueryWrapper<AccessoriesPurchaseContract> queryWrapper = new QueryWrapper<>();

        // 如果有搜索关键字，则添加搜索条件
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like("sku", keyword)
                    .or()
                    .like("color", keyword)
                    .or()
                    .like("brand", keyword)
                    .or()
                    .like("name_en", keyword)
                    .or()
                    .like("factory", keyword)
                    .or()
                    .like("follower", keyword)
            );
        }

        // 如果有排序字段，则添加排序条件
        if (sortBy != null && !sortBy.isEmpty()) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByDesc(sortBy);
            } else {
                queryWrapper.orderByAsc(sortBy);
            }
        }

        queryWrapper.in(importIdList != null && !importIdList.isEmpty(), "import_id", importIdList);
        List<AccessoriesPurchaseContract> contractList = super.list(queryWrapper);

        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("辅料购货合同");

            // 创建表头样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // 根据用户角色获取导出字段配置
            List<String> exportFields = getExportFieldsByRole();

            // 创建表头行
            Row headerRow = sheet.createRow(0);

            // 填充表头
            for (int i = 0; i < exportFields.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(exportFields.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 填充数据
            int rowNum = 1;
            if (contractList != null && !contractList.isEmpty()) {
                for (AccessoriesPurchaseContract contract : contractList) {
                    Row row = sheet.createRow(rowNum++);

                    // 根据角色权限导出指定字段
                    for (int i = 0; i < exportFields.size(); i++) {
                        String field = exportFields.get(i);
                        Cell cell = row.createCell(i);

                        switch (field) {
                            case "图片":
                                // 处理图片列，如果存在图片则插入图片，否则保留URL文本
                                String imageUrl = contract.getImageUrl();
                                if (imageUrl != null && !imageUrl.isEmpty()) {
                                    // 检查是否为API地址
                                    if (imageUrl.startsWith("/api/files/download/")) {
                                        // 从API地址中提取文件key
                                        String fileKey = extractFileKeyFromUrl(imageUrl);

                                        if (fileKey != null) {
                                            // 尝试获取图片数据
                                            byte[] imageData = getImageDataFromFileKey(fileKey);
                                            if (imageData != null && imageData.length > 0) {
                                                // 插入图片到单元格
                                                insertImageToCell(workbook, sheet, row, i, imageData);
                                            } else {
                                                logger.error("获取图片数据失败，fileKey: " + fileKey);
                                                cell.setCellValue(imageUrl);
                                            }
                                        } else {
                                            // 无法提取文件key，则保留URL文本
                                            logger.error("无法提取文件key，fileUrl: " + imageUrl);
                                            cell.setCellValue(imageUrl);
                                        }
                                    } else {
                                        // 不是API地址，保留URL文本
                                        logger.error("不是API地址，fileUrl: " + imageUrl);
                                        cell.setCellValue(imageUrl);
                                    }
                                } else {
                                    // 没有图片URL，创建空单元格
                                    cell.setCellValue("");
                                }
                                break;
                            case "货号":
                                cell.setCellValue(contract.getSku() != null ? contract.getSku() : "");
                                break;
                            case "颜色":
                                cell.setCellValue(contract.getColor() != null ? contract.getColor() : "");
                                break;
                            case "品牌":
                                cell.setCellValue(contract.getBrand() != null ? contract.getBrand() : "");
                                break;
                            case "洗标颜色":
                                cell.setCellValue(contract.getWashLabelColor() != null ? contract.getWashLabelColor() : "");
                                break;
                            case "洗标种类":
                                cell.setCellValue(contract.getWashLabelType() != null ? contract.getWashLabelType() : "");
                                break;
                            case "工厂":
                                cell.setCellValue(contract.getFactory() != null ? contract.getFactory() : "");
                                break;
                            case "地址":
                                cell.setCellValue(contract.getAddress() != null ? contract.getAddress() : "");
                                break;
                            case "跟单":
                                cell.setCellValue(contract.getFollower() != null ? contract.getFollower() : "");
                                break;
                            case "数量":
                                cell.setCellValue(contract.getQuantity() != null ? contract.getQuantity() : 0);
                                break;
                            case "洗标实际出货数量":
                                cell.setCellValue(contract.getWashShipQuantity() != null ? contract.getWashShipQuantity() : 0);
                                break;
                            case "吊牌实际出货数量":
                                cell.setCellValue(contract.getTagShipQuantity() != null ? contract.getTagShipQuantity() : 0);
                                break;
                            case "洗标单价":
                                cell.setCellValue(contract.getWashUnitPrice() != null ? contract.getWashUnitPrice().doubleValue() : 0.0);
                                break;
                            case "洗标总价":
                                cell.setCellValue(contract.getWashTotalPrice() != null ? contract.getWashTotalPrice().doubleValue() : 0.0);
                                break;
                            case "吊牌单价":
                                cell.setCellValue(contract.getTagUnitPrice() != null ? contract.getTagUnitPrice().doubleValue() : 0.0);
                                break;
                            case "吊牌总价":
                                cell.setCellValue(contract.getTagTotalPrice() != null ? contract.getTagTotalPrice().doubleValue() : 0.0);
                                break;
                            case "洗标优先级":
                                cell.setCellValue(contract.getWashPriority() != null ? contract.getWashPriority() : 0);
                                break;
                            case "洗标状态":
                                cell.setCellValue(getStatusDescription(contract.getWashStatus()));
                                break;
                            case "洗标确认时间":
                                if (contract.getWashConfirmTime() != null) {
                                    CellStyle dateCellStyle = workbook.createCellStyle();
                                    CreationHelper createHelper = workbook.getCreationHelper();
                                    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                                    cell.setCellValue(contract.getWashConfirmTime());
                                    cell.setCellStyle(dateCellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                                break;
                            case "洗标出货时间":
                                if (contract.getWashShipTime() != null) {
                                    CellStyle dateCellStyle = workbook.createCellStyle();
                                    CreationHelper createHelper = workbook.getCreationHelper();
                                    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                                    cell.setCellValue(contract.getWashShipTime());
                                    cell.setCellStyle(dateCellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                                break;
                            case "洗标快递单号":
                                cell.setCellValue(contract.getWashExpressNo() != null ? contract.getWashExpressNo() : "");
                                break;
                            case "吊牌优先级":
                                cell.setCellValue(contract.getTagPriority() != null ? contract.getTagPriority() : 0);
                                break;
                            case "吊牌状态":
                                cell.setCellValue(getStatusDescription(contract.getTagStatus()));
                                break;
                            case "吊牌确认时间":
                                if (contract.getTagConfirmTime() != null) {
                                    CellStyle dateCellStyle = workbook.createCellStyle();
                                    CreationHelper createHelper = workbook.getCreationHelper();
                                    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                                    cell.setCellValue(contract.getTagConfirmTime());
                                    cell.setCellStyle(dateCellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                                break;
                            case "吊牌出货时间":
                                if (contract.getTagShipTime() != null) {
                                    CellStyle dateCellStyle = workbook.createCellStyle();
                                    CreationHelper createHelper = workbook.getCreationHelper();
                                    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                                    cell.setCellValue(contract.getTagShipTime());
                                    cell.setCellStyle(dateCellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                                break;
                            case "吊牌快递单号":
                                cell.setCellValue(contract.getTagExpressNo() != null ? contract.getTagExpressNo() : "");
                                break;
                            case "英文品名":
                                cell.setCellValue(contract.getNameEn() != null ? contract.getNameEn() : "");
                                break;
                            case "大面材料":
                                cell.setCellValue(contract.getMaterialMain() != null ? contract.getMaterialMain() : "");
                                break;
                            case "里衬材质":
                                cell.setCellValue(contract.getMaterialLining() != null ? contract.getMaterialLining() : "");
                                break;
                            case "季度":
                                cell.setCellValue(contract.getQuarter() != null ? contract.getQuarter() : "");
                                break;
                            default:
                                cell.setCellValue("");
                                break;
                        }
                    }
                }
            }

            // 自动调整列宽
            for (int i = 0; i < exportFields.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=accessories_purchase_contracts.xlsx");

            // 写入输出流
            workbook.write(response.getOutputStream());
            workbook.close();
            response.getOutputStream().flush();
        } catch (Exception e) {
            logger.error("导出 Excel 文件失败", e);
        }
    }

    @Override
    public WhiteResponse getPageByUserRole(Long current, Long size, Long importId, Long guestId) {

        // 获取当前用户角色
        List<String> userRoles = getCurrentUserRoles();

        // 创建分页对象
        Page<AccessoriesPurchaseContract> page = new Page<>(current, size);

        // 创建查询条件
        QueryWrapper<AccessoriesPurchaseContract> queryWrapper = new QueryWrapper<>();
        // 根据importId筛选数据
        if (importId != null && importId > 0) {
            queryWrapper.eq("import_id", importId);
        } else {
            // 根据guestId筛选数据
            if (guestId != null && guestId > 0) {
                // 先从关联表中获取importIds
                // 创建查询条件
                QueryWrapper<GuestTableImport> guest_queryWrapper = new QueryWrapper<>();
                guest_queryWrapper.eq("guest_id", guestId);
                // 获取guestImports
                List<GuestTableImport> guestImports = guestTableImportService.list(guest_queryWrapper);
                // 从guestImports中获取importIds
                List<Integer> importIds = guestImports.stream().map(GuestTableImport::getImportId).collect(java.util.stream.Collectors.toList());
                queryWrapper.in(!importIds.isEmpty(), "import_id", importIds);
                // 构建queryWrapper的importIds查询条件
                queryWrapper.in(!importIds.isEmpty(), "import_id", importIds);
            }
        }

        // 根据角色筛选数据
        if (userRoles.contains("3294") || userRoles.contains("5293") || userRoles.contains("6666")) {
            // 3294和5293角色可以查看所有数据
            // 不添加额外的筛选条件
        } else if (userRoles.contains("5555")) {
            // 5555角色按用户名-对应表格跟单筛选
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                queryWrapper.eq("follower", username);
            }
        } else if (userRoles.contains("7777")) {
            // 6666角色按用户名-对应表格工厂筛选
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                queryWrapper.eq("factory", username);
            }
        } else {
            // 其他角色默认不返回数据或根据安全策略返回特定数据
            // 这里可以根据实际业务需求进行调整
            queryWrapper.eq("id", -1); // 不返回任何数据
        }

        // 执行分页查询
        Page<AccessoriesPurchaseContract> resultPage = this.page(page, queryWrapper);
        // System.out.println(resultPage.getRecords());
        return WhiteResponse.success(resultPage);
    }

    @Override
    public WhiteResponse getTotalByImportId(Long importId) {
        QueryWrapper<AccessoriesPurchaseContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("import_id", importId);

        // 查出所有id符合的数据
        List<AccessoriesPurchaseContract> contracts = this.list(queryWrapper);

        // 使用流操作，确保 null 值被处理为 BigDecimal.ZERO
        BigDecimal washTotalPrice = contracts.stream()
                .map(contract -> Optional.ofNullable(contract.getWashTotalPrice()).orElse(BigDecimal.ZERO)) // 如果为 null，替换为 BigDecimal.ZERO
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tagTotalPrice = contracts.stream()
                .map(contract -> Optional.ofNullable(contract.getTagTotalPrice()).orElse(BigDecimal.ZERO)) // 如果为 null，替换为 BigDecimal.ZERO
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 返回计算结果
        return WhiteResponse.success(new HashMap<String, BigDecimal>() {{
            put("washTotalPrice", washTotalPrice);
            put("tagTotalPrice", tagTotalPrice);
        }});
    }


    /**
     * 从Excel工作表中提取图片信息
     *
     * @param sheet    Excel工作表
     * @param imageMap 图片映射，key为单元格引用(row,col)，value为图片URL
     */
    private void extractImagesFromSheet(XSSFSheet sheet, Map<String, String> imageMap) {
        try {
            // 方法1: 处理传统浮动图片
            processFloatingPictures(sheet, imageMap);

            // 方法2: 处理嵌入单元格的图片（DISPIMG公式）
            processEmbeddedCellPictures(sheet, imageMap);
        } catch (Exception e) {
            logger.error("从Excel工作表中提取图片信息失败", e);
        }
    }

    /**
     * 处理浮动图片
     *
     * @param sheet    Excel工作表
     * @param imageMap 图片映射，key为单元格引用(row,col)，value为图片URL
     */
    private void processFloatingPictures(XSSFSheet sheet, Map<String, String> imageMap) {
        try {
            XSSFDrawing drawing = sheet.getDrawingPatriarch();
            if (drawing != null) {
                for (XSSFShape shape : drawing.getShapes()) {
                    if (shape instanceof XSSFPicture) {
                        XSSFPicture picture = (XSSFPicture) shape;
                        XSSFClientAnchor anchor = picture.getClientAnchor();

                        // 获取图片所在的行和列（使用起始位置）
                        int row = anchor.getRow1();
                        int col = anchor.getCol1();
                        String cellRef = row + "," + col;

                        // 获取图片数据
                        XSSFPictureData pictureData = picture.getPictureData();
                        byte[] imageData = pictureData.getData();

                        // 创建MultipartFile模拟对象用于上传
                        String fileName = "imported_image_" + UUID.randomUUID() + "." + getExtension(pictureData);

                        // 上传图片到MinIO
                        WhiteResponse response = minioService.uploadFile(
                                new CustomMultipartFile(fileName, imageData, pictureData.getMimeType()),
                                "contract_image",
                                0L,
                                0L
                        );

                        if (response.getCode().equals(ResponseCode.SUCCESS.getCode())) {
                            String imageUrl = (String) response.getData();
                            imageMap.put(cellRef, imageUrl);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("处理浮动图片失败", e);
        }
    }

    /**
     * 处理嵌入单元格的图片
     *
     * @param sheet    Excel工作表
     * @param imageMap 图片映射，key为单元格引用(row,col)，value为图片URL
     */
    private void processEmbeddedCellPictures(XSSFSheet sheet, Map<String, String> imageMap) {
        try {
            // 创建一个临时列表来存储所有DISPIMG单元格的位置
            List<String> dispImgCells = new ArrayList<>();

            // 遍历所有行和单元格查找嵌入的图片公式
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // 检查单元格是否包含公式且是DISPIMG公式
                    if (cell.getCellType() == CellType.FORMULA) {
                        String formula = cell.getCellFormula();
                        if (formula != null && formula.startsWith("_xlfn.DISPIMG")) {
                            // 记录包含DISPIMG公式的单元格位置
                            String cellRef = cell.getRowIndex() + "," + cell.getColumnIndex();
                            dispImgCells.add(cellRef);
                        }
                    }
                }
            }

            // 如果有图片但没有明确的单元格关联，尝试启发式分配
            if (!imageMap.isEmpty() && !dispImgCells.isEmpty()) {
                // 获取所有图片URL
                List<String> imageUrls = new ArrayList<>(imageMap.values());

                // 如果图片数量和DISPIMG单元格数量相等，一对一关联
                if (imageUrls.size() == dispImgCells.size()) {
                    for (int i = 0; i < dispImgCells.size(); i++) {
                        imageMap.put(dispImgCells.get(i), imageUrls.get(i));
                    }
                }
                // 如果只有一张图片，但有多个DISPIMG单元格，将图片关联到所有单元格
                else if (imageUrls.size() == 1) {
                    String imageUrl = imageUrls.get(0);
                    for (String cellRef : dispImgCells) {
                        imageMap.put(cellRef, imageUrl);
                    }
                }
                // 其他情况，按顺序循环分配
                else {
                    for (int i = 0; i < dispImgCells.size(); i++) {
                        String imageUrl = imageUrls.get(i % imageUrls.size());
                        imageMap.put(dispImgCells.get(i), imageUrl);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("处理嵌入单元格图片失败", e);
        }
    }

    /**
     * 根据图片数据获取文件扩展名
     *
     * @param pictureData 图片数据
     * @return 文件扩展名
     */
    private String getExtension(XSSFPictureData pictureData) {
        int pictureType = pictureData.getPictureType();
        switch (pictureType) {
            case 5: // JPEG
            case 6: // JPG
                return "jpg";
            case 3: // PNG
                return "png";
            case 7: // GIF
                return "gif";
            case 1: // BMP
                return "bmp";
            default:
                return "jpg"; // 默认
        }
    }

    /**
     * 从URL中提取文件key
     *
     * @param url API地址
     * @return 文件key
     */
    private String extractFileKeyFromUrl(String url) {
        QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_url", url);
        return fileResourceService.getOne(queryWrapper).getFileKey();
    }

    /**
     * 根据文件key获取图片数据
     *
     * @param fileKey 文件key
     * @return 图片数据
     */
    private byte[] getImageDataFromFileKey(String fileKey) {
        try {
            // 使用fileResourceService根据fileKey获取文件信息
            QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("file_key", fileKey);
            FileResource fileResource = fileResourceService.getOne(queryWrapper);

            if (fileResource != null) {
                // 使用minioService下载文件数据
                return minioService.downloadFileAsBytes(fileKey);
            } else {
                logger.error("文件不存在");
            }
        } catch (Exception e) {
            logger.error("获取图片数据失败", e);
        }
        return null;
    }

    /**
     * 将图片插入到指定单元格
     *
     * @param workbook  工作簿
     * @param sheet     工作表
     * @param row       行
     * @param column    列索引
     * @param imageData 图片数据
     */
    private void insertImageToCell(Workbook workbook, Sheet sheet, Row row, int column, byte[] imageData) {
        try {
            // 创建一个绘制容器（如果尚不存在）
            XSSFDrawing drawing = null;
            if (sheet instanceof XSSFSheet) {
                drawing = ((XSSFSheet) sheet).getDrawingPatriarch();
                if (drawing == null) {
                    drawing = ((XSSFSheet) sheet).createDrawingPatriarch();
                }
            }

            if (drawing != null) {
                // 确定图片类型
                int pictureType = Workbook.PICTURE_TYPE_JPEG; // 默认JPEG
                if (imageData.length > 2) {
                    // 简单判断图片类型
                    if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
                        pictureType = Workbook.PICTURE_TYPE_JPEG;
                    } else if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50) {
                        pictureType = Workbook.PICTURE_TYPE_PNG;
                    } else if (imageData[0] == (byte) 0x47 && imageData[1] == (byte) 0x49) {
                        pictureType = Workbook.PICTURE_TYPE_PICT;
                    } else if (imageData[0] == (byte) 0x42 && imageData[1] == (byte) 0x4D) {
                        pictureType = Workbook.PICTURE_TYPE_DIB;
                    }
                }

                logger.info("图片类型：" + pictureType);
                // 添加图片到工作簿
                int pictureIdx = workbook.addPicture(imageData, pictureType);

                // 创建锚点（定义图片在单元格中的位置）
                CreationHelper helper = workbook.getCreationHelper();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(column);
                anchor.setRow1(row.getRowNum());
                anchor.setCol2(column + 1);
                anchor.setRow2(row.getRowNum() + 1);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

                // 创建图片并放置在单元格中
                drawing.createPicture(anchor, pictureIdx);

                // 设置单元格值为空（图片会覆盖单元格）
                Cell imageCell = row.createCell(column);
                imageCell.setCellValue("");
            } else {
                logger.error("无法创建绘制容器");
                Cell imageCell = row.createCell(column);
                imageCell.setCellValue("图片数据无法加载");
            }
        } catch (Exception e) {
            logger.error("插入图片失败", e);
            Cell imageCell = row.createCell(column);
            imageCell.setCellValue("图片加载失败");
        }
    }

    /**
     * 自定义MultipartFile实现类，用于处理图片字节数据
     */
    private static class CustomMultipartFile implements MultipartFile {
        private final String name;
        private final byte[] content;
        private final String contentType;

        public CustomMultipartFile(String name, byte[] content, String contentType) {
            this.name = name;
            this.content = content;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return name;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                fos.write(content);
            }
        }
    }

    /**
     * 将状态数字转换为中文描述
     * 0-未下单, 1-做货中, 2-货好等付款, 3-已出货
     *
     * @param status 状态数字
     * @return 状态中文描述
     */
    private String getStatusDescription(String status) {
        if (status == null || status.isEmpty()) {
            return "";
        }

        try {
            int statusValue = Integer.parseInt(status);
            switch (statusValue) {
                case 0:
                    return "未下单";
                case 1:
                    return "做货中";
                case 2:
                    return "货好等付款";
                case 3:
                    return "已出货";
                default:
                    return status; // 如果不是0-3的值，返回原值
            }
        } catch (NumberFormatException e) {
            // 如果状态不是数字，返回原值
            return status;
        }
    }

    /**
     * 将状态中文描述转换为数字
     * 未下单→0, 做货中→1, 货好等付款→2, 已出货→3
     *
     * @param statusDesc 状态中文描述
     * @return 状态数字
     */
    private String convertStatusToNumber(String statusDesc) {
        if (statusDesc == null || statusDesc.isEmpty()) {
            return statusDesc;
        }

        String cleanedDesc = statusDesc.trim();

        // 检查各种可能的状态描述，使用包含匹配以处理可能的空格或特殊字符
        if (cleanedDesc.contains("未下单")) {
            return "0";
        } else if (cleanedDesc.contains("做货中")) {
            return "1";
        } else if (cleanedDesc.contains("货好等付款")) {
            return "2";
        } else if (cleanedDesc.contains("已出货")) {
            return "3";
        } else {
            // 检查是否已经是数字
            try {
                Integer.parseInt(cleanedDesc);
                return cleanedDesc; // 如果已经是数字，直接返回
            } catch (NumberFormatException e) {
                // 如果不是预定义的中文状态，也不是数字，返回原值
                logger.error("无法识别的状态描述：" + statusDesc);
                return statusDesc;
            }
        }
    }
}