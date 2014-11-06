'use strict';

controllers.controller('IndexController', ['$scope', '$http', '$rootScope', '$routeParams', '$window', '$location', function IndexController($scope, $http, $rootScope, $routeParams, $window, $location) {
	
	$scope.articles = [];
	
	$scope.fetchAllArticles = function() {
		
	    $http({
	      url: 'article/fetch-all',
	      method: "GET",
	      data: '',
	      headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).success(function (data, status, headers, config) {
	    	
	    	console.log(data);
	    	
	    	$scope.articles = data.articles.reverse();
	    	
	    }).error(function (data, status, headers, config) {

	    	console.error(data);
	    
	    });
		
	};
	
	$scope.fetchAllArticles();
	
}]);