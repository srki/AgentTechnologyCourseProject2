/**
 * Created by Srđan on 9.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.HttpConsole', [])
        .factory('HttpConsole', function ($http) {
            return {
                getClasses: function () {
                    return $http({
                        method: 'GET',
                        url: 'api/agents/classes'
                    });
                },
                getRunning: function () {
                    return $http({
                        method: 'GET',
                        url: 'api/agents/running'
                    });
                },
                runAgent: function (type, name) {
                    return $http({
                        method: 'PUT',
                        url: 'api/agents/running/' + type + '/' + name
                    });
                },
                stopAgent: function (aid) {
                    return $http({
                        method: 'DELETE',
                        url: 'api/agents/running/' + aid
                    });
                },
                sendMessage: function (message) {
                    return $http({
                        method: 'POST',
                        url: 'api/messages',
                        data: message
                    })
                },
                getPerformatives: function () {
                    return $http({
                        method: 'GET',
                        url: 'api/messages'
                    })
                },
                getLogs: function (last) {
                    return $http({
                        method: 'GET',
                        url: 'api/logs',
                        params: {
                            last: last
                        }
                    })
                }
            };
        });
}(angular));