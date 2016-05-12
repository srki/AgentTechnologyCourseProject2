/**
 * Created by Srđan on 9.5.2016..
 * Angular service for HTTP console requests
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.HttpConsole', [])
        .factory('HttpConsole', function ($http, $interval) {
            return {
                getClasses: function (success, error) {
                    $http({
                        method: 'GET',
                        url: 'api/agents/classes'
                    }).then(success, error);
                },
                getRunning: function (success, error) {
                    $http({
                        method: 'GET',
                        url: 'api/agents/running'
                    }).then(success, error);
                },
                runAgent: function (type, name, success, error) {
                    $http({
                        method: 'PUT',
                        url: 'api/agents/running/' + type + '/' + name
                    }).then(success, error);
                },
                stopAgent: function (aid, success, error) {
                    $http({
                        method: 'DELETE',
                        url: 'api/agents/running/' + aid
                    }).then(success, error);
                },
                sendMessage: function (message, success, error) {
                    $http({
                        method: 'POST',
                        url: 'api/messages',
                        data: message
                    }).then(success, error);
                },
                getPerformatives: function (success, error) {
                    $http({
                        method: 'GET',
                        url: 'api/messages'
                    }).then(success, error);
                },
                setStreamListeners: function (success, error) {
                    var last = new Date().getTime(),
                        intervalId = $interval(function () {
                            $http({
                                method: 'GET',
                                url: 'api/stream',
                                params: {
                                    last: last
                                }
                            }).then(success, error);
                            last = new Date().getTime();
                        }, 1000);

                    return function () {
                        $interval.cancel(intervalId);
                    };
                }
            };
        });
}(angular));