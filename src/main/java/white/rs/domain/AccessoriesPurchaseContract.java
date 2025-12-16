package white.rs.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 洗标吊牌信息表（支持批量导入，多季度管理）
 * @TableName accessories_purchase_contract
 */
@TableName(value ="accessories_purchase_contract")
public class AccessoriesPurchaseContract implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 图片地址（可存文件URL或MinIO路径）
     */
    private String imageUrl;

    /**
     * 货号
     */
    private String sku;

    /**
     * 颜色
     */
    private String color;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 英文品名
     */
    private String nameEn;

    /**
     * 大面材料
     */
    private String materialMain;

    /**
     * 里衬材质
     */
    private String materialLining;

    /**
     * 洗标颜色
     */
    private String washLabelColor;

    /**
     * 洗标种类
     */
    private String washLabelType;

    /**
     * 工厂
     */
    private String factory;

    /**
     * 地址
     */
    private String address;

    /**
     * 跟单人
     */
    private String follower;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 洗标单价
     */
    private BigDecimal washUnitPrice;

    /**
     * 洗标总价
     */
    private BigDecimal washTotalPrice;

    /**
     * 吊牌单价
     */
    private BigDecimal tagUnitPrice;

    /**
     * 吊牌总价
     */
    private BigDecimal tagTotalPrice;

    /**
     * 洗标优先级
     */
    private Integer washPriority;

    /**
     * 洗标状态（如：未确认/已确认/已出货）
     */
    private String washStatus;

    /**
     * 洗标确认时间
     */
    private Date washConfirmTime;

    /**
     * 洗标出货数量
     */
    private Integer washShipQuantity;

    /**
     * 洗标实际出货时间
     */
    private Date washShipTime;

    /**
     * 洗标快递单号
     */
    private String washExpressNo;

    /**
     * 吊牌优先级
     */
    private Integer tagPriority;

    /**
     * 吊牌状态（如：未确认/已确认/已出货）
     */
    private String tagStatus;

    /**
     * 吊牌确认时间
     */
    private Date tagConfirmTime;

    /**
     * 吊牌出货时间
     */
    private Date tagShipTime;

    /**
     * 吊牌实际出货数量
     */
    private Integer tagShipQuantity;

    /**
     * 吊牌快递单号
     */
    private String tagExpressNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：1 启用，0 禁用
     */
    private Integer status;

    /**
     * 整体优先级字段
     */
    private Integer priority;

    /**
     * 导入批次ID（用于区分不同季度表）
     */
    private Long importId;

    /**
     * 季度
     */
    private String quarter;

    /**
     * SC ID 唯一字段，由sku和颜色组成
     */
    private String scId;


    /**
     * 额外字段（用于存储多余扩展字段）
     */
    private Object extraJson;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 图片地址（可存文件URL或MinIO路径）
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 图片地址（可存文件URL或MinIO路径）
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 货号
     */
    public String getSku() {
        return sku;
    }

    /**
     * 货号
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 颜色
     */
    public String getColor() {
        return color;
    }

    /**
     * 颜色
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 英文品名
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * 英文品名
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    /**
     * 大面材料
     */
    public String getMaterialMain() {
        return materialMain;
    }

    /**
     * 大面材料
     */
    public void setMaterialMain(String materialMain) {
        this.materialMain = materialMain;
    }

    /**
     * 里衬材质
     */
    public String getMaterialLining() {
        return materialLining;
    }

    /**
     * 里衬材质
     */
    public void setMaterialLining(String materialLining) {
        this.materialLining = materialLining;
    }

    /**
     * 洗标颜色
     */
    public String getWashLabelColor() {
        return washLabelColor;
    }

    /**
     * 洗标颜色
     */
    public void setWashLabelColor(String washLabelColor) {
        this.washLabelColor = washLabelColor;
    }

    /**
     * 洗标种类
     */
    public String getWashLabelType() {
        return washLabelType;
    }

    /**
     * 洗标种类
     */
    public void setWashLabelType(String washLabelType) {
        this.washLabelType = washLabelType;
    }

    /**
     * 工厂
     */
    public String getFactory() {
        return factory;
    }

    /**
     * 工厂
     */
    public void setFactory(String factory) {
        this.factory = factory;
    }

    /**
     * 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 跟单人
     */
    public String getFollower() {
        return follower;
    }

    /**
     * 跟单人
     */
    public void setFollower(String follower) {
        this.follower = follower;
    }

    /**
     * 数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 洗标单价
     */
    public BigDecimal getWashUnitPrice() {
        return washUnitPrice;
    }

    /**
     * 洗标单价
     */
    public void setWashUnitPrice(BigDecimal washUnitPrice) {
        this.washUnitPrice = washUnitPrice;
    }

    /**
     * 洗标总价
     */
    public BigDecimal getWashTotalPrice() {
        return washTotalPrice;
    }

    /**
     * 洗标总价
     */
    public void setWashTotalPrice(BigDecimal washTotalPrice) {
        this.washTotalPrice = washTotalPrice;
    }

    /**
     * 吊牌单价
     */
    public BigDecimal getTagUnitPrice() {
        return tagUnitPrice;
    }

    /**
     * 吊牌单价
     */
    public void setTagUnitPrice(BigDecimal tagUnitPrice) {
        this.tagUnitPrice = tagUnitPrice;
    }

    /**
     * 吊牌总价
     */
    public BigDecimal getTagTotalPrice() {
        return tagTotalPrice;
    }

    /**
     * 吊牌总价
     */
    public void setTagTotalPrice(BigDecimal tagTotalPrice) {
        this.tagTotalPrice = tagTotalPrice;
    }

    /**
     * 洗标优先级
     */
    public Integer getWashPriority() {
        return washPriority;
    }

    /**
     * 洗标优先级
     */
    public void setWashPriority(Integer washPriority) {
        this.washPriority = washPriority;
    }

    /**
     * 洗标状态（如：未确认/已确认/已出货）
     */
    public String getWashStatus() {
        return washStatus;
    }

    /**
     * 洗标状态（如：未确认/已确认/已出货）
     */
    public void setWashStatus(String washStatus) {
        this.washStatus = washStatus;
    }

    /**
     * 洗标确认时间
     */
    public Date getWashConfirmTime() {
        return washConfirmTime;
    }

    /**
     * 洗标确认时间
     */
    public void setWashConfirmTime(Date washConfirmTime) {
        this.washConfirmTime = washConfirmTime;
    }

    /**
     * 洗标出货数量
     */
    public Integer getWashShipQuantity() {
        return washShipQuantity;
    }

    /**
     * 洗标出货数量
     */
    public void setWashShipQuantity(Integer washShipQuantity) {
        this.washShipQuantity = washShipQuantity;
    }

    /**
     * 洗标实际出货时间
     */
    public Date getWashShipTime() {
        return washShipTime;
    }

    /**
     * 洗标实际出货时间
     */
    public void setWashShipTime(Date washShipTime) {
        this.washShipTime = washShipTime;
    }

    /**
     * 洗标快递单号
     */
    public String getWashExpressNo() {
        return washExpressNo;
    }

    /**
     * 洗标快递单号
     */
    public void setWashExpressNo(String washExpressNo) {
        this.washExpressNo = washExpressNo;
    }

    /**
     * 吊牌优先级
     */
    public Integer getTagPriority() {
        return tagPriority;
    }

    /**
     * 吊牌优先级
     */
    public void setTagPriority(Integer tagPriority) {
        this.tagPriority = tagPriority;
    }

    /**
     * 吊牌状态（如：未确认/已确认/已出货）
     */
    public String getTagStatus() {
        return tagStatus;
    }

    /**
     * 吊牌状态（如：未确认/已确认/已出货）
     */
    public void setTagStatus(String tagStatus) {
        this.tagStatus = tagStatus;
    }

    /**
     * 吊牌确认时间
     */
    public Date getTagConfirmTime() {
        return tagConfirmTime;
    }

    /**
     * 吊牌确认时间
     */
    public void setTagConfirmTime(Date tagConfirmTime) {
        this.tagConfirmTime = tagConfirmTime;
    }

    /**
     * 吊牌出货时间
     */
    public Date getTagShipTime() {
        return tagShipTime;
    }

    /**
     * 吊牌出货时间
     */
    public void setTagShipTime(Date tagShipTime) {
        this.tagShipTime = tagShipTime;
    }

    /**
     * 吊牌实际出货数量
     */
    public Integer getTagShipQuantity() {
        return tagShipQuantity;
    }

    /**
     * 吊牌实际出货数量
     */
    public void setTagShipQuantity(Integer tagShipQuantity) {
        this.tagShipQuantity = tagShipQuantity;
    }

    /**
     * 吊牌快递单号
     */
    public String getTagExpressNo() {
        return tagExpressNo;
    }

    /**
     * 吊牌快递单号
     */
    public void setTagExpressNo(String tagExpressNo) {
        this.tagExpressNo = tagExpressNo;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 状态：1 启用，0 禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态：1 启用，0 禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 整体优先级字段
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 整体优先级字段
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 导入批次ID（用于区分不同季度表）
     */
    public Long getImportId() {
        return importId;
    }

    /**
     * 导入批次ID（用于区分不同季度表）
     */
    public void setImportId(Long importId) {
        this.importId = importId;
    }

    /**
     * 季度
     */
    public String getQuarter() {
        return quarter;
    }

    /**
     * 季度
     */
    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    /**
     * SC ID
     */
    public String getScId() {
        return scId;
    }

    /**
     * SC ID
     */
    public void setScId(String scId) {
        this.scId = scId;
    }

    /**
     * 额外字段（用于存储多余扩展字段）
     */
    public Object getExtraJson() {
        return extraJson;
    }

    /**
     * 额外字段（用于存储多余扩展字段）
     */
    public void setExtraJson(Object extraJson) {
        this.extraJson = extraJson;
    }

    /**
     * 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }



    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AccessoriesPurchaseContract other = (AccessoriesPurchaseContract) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImageUrl() == null ? other.getImageUrl() == null : this.getImageUrl().equals(other.getImageUrl()))
            && (this.getSku() == null ? other.getSku() == null : this.getSku().equals(other.getSku()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getBrand() == null ? other.getBrand() == null : this.getBrand().equals(other.getBrand()))
            && (this.getNameEn() == null ? other.getNameEn() == null : this.getNameEn().equals(other.getNameEn()))
            && (this.getMaterialMain() == null ? other.getMaterialMain() == null : this.getMaterialMain().equals(other.getMaterialMain()))
            && (this.getMaterialLining() == null ? other.getMaterialLining() == null : this.getMaterialLining().equals(other.getMaterialLining()))
            && (this.getWashLabelColor() == null ? other.getWashLabelColor() == null : this.getWashLabelColor().equals(other.getWashLabelColor()))
            && (this.getWashLabelType() == null ? other.getWashLabelType() == null : this.getWashLabelType().equals(other.getWashLabelType()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getFollower() == null ? other.getFollower() == null : this.getFollower().equals(other.getFollower()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getWashUnitPrice() == null ? other.getWashUnitPrice() == null : this.getWashUnitPrice().equals(other.getWashUnitPrice()))
            && (this.getWashTotalPrice() == null ? other.getWashTotalPrice() == null : this.getWashTotalPrice().equals(other.getWashTotalPrice()))
            && (this.getTagUnitPrice() == null ? other.getTagUnitPrice() == null : this.getTagUnitPrice().equals(other.getTagUnitPrice()))
            && (this.getTagTotalPrice() == null ? other.getTagTotalPrice() == null : this.getTagTotalPrice().equals(other.getTagTotalPrice()))
            && (this.getWashPriority() == null ? other.getWashPriority() == null : this.getWashPriority().equals(other.getWashPriority()))
            && (this.getWashStatus() == null ? other.getWashStatus() == null : this.getWashStatus().equals(other.getWashStatus()))
            && (this.getWashConfirmTime() == null ? other.getWashConfirmTime() == null : this.getWashConfirmTime().equals(other.getWashConfirmTime()))
            && (this.getWashShipQuantity() == null ? other.getWashShipQuantity() == null : this.getWashShipQuantity().equals(other.getWashShipQuantity()))
            && (this.getWashShipTime() == null ? other.getWashShipTime() == null : this.getWashShipTime().equals(other.getWashShipTime()))
            && (this.getWashExpressNo() == null ? other.getWashExpressNo() == null : this.getWashExpressNo().equals(other.getWashExpressNo()))
            && (this.getTagPriority() == null ? other.getTagPriority() == null : this.getTagPriority().equals(other.getTagPriority()))
            && (this.getTagStatus() == null ? other.getTagStatus() == null : this.getTagStatus().equals(other.getTagStatus()))
            && (this.getTagConfirmTime() == null ? other.getTagConfirmTime() == null : this.getTagConfirmTime().equals(other.getTagConfirmTime()))
            && (this.getTagShipTime() == null ? other.getTagShipTime() == null : this.getTagShipTime().equals(other.getTagShipTime()))
            && (this.getTagShipQuantity() == null ? other.getTagShipQuantity() == null : this.getTagShipQuantity().equals(other.getTagShipQuantity()))
            && (this.getTagExpressNo() == null ? other.getTagExpressNo() == null : this.getTagExpressNo().equals(other.getTagExpressNo()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getImportId() == null ? other.getImportId() == null : this.getImportId().equals(other.getImportId()))
            && (this.getQuarter() == null ? other.getQuarter() == null : this.getQuarter().equals(other.getQuarter()))
            && (this.getScId() == null ? other.getScId() == null : this.getScId().equals(other.getScId()))
            && (this.getExtraJson() == null ? other.getExtraJson() == null : this.getExtraJson().equals(other.getExtraJson()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImageUrl() == null) ? 0 : getImageUrl().hashCode());
        result = prime * result + ((getSku() == null) ? 0 : getSku().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getBrand() == null) ? 0 : getBrand().hashCode());
        result = prime * result + ((getNameEn() == null) ? 0 : getNameEn().hashCode());
        result = prime * result + ((getMaterialMain() == null) ? 0 : getMaterialMain().hashCode());
        result = prime * result + ((getMaterialLining() == null) ? 0 : getMaterialLining().hashCode());
        result = prime * result + ((getWashLabelColor() == null) ? 0 : getWashLabelColor().hashCode());
        result = prime * result + ((getWashLabelType() == null) ? 0 : getWashLabelType().hashCode());
        result = prime * result + ((getFactory() == null) ? 0 : getFactory().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getFollower() == null) ? 0 : getFollower().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getWashUnitPrice() == null) ? 0 : getWashUnitPrice().hashCode());
        result = prime * result + ((getWashTotalPrice() == null) ? 0 : getWashTotalPrice().hashCode());
        result = prime * result + ((getTagUnitPrice() == null) ? 0 : getTagUnitPrice().hashCode());
        result = prime * result + ((getTagTotalPrice() == null) ? 0 : getTagTotalPrice().hashCode());
        result = prime * result + ((getWashPriority() == null) ? 0 : getWashPriority().hashCode());
        result = prime * result + ((getWashStatus() == null) ? 0 : getWashStatus().hashCode());
        result = prime * result + ((getWashConfirmTime() == null) ? 0 : getWashConfirmTime().hashCode());
        result = prime * result + ((getWashShipQuantity() == null) ? 0 : getWashShipQuantity().hashCode());
        result = prime * result + ((getWashShipTime() == null) ? 0 : getWashShipTime().hashCode());
        result = prime * result + ((getWashExpressNo() == null) ? 0 : getWashExpressNo().hashCode());
        result = prime * result + ((getTagPriority() == null) ? 0 : getTagPriority().hashCode());
        result = prime * result + ((getTagStatus() == null) ? 0 : getTagStatus().hashCode());
        result = prime * result + ((getTagConfirmTime() == null) ? 0 : getTagConfirmTime().hashCode());
        result = prime * result + ((getTagShipTime() == null) ? 0 : getTagShipTime().hashCode());
        result = prime * result + ((getTagShipQuantity() == null) ? 0 : getTagShipQuantity().hashCode());
        result = prime * result + ((getTagExpressNo() == null) ? 0 : getTagExpressNo().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getImportId() == null) ? 0 : getImportId().hashCode());
        result = prime * result + ((getQuarter() == null) ? 0 : getQuarter().hashCode());
        result = prime * result + ((getScId() == null) ? 0 : getScId().hashCode());
        result = prime * result + ((getExtraJson() == null) ? 0 : getExtraJson().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", sku=").append(sku);
        sb.append(", color=").append(color);
        sb.append(", brand=").append(brand);
        sb.append(", nameEn=").append(nameEn);
        sb.append(", materialMain=").append(materialMain);
        sb.append(", materialLining=").append(materialLining);
        sb.append(", washLabelColor=").append(washLabelColor);
        sb.append(", washLabelType=").append(washLabelType);
        sb.append(", factory=").append(factory);
        sb.append(", address=").append(address);
        sb.append(", follower=").append(follower);
        sb.append(", quantity=").append(quantity);
        sb.append(", washUnitPrice=").append(washUnitPrice);
        sb.append(", washTotalPrice=").append(washTotalPrice);
        sb.append(", tagUnitPrice=").append(tagUnitPrice);
        sb.append(", tagTotalPrice=").append(tagTotalPrice);
        sb.append(", washPriority=").append(washPriority);
        sb.append(", washStatus=").append(washStatus);
        sb.append(", washConfirmTime=").append(washConfirmTime);
        sb.append(", washShipQuantity=").append(washShipQuantity);
        sb.append(", washShipTime=").append(washShipTime);
        sb.append(", washExpressNo=").append(washExpressNo);
        sb.append(", tagPriority=").append(tagPriority);
        sb.append(", tagStatus=").append(tagStatus);
        sb.append(", tagConfirmTime=").append(tagConfirmTime);
        sb.append(", tagShipTime=").append(tagShipTime);
        sb.append(", tagShipQuantity=").append(tagShipQuantity);
        sb.append(", tagExpressNo=").append(tagExpressNo);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", priority=").append(priority);
        sb.append(", importId=").append(importId);
        sb.append(", quarter=").append(quarter);
        sb.append(", scId=").append(scId);
        sb.append(", extraJson=").append(extraJson);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}