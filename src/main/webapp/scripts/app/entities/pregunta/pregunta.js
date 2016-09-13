'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pregunta', {
                parent: 'entity',
                url: '/pregunta',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.pregunta.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pregunta/preguntas.html',
                        controller: 'PreguntaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pregunta');
                        return $translate.refresh();
                    }]
                }
            })
            .state('preguntaDetail', {
                parent: 'entity',
                url: '/pregunta/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.pregunta.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pregunta/pregunta-detail.html',
                        controller: 'PreguntaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pregunta');
                        return $translate.refresh();
                    }]
                }
            });
    });
