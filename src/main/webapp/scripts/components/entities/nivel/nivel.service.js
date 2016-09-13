'use strict';

angular.module('facultadhumanidadesApp')
    .factory('Nivel', function ($resource, DateUtils) {
        return $resource('api/nivels/:id', {}, {
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
