package ck.solo.simpletesting.dao;

import java.util.List;

import ck.solo.simpletesting.model.Question;

public interface QuestionDao {

	List<Question> findAllQuestions();

	Question getByKey(Integer questionId);

	void persist(Question question);

}
