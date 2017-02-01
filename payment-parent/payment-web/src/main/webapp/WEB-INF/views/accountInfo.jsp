<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<table class="table table-striped">
	<thead>
		<tr>
			<th>Account Info</th>
			<th>Description</th>
			<th>Extra Info</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">Account Number</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getAccountNumber()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">IBAN</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getIban().getIBANValue()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Account Holder's Name</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getCustomerDTO().getName()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Account Type</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getAccountName()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Account Balance</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().balance}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Currency</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getCurreny().getCurrencyCode()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Account Status</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getAccountStatus().toString()}</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">Payment Date Rule</th>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getPaymentRule()}</td>
			<td>${PAYMENT_MENU_CONTEXT.getCurrentAccount().getPaymentRuleInfo()}</td>
		</tr>
	</tbody>
</table>