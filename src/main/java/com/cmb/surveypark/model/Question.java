package com.cmb.surveypark.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Question implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 214917565522705302L;

	private static final String RN = "\r\n";

	private Integer id;
	
	//题型0-8
	private int questionType;
	private String title;
	//选项
	private String options;
	private String[] optionArr;
	
	private boolean other;
	//其他项样式：0-无 1-文本框 2-下拉列表
	private int otherStyle;
	//其他项下拉选项
	private String otherSelectOptions;
	private String[] otherSelectOptionArr;
	
	//矩阵式列标题
	private String matrixRowTitles;
	private String[] matrixRowTitleArr;
	
	//矩阵式行标题
	private String matrixColTitles;
	private String[] matrixColTitleArr;
	//矩阵式下拉选项集
	private String matrixSelectOptions;
	private String[] matrixSelectOptionArr;
	
	//建立从Question到Page的many to one 关系
	private Page page;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
		if(!StringUtils.isBlank(options)) {
			this.optionArr = StringUtils.split(options, RN);
		}
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public int getOtherStyle() {
		return otherStyle;
	}

	public void setOtherStyle(int otherStyle) {
		this.otherStyle = otherStyle;
	}

	public String getOtherSelectOptions() {
		return otherSelectOptions;
	}

	public void setOtherSelectOptions(String otherSelectOptions) {
		this.otherSelectOptions = otherSelectOptions;
		if(!StringUtils.isBlank(otherSelectOptions)) {
			this.otherSelectOptionArr = StringUtils.split(otherSelectOptions, RN);
		}
	}

	public String getMatrixRowTitles() {
		return matrixRowTitles;
	}

	public void setMatrixRowTitles(String matrixRowTitles) {
		this.matrixRowTitles = matrixRowTitles;
		if(!StringUtils.isBlank(matrixRowTitles)) {
			this.matrixRowTitleArr = StringUtils.split(matrixRowTitles, RN);
		}
	}

	public String getMatrixColTitles() {
		return matrixColTitles;
	}

	public void setMatrixColTitles(String matrixColTitles) {
		this.matrixColTitles = matrixColTitles;
		if(!StringUtils.isBlank(matrixColTitles)) {
			this.matrixColTitleArr = StringUtils.split(matrixColTitles, RN);
		}
	}

	public String getMatrixSelectOptions() {
		return matrixSelectOptions;
	}

	public void setMatrixSelectOptions(String matrixSelectOptions) {
		this.matrixSelectOptions = matrixSelectOptions;
		if(!StringUtils.isBlank(matrixSelectOptions)) {
			this.matrixSelectOptionArr = StringUtils.split(matrixSelectOptions, RN);
		}
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String[] getOptionArr() {
		return optionArr;
	}

	public String[] getOtherSelectOptionArr() {
		return otherSelectOptionArr;
	}

	public String[] getMatrixRowTitleArr() {
		return matrixRowTitleArr;
	}

	public String[] getMatrixColTitleArr() {
		return matrixColTitleArr;
	}

	public String[] getMatrixSelectOptionArr() {
		return matrixSelectOptionArr;
	}


}
