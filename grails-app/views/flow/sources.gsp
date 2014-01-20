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
</head>
<body>
    <div class="container">
        <h3>Mongo <span class="label label-default"></span></h3>
        <table class="table table-striped">
            <g:each in="${mongoConnectionConfigs}" var="item">
                <tr>
                    <td>${item.host}</td>
                    <td>${item.port}</td>
                    <td>${item.databaseName}</td>
                    <td>${item.collectionName}</td>
                    <td>
                        <g:form>
                            <input type="hidden" name="id" value="${item.id}"/>
                            <g:actionSubmit action="delMongoConnectionConfig" value="Удалить" class="btn btn-default">Удалить</g:actionSubmit>
                            <g:actionSubmit action="startMongoCollector" value="Подключиться" class="btn btn-default"/>
                        </g:form>
                    </td>
                </tr>
            </g:each>
        </table>
        <g:form action="addMongoConnectionConfig" class="form-horizontal" role="form">
            <div class="form-group">
                <label for="host" class="col-sm-2 control-label">host</label>
                <div class="col-sm-10">
                    <input name="host" class="form-control" id="host" placeholder="host">
                </div>
            </div>
            <div class="form-group">
                <label for="port" class="col-sm-2 control-label">port</label>
                <div class="col-sm-10">
                    <input name="port" class="form-control" id="port" placeholder="port">
                </div>
            </div>
            <div class="form-group">
                <label for="databaseName" class="col-sm-2 control-label">databaseName</label>
                <div class="col-sm-10">
                    <input name="databaseName" class="form-control" id="databaseName" placeholder="databaseName">
                </div>
            </div>
            <div class="form-group">
                <label for="collectionName" class="col-sm-2 control-label">collectionName</label>
                <div class="col-sm-10">
                    <input name="collectionName" class="form-control" id="collectionName" placeholder="collectionName">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <g:submitButton name="Добавить" class="btn btn-default"/>
                </div>
            </div>
        </g:form>
        <h3>Socket<span class="label label-default">New</span></h3>
        <table class="table table-striped">
            <g:each in="${sockets}" var="item">
                <tr>
                    <td>${item.host}</td>
                    <td>${item.port}</td>
                    <td>
                        <g:form>
                            <input type="hidden" name="id" value="${item.id}"/>
                            <g:actionSubmit action="delSocket" value="Удалить" class="btn btn-default">Удалить</g:actionSubmit>
                            <g:actionSubmit action="startSocketCollector" value="Подключиться" class="btn btn-default"/>
                            <g:actionSubmit action="stopSocketCollector" value="Отключить" class="btn btn-default"/>
                        </g:form>
                    </td>

                </tr>
            </g:each>
        </table>
        <g:form action="addSocket" class="form-horizontal" role="form">
            <div class="form-group">
                <label for="host" class="col-sm-2 control-label">host</label>
                <div class="col-sm-10">
                    <input name="host" class="form-control" value="localhost" placeholder="host">
                </div>
            </div>
            <div class="form-group">
                <label for="port" class="col-sm-2 control-label">port</label>
                <div class="col-sm-10">
                    <input name="port" class="form-control" placeholder="port">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <g:submitButton name="Добавить" class="btn btn-default"/>
                </div>
            </div>
        </g:form>
    </div>
</body>
</html>