package cn.walle.platform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.OperationLog;

/**
 * Model class for WpDynamicService
 */
@Entity
@Table(name = "WP_DYNAMIC_SERVICE")
@DynamicInsert
@DynamicUpdate
public class WpDynamicServiceModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WpDynamicService";

	public static final class FieldNames {
		/**
		 * uuid
		 */
		public static final String uuid = "uuid";
		/**
		 * serviceName
		 */
		public static final String serviceName = "serviceName";
		/**
		 * contentType
		 */
		public static final String contentType = "contentType";
		/**
		 * content
		 */
		public static final String content = "content";
		/**
		 * parentUuid
		 */
		public static final String parentUuid = "parentUuid";
		/**
		 * seq
		 */
		public static final String seq = "seq";
		/**
		 * recVer
		 */
		public static final String recVer = "recVer";
		/**
		 * creator
		 */
		public static final String creator = "creator";
		/**
		 * createTime
		 */
		public static final String createTime = "createTime";
		/**
		 * modifier
		 */
		public static final String modifier = "modifier";
		/**
		 * modifyTime
		 */
		public static final String modifyTime = "modifyTime";
	}

	//uuid
	private String uuid;
	//serviceName
	private String serviceName;
	//contentType
	private String contentType;
	//content
	private String content;
	//parentUuid
	private String parentUuid;
	//seq
	private Integer seq;
	//recVer
	private Long recVer;
	//creator
	private String creator;
	//createTime
	private Date createTime;
	//modifier
	private String modifier;
	//modifyTime
	private Date modifyTime;

	/**
	 * Get uuid
	 */
	@Column(name = "UUID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getUuid() {
		return uuid;
	}

	/**
	 * Set uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
		addValidField(FieldNames.uuid);
	}

	/**
	 * Get serviceName
	 */
	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Set serviceName
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
		addValidField(FieldNames.serviceName);
	}

	/**
	 * Get contentType
	 */
	@Column(name = "CONTENT_TYPE")
	public String getContentType() {
		return contentType;
	}

	/**
	 * Set contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
		addValidField(FieldNames.contentType);
	}

	/**
	 * Get content
	 */
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	/**
	 * Set content
	 */
	public void setContent(String content) {
		this.content = content;
		addValidField(FieldNames.content);
	}

	/**
	 * Get parentUuid
	 */
	@Column(name = "PARENT_UUID")
	public String getParentUuid() {
		return parentUuid;
	}

	/**
	 * Set parentUuid
	 */
	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
		addValidField(FieldNames.parentUuid);
	}

	/**
	 * Get seq
	 */
	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	/**
	 * Set seq
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
		addValidField(FieldNames.seq);
	}

	/**
	 * Get recVer
	 */
	@Column(name = "REC_VER")
	@Version
	public Long getRecVer() {
		return recVer;
	}

	/**
	 * Set recVer
	 */
	public void setRecVer(Long recVer) {
		this.recVer = recVer;
		addValidField(FieldNames.recVer);
	}

	/**
	 * Get creator
	 */
	@Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	/**
	 * Set creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
		addValidField(FieldNames.creator);
	}

	/**
	 * Get createTime
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField(FieldNames.createTime);
	}

	/**
	 * Get modifier
	 */
	@Column(name = "MODIFIER")
	public String getModifier() {
		return modifier;
	}

	/**
	 * Set modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
		addValidField(FieldNames.modifier);
	}

	/**
	 * Get modifyTime
	 */
	@Column(name = "MODIFY_TIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		addValidField(FieldNames.modifyTime);
	}

}
