'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('nivel', {
                parent: 'entity',
                url: '/nivel',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.nivel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nivel/nivels.html',
                        controller: 'NivelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nivel');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nivelDetail', {
                parent: 'entity',
                url: '/nivel/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.nivel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nivel/nivel-detail.html',
                        controller: 'NivelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nivel');
                        return $translate.refresh();
                    }]
                }
            });
    });
