/**
 * Created by Srđan on 9.5.2016..
 * Angular controller for / route.
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.HomeCtrl', [])
        .controller('HomeCtrl', function ($rootScope, $scope, $location, $log) {
            var init = function () {
                try {
                    if (WebSocket) {
                        $log.info("WebSockets supported.");
                    }
                } catch (e) {
                    $log.warn("WebSockets not supported.");
                    $scope.showHttpConsole();
                }
            };

            $scope.showHttpConsole = function () {
                $rootScope.type = 'HTTP';
                $location.path("/agent_console");
            };

            $scope.showWebSocketConsole = function () {
                $rootScope.type = 'WEB_SOCKET';
                $location.path("/agent_console")
            };

            init();
        });
}(angular));