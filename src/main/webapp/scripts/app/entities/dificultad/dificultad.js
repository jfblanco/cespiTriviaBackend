'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dificultad', {
                parent: 'entity',
                url: '/dificultad',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.dificultad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dificultad/dificultads.html',
                        controller: 'DificultadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dificultad');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dificultadDetail', {
                parent: 'entity',
                url: '/dificultad/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.dificultad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dificultad/dificultad-detail.html',
                        controller: 'DificultadDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dificultad');
                        return $translate.refresh();
                    }]
                }
            });
    });
