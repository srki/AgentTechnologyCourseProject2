/**
 * Created by Srđan on 9.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.HomeCtrl', [])
        .controller('HomeCtrl', function ($scope, $location) {
            var init = function () {
                try {
                    if (WebSocket) {
                        console.log("WebSockets supported.");
                    }
                } catch (e) {
                    console.log("WebSockets not supported.");
                    $scope.showHttpConsole();
                }
            };

            $scope.showHttpConsole = function () {
                $location.path("/http");
            };

            $scope.showWebSocketConsole = function () {
                $location.path("/websocket")
            };

            init();
        });
}(angular));