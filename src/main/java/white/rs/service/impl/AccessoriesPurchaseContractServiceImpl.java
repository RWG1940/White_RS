package white.rs.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.domain.FileResource;
import white.rs.domain.TableImport;
import white.rs.service.AccessoriesPurchaseContractService;
import white.rs.mapper.AccessoriesPurchaseContractMapper;
import org.springframework.stereotype.Service;
import white.rs.service.TableImportService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;



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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return WhiteResponse.fail();
        }

    }

    @Override
    public WhiteResponse importExcel(MultipartFile file,String importId) {
        try {
            List<AccessoriesPurchaseContract> list = new ArrayList<>();

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

            // 创建列名映射
            Map<String, Integer> fieldMap = new HashMap<>();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerName = cell.getStringCellValue().trim();
                    fieldMap.put(headerName, i);
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

                if (fieldMap.containsKey("洗标状态")) {
                    Cell cell = row.getCell(fieldMap.get("洗标状态"));
                    if (cell != null) {
                        contract.setWashStatus(getCellValueAsString(cell));
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

                if (fieldMap.containsKey("吊牌状态")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌状态"));
                    if (cell != null) {
                        contract.setTagStatus(getCellValueAsString(cell));
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

                if (fieldMap.containsKey("吊牌快递单号")) {
                    Cell cell = row.getCell(fieldMap.get("吊牌快递单号"));
                    if (cell != null) {
                        contract.setTagExpressNo(getCellValueAsString(cell));
                    }
                }

                if (fieldMap.containsKey("季度")) {
                    Cell cell = row.getCell(fieldMap.get("季度"));
                    if (cell != null) {
                        contract.setQuarter(getCellValueAsString(cell));
                    }
                }
                // 添加导入批次ID
                contract.setImportId(Long.valueOf(importId));
                list.add(contract);
            }
            // 将导入批次id写入数据库中，便于前端查看已有的批次
            // 这里不应该声明对象，后续有待优化
            TableImport tableImport = new TableImport();
            tableImport.setId(Long.valueOf(importId));
            if (tableImportService.getById(importId) == null){
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


                // ② 查询数据库是否已存在该 sc_id
                AccessoriesPurchaseContract exist = this.lambdaQuery()
                        .eq(AccessoriesPurchaseContract::getScId, scId)
                        .one();

                if (exist != null) {
                    // ③ 已存在 → 执行更新（根据 sc_id 更新）
                    this.lambdaUpdate()
                            .eq(AccessoriesPurchaseContract::getScId, scId)
                            .update(item);

                } else {
                    // ④ 不存在 → 新增
                    this.save(item);
                }
            }

            return WhiteResponse.success("导入完成，共 " + list.size() + " 条（自动处理新增与更新）");

        } catch (Exception e) {
            e.printStackTrace();
            return WhiteResponse.fail("导入失败: " + e.getMessage());
        }
    }

    @Override
    public void exportExcel(List<AccessoriesPurchaseContract> contractList, HttpServletResponse response) {
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
            
            // 创建表头行
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "图片", "货号", "颜色", "品牌", "英文品名", "大面材料", "里衬材质", 
                "洗标颜色", "洗标种类", "工厂", "地址", "跟单", "数量", "洗标单价", 
                "洗标总价", "吊牌单价", "吊牌总价", "洗标优先级", "洗标状态", "洗标确认时间", 
                "洗标实际出货数量", "洗标出货时间", "洗标快递单号", "吊牌优先级", "吊牌状态", 
                "吊牌确认时间", "吊牌出货时间", "吊牌实际出货数量", "吊牌快递单号", "季度"
            };
            
            // 填充表头
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // 填充数据
            int rowNum = 1;
            if (contractList != null && !contractList.isEmpty()) {
                for (AccessoriesPurchaseContract contract : contractList) {
                    Row row = sheet.createRow(rowNum++);
                    
                    // 处理图片列，如果存在图片则插入图片，否则保留URL文本
                    String imageUrl = contract.getImageUrl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // 检查是否为API地址
                        if (imageUrl.startsWith("/api/files/download/")) {
                            // 从API地址中提取文件key
                            String fileKey = extractFileKeyFromUrl(imageUrl);
                            System.out.println("提取的fileKey: " + fileKey + ", imageUrl: " + imageUrl);
                            if (fileKey != null) {
                                // 尝试获取图片数据
                                byte[] imageData = getImageDataFromFileKey(fileKey);
                                if (imageData != null && imageData.length > 0) {
                                    // 插入图片到单元格
                                    insertImageToCell(workbook, sheet, row, 0, imageData);
                                } else {
                                    // 如果无法获取图片数据，则保留URL文本
                                    System.err.println("无法获取图片数据，fileKey: " + fileKey);
                                    row.createCell(0).setCellValue(imageUrl);
                                }
                            } else {
                                // 无法提取文件key，则保留URL文本
                                System.err.println("无法提取文件key，imageUrl: " + imageUrl);
                                row.createCell(0).setCellValue(imageUrl);
                            }
                        } else {
                            // 不是API地址，保留URL文本
                            System.out.println("不是API地址，保留原始URL: " + imageUrl);
                            row.createCell(0).setCellValue(imageUrl);
                        }
                    } else {
                        // 没有图片URL，创建空单元格
                        row.createCell(0).setCellValue("");
                    }
                    
                    // 填充其他字段
                    row.createCell(1).setCellValue(contract.getSku() != null ? contract.getSku() : "");
                    row.createCell(2).setCellValue(contract.getColor() != null ? contract.getColor() : "");
                    row.createCell(3).setCellValue(contract.getBrand() != null ? contract.getBrand() : "");
                    row.createCell(4).setCellValue(contract.getNameEn() != null ? contract.getNameEn() : "");
                    row.createCell(5).setCellValue(contract.getMaterialMain() != null ? contract.getMaterialMain() : "");
                    row.createCell(6).setCellValue(contract.getMaterialLining() != null ? contract.getMaterialLining() : "");
                    row.createCell(7).setCellValue(contract.getWashLabelColor() != null ? contract.getWashLabelColor() : "");
                    row.createCell(8).setCellValue(contract.getWashLabelType() != null ? contract.getWashLabelType() : "");
                    row.createCell(9).setCellValue(contract.getFactory() != null ? contract.getFactory() : "");
                    row.createCell(10).setCellValue(contract.getAddress() != null ? contract.getAddress() : "");
                    row.createCell(11).setCellValue(contract.getFollower() != null ? contract.getFollower() : "");
                    row.createCell(12).setCellValue(contract.getQuantity() != null ? contract.getQuantity() : 0);
                    row.createCell(13).setCellValue(contract.getWashUnitPrice() != null ? contract.getWashUnitPrice().doubleValue() : 0.0);
                    row.createCell(14).setCellValue(contract.getWashTotalPrice() != null ? contract.getWashTotalPrice().doubleValue() : 0.0);
                    row.createCell(15).setCellValue(contract.getTagUnitPrice() != null ? contract.getTagUnitPrice().doubleValue() : 0.0);
                    row.createCell(16).setCellValue(contract.getTagTotalPrice() != null ? contract.getTagTotalPrice().doubleValue() : 0.0);
                    row.createCell(17).setCellValue(contract.getWashPriority() != null ? contract.getWashPriority() : 0);
                    row.createCell(18).setCellValue(contract.getWashStatus() != null ? contract.getWashStatus() : "");
                    // 对于日期类型，需要特殊处理
                    if (contract.getWashConfirmTime() != null) {
                        row.createCell(19).setCellValue(contract.getWashConfirmTime());
                    } else {
                        row.createCell(19).setCellValue("");
                    }
                    row.createCell(20).setCellValue(contract.getWashShipQuantity() != null ? contract.getWashShipQuantity() : 0);
                    if (contract.getWashShipTime() != null) {
                        row.createCell(21).setCellValue(contract.getWashShipTime());
                    } else {
                        row.createCell(21).setCellValue("");
                    }
                    row.createCell(22).setCellValue(contract.getWashExpressNo() != null ? contract.getWashExpressNo() : "");
                    row.createCell(23).setCellValue(contract.getTagPriority() != null ? contract.getTagPriority() : 0);
                    row.createCell(24).setCellValue(contract.getTagStatus() != null ? contract.getTagStatus() : "");
                    if (contract.getTagConfirmTime() != null) {
                        row.createCell(25).setCellValue(contract.getTagConfirmTime());
                    } else {
                        row.createCell(25).setCellValue("");
                    }
                    if (contract.getTagShipTime() != null) {
                        row.createCell(26).setCellValue(contract.getTagShipTime());
                    } else {
                        row.createCell(26).setCellValue("");
                    }
                    row.createCell(27).setCellValue(contract.getTagShipQuantity() != null ? contract.getTagShipQuantity() : 0);
                    row.createCell(28).setCellValue(contract.getTagExpressNo() != null ? contract.getTagExpressNo() : "");
                    row.createCell(29).setCellValue(contract.getQuarter() != null ? contract.getQuarter() : "");
                }
            }
            
            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
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
            e.printStackTrace();
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
                System.err.println("未找到文件资源，fileKey: " + fileKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图片插入到指定单元格
     *
     * @param workbook   工作簿
     * @param sheet      工作表
     * @param row        行
     * @param column     列索引
     * @param imageData  图片数据
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
                
                System.out.println("准备添加图片到工作簿，图片类型: " + pictureType + ", 图片数据长度: " + imageData.length);
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
                System.out.println("成功创建图片");
                
                // 设置单元格值为空（图片会覆盖单元格）
                Cell imageCell = row.createCell(column);
                imageCell.setCellValue("");
            } else {
                // 如果无法创建图片，则回退到显示URL文本
                System.err.println("无法创建图片容器");
                Cell imageCell = row.createCell(column);
                imageCell.setCellValue("图片数据无法加载");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 出错时回退到显示URL文本
            System.err.println("插入图片到单元格时发生异常");
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
}