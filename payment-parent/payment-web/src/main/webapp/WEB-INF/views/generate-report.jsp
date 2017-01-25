<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/main.css'/>" rel="stylesheet">

<h1 style="color: DarkSlateBlue;" class="padding">Generate Report</h1>

<form class="form-horizontal" action="/generateReport" method="post">

	<!-- 	<div class="row form-group"> -->
	<!-- 		<div class="col-xs-2">Account</div> -->
	<!-- 		<div class="col-xs-3"> -->
	<!-- 			<select name="category" class="form-control"> -->
	<!-- 				<option value="-1">Any</option> -->
	<%-- 				<c:forEach var="account" items="${accounts}"> --%>
	<%-- 					<option value="${account.getId()}">${account.getAccountNumber()}</option> --%>
	<%-- 				</c:forEach> --%>
	<!-- 			</select> -->
	<!-- 		</div> -->
	<!-- 	</div> -->

	<div class="col-xs-12">
		<div class="row form-group">

			<div class="col-xs-2">from</div>
			<!-- 			&nbsp; -->
			<div class="col-xs-3">
				<select name="date-from-year" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>
			<!-- 			&nbsp; -->
			<!-- 			<div class="col-xs-2">Date from (Month)</div> -->
			<!-- 			&nbsp; -->
			<div class="col-xs-3">
				<select name="date-from-month" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>
			<!-- 			&nbsp; -->
			<!-- 			<div class="col-xs-2">Date from (Day)</div> -->
			<!-- 			&nbsp; -->
			<div class="col-xs-3">
				<select name="date-from-day" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>

		</div>


		<div class="row form-group">

			<div class="col-xs-2">To</div>
			<div class="col-xs-3">
				<select name="date-to-year" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>
			&nbsp;

			<!-- 			<div class="col-xs-2">Date To (Month)</div> -->
			<div class="col-xs-3">
				<select name="date-to-month" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>
			&nbsp;
			<!-- 			<div class="col-xs-2">To</div> -->
			<div class="col-xs-3">
				<select name="date-to-day" class="form-control">
					<option value=" ">------</option>
					<c:forEach var="cat" items="${requestScope.allCategory}">
						<option value="${cat.categoryName}">${cat.categoryName}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="col-xs-2">Extension</div>
		<div class="col-xs-3">
			<select name="extension" class="form-control">
				<c:forEach var="ext" items="${requestScope.extensions}">
					<option value="${ext}">${ext}</option>
				</c:forEach>
			</select>
		</div>



		<div class="row form-group">
			<div class="col-xs-12">
				<div class="pull-right">
					<button type="submit" class="btn btn-primary">Generate</button>
				</div>
			</div>
		</div>
	</div>
</form>