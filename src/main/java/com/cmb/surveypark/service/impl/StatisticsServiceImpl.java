package com.cmb.surveypark.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmb.surveypark.dao.BaseDao;
import com.cmb.surveypark.model.Answer;
import com.cmb.surveypark.model.Question;
import com.cmb.surveypark.model.statistics.OptionStatisticsModel;
import com.cmb.surveypark.model.statistics.QuestionStatisticsModel;
import com.cmb.surveypark.service.StatisticsService;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Resource(name="answerDao")
	private BaseDao<Answer> answerDao;
	
	@Resource(name="questionDao")
	private BaseDao<Question> questionDao;
	
	
	@Override
	public QuestionStatisticsModel statistics(Integer qid) {
		Question q = questionDao.getEntity(qid);
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		qsm.setQuestion(q);
		
		//统计回答问题的总人数
		String qhql = "select count(*) from Answer a where a.questionId = ?";
		Long qcount = (Long) answerDao.uniqueResult(qhql, qid);
		qsm.setCount(qcount.intValue());
		
		String hql = "select count(*) from Answer a where a.questionId = ? and concat(',' + a.answerIds + ',') like ?";
		
		//统计每个选项的情况
		int type = q.getQuestionType();
		switch (type) {
		//非矩阵式
		case 1:
		case 2:
		case 3:
		case 4:
			String arr[] = q.getOptionArr();
			for (int i = 0; i < arr.length; i++) {
				OptionStatisticsModel osm = new OptionStatisticsModel();
				osm.setOptionLabel(arr[i]);
				osm.setOptionIndex(i);
				
				Long count = (Long) answerDao.uniqueResult(hql, qid, "%," + arr[i] + ",%");
				osm.setCount(count.intValue());
				qsm.getOptions().add(osm);
			}
			
			//other
			if(q.isOther()) {
				OptionStatisticsModel osm = new OptionStatisticsModel();
				osm.setOptionLabel("其他");
				
				Long count = (Long) answerDao.uniqueResult(hql, qid, "%,other,%");
				osm.setCount(count.intValue());
				qsm.getOptions().add(osm);
			}
			
			break;
		
		//矩阵式
		case 6:
		case 7:
		case 8:
			String[] rowArr = q.getMatrixRowTitleArr();
			String[] colArr = q.getMatrixColTitleArr();
			String[] selectArr = q.getMatrixSelectOptionArr(); 
			for(int i = 0; i < rowArr.length; i++) {
				for(int j = 0; j < colArr.length; j++) {
					OptionStatisticsModel osm = new OptionStatisticsModel();
					
					if(type != 8) {
						osm.setMatrixRowIndex(i);
						osm.setMatrixRowLabel(rowArr[i]);
						osm.setMatrixColIndex(j);
						osm.setMatrixColLabel(colArr[j]);
						Long count = (Long) answerDao.uniqueResult(hql, qid, "%" + i + "_" + j + "%");
						osm.setCount(count.intValue());
						qsm.getOptions().add(osm);
					} else {
						for(int k = 0; k < selectArr.length; k++) {
							osm.setMatrixRowIndex(i);
							osm.setMatrixRowLabel(rowArr[i]);
							osm.setMatrixColIndex(j);
							osm.setMatrixColLabel(colArr[j]);
							Long count = (Long) answerDao.uniqueResult(hql, qid, "%" + i + "_" + j + "_" + k + "%");
							osm.setCount(count.intValue());
							qsm.getOptions().add(osm);
						}
					}
				}
			}
			break;
			
		default:
			break;
		}
		
		return qsm;
	}

}
