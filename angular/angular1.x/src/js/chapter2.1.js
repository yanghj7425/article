var app = angular.module('myApp', [])
app.service('dataService', [DataServive])
app.controller('myController', ['dataService', MyController])

function DataServive(data) {
	return {
		getWorlds: function getWorlds() {
			return data + '\tdataService'
		},
		splitWord: function splitWord(data) {
			return data.split('')
		}
	}
}

function MyController(dataService) {
	var ctrl = this
	ctrl.name = 'YHJ'
	ctrl.message = dataService.splitWord(ctrl.name)
	console.log('1 :\t' + ctrl.message);
	ctrl.funClick = function(data) {
		var msg = dataService.getWorlds(data);
		console.log(msg);
		ctrl.message = dataService.splitWord(msg);
		alert(ctrl.message);
	}
}