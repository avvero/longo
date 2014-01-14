
var canApply = true;
function doApply() {
    canApply = false;
    setTimeout(function() { canApply = true }, 100);
}

$( document ).ready(function() {
    var flowModule = angular.module("flow", [])
    flowModule.filter('reverse', function() {
        return function(items) {
            return items.slice().reverse();
        };
    });
    flowModule.controller("flowController",
        function($scope) {
            $scope.items = [];
            $scope.remove = function(index) {
                $scope.items.splice(index, 1);
            }
            $scope.addLogEntry = function (log) {
                $scope.items.push(log);
                $scope.$apply();
            };
            $scope.addLimitLogEntry = function (log) {
                if ($scope.items.length > 30) {
                    $scope.items.splice(0,1);
                }
                $scope.items.push(log);
                if (canApply) {
                    $scope.$apply();
                    doApply();
                }
            };
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