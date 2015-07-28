package ck.solo.simpletesting.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import ck.solo.simpletesting.model.Question;

@Repository("questionDao")
public class QuestionDaoImpl extends AbstractDao<Integer, Question>implements QuestionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> findAllQuestions() {
		Criteria criteria = createEntityCriteria();
		return (List<Question>) criteria.list();
	}

}
