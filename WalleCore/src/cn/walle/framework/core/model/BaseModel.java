package cn.walle.framework.core.model;

import java.util.List;

public interface BaseModel {

	static final String ROW_STATE_ADDED = "Added";
	static final String ROW_STATE_DELETED = "Deleted";
	static final String ROW_STATE_MODIFIED = "Modified";

	String getRowState();

	void setRowState(String rowState);

	void addValidField(String fieldName);
	
	List<String> validFields();

}