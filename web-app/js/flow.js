
var logPerPage = 200
var waitBeforeNextApplyTimeout = 100
var applyRemainsTimeout = 1000

// ########################################################################
$( document ).ready(function() {
    var flowModule = angular.module("flow", [])
    flowModule.filter('reverse', function() {
        return function(items) {
            return items.slice().reverse();
        };
    });
    flowModule.controller("flowController",
        function($scope) {
            $scope.waitToApply = 0; // ждут обновления
            $scope.canApply = true; // включенность возможности обновления
            $scope.isStopApply = false; // остановили обновление страницы
            $scope.items = [];
            $scope.remove = function(index) {
                $scope.items.splice(index, 1);
            }
            $scope.waitBeforeNextApply = function() {
                $scope.canApply = false;
                setTimeout(function() { $scope.canApply = true }, waitBeforeNextApplyTimeout);
            }
            $scope.applyRemains = function() {
                setTimeout(function() {
                    // Если обновлять можно и есть остатки (зависли), то обновим страницу
                    if (!$scope.isStopApply && $scope.waitToApply > 0) {
                        $scope.waitToApply = 0
                        $scope.$apply();
                        $scope.waitBeforeNextApply();
                    }
                }, applyRemainsTimeout);
            }
            $scope.addLimitLogEntry = function (log) {
                // Не выходим за лимит
                if ($scope.items.length > logPerPage) {
                    $scope.items.splice(0,1);
                }
                $scope.items.push(log);
                $scope.waitToApply += 1
                // Если можно обновить, то обновим и поставим ожидалку
                if ($scope.canApply && !$scope.isStopApply) {
                    $scope.waitToApply = 0
                    $scope.$apply();
                    $scope.waitBeforeNextApply();
                } else {
                    // Обновим остатки
                    $scope.applyRemains()
                }
            };
            $scope.stopApply = function () {
                $scope.isStopApply = !$scope.isStopApply;
            }
            //------------------
            $scope.LogFlow = {
                socket: null,
                chatSubscription: null,
                notificationSubscription: null,
                publicSubscription: null,
                transport: null,

                subscribe: function (options) {
                    var defaults = {
                        contentType: "application/json",
                        shared: false,
                        transport: 'websocket',
                        //transport: 'long-polling',
                        fallbackTransport: 'long-polling',
                        trackMessageLength: true
                    }
                    var logFlowRequest = $.extend({}, defaults, options);

                    logFlowRequest.onOpen = function (response) {
                        $scope.LogFlow.chatSubscription.push("{message: 'Im in!'}");
                    };
                    logFlowRequest.onReconnect = function (request, response) {
                    };
                    logFlowRequest.onMessage = function (response) {
                        $scope.LogFlow.onMessage(response);
                    };
                    logFlowRequest.onError = function (response) {
                    };
                    logFlowRequest.onTransportFailure = function (errorMsg, request) {
                    };
                    logFlowRequest.onClose = function (response) {
                    };
                    $scope.LogFlow.chatSubscription = $scope.LogFlow.socket.subscribe(logFlowRequest);
                },

                unsubscribe: function () {
                    $scope.LogFlow.socket.unsubscribe();
                },

                onMessage: function (response) {
                    var data = $.parseJSON(response.responseBody);
//                    var table = $('#logBody')
//                    var tr = $( "<tr>")
//                    // -----------
//                    var level = data.level
                    var date = moment(new Date(data.timestamp['$date'])).format("YYYY-MM-DD HH:mm:ss")
                    data.date = date
//                    var thread = data.thread
                    var user = data.properties ? '('+data.properties.userLogin+','+data.properties.sessionId+')' : ""
                    data.user = user
//                    var method = data.class.className+":"+data.method+":"+data.lineNumber
                    var message = data.message
//                    // -----------
//                    var log = date + "&nbsp;&nbsp;" + level + "&nbsp;" + thread + "&nbsp;" + user + "&nbsp;"
//                        + method + "&nbsp;-&nbsp;"// + message
//                    // -----------
//                    table.prepend(tr)
//                    //
//                    // XXX Нормально парсим в строку нужно нам
                    if ((message + " ").indexOf('\r\n')!= -1) {
//                        var tr2 = $( "<tr>")
//                        tr2.append($( "<td>"))
                        message = safeTags(message)
                        message = message.replaceAll('\r\n', '<br/>')
//                        tr2.append($( "<td>").append(message))
//                        table.prepend(tr2)
                        data.message = ""
                        data.messageLong = message
                    }
//                    // -----------
//                    tr.append($( "<td colspan='2'>").append(log))
//                    table.prepend(tr)

                    $scope.addLimitLogEntry(data)
                }
            };
            if (typeof atmosphere == 'undefined') {
                // if using jquery.atmosphere.js
                $scope.LogFlow.socket = $.atmosphere;
            } else {
                // if using atmosphere.js
                $scope.LogFlow.socket = atmosphere;
            }
            var logFlowRequest = {
                url: 'log/'  + id
            };
            $scope.LogFlow.subscribe(logFlowRequest);
        }
    );
});