<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Тестовый вопрос</title>

    <style>
        li {
    		list-style-type: none; /* Убираем маркеры */
   		}
   		ul {
    		margin-left: 0; /* Отступ слева в браузере IE и Opera */
    		padding-left: 0; /* Отступ слева в браузере Firefox, Safari, Chrome */
   		}
    </style>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js">
</script>

<script type="text/javascript">


var testQuestionId;
var prefix_ajax = '${pageContext.request.contextPath}/ajax';
var prefix_answersresult = '${pageContext.request.contextPath}/answersresult';
var question_timer=0;
var timer=null;


	function getSelectedAnswerId() { 
		var selectedValue = $('input[name=answer_id]:checked').val();
		if (typeof selectedValue === "undefined") {
			return -1;
		} else {
			return selectedValue;
		}
	}
	
	function getCurrentQuestionId() { 
		if (typeof testQuestionId === "undefined") {
			return -1;
		} else {
			return testQuestionId;
		}
	}
	

    
	    var testNextQuestion = function() {
	    	
	    	stopTimer();
	    	
	        $.ajax({
	            type: 'POST',
	            url:  prefix_ajax,
	            data: {
	                "currentQuestionId": getCurrentQuestionId(),
	                "currentAnswerId": getSelectedAnswerId()
	            },
	            dataType: 'json',
	            async: true,
	            success: function(result) {
	            	
	                if (result.testQuestion == null) {
	                	window.location.href = prefix_answersresult;
	                }
	                
	                $('#question_text').text(result.testQuestion.question);
	                testQuestionId = result.testQuestion.id;
	                
	                
	                var answers=$('#answers');
	                answers.empty();
	                for(var i=0; i < result.testAnswers.length; i++) {
	                    var a=result.testAnswers[i];
	                    
	                    var inp=$('<input type="radio" name="answer_id" value="'+a.id+'"/>');
	                    var label=$('<label/>').text(" "+a.answer).prepend(inp);            
	                    var li=$('<li/>').append( $('<div/>').append(label) );

	                    answers.append(li);
	                }
	                
	                
	                if(result.testQuestionTimer && result.testQuestionTimer>0) {
	                    $('#timer').show();
	                    question_timer=Math.round((new Date()).getTime()/1000)+ result.testQuestionTimer;
	                    updateTimer();
	                    setupTimer();
	                } else {
	                    $('#timer').hide();
	                }
	                
	                
	            },
	            error: function(jqXHR, textStatus, errorThrown) {
	                alert(jqXHR.status + ' ' + jqXHR.responseText);
	            }
	        });
	    }
	    
	    
	    
	    
	    
	    function setupTimer() {
	        timer=setInterval(function() {
	            updateTimer();
	            if(Math.round((new Date()).getTime()/1000)>=question_timer) {
	                clearInterval(timer);
	                timer=null;
	                // alert('Время истекло');
	                testNextQuestion();
	            }
	        },1000);
	    }

	    function updateTimer() {
	        var t=$('#timer');
	        if(question_timer>0) {
	            var val=question_timer-Math.round((new Date()).getTime()/1000);
	            if(val<0) { val=0; }
	            
	            var sec=val%60;
	            if(sec<10) { sec='0'+sec;  }
	            t.text(Math.floor(val/60)+':'+sec);
	        } else {
	            t.text('');
	        }
	    }

	    function stopTimer() {
	        if(timer!==null) {
	            clearInterval(timer);
	            timer=null;
	            question_timer=0;
	            updateTimer();
	        }
	    }
	
	    
	    testNextQuestion();
</script>

</head>

<body>


<div id="timer">0:00</div>


  	<p id="question_text"></p>
    <ul id="answers"></ul>
	<button type="button" onclick="testNextQuestion()">Следующий вопрос</button>
	
</body>
</html>