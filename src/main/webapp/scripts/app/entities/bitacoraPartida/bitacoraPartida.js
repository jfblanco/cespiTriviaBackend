'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bitacoraPartida', {
                parent: 'entity',
                url: '/bitacoraPartida',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.bitacoraPartida.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bitacoraPartida/bitacoraPartidas.html',
                        controller: 'BitacoraPartidaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bitacoraPartida');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bitacoraPartidaDetail', {
                parent: 'entity',
                url: '/bitacoraPartida/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.bitacoraPartida.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bitacoraPartida/bitacoraPartida-detail.html',
                        controller: 'BitacoraPartidaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bitacoraPartida');
                        return $translate.refresh();
                    }]
                }
            });
    });
