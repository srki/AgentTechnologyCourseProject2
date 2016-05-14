/**
 * Created by Srđan on 9.5.2016..
 * Angular controller for /agent_console route.
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.ConsoleCtrl', [])
        .controller('ConsoleCtrl', function ($rootScope, $location, $scope, $uibModal, HttpConsole, WebSocketConsole) {
            var consoleService = null,
                performatives = [],
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
                    $scope.agentTypes = [];
                    $scope.runningAgents = [];

                    // Init message stream and clean it on destroy
                    $scope.$on("$destroy", consoleService.setStreamListeners(function (response) {
                        for (var msg in response.data) {
                            if (!response.data.hasOwnProperty(msg)) {
                                continue;
                            }

                            switch (response.data[msg].type) {
                                case 'LOG':
                                    $scope.logs.push(response.data[msg]);
                                    break;
                                case "ADD_CLASS":
                                    break;
                                case "REMOVE_CLASS":
                                    break;
                                case "ADD_AGENT":
                                    break;
                                case "REMOVE_AGENT":
                                    break;
                            }
                        }
                    }));

                    // Get all performatives
                    consoleService.getPerformatives(function (response) {
                        performatives = response.data;
                    }, errorCallback);

                    // Init agent types
                    consoleService.getClasses(function (response) {
                        Array.prototype.push.apply($scope.agentTypes, response.data);
                    }, errorCallback);

                    consoleService.getRunning(function (response) {
                        Array.prototype.push.apply($scope.runningAgents, response.data);
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
                        });
                    }
                };

            $scope.runAgent = function (type) {
                var scope = $scope.$new(true);
                scope.consoleService = consoleService;
                scope.type = type;

                $uibModal.open({
                    animation: true,
                    templateUrl: 'partials/new_agent_modal.html',
                    controller: 'NewAgentModalCtrl',
                    scope: scope
                });
            };

            $scope.stopAgent = function (aid) {
                consoleService.stopAgent(aid,
                    function (response) {

                    },
                    function (response) {
                        $scope.alertMessage = "Error: " + response.data.message;
                    });
            };

            $scope.newMessage = function (aid) {
                var scope = $scope.$new(true);
                scope.consoleService = consoleService;
                scope.performatives = performatives;
                scope.runningAgents = $scope.runningAgents;
                scope.message = {
                    receivers: aid ? [aid] : []
                };

                $uibModal.open({
                    animation: true,
                    templateUrl: 'partials/new_message_modal.html',
                    controller: 'NewMessageModalCtrl',
                    size: 'lg',
                    scope: scope
                });
            };

            init();
        });
}(angular));