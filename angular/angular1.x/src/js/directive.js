var myApp = angular.module("myApp");
myApp.directive('demoDirective', function () {
    return {
        restrict: 'EA',
        scope: {
            name: '@'
        },
        template: "<div> hello, {{name}} !</div>",
        compile: function (element, attributes) {
            element.css("border", "1px solid #ccc");

            var linkFunction = function ($scope, element, attributes) {
                element.html("Name: <b>" + $scope.name + "</b>");
                element.css("background-color", "#ff00ff");
            };
            return linkFunction;
        }
    };
});


myApp.directive('toolbar', function () {
    return {
        restrict: 'AE',
        scope: {
            value1: '=',
            value2: '=',
        },
        template: "<ul><li><a ng-click='Add()' href=''>{{value1}}</a></li><li><a ng-click='Edit()' href='#'>{{value2}}</a></li></ul>",
        link: function ($scope, element, attrs) {
            console.log('init');
            $scope.Add = function () {
                $scope.value1 = $scope.value1 + $scope.value1;
            };
            $scope.Edit = function () {
                console.log('value1: ' + $scope.value1);
                console.log('value2:' + $scope.value2);
                console.log('edit');
            }
        }
    }
});


myApp.directive('superman', function(){
    return {
        restrict: 'E',
        templateUrl: 'superman-template.html',
        controller: function(){
            this.message = 'I`m superman'
        },
        controlerAs: 'supermanCtrl',
        link: function(scope, element, addributes){
            element.on('click',function(){
                alert('I am superman !')
            })
        }
    }
});