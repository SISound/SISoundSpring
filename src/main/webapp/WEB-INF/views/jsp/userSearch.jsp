<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
		<title>SISound</title>
				
		<script type="text/javascript">
		
		
		</script>
	</head>
	
	<body id="mainBody">
				
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
<!-- 		<table class="sortingTable"> -->
<!-- 			<tr> -->
<!-- 				<td class="sortTd">Sort by: |</td> -->
<!-- 				<td><a class="sortingLink" href="sortSongs/likes">Likes </a>|</td> -->
<!-- 				<td><a class="sortingLink" href="sortSongs/date">UploadDate </a>|</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->

		<span class="searching"><c:out value="Results for searching users, containing ${ searched }:"></c:out></span>
		
		<table>
			<c:forEach items="${searchedUsers}" var="user">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile" class="${user.username}">
					      	<c:if test="${user.profilPicture != null}">
								<div class="main-image"><img src="getPic${user.username }" alt="Camera" style="width:60px;height:60px;">

						        </div>
				 			</c:if>
					        <c:if test="${user.profilPicture == null}">
								<div class="main-image"><img src="img/defaultProfile.png" alt="Camera" style="width:60px;height:60px;">
						        </div>
				 			</c:if>
						        <a class="link-title" href="profile${user.username }"><c:out value="${ user.name }"></c:out></a>
						        <a class="link-excerpt" href="profile${user.username }"><c:out value="${user.username }"></c:out></a>
					        <c:if test="${ user.username != sessionUser.username}">
					        		<button class="followButton" id="followButton" value="${ song.user.username }" onclick="followUnfollow(this.value)">Follow</button>
					        </c:if>		
					      </div>
					     
					    </li>
					  </ol>
					</nav>
				</tr>
				<br><br><br><br><br>
			</c:forEach>
		</table>
	</body>
</html>	