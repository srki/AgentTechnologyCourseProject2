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
                    var errorCallback = function (response) {
                        $scope.alertMessage = "Error: " + response.data.message;
                    };

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
                    $scope.runningAgents = [];

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

                    // Get all performatives
                    consoleService.getPerformatives(function (response) {
                        $scope.performatives = response.data;
                    }, errorCallback);


                    // Init agent types
                    consoleService.getClasses(function (response) {
                        //$scope.agentTypes = response.data;
                    }, errorCallback);

                    consoleService.getRunning(function (response) {
                        //$scope.runningAgents = response.data;
                    }, errorCallback);

                    for (var i = 0; i < 10; i++) {
                        $scope.agentTypes.push({
                            module: 'rs.ac.uns.ftn.informatika.agents',
                            name: 'ping' + (i + 1)
                        });

                        $scope.runningAgents.push({
                            name: 'Agent 00' + i,
                            host: {
                                address: '127.0.0.1',
                                alias: 'localhost'
                            },
                            type: {
                                module: 'rs.ac.uns.ftn.informatika.agents',
                                name: 'ping' + (i + 1)
                            }
                        })
                    }

                };

            $scope.startAgent = function (type) {

            };

            $scope.newMessage = function () {

            };

            init();
        });
}(angular));