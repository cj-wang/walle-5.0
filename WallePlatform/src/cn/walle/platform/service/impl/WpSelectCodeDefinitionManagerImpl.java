package cn.walle.platform.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.util.JSONDataUtils;
import cn.walle.framework.core.web.SelectCodeDefinitionsJsInitializeContextListener;
import cn.walle.platform.model.WpSelectCodeDefinitionModel;
import cn.walle.platform.service.WpSelectCodeDefinitionManager;

@Service
public class WpSelectCodeDefinitionManagerImpl extends BaseManagerImpl implements WpSelectCodeDefinitionManager {

	private Map<String, SelectCodeDefinition> selectCodeCache;

	private void initCache() {
		Map<String, SelectCodeDefinition> selectCodeCache = new HashMap<String, SelectCodeDefinition>();
		List<WpSelectCodeDefinitionModel> selectCodes = this.dao.getAll(WpSelectCodeDefinitionModel.class);
		for (WpSelectCodeDefinitionModel selectCode : selectCodes) {
			if (selectCode.getDefinition() == null || selectCode.getDefinition().trim().length() == 0) {
				continue;
			}
			try {
				selectCodeCache.put(selectCode.getCodeType(),
						(SelectCodeDefinition) JSONDataUtils.parseJSONObject(SelectCodeDefinition.class, selectCode.getDefinition()));
			} catch (Exception ex) {
				throw new SystemException("CodeType " + selectCode.getCodeType() + " definition parse error", ex);
			}
		}
		this.selectCodeCache = selectCodeCache;
	}

	public List<WpSelectCodeDefinitionModel> saveTreeData(Collection<WpSelectCodeDefinitionModel> models) {
		List<WpSelectCodeDefinitionModel> result = this.dao.saveTreeData(models, "uuid", "parentUuid");
		this.initCache();
		//refresh js files
		SelectCodeDefinitionsJsInitializeContextListener.generateFiles();
		return result;
	}
	
	public void refreshServerCache() {
		SelectCodeDefinitionsJsInitializeContextListener.generateFiles();
	}

	@Override
	public SelectCodeDefinition getSelectCodeDefinition(String codeType) {
		if (this.selectCodeCache == null) {
			synchronized (WpSelectCodeDefinitionManagerImpl.class) {
				if (this.selectCodeCache == null) {
					this.initCache();
				}
			}
		}
		return this.selectCodeCache.get(codeType);
	}

	@Override
	public List<String> getAllCodeTypes() {
		List<WpSelectCodeDefinitionModel> selectCodes = this.dao.createCommonQuery(WpSelectCodeDefinitionModel.class)
		.addCondition(Condition.isNotNull(WpSelectCodeDefinitionModel.FieldNames.definition))
		.query();
		List<String> codeTypes = new ArrayList<String>();
		for (WpSelectCodeDefinitionModel selectCode : selectCodes) {
			codeTypes.add(selectCode.getCodeType());
		}
		return codeTypes;
	}

}
