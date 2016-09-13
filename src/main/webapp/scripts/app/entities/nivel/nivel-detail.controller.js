'use strict';

angular.module('facultadhumanidadesApp')
    .controller('NivelDetailController', function ($scope, $stateParams, Nivel) {
        $scope.nivel = {};
        $scope.load = function (id) {
            Nivel.get({id: id}, function(result) {
              $scope.nivel = result;
            });
        };
        $scope.load($stateParams.id);
    });
