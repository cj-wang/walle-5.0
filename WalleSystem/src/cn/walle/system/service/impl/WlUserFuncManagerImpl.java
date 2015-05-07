package cn.walle.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.model.WlUserFuncModel;
import cn.walle.system.service.WlFunctionManager;
import cn.walle.system.service.WlSysLogManager;
import cn.walle.system.service.WlUserFuncManager;
import cn.walle.system.service.WlUserManager;

@Service
public class WlUserFuncManagerImpl extends BaseManagerImpl implements WlUserFuncManager {

	@Autowired
	private WlSysLogManager sysLogManager;
	@Autowired
	private WlUserManager userManager;
	@Autowired
	private WlFunctionManager funcManager;
	
	
	public WlUserFuncModel get(String id) {
		return this.dao.get(WlUserFuncModel.class, id);
	}

	public List<WlUserFuncModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlUserFuncModel.class, orderBy, pagingInfo);
	}

	public List<WlUserFuncModel> findByExample(WlUserFuncModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlUserFuncModel save(WlUserFuncModel model) {
		return this.dao.save(model);
	}

	public List<WlUserFuncModel> saveAll(Collection<WlUserFuncModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlUserFuncModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlUserFuncModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlUserFuncModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlUserFuncModel.class, ids);
	}
	public ArrayList getUserFunc(String userId)
	{
		WlUserFuncModel userModel=new WlUserFuncModel();
		userModel.setUserId(userId);
		List<WlUserFuncModel> userFuncModels=this.findByExample(userModel, null, null);
		if(userFuncModels.size()>0)
		{
			//java.awt.List funcIds=new java.awt.List();
			ArrayList funcIds=new ArrayList();
			funcIds.add(0,"1");
			for(int i=0;i<userFuncModels.size();i++)
			{
				funcIds.add( i+1,userFuncModels.get(i).getFuncId());
			}
			return funcIds;
		}
		return null;
	}
	public void saveFunc(String userId,List<String> funcIds)
	{
		WlUserFuncModel userModel=new WlUserFuncModel();
		userModel.setUserId(userId);
		Collection<WlUserFuncModel> models=this.findByExample(userModel, null, null);
		this.removeAll(models);
		for (String funcId : funcIds) {
			if(funcId==null || "".equals(funcId.trim())||"1".equals(funcId)){
				continue;
			}
			WlUserFuncModel usermodel=new WlUserFuncModel();
			usermodel.setUserId(userId);
			usermodel.setFuncId(funcId);
			this.save(usermodel);
			sysLogManager.saveSysLog("user", "人员赋权", "1", "对用户["+userManager.get(userId).getLoginName()+"]进行赋权操作", "人员赋权成功", null);
		}
	}

	@Override
	public void setPersonalHomePage(String id) {
		// TODO Auto-generated method stub
		
	}

	
	public void saveFuncsForUser(String userId, String[] funcIds) {
		this.removeAll(this.dao.createCommonQuery(WlUserFuncModel.class)
				.addCondition(Condition.eq(WlUserFuncModel.FieldNames.userId, userId))
				.addDynamicCondition(Condition.notIn(WlUserFuncModel.FieldNames.funcId, funcIds))
				.query());
		
		WlUserFuncModel example = new WlUserFuncModel();
		example.setUserId(userId);
		Set<String> funcIdsSet = new HashSet<String>();
		for (WlUserFuncModel userFunc : this.findByExample(example, null, null)) {
			funcIdsSet.add(userFunc.getFuncId());
		}
		
		for (String funcId : funcIds) {
			if (! funcIdsSet.contains(funcId)) {
				WlUserFuncModel userFunc = new WlUserFuncModel();
				userFunc.setUserId(userId);
				userFunc.setFuncId(funcId);
				this.save(userFunc);
			}
		}
	}


	public void saveUsersForFunc(String funcId, String[] checkedUserIds, String[] uncheckedUserIds) {
		this.removeAll(this.dao.createCommonQuery(WlUserFuncModel.class)
				.addCondition(Condition.eq(WlUserFuncModel.FieldNames.funcId, funcId))
				.addCondition(Condition.in(WlUserFuncModel.FieldNames.userId, uncheckedUserIds))
				.query());
		
		while (funcId != null && funcId.trim().length() > 0
				&& ! "0".equals(funcId) && this.dao.exists(WlFunctionModel.class, funcId)) {
			WlUserFuncModel example = new WlUserFuncModel();
			example.setFuncId(funcId);
			Set<String> userIdsSet = new HashSet<String>();
			for (WlUserFuncModel userFunc : this.findByExample(example, null, null)) {
				userIdsSet.add(userFunc.getUserId());
			}

			for (String userId : checkedUserIds) {
				if (! userIdsSet.contains(userId)) {
					WlUserFuncModel userFunc = new WlUserFuncModel();
					userFunc.setUserId(userId);
					userFunc.setFuncId(funcId);
					this.save(userFunc);
				}
			}
			
			funcId = this.dao.get(WlFunctionModel.class, funcId).getParentId();
		}
	}
	
}
