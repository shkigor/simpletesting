package ck.solo.simpletesting.dao;

import java.util.List;

import ck.solo.simpletesting.model.Answer;

public interface AnswerDao {

	List<Answer> findAllAnswers();

	List<Answer> findRandomAnswers();

	List<Answer> findCorrectAndRandomAnswers(Answer correctAnswer, int questionCountMin, int questionCountMax);

	Answer getByKey(Integer answerId);

	void persist(Answer answer);

}
