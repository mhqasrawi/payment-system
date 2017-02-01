<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/main.css'/>" rel="stylesheet">

<h1 style="color: DarkSlateBlue;" class="padding">Generate Report</h1>

<c:if test="${param['success'] ne null}">
		<div class="alert alert-success" role="alert">
           Report generated successfully
        </div>
	</c:if>

	<c:if test="${error ne null}">
    		<div class="alert alert-warning" role="alert">
                Report generation failed. Please check your input: ${error}
             </div>
    	</c:if>
<form class="form-horizontal" action="/generateReport" method="post">



		<div class="col-xs-2">Extension</div>
		<div class="col-xs-3">
			<select name="extension" class="form-control">
				<c:forEach var="ext" items="${requestScope.extensions}">
					<option value="${ext}">${ext}</option>
				</c:forEach>
			</select>
		</div>

        <div class="col-xs-2">Transcription language</div>
        <div class="col-xs-3">
        	<select name="transcriber" class="form-control">
        		<c:forEach var="trans" items="${transcribers}">
        			<option value="${trans.getClass()}">${trans.getSupportedLanguage()}</option>
        		</c:forEach>
        	</select>
       	</div>
        <div class="row form-group">
		     <div class="col-xs-12">File Name</div>
		     <div class="col-xs-12">
			<input type="text" id="file-name" name="file-name" class="form-control"
			placeholder="Name" required autofocus>
		</div>
		</div>

         <div class="row form-group">
        		     <div class="col-xs-12">File Directory</div>
        		     <div class="col-xs-12">
        			<input type="text" id="file-directory" name="file-directory" class="form-control"
        			placeholder="Name" required autofocus>
        		</div>
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