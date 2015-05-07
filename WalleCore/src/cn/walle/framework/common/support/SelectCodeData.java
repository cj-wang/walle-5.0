package cn.walle.framework.common.support;

public class SelectCodeData extends QueryData {

	private String keyFieldName;
	private String labelFieldName;
	
	public String getKeyFieldName() {
		return keyFieldName;
	}
	public void setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
	}
	public String getLabelFieldName() {
		return labelFieldName;
	}
	public void setLabelFieldName(String labelFieldName) {
		this.labelFieldName = labelFieldName;
	}
	
}
