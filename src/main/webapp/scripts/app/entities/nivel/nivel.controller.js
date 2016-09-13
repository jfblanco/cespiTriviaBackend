'use strict';

angular.module('facultadhumanidadesApp')
    .controller('NivelController', function ($scope, Nivel) {
        $scope.nivels = [];
        $scope.loadAll = function() {
            Nivel.query(function(result) {
               $scope.nivels = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Nivel.get({id: id}, function(result) {
                $scope.nivel = result;
                $('#saveNivelModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.nivel.id != null) {
                Nivel.update($scope.nivel,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Nivel.save($scope.nivel,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Nivel.get({id: id}, function(result) {
                $scope.nivel = result;
                $('#deleteNivelConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Nivel.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNivelConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveNivelModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.nivel = {codigo: null, descripcion: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
