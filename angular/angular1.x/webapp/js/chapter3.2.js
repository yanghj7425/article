var app = angular.module('myApp',[])
app.controller('myAppController',function($scope){
	$scope.text = 'htl';
	$scope.fruit = [
		{ label: "Apples", value: 1, id: 2 },
		{ label: "Oranges", value: 2, id: 1 },
		{ label: "Limes", value: 4, id: 4 },
		{ label: "Lemons", value: 5, id: 3 }];
		$scope.selectedFruitNgOptions = '';
		$scope.num = 2;

	$scope.change = function(selectedFruitNgOptions){
		if ((selectedFruitNgOptions || '') == ''){
			return false;
		}
		console.log(selectedFruitNgOptions);
		return true;
	}
})