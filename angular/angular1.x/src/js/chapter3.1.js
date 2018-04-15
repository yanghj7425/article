var app = angular.module('myApp',[])

app.controller('ctrl', function($scope){
	var ctrl = this
	ctrl.textInput = {
		value:'5'
	}
	ctrl.numberInput = {
		value: 5
	}
})