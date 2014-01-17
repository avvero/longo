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
    <script>var collector = "${collector}";</script>
    <script>var id = "${id}";</script>
    <g:javascript library="angular"/>
    <g:javascript library="application"/>
    <g:javascript library="flow"/>
</head>
<body>
    <div id="App1" ng-app="flow" ng-controller="flowController">
        <div class="container">
            <div class="fill">
                <div>
                    <div ng-repeat="log in items | reverse | filter:search"
                        class="
                        {{log.level == 'ERROR' ? 'alert-danger' : ''}}
                        {{log.level == 'WARN' ? 'alert-warning' : ''}}
                        {{log.level == 'INFO' ? 'alert-info' : ''}}
                        ">
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
            </div>
        </div>
        <div class="container" style="padding-top: 70px">
            <div class="navbar navbar-default navbar-fixed-bottom" role="navigation">
                <div class="container">
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li>
                                <form action="/Stub-0.1/gateToShop/index" method="post" class="navbar-form navbar-left" role="search">
                                    <div class="form-group">
                                        <input ng-model="search.message" class="form-control">
                                    </div>
                                    <div class="btn-group">
                                        <button type="button"
                                                class="btn btn-sm btn-success {{showDebug == true ? 'active' : ''}}"
                                                ng-click="changeShowDebug();">
                                            DEBUG</button>
                                        <button type="button"
                                                class="btn btn-sm btn-info {{showInfo == true ? 'active' : ''}}"
                                                ng-click="changeShowInfo();">
                                            INFO</button>
                                        <button type="button"
                                                class="btn btn-sm btn-warning {{showWarn == true ? 'active' : ''}}"
                                                ng-click="changeShowWarn();">
                                            WARN</button>
                                        <button type="button"
                                                class="btn btn-sm btn-danger {{showError == true ? 'active' : ''}}"
                                                ng-click="changeShowDanger();">
                                            ERROR</button>
                                    </div>
                                </form>
                            </li>
                            <li></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                Всего событий: {{items.length}}
                            </li>
                            <li>
                                <form class="navbar-form navbar-left">
                                    <button type="button" class="btn btn-warning {{isStopApply == true ? 'active' : ''}}" ng-click="stopApply();">STOP <span class="badge">{{waitToApply}}</span></button>
                                </form>
                            </li>
                        </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>