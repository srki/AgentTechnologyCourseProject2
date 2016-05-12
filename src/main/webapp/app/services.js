/**
 * Created by Srđan on 9.5.2016..
 * Angular services module
 */
/*global angular*/
(function (angular) {
    "use strict";

    angular.module('app.services', [
        'app.HttpConsole',
        'app.WebSocketConsole'
    ]);
}(angular));