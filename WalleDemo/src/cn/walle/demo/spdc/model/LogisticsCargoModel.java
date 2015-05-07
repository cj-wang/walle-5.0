package cn.walle.demo.spdc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for 提单货类信息
 */
@Entity
@Table(name = "LOGISTICS_CARGO")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class LogisticsCargoModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "LogisticsCargo";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String logisticsCargoId = "logisticsCargoId";
		/**
		 * Phisical
		 */
		public static final String logisticsDetailsId = "logisticsDetailsId";
		/**
		 * 提单号
		 */
		public static final String blNo = "blNo";
		/**
		 * 货名
		 */
		public static final String cargoName = "cargoName";
		/**
		 * 危险品代码
		 */
		public static final String hazardCode = "hazardCode";
		/**
		 * 计费货类
		 */
		public static final String billingCargoType = "billingCargoType";
		/**
		 * 件数
		 */
		public static final String quantity = "quantity";
		/**
		 * 包装
		 */
		public static final String packaging = "packaging";
		/**
		 * 重量
		 */
		public static final String weight = "weight";
		/**
		 * 体积
		 */
		public static final String volume = "volume";
		/**
		 * 仓内外
		 */
		public static final String insideOutside = "insideOutside";
		/**
		 * 结算代码
		 */
		public static final String settlementCode = "settlementCode";
		/**
		 * 堆位
		 */
		public static final String stackLocation = "stackLocation";
		/**
		 * 唛头
		 */
		public static final String marks = "marks";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * 卡号
		 */
		public static final String cardNumber = "cardNumber";
		/**
		 * isBonded
		 */
		public static final String isBonded = "isBonded";
		/**
		 * 是否有效
		 */
		public static final String activity = "activity";
		/**
		 * 货物权属人代码
		 */
		public static final String cargoOwner = "cargoOwner";
		/**
		 * 货物权属人名称
		 */
		public static final String cargoOwnerName = "cargoOwnerName";
		/**
		 * 录入人
		 */
		public static final String createdByUser = "createdByUser";
		/**
		 * 创建组织
		 */
		public static final String createOffice = "createOffice";
		/**
		 * 创建时间
		 */
		public static final String createdDtmLoc = "createdDtmLoc";
		/**
		 * 创建者当地时区
		 */
		public static final String createdTimeZone = "createdTimeZone";
		/**
		 * 修改人
		 */
		public static final String updatedByUser = "updatedByUser";
		/**
		 * 修改组织
		 */
		public static final String updatedOffice = "updatedOffice";
		/**
		 * 修改时间
		 */
		public static final String updatedDtmLoc = "updatedDtmLoc";
		/**
		 * 修改者当地时区
		 */
		public static final String updatedTimeZone = "updatedTimeZone";
		/**
		 * 记录版本
		 */
		public static final String recordVersion = "recordVersion";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
	}

	//Phisical
	private String logisticsCargoId;
	//Phisical
	private String logisticsDetailsId;
	//提单号
	private String blNo;
	//货名
	private String cargoName;
	//危险品代码
	private String hazardCode;
	//计费货类
	private String billingCargoType;
	//件数
	private Integer quantity;
	//包装
	private String packaging;
	//重量
	private Double weight;
	//体积
	private String volume;
	//仓内外
	private Integer insideOutside;
	//结算代码
	private String settlementCode;
	//堆位
	private String stackLocation;
	//唛头
	private String marks;
	//备注
	private String remark;
	//卡号
	private String cardNumber;
	//isBonded
	private Integer isBonded;
	//是否有效
	private Integer activity;
	//货物权属人代码
	private String cargoOwner;
	//货物权属人名称
	private String cargoOwnerName;
	//录入人
	private String createdByUser;
	//创建组织
	private String createOffice;
	//创建时间
	private Date createdDtmLoc;
	//创建者当地时区
	private String createdTimeZone;
	//修改人
	private String updatedByUser;
	//修改组织
	private String updatedOffice;
	//修改时间
	private Date updatedDtmLoc;
	//修改者当地时区
	private String updatedTimeZone;
	//记录版本
	private Long recordVersion;
	//公司代码
	private String principalGroupCode;

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_CARGO_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getLogisticsCargoId() {
		return logisticsCargoId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setLogisticsCargoId(String logisticsCargoId) {
		this.logisticsCargoId = logisticsCargoId;
		addValidField(FieldNames.logisticsCargoId);
	}

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_DETAILS_ID")
	public String getLogisticsDetailsId() {
		return logisticsDetailsId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setLogisticsDetailsId(String logisticsDetailsId) {
		this.logisticsDetailsId = logisticsDetailsId;
		addValidField(FieldNames.logisticsDetailsId);
	}

	/**
	 * Get 提单号
	 */
	@Column(name = "BL_NO")
	public String getBlNo() {
		return blNo;
	}

	/**
	 * Set 提单号
	 */
	public void setBlNo(String blNo) {
		this.blNo = blNo;
		addValidField(FieldNames.blNo);
	}

	/**
	 * Get 货名
	 */
	@Column(name = "CARGO_NAME")
	public String getCargoName() {
		return cargoName;
	}

	/**
	 * Set 货名
	 */
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
		addValidField(FieldNames.cargoName);
	}

	/**
	 * Get 危险品代码
	 */
	@Column(name = "HAZARD_CODE")
	public String getHazardCode() {
		return hazardCode;
	}

	/**
	 * Set 危险品代码
	 */
	public void setHazardCode(String hazardCode) {
		this.hazardCode = hazardCode;
		addValidField(FieldNames.hazardCode);
	}

	/**
	 * Get 计费货类
	 */
	@Column(name = "BILLING_CARGO_TYPE")
	public String getBillingCargoType() {
		return billingCargoType;
	}

	/**
	 * Set 计费货类
	 */
	public void setBillingCargoType(String billingCargoType) {
		this.billingCargoType = billingCargoType;
		addValidField(FieldNames.billingCargoType);
	}

	/**
	 * Get 件数
	 */
	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Set 件数
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		addValidField(FieldNames.quantity);
	}

	/**
	 * Get 包装
	 */
	@Column(name = "PACKAGING")
	public String getPackaging() {
		return packaging;
	}

	/**
	 * Set 包装
	 */
	public void setPackaging(String packaging) {
		this.packaging = packaging;
		addValidField(FieldNames.packaging);
	}

	/**
	 * Get 重量
	 */
	@Column(name = "WEIGHT")
	public Double getWeight() {
		return weight;
	}

	/**
	 * Set 重量
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
		addValidField(FieldNames.weight);
	}

	/**
	 * Get 体积
	 */
	@Column(name = "VOLUME")
	public String getVolume() {
		return volume;
	}

	/**
	 * Set 体积
	 */
	public void setVolume(String volume) {
		this.volume = volume;
		addValidField(FieldNames.volume);
	}

	/**
	 * Get 仓内外
	 */
	@Column(name = "INSIDE_OUTSIDE")
	public Integer getInsideOutside() {
		return insideOutside;
	}

	/**
	 * Set 仓内外
	 */
	public void setInsideOutside(Integer insideOutside) {
		this.insideOutside = insideOutside;
		addValidField(FieldNames.insideOutside);
	}

	/**
	 * Get 结算代码
	 */
	@Column(name = "SETTLEMENT_CODE")
	public String getSettlementCode() {
		return settlementCode;
	}

	/**
	 * Set 结算代码
	 */
	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
		addValidField(FieldNames.settlementCode);
	}

	/**
	 * Get 堆位
	 */
	@Column(name = "STACK_LOCATION")
	public String getStackLocation() {
		return stackLocation;
	}

	/**
	 * Set 堆位
	 */
	public void setStackLocation(String stackLocation) {
		this.stackLocation = stackLocation;
		addValidField(FieldNames.stackLocation);
	}

	/**
	 * Get 唛头
	 */
	@Column(name = "MARKS")
	public String getMarks() {
		return marks;
	}

	/**
	 * Set 唛头
	 */
	public void setMarks(String marks) {
		this.marks = marks;
		addValidField(FieldNames.marks);
	}

	/**
	 * Get 备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * Set 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
		addValidField(FieldNames.remark);
	}

	/**
	 * Get 卡号
	 */
	@Column(name = "CARD_NUMBER")
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * Set 卡号
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
		addValidField(FieldNames.cardNumber);
	}

	/**
	 * Get isBonded
	 */
	@Column(name = "IS_BONDED")
	public Integer getIsBonded() {
		return isBonded;
	}

	/**
	 * Set isBonded
	 */
	public void setIsBonded(Integer isBonded) {
		this.isBonded = isBonded;
		addValidField(FieldNames.isBonded);
	}

	/**
	 * Get 是否有效
	 */
	@Column(name = "ACTIVITY")
	public Integer getActivity() {
		return activity;
	}

	/**
	 * Set 是否有效
	 */
	public void setActivity(Integer activity) {
		this.activity = activity;
		addValidField(FieldNames.activity);
	}

	/**
	 * Get 货物权属人代码
	 */
	@Column(name = "CARGO_OWNER")
	public String getCargoOwner() {
		return cargoOwner;
	}

	/**
	 * Set 货物权属人代码
	 */
	public void setCargoOwner(String cargoOwner) {
		this.cargoOwner = cargoOwner;
		addValidField(FieldNames.cargoOwner);
	}

	/**
	 * Get 货物权属人名称
	 */
	@Column(name = "CARGO_OWNER_NAME")
	public String getCargoOwnerName() {
		return cargoOwnerName;
	}

	/**
	 * Set 货物权属人名称
	 */
	public void setCargoOwnerName(String cargoOwnerName) {
		this.cargoOwnerName = cargoOwnerName;
		addValidField(FieldNames.cargoOwnerName);
	}

	/**
	 * Get 录入人
	 */
	@Column(name = "CREATED_BY_USER")
	public String getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * Set 录入人
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
		addValidField(FieldNames.createdByUser);
	}

	/**
	 * Get 创建组织
	 */
	@Column(name = "CREATE_OFFICE")
	public String getCreateOffice() {
		return createOffice;
	}

	/**
	 * Set 创建组织
	 */
	public void setCreateOffice(String createOffice) {
		this.createOffice = createOffice;
		addValidField(FieldNames.createOffice);
	}

	/**
	 * Get 创建时间
	 */
	@Column(name = "CREATED_DTM_LOC")
	public Date getCreatedDtmLoc() {
		return createdDtmLoc;
	}

	/**
	 * Set 创建时间
	 */
	public void setCreatedDtmLoc(Date createdDtmLoc) {
		this.createdDtmLoc = createdDtmLoc;
		addValidField(FieldNames.createdDtmLoc);
	}

	/**
	 * Get 创建者当地时区
	 */
	@Column(name = "CREATED_TIME_ZONE")
	public String getCreatedTimeZone() {
		return createdTimeZone;
	}

	/**
	 * Set 创建者当地时区
	 */
	public void setCreatedTimeZone(String createdTimeZone) {
		this.createdTimeZone = createdTimeZone;
		addValidField(FieldNames.createdTimeZone);
	}

	/**
	 * Get 修改人
	 */
	@Column(name = "UPDATED_BY_USER")
	public String getUpdatedByUser() {
		return updatedByUser;
	}

	/**
	 * Set 修改人
	 */
	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
		addValidField(FieldNames.updatedByUser);
	}

	/**
	 * Get 修改组织
	 */
	@Column(name = "UPDATED_OFFICE")
	public String getUpdatedOffice() {
		return updatedOffice;
	}

	/**
	 * Set 修改组织
	 */
	public void setUpdatedOffice(String updatedOffice) {
		this.updatedOffice = updatedOffice;
		addValidField(FieldNames.updatedOffice);
	}

	/**
	 * Get 修改时间
	 */
	@Column(name = "UPDATED_DTM_LOC")
	public Date getUpdatedDtmLoc() {
		return updatedDtmLoc;
	}

	/**
	 * Set 修改时间
	 */
	public void setUpdatedDtmLoc(Date updatedDtmLoc) {
		this.updatedDtmLoc = updatedDtmLoc;
		addValidField(FieldNames.updatedDtmLoc);
	}

	/**
	 * Get 修改者当地时区
	 */
	@Column(name = "UPDATED_TIME_ZONE")
	public String getUpdatedTimeZone() {
		return updatedTimeZone;
	}

	/**
	 * Set 修改者当地时区
	 */
	public void setUpdatedTimeZone(String updatedTimeZone) {
		this.updatedTimeZone = updatedTimeZone;
		addValidField(FieldNames.updatedTimeZone);
	}

	/**
	 * Get 记录版本
	 */
	@Column(name = "RECORD_VERSION")
	@Version
	public Long getRecordVersion() {
		return recordVersion;
	}

	/**
	 * Set 记录版本
	 */
	public void setRecordVersion(Long recordVersion) {
		this.recordVersion = recordVersion;
		addValidField(FieldNames.recordVersion);
	}

	/**
	 * Get 公司代码
	 */
	@Column(name = "PRINCIPAL_GROUP_CODE")
	public String getPrincipalGroupCode() {
		return principalGroupCode;
	}

	/**
	 * Set 公司代码
	 */
	public void setPrincipalGroupCode(String principalGroupCode) {
		this.principalGroupCode = principalGroupCode;
		addValidField(FieldNames.principalGroupCode);
	}

}
