package cn.walle.framework.core.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DynamicModelClass extends LinkedHashMap<String, Object> implements BaseModel {

	@Override
	public String getRowState() {
		return (String) super.get("rowState");
	}

	@Override
	public void setRowState(String rowState) {
		super.put("rowState", rowState);
	}

	@Override
	public void addValidField(String fieldName) {
		//
	}

	@Override
	public List<String> validFields() {
		return new ArrayList<String>(super.keySet());
	}

}
