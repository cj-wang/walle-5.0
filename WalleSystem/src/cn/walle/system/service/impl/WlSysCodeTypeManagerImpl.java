package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSysCodeModel;
import cn.walle.system.model.WlSysCodeTypeModel;
import cn.walle.system.service.WlSysCodeTypeManager;

@Service
public class WlSysCodeTypeManagerImpl extends BaseManagerImpl implements WlSysCodeTypeManager {

	public List<WlSysCodeTypeModel> saveAll(Collection<WlSysCodeTypeModel> models) {
		return this.dao.saveAll(models);
	}

	public WlSysCodeTypeModel get(String id) {
		return this.dao.get(WlSysCodeTypeModel.class, id);
	}

	public List<WlSysCodeTypeModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlSysCodeTypeModel.class, orderBy, pagingInfo);
	}

	public List<WlSysCodeTypeModel> findByExample(WlSysCodeTypeModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlSysCodeTypeModel save(WlSysCodeTypeModel model) {
		return this.dao.save(model);
	}

	public void remove(WlSysCodeTypeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlSysCodeTypeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlSysCodeTypeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlSysCodeTypeModel.class, ids);
	}

	public String saveModel(WlSysCodeTypeModel model){
		if("1".equals(model.getSystem())){
			model.setSystem("system");
		}
		boolean res = false;
		String codeTypeId = model.getCodeTypeId();
		String typeName = model.getCodeTypeName();
		String typeCode = model.getTypeCode();
		if(codeTypeId==null || "".equals(codeTypeId)){
			WlSysCodeTypeModel nameexample = new WlSysCodeTypeModel();
			nameexample.setCodeTypeName(typeName);
			List<WlSysCodeTypeModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>0){
					res = false;
					throw new ApplicationException("系统代码类型名称"+typeName+"已经存在,不能保存!");
				}else{
					res = true;
				}
			}else{
				res = true;
			}
			
			WlSysCodeTypeModel codeexample = new WlSysCodeTypeModel();
			codeexample.setTypeCode(typeCode);
			List<WlSysCodeTypeModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>0){
					res = false;
					throw new ApplicationException("系统代码类型值"+typeCode+"已经存在，不能保存！");
				}else{
					res = true;
				}
			}else{
				res = true;
			}
		}else{
			WlSysCodeTypeModel nameexample = new WlSysCodeTypeModel();
			nameexample.setCodeTypeName(typeName);
			List<WlSysCodeTypeModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>1){
					res = false;
					throw new ApplicationException("系统代码类型名称"+typeName+"已经存在,不能保存!");
				}else if(namemodel.size()==1){
					//判断是否与其他系统代码名称一致
					String codeid = ((WlSysCodeTypeModel)namemodel.get(0)).getCodeTypeId();
					if(codeid.equals(codeTypeId)){
						res = true;
					}else{
						res = false;
						throw new ApplicationException("系统代码类型名称"+typeName+"已经存在,不能保存!");
					}
				}else{
					res = true;
				}
			}else{
				res = true;
			}
				
			WlSysCodeTypeModel codeexample = new WlSysCodeTypeModel();
			codeexample.setTypeCode(typeCode);
			List<WlSysCodeTypeModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>1){
					res = false;
					throw new ApplicationException("系统代码类型值"+typeCode+"已经存在,不能保存!");
				}else if(codemodel.size()==1){
					//判断是否与其他系统代码类型名称一致
					String codeid = ((WlSysCodeTypeModel)codemodel.get(0)).getCodeTypeId();
					if(codeid.equals(codeTypeId)){
						res = true;
					}else{
						res = false;
						throw new ApplicationException("系统代码类型值"+typeCode+"已经存在,不能保存!");
					}
				}else{
					res = true;
				}
			}else{
				res = true;
			}
		}
		//保存系统代码类型
		if(res){
			this.dao.save(model);
		}
		return "保存成功！";
	}
	
	public String delByPk(String id){
		boolean res = false;
		
		WlSysCodeTypeModel model = this.dao.get(WlSysCodeTypeModel.class, id);
		String typeCode = model.getTypeCode();
		//判断该系统代码类型是否存在系统代码，若存在则不能删除
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(typeCode);
		List<WlSysCodeModel> codeModel = this.dao.findByExample(example, null, null);
		if(codeModel!=null){
			if(codeModel.size()>0){
				res = false;
				throw new ApplicationException("该系统代码类型存在系统代码，不能进行删除操作！");
			}else{
				res = true;
			}
		}else{
			res = true;
		}
		
		//删除该系统代码
		if(res){
			this.dao.removeByPk(WlSysCodeTypeModel.class, id);
		}
		
		return "删除成功！";
	}
}
