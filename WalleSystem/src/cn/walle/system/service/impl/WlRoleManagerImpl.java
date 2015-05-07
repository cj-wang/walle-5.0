package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlRoleFuncModel;
import cn.walle.system.model.WlRoleModel;
import cn.walle.system.model.WlUserRoleModel;
import cn.walle.system.query.UserMenuQueryCondition;
import cn.walle.system.query.UserMenuQueryItem;
import cn.walle.system.service.WlRoleManager;
import cn.walle.system.service.WlSysLogManager;

@Service
public class WlRoleManagerImpl extends BaseManagerImpl implements WlRoleManager {

	@Autowired
	private WlSysLogManager sysLogManager;
	
	public WlRoleModel get(String id) {
		return this.dao.get(WlRoleModel.class, id);
	}

	public List<WlRoleModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlRoleModel.class, orderBy, pagingInfo);
	}

	public List<WlRoleModel> findByExample(WlRoleModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlRoleModel save(WlRoleModel model) {
		return this.dao.save(model);
	}

	public List<WlRoleModel> saveAll(Collection<WlRoleModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlRoleModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlRoleModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlRoleModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlRoleModel.class, ids);
	}
/**
 * 删除未赋权的角色
 * @param id
 * @author Administrator
 */
	public void delByPk(String id){
		//判断该角色没有赋给任何用户
		WlUserRoleModel role = new WlUserRoleModel();
		role.setRoleId(id);
		List<WlUserRoleModel> rolemodel = this.dao.findByExample(role, null, null);
		if(rolemodel!=null){
			if(rolemodel.size()>0){
				throw new ApplicationException("已有用户赋予了该角色，不能进行删除操作！");
			}
		}
		
		//判断该角色没有赋予任何权限
		WlRoleFuncModel func = new WlRoleFuncModel();
		func.setRoleId(id);
		List<WlRoleFuncModel> funcmodel = this.dao.findByExample(func, null, null);
		if(funcmodel!=null){
			if(funcmodel.size()>0){
				throw new ApplicationException("该角色已赋予了权限，不能进行删除操作！");
			}
		}
		
		this.dao.removeByPk(WlRoleModel.class, id);
		sysLogManager.saveSysLog("role", "删除角色", "1", "删除成功", " 删除角色成功", null);
	}
	/**
	 * 角色信息的保存
	 * @param model
	 * @author Administrator
	 */
	public void saveModel(WlRoleModel model){
		String roleId = model.getRoleId();
		String name = model.getName();
		String code = model.getCode();
		if(roleId==null || "".equals(roleId)){
			WlRoleModel examplename = new WlRoleModel();
			examplename.setName(name);
			List<WlRoleModel> namemodel = this.dao.findByExample(examplename, null, null);
			if(namemodel!=null){
				if(namemodel.size()>0){
					throw new ApplicationException("角色名称已存在，不能完成保存！");
				}
			}
			
			WlRoleModel examplecode = new WlRoleModel();
			examplecode.setCode(code);
			List<WlRoleModel> codemodel = this.dao.findByExample(examplecode, null, null);
			if(codemodel!=null){
				if(codemodel.size()>0){
					throw new ApplicationException("角色代码已存在，不能完成保存！");
				}
			}
			sysLogManager.saveSysLog("role", "添加角色", "1", "添加成功", " 添加"+model.getName()+"角色成功", null);
		}else{
			WlRoleModel examplename = new WlRoleModel();
			examplename.setName(name);
			List<WlRoleModel> namemodel = this.dao.findByExample(examplename, null, null);
			if(namemodel!=null){
				if(namemodel.size()>1){
					throw new ApplicationException("角色名称已存在，不能完成保存！");
				}else if(namemodel.size()==1){
					//判断是否与其他角色名称一致
					String roleidtemp = ((WlRoleModel)namemodel.get(0)).getRoleId();
					if(! roleidtemp.equals(roleId)){
						throw new ApplicationException("角色名称已存在，不能完成保存！");
					}
				}
			}
			
			WlRoleModel examplecode = new WlRoleModel();
			examplecode.setCode(code);
			List<WlRoleModel> codemodel = this.dao.findByExample(examplecode, null, null);
			if(codemodel!=null){
				if(codemodel.size()>1){
					throw new ApplicationException("角色代码已存在，不能完成保存！");
				}else if(codemodel.size()==1){
					//判断是否与其他角色的代码一致
					String roleidtemp = ((WlRoleModel)codemodel.get(0)).getRoleId();
					if(! roleidtemp.equals(roleId)){
						throw new ApplicationException("角色代码已存在，不能完成保存！");
					}
				}
			}
			sysLogManager.saveSysLog("role", "编辑角色", "1", "编辑成功", " 编辑"+model.getName()+"角色成功", null);
		}
		//保存用户
		this.save(model);
	}
	
	/**
	 * 根据用户Id查询用户所属角色
	 * @param userId
	 * @return
	 */
	public List<UserMenuQueryItem> getRolesByUserId(String userId){
		if(userId == null || "".equals(userId)){
			throw new ApplicationException("用户Id为空");
		}
		UserMenuQueryCondition condition = new UserMenuQueryCondition();
		condition.setUserId(userId);
		return this.dao.query(condition, UserMenuQueryItem.class);
	}

}
