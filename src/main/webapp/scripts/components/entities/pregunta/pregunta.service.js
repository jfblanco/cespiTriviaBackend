'use strict';

angular.module('facultadhumanidadesApp')
    .factory('Pregunta', function ($resource, DateUtils) {
        return $resource('api/preguntas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
