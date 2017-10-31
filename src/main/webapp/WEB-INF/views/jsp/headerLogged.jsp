<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SISound</title>
 <link rel="stylesheet" type="text/css" href="<c:url value="css/header.css" />" />
<%--  <link rel="stylesheet" type="text/css" href = "<c:url value="css/logReg.css" />" /> --%>

</head>
<body>
	<nav class = "nav">
		<a href="index">
 			<img class = "logoHeader"src="<c:url value="img/logo.png"/>">
		</a>
		
<!-- 		<form action="searchSong" method="get" class="form"> -->
			<input id="input" onkeyup="getSongs()" type="text" placeholder="Search" class="header-input desktop-only" />
<!-- 		</form> -->
		
<!-- 		<a class = headerText href="loginPage">Profile</a> -->

		<a class = "headerTextLogged" href="index">Home</a>
		
		<div class="dropdown">
			<table>
				<tr>
					<c:if test="${sessionUser.profilPicture != null}">
						<td><img class="profilePic" alt="profilePic" src="getPicHeader" ></td>
						<td class="usernameTd"><span class="profileButton"><c:out value="${ sessionUser.username }"></c:out></span></td>
			 		</c:if>
				</tr>
				<tr>
					<c:if test="${sessionUser.profilPicture == null}">
						<td><img class="profilePic" alt="defaultProfilPic" src="<c:url value="img/defaultProfile.png" />"></td>

						<td class="usernameTd"><span class="profileButton"><c:out value="${ sessionUser.username }"></c:out></span></td>

			  		</c:if>
				</tr>
			</table>
			 <div class="dropdown-content">
			    <a href="profile${sessionUser.username }" >Profile</a>
			    <a href="	uploadPage">Upload</a>
			    <form action="logout" method="post">
			 		<div id="signOutDiv"><input id="signOut" type="submit" value="Sign Out"></div>
			    </form>
			 </div>
		</div>
	</nav>
		
<!--   <div class="fullscreen-bg"></div> -->

</body>
</html>