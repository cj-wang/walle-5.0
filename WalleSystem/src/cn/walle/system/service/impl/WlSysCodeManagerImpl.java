package cn.walle.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSysCodeModel;
import cn.walle.system.service.WlSysCodeManager;

@Service
public class WlSysCodeManagerImpl extends BaseManagerImpl implements WlSysCodeManager {

	public List<WlSysCodeModel> saveAll(Collection<WlSysCodeModel> models) {
		return this.dao.saveAll(models);
	}
	public WlSysCodeModel get(String id) {
		return this.dao.get(WlSysCodeModel.class, id);
	}

	public List<WlSysCodeModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlSysCodeModel.class, orderBy, pagingInfo);
	}

	public List<WlSysCodeModel> findByExample(WlSysCodeModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlSysCodeModel save(WlSysCodeModel model) {
		return this.dao.save(model);
	}

	public void remove(WlSysCodeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlSysCodeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlSysCodeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlSysCodeModel.class, ids);
	}
	
	public String saveModel(WlSysCodeModel model){
		if(checkModel(model)){
			this.dao.save(model);
		}
		return "保存成功！";
	}

	/**
	 * 根据流程Id删除系统代码中相关的流程
	 * @param processDefinitionId
	 */
	public void removeByProcessDeploymentId(String deploymentId){
		if(deploymentId == null || "".equals(deploymentId)){
			throw new ApplicationException("流程定义的id为空！");
		}
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId("jbpm_processes");
		example.setCodeValue(deploymentId);
		List<WlSysCodeModel> list = this.findByExample(example, null, null);
		if(list.size()==0)return;
		this.removeAll(list);
	}
	
	/**
	 * 根据代码类型查询代码
	 * @param codeType
	 * @return
	 */
	public List<WlSysCodeModel> findSysCodeByCodeType(String codeType){
		if(codeType == null || "".equals(codeType)){
			throw new ApplicationException("查询系统代码失败，代码类型为空！");
		}
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(codeType);
		return this.findByExample(example, null, null);
	}
	
	/**
	 * 根据代码类型,父代码值查询代码
	 * @param codeType
	 * @param parentCodeValue
	 * @return
	 */
	public List<WlSysCodeModel> findSysCodeByCodeTypeAndParent(String codeType,String parentCodeValue){
		if(codeType == null || "".equals(codeType)){
			throw new ApplicationException("查询系统代码失败，代码类型为空！");
		}
		if(parentCodeValue == null || "".equals(parentCodeValue)){
			throw new ApplicationException("查询系统代码失败，父代码值为空！");
		}
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(codeType);
		example.setParentCodeValue(parentCodeValue);
		return this.findByExample(example, null, null);
	}
	
	/**
	 * 根据代码类型及代码值获取父代码值
	 * @param codeType
	 * @param codeValue
	 * @return
	 */
	public String getParentCodeValueByCodeTypeAndCodeValue(String codeType,String codeValue){
		if(codeType ==null || "".equals(codeType)){
			throw new ApplicationException("查询系统代码失败，代码类型为空！");
		}
		if(codeValue == null || "".equals(codeValue)){
			throw new ApplicationException("查询系统代码失败，代码值为空！");
		}
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(codeType);
		example.setCodeValue(codeValue);
		List<WlSysCodeModel> list = this.findByExample(example, null, null);
		if(list.size()>0){
			return list.get(0).getParentCodeValue();
		}
		return null;
	}
	
	/**
	 * 将代码类型为codetype代码名称为codeName代码值为codeValue的系统代码的代码值替换成newCodeValue
	 * @param codeType
	 * @param codeName
	 * @param codeValue
	 * @param newCodeValue
	 */
	public void updateCodeValue(String codeType,String codeName,String codeValue,String newCodeValue){
		if(codeType == null || "".equals(codeType))throw new ApplicationException("修改系统代码失败，代码类型为空！");
		if((codeName == null || "".equals(codeName)|| "null".equals(codeName)) && (codeValue == null || "".equals(codeValue)|| "null".equals(codeValue))){
			throw new ApplicationException("修改系统代码失败，代码名称及代码原代码值不能全为空！");
		}
		if(newCodeValue == null || "".equals(newCodeValue))throw new ApplicationException("修改系统代码失败，新代码值为空！");
		
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(codeType);
		if(codeName != null && !"".equals(codeName) && !"null".equals(codeName)){
			example.setCodeName(codeName);
		}
		if(codeValue != null && !"".equals(codeValue) && !"null".equals(codeValue)){
			example.setCodeValue(codeValue);
		}
		List<WlSysCodeModel> list = this.findByExample(example, null, null);
		if(list.size() == 0)throw new ApplicationException("无此记录");
		WlSysCodeModel model = list.get(0);
		model.setCodeValue(newCodeValue);
		this.save(model);
	}
	/**
	 *  将代码类型为codetype代码名称为codeName父代码值为parentCodeValue的系统代码的父代码值替换成newParentCodeValue
	 * @param codeType
	 * @param codeName
	 * @param parentCodeValue
	 * @param newParentCodeValue
	 */
	public void updateParentCodeValue(String codeType,String codeName,String parentCodeValue,String newParentCodeValue){
		if(codeType == null || "".equals(codeType))
			throw new ApplicationException("修改系统父代码失败，代码类型为空！");
		if(codeName == null || "".equals(codeName)|| "null".equals(codeName)){
			throw new ApplicationException("修改系统父代码失败，代码名称为空！");
		}
		if(newParentCodeValue == null || "".equals(newParentCodeValue))
			throw new ApplicationException("修改系统父代码失败，新父代码值为空！");
		
		WlSysCodeModel example = new WlSysCodeModel();
		example.setCodeTypeId(codeType);
		example.setCodeName(codeName);
		if(parentCodeValue != null && !"".equals(parentCodeValue) && !"null".equals(parentCodeValue)){
			example.setParentCodeValue(parentCodeValue);
		}
		List<WlSysCodeModel> list = this.findByExample(example, null, null);
		if(list.size() == 0)
			throw new ApplicationException("无此记录");
		WlSysCodeModel model = list.get(0);
		model.setParentCodeValue(newParentCodeValue);
		this.save(model);
	}
	/**
	 * 为避免对一个sysCodeType插入重复的syscode，对获取系统代码集合进行判断，如果正确则调用saveAll方法保存，否则抛出ApplicationException
	 * @author mafubin
	 * @param models
	 * @return
	 */
	@Override
	public List<WlSysCodeModel> saveGrid(Collection<WlSysCodeModel> models) {
		if(models==null || models.size() ==0){
			throw new ApplicationException("不能保存空的数据！");
		}
		//判断所插入的数据中是否存在相同的数据
		Iterator<WlSysCodeModel> iterClient = models.iterator();
		ArrayList<String>codeNames = new ArrayList<String>();
		ArrayList<String>codeValues = new ArrayList<String>();
		while(iterClient.hasNext()){
			WlSysCodeModel tempModel = iterClient.next();
			if(codeNames.size()!=0 && codeNames.contains(tempModel.getCodeName())){
				throw new ApplicationException("即将保存的数据中存在相同的系统代码名称："+tempModel.getCodeName());
			}else{
				codeNames.add(tempModel.getCodeName());
			}
			if(codeNames.size()!=0 && codeValues.contains(tempModel.getCodeValue())){
				throw new ApplicationException("即将保存的数据中存在相同的系统代码名称："+tempModel.getCodeValue());
			}else{
				codeValues.add(tempModel.getCodeValue());
			}
		}
		
		//判断所插入的数据是否已经存在
		Iterator<WlSysCodeModel> iterClient2 = models.iterator();
		boolean saveFlag = true;
		while(iterClient2.hasNext()){
			WlSysCodeModel tempModel = iterClient2.next();
			saveFlag = saveFlag && checkModel(tempModel);
		}
		
		if(saveFlag){
			return saveAll(models);
		}
		return null;
	}
	
	/**
	 * 检查model，是否符合要求
	 * @author mafubin
	 * @param model
	 * @return
	 */
	private boolean checkModel(WlSysCodeModel model){
		boolean res = false;
		String codeId = model.getCodeId();
		String codeName = model.getCodeName();
		String codeValue = model.getCodeValue();
		String codeTypeId = model.getCodeTypeId();
		if(codeId==null || "".equals(codeId)){
			WlSysCodeModel nameexample = new WlSysCodeModel();
			nameexample.setCodeName(codeName);
			nameexample.setCodeTypeId(codeTypeId);
			List<WlSysCodeModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>0){
					res = false;
					throw new ApplicationException("系统代码名称 '"+codeName+"' 存在同名操作，不能保存！");
				}else{
					res = true;
				}
			}else{
				res = true;
			}
			
			WlSysCodeModel codeexample = new WlSysCodeModel();
			codeexample.setCodeValue(codeValue);
			codeexample.setCodeTypeId(codeTypeId);
			List<WlSysCodeModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>0){
					res = false;
					throw new ApplicationException("系统代码值  '"+codeValue+"'  存在同名操作，不能保存！");
				}else{
					res = true;
				}
			}else{
				res = true;
			}
		}else{
			WlSysCodeModel nameexample = new WlSysCodeModel();
			nameexample.setCodeName(codeName);
			nameexample.setCodeTypeId(codeTypeId);
			List<WlSysCodeModel> namemodel = this.dao.findByExample(nameexample, null, null);
			if(namemodel!=null){
				if(namemodel.size()>1){
					res = false;
					throw new ApplicationException("系统代码名称  '"+codeName+"'  存在同名操作，不能保存！");
				}else if(namemodel.size()==1){
					//判断是否与其他系统代码名称一致
					String codeid = ((WlSysCodeModel)namemodel.get(0)).getCodeId();
					if(codeid.equals(codeId)){
						res = true;
					}else{
						res = false;
						throw new ApplicationException("系统代码名称  '"+codeName+"'  存在同名操作，不能保存！");
					}
				}else{
					res = true;
				}
			}else{
				res = true;
			}
				
			WlSysCodeModel codeexample = new WlSysCodeModel();
			codeexample.setCodeValue(codeValue);
			codeexample.setCodeTypeId(codeTypeId);
			List<WlSysCodeModel> codemodel = this.dao.findByExample(codeexample, null, null);
			if(codemodel!=null){
				if(codemodel.size()>1){
					res = false;
					throw new ApplicationException("系统代码值  '"+codeValue+"'  存在同名操作，不能保存！");
				}else if(codemodel.size()==1){
					//判断是否与其他系统代码类型名称一致
					String codeid = ((WlSysCodeModel)codemodel.get(0)).getCodeId();
					if(codeid.equals(codeId)){
						res = true;
					}else{
						res = false;
						throw new ApplicationException("系统代码值   '"+codeValue+"'  存在同名操作，不能保存！");
					}
				}else{
					res = true;
				}
			}else{
				res = true;
			}
		}
		return res;
	}
}
