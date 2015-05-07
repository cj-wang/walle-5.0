package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;


public class GrantedUserByFuncQueryCondition extends BaseQueryCondition {

	private String funcId;

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

}
