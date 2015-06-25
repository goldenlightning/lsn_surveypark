package com.cmb.surveypark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmb.surveypark.dao.BaseDao;
import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource(name="userDao")
	public void setBaseDao(BaseDao<User> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public boolean isRegisted(String email) {
		String hql = "from User u where u.email = ?";
		List<User> list = this.findEntityByHQL(hql, email);
		return  list != null && list.size() > 0;
	}

	@Override
	public User validateLoginInfo(String email, String md5) {
		String hql = "from User u where u.email = ? and u.password = ?";
		List<User> list = this.findEntityByHQL(hql, email, md5);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
