function MyDataService() {
	return {
		getWorlds: function getWorlds() {
			return ["this world", "another world"];
		}
		
	}
}

function DemoController(worldService) {
	var vm = this;
	vm.message = worldService.getWorlds().map(function(w) {
		return "hello," + w + "!";
	})
}

function startup($rootScope, $window) {
	$window.alert("Hello, user! Loading word ...");
	$rootScope.hasStarted = true;
}
angular.module("myDemoApp", [ /* module dependencies go here */ ])
	.service("worldService", [MyDataService])
	.controller("demoController", ["worldService", DemoController])
	.config(function() {
		console.log('configuring application');
	})
	.run(["$rootScope", "$window", startup]);