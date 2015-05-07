package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlOnlineUserModel;
import cn.walle.system.query.OnlineUsersQueryCondition;
import cn.walle.system.query.OnlineUsersQueryItem;
import cn.walle.system.service.WlOnlineUserManager;

@Service
public class WlOnlineUserManagerImpl extends BaseManagerImpl implements WlOnlineUserManager {
	public WlOnlineUserModel get(String id) {
		return this.dao.get(WlOnlineUserModel.class, id);
	}

	public List<WlOnlineUserModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlOnlineUserModel.class, orderBy, pagingInfo);
	}

	public List<WlOnlineUserModel> findByExample(WlOnlineUserModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlOnlineUserModel save(WlOnlineUserModel model) {
		return this.dao.save(model);
	}

	public List<WlOnlineUserModel> saveAll(Collection<WlOnlineUserModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlOnlineUserModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlOnlineUserModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlOnlineUserModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlOnlineUserModel.class, ids);
	}

	
	public Object[] getAllPrincipals() {
		List<OnlineUsersQueryItem> users = dao.query(new OnlineUsersQueryCondition(), OnlineUsersQueryItem.class);
		String[] userIds = new String[users.size()];
		for (int i = 0; i < userIds.length; i++) {
			userIds[i] = users.get(i).getUserId();
		}
		return userIds;
	}

/**
 * 
 */
	public void refreshLastRequest(String sessionId) {
		WlOnlineUserModel example = new WlOnlineUserModel();
		example.setSessionId(sessionId);
		for (WlOnlineUserModel user : dao.findByExample(example)) {
			user.setLastRequestTime(dao.getSysDate());
			dao.save(user);
		}
	}

	public void removeSessionInformation(String sessionId) {
		WlOnlineUserModel example = new WlOnlineUserModel();
		example.setSessionId(sessionId);
		for (WlOnlineUserModel user : dao.findByExample(example)) {
			dao.remove(user);
		}
	}

	public void expire(String sessionId) {
		WlOnlineUserModel example = new WlOnlineUserModel();
		example.setSessionId(sessionId);
		for (WlOnlineUserModel user : dao.findByExample(example)) {
			user.setExpired("1");
			dao.save(user);
		}
	}
/**
 * 强制退出
 * @param onlineUserId 
 * @author Administrator
 */
	public void forceExpired(String onlineUserId) {
		WlOnlineUserModel user = get(onlineUserId);
		user.setExpired("1");
		save(user);
	}

}
