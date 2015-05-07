package cn.walle.framework.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all Models.
 * @author cj
 *
 */
public abstract class BaseModelClass extends BaseObject implements BaseModel {

	private String rowState;

	private List<String> validFields = new ArrayList<String>();
	
	public String getRowState() {
		return rowState;
	}

	public void setRowState(String rowState) {
		this.rowState = rowState;
	}

	public void addValidField(String fieldName) {
		if (! validFields.contains(fieldName)) {
			validFields.add(fieldName);
		}
	}
	
	public List<String> validFields() {
		return validFields;
	}
	
}
