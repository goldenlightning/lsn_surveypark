package com.cmb.surveypark.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 调查类
 * 
 * @author ThinkPad
 *
 */
public class Survey implements Serializable {
	
	private static final long serialVersionUID = -471505185711017064L;
	
	private Integer id;
	private String title = "未命名";
	private boolean closed;
	private String preText = "上一步";
	private String nextText = "下一步";
	private String doneText = "完成";
	private String exitText = "退出";
	private Date createTime = new Date();

	private String logoPhotoPath;
	
	private float minOrderno;
	private float maxOrderno;
	
	/**
	 * many to one的关系 这样而不是user那边one to
	 * many是因为如果从用户那边的话，把用户信息存储到session中会带入survey信息
	 */
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 建立从Survey到Page之间一对多关联关系
	private Set<Page> pages = new HashSet<Page>();

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreText() {
		return preText;
	}

	public void setPreText(String preText) {
		this.preText = preText;
	}

	public String getNextText() {
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getDoneText() {
		return doneText;
	}

	public void setDoneText(String doneText) {
		this.doneText = doneText;
	}

	public String getExitText() {
		return exitText;
	}

	public void setExitText(String exitText) {
		this.exitText = exitText;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getLogoPhotoPath() {
		return logoPhotoPath;
	}

	public void setLogoPhotoPath(String logoPhotoPath) {
		this.logoPhotoPath = logoPhotoPath;
	}

	public float getMinOrderno() {
		return minOrderno;
	}

	public void setMinOrderno(float minOrderno) {
		this.minOrderno = minOrderno;
	}

	public float getMaxOrderno() {
		return maxOrderno;
	}

	public void setMaxOrderno(float maxOrderno) {
		this.maxOrderno = maxOrderno;
	}

}
