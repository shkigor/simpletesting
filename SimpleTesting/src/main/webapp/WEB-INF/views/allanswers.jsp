<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список ответов</title>
 
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
 
</head>
 
 
<body>
    <h2>Список ответов</h2>  
    <table>
        <tr>
            <td>id</td><td>Ответ</td>
        </tr>
        <c:forEach items="${answers}" var="answer">
            <tr>
            <td>${answer.id}</td>
            <td>${answer.answer}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    
<p><a href="<c:url value="/" />">На главную</a></p>
</body>
</html>