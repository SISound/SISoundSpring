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
	</script>	
	
	<body>
	
		<div class="wrapper">
			<div class="commentBoxfloat">
			  <form id="cmnt" action = "addComment?id=${ playlist.id }&song=${playlist.isSong}" method = POST>
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
		

			<c:forEach items="${ playlist.comments }" var="comment">
				<div class="comment">
					<img src="getPic${ comment.user }" class="comment-avatar">
		            <a class="comment-author" href="profile${ comment.user }"><c:out value="${ comment.user }"></c:out></a>
		            <div class="comment-text">
		                <c:out value="${ comment.text }"></c:out>
		                <div class="comment-date"> <c:out value="${ comment.date }"></c:out> </div>
		            </div>
		            <button class="comment-reply" id="${ comment.id }" value="${ comment.id }" onclick="showDiv(this.id)">reply</button>
<!-- 		            <a onclick="showDiv() class="comment-reply"></a> -->
	        	</div>
	        	
	      		<div  id="r${ comment.id }" style="display: none;" class="wrapper">
					<div class="commentBoxfloat">
						 <form id="cmnt" action = "addSubComment?id=${ playlist.id }&song=${playlist.isSong}&parent=${comment.id}" method = POST>
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
	       			 </div>
	        	</c:forEach>
			</c:forEach>     
        
	</body>
</html>