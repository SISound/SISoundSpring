<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
		<title>SISound</title>
	</head>
	
	<body>
		<header id="loginHeader">
		
		</header>
		
		<div id="registerDiv">
			<form action="registerUser" method="POST">
				<div id="regDiv">
					<c:if test="${ requestScope.error == null }">
						<a class=regMessage>Please register an account</a>
					</c:if>
					<c:if test="${ requestScope.error != null }">
						<a class=regMessage>Sorry, registration unsuccessfull. Reason: ${ requestScope.error }</a>
					</c:if>
					<f:form commandName="user" >
							<f:input class="login" type="text" placeholder="Username" path="username" required=""/><br>
							<f:input class="login" type="password" placeholder="Password" path="password" required=""/><br>
							<input class="login" type="password" placeholder="Repeat Password" required="" name="password2"/><br>
							<f:input class="login" type="email" placeholder="e-mail" path="email" required=""/><br>
					</f:form>
							<input id="rb" type="submit" value="" style="background-image: url('img/signup.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
				</div>
			</form>	
			<a class="logLink" href="loginPage" name="regLink">Already have an account? Please login here.</a>
		</div>
	</body>
</html>