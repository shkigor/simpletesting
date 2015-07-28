<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Результаты тестирования</title>
</head>
<body>
<h3>Вы закончили тест!</h3>
<h4>Результаты теста:</h4>
	
	    <table border="1" cellpadding="5" cellspacing="0" width="50%">
        <tr>
            <th>Вопрос</th><th>Правильный ответ</th><th>Ваш ответ</th>
        </tr>
        <c:forEach var="item" items="${tests}">
            <tr>
            <td>${item.testQuestion.question}</td>
            <td align="center">${item.testQuestion.answer.answer}</td>
            <td align="center">
					<c:choose>
					    <c:when test="${item.testQuestion.answer eq item.testAnswerResult}">
							<div style="color : black">${item.testAnswerResult.answer}</div>
					    </c:when>    
					    <c:otherwise>
							<div style="color : red">${item.testAnswerResult.answer}</div>
					    </c:otherwise>
					</c:choose>
            </td>
            </tr>
        </c:forEach>
    </table>
	
	
<h3>Вы ответили верно на ${correctAnswers} вопросов из ${fn:length(tests)}</h3>
<p><a href="<c:url value="/test/start" />">Начать тест заново</a></p>
<p><a href="<c:url value="/" />">На главную</a></p>
</body>
</html>