package com.cmb.surveypark.struts2.interceptor;

import com.cmb.surveypark.model.User;
import com.cmb.surveypark.struts2.UserAware;
import com.cmb.surveypark.struts2.action.BaseAction;
import com.cmb.surveypark.struts2.action.LoginAction;
import com.cmb.surveypark.struts2.action.RegAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseAction action = (BaseAction) invocation.getAction(); 
		if(action instanceof LoginAction || action instanceof RegAction) {
			return invocation.invoke();
		} else {
			User user = (User) invocation.getInvocationContext().getSession().get("user");
			if(user == null) {
				return "login";
			} else {
				if(action instanceof UserAware) {
					((UserAware)action).setUser(user);
				}
				return invocation.invoke();
			}
		}
	}

}
