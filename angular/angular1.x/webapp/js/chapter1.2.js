function MyDataService() {
	return {
		getWorlds: function getWorlds() {
			return ["this world", "another world"];
		}

	}
}

function DemoController(worldService) {
	var vm = this;
	vm.message = worldService.getWorlds().map(function (w) {
		return "hello," + w + "!";
	});


	vm.fruit = [
		{ label: "Apples", value: 4, id: 2 },
		{ label: "Oranges", value: 2, id: 1 },
		{ label: "Limes", value: 4, id: 4 },
		{ label: "Lemons", value: 5, id: 3 }
	];
}

function startup($rootScope, $window) {
	$window.alert("Hello, user! Loading word ...");
	$rootScope.hasStarted = true;
}

angular.module("myDemoApp", [ /* module dependencies go here */])
	.service("worldService", [MyDataService])
	.controller("demoController", ["worldService", DemoController])
	.config(function () {
		console.log('configuring application');
	})
	.run(["$rootScope", "$window", startup]);