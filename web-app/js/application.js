var LogFlow;
if (typeof jQuery !== 'undefined') {
    (function($) {

        /*
         The Jabber variable holds all JavaScript code required for communicating with the server.
         It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
         */
        LogFlow = {
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
                    console.log('jabberOpen transport: ' + response.transport);
                    LogFlow.chatSubscription.push("{message: 'Im in!'}");
                };
                logFlowRequest.onReconnect = function (request, response) {
                    console.log("jabberReconnect");
                };
                logFlowRequest.onMessage = function (response) {
                    LogFlow.onMessage(response);
                };
                logFlowRequest.onError = function (response) {
                    console.log('jabberError: ' + response);
                };
                logFlowRequest.onTransportFailure = function (errorMsg, request) {
                    console.log('jabberTransportFailure: ' + errorMsg);
                };
                logFlowRequest.onClose = function (response) {
                    console.log('jabberClose: ' + response);
                };
                LogFlow.chatSubscription = LogFlow.socket.subscribe(logFlowRequest);
            },

            unsubscribe: function () {
                LogFlow.socket.unsubscribe();
            },

            onMessage: function (response) {
                var data = $.parseJSON(response.responseBody);
                console.log(data);
                var table = $('#logBody')

                var tr = $( "<tr>")
                tr.append($( "<td>").append(data.timestamp['$date']))
                tr.append($( "<td>").append(data.level))
                tr.append($( "<td>").append(data.message))
                tr.append($( "<td>").append(data.thread))
                tr.append($( "<td>").append(data.method))
                tr.append($( "<td>").append(data.fileName))
                table.prepend(tr)
            }
        };

        $(window).unload(function () {
            LogFlow.unsubscribe();
        });

        $(document).ready(function () {
            if (typeof atmosphere == 'undefined') {
                // if using jquery.atmosphere.js
                LogFlow.socket = $.atmosphere;
            } else {
                // if using atmosphere.js
                LogFlow.socket = atmosphere;
            }
            var logFlowRequest = {
                url: 'log/1'
            };
            LogFlow.subscribe(logFlowRequest);
            //LogFlow.chatSubscription.push({"Im in!}");
        });
    })(jQuery);
}
