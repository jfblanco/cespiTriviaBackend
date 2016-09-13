'use strict';

angular.module('facultadhumanidadesApp')
    .controller('BitacoraPartidaDetailController', function ($scope, $stateParams, BitacoraPartida, Pregunta, Partida) {
        $scope.bitacoraPartida = {};
        $scope.load = function (id) {
            BitacoraPartida.get({id: id}, function(result) {
              $scope.bitacoraPartida = result;
            });
        };
        $scope.load($stateParams.id);
    });
