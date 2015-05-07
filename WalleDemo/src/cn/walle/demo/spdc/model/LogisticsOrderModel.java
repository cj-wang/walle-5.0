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
 * Model class for 办单主信息
 */
@Entity
@Table(name = "LOGISTICS_ORDER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class LogisticsOrderModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "LogisticsOrder";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String logisticsOrderId = "logisticsOrderId";
		/**
		 * 作业单号
		 */
		public static final String logisticsOrderNo = "logisticsOrderNo";
		/**
		 * 是否已确认
		 */
		public static final String isConfirmed = "isConfirmed";
		/**
		 * 是否计费
		 */
		public static final String isBill = "isBill";
		/**
		 * SO号
		 */
		public static final String soNo = "soNo";
		/**
		 * 提柜地点
		 */
		public static final String pickLocation = "pickLocation";
		/**
		 * 还柜地点
		 */
		public static final String backLocation = "backLocation";
		/**
		 * 委托人
		 */
		public static final String consignee = "consignee";
		/**
		 * 委托人名称
		 */
		public static final String consigneeName = "consigneeName";
		/**
		 * 客户委托单号
		 */
		public static final String consigneeNo = "consigneeNo";
		/**
		 * 收货人
		 */
		public static final String consigner = "consigner";
		/**
		 * 收货人名称
		 */
		public static final String consignerName = "consignerName";
		/**
		 * 提/还吉重
		 */
		public static final String logisticsOrderType = "logisticsOrderType";
		/**
		 * 船舶代码
		 */
		public static final String vesselCode = "vesselCode";
		/**
		 * 船舶航次
		 */
		public static final String voyage = "voyage";
		/**
		 * 预出口日期
		 */
		public static final String estimatedToDeparture = "estimatedToDeparture";
		/**
		 * 申请日期
		 */
		public static final String applicationDate = "applicationDate";
		/**
		 * 经办人
		 */
		public static final String functionary = "functionary";
		/**
		 * 合同号
		 */
		public static final String contractNo = "contractNo";
		/**
		 * 合同日期
		 */
		public static final String contractDate = "contractDate";
		/**
		 * XD单来源单号
		 */
		public static final String logisticsOrdernoRef = "logisticsOrdernoRef";
		/**
		 * 提货有效期
		 */
		public static final String deliveryDate = "deliveryDate";
		/**
		 * 进口日期
		 */
		public static final String feedinDate = "feedinDate";
		/**
		 * 控制字
		 */
		public static final String controlWord = "controlWord";
		/**
		 * 办单日期
		 */
		public static final String orderDate = "orderDate";
		/**
		 * 联系电话
		 */
		public static final String telephoneno = "telephoneno";
		/**
		 * 是否有效
		 */
		public static final String activity = "activity";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
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
		 * 是否锁定
		 */
		public static final String isLocked = "isLocked";
		/**
		 * 单证类别
		 */
		public static final String logisticsOrderClass = "logisticsOrderClass";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
	}

	//Phisical
	private String logisticsOrderId;
	//作业单号
	private String logisticsOrderNo;
	//是否已确认
	private Integer isConfirmed;
	//是否计费
	private Integer isBill;
	//SO号
	private String soNo;
	//提柜地点
	private String pickLocation;
	//还柜地点
	private String backLocation;
	//委托人
	private String consignee;
	//委托人名称
	private String consigneeName;
	//客户委托单号
	private String consigneeNo;
	//收货人
	private String consigner;
	//收货人名称
	private String consignerName;
	//提/还吉重
	private String logisticsOrderType;
	//船舶代码
	private String vesselCode;
	//船舶航次
	private String voyage;
	//预出口日期
	private Date estimatedToDeparture;
	//申请日期
	private Date applicationDate;
	//经办人
	private String functionary;
	//合同号
	private String contractNo;
	//合同日期
	private Date contractDate;
	//XD单来源单号
	private String logisticsOrdernoRef;
	//提货有效期
	private Date deliveryDate;
	//进口日期
	private Date feedinDate;
	//控制字
	private String controlWord;
	//办单日期
	private Date orderDate;
	//联系电话
	private String telephoneno;
	//是否有效
	private Integer activity;
	//备注
	private String remark;
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
	//是否锁定
	private Integer isLocked;
	//单证类别
	private String logisticsOrderClass;
	//公司代码
	private String principalGroupCode;

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_ORDER_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getLogisticsOrderId() {
		return logisticsOrderId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setLogisticsOrderId(String logisticsOrderId) {
		this.logisticsOrderId = logisticsOrderId;
		addValidField(FieldNames.logisticsOrderId);
	}

	/**
	 * Get 作业单号
	 */
	@Column(name = "LOGISTICS_ORDER_NO")
	public String getLogisticsOrderNo() {
		return logisticsOrderNo;
	}

	/**
	 * Set 作业单号
	 */
	public void setLogisticsOrderNo(String logisticsOrderNo) {
		this.logisticsOrderNo = logisticsOrderNo;
		addValidField(FieldNames.logisticsOrderNo);
	}

	/**
	 * Get 是否已确认
	 */
	@Column(name = "IS_CONFIRMED")
	public Integer getIsConfirmed() {
		return isConfirmed;
	}

	/**
	 * Set 是否已确认
	 */
	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
		addValidField(FieldNames.isConfirmed);
	}

	/**
	 * Get 是否计费
	 */
	@Column(name = "IS_BILL")
	public Integer getIsBill() {
		return isBill;
	}

	/**
	 * Set 是否计费
	 */
	public void setIsBill(Integer isBill) {
		this.isBill = isBill;
		addValidField(FieldNames.isBill);
	}

	/**
	 * Get SO号
	 */
	@Column(name = "SO_NO")
	public String getSoNo() {
		return soNo;
	}

	/**
	 * Set SO号
	 */
	public void setSoNo(String soNo) {
		this.soNo = soNo;
		addValidField(FieldNames.soNo);
	}

	/**
	 * Get 提柜地点
	 */
	@Column(name = "PICK_LOCATION")
	public String getPickLocation() {
		return pickLocation;
	}

	/**
	 * Set 提柜地点
	 */
	public void setPickLocation(String pickLocation) {
		this.pickLocation = pickLocation;
		addValidField(FieldNames.pickLocation);
	}

	/**
	 * Get 还柜地点
	 */
	@Column(name = "BACK_LOCATION")
	public String getBackLocation() {
		return backLocation;
	}

	/**
	 * Set 还柜地点
	 */
	public void setBackLocation(String backLocation) {
		this.backLocation = backLocation;
		addValidField(FieldNames.backLocation);
	}

	/**
	 * Get 委托人
	 */
	@Column(name = "CONSIGNEE")
	public String getConsignee() {
		return consignee;
	}

	/**
	 * Set 委托人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
		addValidField(FieldNames.consignee);
	}

	/**
	 * Get 委托人名称
	 */
	@Column(name = "CONSIGNEE_NAME")
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * Set 委托人名称
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
		addValidField(FieldNames.consigneeName);
	}

	/**
	 * Get 客户委托单号
	 */
	@Column(name = "CONSIGNEE_NO")
	public String getConsigneeNo() {
		return consigneeNo;
	}

	/**
	 * Set 客户委托单号
	 */
	public void setConsigneeNo(String consigneeNo) {
		this.consigneeNo = consigneeNo;
		addValidField(FieldNames.consigneeNo);
	}

	/**
	 * Get 收货人
	 */
	@Column(name = "CONSIGNER")
	public String getConsigner() {
		return consigner;
	}

	/**
	 * Set 收货人
	 */
	public void setConsigner(String consigner) {
		this.consigner = consigner;
		addValidField(FieldNames.consigner);
	}

	/**
	 * Get 收货人名称
	 */
	@Column(name = "CONSIGNER_NAME")
	public String getConsignerName() {
		return consignerName;
	}

	/**
	 * Set 收货人名称
	 */
	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
		addValidField(FieldNames.consignerName);
	}

	/**
	 * Get 提/还吉重
	 */
	@Column(name = "LOGISTICS_ORDER_TYPE")
	public String getLogisticsOrderType() {
		return logisticsOrderType;
	}

	/**
	 * Set 提/还吉重
	 */
	public void setLogisticsOrderType(String logisticsOrderType) {
		this.logisticsOrderType = logisticsOrderType;
		addValidField(FieldNames.logisticsOrderType);
	}

	/**
	 * Get 船舶代码
	 */
	@Column(name = "VESSEL_CODE")
	public String getVesselCode() {
		return vesselCode;
	}

	/**
	 * Set 船舶代码
	 */
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
		addValidField(FieldNames.vesselCode);
	}

	/**
	 * Get 船舶航次
	 */
	@Column(name = "VOYAGE")
	public String getVoyage() {
		return voyage;
	}

	/**
	 * Set 船舶航次
	 */
	public void setVoyage(String voyage) {
		this.voyage = voyage;
		addValidField(FieldNames.voyage);
	}

	/**
	 * Get 预出口日期
	 */
	@Column(name = "ESTIMATED_TO_DEPARTURE")
	public Date getEstimatedToDeparture() {
		return estimatedToDeparture;
	}

	/**
	 * Set 预出口日期
	 */
	public void setEstimatedToDeparture(Date estimatedToDeparture) {
		this.estimatedToDeparture = estimatedToDeparture;
		addValidField(FieldNames.estimatedToDeparture);
	}

	/**
	 * Get 申请日期
	 */
	@Column(name = "APPLICATION_DATE")
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * Set 申请日期
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
		addValidField(FieldNames.applicationDate);
	}

	/**
	 * Get 经办人
	 */
	@Column(name = "FUNCTIONARY")
	public String getFunctionary() {
		return functionary;
	}

	/**
	 * Set 经办人
	 */
	public void setFunctionary(String functionary) {
		this.functionary = functionary;
		addValidField(FieldNames.functionary);
	}

	/**
	 * Get 合同号
	 */
	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * Set 合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
		addValidField(FieldNames.contractNo);
	}

	/**
	 * Get 合同日期
	 */
	@Column(name = "CONTRACT_DATE")
	public Date getContractDate() {
		return contractDate;
	}

	/**
	 * Set 合同日期
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
		addValidField(FieldNames.contractDate);
	}

	/**
	 * Get XD单来源单号
	 */
	@Column(name = "LOGISTICS_ORDERNO_REF")
	public String getLogisticsOrdernoRef() {
		return logisticsOrdernoRef;
	}

	/**
	 * Set XD单来源单号
	 */
	public void setLogisticsOrdernoRef(String logisticsOrdernoRef) {
		this.logisticsOrdernoRef = logisticsOrdernoRef;
		addValidField(FieldNames.logisticsOrdernoRef);
	}

	/**
	 * Get 提货有效期
	 */
	@Column(name = "DELIVERY_DATE")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Set 提货有效期
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
		addValidField(FieldNames.deliveryDate);
	}

	/**
	 * Get 进口日期
	 */
	@Column(name = "FEEDIN_DATE")
	public Date getFeedinDate() {
		return feedinDate;
	}

	/**
	 * Set 进口日期
	 */
	public void setFeedinDate(Date feedinDate) {
		this.feedinDate = feedinDate;
		addValidField(FieldNames.feedinDate);
	}

	/**
	 * Get 控制字
	 */
	@Column(name = "CONTROL_WORD")
	public String getControlWord() {
		return controlWord;
	}

	/**
	 * Set 控制字
	 */
	public void setControlWord(String controlWord) {
		this.controlWord = controlWord;
		addValidField(FieldNames.controlWord);
	}

	/**
	 * Get 办单日期
	 */
	@Column(name = "ORDER_DATE")
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * Set 办单日期
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
		addValidField(FieldNames.orderDate);
	}

	/**
	 * Get 联系电话
	 */
	@Column(name = "TELEPHONENO")
	public String getTelephoneno() {
		return telephoneno;
	}

	/**
	 * Set 联系电话
	 */
	public void setTelephoneno(String telephoneno) {
		this.telephoneno = telephoneno;
		addValidField(FieldNames.telephoneno);
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
	 * Get 是否锁定
	 */
	@Column(name = "IS_LOCKED")
	public Integer getIsLocked() {
		return isLocked;
	}

	/**
	 * Set 是否锁定
	 */
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
		addValidField(FieldNames.isLocked);
	}

	/**
	 * Get 单证类别
	 * 譬如：X 为集装箱出口作业办单
	 */
	@Column(name = "LOGISTICS_ORDER_CLASS")
	public String getLogisticsOrderClass() {
		return logisticsOrderClass;
	}

	/**
	 * Set 单证类别
	 * 譬如：X 为集装箱出口作业办单
	 */
	public void setLogisticsOrderClass(String logisticsOrderClass) {
		this.logisticsOrderClass = logisticsOrderClass;
		addValidField(FieldNames.logisticsOrderClass);
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
