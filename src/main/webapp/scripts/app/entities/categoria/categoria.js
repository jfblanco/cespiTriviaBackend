'use strict';

angular.module('facultadhumanidadesApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('categoria', {
                parent: 'entity',
                url: '/categoria',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.categoria.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/categoria/categorias.html',
                        controller: 'CategoriaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('categoria');
                        return $translate.refresh();
                    }]
                }
            })
            .state('categoriaDetail', {
                parent: 'entity',
                url: '/categoria/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'facultadhumanidadesApp.categoria.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/categoria/categoria-detail.html',
                        controller: 'CategoriaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('categoria');
                        return $translate.refresh();
                    }]
                }
            });
    });
