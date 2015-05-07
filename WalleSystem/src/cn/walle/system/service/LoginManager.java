package cn.walle.system.service;

import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetailsService;
import cn.walle.system.entity.MenuListEntity;
import cn.walle.system.query.AuthorizedFunctionsQueryItem;

public interface LoginManager extends BaseManager, AcegiUserDetailsService {

	List<AuthorizedFunctionsQueryItem> getMainMenus();
	
	List<AuthorizedFunctionsQueryItem> getAuthorizedFunctions();
	
	boolean checkFunctionAuthorization(String funcCode);
	
	List<MenuListEntity> getUserMenusForCurrentUser();

}
