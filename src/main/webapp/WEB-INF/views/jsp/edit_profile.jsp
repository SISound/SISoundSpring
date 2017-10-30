<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css" />"/>
		<title>SISound</title>
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="css/editProfile.css" />" />
		
		<script type="text/javascript" src=" <c:url value="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js" />"></script>
		<script type="text/javascript" src=" <c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" />"></script>
		
		
		
	</head>
	<body>
	
	<jsp:include page="headerLogged.jsp"></jsp:include>
		
<div class="wrapper">
  <div class="profile">
    <div class="content">
      <h1>Edit Profile</h1>
      <form action="edit" method = POST enctype="multipart/form-data">
      	<f:form commandName="user" >
        <!-- Photo -->
        <fieldset>
          <div class="grid-35">
            <label for="avatar">Profile Picture</label>
          </div>
          <div class="grid-65">
            <span class="photo" title="Upload your Avatar!"></span>
            <input type="file" class="btn" name="profilepic" accept="image/*" />
          </div>
        </fieldset>
        
        <fieldset>
          <div class="grid-35">
          <br>
            <label for="fname">Cover Photo</label>
          </div>
          <div class="grid-65">
            <input type="file" class="btn" name="coverpic" accept="image/*"/>
          </div>
        </fieldset>
        
        <fieldset>
          <div class="grid-35">
            <label for="fname">First Name</label>
          </div>
          <div class="grid-65">
            <f:input type="text" id="fname" tabindex="1" path="firstName"/>
          </div>
        </fieldset>
        <fieldset>
          <div class="grid-35">
            <label for="lname">Last Name</label>
          </div>
          <div class="grid-65">
            <f:input type="text" id="lname" tabindex="2" path="lastName"/>
          </div>
        </fieldset>
        <!-- Description about User -->
        <fieldset>
          <div class="grid-35">
            <br>
            <label for="description">About you</label>
          </div>
          <div class="grid-65">
            <f:textarea name="" id="" cols="30" rows="auto" tabindex="3" path="bio"></f:textarea>
          </div>
        </fieldset>
        <!-- Location -->
        <fieldset>
          <div class="grid-35">
            <label for="location">City</label>
          </div>
          <div class="grid-65">
            <f:input type="text" id="location" tabindex="4"  path="city"/>
          </div>
        </fieldset>
        <!-- Country -->
        <fieldset>
          <div class="grid-35">
            <label for="country">Country</label>
          </div>
          <div class="grid-65">
            <input type="text" id="country" tabindex="5" />
          </div>
        </fieldset>
        <!-- Email -->
        <fieldset>
          <div class="grid-35">
            <label for="email">Email Address</label>
          </div>
          <div class="grid-65">
            <input type="email" id="email" tabindex="6" />
          </div>
        </fieldset>
        <!-- Highest Qualification -->
        <fieldset>
          <div class="grid-35">
            <label for="qualification">Highest level of Education</label>
          </div>
          <div class="grid-65">
            <select name="qualification" id="qualification" tabindex="8">
              <option selected="selected" value="--------------" disabled>--------------</option>
              <option value="primary_school">Primary school or equivalent</option>
              <option value="high_school">High school or equivalent</option>
              <option value="associate">Associate degree or equivalent</option>
              <option value="bachelors">Bachelor's degree or equivalent</option>
              <option value="post_graduate">Post-graduate or equivalent</option>
            </select>
          </div>
        </fieldset>
<%--         <fieldset> --%>
          <input type="submit" class="Btn" value="Save Changes"/>
<%--         </fieldset> --%>
		</f:form>
      </form>
        <form action="profile${sessionUser.username }">
          <input type="submit" class="Btn cancel" value="Cancel"/>
          </form>
    </div>
  </div>
</div>


<!-- http://www.smashingmagazine.com/2013/08/08/release-livestyle-css-live-reload/ -->	
		
		
	</body>
</html>