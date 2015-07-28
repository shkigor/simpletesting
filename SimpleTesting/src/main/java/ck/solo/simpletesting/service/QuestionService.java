package ck.solo.simpletesting.service;

import java.util.List;

import ck.solo.simpletesting.model.Question;

public interface QuestionService {

	List<Question> findAllQuestions();

	Question getByKey(Integer questionId);

	void persist(Question question);
}
