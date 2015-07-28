package ck.solo.simpletesting.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ck.solo.simpletesting.helpers.Helpers;
import ck.solo.simpletesting.model.Answer;

@Repository("answerDao")
public class AnswerDaoImpl extends AbstractDao<Integer, Answer>implements AnswerDao {

	private static final Logger logger = LoggerFactory.getLogger(AnswerDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> findAllAnswers() {
		Criteria criteria = createEntityCriteria();
		return (List<Answer>) criteria.list();
	}

	public List<Answer> findRandomAnswers() {
		List<Answer> listAllAnswers = findAllAnswers();
		Collections.shuffle(listAllAnswers);

		return listAllAnswers.subList(0, Helpers.randInt(2, 6));
	}

	public List<Answer> findCorrectAndRandomAnswers(Answer correctAnswer, int questionCountMin, int questionCountMax) {

		List<Answer> listAllAnswers = findAllAnswers();
		Collections.shuffle(listAllAnswers);

		LinkedList<Answer> subListAnswers = new LinkedList<Answer>(listAllAnswers.subList(0, Helpers.randInt(questionCountMin, questionCountMax)));

		if (!subListAnswers.contains(correctAnswer)) {
			logger.info("subListAnswers - {}. NO contains - {}", subListAnswers, correctAnswer);
			subListAnswers.removeLast();
			subListAnswers.add(correctAnswer);
			Collections.shuffle(subListAnswers);
		}

		return subListAnswers;
	}

}
