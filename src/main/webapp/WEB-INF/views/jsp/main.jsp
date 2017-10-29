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
			function followUnfollow(value) {
				var button = document.getElementById("followButton");
				var title = button.innerHTML;
				var followed=value;
				
				if(title=="Follow"){
					followUser(followed);
				}
				else{
					unfollowUser(followed);
				}
			}
			
			function followUser(followed){
				var request = new XMLHttpRequest();
				var fwd=followed;
				request.onreadystatechange = function() {
					//when response is received
					if (this.readyState == 4 && this.status == 200) {
						var button = document.getElementById("followButton");
						button.innerHTML = "Unfollow";
						button.style.background='rgba(92,168,214,0.9)';
					}
					else
						if (this.readyState == 4 && this.status == 401) {
							alert("Sorry, you must log in to like this video!");
						}
				}
				request.open("post", "followUser?followed="+followed, true);
				request.send();
			}
			
			function unfollowUser(followed) {
				var request = new XMLHttpRequest();
				var fwd=followed;
				request.onreadystatechange = function() {
					//when response is received
					if (this.readyState == 4 && this.status == 200) {
						var button = document.getElementById("followButton");
						button.innerHTML = "Follow";
						button.style.background='white';
					}
					else
						if (this.readyState == 4 && this.status == 401) {
							alert("Sorry, you must log in to like this video!");
						}
				}
				request.open("post", "unfollowUser?followed="+followed, true);
				request.send();
			}
		</script>
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
					        <c:if test="${ song.user.username != sessionUser.username}">
					        	 <button class="followButton" id="followButton" value="${ song.user.username }" onclick="followUnfollow(this.value)">Follow</button>
					        </c:if>
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