var app = angular.module('myApp', [])

app.controller('parentController', function($scope){
	$scope.parentVariable = 'I`m parent ! '

	$scope.parentClick = function(){
		alert('b')
	}
})

app.controller('childrenController', function($scope){
	$scope.childrenVariable = 'I`m child !'

	$scope.childrenClick = function(){
		alert('a')
		$scope.parentVariable = 'Override !'
		$scope.childrenVariable = 'child !'
		console.log($scope.parentVariable)
	}
})