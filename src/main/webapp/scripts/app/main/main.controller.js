'use strict';

angular.module('facultadhumanidadesApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    }).config(function ($stateProvider) {

        $stateProvider.state('homePage', {
            views: {
                'nivels@': {
                    templateUrl: '../entities/nivel/nivels.html',
                    controller: 'NivelController'
                },
                'dificultads@': {
                    templateUrl: '../entities/dificultad/dificultads.html',
                    controller: 'DificultadController'
                },
                'categorias@': {
                    templateUrl: '../entities/categoria/categorias.html',
                    controller: 'CategoriaController'
                },
                'preguntas@': {
                    templateUrl: '../entities/pregunta/preguntas.html',
                    controller: 'PreguntaController'
                }
            }
        });
        
    });
