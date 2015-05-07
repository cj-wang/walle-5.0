package cn.walle.framework.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.MongoCommonQueryManager;
import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryField;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.util.DateUtils;
import cn.walle.framework.core.util.ParameterUtils;

/**
 * A bean to handle common queries at mongodb.
 * @author cj
 *
 */
@Service
public class MongoCommonQueryManagerImpl extends BaseManagerImpl implements MongoCommonQueryManager {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SelectCodeManager selectCodeManager;

	public QueryData query(QueryInfo queryInfo) {
		String queryType = queryInfo.getQueryType();
		List<QueryField> queryFields = queryInfo.getQueryFields();
		String orderBy = queryInfo.getOrderBy();
		PagingInfo pagingInfo = queryInfo.getPagingInfo();
		Map<String, String> fieldCodeTypes = queryInfo.getFieldCodeTypes();

		Query query = new Query();
		
		if (queryFields != null) {
			List<Criteria> criterias = new ArrayList<Criteria>();
			for (QueryField queryField : queryFields) {
				String parameterName = queryField.getFieldName();
				Object parameterValue = queryField.getFieldValue();
				if (! ParameterUtils.isParamValid(parameterName)
						|| ! ParameterUtils.isParamValid(parameterValue)) {
					continue;
				}
				if (parameterValue instanceof Date) {
					parameterValue = DateUtils.format((Date) parameterValue);
				}
				String operator = queryField.getOperator();
				if (! ParameterUtils.isParamValid(operator)) {
					operator = "=";
				}
				if (parameterName.matches("\\[\\w+(,\\w+)*\\]")) {
					parameterName = parameterName.substring(1, parameterName.length() - 1);
					String[] fieldNames = parameterName.split(",");
					List<Criteria> orCriterias = new ArrayList<Criteria>();
					for (String fieldName : fieldNames) {
						if (fieldCodeTypes != null && fieldCodeTypes.containsKey(fieldName)) {
							SelectCodeDefinition selectCodeDefinition = selectCodeManager.getSelectCodeDefinition(fieldCodeTypes.get(fieldName));
							List<QueryField> selectCodeQueryFields = new ArrayList<QueryField>();
							if (selectCodeDefinition.getQueryFields() != null) {
								selectCodeQueryFields.addAll(selectCodeDefinition.getQueryFields());
							}
							QueryField selectCodeQueryField = new QueryField();
							selectCodeQueryField.setFieldName(selectCodeDefinition.getLabelFieldName());
							selectCodeQueryField.setOperator(operator);
							selectCodeQueryField.setFieldValue(parameterValue);
							selectCodeQueryFields.add(selectCodeQueryField);
							
							QueryInfo selectCodeQueryInfo = new QueryInfo();
							selectCodeQueryInfo.setQueryType(selectCodeDefinition.getQueryType());
							selectCodeQueryInfo.setQueryFields(selectCodeQueryFields);
							List<?> selectCodeDataList = this.query(selectCodeQueryInfo).getDataList();
							if (selectCodeDataList.size() == 0) {
								continue;
							}
							Object[] selectCodeKeyValues = new Object[selectCodeDataList.size()];
							for (int i = 0; i < selectCodeKeyValues.length; i++) {
								selectCodeKeyValues[i] = new CustomBeanWrapper(selectCodeDataList.get(i)).getPropertyValue(
										selectCodeDefinition.getKeyFieldName());
							}
							orCriterias.add(this.createCriteria(fieldName, "in", selectCodeKeyValues));
						} else {
							orCriterias.add(this.createCriteria(fieldName, operator, parameterValue));
						}
					}
					query.addCriteria(new Criteria().orOperator(orCriterias.toArray(new Criteria[0])));
				} else {
					criterias.add(this.createCriteria(parameterName, operator, parameterValue));
				}
			}
			if (criterias.size() > 0) {
				query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));
			}
		}
		
		if (pagingInfo != null) {
			pagingInfo.setTotalRows((int) this.mongoTemplate.count(query, queryType));
			query.skip(pagingInfo.getCurrentRow());
			query.limit(pagingInfo.getPageSize());
		}
		
		if (orderBy != null) {
			String[] orders = orderBy.split(",");
			for (String order : orders) {
				order = order.trim();
				if (order.length() == 0) {
					continue;
				}
				if (order.toLowerCase().endsWith(" asc")) {
					query.with(new Sort(Direction.ASC, order.substring(0, order.length() - 4).trim()));
				} else if (order.toLowerCase().endsWith(" desc")) {
					query.with(new Sort(Direction.DESC, order.substring(0, order.length() - 4).trim()));
				} else {
					query.with(new Sort(Direction.ASC, order.trim()));
				}
			}
		}
		
		log.info("mongodb find \"" + queryType + "\", " + query);
		List<?> dataList = this.mongoTemplate.find(query, DynamicModelClass.class, queryType);
		
		for (Object object : dataList) {
			DynamicModelClass model = (DynamicModelClass) object;
			loadRefDocument(model);
		}
		
		QueryData queryData = new QueryData();
		queryData.setDataList(dataList);
		queryData.setPagingInfo(pagingInfo);
		
		if (fieldCodeTypes != null && fieldCodeTypes.size() > 0) {
			queryData.setSelectCodeValues(selectCodeManager.getSelectCodeValues(dataList, fieldCodeTypes));
		}
		
		return queryData;
	}


	private Criteria createCriteria(String parameterName, String operator, Object parameterValue) {
		Criteria criteria = new Criteria(parameterName);
		//TODO add more operators support
		if (operator.equals(">")) {
			criteria.gt(parameterValue);
		} else if (operator.equals(">=")) {
			criteria.gte(parameterValue);
		} else if (operator.equals("<")) {
			criteria.lt(parameterValue);
		} else if (operator.equals("<=")) {
			criteria.lte(parameterValue);
		} else if (operator.equals("in")) {
			if (parameterValue instanceof Object[]) {
				criteria.in((Object[]) parameterValue);
			} else if (parameterValue instanceof Collection<?>) {
				criteria.in((Collection<?>) parameterValue);
			} else {
				criteria.in((Object[]) ParameterUtils.splitValues(parameterValue.toString()));
			}
		} else if (operator.equals("notIn")) {
			if (parameterValue instanceof Object[]) {
				criteria.nin((Object[]) parameterValue);
			} else if (parameterValue instanceof Collection<?>) {
				criteria.nin((Collection<?>) parameterValue);
			} else {
				criteria.nin((Object[]) ParameterUtils.splitValues(parameterValue.toString()));
			}
		} else if (operator.equals("like") || operator.equals("ilike") || operator.equals("ilikeAnywhere")) {
			criteria.regex(".*" + parameterValue + ".*");
		} else if (operator.equals("<>")) {
			criteria.ne(parameterValue);
		} else {
			criteria.is(parameterValue);
		}
		return criteria;
	}
	
	private void loadRefDocument(DynamicModelClass model) {
		for (String field : model.validFields()) {
			if (field.indexOf("@") > 0) {
				Object id = model.get(field);
				if (id != null) {
					String docName = field.substring(0, field.indexOf("@"));
					String collection = field.substring(field.indexOf("@") + 1);
					DynamicModelClass subDoc = this.mongoTemplate.findById(id, DynamicModelClass.class, collection);
					if (subDoc != null) {
						loadRefDocument(subDoc);
						for (String subField : subDoc.validFields()) {
							model.put(docName + "." + subField, subDoc.get(subField));
						}
					}
				}
			}
		}
	}

}
