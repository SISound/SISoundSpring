<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SISound</title>
 <link rel="stylesheet" type="text/css" href = "<c:url value="css/logReg.css" />" />
</head>
<body>

	<jsp:include page="headerNotLogged.jsp"></jsp:include>
	
 	<div>	
			<input type="radio" checked id="toggle--login" name="toggle" class="ghost" />
		  <input type="radio" id="toggle--signup" name="toggle" class="ghost" />
		
<!-- 		  <img class="logo framed" src="https://www.tumblr.com/images/logo/logo_large.png?v=7ea0eb57dd627a95f82be5bde0c43d59" alt="Tumblr logo" /> -->
		
		
		  <form class="form form--login framed" action = "loginUser" method = POST>
		    <input type="text" placeholder="Username" class="input input--top" name = "username"/>
		    <input type="password" placeholder="Password" class="input" name = "password" />
		    <input type="submit" class="input input--submit" />
		    
		    <label for="toggle--signup" class="text text--small text--centered">New? <b>Sign up</b></label>
		  </form>
		  
		
		  <form class="form form--signup framed" action = "registerUser" method = POST>
		 		
		    <input type="email" placeholder="Email" class="input input--top" />
		    <input type="password" placeholder="Password" class="input" />
		    <input type="text" placeholder="Username" class="input" />
		    <input type="submit" value="Sign up" class="input input--submit" />
    		
    		<label for="toggle--login" class="text text--small text--centered">Not new? <b>Log in</b></label>
		  </form>
    </div>
</body>
</html>