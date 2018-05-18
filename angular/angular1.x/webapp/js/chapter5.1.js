var  app = angular.module('myApp',[])
app.component('helloworld',{
	template: '<span> hello {{ $ctrl.name }}, I`m {{ $ctrl.myName }} </span>',
	bindings: {name: '@'},
	controller: function(){
		this.$onInit = function(){
			this.myName = 'Yellow ' + this.name;
		}
	}
});