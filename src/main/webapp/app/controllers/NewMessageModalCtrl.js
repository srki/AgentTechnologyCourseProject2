/**
 * Created by Srđan on 12.5.2016..
 * Modal for sending new message.
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.NewMessageModalCtrl', [])
        .controller('NewMessageModalCtrl', function ($scope, $uibModalInstance) {
            $scope.send = function () {
                if (!$scope.message.performative) {
                    $scope.alertMessage = "You have to choose performative."
                } else if (!$scope.message.receivers || !$scope.message.receivers.length) {
                    $scope.alertMessage = "You have to specify at least one receiver."
                } else {
                    $scope.alertMessage = null;
                }

                if ($scope.alertMessage) {
                    return;
                }

                $scope.consoleService.sendMessage($scope.message,
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