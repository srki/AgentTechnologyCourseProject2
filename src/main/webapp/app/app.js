/**
 * Created by Srđan on 9.5.2016..
 * Angular main app module
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
                .when('/agent_console', {
                    templateUrl: 'partials/console.html',
                    controller: 'ConsoleCtrl'
                })
                .otherwise('/');
            $locationProvider.html5Mode(true);
        })
}(angular));