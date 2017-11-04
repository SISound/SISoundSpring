<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />

	</head>
	
	<script type="text/javascript">
	
		//like/unlike
	    function handleLike(id){
	        var button = document.getElementById(id);
	        var value=button.value;
	        var title = button.innerHTML;
	        if(title =="Like"){
	            likeSong(value, id);
	        }
	        else{
	            unlikeSong(value, id);
	        }
	    }
	
	    function likeSong(value, id) {
	        var request = new XMLHttpRequest();
	        var dislikeId = "d" + value;

	        request.onreadystatechange = function() {
	            //when response is received
	            if (this.readyState == 4 && this.status == 200) {
	                var button1 = document.getElementById(id);
	                button1.innerHTML = "Unlike";
					var likeCntId = "lcnt" + value;
					var dislikeCntId = "dcnt" + value;
	                document.getElementById(likeCntId).value++;
	                if(document.getElementById(dislikeCntId).value>0){
	                    document.getElementById(dislikeCntId).value--;
	                }
	                var button = document.getElementById(dislikeId);
	                button.innerHTML = "Dislike";
	            }
	            else
	            if (this.readyState == 4 && this.status == 401) {
	                alert("Sorry, you must log in to like this song!");
	            }
	                
	        }
	        request.open("post", "restLikeSong?songId=" + value, true);
	        request.send();
	    }
	    
	    function unlikeSong(value, id) {
	        var request = new XMLHttpRequest();
	        request.onreadystatechange = function() {
	            //when response is received
	            if (this.readyState == 4 && this.status == 200) {
	                var button = document.getElementById(id);
	                button.innerHTML = "Like";
					var likeCntId = "lcnt" + value;
	                if(document.getElementById(likeCntId).value > 0){
	                    document.getElementById(likeCntId).value--;
	                }
	            }
	            else
	                if (this.readyState == 4 && this.status == 401) {
	                    alert("Sorry, you must log in to unlike this song!");
	                }
	        }
	        request.open("post", "restUnlikeSong?songId=" + value, true);
	        request.send();
	    }
		
		//dislike/undislike
		function handleDislike(id) {
			var button = document.getElementById(id);
			var value = button.value;
			var title = button.innerHTML;
			if(title =="Dislike"){
				dislikeSong(value, id);
			}
			else{
				undislikeSong(value, id);
			}
		}
		
		function dislikeSong(value, id) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
			var likedId = "l" + value;
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById(id);
					button.innerHTML = "Undislike";
					var likeCntId = "lcnt" + value;
					var dislikeCntId = "dcnt" + value;
					document.getElementById(dislikeCntId).value++;
					if(document.getElementById(likeCntId).value > 0){
						document.getElementById(likeCntId).value--;
					}
					var button=document.getElementById(likedId);
					button.innerHTML="Like";
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to dislike this song!");
				}
					
			}
			request.open("post", "restDislikeSong?songId=" + value, true);
			request.send();
		}
		
		function undislikeSong(value, id){
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById(id);
					button.innerHTML = "Dislike";
					var dislikeCntId = "dcnt" + value;
					if(document.getElementById(dislikeCntId).value > 0){
						document.getElementById(dislikeCntId).value--;
					}
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to undislike this song!");
				}
					
			}
			request.open("post", "restUndislikeSong?songId=" + value, true);
			request.send();
		}
		
		
		//DISABLE PLAYING MORE THAN 1 SONGS
		document.addEventListener('play', function(e){
		    var audios = document.getElementsByTagName('audio');
		    for(var i = 0, len = audios.length; i < len;i++){
		        if(audios[i] != e.target){
		            audios[i].pause();
		        }
		    }
		}, true);	
		
	</script>
	
	<body>

		
		<table>
			<c:forEach items="${ songsToShow }" var="songsToShow">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile" class="${ songsToShow.user.username }">
					      	
					      	<c:if test="${ songsToShow.user.profilPicture != null}">
								<class="main-image"><img src="getPic${ songsToShow.user.username }" class="main-image" alt="Camera" style="width:60px;height:60px;">
				 			</c:if>
					        <c:if test="${songsToShow.user.profilPicture == null}">
								<img src="img/defaultProfile.png" class="main-image" alt="Camera" style="width:60px;height:60px;">
				 			</c:if>
					        
					        <a class="link-title" href="track=${ songsToShow.id }"><c:out value="${ songsToShow.title }"></c:out></a>
					        <a class="link-excerpt" href="profile${ songsToShow.user.username }"><c:out value="${ songsToShow.user.username }"></c:out></a>
					        
					        <c:if test="${ songsToShow.user.username != sessionUser.username || !sessionUser.followedIds[songsToShow.user.userID] }">
					        	<form method = POST action="follow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}">
					        		<button class="followButton" >Follow</button>
					        	</form>
					        </c:if>
					        
					        <c:if test="${ sessionUser.followedIds[songsToShow.user.userID] }">
					        	<form method = POST action="unfollow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}">
					        		<button class="unfollowButton" >Unfollow</button>
					        	</form>
					        </c:if>
					        
					        	<form action="index">
							       	<button class="addToPlaylist" >&#8801 Add to playlist</button>
<%-- 					      		<button class="actionButton" id="shareButton" value="${ songsToShow.id }" >&#10609Share</button> --%>
								</form>
								
								<div class="likeDiv">
								
<!-- 									like/unlike -->
									<c:if test="${ sessionUser.likedSongs[songsToShow.id] }">
										<button class="actionButtonClicked" id="l${ songsToShow.id }" value="${songsToShow.id }" onclick="handleLike( this.id )">Unlike</button>
									</c:if>
									<c:if test="${ !sessionUser.likedSongs[songsToShow.id] }">
										<button class="actionButton" id="l${songsToShow.id }" value="${songsToShow.id }" onclick="handleLike( this.id )">Like</button>
									</c:if>
									<input id="lcnt${songsToShow.id }" type="number" min="0" onkeydown="return false" value="${ songsToShow.likesCount }">
									
<!-- 									dislike/undislike -->
									<c:if test="${sessionUser.dislikedSongs[songsToShow.id]}">
										<button class="actionButtonClicked" id="d${songsToShow.id }" value="${songsToShow.id }" onclick="handleDislike( this.id )">Undislike</button>
									</c:if>
									<c:if test="${!sessionUser.dislikedSongs[songsToShow.id]}">
										<button class="actionButton" id="d${songsToShow.id }" value="${songsToShow.id }" onclick="handleDislike( this.id )">Dislike</button>
									</c:if>
									<input id="dcnt${songsToShow.id }" type="number" min="0" onkeydown="return false" value="${modelSong.dislikesCount }">

									<button class="actionButton" >&#128172Comment</button>
								</div>
					        		
					      </div>
					      
					      <audio controls class="myPlayer">
							  <source src="getSong${songsToShow.url}" type="audio/mpeg">
						  </audio>	
					    </li>
					  </ol>
					</nav>
				</tr>
				<br><br><br><br><br><br><br>
			</c:forEach>
		</table>
				
		
	</body>
</html>