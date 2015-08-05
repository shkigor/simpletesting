package ck.solo.simpletesting.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ck.solo.simpletesting.helpers.Test;
import ck.solo.simpletesting.helpers.TestPerformer;
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
	TestPerformer testPerformer;

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

		// удалить из сессии список неотвеченных вопросов
		request.getSession().removeAttribute("listQuestions");
		// удалить из сессии список пройденных вопросов/тестов
		request.getSession().removeAttribute("listTestResults");

		// список неотвеченных вопросов
		LinkedList<Question> listQuestions = new LinkedList<Question>(questionService.findAllQuestions());
		// список пройденных вопросов/тестов
		LinkedList<Test> listTestResults = new LinkedList<Test>();

		// Запомнить список вопросов в сессии
		request.getSession().setAttribute("listQuestions", listQuestions);
		// Запомнить список ответов в сессии
		request.getSession().setAttribute("listTestResults", listTestResults);

		return "test";
	}

	/**
	 * Метод возвращает очередной тестовый вопрос в формате JSON.
	 * 
	 * @param currentQuestionId
	 *            id текущего вопроса
	 * @param currentAnswerId
	 *            id ответа пользователя
	 * @return объект Test в формате JSON
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/next" }, method = RequestMethod.POST)
	@ResponseBody
	public Test controllerNextTest(HttpServletRequest request, HttpServletResponse response, @RequestParam int currentQuestionId, @RequestParam int currentAnswerId) {

		logger.info("Метод -- controllerNextTest()" + " currentQuestionId = " + currentQuestionId + " currentAnswerId = " + currentAnswerId);

		Test test = null;

		// Получить список вопросов из сессии
		LinkedList<Question> listQuestions = (LinkedList<Question>) request.getSession().getAttribute("listQuestions");
		// Получить список ответов из сессии
		LinkedList<Test> listTestResults = (LinkedList<Test>) request.getSession().getAttribute("listTestResults");

		// Запомнить ответ пользователя на последний вопрос
		if (currentQuestionId != -1) {
			listTestResults.getLast().setTestAnswerResult(answerService.getByKey(currentAnswerId));
		}

		// Если еще есть вопросы в тесте
		if (!listQuestions.isEmpty()) {

			// Взять первый вопрос из списка
			Question testQuestion = listQuestions.getFirst();

			test = testPerformer.nextTest(testQuestion);

			// Удалить вопрос (он первый в списке) из списка неотвеченных вопросов
			listQuestions.removeFirst();
			// Запомнить список в сессии
			request.getSession().setAttribute("listQuestions", listQuestions);

			// Добавить в список пройденных вопросов/тестов очередной Тест
			listTestResults.add(test);
			// Запомнить список в сессии
			request.getSession().setAttribute("listTestResults", listTestResults);
		}

		if (test == null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			try {
				Writer writer = response.getWriter();
				writer.write("null");
				writer.close();
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return test;

	}

}
