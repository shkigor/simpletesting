package ck.solo.simpletesting.helpers;

import ck.solo.simpletesting.model.Question;

public interface TestPerformer {

	Test nextTest(Question question);

}
