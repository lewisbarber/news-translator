'use strict';

controllers.controller('ArticleController', ['$scope', '$compile', '$http', '$rootScope', '$routeParams', '$window', '$location', '$sce', function ArticleController($scope, $compile, $http, $rootScope, $routeParams, $window, $location, $sce) {
	
	$scope.article = {};
	
	$scope.fetchArticleByArticleId = function() {
		
	    $http({
	      url: 'article/fetch/' + $routeParams.articleId,
	      method: "GET",
	      data: '',
	      headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).success(function (data, status, headers, config) {
	    	
	    	console.log(data);
	    	
	    	$scope.article = data.article; 
	    	
	    	$scope.article.body = '<img src="' + data.article.imagePath + '" border="0" width="360" />' + data.article.body;
	    	
	    }).error(function (data, status, headers, config) {

	    	console.error(data);
	    
	    });
		
	};
	
	var trusted = {};
	
	$scope.renderHtml = function(html)
	{
		return (trusted[html] || (trusted[html] = $sce.trustAsHtml(html)));
	};
	
	$('.article').on('mouseenter', '.translated_word', function(){
		$(document).foundation();
	});
	
	$scope.fetchArticleByArticleId();
	
}]);