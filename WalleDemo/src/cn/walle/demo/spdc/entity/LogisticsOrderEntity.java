package cn.walle.demo.spdc.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.walle.demo.spdc.model.LogisticsOrderModel;

public class LogisticsOrderEntity extends LogisticsOrderModel {

	private List<LogisticsDetailsEntity> details;
	private Map<String, Map<Object, String>> selectCodeValues;
	
	public List<LogisticsDetailsEntity> getDetails() {
		return details;
	}

	public void setDetails(List<LogisticsDetailsEntity> details) {
		this.details = details;
	}

	public Map<String, Map<Object, String>> getSelectCodeValues() {
		return selectCodeValues;
	}

	public void setSelectCodeValues(Map<String, Map<Object, String>> selectCodeValues) {
		this.selectCodeValues = selectCodeValues;
	}
	
}
