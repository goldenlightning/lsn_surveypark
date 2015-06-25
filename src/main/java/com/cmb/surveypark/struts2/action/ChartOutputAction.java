package com.cmb.surveypark.struts2.action;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.statistics.QuestionStatisticsModel;
import com.cmb.surveypark.service.StatisticsService;

@Controller
@Scope("prototype")
public class ChartOutputAction extends BaseAction<Page> {

	private static final long serialVersionUID = 1L;
	
	private Integer qid;
	
	//图表类型
	private int chartType;
	
	@Resource
	private StatisticsService ss;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public InputStream getIs() {
		QuestionStatisticsModel qsm = ss.statistics(qid);
		
		return null;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

}
