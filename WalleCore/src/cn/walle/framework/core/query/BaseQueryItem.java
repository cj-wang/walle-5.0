package cn.walle.framework.core.query;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Base class for all QueryItems.
 * @author cj
 *
 */
@MappedSuperclass
public abstract class BaseQueryItem extends BaseModelClass {

	private String uuid;
	private Integer rownum;

	@Column(name = "UUID__")
	@Id
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Transient
	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

}
