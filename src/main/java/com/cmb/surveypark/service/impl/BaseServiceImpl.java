package com.cmb.surveypark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cmb.surveypark.dao.BaseDao;
import com.cmb.surveypark.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao;
	
	@Resource
	public void setBaseDao(BaseDao<T> baseDao) {
		this.dao = baseDao;
	}

	@Override
	public void saveEntity(T t) {
		dao.saveEntity(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
	}

	@Override
	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	@Override
	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
		
	}

	@Override
	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	@Override
	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}
	
	@Override
	public Object uniqueResult(String hql, Object... objects) {
		return dao.uniqueResult(hql, objects);
	}

}
