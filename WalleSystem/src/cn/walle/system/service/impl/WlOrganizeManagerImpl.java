package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlOrganizeModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.query.FindOrgByNameOrManageQueryCondition;
import cn.walle.system.query.FindOrgByNameOrManageQueryItem;
import cn.walle.system.query.OrganizeQueryCondition;
import cn.walle.system.query.OrganizeQueryItem;
import cn.walle.system.service.WlOrganizeManager;
import cn.walle.system.service.WlSysLogManager;


@Service
public class WlOrganizeManagerImpl extends BaseManagerImpl implements WlOrganizeManager {

	@Autowired
	private WlSysLogManager sysLogManager;
	
	public WlOrganizeModel get(String id) {
		return this.dao.get(WlOrganizeModel.class, id);
	}

	public List<WlOrganizeModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlOrganizeModel.class, orderBy, pagingInfo);
	}

	public List<WlOrganizeModel> findByExample(WlOrganizeModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlOrganizeModel save(WlOrganizeModel model) {
		return this.dao.save(model);
	}

	public List<WlOrganizeModel> saveAll(Collection<WlOrganizeModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlOrganizeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlOrganizeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlOrganizeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlOrganizeModel.class, ids);
	}

	public void delByPk(String id){
		//判断该组织是否是总组织，若是则不能删除
		WlOrganizeModel root = this.dao.get(WlOrganizeModel.class, id);
		String parentOrganizeId = root.getParentOrganizeId();
		if("0".equals(parentOrganizeId)){
			throw new ApplicationException("该组织为根组织，不能进行删除操作！");
		}
		
		//判断该组织下是否存在下属组织，若存在则不能删除
		WlOrganizeModel example = new WlOrganizeModel();
		example.setParentOrganizeId(id);
		example.setState("U");
		List<WlOrganizeModel> listmodel = this.dao.findByExample(example, null, null);
		if(listmodel!=null){
			if(listmodel.size()>0){
				throw new ApplicationException("该组织下存在下属组织，不能进行删除操作！");
			}
		}
		
		//判断该组织是否存在用户，若存在则不能删除
		WlUserModel user = new WlUserModel();
		user.setOrganizeId(id);
		List<WlUserModel> usermodel = this.dao.findByExample(user, null, null);
		if(usermodel!=null){
			if(usermodel.size()>0){
				throw new ApplicationException("该组织下存在用户，不能进行删除操作！");
			}
		}
		
		//删除该组织
		//this.dao.removeByPk(WlOrganizeModel.class, id);
		WlOrganizeModel org = this.get(id);
		org.setState("F");
		this.save(org);
		sysLogManager.saveSysLog("org", "删除", "1", "对组织[" + org.getName() + "]进行删除", "删除组织[" + org.getName() + "]成功", null);
		
	}
	
	public WlOrganizeModel saveModel(WlOrganizeModel model) {
		model.setState("U");
		String organizeId = model.getOrganizeId();
		String name = model.getName();
		if(organizeId==null || "".equals(organizeId)){
			WlOrganizeModel example = new WlOrganizeModel();
			example.setName(name);
			example.setState("U");
			List<WlOrganizeModel> listmodel = this.dao.findByExample(example, null, null);
			if(listmodel!=null){
				if(listmodel.size()>0){
					throw new ApplicationException("组织"+name+"存在同名操作，不能保存！");
				}
			}
		}else{
			WlOrganizeModel example = new WlOrganizeModel();
			example.setName(name);
			example.setState("U");
			List<WlOrganizeModel> listmodel = this.dao.findByExample(example, null, null);
			if(listmodel!=null){
				if(listmodel.size()>1){
					throw new ApplicationException("组织"+name+"存在同名操作，不能保存！");
				}else if(listmodel.size()==1){
					//判断是否与其他组织名称一致
					String orgid = ((WlOrganizeModel)listmodel.get(0)).getOrganizeId();
					if(! orgid.equals(organizeId)){
						throw new ApplicationException("组织"+name+"存在同名操作，不能保存！");
					}
				}
			}
		}
		//保存组织
		model = this.dao.save(model);
		sysLogManager.saveSysLog("org", "增加", "1", "新增组织[" + model.getName() + "]", "增加" + model.getName() + "成功", null);
		return model;
	}

	@Override
	public WlOrganizeModel getOrganize(WlOrganizeModel model) {
		List list = this.findByExample(model, null, null);
		if(list.size()==0){
			return null;
		}else{
			return (WlOrganizeModel) list.get(0);
		}
	}

	public List<OrganizeQueryItem> getAllOrgByEx() {
		List<OrganizeQueryItem> list = null;
		OrganizeQueryCondition con = new OrganizeQueryCondition();
		
		return this.dao.query(con, OrganizeQueryItem.class);
	}

}
