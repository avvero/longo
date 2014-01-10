<%--
  Created by IntelliJ IDEA.
  User: avvero
  Date: 01.12.13
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript library="jquery"/>
    <g:javascript library="atmosphere-meteor"/>
    <script>var id = ${id};</script>
    <g:javascript library="angular"/>
    <g:javascript library="application"/>
    <g:javascript library="flow"/>
</head>
<body>
    <div id="App1" ng-app="flow" ng-controller="flowController">
        <h1>Your order</h1>
        <div ng-repeat="item in items">
            <span>{{item.product_name}}</span>
            <span>{{item.price | currency}}</span>
            <button ng-click="remove($index);">Remove</button>
            <button ng-click="addItem()">Add to list</button>
        </div>
    </div>
    <table style="font-size: small;">
        <tbody id="logBody">

        </tbody>
    </table>
</body>
</html>