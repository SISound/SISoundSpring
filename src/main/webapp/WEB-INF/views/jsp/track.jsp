<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<script type="text/javascript" src=" <c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" />"></script>
		<script type="text/javascript" src=" <c:url value="https://www.grandvincent-marion.fr/_codepen/jquery.fancybox.pack.js" />"></script>
		<script type="text/javascript" src=" <c:url value="https://www.grandvincent-marion.fr/_codepen/mediaelement-and-player.min.js" />"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value="css/musicPlayer.css" />" />

		<link  rel="stylesheet" type="text/css" href="<c:url value="https://p.typekit.net/p.gif?s=1&k=yyw5dsj&app=typekit&ht=tk&h=www.flagstar.com&f=6846.6848.6849.6851.6852&a=1922000&sl=195&fl=122&js=1.14.8&_=1458594264430" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/profile.css" />" />

		<title>SISound</title>

		<script>
			function likeSong() {
				var counter=document.getElementByClassName("likeButton");
				counter.button.style.background='red';
			}
		</script>
				
	</head>
<body>
	<script src="<c:url value="js/musicPlayer.js" />"  type ="text/javascript"></script>
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	<br>
	<br>
	<br>
	<br>
	
<div class = profileASD>

	<div class="contain">
		<div class="container">
		
			<div class="music-player">
				<div class="cover">
<!-- 					<img src="https://www.grandvincent-marion.fr/_codepen/kygo.png" alt=""> -->
					
					<c:if test="${ modelUser.profilPicture == null}">
						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					
					</c:if>
					<c:if test="${ modelUser.profilPicture != null}">
						<img class="avatar" alt="profilePic" src="getPic${ modelUser.username }">
					</c:if>
					
				</div>
				<div class="titre">
					<h3><c:out value="${ modelUser.username }"></c:out></h3>
					<h1><c:out value="${ modelSong.title }"></c:out></h1>
				</div>
				<div class="lecteur">
					<audio style="width: 100%;" class="fc-media fc-audio"><source src="getSong${modelSong.url}" type="audio/mp3"/></audio>
				</div>
			</div>
			
			
				<div class="banner">
			
			<div class="song-info">
				<h1 class="heading-medium"> <c:out value="${ modelSong.title }"></c:out> </h1>
				<h3 class="heading-small"> Genre: <c:out value="${ modelSong.genre }"></c:out>  </h3>
				<h5 class="heading-small"> Uploaded: <c:out value="${ modelSong.uploadDateOnly }"></c:out> </h5>
				<table class="actionsTable">
					<tr>
						<td><button class="likeButton" onclick="likeSong()">&#10084Like</button></td>
						<td class="counterTd" id="likeCount"><c:out value="${modelSong.likesCount }"></c:out></td>
						<td><button class="dislikeButton">&#128078Dislike</button></td>
						<td class="counterTd" id="dislikeCount"><c:out value="${modelSong.dislikesCount }"></c:out></td>
					</tr>
				</table>
			</div>
			</div>
			
		</div>
	</div>
	
	<div class="banner">
<%-- 		<c:if test="${ modelUser.coverPhoto == null}"> --%>
<%-- 			<img class="banner containter" src="<c:url value="img/cover.jpg"/>">					 --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${ modelUser.coverPhoto != null}"> --%>
<!-- 			<img class="banner" alt="profilePic" src="getPicCover"> -->
<%-- 		</c:if> --%>

<!-- 		<div class="container"> -->
<!-- 			<div class="profile-pic"> -->
<!-- 				<div class="avatar"> -->
<%-- 					<c:if test="${ modelUser.profilPicture == null}"> --%>
<%-- 						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					 --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${ modelUser.profilPicture != null}"> --%>
<!-- 						<img class="avatar" alt="profilePic" src="getPicProfile"> -->
<%-- 					</c:if> --%>
<!-- 				</div> -->
<%-- 				<a href="profile${song.user.username }" class="button button-primary mt-20"><c:out value="${ modelUser.username }"></c:out></a> --%>

<!-- 			</div> -->
<!-- 			<div class="bio"> -->
<%-- 				<h1 class="heading-medium"> <c:out value="${ modelSong.title }"></c:out> </h1> --%>
<%-- 				<h3 class="heading-small"> Genre: <c:out value="${ modelSong.genre }"></c:out>  </h3> --%>
<%-- 				<h5 class="heading-small"> Uploaded: <c:out value="${ modelSong.uploadDateOnly }"></c:out> </h5> --%>
<!-- 				<p class="body-small"> ADD WORKING BUTTONS </p> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
	COMMENTS
	
	</div>
	
</body>
</html>