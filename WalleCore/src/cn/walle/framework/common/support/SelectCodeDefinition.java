package cn.walle.framework.common.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import cn.walle.framework.common.service.CommonQueryManager;
import cn.walle.framework.core.model.BaseObject;
import cn.walle.framework.core.util.ContextUtils;

public class SelectCodeDefinition extends BaseObject {

	public static final String DEFINITION_BEAN_ID_PREFIX = "selectCode.";
	
	public static final String SYSTEM_CODE_TEMPLATE_BEAN_ID = "selectCode.SystemCodeTemplate";

	private static final Map<String, Map<Locale, String>> localedLabelFieldNamesCache = new HashMap<String, Map<Locale, String>>();
	
	private String queryType;
	private List<QueryField> queryFields;
	private String orderBy;
	private String keyFieldName;
	private String labelFieldName;
	private String iconFieldName;
	private String parentFieldName;
	private Map<String, String> localedLabelFieldNames;
	private List<Map<String, String>> columns;
	private String i18nRoot;

	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public List<QueryField> getQueryFields() {
		return queryFields;
	}
	public void setQueryFields(List<QueryField> queryFields) {
		this.queryFields = queryFields;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getKeyFieldName() {
		return keyFieldName;
	}
	public void setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
	}
	public String getLabelFieldName() {
		Locale locale = LocaleContextHolder.getLocale();
		if (! localedLabelFieldNamesCache.containsKey(queryType + "." + labelFieldName)
				|| ! localedLabelFieldNamesCache.get(queryType + "." + labelFieldName).containsKey(locale)) {
			synchronized (localedLabelFieldNamesCache) {
				if (! localedLabelFieldNamesCache.containsKey(queryType + "." + labelFieldName)
						|| ! localedLabelFieldNamesCache.get(queryType + "." + labelFieldName).containsKey(locale)) {
					String resultLabelFieldName = labelFieldName;
					String localedLabelFieldName = resultLabelFieldName;
					String[] localeParts = locale.toString().split("_");
					List<String> tmpLocaleParts = new ArrayList<String>();
					List<String> dataItemFields = ContextUtils.getBeanOfType(CommonQueryManager.class).getQueryDataItemFields(queryType);
					for (String localePart : localeParts) {
						tmpLocaleParts.add(localePart);
						String tmpLocale = StringUtils.join(tmpLocaleParts, '_');
						if (localedLabelFieldNames != null && localedLabelFieldNames.containsKey(tmpLocale)) {
							resultLabelFieldName = localedLabelFieldNames.get(tmpLocale);
						} else {
							localedLabelFieldName = localedLabelFieldName + localePart.substring(0, 1).toUpperCase() + localePart.substring(1).toLowerCase();
							if (dataItemFields.contains(localedLabelFieldName)) {
								resultLabelFieldName = localedLabelFieldName;
							}
						}
					}
					if (localedLabelFieldNamesCache.containsKey(queryType + "." + labelFieldName)) {
						localedLabelFieldNamesCache.get(queryType + "." + labelFieldName).put(locale, resultLabelFieldName);
					} else {
						Map<Locale, String> cache = new HashMap<Locale, String>();
						cache.put(locale, resultLabelFieldName);
						localedLabelFieldNamesCache.put(queryType + "." + labelFieldName, cache);
					}
					return resultLabelFieldName;
				}
			}
		}
		return localedLabelFieldNamesCache.get(queryType + "." + labelFieldName).get(locale);
	}
	public String getIconFieldName() {
		return iconFieldName;
	}
	public void setIconFieldName(String iconFieldName) {
		this.iconFieldName = iconFieldName;
	}
	public String getParentFieldName() {
		return parentFieldName;
	}
	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}
	public void setLabelFieldName(String labelFieldName) {
		this.labelFieldName = labelFieldName;
	}
	public Map<String, String> getLocaledLabelFieldNames() {
		return localedLabelFieldNames;
	}
	public void setLocaledLabelFieldNames(Map<String, String> localedLabelFieldNames) {
		this.localedLabelFieldNames = localedLabelFieldNames;
	}
	public List<Map<String, String>> getColumns() {
		return columns;
	}
	public void setColumns(List<Map<String, String>> columns) {
		this.columns = columns;
	}
	public String getI18nRoot() {
		return i18nRoot;
	}
	public void setI18nRoot(String i18nRoot) {
		this.i18nRoot = i18nRoot;
	}

}
