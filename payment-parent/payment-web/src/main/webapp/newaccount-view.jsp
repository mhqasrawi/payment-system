<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="ps" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Account</title>
<!-- Bootstrap core CSS -->
<link href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link href="<c:url value="/static/main.css"/>" rel="stylesheet">
<link rel="stylesheet"
	href="http://www.w3schools.com/lib/w3-theme-black.css">
<!-- <link rel="stylesheet" -->
<!-- 	href="https://fonts.googleapis.com/css?family=Roboto"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->

<style>
h1.padding {
	padding-left: 3cm;
	margin-top: 7%;
}

html, body, h1, h2, h3, h4, h5, h6 {
	font-family: "Verdana", sans-serif;
	font-weight: lighter;
	color: rgb(30, 205, 151);
}

.w3-sidenav a, .w3-sidenav h4 {
	padding: 12px;
}

.w3-navbar li a {
	padding-top: 12px;
	padding-bottom: 12px;
}

label.padding2 {
	padding-left: 2cm;
	font-style: normal;
	font-weight: bold;
	color: rgb(30, 205, 151);
}
</style>
</head>
<body>
	<div class="w3-top">
		<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large">
			<li><a>Payment System</a></li>
		</ul>
	</div>
	<p />

	<p />
	<h1 style="color: DarkSlateBlue;" class="padding">Create New
		Account</h1>


	<c:choose>
		<c:when test="${requestScope.IBANError ne null}">
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>

				Error ! ${requestScope.IBANError.getMessage()}
			</div>
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>


	<form class="form-horizontal" action="/newAccount" method="post">
		<div class="form-group" style="margin-top: 30px">
			<label class="control-label col-sm-2 " for="accountNumber"
				class="padding2" style="font-weight: normal;">Account
				Number:</label>
			<div class="col-sm-3">
				<input text" class="form-control" name="accountNumber"
					id="accountNumber" placeholder="Enter Account Number" required style="width: 370px;">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="ibandto" class="padding2"
				style="font-weight: normal;">IBAN:</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="ibandto" id="ibandto"
					placeholder="Enter IBAN" required style="width: 370px;">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="accountName"
				class="padding2 " style="font-weight: normal;"> Account
				Type:</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="accountName"
					id="accountName" placeholder="Enter Account Type" required style="width: 370px;">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="balance" class="padding2"
				style="font-weight: normal;">Account Balance:</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="balance" id="balance"
					placeholder="Enter balance" required style="width: 370px;">
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2" for="balance"
				style="font-weight: normal;">Choose Currency Code:</label>
			<div class="col-sm-3">
				<select class="form-control" id="currency" name="currency"
					onmouseover="this.size=4;" onmouseout="this.size=1;" style="width: 370px;">
					<c:forEach var="currencyItem" items="${requestScope.currencyList}">
						<option value="${currencyItem}">${currencyItem}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2" for="customerDTO"
				class="padding2" style="font-weight: normal;">Account
				Holder's Name:</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="customerDTO"
					id="customerDTO" placeholder="Enter Holder's Name" required style="width: 370px;">
			</div>
		</div>


		<div class="form-group">
			<label class="control-label col-sm-2" for="accountStatus"
				class="padding2" style="font-weight: normal;">Account Status
				:</label>
			<div class="col-sm-3">
				<select class="form-control" name="accountStatus" style="width: 370px;">
					<option value="ACTIVE">ACTIVE</option>
					<option value="INACTIVE">INACTIVE</option>
				</select>
			</div>
		</div>

		<script type="text/javascript"
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#paymentRule").change(function() {
					if ($(this).val() == "xDays") {
						$("#paymentRuleInfo").show();
					} else {
						$("#paymentRuleInfo").hide();
					}
				});
			});
		</script>


		<div class="form-group">
			<label class="control-label col-sm-2" for="daterule" class="padding2"
				style="font-weight: normal;">Choose Payment Date Rule:</label>
			<div class="col-sm-3">
				<!--<select class="form-control"  id="paymentRule" name="paymentRule" >-->
				<select class="form-control" id="paymentRule" name="paymentRule"
					onmouseover="this.size=4;" onmouseout="this.size=1;" style="width: 370px;">
					<c:forEach var="dataRule" items="${requestScope.dataRules}">
						<option value="${dataRule.getId()}">${dataRule.getDescription()}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div id="paymentRuleInfo" style="display: none">
			<div class="form-group">
				<label class="control-label col-sm-2" for="paymentRuleInfo"
					class="padding2" style="font-weight: normal;">Days Count :</label>
				<div class="col-sm-2">

					<input class="form-control" type="text" name="paymentRuleInfo"
						id="paymentRuleInfo" placeholder="Enter Days" />
				</div>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default"
					style="margin-left: 60px;">Submit</button>
				<button class="btn btn-default"
					onclick="window.location.href='/paymentSystem'"
					style="margin-left: 20px; width: 80px;">Back</button>
			</div>
		</div>
	</form>
</body>
</html>