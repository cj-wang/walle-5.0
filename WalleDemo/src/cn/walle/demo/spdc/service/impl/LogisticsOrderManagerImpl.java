package cn.walle.demo.spdc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.demo.spdc.entity.LogisticsDetailsEntity;
import cn.walle.demo.spdc.entity.LogisticsOrderEntity;
import cn.walle.demo.spdc.model.LogisticsCargoModel;
import cn.walle.demo.spdc.model.LogisticsContainerModel;
import cn.walle.demo.spdc.model.LogisticsDetailsModel;
import cn.walle.demo.spdc.model.LogisticsOrderModel;
import cn.walle.demo.spdc.service.LogisticsOrderManager;
import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;

@Service
public class LogisticsOrderManagerImpl extends BaseManagerImpl implements LogisticsOrderManager {
	
	@Autowired
	private SelectCodeManager selectCodeManager;
	
	/**
	 * 保存订单，并且同步到仓码
	 */
	public LogisticsOrderEntity saveAndPostToCm(LogisticsOrderEntity orderEntity) {
		return this.save(orderEntity);
	}
	
	public LogisticsOrderEntity get(String id) {
		LogisticsOrderEntity orderEntity = this.dao.get(LogisticsOrderEntity.class, id);
		orderEntity.setDetails(this.dao.createCommonQuery(LogisticsDetailsEntity.class)
				.addCondition(Condition.eq(LogisticsDetailsModel.FieldNames.logisticsOrderId, id))
				.query());
		for (LogisticsDetailsEntity detailEntity : orderEntity.getDetails()) {
			detailEntity.setCargos(this.dao.createCommonQuery(LogisticsCargoModel.class)
					.addCondition(Condition.eq(LogisticsCargoModel.FieldNames.logisticsDetailsId, detailEntity.getLogisticsDetailsId()))
					.query());
			detailEntity.setContainers(this.dao.createCommonQuery(LogisticsContainerModel.class)
					.addCondition(Condition.eq(LogisticsContainerModel.FieldNames.logisticsDetailsId, detailEntity.getLogisticsDetailsId()))
					.query());
		}
		//为前台combogrid控件准备代码值，否则控件再查询影响页面加载速度
		addSelectCodeValues(orderEntity);
		return orderEntity;
	}
	
	public LogisticsOrderEntity getByOrderNo(String orderNo) {
		return this.get(this.getIdByOrderNo(orderNo));
	}

	public LogisticsOrderEntity save(LogisticsOrderEntity orderEntity) {
		this.removeDetailsByPk(orderEntity.getLogisticsOrderId());
		orderEntity = this.dao.save(orderEntity);
		for (LogisticsDetailsEntity detailEntity : orderEntity.getDetails()) {
			detailEntity.setLogisticsOrderId(orderEntity.getLogisticsOrderId());
		}
		orderEntity.setDetails(this.dao.saveAll(orderEntity.getDetails()));
		for (LogisticsDetailsEntity detailEntity : orderEntity.getDetails()) {
			for (LogisticsCargoModel cargo : detailEntity.getCargos()) {
				cargo.setLogisticsDetailsId(detailEntity.getLogisticsDetailsId());
			}
			detailEntity.setCargos(this.dao.saveAll(detailEntity.getCargos()));
			for (LogisticsContainerModel container : detailEntity.getContainers()) {
				container.setLogisticsDetailsId(detailEntity.getLogisticsDetailsId());
			}
			detailEntity.setContainers(this.dao.saveAll(detailEntity.getContainers()));
		}
		return orderEntity;
	}

	public void removeByPk(String id) {
		this.removeDetailsByPk(id);
		this.dao.removeByPk(LogisticsOrderModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		for (String id : ids) {
			this.removeByPk(id);
		}
	}
	
	public void removeByOrderNo(String orderNo) {
		this.removeByPk(this.getIdByOrderNo(orderNo));
	}

	public void removeAllByOrderNo(Collection<String> orderNos) {
		for (String orderNo : orderNos) {
			this.removeByPk(this.getIdByOrderNo(orderNo));
		}
	}
	
	private String getIdByOrderNo(String orderNo) {
		List<LogisticsOrderModel> orderList = this.dao.createCommonQuery(LogisticsOrderModel.class)
		.addCondition(Condition.eq(LogisticsOrderModel.FieldNames.logisticsOrderNo, orderNo))
		.query();
		if (orderList.size() > 0) {
			return orderList.get(0).getLogisticsOrderId();
		} else {
			throw new ApplicationException("Order no '" + orderNo + "' not exists");
		}
	}

	private void removeDetailsByPk(String id) {
		List<LogisticsDetailsModel> details = this.dao.createCommonQuery(LogisticsDetailsModel.class)
		.addCondition(Condition.eq(LogisticsDetailsModel.FieldNames.logisticsOrderId, id))
		.query();
		for (LogisticsDetailsModel detail : details) {
			this.dao.removeAll(this.dao.createCommonQuery(LogisticsCargoModel.class)
					.addCondition(Condition.eq(LogisticsCargoModel.FieldNames.logisticsDetailsId, detail.getLogisticsDetailsId()))
					.query());
			this.dao.removeAll(this.dao.createCommonQuery(LogisticsContainerModel.class)
					.addCondition(Condition.eq(LogisticsContainerModel.FieldNames.logisticsDetailsId, detail.getLogisticsDetailsId()))
					.query());
		}
		this.dao.removeAll(details);
	}
	
	/**
	 * 为前台combogrid控件准备代码值，否则控件再查询影响页面加载速度
	 * @param orderEntity
	 */
	private void addSelectCodeValues(LogisticsOrderEntity orderEntity) {
		List<LogisticsOrderEntity> orderList = new ArrayList<LogisticsOrderEntity>();
		orderList.add(orderEntity);
		Map<String, String> fieldCodeTypes = new HashMap<String, String>();
		fieldCodeTypes.put("consignee", "CIM_CUST");
		fieldCodeTypes.put("consigner", "CIM_CUST");
		this.addSelectCodeValues(orderEntity, this.selectCodeManager.getSelectCodeValues(orderList, fieldCodeTypes));
		//containers
		List<LogisticsContainerModel> containerList = new ArrayList<LogisticsContainerModel>();
		for (LogisticsDetailsEntity detail : orderEntity.getDetails()) {
			containerList.addAll(detail.getContainers());
		}
		fieldCodeTypes.clear();
		fieldCodeTypes.put("operatorComp", "CIM_CUST");
		fieldCodeTypes.put("ownerComp", "CIM_CUST");
		this.addSelectCodeValues(orderEntity, this.selectCodeManager.getSelectCodeValues(containerList, fieldCodeTypes));
	}
	
	private void addSelectCodeValues(LogisticsOrderEntity orderEntity, Map<String, Map<Object, String>> selectCodeValues) {
		if (orderEntity.getSelectCodeValues() == null) {
			orderEntity.setSelectCodeValues(new HashMap<String, Map<Object,String>>());
		}
		for (String codeType : selectCodeValues.keySet()) {
			if (orderEntity.getSelectCodeValues().containsKey(codeType)) {
				orderEntity.getSelectCodeValues().get(codeType).putAll(selectCodeValues.get(codeType));
			} else {
				orderEntity.getSelectCodeValues().put(codeType, selectCodeValues.get(codeType));
			}
		}
	}
	
}
