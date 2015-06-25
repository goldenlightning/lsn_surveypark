package com.cmb.surveypark.struts2.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.UserService;
import com.cmb.surveypark.util.DataUtil;

@Controller
@Scope("prototype")
public class RegAction extends BaseAction<User> {

	private static final long serialVersionUID = 1L;

	
	private String confirmPassword;
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Resource
	private UserService userService;
	
	
	/**
	 * 到达注册页面
	 * @return
	 */
	@SkipValidation
	public String toRegPage() {
		return "regPage";
	}
	
	/**
	 * 进行用户注册
	 * @return
	 */
	public String doReg() {
		model.setPassword(DataUtil.md5(model.getPassword()));
		userService.saveEntity(model);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		//1.非空
		String email = model.getEmail();
		if(StringUtils.isBlank(email)) {
			addFieldError("email", "email是必填项");
		}
		if(StringUtils.isBlank(model.getPassword())) {
			addFieldError("password", "password是必填项");
		}
		if(StringUtils.isBlank(model.getNickName())) {
			addFieldError("nickName", "nickName是必填项");
		}
		
		if(hasErrors())
			return ;
		
		//2.密码一致
		if(!model.getPassword().equals(confirmPassword)) {
			addFieldError("password", "密码不一致");
			return;
		}
		
		//email占用
		if(userService.isRegisted(model.getEmail())) {
			addFieldError("email", "email已经占用");
		}
	}

}
