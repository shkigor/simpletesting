package ck.solo.simpletesting.helpers;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ck.solo.simpletesting.model.Answer;
import ck.solo.simpletesting.model.Question;

@Component
@Scope("prototype")
public class Test implements Serializable {

	private static final long serialVersionUID = 1L;

	private Question testQuestion;
	private List<Answer> testAnswers;
	private Answer testAnswerResult;
	private int testQuestionTimer;

	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	public Test() {
		logger.info("конструктор Test()");
	}

	public Test(Question testQuestion, List<Answer> testAnswers, int testQuestionTimer) {
		logger.info("конструктор Test(Question testQuestion = " + testQuestion + ", List<Answer> testAnswers = " + testAnswers + ", int testQuestionTimer = " + testQuestionTimer + ")");
		this.testQuestion = testQuestion;
		this.testAnswers = testAnswers;
		this.testQuestionTimer = testQuestionTimer;
	}

	public Answer getTestAnswerResult() {
		return testAnswerResult;
	}

	public void setTestAnswerResult(Answer testAnswerResult) {
		this.testAnswerResult = testAnswerResult;
	}

	public Question getTestQuestion() {
		return testQuestion;
	}

	public void setTestQuestion(Question testQuestion) {
		this.testQuestion = testQuestion;
	}

	public List<Answer> getTestAnswers() {
		return testAnswers;
	}

	public void setTestAnswers(List<Answer> testAnswers) {
		this.testAnswers = testAnswers;
	}

	public int getTestQuestionTimer() {
		return testQuestionTimer;
	}

	public void setTestQuestionTimer(int testQuestionTimer) {
		this.testQuestionTimer = testQuestionTimer;
	}

	@Override
	public String toString() {
		return "Test [testQuestion=" + testQuestion + ", testAnswers=" + testAnswers + ", testAnswerResult=" + testAnswerResult + ", testQuestionTimer=" + testQuestionTimer + "]";
	}

}
