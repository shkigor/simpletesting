package ck.solo.simpletesting.helpers;

import java.io.Serializable;
import java.util.List;

import ck.solo.simpletesting.model.Answer;
import ck.solo.simpletesting.model.Question;

public class Test implements Serializable {

	private static final long serialVersionUID = 1L;

	private Question testQuestion;
	private List<Answer> testAnswers;
	private Answer testAnswerResult;
	private int testQuestionTimer;

	public Test() {

	}

	public Test(Question testQuestion, List<Answer> testAnswers, int testQuestionTimer) {
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
