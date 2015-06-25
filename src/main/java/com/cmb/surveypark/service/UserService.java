package com.cmb.surveypark.service;

import com.cmb.surveypark.model.User;

public interface UserService extends BaseService<User> {

	/**
	 * 判断email是否存在
	 * @param email
	 * @return
	 */
	boolean isRegisted(String email);

	User validateLoginInfo(String email, String md5);

}
