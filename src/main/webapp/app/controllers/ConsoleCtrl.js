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

                    // Get all performatives
                    consoleService.getPerformatives(
                        function (response) {
                            console.log(response.data);
                        },
                        function (response) {

                        }
                    );

                    // Init message stream and clean it on destroy
                    $scope.$on("$destroy", consoleService.setStreamListeners(function (response) {
                        console.log(response.data)
                    }));

                };

            init();
        });
}(angular));