package cn.walle.framework.core.model;

import java.util.Date;

public interface OperationLog {
	
	String getCreator();
	
	void setCreator(String creator);
	
	Date getCreateTime();
	
	void setCreateTime(Date createTime);
	
	String getModifier();
	
	void setModifier(String modifier);
	
	Date getModifyTime();
	
	void setModifyTime(Date modifyTime);

}
