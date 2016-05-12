/**
 * Created by Srđan on 12.5.2016..
 * Modal for creating new agent.
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.NewAgentModalCtrl', [])
        .controller('NewAgentModalCtrl', function ($scope, $uibModalInstance) {
            $scope.runAgent = function () {
                if (!$scope.name) {
                    $scope.alertMessage = "Name can not be empty."
                } else {
                    $scope.alertMessage = null;
                }

                if ($scope.alertMessage) {
                    return;
                }

                $scope.consoleService.runAgent($scope.type, $scope.name,
                    function () {
                        $scope.close();
                    },
                    function (response) {
                        $scope.alertMessage = "Error:" + response.data.message;
                    });

            };

            $scope.close = function () {
                $uibModalInstance.close();
            };
        });
}(angular));