package cn.walle.system.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class OrgUserAsyncTreeCheckedByFuncQueryItem extends BaseQueryItem {

	private String id;
	private String name;
	private String parentId;
	private String checked;
	private String nodeState;

	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		addValidField("id");
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		addValidField("name");
	}

	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
		addValidField("parentId");
	}

	@Column(name = "CHECKED")
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
		addValidField("checked");
	}

	@Column(name = "NODE_STATE")
	public String getNodeState() {
		return nodeState;
	}

	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
		addValidField("nodeState");
	}

}
