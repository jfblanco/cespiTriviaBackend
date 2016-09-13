'use strict';

angular.module('facultadhumanidadesApp')
    .controller('DificultadDetailController', function ($scope, $stateParams, Dificultad) {
        $scope.dificultad = {};
        $scope.load = function (id) {
            Dificultad.get({id: id}, function(result) {
              $scope.dificultad = result;
            });
        };
        $scope.load($stateParams.id);
    });
