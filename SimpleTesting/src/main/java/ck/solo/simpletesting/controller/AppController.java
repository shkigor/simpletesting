package ck.solo.simpletesting.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ck.solo.simpletesting.helpers.Test;
import ck.solo.simpletesting.model.Answer;
import ck.solo.simpletesting.model.Question;
import ck.solo.simpletesting.service.AnswerService;
import ck.solo.simpletesting.service.CommonService;
import ck.solo.simpletesting.service.QuestionService;

@Controller
public class AppController {

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	@Autowired
	CommonService commonService;

	@Autowired
	private Environment environment;

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	/**
	 * Метод заполняет таблицы тестовыми вопросами и ответами.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/fillDB" }, method = RequestMethod.POST)
	@ResponseBody
	public String fillDB() {
		logger.info("Метод -- fillDB().");

		commonService.fillDB();
		return "success";
	}

	/**
	 * Метод показывает все существующие вопросы.
	 * 
	 * @param model
	 *            1) список всех вопросов
	 * @return вид allquestions.jsp
	 */
	@RequestMapping(value = { "/questions" }, method = RequestMethod.GET)
	public String listQuestions(ModelMap model) {
		logger.info("Метод -- listQuestions().");

		List<Question> questions = questionService.findAllQuestions();
		model.addAttribute("questions", questions);
		return "allquestions";
	}

	/**
	 * Метод показывает все существующие ответы.
	 * 
	 * @param model
	 *            1) список всех ответов
	 * @return вид allanswers.jsp
	 */
	@RequestMapping(value = { "/answers" }, method = RequestMethod.GET)
	public String listAnswers(ModelMap model) {
		logger.info("Метод -- listAnswers().");

		List<Answer> answers = answerService.findAllAnswers();
		model.addAttribute("answers", answers);
		return "allanswers";
	}

	/**
	 * Метод показывает результат прохождения тестов.
	 * 
	 * @param model
	 *            1) список всех тестовых вопросов, 2) кол-во правильных ответов
	 * @param request
	 *            HttpServletRequest
	 * @return вид answersresult.jsp
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/answersresult" }, method = RequestMethod.GET)
	public String answersResult(ModelMap model, HttpServletRequest request) {
		logger.info("Метод -- selectedResult().");

		LinkedList<Test> listTestResults = (LinkedList<Test>) request.getSession().getAttribute("listTestResults");
		if (listTestResults != null) {
			model.addAttribute("tests", listTestResults);

			int correctAnswers = 0;

			for (Test test : listTestResults) {
				if (test.getTestAnswerResult() != null) {
					if (test.getTestQuestion().getAnswer().equals(test.getTestAnswerResult())) {
						correctAnswers++;
					}
				}
			}

			model.addAttribute("correctAnswers", correctAnswers);
		}
		return "answersresult";
	}

	/**
	 * Метод показывает страницу-заготовку для тестовых вопросов.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return вид test.jsp
	 */
	@RequestMapping(value = { "/test/start" }, method = RequestMethod.GET)
	public String test(HttpServletRequest request) {
		logger.info("Метод -- test().");

		request.getSession().removeAttribute("listQuestions");
		request.getSession().removeAttribute("listTestResults");
		return "test";
	}

	/**
	 * Метод возвращает очередной тестовый вопрос в формате JSON.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param currentQuestionId
	 *            id текущего вопроса
	 * @param currentAnswerId
	 *            id ответа пользователя
	 * @return объект Test в формате JSON
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/ajax" }, method = RequestMethod.POST)
	@ResponseBody
	public Test getNextTest(HttpServletRequest request, @RequestParam int currentQuestionId, @RequestParam int currentAnswerId) {

		logger.info("Метод -- getMyTest()" + " currentQuestionId = " + currentQuestionId + " currentAnswerId = " + currentAnswerId);

		int testQuestionTimer = Integer.valueOf(environment.getRequiredProperty("test.question.timer"));
		int questionCountMin = Integer.valueOf(environment.getRequiredProperty("question.count.min"));
		int questionCountMax = Integer.valueOf(environment.getRequiredProperty("question.count.max"));

		Test test = new Test();
		// список неотвеченных вопросов
		LinkedList<Question> listQuestions = null;
		// список пройденных вопросов/тестов
		LinkedList<Test> listTestResults = null;
		HttpSession session = request.getSession();

		// Если сессия не новая
		if (!session.isNew()) {
			listQuestions = (LinkedList<Question>) request.getSession().getAttribute("listQuestions");
			listTestResults = (LinkedList<Test>) request.getSession().getAttribute("listTestResults");
		}

		if (listQuestions == null) {
			listQuestions = new LinkedList<Question>(questionService.findAllQuestions());
			listTestResults = new LinkedList<Test>();
		}

		// Запомнить ответ пользователя на последний вопрос
		if (currentQuestionId != -1) {
			listTestResults.getLast().setTestAnswerResult(answerService.getByKey(currentAnswerId));
		}

		// Если еще есть вопросы в тесте
		if (!listQuestions.isEmpty()) {
			logger.info("listQuestions empty" + listQuestions);
			// Взять первый вопрос из списка
			Question testQuestion = listQuestions.getFirst();
			// Сформировать для вопроса случайный список ответов
			List<Answer> testAnswers = answerService.findCorrectAndRandomAnswers(testQuestion.getAnswer(), questionCountMin, questionCountMax);

			// Создать новый тест (с вопросом, списком ответов и временем, которое дается на ответ)
			// test = new Test(testQuestion, testAnswers, testQuestionTimer);
			test.setTestQuestion(testQuestion);
			test.setTestAnswers(testAnswers);
			test.setTestQuestionTimer(testQuestionTimer);

			// Удалить вопрос (он первый в списке) из списка неотвеченных вопросов
			listQuestions.removeFirst();
			// Запомнить список в сессии
			request.getSession().setAttribute("listQuestions", listQuestions);

			// Добавить в список пройденных вопросов/тестов очередной Тест
			listTestResults.add(test);
			// Запомнить список в сессии
			request.getSession().setAttribute("listTestResults", listTestResults);
		} else {
			// Если тестовых вопросов больше нет
			// удалить из сессии список неотвеченных вопросов
			request.getSession().removeAttribute("listQuestions");
		}

		return test;
	}

}
