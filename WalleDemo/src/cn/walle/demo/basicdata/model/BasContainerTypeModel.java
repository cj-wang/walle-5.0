package cn.walle.demo.basicdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for comment
 * on table  BAS_CONTAINER_TYPE  is  箱型表
 */
@Entity
@Table(name = "BAS_CONTAINER_TYPE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BasContainerTypeModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "BasContainerType";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String basContainerTypeId = "basContainerTypeId";
		/**
		 * 箱型代码
		 */
		public static final String containerType = "containerType";
		/**
		 * 尺寸(FT)
		 */
		public static final String sizeClass = "sizeClass";
		/**
		 * 类型
		 */
		public static final String typeClass = "typeClass";
		/**
		 * 长度(FT)
		 */
		public static final String length = "length";
		/**
		 * 宽度(FT)
		 */
		public static final String width = "width";
		/**
		 * 高度(FT)
		 */
		public static final String height = "height";
		/**
		 * 皮重(KG)
		 */
		public static final String weight = "weight";
		/**
		 * 载重(KG)
		 */
		public static final String payload = "payload";
		/**
		 * TEU
		 */
		public static final String teu = "teu";
		/**
		 * 国际标准
		 */
		public static final String containerIsoNo = "containerIsoNo";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * 有效
		 */
		public static final String active = "active";
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
	private String basContainerTypeId;
	//箱型代码
	private String containerType;
	//尺寸(FT)
	private Integer sizeClass;
	//类型
	private String typeClass;
	//长度(FT)
	private Double length;
	//宽度(FT)
	private Double width;
	//高度(FT)
	private Double height;
	//皮重(KG)
	private Double weight;
	//载重(KG)
	private Double payload;
	//TEU
	private Double teu;
	//国际标准
	private String containerIsoNo;
	//备注
	private String remark;
	//有效
	private Integer active;
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
	@Column(name = "BAS_CONTAINER_TYPE_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getBasContainerTypeId() {
		return basContainerTypeId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setBasContainerTypeId(String basContainerTypeId) {
		this.basContainerTypeId = basContainerTypeId;
		addValidField(FieldNames.basContainerTypeId);
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
	 * Get 尺寸(FT)
	 */
	@Column(name = "SIZE_CLASS")
	public Integer getSizeClass() {
		return sizeClass;
	}

	/**
	 * Set 尺寸(FT)
	 */
	public void setSizeClass(Integer sizeClass) {
		this.sizeClass = sizeClass;
		addValidField(FieldNames.sizeClass);
	}

	/**
	 * Get 类型
	 */
	@Column(name = "TYPE_CLASS")
	public String getTypeClass() {
		return typeClass;
	}

	/**
	 * Set 类型
	 */
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
		addValidField(FieldNames.typeClass);
	}

	/**
	 * Get 长度(FT)
	 */
	@Column(name = "LENGTH")
	public Double getLength() {
		return length;
	}

	/**
	 * Set 长度(FT)
	 */
	public void setLength(Double length) {
		this.length = length;
		addValidField(FieldNames.length);
	}

	/**
	 * Get 宽度(FT)
	 */
	@Column(name = "WIDTH")
	public Double getWidth() {
		return width;
	}

	/**
	 * Set 宽度(FT)
	 */
	public void setWidth(Double width) {
		this.width = width;
		addValidField(FieldNames.width);
	}

	/**
	 * Get 高度(FT)
	 */
	@Column(name = "HEIGHT")
	public Double getHeight() {
		return height;
	}

	/**
	 * Set 高度(FT)
	 */
	public void setHeight(Double height) {
		this.height = height;
		addValidField(FieldNames.height);
	}

	/**
	 * Get 皮重(KG)
	 */
	@Column(name = "WEIGHT")
	public Double getWeight() {
		return weight;
	}

	/**
	 * Set 皮重(KG)
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
		addValidField(FieldNames.weight);
	}

	/**
	 * Get 载重(KG)
	 */
	@Column(name = "PAYLOAD")
	public Double getPayload() {
		return payload;
	}

	/**
	 * Set 载重(KG)
	 */
	public void setPayload(Double payload) {
		this.payload = payload;
		addValidField(FieldNames.payload);
	}

	/**
	 * Get TEU
	 */
	@Column(name = "TEU")
	public Double getTeu() {
		return teu;
	}

	/**
	 * Set TEU
	 */
	public void setTeu(Double teu) {
		this.teu = teu;
		addValidField(FieldNames.teu);
	}

	/**
	 * Get 国际标准
	 */
	@Column(name = "CONTAINER_ISO_NO")
	public String getContainerIsoNo() {
		return containerIsoNo;
	}

	/**
	 * Set 国际标准
	 */
	public void setContainerIsoNo(String containerIsoNo) {
		this.containerIsoNo = containerIsoNo;
		addValidField(FieldNames.containerIsoNo);
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
	 * Get 有效
	 */
	@Column(name = "ACTIVE")
	public Integer getActive() {
		return active;
	}

	/**
	 * Set 有效
	 */
	public void setActive(Integer active) {
		this.active = active;
		addValidField(FieldNames.active);
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
