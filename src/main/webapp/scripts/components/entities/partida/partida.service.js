'use strict';

angular.module('facultadhumanidadesApp')
    .factory('Partida', function ($resource, DateUtils) {
        return $resource('api/partidas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
