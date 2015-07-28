<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Тест знаний</title>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>
<script type="text/javascript">

var prefix = '${pageContext.request.contextPath}';

function doTest() {
	window.location.href = prefix + '/test/start';
}

function doFillDB() {
     $.post( prefix + '/fillDB', function() {
       $('#fill').text('БД успешно заполнена!');
     })
       .fail(function() {
         alert( "error" );
       });
}

</script>

</head>
<body>



<p><a href="<c:url value="/questions"/>">Список вопросов</a></p>

<p><a href="answers">Список ответов</a></p>
<%--<p><a href="test1">Начать тест</a></p>--%>

<button type="button" onclick="doFillDB()">Заполнить данными БД</button>
<div id="fill"></div>
<br />
<button type="button" onclick="doTest()">Начать тест</button>

</body>
</html>