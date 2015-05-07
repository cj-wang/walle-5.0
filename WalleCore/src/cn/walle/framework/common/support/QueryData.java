package cn.walle.framework.common.support;

import java.util.List;
import java.util.Map;

import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.model.BaseObject;
import cn.walle.framework.core.support.PagingInfo;

public class QueryData extends BaseObject {
	
	private List<?> dataList;
	//Map<codeType, Map<key, label>>
	private Map<String, Map<Object, String>> selectCodeValues;
	private PagingInfo pagingInfo;

	public List<?> getDataList() {
		return dataList;
	}
	public <ITEM extends BaseModel> List<ITEM> getDataList(Class<ITEM> itemClass) {
		return (List<ITEM>) dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public Map<String, Map<Object, String>> getSelectCodeValues() {
		return selectCodeValues;
	}
	public void setSelectCodeValues(
			Map<String, Map<Object, String>> selectCodeValues) {
		this.selectCodeValues = selectCodeValues;
	}
	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

}
