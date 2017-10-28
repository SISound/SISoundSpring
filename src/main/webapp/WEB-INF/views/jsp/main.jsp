<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
		<title>SISound</title>
	</head>
	
	<body id="mainBody">
				
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<table>
			<c:forEach items="${songs}" var="song">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile">
					      	<c:if test="${song.user.profilPicture != null}">
								<div class="main-image"><img src="${song.user.profilPicture}" alt="Camera" style="width:60px;height:60px;">
						          <img src="http://www.meetchaos.com/resources/images/camera.png" class="imtip" />
						        </div>
				 			</c:if>
					        <c:if test="${song.user.profilPicture == null}">
								<div class="main-image"><img src="img/defaultProfile.png" alt="Camera" style="width:60px;height:60px;">
						          <img src="http://www.meetchaos.com/resources/images/camera.png" class="imtip" />
						        </div>
				 			</c:if>
					        <a class="link-title" href="songPage"><c:out value="${ song.title }"></c:out></a>
					        <a class="link-excerpt" href="songPage"><c:out value="${ song.user.username }"></c:out></a>
					         Like Dislike Comment Share
					      </div>
					    </li>
					  </ol>
					</nav>
				</tr>
				<br/>
				<br/>
				<br/>
				<br/>
			</c:forEach>
		</table>
	</body>
</html>