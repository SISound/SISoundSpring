<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SISound</title>
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://fonts.googleapis.com/css?family=Raleway:300,400,500,600,700,800" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://decorator.io/cdn/modulr.min.css" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://decorator.io/cdn/modules/tooltip.min.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/comments.css" />" />
	</head>
	<body>
		<!-- Wrapper Start -->  
		<div class="wrapper748 center padding-40 tablet-padding">
		  
		  <!-- Comment Form Start -->
		  <div class="row shadow padding-15 white">
		    
		    <form class="form wire teal">
		      <textarea placeholder="say something..."></textarea>
		    
		      <!-- Submit Start -->
		      <div class="row pull-left -margin padding-top-10">
		        <span class="btn fill-transparent text-gray hover-disable -padding-left">
		          500 characters left
		        </span>
		        <button class="btn teal pull-right">Submit</button>
		      </div>
		      <!-- Submit End -->
		    </form>
		    
		  </div>
		  <!-- Comment Form End -->
		  
		  <!-- Filter Start -->
		  <div class="row padding-top-15">
		    <ul class="filter filter-bottom border-1 teal">
		      <li class="relative"><a class="active" href="#">New </a>        <span class="label teal">21</span>
		      </li>
		      <li><a href="#">Oldest</a></li>
		      <li><a href="#">My Comments</a></li>
		    </ul>
		  </div>
		  <!-- Filter End -->
		  
		  <!-- Comments Start -->
		  <div class="row padding-top-20">
		    
		    <!-- A Comment Start -->
		    <div class="col-12 margin-bottom padding-15 fill-white shadow border border-1 solid teal padding-bottom-5">
		      
		      <!-- Comment Header Start -->
		      <div class="row">
		        <a href="#" class="thumbnail col-1 tablet-hide padding-5">
		          <img src="https://decorator.io/modulr/webroot/media/nouser.png" />
		        </a>
		        <div class="col-11 padding-5">
		          <p class="lh-12 padding-top-5">
		            <a href="#" class="text-teal text-strong hover-text-underline">Some Dude</a>
		          </p>
		          <p class="lh-12"><span>2 min ago</span></p>
		        </div>
		      </div>
		      <!-- Comment Header End -->
		      
		      <!-- Comment Content Start -->
		      <div class="row padding-5">
		        <p>
		         Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
		         Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
		        </p>
		      </div>
		      <!-- Comment Content End -->
		      
		      <!-- Comment Meta Start -->
		      <div class="row padding-5 border border-top border-1 solid silver margin-top">
		        
		        <!-- Voting Start -->
		        <a href="#" class="btn hover-fill-disable fill-transparent text-teal -padding-left">
		          <i class="fa fa-lg fa-angle-up"></i> 255
		        </a>
		        <a href="#" class="btn  hover-fill-disable fill-transparent text-red">
		          <i class="fa fa-lg fa-angle-down"></i> 13
		        </a>
		        <!-- Voting End -->
		        
		        <!-- Action Start -->
		        <div class="pull-right">
		          <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal">
		            <i class="fa fa-exclamation-triangle"></i> Report
		          </a>
		          <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal -padding-right">
		            <i class="fa fa-reply"></i> Reply
		          </a>
		        </div>
		        <!-- Actions End -->
		        
		      </div>
		      <!-- Comment Meta End -->
		      
		    </div>
		    <!-- A Comment End -->
		    
		    
		    <!-- Nested Comment Start -->
		    <div class="row flex margin-bottom">
		      
		      <!-- Offset Start -->
		      <div class="col-2 after"></div>
		      <!-- Offset End -->
		
		      <div class="col-10 margin-v padding-15 fill-white border shadow padding-bottom-5 before">
		
		        <!-- Comment Header Start -->
		        <div class="row">
		          <a href="#" class="thumbnail col-1 tablet-hide padding-5">
		            <img src="https://decorator.io/modulr/webroot/media/nouser.png" />
		          </a>
		          <div class="col-11 padding-5">
		            <p class="lh-12">
		              <a href="#" class="text-teal text-strong hover-text-underline">Some Dude</a>
		            </p>
		            <p class="lh-12"><span>2 min ago</span></p>
		          </div>
		        </div>
		        <!-- Comment Header End -->
		
		        <!-- Comment Content Start -->
		        <div class="row padding-5">
		          <p>
		            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
		          </p>
		        </div>
		        <!-- Comment Content End -->
		
		        <!-- Comment Meta Start -->
		        <div class="row padding-5 border border-top border-1 solid silver margin-top">
		
		          <!-- Voting Start -->
		          <a href="#" class="btn hover-fill-disable fill-transparent text-teal -padding-left">
		            <i class="fa fa-lg fa-angle-up"></i> 255
		          </a>
		          <a href="#" class="btn hover-fill-disable fill-transparent text-red">
		            <i class="fa fa-lg fa-angle-down"></i> 13
		          </a>
		          <!-- Voting End -->
		
		          <!-- Action Start -->
		          <div class="pull-right">
		            <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal">
		              <i class="fa fa-exclamation-triangle"></i> Report
		            </a>
		            <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal -padding-right">
		              <i class="fa fa-reply"></i> Reply
		            </a>
		          </div>
		          <!-- Actions End -->
		
		        </div>
		        <!-- Comment Meta End -->
		      </div>
		      
		    </div>
		    <!-- Nested Comment End -->
		    
		    
		    <!-- Nested Comment Start -->
		    <div class="row flex margin-bottom">
		      
		      <!-- Offset Start -->
		      <div class="col-2 after"></div>
		      <!-- Offset End -->
		
		      <div class="col-10 margin-bottom padding-15 fill-white shadow padding-bottom-5 before">
		
		        <!-- Comment Header Start -->
		        <div class="row">
		          <a href="#" class="thumbnail tablet-hide col-1 padding-5">
		            <img src="https://decorator.io/modulr/webroot/media/nouser.png" />
		          </a>
		          <div class="col-11 padding-5">
		            <p class="lh-12">
		              <a href="#" class="text-teal text-strong hover-text-underline">Some Dude</a>
		            </p>
		            <p class="lh-12"><span>2 min ago</span></p>
		          </div>
		        </div>
		        <!-- Comment Header End -->
		
		        <!-- Comment Content Start -->
		        <div class="row padding-5">
		          <p>
		            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
		          </p>
		        </div>
		        <!-- Comment Content End -->
		
		        <!-- Comment Meta Start -->
		        <div class="row padding-5 border border-top border-1 solid silver margin-top">
		
		          <!-- Voting Start -->
		          <a href="#" class="btn hover-fill-disable fill-transparent text-teal -padding-left">
		            <i class="fa fa-lg fa-angle-up"></i> 255
		          </a>
		          <a href="#" class="btn  hover-fill-disable fill-transparent text-red">
		            <i class="fa fa-lg fa-angle-down"></i> 13
		          </a>
		          <!-- Voting End -->
		
		          <!-- Action Start -->
		          <div class="pull-right">
		            <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal">
		              <i class="fa fa-exclamation-triangle"></i> Report
		            </a>
		            <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal -padding-right">
		              <i class="fa fa-reply"></i> Reply
		            </a>
		          </div>
		          <!-- Actions End -->
		
		        </div>
		        <!-- Comment Meta End -->
		      </div>
		      
		    </div>
		    <!-- Nested Comment End -->
		    
		    
		    <!-- A Comment Start -->
		    <div class="col-12 margin-bottom padding-15 fill-white shadow padding-bottom-5">
		      
		      <!-- Comment Header Start -->
		      <div class="row">
		        <a href="#" class="thumbnail col-1 tablet-hide padding-5">
		          <img src="https://decorator.io/modulr/webroot/media/nouser.png" />
		        </a>
		        <div class="col-11 padding-5">
		          <p class="lh-12 padding-top-5">
		            <a href="#" class="text-teal text-strong hover-text-underline">Some Dude</a>
		          </p>
		          <p class="lh-12"><span>2 min ago</span></p>
		        </div>
		      </div>
		      <!-- Comment Header End -->
		      
		      <!-- Comment Content Start -->
		      <div class="row padding-5">
		          <p>
		            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
		            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
		          </p>
		      </div>
		      <!-- Comment Content End -->
		      
		      <!-- Comment Meta Start -->
		      <div class="row padding-5 border border-top border-1 solid silver margin-top">
		        
		        <!-- Voting Start -->
		        <a href="#" class="btn hover-fill-disable fill-transparent text-teal -padding-left">
		          <i class="fa fa-lg fa-angle-up"></i> 255
		        </a>
		        <a href="#" class="btn  hover-fill-disable fill-transparent text-red">
		          <i class="fa fa-lg fa-angle-down"></i> 13
		        </a>
		        <!-- Voting End -->
		        
		        <!-- Action Start -->
		        <div class="pull-right">
		          <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal">
		            <i class="fa fa-exclamation-triangle"></i> Report
		          </a>
		          <a href="#" class="btn fill-transparent hover-fill-disable text-gray hover-text-teal -padding-right">
		            <i class="fa fa-reply"></i> Reply
		          </a>
		        </div>
		        <!-- Actions End -->
		        
		      </div>
		      <!-- Comment Meta End -->
		      
		    </div>
		    <!-- A Comment End -->
		    
		  </div>
		  <!-- Comments End -->
		  
		</div>
		<!-- Wrapper End -->
	</body>
</html>