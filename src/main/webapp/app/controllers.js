/**
 * Created by Srđan on 9.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.controllers', [
        'app.HomeCtrl',
        'app.HttpCtrl',
        'app.WebSocketCtrl'
    ]);
}(angular));