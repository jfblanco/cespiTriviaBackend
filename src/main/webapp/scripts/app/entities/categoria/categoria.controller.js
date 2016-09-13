'use strict';

angular.module('facultadhumanidadesApp')
    .controller('CategoriaController', function ($scope, Categoria) {
        $scope.categorias = [];
        $scope.loadAll = function() {
            Categoria.query(function(result) {
               $scope.categorias = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Categoria.get({id: id}, function(result) {
                $scope.categoria = result;
                $('#saveCategoriaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.categoria.id != null) {
                Categoria.update($scope.categoria,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Categoria.save($scope.categoria,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Categoria.get({id: id}, function(result) {
                $scope.categoria = result;
                $('#deleteCategoriaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Categoria.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCategoriaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCategoriaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.categoria = {descripcion: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
