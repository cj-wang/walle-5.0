package cn.walle.framework.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.MongoCommonSaveManager;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.util.DateUtils;

@Service
public class MongoCommonSaveManagerImpl extends BaseManagerImpl implements MongoCommonSaveManager {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public DynamicModelClass save(DynamicModelClass model) {
		if (BaseModel.ROW_STATE_DELETED.equals(model.getRowState())) {
			this.remove(model);
			return null;
		} else {
			String collection = determineCollection(model);
			this.fixModel(model, collection);
			log.info("mongodb save \"" + collection + "\", " + model);
			this.mongoTemplate.save(model, collection);
			return model;
		}
	}

	@Override
	public List<DynamicModelClass> saveAll(Collection<DynamicModelClass> models) {
		List<DynamicModelClass> result = new ArrayList<DynamicModelClass>();
		for (DynamicModelClass model : models) {
			model = this.save(model);
			if (model != null) {
				result.add(model);
			}
		}
		return result;
	}

	@Override
	public List<DynamicModelClass> saveTreeData(Collection<DynamicModelClass> models, String idField, String parentField) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public void remove(DynamicModelClass model) {
		String collection = determineCollection(model);
		log.info("mongodb remove \"" + collection + "\", " + model);
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(((DynamicModelClass) model).get("_id"))), collection);
	}

	@Override
	public void removeAll(Collection<DynamicModelClass> models) {
		for (DynamicModelClass model : models) {
			this.remove(model);
		}
	}
	
	private void fixModel(DynamicModelClass model, String collection) {
		if (model.containsKey("_id")) {
			if (collection != null) {
				DynamicModelClass dbmodel = this.mongoTemplate.findById(model.get("_id"), DynamicModelClass.class, collection);
				dbmodel.putAll(model);
				model.putAll(dbmodel);
			}
		} else {
			model.put("_id", UUID.randomUUID().toString());
		}
		model.remove("rowState");
		AcegiUserDetails userDetails = SessionContext.getUser();
		String userID = userDetails == null ? null : userDetails.getUserId();
		String sysDate = DateUtils.formatDateTime(new Date());
		if (model.get("creator") == null && model.get("createTime") == null) {
			model.put("creator", userID);
			model.put("createTime", sysDate);
		} else {
			model.put("modifier", userID);
			model.put("modifyTime", sysDate);
		}
		for (String field : model.validFields()) {
			if (field.indexOf(".") >= 0) {
				model.remove(field);
			} else {
				Object value = model.get(field);
				if (value instanceof DynamicModelClass[]) {
					for (DynamicModelClass subModel : (DynamicModelClass[]) value) {
						this.fixModel(subModel, null);
					}
				}
			}
		}
	}
	
	private String determineCollection(DynamicModelClass model) {
		String _class = (String) model.get("_class");
		if (_class != null) {
			int dsIndex = _class.indexOf("@");
			if (dsIndex > 0) {
				return _class.substring(0, dsIndex);
			} else {
				return _class;
			}
		}
		throw new SystemException("Model collection not specified. Should set '_class' property to the model object");
	}
	
}
