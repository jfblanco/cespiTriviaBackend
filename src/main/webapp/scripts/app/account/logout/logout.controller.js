'use strict';

angular.module('facultadhumanidadesApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
