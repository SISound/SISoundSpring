<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>"/>
	<title>SISound</title>
	
	<script type="text/javascript">
			var MAX_FILE_SIZE = 195000000000;
			// getElementById shorthand
			function $id(id) {
				return document.getElementById(id);
			}
			// replace default text with file info
			function Output(string, where) {
				var m = document.querySelector(where);
				m.innerHTML = string;  
			}
			// attach events
			function Init() {
				var filefield = $id("filefield"),		
					submitbutton = $id("submit");
		
				// file select
				filefield.addEventListener("change", FileSelectHandler, false);
		
				// is XHR2 available?
				var xhr = new XMLHttpRequest();
				if (xhr.upload) {	
					// file drop
					filefield.addEventListener("dragover", FileDragHover, false);
					filefield.addEventListener("dragleave", FileDragHover, false);
					filefield.addEventListener("drop", FileSelectHandler, false);
					filefield.style.display = "block";		
					//submitbutton.style.display = "none";
				}
			}
			if (window.File && window.FileList && window.FileReader) {
				window.onload = function() {
			    Init();
			  }
			}
			function FileDragHover(e) {
				e.stopPropagation();
				e.preventDefault();
				e.target.className = (e.type == "dragover" ? "hover" : "");
			}
			function FileSelectHandler(e) {
				// cancel event and hover styling
				FileDragHover(e);
				// fetch FileList object
				var files = e.target.files || e.dataTransfer.files;  
				// process all File objects  
			  if(files.length>0){
			    for(var i=0; i<files.length; i++){
			      ParseFile(files[i], "#filefield label");
			    }
			  }
			}
			function ParseFile(file, where) {
				Output(
					"<p>File name: <strong>" + file.name +
					"<br></strong> type: <strong>" + file.type +
					"<br></strong> size: <strong>" + ((file.size > MAX_FILE_SIZE) ? file.size+" <span class=\"warning\"></span>" : file.size) +
					"</strong> bytes</p>"    
				,where);
			  $id("submit").addEventListener("click", function(e){
			    var xhr = new XMLHttpRequest();
			    if (xhr.upload && file.size <= MAX_FILE_SIZE) {
			      var uploadbutton = $id("submit");
			      xhr.upload.addEventListener("progress", function(e) {
			        uploadbutton.disabled = true;
			        var pc = parseInt((e.loaded / (e.total / 100)));
			        var offset = uploadbutton.offsetWidth - uploadbutton.offsetWidth*(pc/100);
			        uploadbutton.style.boxShadow = "-"+offset+"px 0 0 rgb(51, 153, 137) inset";
			      }, false);
			      xhr.onreadystatechange = function(e) {
			        if (xhr.readyState == 4) {
			          uploadbutton.value = (xhr.status == 200 ? "Completed" : "LOL Nope");
			          setTimeout(function(){
			            //window.location = "http://www.yoururl.com" //redirect to next page
			            console.log("Pretend that you were redirected");
			          }, 2000);
			        }
			      }
			      var formData = new FormData($id("upload"));
			      formData.append("file", file);
			      xhr.open("POST", $id("upload").action, true);
			      xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
			      xhr.send(formData);
			    }
			  });  
			}
	</script>
</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<c:if test="${empty sessionScope.user }">
				<c:redirect url="index"></c:redirect>
		</c:if>
		
<!-- 			<select name="genre"> -->
<%--    							 <option  value="null"><c:out value="---------------"></c:out></option> --%>
<%--       					<c:forEach items="${genres }" var="genre"> --%>
<%--    							 <option  value="${genre.key}"><c:out value="${genre.key }"></c:out></option> --%>
<%--       					</c:forEach> --%>
<!--   			</select> -->
<!-- 		<div id="uploadDiv"> -->
<!-- 			<form id="uploadForm" action="saveSong" method="post" enctype="multipart/form-data"> -->
<!-- 			    <input class="uploader" type="file" name="song" accept="audio/*"/></br> -->
<!-- 			    <input class="uploader" type="submit" /></br> -->
			    
<!-- 			    <select name="genre"> -->
<%--    							 <option  value="null"><c:out value="---------------"></c:out></option> --%>
<%--       					<c:forEach items="${genres }" var="genre"> --%>
<%--    							 <option  value="${genre.key}"><c:out value="${genre.key }"></c:out></option> --%>
<%--       					</c:forEach> --%>
<!--   			</select> -->
<!-- 			</form> -->
			
<!-- 		</div> -->

			<form class="upload-form" enctype="multipart/form-data" action="saveSong" id="upload" method="post">
			    <fieldset id="titlefield"><label for="title"> <h3>Share your sounds</h3></label>
			    	<div class="grid-35">
            			<label for="qualification">Genre</label>
        		 	</div>
          			<div class="grid-65">
           				<select name="genre">
           						 <option  value="null"><c:out value="---------------"></c:out></option>
           					<c:forEach items="${genres }" var="genre">
           						 <option  value="${genre.key}"><c:out value="${genre.key }"></c:out></option>
           					</c:forEach>
  						</select>
          			</div>
			    </fieldset>
			    <fieldset id="filefield"><label for="file"><h4>Drag your file here or click this area</h4><footer id="controls"></footer></label>
			    	<input type="file" name="song" id="file" accept="audio/*"/>
			    </fieldset>
			    <input type="submit" value="Upload!" id="submit" />
			</form>
	</body>
</html>