/**
 * Created by Srđan on 9.5.2016..
 */
/*global angular*/
(function (angular) {
    "use strict";
    angular.module('app', ['app.controllers', 'app.services', 'ngRoute', 'ngAnimate', 'ui.bootstrap'])
        .config(function ($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'partials/home.html',
                    controller: 'HomeCtrl'
                })
                .when('/http', {
                    templateUrl: 'partials/console.html',
                    controller: 'HttpCtrl'
                })
                .when('/websocket', {
                    templateUrl: 'partials/console.html',
                    controller: 'WebSocketCtrl'
                })
                .otherwise('/');
            $locationProvider.html5Mode(true);
        })
}(angular));