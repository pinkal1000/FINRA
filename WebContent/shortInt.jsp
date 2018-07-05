<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Short Interest Reader</title>
</head>
 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular.min.js"></script>
<body>
<jsp:useBean id="shortReader" scope="session" class="com.stocks.shortinterest.FinraShortInterestReader"></jsp:useBean> 
<% 
shortReader.readShortData();
//String symbolJSON =shortReader.getSymbols();
String symbolJSON = shortReader.getShortJSON();
%>
<div ng-app="autocomplete" ng-controller="autocompleteCtrl">

<!--    <p>Name : <input type="text" ng-model="name"></p> -->
<!--  <h1><span ng-bind="name"></span></h1> -->

  <H1>Stock Symbol DropDown </H1>
  <select ng-model="symbolopt">
  
   <option ng-repeat="x in name"   value ="{{ x }}" >
   {{ x["Security Symbol"] }}</option>
   
  </select>
  <H1>Values of selected Stock Symbol {{ symbolopt['Security Symbol'] }}</H1>
  <h1><span ng-bind="symbolopt"></span></h1>
  
  <H1>Auto Complete with DataList </H1>
     <div>
        <label>Stock Symbol</label>
        <input type="text"  ng-model="tickersymbol" list="symbols" >
     </div>
  
  
  <datalist id="symbols">
   <select ng-model="tickersymbol">
  
   <option ng-repeat="x in name"   value ="{{ x['Security Symbol'] }}" >
   {{ x["Security Symbol"] }}</option>
   
  </select>
  </datalist>


</div>
 
 
 
<script>
var app = angular.module("autocomplete", []);

app.controller("autocompleteCtrl", function($scope) {
	$scope.name = <%=symbolJSON%>;
});

	
	
 
</script>
</body>
</html>
