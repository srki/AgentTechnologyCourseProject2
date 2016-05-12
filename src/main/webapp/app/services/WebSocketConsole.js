/**
 * Created by Srđan on 9.5.2016..
 * Angular service for WebSocket console requests
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.WebSocketConsole', [])
        .factory('WebSocketConsole', function ($location, $log) {
            var socket = null,
                sendQueue = [],
                successListeners = {},
                errorListeners = {},
                addSuccessListener = function (type, listener) {
                    successListeners[type] = listener;
                },
                addErrorListener = function (type, listener) {
                    errorListeners[type] = listener;
                },
                send = function (type, data) {
                    var json = JSON.stringify({
                        type: type,
                        data: data
                    });

                    if (socket.readyState == socket.CONNECTING) {
                        sendQueue.push(json)
                    } else {
                        socket.send(json);
                    }
                },
                addListenersAndSend = function (type, success, error, data) {
                    addSuccessListener(type, success);
                    addErrorListener(type, error);
                    send(type, data);
                };

            try {
                socket = new WebSocket('ws://' + $location.host() + ":" + $location.port() + '/data')
            } catch (e) {
                return;
            }

            (function (socket) {
                socket.onmessage = function (event) {
                    var object = event.data ? JSON.parse(event.data) : {}, success, error;
                    object.data = object.data ? JSON.parse(object.data) : {};

                    if (object.success) {
                        success = successListeners[object.type];
                        if (success) {
                            object.status = 200;
                            success(object);
                        }
                    } else {
                        error = errorListeners[object.type];
                        object.status = 400;
                        if (error) {
                            error(object);
                        }
                    }
                };

                socket.onopen = function () {
                    $log.info("WebSocket onOpen");
                    while (sendQueue.length > 0) {
                        socket.send(sendQueue.pop());
                    }
                }
            })(socket);

            return {
                getClasses: function (success, error) {
                    addListenersAndSend('GET_CLASSES', success, error);
                },
                getRunning: function (success, error) {
                    addListenersAndSend('GET_RUNNING', success, error);
                },
                runAgent: function (type, name, success, error) {
                    addListenersAndSend('RUN_AGENT', success, error, {
                        type: type,
                        name: name
                    });
                },
                stopAgent: function (aid, success, error) {
                    addListenersAndSend('STOP_AGENT', success, error, aid);
                },
                sendMessage: function (message, success, error) {
                    addListenersAndSend('SEND_MESSAGE', success, error, message);
                },
                getPerformatives: function (success, error) {
                    addListenersAndSend('GET_PERFORMATIVES', success, error);
                },
                setStreamListeners: function (success, error) {
                    addSuccessListener('STREAM_MESSAGE', success);
                    addErrorListener('STREAM_MESSAGE', error);
                }
            };
        });
}(angular));