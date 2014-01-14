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
        <div ng-repeat="log in items">
            <span>
                {{log.date}}&nbsp;&nbsp;
                {{log.level}}&nbsp;
                {{log.thread}}&nbsp;
                {{log.user}}&nbsp;
                {{log.class.className}}:{{log.method}}:{{log.lineNumber}}&nbsp;
                {{log.message}}&nbsp;
            </span>
            <div ng-bind-html-unsafe="log.messageLong"></div>
        </div>
    </div>
    <table style="font-size: small;">
        <tbody id="logBody">

        </tbody>
    </table>
</body>
</html>