'use strict';

angular.module('facultadhumanidadesApp')
    .controller('BitacoraPartidaController', function ($scope, BitacoraPartida, Pregunta, Partida) {
        $scope.bitacoraPartidas = [];
        $scope.preguntas = Pregunta.query();
        $scope.partidas = Partida.query();
        $scope.loadAll = function() {
            BitacoraPartida.query(function(result) {
               $scope.bitacoraPartidas = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            BitacoraPartida.get({id: id}, function(result) {
                $scope.bitacoraPartida = result;
                $('#saveBitacoraPartidaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bitacoraPartida.id != null) {
                BitacoraPartida.update($scope.bitacoraPartida,
                    function () {
                        $scope.refresh();
                    });
            } else {
                BitacoraPartida.save($scope.bitacoraPartida,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            BitacoraPartida.get({id: id}, function(result) {
                $scope.bitacoraPartida = result;
                $('#deleteBitacoraPartidaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BitacoraPartida.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBitacoraPartidaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBitacoraPartidaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bitacoraPartida = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
