var LogFlow;
$( document ).ready(function() {
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
                    LogFlow.chatSubscription.push("{message: 'Im in!'}");
                };
                logFlowRequest.onReconnect = function (request, response) {
                };
                logFlowRequest.onMessage = function (response) {
                    LogFlow.onMessage(response);
                };
                logFlowRequest.onError = function (response) {
                };
                logFlowRequest.onTransportFailure = function (errorMsg, request) {
                };
                logFlowRequest.onClose = function (response) {
                };
                LogFlow.chatSubscription = LogFlow.socket.subscribe(logFlowRequest);
            },

            unsubscribe: function () {
                LogFlow.socket.unsubscribe();
            },

            onMessage: function (response) {
                var data = $.parseJSON(response.responseBody);
                var table = $('#logBody')
                var tr = $( "<tr>")
                // -----------
                var level = data.level
                var date = moment(new Date(data.timestamp['$date'])).format("YYYY-MM-DD HH:mm:ss")
                var thread = data.thread
                var user = data.properties ? '('+data.properties.userLogin+','+data.properties.sessionId+')' : ""
                var method = data.class.className+":"+data.method+":"+data.lineNumber
                var message = data.message
                // -----------
                var log = date + "&nbsp;&nbsp;" + level + "&nbsp;" + thread + "&nbsp;" + user + "&nbsp;"
                    + method + "&nbsp;-&nbsp;"// + message
                // -----------
//                tr.append($( "<td>").append(data.level))
//                tr.append($( "<td nowrap>").append(date))
//                tr.append($( "<td nowrap>").append(data.thread))
//                tr.append($( "<td nowrap>").append('('+data.properties.userLogin+','+data.properties.sessionId+')'))
//                tr.append($( "<td nowrap>").append(data.class.className+":"+data.method+":"+data.lineNumber))
//                tr.append($( "<td style='widows: 300px'>").append(data.message))
                table.prepend(tr)
                //
                // XXX Нормально парсим в строку нужно нам
                if ((message + " ").indexOf('\r\n')!= -1) {
                    var tr2 = $( "<tr>")
                    tr2.append($( "<td>"))
                    message = safeTags(message)
                    message = message.replaceAll('\r\n', '<br/>')
                    tr2.append($( "<td>").append(message))
                    table.prepend(tr2)
                } else {
                    log += message
                }
                // -----------
                tr.append($( "<td colspan='2'>").append(log))
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
                url: 'log/'  + id
            };
            LogFlow.subscribe(logFlowRequest);
            //LogFlow.chatSubscription.push({"Im in!}");
        });
});

// ----------------
String.prototype.replaceAll = function( token, newToken, ignoreCase ) {
    var _token;
    var str = this + "";
    var i = -1;

    if ( typeof token === "string" ) {

        if ( ignoreCase ) {

            _token = token.toLowerCase();

            while( (
                i = str.toLowerCase().indexOf(
                    token, i >= 0 ? i + newToken.length : 0
                ) ) !== -1
                ) {
                str = str.substring( 0, i ) +
                    newToken +
                    str.substring( i + token.length );
            }

        } else {
            return this.split( token ).join( newToken );
        }

    }
    return str;
};

function safeTags(str) {
    return str.replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('>','&gt;') ;
}
