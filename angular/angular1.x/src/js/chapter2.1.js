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
	ctrl.funClick = function(data) {
		var msg = dataService.getWorlds(data)
		ctrl.message = dataService.splitWord(msg);
		alert(msg)
	}
}