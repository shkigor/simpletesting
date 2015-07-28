package ck.solo.simpletesting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ck.solo.simpletesting.dao.AnswerDao;
import ck.solo.simpletesting.model.Answer;

@Service("answerService")
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao dao;

	@Override
	public List<Answer> findAllAnswers() {
		return dao.findAllAnswers();
	}

	@Override
	public List<Answer> findRandomAnswers() {
		return dao.findRandomAnswers();
	}

	@Override
	public List<Answer> findCorrectAndRandomAnswers(Answer correctAnswer, int questionCountMin, int questionCountMax) {
		return dao.findCorrectAndRandomAnswers(correctAnswer, questionCountMin, questionCountMax);
	}

	@Override
	public Answer getByKey(Integer answerId) {
		return dao.getByKey(answerId);
	}

	@Override
	public void persist(Answer answer) {
		dao.persist(answer);
	}

}
