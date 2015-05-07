package cn.walle.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.system.entity.MenuListEntity;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.query.AuthorizedFunctionsQueryCondition;
import cn.walle.system.query.AuthorizedFunctionsQueryItem;
import cn.walle.system.query.UserLoginQueryCondition;
import cn.walle.system.query.UserMenuQueryCondition;
import cn.walle.system.query.UserMenuQueryItem;
import cn.walle.system.service.LoginManager;
import cn.walle.system.service.WlUserManager;

@Service
public class LoginManagerImpl extends BaseManagerImpl implements LoginManager {

	@Autowired
	private WlUserManager wlUserManager;
	
	/**
	 * 根据用户名取用户信息，登录时，由Spring Security调用。
	 * 返回的对象会存放在session里，可以用下面的方法取得：
	 * SessionContextUserEntity.currentUser();
	 * @author cj
	 */
	public AcegiUserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		UserLoginQueryCondition ulqc = new UserLoginQueryCondition();
		ulqc.setState(new String[] {"U"});
		ulqc.setUsername(username);
		List<WlUserModel> users = this.dao.query(ulqc, WlUserModel.class);
		if (users.size() == 0) {
			throw new UsernameNotFoundException("User " + username + " not found or has been forbidden");
		}
		WlUserModel user = users.iterator().next();
		
		SessionContextUserEntity contextUser = new SessionContextUserEntity();
		contextUser.setUserId(user.getUserId());
		contextUser.setUsername(user.getLoginName());
		contextUser.setPassword(user.getPassword());
		contextUser.setFullname(user.getName());
		contextUser.setTenantId(user.getTenantId());
		return contextUser;
	}

	public List<AuthorizedFunctionsQueryItem> getMainMenus() {
		AuthorizedFunctionsQueryCondition c = new AuthorizedFunctionsQueryCondition();
		c.setFuncTypes(new String[] {"pageGroup", "dynamic", "ajax", "iframe"});
		c.setUserId(SessionContextUserEntity.currentUser().getUserId());
		return this.dao.query(c, AuthorizedFunctionsQueryItem.class);
	}

	public List<AuthorizedFunctionsQueryItem> getAuthorizedFunctions() {
		AuthorizedFunctionsQueryCondition c = new AuthorizedFunctionsQueryCondition();
		c.setFuncTypes(new String[] {"function"});
		c.setUserId(SessionContextUserEntity.currentUser().getUserId());
		return this.dao.query(c, AuthorizedFunctionsQueryItem.class);
	}
	
	public boolean checkFunctionAuthorization(String funcCode) {
		AuthorizedFunctionsQueryCondition c = new AuthorizedFunctionsQueryCondition();
		c.setFuncTypes(new String[] {"function"});
		c.setUserId(SessionContextUserEntity.currentUser().getUserId());
		return this.dao.queryRowCount(c, "func_code = ?", new Object[] {funcCode}) > 0;
	}
	
	public List<MenuListEntity> getUserMenusForCurrentUser() {
		List<MenuListEntity> result = new ArrayList<MenuListEntity>();
		
		//获取当前用户所有的菜单项目
		UserMenuQueryCondition c = new UserMenuQueryCondition();
		c.setUserId(SessionContextUserEntity.currentUser().getUserId());
		List<UserMenuQueryItem> allMenus = this.dao.query(c, UserMenuQueryItem.class);
		if(allMenus.size()==0){
			return null;
		}
		
		//定义三级菜单
		List<MenuListEntity> menu1 = new ArrayList<MenuListEntity>();
		List<MenuListEntity> menu2 = new ArrayList<MenuListEntity>();
		List<MenuListEntity> menu3 = new ArrayList<MenuListEntity>();
		
		for (UserMenuQueryItem userMenuQueryItem : allMenus) {
			MenuListEntity e = new MenuListEntity();
			e.setUserId(userMenuQueryItem.getUserId());
			e.setFuncId(userMenuQueryItem.getFuncId());
			e.setFuncCode(userMenuQueryItem.getFuncCode());
			e.setName(userMenuQueryItem.getName());
			e.setParentId(userMenuQueryItem.getParentId());
			e.setFuncLevel(userMenuQueryItem.getFuncLevel());
			e.setFunSeq(userMenuQueryItem.getFunSeq());
			e.setViewname(userMenuQueryItem.getViewname());
			e.setFuncImg( userMenuQueryItem.getFuncImg());
			e.setFuncType(userMenuQueryItem.getFuncType());
			e.setSys(userMenuQueryItem.getSys());
			menu1.add(e);
			menu2.add(e);
			menu3.add(e);
		}
		menu1.get(0);
		for (MenuListEntity menuListEntity1 : menu1) {
			if(menuListEntity1.getParentId().equals("1")){
				
				menu2.get(0);
				for (MenuListEntity menuListEntity2 : menu2) {
					if(menuListEntity2.getParentId().equals(menuListEntity1.getFuncId())){

						menu3.get(0);
						for (MenuListEntity menuListEntity3 : menu3) {
							if(menuListEntity3.getParentId().equals(menuListEntity2.getFuncId())){
								menuListEntity2.getMenuItemList().add(menuListEntity3);
							}
						}
						menuListEntity1.getMenuItemList().add(menuListEntity2);
						
					}
				}
				result.add(menuListEntity1);
			}
			
		}
		
		return result;
	}
	
}
