<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">
<script type="text/javascript"
	src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript"
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
<body>
	<div class="container">
		<form method="post" action="./new-payment">
			<div class="row form-group">
				<label class="col-2 col-form-label">Beneficary IBAN</label>
				<div class="col-5">
					<input class="form-control" type="text"
						style="width: 400px; height: 30px" name="beneficary-iban">
				</div>
			</div>
			<div class="row form-group">
				<label class="col-2 col-form-label">Beneficiary Name</label>
				<div class="col-5">
					<input class="form-control" type="text"
						style="width: 400px; height: 30px" name="beneficiary-name">
				</div>
			</div>
			<div class="row form-group">
				<label class="col-2 col-form-label">Payment Amount</label>
				<div class="col-5">
					<input class="form-control" type="text"
						style="width: 400px; height: 30px" name="payment-amount">
				</div>
			</div>
			<div class="row form-group">
				<label class="col-2 col-form-label">Transfer Currency</label>
				<div class="col-5">
					<select id="transfer-currency" name="transfer-currency"
						class="form-control" style="width: 400px; height: 30px">
						<c:forEach items="${currencies}" var="currency">
							<option value="${currency}">${currency}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row form-group">
				<label class="col-2 col-form-label">Payment Date</label>
				<div class="input-group date" data-provide="datepicker"
					id="date-picker" style="width: 400px; height: 30px">
					<input name="payment-date" type="text" class="form-control" style="height: 30px">
					<div class="input-group-addon">
						<span class="glyphicon glyphicon-th"></span>
					</div>
				</div>
			</div>
			<div class="row form-group">
				<label class="col-2 col-form-label">Purpose</label>
				<div class="col-5">
					<select id="payment-purpose" name="payment-purpose"
						class="form-control" style="width: 400px; height: 30px">
						<c:forEach items="${purposes}" var="purpose">
							<option value="${purpose.shortCode}">${purpose.shortCode}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row form-group">
				<button type="submit" class="btn btn-primary">Submit
					Payment</button>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		$('#date-picker').datepicker({
			format : 'mm/dd/yyyy',
			startDate : '-3d'
		});
	</script>
</body>
</html>


