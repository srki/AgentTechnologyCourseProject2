/**
 * Created by Srđan on 9.5.2016..
 * Angular controller for /agent_console route.
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.ConsoleCtrl', [])
        .controller('ConsoleCtrl', function ($rootScope, $location, $scope, HttpConsole, WebSocketConsole) {
            var consoleService = null,
                init = function () {
                    if (!$rootScope.type) {
                        $location.path('/');
                        return;
                    } else {
                        consoleService = $rootScope.type === 'HTTP' ? HttpConsole : WebSocketConsole;
                    }

                    $scope.alertMessage = null;
                    $scope.logs = [];
                    $scope.performatives = [];
                    $scope.agentTypes = [];
                    $scope.agnets = [];

                    // Get all performatives
                    consoleService.getPerformatives(
                        function (response) {
                            $scope.performatives = response.data;
                        },
                        function (response) {
                            $scope.alertMessage = "Error: " + response.data.message;
                        }
                    );

                    // Init message stream and clean it on destroy
                    $scope.$on("$destroy", consoleService.setStreamListeners(function (response) {
                        for (var msg in response.data) {
                            if (!response.data.hasOwnProperty(msg)) {
                                continue;
                            }

                            switch (response.data[msg].type) {
                                case 'STREAM_MESSAGES':
                                    $scope.logs.push(response.data[msg]);
                                    break;
                            }
                        }
                    }));

                };

            $scope.newMessage = function () {

            };

            init();
        });
}(angular));