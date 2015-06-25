package com.cmb.surveypark.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.UserService;
import com.cmb.surveypark.util.DataUtil;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;
	
	private Map<String, Object> sessionMap;
	
	/**
	 * 到达注册页面
	 * @return
	 */
	@SkipValidation
	public String toLoginPage() {
		return "loginPage";
	}
	
	/**
	 * 进行登录处理
	 * @return
	 */
	public String doLogin() {
		return SUCCESS;
	}
	
	/**
	 * 登录信息校验
	 */
	@Override
	public void validate() {
		//1.非空
		User user = userService.validateLoginInfo(model.getEmail(), DataUtil.md5(model.getPassword()));
		if(null == user) {
			addActionError("email/password错误");
		} else {
			sessionMap.put("user", user);
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

}
