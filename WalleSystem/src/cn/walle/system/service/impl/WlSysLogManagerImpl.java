package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlSysLogModel;
import cn.walle.system.service.WlSysLogManager;
import cn.walle.system.service.WlUserManager;

@Service
public class WlSysLogManagerImpl extends BaseManagerImpl implements WlSysLogManager {
	@Autowired
	WlUserManager userManager;

	public List<WlSysLogModel> saveAll(Collection<WlSysLogModel> models) {
		return this.dao.saveAll(models);
	}
	
	public WlSysLogModel get(String id) {
		return this.dao.get(WlSysLogModel.class, id);
	}

	public List<WlSysLogModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlSysLogModel.class, orderBy, pagingInfo);
	}

	public List<WlSysLogModel> findByExample(WlSysLogModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlSysLogModel save(WlSysLogModel model) {
		return this.dao.save(model);
	}

	public void remove(WlSysLogModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlSysLogModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlSysLogModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlSysLogModel.class, ids);
	}
	
	/**将用户对系统的操作保存到系统日志表中
	 * @param operOject   操作对象 
	 * @param operAction  操作动作
	 * @param result 结果
	 * @param logDesc 日志描述
	 * @param remarks 备注
	 * @param state 状态 
	 */
	public void saveSysLog(String operOject,String operAction,String result,String logDesc,String remarks,String state){
		if(null==operAction||"".equals(operAction)||null==operOject||"".equals(operOject)){
			throw new ApplicationException("操作对象及操作动作不能为空，系统操作日志写入失败");
		}
		String userId = SessionContextUserEntity.currentUser().getUserModel().getUserId();
//		WlUserModel userModel = new WlUserModel();
//		userModel.setUserId(userId);
//		userModel = this.userManager.findByExample(userModel, null, null).get(0);
		
		WlSysLogModel model=new WlSysLogModel();
		model.setLogDate(new Date());
		model.setOperUserId(userId);
		model.setOperAction(operAction);
		model.setOperOject(operOject);
		model.setResult(result);
		//model.setLogDesc(userModel.getName() + logDesc);
		model.setLogDesc(logDesc);
		model.setState(state);
		model.setRemarks(remarks);
		this.save(model);
	}

}
