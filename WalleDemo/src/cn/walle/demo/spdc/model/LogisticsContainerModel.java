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
 * Model class for 提单集装箱信息
 */
@Entity
@Table(name = "LOGISTICS_CONTAINER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class LogisticsContainerModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "LogisticsContainer";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String logisticsContainerId = "logisticsContainerId";
		/**
		 * Phisical
		 */
		public static final String logisticsDetailsId = "logisticsDetailsId";
		/**
		 * 箱号
		 */
		public static final String containerNo = "containerNo";
		/**
		 * 箱型代码
		 */
		public static final String containerType = "containerType";
		/**
		 * 状态
		 */
		public static final String status = "status";
		/**
		 * 封号
		 */
		public static final String sealNo = "sealNo";
		/**
		 * 提柜地点
		 */
		public static final String picLocation = "picLocation";
		/**
		 * 控箱公司
		 */
		public static final String operatorComp = "operatorComp";
		/**
		 * 箱属公司
		 */
		public static final String ownerComp = "ownerComp";
		/**
		 * 还柜地点
		 */
		public static final String backLocation = "backLocation";
		/**
		 * 拖车
		 */
		public static final String truck = "truck";
		/**
		 * 件数
		 */
		public static final String quantity = "quantity";
		/**
		 * 重量
		 */
		public static final String weight = "weight";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * S/O号
		 */
		public static final String soNo = "soNo";
		/**
		 * 有效日期
		 */
		public static final String effectiveDate = "effectiveDate";
		/**
		 * 陆运/船到
		 */
		public static final String landOrShip = "landOrShip";
		/**
		 * 先进先出
		 */
		public static final String isFirstinFirstout = "isFirstinFirstout";
		/**
		 * 箱号字头
		 */
		public static final String containerWord = "containerWord";
		/**
		 * 箱体重
		 */
		public static final String containerWeight = "containerWeight";
		/**
		 * 提单号
		 */
		public static final String blNo = "blNo";
		/**
		 * 车队
		 */
		public static final String truckTeam = "truckTeam";
		/**
		 * 船舶代码
		 */
		public static final String vesselCode = "vesselCode";
		/**
		 * 船舶名称
		 */
		public static final String vesselName = "vesselName";
		/**
		 * 录入人
		 */
		public static final String createdByUser = "createdByUser";
		/**
		 * 船舶航次
		 */
		public static final String voyage = "voyage";
		/**
		 * 是否有效
		 */
		public static final String activity = "activity";
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
		 * 箱状态
		 */
		public static final String containerState = "containerState";
		/**
		 * 堆位
		 */
		public static final String stackLocation = "stackLocation";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
	}

	//Phisical
	private String logisticsContainerId;
	//Phisical
	private String logisticsDetailsId;
	//箱号
	private String containerNo;
	//箱型代码
	private String containerType;
	//状态
	private String status;
	//封号
	private String sealNo;
	//提柜地点
	private String picLocation;
	//控箱公司
	private String operatorComp;
	//箱属公司
	private String ownerComp;
	//还柜地点
	private String backLocation;
	//拖车
	private String truck;
	//件数
	private Integer quantity;
	//重量
	private Double weight;
	//备注
	private String remark;
	//S/O号
	private String soNo;
	//有效日期
	private Date effectiveDate;
	//陆运/船到
	private Integer landOrShip;
	//先进先出
	private Integer isFirstinFirstout;
	//箱号字头
	private String containerWord;
	//箱体重
	private Double containerWeight;
	//提单号
	private String blNo;
	//车队
	private String truckTeam;
	//船舶代码
	private String vesselCode;
	//船舶名称
	private String vesselName;
	//录入人
	private String createdByUser;
	//船舶航次
	private String voyage;
	//是否有效
	private Integer activity;
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
	//箱状态
	private String containerState;
	//堆位
	private String stackLocation;
	//公司代码
	private String principalGroupCode;

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "LOGISTICS_CONTAINER_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getLogisticsContainerId() {
		return logisticsContainerId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setLogisticsContainerId(String logisticsContainerId) {
		this.logisticsContainerId = logisticsContainerId;
		addValidField(FieldNames.logisticsContainerId);
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
	 * Get 箱号
	 */
	@Column(name = "CONTAINER_NO")
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * Set 箱号
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
		addValidField(FieldNames.containerNo);
	}

	/**
	 * Get 箱型代码
	 */
	@Column(name = "CONTAINER_TYPE")
	public String getContainerType() {
		return containerType;
	}

	/**
	 * Set 箱型代码
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
		addValidField(FieldNames.containerType);
	}

	/**
	 * Get 状态
	 */
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	/**
	 * Set 状态
	 */
	public void setStatus(String status) {
		this.status = status;
		addValidField(FieldNames.status);
	}

	/**
	 * Get 封号
	 */
	@Column(name = "SEAL_NO")
	public String getSealNo() {
		return sealNo;
	}

	/**
	 * Set 封号
	 */
	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
		addValidField(FieldNames.sealNo);
	}

	/**
	 * Get 提柜地点
	 */
	@Column(name = "PIC_LOCATION")
	public String getPicLocation() {
		return picLocation;
	}

	/**
	 * Set 提柜地点
	 */
	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
		addValidField(FieldNames.picLocation);
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
	 * Get 箱属公司
	 */
	@Column(name = "OWNER_COMP")
	public String getOwnerComp() {
		return ownerComp;
	}

	/**
	 * Set 箱属公司
	 */
	public void setOwnerComp(String ownerComp) {
		this.ownerComp = ownerComp;
		addValidField(FieldNames.ownerComp);
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
	 * Get 拖车
	 */
	@Column(name = "TRUCK")
	public String getTruck() {
		return truck;
	}

	/**
	 * Set 拖车
	 */
	public void setTruck(String truck) {
		this.truck = truck;
		addValidField(FieldNames.truck);
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
	 * Get S/O号
	 */
	@Column(name = "SO_NO")
	public String getSoNo() {
		return soNo;
	}

	/**
	 * Set S/O号
	 */
	public void setSoNo(String soNo) {
		this.soNo = soNo;
		addValidField(FieldNames.soNo);
	}

	/**
	 * Get 有效日期
	 */
	@Column(name = "EFFECTIVE_DATE")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Set 有效日期
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
		addValidField(FieldNames.effectiveDate);
	}

	/**
	 * Get 陆运/船到
	 */
	@Column(name = "LAND_OR_SHIP")
	public Integer getLandOrShip() {
		return landOrShip;
	}

	/**
	 * Set 陆运/船到
	 */
	public void setLandOrShip(Integer landOrShip) {
		this.landOrShip = landOrShip;
		addValidField(FieldNames.landOrShip);
	}

	/**
	 * Get 先进先出
	 */
	@Column(name = "IS_FIRSTIN_FIRSTOUT")
	public Integer getIsFirstinFirstout() {
		return isFirstinFirstout;
	}

	/**
	 * Set 先进先出
	 */
	public void setIsFirstinFirstout(Integer isFirstinFirstout) {
		this.isFirstinFirstout = isFirstinFirstout;
		addValidField(FieldNames.isFirstinFirstout);
	}

	/**
	 * Get 箱号字头
	 */
	@Column(name = "CONTAINER_WORD")
	public String getContainerWord() {
		return containerWord;
	}

	/**
	 * Set 箱号字头
	 */
	public void setContainerWord(String containerWord) {
		this.containerWord = containerWord;
		addValidField(FieldNames.containerWord);
	}

	/**
	 * Get 箱体重
	 */
	@Column(name = "CONTAINER_WEIGHT")
	public Double getContainerWeight() {
		return containerWeight;
	}

	/**
	 * Set 箱体重
	 */
	public void setContainerWeight(Double containerWeight) {
		this.containerWeight = containerWeight;
		addValidField(FieldNames.containerWeight);
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
	 * Get 车队
	 */
	@Column(name = "TRUCK_TEAM")
	public String getTruckTeam() {
		return truckTeam;
	}

	/**
	 * Set 车队
	 */
	public void setTruckTeam(String truckTeam) {
		this.truckTeam = truckTeam;
		addValidField(FieldNames.truckTeam);
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
	 * Get 船舶名称
	 */
	@Column(name = "VESSEL_NAME")
	public String getVesselName() {
		return vesselName;
	}

	/**
	 * Set 船舶名称
	 */
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
		addValidField(FieldNames.vesselName);
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
	 * Get 箱状态
	 * E:空箱 F:重箱
	 */
	@Column(name = "CONTAINER_STATE")
	public String getContainerState() {
		return containerState;
	}

	/**
	 * Set 箱状态
	 * E:空箱 F:重箱
	 */
	public void setContainerState(String containerState) {
		this.containerState = containerState;
		addValidField(FieldNames.containerState);
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
