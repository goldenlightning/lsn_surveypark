package com.cmb.surveypark.struts2.action;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
 			model = (T) clazz.newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public T model;
	
	@Override
	public void prepare() throws Exception {
	}
	
	public T getModel() {
		return model;
	} 

}
