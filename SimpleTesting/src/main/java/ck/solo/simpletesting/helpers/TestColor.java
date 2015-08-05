package ck.solo.simpletesting.helpers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ck.solo.simpletesting.model.Answer;
import ck.solo.simpletesting.model.Question;
import ck.solo.simpletesting.service.AnswerService;

@Component
public class TestColor implements TestPerformer {

	private static final Logger logger = LoggerFactory.getLogger(TestColor.class);

	@Autowired
	Test test;

	@Autowired
	private Environment environment;

	@Autowired
	AnswerService answerService;

	@Autowired
	ApplicationContext ctx;

	public TestColor() {
		logger.info("конструктор TestColor()");
	}

	@Override
	public Test nextTest(Question question) {
		logger.info("метод nextTest(): question - {}", question);

		int testQuestionTimer = Integer.valueOf(environment.getRequiredProperty("test.question.timer"));
		int questionCountMin = Integer.valueOf(environment.getRequiredProperty("question.count.min"));
		int questionCountMax = Integer.valueOf(environment.getRequiredProperty("question.count.max"));

		// Сформировать для вопроса случайный список ответов
		List<Answer> testAnswers = answerService.findCorrectAndRandomAnswers(question.getAnswer(), questionCountMin, questionCountMax);

		// Создать новый тест (с вопросом, списком ответов и временем, которое дается на ответ)
		test = ctx.getBean(Test.class);
		test.setTestQuestion(question);
		test.setTestAnswers(testAnswers);
		test.setTestQuestionTimer(testQuestionTimer);

		return test;
	}

	@Override
	public String toString() {
		return "TestSystem [test=" + test + "]";
	}

}
