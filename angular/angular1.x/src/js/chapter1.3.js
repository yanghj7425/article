angular.module('myApp', [])
	.controller('MyController', function($scope){
		$scope.name = 'Li';
	})
	.run(function($rootScope){
		$rootScope.name = "Wan"
	})