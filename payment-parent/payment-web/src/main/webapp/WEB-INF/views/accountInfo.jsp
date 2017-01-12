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
      <td>${account.getAccountNumber()}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">IBAN</th>
      <td>${account.getIban().getIBANValue()}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">Account Holder's Name</th>
      <td>${account.getCustomerDTO().getName()}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">Account Type</th>
      <td>${account.getAccountName()}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">Account Balance</th>
      <td>${account.balance}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">Currency</th>
      <td>${account.getCurreny().getCurrencyCode()}</td>
      <td></td>
    </tr>
      <tr>
      <th scope="row">Account Status</th>
      <td>${account.getAccountStatus().toString()}</td>
      <td></td>
    </tr>
       <tr>
      <th scope="row">Payment Date Rule</th>
      <td>${account.getPaymentRule()}</td>
     <td>${account.getPaymentRuleInfo()}</td>
    </tr>
 
  </tbody>
</table>