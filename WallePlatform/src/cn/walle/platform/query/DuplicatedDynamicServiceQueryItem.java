package cn.walle.platform.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class DuplicatedDynamicServiceQueryItem extends BaseQueryItem {

	private String serviceName;

	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
		addValidField("serviceName");
	}

}
