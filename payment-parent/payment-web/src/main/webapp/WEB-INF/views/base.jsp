<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>W3.CSS Template</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/	lib/w3.css">
<link rel="stylesheet"
	href="http://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<%-- <link href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>" --%>
<!-- 	rel="stylesheet"> -->
<%-- <link href="<c:url value='/static/main.css'/>" rel="stylesheet"> --%>


<style>
html, body, h1, h2, h3, h4, h5, h6 {
	font-family: "Roboto", sans-serif
}

.w3-sidenav a, .w3-sidenav h4 {
	padding: 12px;
}

.w3-navbar li a {
	padding-top: 12px;
	padding-bottom: 12px;
}
</style>
<link class="cssdeck" rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css"
	class="cssdeck">
</head>
<body>

	<!-- Navbar -->
	<div class="w3-top">
		<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large">
			<li><a>Payment System</a></li>
		</ul>
	</div>

	<!-- Sidenav -->
	<nav class="w3-sidenav w3-collapse w3-theme-l5 w3-animate-left"
		style="z-index: 3; width: 250px; margin-top: 51px;" id="mySidenav">
		<a href="javascript:void(0)" onclick="w3_close()"
			class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large"
			title="close menu"> <i class="fa fa-remove"></i>
		</a>
		<h4>
			<b>Account Options</b>
		</h4>
		<script class="cssdeck"
			src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script class="cssdeck"
			src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
		<script>
			$(document).ready(function() {
				$('label.tree-toggler').click(function() {
					$(this).parent().children('ul.tree').toggle(500);
				});
			});
		</script>
		<div class="well" style="width: 400px; padding: 8px 0;">
			<div style="overflow-y: scroll; overflow-x: hidden; height: 600px;">
				<ul class="nav nav-list">
					<li><label class="tree-toggler nav-header">Show
							Account Info</label>
						<ul class="nav nav-list tree">
							<li><a href="<c:url value="/accountInfo" />"> Account Info</a></li>
						</ul></li>
					<li class="divider"></li>
					<li><label class="tree-toggler nav-header">Create New
							Payment</label>
						<ul class="nav nav-list tree">
							<li><a href="<c:url value="/new-payment" />">Create Payment</a></li>
						</ul></li>
					<li class="divider"></li>
					<li><label class="tree-toggler nav-header">Generate
							Report</label>
						<ul class="nav nav-list tree">
							<li><a href="<c:url value="/generateReport" />">Generate
									Report</a></li>
						</ul></li>
					<li class="divider"></li>
					<li><label class="tree-toggler nav-header">New Payment
							Purpose</label>
						<ul class="nav nav-list tree">
							<li><a href="/new-payment-purpose"> New Payment Purpose</a></li>
						</ul></li>

				</ul>
			</div>
		</div>

	</nav>

	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div> 

	<!-- Main content: shift it to the right by 250 pixels when the sidenav is visible -->
	<div class="w3-main" style="margin-left: 250px">

		<div class="w3-row w3-padding-64">
			<div class="w3-twothird w3-container">
				<c:if test="${pageContent ne null}">
					<jsp:include page="${pageContent}">
						<jsp:param value="${account}" name="account" />
					</jsp:include>
				</c:if>
			</div>
		</div>
	</div>


	<!-- END MAIN -->


	<script>
		// Get the Sidenav
		var mySidenav = document.getElementById("mySidenav");

		// Get the DIV with overlay effect
		var overlayBg = document.getElementById("myOverlay");

		// Toggle between showing and hiding the sidenav, and add overlay effect
		function w3_open() {
			if (mySidenav.style.display === 'block') {
				mySidenav.style.display = 'none';
				overlayBg.style.display = "none";
			} else {
				mySidenav.style.display = 'block';
				overlayBg.style.display = "block";
			}
		}

		// Close the sidenav with the close button
		function w3_close() {
			mySidenav.style.display = "none";
			overlayBg.style.display = "none";
		}
	</script>

</body>
</html>
