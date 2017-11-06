<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SISound</title>
		<link rel="stylesheet" type="text/css" href="<c:url value="css/comments.css" />" />
	</head>
	
	<script type="text/javascript">
		//reply comment
		function showDiv(id) {
			var newid = "r" + id;
		    var x = document.getElementById(newid);
		    if (x.style.display === "none") {
		        x.style.display = "block";
		    } else {
		        x.style.display = "none";
		    }
		}
		
		//like/unlike
	    function handleLike(id, isSong){
	        var button = document.getElementById(id);
	        var value=button.value;
	        var title = button.innerHTML;
	        if(title =="Like"){
	            likeComment(value, id, isSong);
	        }
	        else{
	            unlikeComment(value, id, isSong);
	        }
	    }
	
	    function likeComment(value, id, isSong) {
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
	                alert("Sorry, you have to log in to like this song!");
	            }
	                
	        }
	        request.open("post", "restLikeComment?commentId=" + value + "&isSong=" + isSong, true);
	        request.send();
	    }
	    
	    function unlikeComment(value, id, isSong) {
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
	            else if (this.readyState == 4 && this.status == 401) {
	                    alert("Sorry, you have to log in to unlike this song!");
	                }
	        }
	        request.open("post", "restUnlikeComment?commentId=" + value + "&isSong=" + isSong, true);
	        request.send();
	    }
		
		//dislike/undislike
		function handleDislike(id, isSong) {
			var button = document.getElementById(id);
			var value = button.value;
			var title = button.innerHTML;
			if(title =="Dislike"){
				dislikeComment(value, id, isSong);
			}
			else{
				undislikeComment(value, id, isSong);
			}
		}
		
		function dislikeComment(value, id, isSong) {
			var request = new XMLHttpRequest();
			var likedId = "l" + value;
			
			request.onreadystatechange = function() {
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
				else if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you have to log in to dislike this song!");
				}
					
			}
			 request.open("post", "restDislikeComment?commentId=" + value + "&isSong=" + isSong, true);
			request.send();
		}
		
		function undislikeComment(value, id, isSong){
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
					alert("Sorry, you have to log in to undislike this song!");
				}
					
			}
			request.open("post", "restUndislikeComment?commentId=" + value + "&isSong=" + isSong, true);
			request.send();
		}
	</script>	
	
	<body>
	
		<div class="wrapper">
			<div class="commentBoxfloat">
			  <form id="cmnt" action = "addComment?id=${ commentable.id }&song=${commentable.isSong}" method = POST>
			    <fieldset>
			      <div class="form_grp">
			        <label>comment</label>
			        <textarea name="commentText" placeholder="Write your comment here."></textarea>        
			      </div>
			      <div class="form_grp">
			      <button type="submit" >Submit</button>
			      </div>
			    </fieldset>
			  </form>  
			</div>
		</div>
		

			<c:forEach items="${ commentable.comments }" var="comment">
				<div class="comment">
					<img src="getPic${ comment.user }" class="comment-avatar">
		            <a class="comment-author" href="profile${ comment.user }"><c:out value="${ comment.user }"></c:out></a>
		            <div class="comment-text">
		                <c:out value="${ comment.text }"></c:out>
		                <div class="comment-date"> <c:out value="${ comment.date }"></c:out> </div>
		            </div>
		            <button class="comment-reply" id="${ comment.id }" value="${ comment.id }" onclick="showDiv(this.id)">reply</button>
		            
<!-- 		            if is song -->
		            <c:if test="${ commentable.isSong }">
<!-- 		            	likes -->
		            	<c:if test="${ sessionUser.likedSongComments[comment.id] }">
							<button class="comment-like" id="l${ comment.id }" value="${comment.id }" onclick="handleLike( this.id, true )">Unlike</button>
						</c:if>
						<c:if test="${ !sessionUser.likedSongComments[comment.id] }">
							<button class="comment-like" id="l${comment.id }" value="${comment.id }" onclick="handleLike( this.id, true )">Like</button>
						</c:if>	
						
		            	<input id="lcnt${ comment.id }" type="number" min="0" onkeydown="return false" value="${ comment.likesCount }">
<!-- 		            	dislikes -->
		            	<c:if test="${ sessionUser.dislikedSongComments[comment.id] }">
							<button class="comment-dislike" id="d${ comment.id }" value="${comment.id }" onclick="handleDislike( this.id, true )">Undislike</button>
						</c:if>
						<c:if test="${ !sessionUser.dislikedSongComments[comment.id] }">
							<button class="comment-dislike" id="d${comment.id }" value="${comment.id }" onclick="handleDislike( this.id, true )">Dislike</button>
						</c:if>	
		            	   
		            	 <input class="comment-dislike" id="dcnt${ comment.id }" type="number" min="0" onkeydown="return false" value="${ comment.dislikesCount }">
		            	   
		            </c:if>
		            
<!-- 		            if is playlist -->
		            <c:if test="${ !commentable.isSong }">
<!-- 		            	likes -->
		            	<c:if test="${ sessionUser.likedPlaylistComments[comment.id] }">
							<button class="comment-like" id="l${ comment.id }" value="${comment.id }" onclick="handleLike( this.id, false )">Unlike</button>
						</c:if>
						<c:if test="${ !sessionUser.likedPlaylistComments[comment.id] }">
							<button class="comment-like" id="l${comment.id }" value="${comment.id }" onclick="handleLike( this.id, false )">Like</button>
						</c:if>	
						
		            	<input id="lcnt${ comment.id }" type="number" min="0" onkeydown="return false" value="${ comment.likesCount }">
<!-- 		            	dislikes -->
		            	<c:if test="${ sessionUser.dislikedPlaylistComments[comment.id] }">
							<button class="comment-dislike" id="d${ comment.id }" value="${comment.id }" onclick="handleDislike( this.id, false )">Undislike</button>
						</c:if>
						<c:if test="${ !sessionUser.dislikedPlaylistComments[comment.id] }">
							<button class="comment-dislike" id="d${comment.id }" value="${comment.id }" onclick="handleDislike( this.id, false )">Dislike</button>
						</c:if>	
		            	   
		            	 <input id="dcnt${comment.id }" type="number" min="0" onkeydown="return false" value="${comment.dislikesCount }">

		            </c:if>
		           
	        	</div>
	        	
	      		<div  id="r${ comment.id }" style="display: none;" class="wrapper">
					<div class="commentBoxfloat">
						 <form id="cmnt" action = "addSubComment?id=${ commentable.id }&song=${commentable.isSong}&parent=${comment.id}" method = POST>
						   <fieldset>
						     <div class="form_grp">
						       <label>reply <c:out value="${ comment.user }"></c:out></label>
						       <textarea name="commentText" placeholder="Write your reply here."></textarea>        
						     </div>
						     <div class="form_grp">
						     <button type="submit" >Submit</button>
						     </div>
						   </fieldset>
						 </form>  
					</div>
				</div>
	        	
	        	<c:forEach items="${ comment.subcoments }" var="subcomment">
		        	<div class="commentreplyfield">
		        		<img src="getPic${ subcomment.user }" class="comment-avatar">
			            <a class="comment-author" href="profile${ subcomment.user }"><c:out value="${ subcomment.user }"></c:out></a>
			            <div class="comment-text">
			                <c:out value="${ subcomment.text }"></c:out>
			                <div class="comment-date"> <c:out value="${ subcomment.date }"></c:out> </div>
			            </div>
			            <button class="comment-like" id="${ comment.id }" value="${ comment.id }" onclick="showDiv(this.id)">like</button>
		           		 <button class="comment-dislike" id="${ comment.id }" value="${ comment.id }" onclick="showDiv(this.id)">dislike</button>
	       			 </div>
	        	</c:forEach>
			</c:forEach>     
        
	</body>
</html>