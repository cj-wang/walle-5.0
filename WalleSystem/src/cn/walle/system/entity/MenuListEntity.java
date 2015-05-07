package cn.walle.system.entity;

import java.util.ArrayList;
import java.util.List;

import cn.walle.system.query.UserMenuQueryItem;

public class MenuListEntity extends UserMenuQueryItem{
	
	private List<MenuListEntity> menuItemList = new ArrayList<MenuListEntity>();

	public List<MenuListEntity> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<MenuListEntity> menuItemList) {
		this.menuItemList = menuItemList;
	}

//	private Map<String,UserMenuQueryItem> menuItemList = new HashMap<String, UserMenuQueryItem>();
	
}
