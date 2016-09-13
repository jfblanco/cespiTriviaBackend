'use strict';

angular.module('facultadhumanidadesApp')
    .controller('CategoriaDetailController', function ($scope, $stateParams, Categoria) {
        $scope.categoria = {};
        $scope.load = function (id) {
            Categoria.get({id: id}, function(result) {
              $scope.categoria = result;
            });
        };
        $scope.load($stateParams.id);
    });
