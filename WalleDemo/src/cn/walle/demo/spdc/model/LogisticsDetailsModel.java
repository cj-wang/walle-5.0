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
 * Model class for 办单明细
 */
@Entity
@Table(name = "LOGISTICS_DETAILS")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class LogisticsDetailsModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "LogisticsDetails";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String logisticsDetailsId = "logisticsDetailsId";
		/**
		 * Phisical
		 */
		public static final String logisticsOrderId = "logisticsOrderId";
		/**
		 * 作业单号
		 */
		public static final String logisticsDetailsNo = "logisticsDetailsNo";
		/**
		 * 提单号
		 */
		public static final String blNo = "blNo";
		/**
		 * 验证码
		 */
		public static final String verificationCode = "verificationCode";
		/**
		 * 作业项目
		 */
		public static final String serviceType = "serviceType";
		/**
		 * 作业情况
		 */
		public static final String serviceRequirements = "serviceRequirements";
		/**
		 * 过磅
		 */
		public static final String weighted = "weighted";
		/**
		 * 付款方式
		 */
		public static final String payment = "payment";
		/**
		 * 特约事项
		 */
		public static final String special = "special";
		/**
		 * 付款人
		 */
		public static final String payer = "payer";
		/**
		 * 结算日期
		 */
		public static final String cutoffDate = "cutoffDate";
		/**
		 * 中转港
		 */
		public static final String portOfTranshipment = "portOfTranshipment";
		/**
		 * 目的港
		 */
		public static final String portOfDestination = "portOfDestination";
		/**
		 * 目的地
		 */
		public static final String destination = "destination";
		/**
		 * 目的国
		 */
		public static final String destinationCountry = "destinationCountry";
		/**
		 * 是否有效
		 */
		public static final String activity = "activity";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * 车牌
		 */
		public static final String truckNo = "truckNo";
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
		 * 作业活动状态(PK为提
		 */
		public static final String workActivity = "workActivity";
		/**
		 * 控箱公司
		 */
		public static final String operatorComp = "operatorComp";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
	}

	//Phisical
	private String logisticsDetailsId;
	//Phisical
	private String logisticsOrderId;
	//作业单号
	private String logisticsDetailsNo;
	//提单号
	private String blNo;
	//验证码
	private String verificationCode;
	//作业项目
	private String serviceType;
	//作业情况
	private String serviceRequirements;
	//过磅
	private Integer weighted;
	//付款方式
	private String payment;
	//特约事项
	private String special;
	//付款人
	private String payer;
	//结算日期
	private Date cutoffDate;
	//中转港
	private String portOfTranshipment;
	//目的港
	private String portOfDestination;
	//目的地
	private String destination;
	//目的国
	private String destinationCountry;
	//是否有效
	private Integer activity;
	//备注
	private String remark;
	//车牌
	private String truckNo;
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
	//作业活动状态(PK为提
	private String workActivity;
	//控箱公司
	private String operatorComp;
	//公司代码
	private String principalGroupCode;

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_DETAILS_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
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
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_ORDER_ID")
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
	@Column(name = "LOGISTICS_DETAILS_NO")
	public String getLogisticsDetailsNo() {
		return logisticsDetailsNo;
	}

	/**
	 * Set 作业单号
	 */
	public void setLogisticsDetailsNo(String logisticsDetailsNo) {
		this.logisticsDetailsNo = logisticsDetailsNo;
		addValidField(FieldNames.logisticsDetailsNo);
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
	 * Get 验证码
	 */
	@Column(name = "VERIFICATION_CODE")
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * Set 验证码
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
		addValidField(FieldNames.verificationCode);
	}

	/**
	 * Get 作业项目
	 */
	@Column(name = "SERVICE_TYPE")
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * Set 作业项目
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
		addValidField(FieldNames.serviceType);
	}

	/**
	 * Get 作业情况
	 */
	@Column(name = "SERVICE_REQUIREMENTS")
	public String getServiceRequirements() {
		return serviceRequirements;
	}

	/**
	 * Set 作业情况
	 */
	public void setServiceRequirements(String serviceRequirements) {
		this.serviceRequirements = serviceRequirements;
		addValidField(FieldNames.serviceRequirements);
	}

	/**
	 * Get 过磅
	 */
	@Column(name = "WEIGHTED")
	public Integer getWeighted() {
		return weighted;
	}

	/**
	 * Set 过磅
	 */
	public void setWeighted(Integer weighted) {
		this.weighted = weighted;
		addValidField(FieldNames.weighted);
	}

	/**
	 * Get 付款方式
	 */
	@Column(name = "PAYMENT")
	public String getPayment() {
		return payment;
	}

	/**
	 * Set 付款方式
	 */
	public void setPayment(String payment) {
		this.payment = payment;
		addValidField(FieldNames.payment);
	}

	/**
	 * Get 特约事项
	 */
	@Column(name = "SPECIAL")
	public String getSpecial() {
		return special;
	}

	/**
	 * Set 特约事项
	 */
	public void setSpecial(String special) {
		this.special = special;
		addValidField(FieldNames.special);
	}

	/**
	 * Get 付款人
	 */
	@Column(name = "PAYER")
	public String getPayer() {
		return payer;
	}

	/**
	 * Set 付款人
	 */
	public void setPayer(String payer) {
		this.payer = payer;
		addValidField(FieldNames.payer);
	}

	/**
	 * Get 结算日期
	 */
	@Column(name = "CUTOFF_DATE")
	public Date getCutoffDate() {
		return cutoffDate;
	}

	/**
	 * Set 结算日期
	 */
	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
		addValidField(FieldNames.cutoffDate);
	}

	/**
	 * Get 中转港
	 */
	@Column(name = "PORT_OF_TRANSHIPMENT")
	public String getPortOfTranshipment() {
		return portOfTranshipment;
	}

	/**
	 * Set 中转港
	 */
	public void setPortOfTranshipment(String portOfTranshipment) {
		this.portOfTranshipment = portOfTranshipment;
		addValidField(FieldNames.portOfTranshipment);
	}

	/**
	 * Get 目的港
	 */
	@Column(name = "PORT_OF_DESTINATION")
	public String getPortOfDestination() {
		return portOfDestination;
	}

	/**
	 * Set 目的港
	 */
	public void setPortOfDestination(String portOfDestination) {
		this.portOfDestination = portOfDestination;
		addValidField(FieldNames.portOfDestination);
	}

	/**
	 * Get 目的地
	 */
	@Column(name = "DESTINATION")
	public String getDestination() {
		return destination;
	}

	/**
	 * Set 目的地
	 */
	public void setDestination(String destination) {
		this.destination = destination;
		addValidField(FieldNames.destination);
	}

	/**
	 * Get 目的国
	 */
	@Column(name = "DESTINATION_COUNTRY")
	public String getDestinationCountry() {
		return destinationCountry;
	}

	/**
	 * Set 目的国
	 */
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
		addValidField(FieldNames.destinationCountry);
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
	 * Get 车牌
	 */
	@Column(name = "TRUCK_NO")
	public String getTruckNo() {
		return truckNo;
	}

	/**
	 * Set 车牌
	 */
	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
		addValidField(FieldNames.truckNo);
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
	 * Get 作业活动状态(PK为提
	 * ,GR为还)
	 */
	@Column(name = "WORK_ACTIVITY")
	public String getWorkActivity() {
		return workActivity;
	}

	/**
	 * Set 作业活动状态(PK为提
	 * ,GR为还)
	 */
	public void setWorkActivity(String workActivity) {
		this.workActivity = workActivity;
		addValidField(FieldNames.workActivity);
	}

	/**
	 * Get 控箱公司
	 */
	@Column(name = "OPERATOR_COMP")
	public String getOperatorComp() {
		return operatorComp;
	}

	/**
	 * Set 控箱公司
	 */
	public void setOperatorComp(String operatorComp) {
		this.operatorComp = operatorComp;
		addValidField(FieldNames.operatorComp);
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
