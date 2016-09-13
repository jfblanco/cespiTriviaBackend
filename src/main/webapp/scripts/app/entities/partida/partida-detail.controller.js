'use strict';

angular.module('facultadhumanidadesApp')
    .controller('PartidaDetailController', function ($scope, $stateParams, Partida) {
        $scope.partida = {};
        $scope.load = function (id) {
            Partida.get({id: id}, function(result) {
              $scope.partida = result;
            });
        };
        $scope.load($stateParams.id);
    });
