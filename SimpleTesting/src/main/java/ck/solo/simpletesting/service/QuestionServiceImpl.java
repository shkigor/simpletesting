package ck.solo.simpletesting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ck.solo.simpletesting.dao.QuestionDao;
import ck.solo.simpletesting.model.Question;

@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao dao;

	@Override
	public List<Question> findAllQuestions() {
		return dao.findAllQuestions();
	}

	@Override
	public Question getByKey(Integer questionId) {
		return dao.getByKey(questionId);
	}

	@Override
	public void persist(Question question) {
		dao.persist(question);

	}

}
