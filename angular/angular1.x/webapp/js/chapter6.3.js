var myApp = angular.module('myApp', []);

myApp.controller('costumeController', CostumeController);

CostumeController.$ingect = ['$scope'];

function CostumeController($scope) {
 
    $scope.value1 = 'Add';
    $scope.value2 = 'Edit';

    $scope.cli = function(){
        console.log( $scope.value1 );
        console.log( $scope.value2 );
        $scope.value1 = $scope.value1 + $scope.value1;
      
    }
}