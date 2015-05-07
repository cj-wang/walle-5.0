package cn.walle.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.model.WlRoleFuncModel;
import cn.walle.system.service.WlRoleFuncManager;
import cn.walle.system.service.WlSysLogManager;

@Service
public class WlRoleFuncManagerImpl extends BaseManagerImpl implements WlRoleFuncManager {

	@Autowired
	private WlSysLogManager sysLogManager;
	
	public WlRoleFuncModel get(String id) {
		return this.dao.get(WlRoleFuncModel.class, id);
	}

	public List<WlRoleFuncModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlRoleFuncModel.class, orderBy, pagingInfo);
	}

	public List<WlRoleFuncModel> findByExample(WlRoleFuncModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}
	
	/**
	 * 如果功能授予了此角色，则抛出异常，前台页面来进行判断
	 * 否则，则对角色进行赋权操作
	 */
	public WlRoleFuncModel save(WlRoleFuncModel model) {
		if(this.dao.getRowCountByExample(model)>0){
			throw new ApplicationException("该功能已授权!");
		}
		return this.dao.save(model);
	}

	public List<WlRoleFuncModel> saveAll(Collection<WlRoleFuncModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlRoleFuncModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlRoleFuncModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlRoleFuncModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlRoleFuncModel.class, ids);
	}
	public ArrayList getRoleFunc(String roleId)
	{
		WlRoleFuncModel roleModel=new WlRoleFuncModel();
		roleModel.setRoleId(roleId);
		List<WlRoleFuncModel> roleFuncModels=this.findByExample(roleModel, null, null);
		if(roleFuncModels.size()>0)
		{
			//java.awt.List funcIds=new java.awt.List();
			ArrayList funcIds=new ArrayList();
			funcIds.add( 0,"1");
			for(int i=0;i<roleFuncModels.size();i++)
			{
				funcIds.add( i+1,roleFuncModels.get(i).getFuncId());
			}
			return funcIds;
		}
		return null;
	}
	public void saveFunc(String roleId,List<String> funcIds)
	{
		WlRoleFuncModel roleModel=new WlRoleFuncModel();
		roleModel.setRoleId(roleId);
		Collection<WlRoleFuncModel> models=this.findByExample(roleModel, null, null);
		this.removeAll(models);
		for (String funcId : funcIds) {
			if(funcId==null || "".equals(funcId.trim())||"1".equals(funcId)){
				continue;
			}
			WlRoleFuncModel rolemodel=new WlRoleFuncModel();
			rolemodel.setRoleId(roleId);
			rolemodel.setFuncId(funcId);
			this.dao.save(rolemodel);
			sysLogManager.saveSysLog("role", "角色赋权", "1", "赋权成功", "角色赋权成功", null);
		}
	}


	public void saveFuncsForRole(String roleId, String[] funcIds) {
		this.removeAll(this.dao.createCommonQuery(WlRoleFuncModel.class)
				.addCondition(Condition.eq(WlRoleFuncModel.FieldNames.roleId, roleId))
				.addDynamicCondition(Condition.notIn(WlRoleFuncModel.FieldNames.funcId, funcIds))
				.query());
		
		WlRoleFuncModel example = new WlRoleFuncModel();
		example.setRoleId(roleId);
		Set<String> funcIdsSet = new HashSet<String>();
		for (WlRoleFuncModel rolefunc : this.findByExample(example, null, null)) {
			funcIdsSet.add(rolefunc.getFuncId());
		}
		
		for (String funcId : funcIds) {
			if (! funcIdsSet.contains(funcId)) {
				WlRoleFuncModel rolefunc = new WlRoleFuncModel();
				rolefunc.setRoleId(roleId);
				rolefunc.setFuncId(funcId);
				this.save(rolefunc);
			}
		}
	}

	public void saveRolesForFunc(String funcId, String[] roleIds) {
		this.removeAll(this.dao.createCommonQuery(WlRoleFuncModel.class)
				.addCondition(Condition.eq(WlRoleFuncModel.FieldNames.funcId, funcId))
				.addDynamicCondition(Condition.notIn(WlRoleFuncModel.FieldNames.roleId, roleIds))
				.query());
		
		while (funcId != null && funcId.trim().length() > 0
				&& ! "0".equals(funcId) && this.dao.exists(WlFunctionModel.class, funcId)) {
			WlRoleFuncModel example = new WlRoleFuncModel();
			example.setFuncId(funcId);
			Set<String> roleIdsSet = new HashSet<String>();
			for (WlRoleFuncModel rolefunc : this.findByExample(example, null, null)) {
				roleIdsSet.add(rolefunc.getRoleId());
			}
			
			for (String roleId : roleIds) {
				if (! roleIdsSet.contains(roleId)) {
					WlRoleFuncModel rolefunc = new WlRoleFuncModel();
					rolefunc.setRoleId(roleId);
					rolefunc.setFuncId(funcId);
					this.save(rolefunc);
				}
			}
			
			funcId = this.dao.get(WlFunctionModel.class, funcId).getParentId();
		}
	}
}
