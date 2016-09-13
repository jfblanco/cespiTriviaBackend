'use strict';

angular.module('facultadhumanidadesApp')
    .controller('PartidaController', function ($scope, Partida) {
        $scope.partidas = [];
        $scope.loadAll = function() {
            Partida.query(function(result) {
               $scope.partidas = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Partida.get({id: id}, function(result) {
                $scope.partida = result;
                $('#savePartidaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.partida.id != null) {
                Partida.update($scope.partida,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Partida.save($scope.partida,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Partida.get({id: id}, function(result) {
                $scope.partida = result;
                $('#deletePartidaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Partida.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartidaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePartidaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.partida = {fecha: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
