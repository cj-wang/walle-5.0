package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSysCodeModel;

public interface WlSysCodeManager extends BaseManager {

	List<WlSysCodeModel> saveAll(Collection<WlSysCodeModel> models);
	

	WlSysCodeModel get(String id);

	List<WlSysCodeModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlSysCodeModel> findByExample(WlSysCodeModel example, String orderBy, PagingInfo pagingInfo);

	WlSysCodeModel save(WlSysCodeModel model);

	void remove(WlSysCodeModel model);

	void removeAll(Collection<WlSysCodeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	String saveModel(WlSysCodeModel model);

	/**
	 * 根据流程Id删除系统代码中相关的流程
	 * @param processDefinitionId
	 */
	void removeByProcessDeploymentId(String processDefinitionId);
	
	/**
	 * 根据代码类型查询代码
	 * @param codeType
	 * @return
	 */
	List<WlSysCodeModel> findSysCodeByCodeType(String codeType);
	
	/**
	 * 根据代码类型,父代码值查询代码
	 * @param codeType
	 * @param parentCodeValue
	 * @return
	 */
	List<WlSysCodeModel> findSysCodeByCodeTypeAndParent(String codeType,String parentCodeValue);
	
	/**
	 * 根据代码类型及代码值获取父代码值
	 * @param codeType
	 * @param codeValue
	 * @return
	 */
	String getParentCodeValueByCodeTypeAndCodeValue(String codeType,String codeValue);
	
	/**
	 * 将代码类型为codetype代码名称为codeName代码值为codeValue的系统代码的代码值替换成newCodeValue
	 * @param codeType
	 * @param codeName
	 * @param codeValue
	 * @param newCodeValue
	 */
	void updateCodeValue(String codeType,String codeName,String codeValue,String newCodeValue);
	
	/**
	 *  将代码类型为codetype代码名称为codeName父代码值为parentCodeValue的系统代码的父代码值替换成newParentCodeValue
	 * @param codeType
	 * @param codeName
	 * @param parentCodeValue
	 * @param newParentCodeValue
	 */
	void updateParentCodeValue(String codeType,String codeName,String parentCodeValue,String newParentCodeValue);
	
	/**
	 * 为避免对一个sysCodeType插入重复的syscode，对获取系统代码集合进行判断，如果正确则调用saveAll方法保存，否则抛出ApplicationException
	 * @author mafubin
	 * @param models
	 * @return
	 */
	List<WlSysCodeModel> saveGrid(Collection<WlSysCodeModel> models);
}
