'use strict';

angular.module('facultadhumanidadesApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


