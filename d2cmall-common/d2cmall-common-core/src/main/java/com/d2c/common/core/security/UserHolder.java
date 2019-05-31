package com.d2c.common.core.security;

//@Component
public class UserHolder {
//	private static final String SESSION_CURRENT_USER = "SESSION_CURRENT_USER";
//	
//	private static UserHolder instance;
//	
//	@Autowired
//	private HttpSession httpSession;
//	
//	private ThreadLocal<CurrentUser> userPools = new ThreadLocal<CurrentUser>();
//	
//	public static UserHolder getInstance(){
//		if(instance == null){
//			instance = SpringHelper.getBean(UserHolder.class);
//		}
//		return instance;
//	}
//
//	public static void setCurrentUser(CurrentUser user) {
//		getInstance().setUser(user);
//	}
//
//	public static <T extends CurrentUser> T getCurrentUser(Class<T> clz) {
//		return BeanUt.cast(getCurrentUser(), clz);
//	}
//
//	public static CurrentUser getCurrentUser() {
//		return getInstance().getUser();
//	}
//
//	public static String getUserName() {
//		CurrentUser user = getCurrentUser();
//		if (user != null) {
//			return user.getUsername();
//		}
//		return "admin";
//	}
//	
//	//************************************
//	
//	public void setUser(CurrentUser user){
//		if(httpSession != null){
//			httpSession.setAttribute(SESSION_CURRENT_USER, user);
//		}else{
//			if(userPools == null) userPools = new ThreadLocal<CurrentUser>();
//			userPools.set(user);
//		}
//	}
//
//	public CurrentUser getUser() {
//		if(httpSession != null){
//			return (CurrentUser) httpSession.getAttribute(SESSION_CURRENT_USER);
//		}else if(userPools != null) {
//			return userPools.get();
//		}else{
//			return null;
//		}
//	}
}
