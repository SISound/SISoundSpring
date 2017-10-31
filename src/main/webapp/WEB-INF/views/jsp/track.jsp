<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
<!-- 		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
	<script type="text/javascript" src=" <c:url value="https://code.jquery.com/jquery-3.2.1.min.js" />"></script>

<!-- https://code.jquery.com/jquery-3.2.1.min.js -->
			<link  rel="stylesheet" type="text/css" href="<c:url value="https://p.typekit.net/p.gif?s=1&k=yyw5dsj&app=typekit&ht=tk&h=www.flagstar.com&f=6846.6848.6849.6851.6852&a=1922000&sl=195&fl=122&js=1.14.8&_=1458594264430" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/profile.css" />" />
<%-- 		<script src="<c:url value="/js/profile.js" />" type ="text/javascript"></script> --%>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/profile.js"></script> --%>

		<title>SISound</title>
		
				
	</head>
<body>
<%-- 	<c:set value="<%= session.getAttribute("user") %>" var="user"></c:set> --%>
	
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	<br>
	<br>
	<br>
	<br>
	
<div class = profileASD>

		ADD MUSIC PLAYER<br>
	<div class="banner">
<%-- 		<c:if test="${ modelUser.coverPhoto == null}"> --%>
<%-- 			<img class="banner containter" src="<c:url value="img/cover.jpg"/>">					 --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${ modelUser.coverPhoto != null}"> --%>
<!-- 			<img class="banner" alt="profilePic" src="getPicCover"> -->
<%-- 		</c:if> --%>

		<div class="container">
			<div class="profile-pic">
				<div class="avatar">
					<c:if test="${ modelUser.profilPicture == null}">
						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					
					</c:if>
					<c:if test="${ modelUser.profilPicture != null}">
						<img class="avatar" alt="profilePic" src="getPicProfile">
					</c:if>
				</div>
				<a href="profile${song.user.username }" class="button button-primary mt-20"><c:out value="${ modelUser.username }"></c:out></a>

			</div>
			<div class="bio">
				<h1 class="heading-medium"> <c:out value="${ song.title }"></c:out> </h1>
				<h3 class="heading-small"> Genre: <c:out value="${ song.genre }"></c:out>  </h3>
				<h5 class="heading-small"> Uploaded: <c:out value="${ song.uploadDate }"></c:out> </h5>
				<p class="body-small"> ADD WORKING BUTTONS </p>
			</div>
		</div>
	</div>
	COMMENTS
	
	</div>
	
</body>
</html>