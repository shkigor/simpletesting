package ck.solo.simpletesting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ck.solo.simpletesting.dao.AnswerDao;
import ck.solo.simpletesting.dao.QuestionDao;
import ck.solo.simpletesting.model.Answer;
import ck.solo.simpletesting.model.Question;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionDao questionDao;

	@Override
	public void fillDB() {
		Answer answerWhite = new Answer("белый");
		answerDao.persist(answerWhite);

		Answer answerBlack = new Answer("черный");
		answerDao.persist(answerBlack);

		Answer answerRed = new Answer("красный");
		answerDao.persist(answerRed);

		Answer answerYellow = new Answer("желтый");
		answerDao.persist(answerYellow);

		Answer answerGreen = new Answer("зеленый");
		answerDao.persist(answerGreen);

		Answer answerBlue = new Answer("синий");
		answerDao.persist(answerBlue);

		Answer answerCyan = new Answer("голубой");
		answerDao.persist(answerCyan);

		Answer answerGray = new Answer("серый");
		answerDao.persist(answerGray);

		Answer answerPink = new Answer("розовый");
		answerDao.persist(answerPink);

		Answer answerOrange = new Answer("оранжевый");
		answerDao.persist(answerOrange);

		Question q1 = new Question("Какой цвет солнца?", answerYellow);
		questionDao.persist(q1);

		Question q2 = new Question("Какой цвет неба в хорошую погоду?", answerCyan);
		questionDao.persist(q2);

		Question q3 = new Question("Какой цвет травы?", answerGreen);
		questionDao.persist(q3);

		Question q4 = new Question("Какой цвет песка?", answerYellow);
		questionDao.persist(q4);

		Question q5 = new Question("Какой цвет цемента?", answerGray);
		questionDao.persist(q5);

		Question q6 = new Question("Какой цвет лепестков ромашки?", answerWhite);
		questionDao.persist(q6);

		Question q7 = new Question("Какой цвет листьев на деревьях весной?", answerGreen);
		questionDao.persist(q7);

		Question q8 = new Question("Какой цвет листьев на деревьях осенью?", answerYellow);
		questionDao.persist(q8);

		Question q9 = new Question("Какой цвет неба ночью?", answerBlack);
		questionDao.persist(q9);
	}

}
