'use strict';

angular.module('facultadhumanidadesApp')
    .controller('DificultadController', function ($scope, Dificultad) {
        $scope.dificultads = [];
        $scope.loadAll = function() {
            Dificultad.query(function(result) {
               $scope.dificultads = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Dificultad.get({id: id}, function(result) {
                $scope.dificultad = result;
                $('#saveDificultadModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.dificultad.id != null) {
                Dificultad.update($scope.dificultad,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Dificultad.save($scope.dificultad,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Dificultad.get({id: id}, function(result) {
                $scope.dificultad = result;
                $('#deleteDificultadConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Dificultad.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDificultadConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveDificultadModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dificultad = {codigo: null, estado: null, descripcion: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
