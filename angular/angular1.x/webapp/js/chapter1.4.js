var app = angular.module('myapp', [])
app.controller('FristController', ['$scope', function($scope) {
	$scope.name = 'FristController'
}]);

app.controller('SecondController',['$scope', function(s){
	s.name = 'SecondController'
}])