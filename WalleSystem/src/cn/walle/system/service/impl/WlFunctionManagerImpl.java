package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.model.WlRoleFuncModel;
import cn.walle.system.query.GetFirstFunctionInfoQueryCondition;
import cn.walle.system.query.GetFirstFunctionInfoQueryItem;
import cn.walle.system.service.WlFunctionManager;
import cn.walle.system.service.WlSysLogManager;

@Service
public class WlFunctionManagerImpl extends BaseManagerImpl implements WlFunctionManager {
	@Autowired
	private WlSysLogManager sysLogManager;

	public List<WlFunctionModel> saveAll(Collection<WlFunctionModel> models) {
		return this.dao.saveAll(models);
	}
	
	public WlFunctionModel get(String id) {
		return this.dao.get(WlFunctionModel.class, id);
	}

	public List<WlFunctionModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlFunctionModel.class, orderBy, pagingInfo);
	}

	public List<WlFunctionModel> findByExample(WlFunctionModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlFunctionModel save(WlFunctionModel model) {
		return this.dao.save(model);
	}

	public void remove(WlFunctionModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlFunctionModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlFunctionModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlFunctionModel.class, ids);
	}
	public void saveModel(WlFunctionModel model){
		String funcId = model.getFuncId();
		String name = model.getName();
		String funcCode = model.getFuncCode();
		if(funcId==null || "".equals(funcId)){
			WlFunctionModel nameexample = new WlFunctionModel();
			nameexample.setName(name);
			List<WlFunctionModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>0){
					throw new ApplicationException("功能模块名称"+name+"存在同名操作，不能保存！");
				}
			}
			
			WlFunctionModel codeexample = new WlFunctionModel();
			codeexample.setFuncCode(funcCode);
			List<WlFunctionModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>0){
					throw new ApplicationException("功能代码编号"+funcCode+"存在同名操作，不能保存！");
				}
			}
		}else{
			WlFunctionModel nameexample = new WlFunctionModel();
			nameexample.setName(name);
			List<WlFunctionModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>1){
					throw new ApplicationException("功能模块名称"+name+"存在同名操作，不能保存！");
				}else if(namemodel.size()==1){
					String funcid = ((WlFunctionModel)namemodel.get(0)).getFuncId();
					if(! funcid.equals(funcId)){
						throw new ApplicationException("功能模块名称"+name+"存在同名操作，不能保存！");
					}
				}
			}
				
			WlFunctionModel codeexample = new WlFunctionModel();
			codeexample.setFuncCode(funcCode);
			List<WlFunctionModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>1){
					throw new ApplicationException("功能代码编号"+funcCode+"存在同名操作，不能保存！");
				}else if(codemodel.size()==1){
					String funcid = ((WlFunctionModel)codemodel.get(0)).getFuncId();
					if(! funcid.equals(funcId)){
						throw new ApplicationException("功能代码编号"+funcCode+"存在同名操作，不能保存！");
					}
				}
			}
		}

		if("".equals(model.getFuncImg()) || model.getFuncImg() == null){
			model.setFuncImg("images/menu_img/fenpei2.gif");
		}
		this.dao.save(model);
		sysLogManager.saveSysLog("fun", "增加", "1",  "增加 [" + model.getName() + "]功能模块成功","增加成功", null);
	}
	/**
	 * 系统功能模块删除
	 */
	public void delByPk(String id){
		
		WlFunctionModel model = this.dao.get(WlFunctionModel.class, id);
		String funcId = model.getFuncId();
		//模块存在下属模块不能删除
		WlFunctionModel func = new WlFunctionModel();
		func.setParentId(funcId);
		List<WlFunctionModel> funcmodel = this.dao.findByExample(func, null, null);
		if(funcmodel!=null){
			if(funcmodel.size()>0){
				throw new ApplicationException("该功能模块存在下属模块，不能进行删除操作！");
			}
		}
		
		//模块已分配角色不能删除
		WlRoleFuncModel example = new WlRoleFuncModel();
		example.setFuncId(funcId);
		List<WlRoleFuncModel> codeModel = this.dao.findByExample(example, null, null);
		if(codeModel!=null){
			if(codeModel.size()>0){
				throw new ApplicationException("该功能模块已赋权给了角色，不能进行删除操作！");
			}
		}
		
		//删除该系统代码
		this.dao.removeByPk(WlFunctionModel.class, id);
		sysLogManager.saveSysLog("fun", "删除", "1", "对功能模块[" + model.getName() + "]进行删除", "删除成功",  null);
		
	}
	
	public String getFunctionNameByUri(String uri) {
		List<WlFunctionModel> functions = dao.createCommonQuery(WlFunctionModel.class)
		.addCondition(Condition.eq("viewname", uri))
		.query();
		if (functions.size() == 0) {
			return "";
		}
		WlFunctionModel function = functions.get(0);
		StringBuilder sb = new StringBuilder(function.getName());
		while (function.getParentId() != null) {
			try {
				function = dao.get(WlFunctionModel.class, function.getParentId());
				sb.insert(0, " → ");
				sb.insert(0, function.getName());
			} catch (ObjectRetrievalFailureException orfex) {
				break;
			}
		}
		return sb.toString();
	}

	@Override
	public void setSysHomePage(String id) {
		// TODO Auto-generated method stub
		
	}

	public GetFirstFunctionInfoQueryItem getFirstSubFunctionInfo(String funcId) {
		GetFirstFunctionInfoQueryCondition con = new GetFirstFunctionInfoQueryCondition();
		con.setFuncid(funcId);
		List list = this.dao.query(con, GetFirstFunctionInfoQueryItem.class);
		if(list != null && list.size() > 0){
			return (GetFirstFunctionInfoQueryItem) list.get(0);
		}
		return null;
	}
	
}
