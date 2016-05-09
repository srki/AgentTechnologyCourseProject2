/**
 * Created by Srđan on 9.5.2016..
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

                    consoleService.getPerformatives(
                        function (response) {
                            console.log(response);
                        },
                        function (response) {

                        }
                    );

                };

            init();
        });
}(angular));