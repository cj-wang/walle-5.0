package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.model.WlUserLoginLogModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.query.ChatUsersQueryCondition;
import cn.walle.system.query.ChatUsersQueryItem;
import cn.walle.system.query.RoleGrantedUserQueryCondition;
import cn.walle.system.query.RoleGrantedUserQueryItem;
import cn.walle.system.query.UserLoginQueryCondition;
import cn.walle.system.query.UserOrganizeQueryCondition;
import cn.walle.system.query.UserOrganizeQueryItem;
import cn.walle.system.query.UsersByNameQueryCondition;
import cn.walle.system.query.UsersByNameQueryItem;
import cn.walle.system.service.WlSysLogManager;
import cn.walle.system.service.WlUserLoginLogManager;
import cn.walle.system.service.WlUserManager;

@Service
public  class WlUserManagerImpl extends BaseManagerImpl implements WlUserManager {

	@Autowired
	private WlSysLogManager sysLogManager;
	@Autowired
	private WlUserLoginLogManager wlUserLoginLogManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SaltSource saltSource;
	
	public WlUserModel get(String id) {
		return this.dao.get(WlUserModel.class, id);
	}

	public List<WlUserModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlUserModel.class, orderBy, pagingInfo);
	}

	public List<WlUserModel> findByExample(WlUserModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlUserModel save(WlUserModel model) {
		model = this.dao.save(model);
		if (model.getUserId().equals(this.getCurrentUserId())) {
			SessionContextUserEntity currentUser = this.getCurrentUser();
			currentUser.setFullname(model.getName());
		}
		return model;
	}

	public List<WlUserModel> saveAll(Collection<WlUserModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlUserModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlUserModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlUserModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlUserModel.class, ids);
	}

	public WlUserModel saveModel(WlUserModel model) {
		model.setLoginName(model.getLoginName().trim());
		String userId = model.getUserId();
		String loginname = model.getLoginName();
		String name = model.getName();
		boolean addFlag = true;
		if(userId==null || "".equals(userId)){
			addFlag = true;
			
			UserLoginQueryCondition c = new UserLoginQueryCondition();
			c.setState(new String[] {"U", "F"});
			c.setUsername(loginname);
			if (this.dao.queryRowCount(c, null, null) > 0) {
				throw new ApplicationException("登录名'" + loginname + "'已被使用，请选择其他登录名");
			}
		}else{
			addFlag = false;
			
			UserLoginQueryCondition c = new UserLoginQueryCondition();
			c.setState(new String[] {"U", "F"});
			c.setUsername(loginname);
			List<WlUserModel> listmodel = this.dao.query(c, WlUserModel.class);
			if (listmodel.size() > 1) {
				throw new ApplicationException("登录名'" + loginname + "'已被使用，请选择其他登录名");
			}
			if(listmodel.size()==1){
				//判断是否是与其他用户的登录名一致
				String useridtemp = ((WlUserModel)listmodel.get(0)).getUserId();
				if(! useridtemp.equals(userId)){
					throw new ApplicationException("登录名'" + loginname + "'已被使用，请选择其他登录名");
				}
			}
		}
		
		if(addFlag){
			//加密密码
			SessionContextUserEntity tmpUserDetails = new SessionContextUserEntity();
			tmpUserDetails.setUsername(model.getLoginName());
			model.setPassword(passwordEncoder.encodePassword(model.getPassword(), saltSource.getSalt(tmpUserDetails)));
			//保存用户
			model = this.save(model);
			
			sysLogManager.saveSysLog("user", "添加用户", "1", "添加用户["+model.getLoginName()+"]", " 添加["+model.getLoginName()+"]成功", null);
		}else{
			//wcj: 修改用户不能重新加密密码！
//				//加密密码
//				SessionContextUserEntity tmpUserDetails = new SessionContextUserEntity();
//				tmpUserDetails.setUsername(model.getLoginName());
//				model.setPassword(passwordEncoder.encodePassword(model.getPassword(), saltSource.getSalt(tmpUserDetails)));
			
			//保存用户
			model = this.save(model);
			
			sysLogManager.saveSysLog("user", "编辑用户", "1", "对用户["+model.getLoginName()+"]进行编辑", " 编辑用户"+model.getLoginName()+"成功", null);
		}
		return model;
	}

	public void delByPk(String id){
		//判断该用户是否是admin，若是则不能删除
		WlUserModel admin = this.dao.get(WlUserModel.class, id);
		String loginname = admin.getLoginName();
		if("Admin".equals(loginname)){
			throw new ApplicationException("该用户为admin，不能进行删除操作！");
		}
		
		//逻辑删除用户,即把用户的状态置为停用
		//this.dao.removeByPk(WlUserModel.class, id);
		admin.setState("S");
		admin.setOrganizeId(" ");
		this.save(admin);
		/*
		//删除此用户所授予的功能
		WlUserFuncModel funcModel =new WlUserFuncModel();
		funcModel.setUserId(id);
		List<WlUserFuncModel>lists = this.wlUserFuncManager.findByExample(funcModel, null, null);
		if(lists != null && lists.size()>0){
			for(Object model : lists){
				String funcId = ((WlUserFuncModel)model).getFuncId();
				this.wlUserFuncManager.removeByPk(funcId);
			}
		}
		//删除此用户所授予的角色
		WlUserRoleModel roleModel = new WlUserRoleModel();
		roleModel.setUserId(id);
		List<>
		*/
		sysLogManager.saveSysLog("user", "删除", "1", "删除用户["+admin.getLoginName()+"]", "删除["+admin.getLoginName()+"]成功", null);
	}
	
	/**
	 * 将禁用的用户启用
	 * @param id
	 * @author inn
	 */
	public void enableUser(String id){
		if(id == null || "".equals(id)){
			throw new ApplicationException("请选择要启用的用户!");
		}
		
		WlUserModel user = this.dao.get(WlUserModel.class, id);
		String status=user.getState();
		//判断用户状态 如果用户当前已经是使用状态U则无需启用
		if(status.equals("U")){
			throw new ApplicationException("此用户可用，无需启用！");
		}
		
		user.setState("U");
		this.save(user);
		sysLogManager.saveSysLog("user", "启用", "1", "对禁用用户["+user.getLoginName()+"]进行启用", "启用"+user.getLoginName()+"成功", null);
	}
	
	/**
	 * 禁用启用用户
	 * @param id
	 * @author inn
	 */
	public void forbidUser(String id){
		if(id == null || "".equals(id)){
			throw new ApplicationException("请选择要禁用的用户!");
		}
		//判断该用户是否是admin，若是则不能操作
		WlUserModel user = this.dao.get(WlUserModel.class, id);
		String loginname = user.getLoginName();
		if("Admin".equals(loginname)){
			throw new ApplicationException("该用户为admin，不能进行禁用操作！");
		}
		
		String status=user.getState();
		//判断用户状态 如果用户当前已经是禁用状态 F则无需禁用
		if(status.equals("F")){
			throw new ApplicationException("此用户已经被禁用，无需再次禁用！");
		}
		user.setState("F");
		this.save(user);
		sysLogManager.saveSysLog("user", "禁用", "1", "对用户["+user.getLoginName()+"]进行禁用操作", "禁用用户["+user.getLoginName()+"]成功", null);
		
	}
	
	/**
	 * 更换密码
	 */
	public void changePassword(String oldPassword,String password){
		//加密旧密码
		oldPassword = passwordEncoder.encodePassword(oldPassword, saltSource.getSalt(SessionContextUserEntity.currentUser()));
		WlUserModel user = this.dao.get(WlUserModel.class, SessionContextUserEntity.currentUser().getUserModel().getUserId());
		if(oldPassword.equals(user.getPassword()))
		{
			//加密新密码
			//如果改为可以修改其他人密码，此处要改
			password = passwordEncoder.encodePassword(password, saltSource.getSalt(SessionContextUserEntity.currentUser()));
			user.setPassword(password);
			this.dao.save(user);
			SessionContextUserEntity.currentUser().setPassword(password);
			//保存密码修改日志
			sysLogManager.saveSysLog("user", "密码修改", "1", "修改成功", null, null);
		}
		else{
			sysLogManager.saveSysLog("user", "密码修改", "0", "修改失败", "原密码填写错误", null);
			throw new ApplicationException("原密码错误!");
		}
		
	}
	
	public void passwordReset(String id){
		WlUserModel user=this.get(id);
		String password = "123456";
		//加密新密码
		SessionContextUserEntity tmpUserDetails = new SessionContextUserEntity();
		tmpUserDetails.setUsername(user.getLoginName());
		password = passwordEncoder.encodePassword(password, saltSource.getSalt(tmpUserDetails));
		user.setPassword(password);
		this.dao.save(user);
		
		sysLogManager.saveSysLog("user", "密码重置", "1", "对用户["+user.getLoginName()+"]的密码进行重置", "重置成功", null);
	}
	
	/**
	 * 重置所有用户密码
	 */
	public void passwordResetAllUsers() {
		for (WlUserModel user : getAll(null, null)) {
			passwordReset(user.getUserId());
		}
	}
	
	/**
	 * 生成用户登录日志并将userLogId保存在session中，在登录到退出过程中存在
	 * @param model
	 */
	public void saveUserLoginLog(WlUserModel model){
		Date now=new Date();
		WlUserLoginLogModel wlUserLoginLogmodel=new WlUserLoginLogModel();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =servletRequestAttributes.getRequest(); 
		wlUserLoginLogmodel.setLoginTime(now);//登录时间
		wlUserLoginLogmodel.setUserId(model.getUserId());//用户
		HttpSession session = null;
		if(request!=null){
			wlUserLoginLogmodel.setUserIp(request.getRemoteAddr());//用户ip
			wlUserLoginLogmodel.setHostName(request.getRemoteHost());//用户主机名
			session = request.getSession();
		}
		
		wlUserLoginLogmodel.setTryTimes(1l);//尝试登录次数
		
		wlUserLoginLogmodel=wlUserLoginLogManager.save(wlUserLoginLogmodel);
		
		if(session != null){
			session.setAttribute("userLogId", wlUserLoginLogmodel.getUserLoginLogId());
		}
		
	}
	
	/**
	 * 用户退出系统时，更改用户登录日志中的退出时间
	 * @param userLogId
	 */
	public void userLogoutLog(String userLogId){
		try{
			WlUserLoginLogModel wlUserLoginLogmodel=wlUserLoginLogManager.get(userLogId);
			wlUserLoginLogmodel.setLogoutTime(new Date());
			wlUserLoginLogManager.save(wlUserLoginLogmodel);
		}catch(ObjectRetrievalFailureException e){
			
		}
	}
	
	/**
	 * 获取当前用户Id的公用方法
	 * @return
	 * @author inn
	 */
	public String getCurrentUserId(){
		String userId = SessionContextUserEntity.currentUser().getUserModel().getUserId();
		if(userId == null || "".equals(userId)){
			throw new ApplicationException("当前用户不存在");
		}
		return userId;
	}
	
	public SessionContextUserEntity getCurrentUser() {
		return SessionContextUserEntity.currentUser();
	}
	
	/**
	 * 通过组织编号来获取在该组织下的所有用户
	 * @param orgId
	 * @return
	 */
	public List<WlUserModel> getUsersByOrgId(String orgId){
		WlUserModel example = new WlUserModel();
		example.setOrganizeId(orgId);
		example.setState("U");//状态为“使用用户”
		return this.findByExample(example, null, null);
	}
	
	/**
	 * 通过用户姓名获取可用用户
	 * @param userName
	 * @return
	 */
	public List<UsersByNameQueryItem> getUsersByUserName(String userName){
		UsersByNameQueryCondition userExample = new UsersByNameQueryCondition();
		userExample.setName(userName);
		List<UsersByNameQueryItem> users = this.dao.query(userExample, UsersByNameQueryItem.class);
		return users;
	}
	
	/**
	 * 通过组织Id查询组织内的成员
	 * @param organizeId
	 * @return
	 */
	public List<UserOrganizeQueryItem> getUsersByOrganizeId(String organizeId){
		if(organizeId == null || "".equals(organizeId)){
			throw new ApplicationException("组织Id为空");
		}
		UserOrganizeQueryCondition queryCondition = new UserOrganizeQueryCondition();
		queryCondition.setOrganizeId(organizeId);
		return this.dao.query(queryCondition, UserOrganizeQueryItem.class);
	}
	
	/**
	 * 通过角色Id查询角色内的成员
	 * @param roleId
	 * @return
	 */
	public List<RoleGrantedUserQueryItem> getUsersByRoleId(String roleId){
		if(roleId == null || "".equals(roleId)){
			throw new ApplicationException("角色Id为空");
		}
		RoleGrantedUserQueryCondition queryCondition = new RoleGrantedUserQueryCondition();
		queryCondition.setRoleId(roleId);
		return this.dao.query(queryCondition, RoleGrantedUserQueryItem.class);
	}

	@Override
	public List<WlFunctionModel> getGrantedFuncListByUserId(String userId) {
		
		return null;
	}

	/**
	 * 在聊天模块中，根据条件查询用户
	 * @param hql
	 * @param obj
	 * @return
	 */
	public List<ChatUsersQueryItem> findUsersByCondition(ChatUsersQueryCondition condition){
		return this.dao.createCommonQuery(condition, ChatUsersQueryItem.class).query();
//		return this.dao.findBySqlCondition(WlUserModel.class, hql, obj,"online_status desc");
	}
	
	/**
	 * 用于Moblie 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public String mobileLogin(String userName,String password){
		if(userName == null || "".equals(userName.trim())){
			return "请输入您的用户名";
		}
		if(password == null || "".equals(password.trim())){
			return "请输入您的密码";
		}
		WlUserModel userExample = new WlUserModel();
		userExample.setLoginName(userName);
		List<WlUserModel> users = this.findByExample(userExample, null, null);
		if (users.size() == 0) {
			return "User " + userName + " not found";
		}
		WlUserModel user = users.iterator().next();
		if(user.getState().equals("F")){
			return "User " + userName + " has been forbidden";
		}
		if(user.getState().equals("S")){
			return "User " + userName + " has been forbidden";
		}
//		SessionContextUserEntity contextUser = new SessionContextUserEntity();
//		contextUser.setUserId(user.getUserId());
//		contextUser.setUsername(user.getLoginName());
//		contextUser.setFullname(user.getName());
//		contextUser.setPassword(user.getPassword());
//		contextUser.setUserModel(user);
		if(user!=null){
			saveUserLoginLog(user);
		}
		return "success";
	}
	
	public  static String encodePw(){
		
		return null;
	}
}
