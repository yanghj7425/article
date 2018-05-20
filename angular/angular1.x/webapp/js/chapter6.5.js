var myApp = angular.module('myApp',[])


myApp.config(function($provide){
    $provide.decorator('superman', function($delegate){
        var directive = $delegate[0];
        directive.templateUrl = 'consumer.html';
    
        return $delegate;
    })
})