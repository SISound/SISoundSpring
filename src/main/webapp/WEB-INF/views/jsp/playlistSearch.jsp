<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
<title>SISound</title>
</head>
<body>
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	<table>
			<c:forEach items="${searchedPlaylists}" var="playlist">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile" class="${playlist.user.username}">
					      	<c:if test="${user.profilPicture != null}">
								<div class="main-image"><img src="getPic${playlist.user.username }" alt="Camera" style="width:60px;height:60px;">

						        </div>
				 			</c:if>
					        <c:if test="${user.profilPicture == null}">
								<div class="main-image"><img src="img/defaultProfile.png" alt="Camera" style="width:60px;height:60px;">
						        </div>
				 			</c:if>
						        <a class="link-title" href="playlist?id=${playlist.id }"><c:out value="${ playlist.title }"></c:out></a>
						        <a class="link-excerpt" href="profile${playlist.user.username }"><c:out value="${playlist.user.username }"></c:out></a>
<%-- 					        <c:if test="${ user.username != sessionUser.username}"> --%>
<%-- 					        		<button class="followButton" id="followButton" value="${ song.user.username }" onclick="followUnfollow(this.value)">Follow</button> --%>
<%-- 					        </c:if>		 --%>
					      </div>
					     
					    </li>
					  </ol>
					</nav>
				</tr>
				<br><br><br><br><br>
			</c:forEach>
		</table>
<%-- 		<jsp:include page="footer.jsp"></jsp:include> --%>
</body>
</html>