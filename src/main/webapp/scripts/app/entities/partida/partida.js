'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partida', {
                parent: 'entity',
                url: '/partida',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.partida.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partida/partidas.html',
                        controller: 'PartidaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partida');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partidaDetail', {
                parent: 'entity',
                url: '/partida/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.partida.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partida/partida-detail.html',
                        controller: 'PartidaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partida');
                        return $translate.refresh();
                    }]
                }
            });
    });
