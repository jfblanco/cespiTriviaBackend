'use strict';

angular.module('facultadhumanidadesApp')
    .controller('PreguntaController', function ($rootScope, $scope, $resource, Pregunta, Nivel, Categoria, Dificultad, Upload) {
        $scope.preguntas = [];
        $scope.nivels = Nivel.query();
        $scope.categorias = Categoria.query();
        $scope.dificultades = Dificultad.query();
        $scope.errorMsg = "";
        $scope.preguntaFilter = "";
        $scope.orderByModel = "";
        
        $scope.picFile = null;
        $scope.croppedDataUrl = null;
        
        $scope.fileResponse = null;
        $scope.imagen = null;
        $scope.myImage='';
        $scope.myCroppedImage='';
        
        $scope.progress = 0;
        
        $scope.loadAll = function() {
            Pregunta.query(function(result) {
               $scope.preguntas = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Pregunta.get({id: id}, function(result) {
                $scope.pregunta = result;
                $scope.pregunta.nivels = $scope.pregunta.nivels;
                $scope.pregunta.categorias = $scope.pregunta.categorias;
                $scope.pregunta.dificultades = $scope.pregunta.dificultades;
                $('#savePreguntaModal').modal('show');
            });
        };

        $scope.save = function (croppedDataUrl,name) {
            if($scope.myCroppedImage != '')
                $scope.uploadFiles(croppedDataUrl,name);
            else
                $scope.savePregunta();
        };

        $scope.delete = function (id) {
            Pregunta.get({id: id}, function(result) {
                $scope.pregunta = result;
                $('#deletePreguntaConfirmation').modal('show');
            });
        };
        
        $scope.abrirImagen = function (id) {
            var ImagenService = $resource('api/imagen');
            ImagenService.get({id: id}, function(result) {
                $scope.imagen = result.data;
                $('#abrirImagenModal').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Pregunta.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePreguntaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePreguntaModal').modal('hide');
            $scope.clear();
        };
        
        $scope.toggleCheckCategoria = function (categoria) {
            if ($scope.pregunta.categorias.indexOf(categoria) === -1) {
                $scope.pregunta.categorias.push(categoria);
            } else {
                $scope.pregunta.categorias.splice($scope.pregunta.categorias.indexOf(categoria), 1);
            }
        };
        
        $scope.toggleCheckNivel = function (nivel) {
            if ($scope.pregunta.nivels.indexOf(nivel) === -1) {
                $scope.pregunta.nivels.push(nivel);
            } else {
                $scope.pregunta.nivels.splice($scope.pregunta.nivels.indexOf(nivel), 1);
            }
        };
        
        $scope.toggleCheckDificultad = function (dificultad) {
            if ($scope.pregunta.dificultades.indexOf(dificultad) === -1) {
                $scope.pregunta.dificultades.push(dificultad);
            } else {
                $scope.pregunta.dificultades.splice($scope.pregunta.dificultades.indexOf(dificultad), 1);
            }
        };

        $scope.clear = function () {
            $scope.pregunta = {descripcion: null, opcion1: null, opcion2: null, opcion3: null, user: {login: "system"}, correcta: null, id: null, nivels : [], categorias : [], dificultades: []};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
            $scope.picFile = null;
            $scope.myImage='';
            $scope.myCroppedImage='';
        };
        
        $scope.uploadFiles = function (dataUrl, name) {
            Upload.upload({
                url: 'api/imagen',
                data: {
                    file: Upload.dataUrltoBlob(dataUrl, name)
                },
            }).then(function (response) {
                $scope.pregunta.imagen = response.data;
                $scope.savePregunta();
            }, function (response) {
                alert("No hemos podido guardar la pregunta, intente nuevamente");
            }, function (evt) {
                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
            });
        }
        
        $scope.savePregunta = function(){
            if($rootScope.username != null)
                $scope.pregunta.user.login = $rootScope.username.login;
            if ($scope.pregunta.id != null) {
                Pregunta.update($scope.pregunta,
                    function () {
                        $scope.clear();
                        $scope.refresh();
                    });
            } else {
                Pregunta.save($scope.pregunta,
                    function () {
                        $scope.clear();
                        $scope.refresh();
                    });
            }
        }
        
        $scope.obtenerNiveles = function(pregunta){
            var niveles = "";
            pregunta.nivels.forEach(function(nivel){
                niveles += nivel.descripcion[0]+nivel.descripcion[1]+nivel.descripcion[2]+" ";                
            });
            return niveles ;
        }
        
        $scope.obtenerDificultades = function(pregunta){
            var dificultades = "";
            pregunta.dificultades.forEach(function(dificultad){
                dificultades += dificultad.descripcion[0]+dificultad.descripcion[1]+dificultad.descripcion[2]+" ";                
            });
            return dificultades ;
        }
        
        $scope.obtenerCategorias = function(pregunta){
            var categorias = "";
            pregunta.categorias.forEach(function(categoria){
                categorias += categoria.descripcion[0]+categoria.descripcion[1]+categoria.descripcion[2]+" ";                
            });
            return categorias ;
        }
        
        $scope.ordenarPorNiveles = function(){
            if($scope.orderByModel == "-nivels")
                $scope.orderByModel = "+nivels";
            else    
                $scope.orderByModel = "-nivels";
        }
        
        $scope.ordenarPorDificultades = function(){
            if($scope.orderByModel == "-dificultades")
                $scope.orderByModel = "+dificultades";
            else
                $scope.orderByModel = "-dificultades";
        }
        
        $scope.ordenarPorCategorias = function(){
            if($scope.orderByModel == "-categorias")
                $scope.orderByModel = "+categorias";
            else
                $scope.orderByModel = "-categorias";
        }
        
        var handleFileSelect=function(evt) {
          var file=evt.currentTarget.files[0];
          var reader = new FileReader();
          reader.onload = function (evt) {
            $scope.$apply(function($scope){
              $scope.myImage=evt.target.result;
            });
          };
          reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);
    });
