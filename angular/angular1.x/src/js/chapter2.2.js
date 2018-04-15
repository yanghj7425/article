var app = angular.module('myApp', [])

app.service('sampleService', [SampleService]);

app.controller('samepleController', function($scope) {
	var ctrl = this

	ctrl.msg = 'hello'
	console.log('adf')
	$scope.name = 'YHJ'

	ctrl.funClick = function(data){
		alert(data)
	}
})

function SampleService(data) {
	return {
		getWord: function getWord(data) {
			return data.split('')
		}
	}
}