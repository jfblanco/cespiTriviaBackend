'use strict';

angular.module('facultadhumanidadesApp')
    .controller('PreguntaDetailController', function ($scope, $stateParams, Pregunta, Nivel, Categoria) {
        $scope.pregunta = {};
        $scope.load = function (id) {
            Pregunta.get({id: id}, function(result) {
              $scope.pregunta = result;
            });
        };
        $scope.load($stateParams.id);
    });
