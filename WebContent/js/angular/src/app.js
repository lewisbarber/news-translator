var translator = angular.module('translator', [
  'ngRoute',
  'ngSanitize',
  'controllers'
]);

translator.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

    $routeProvider.when('/', {
        templateUrl: 'view/index.html',
        controller: 'IndexController',
        title: 'News Translator :: Welcome'
    });

    $routeProvider.when('/article/:articleId', {
        templateUrl: 'view/article.html',
        controller: 'ArticleController',
        title: 'News Translator :: Article'
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    });

}]);

translator.run(function($rootScope, $http, $interpolate) {

  $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
      if (current.hasOwnProperty('$$route')) {
          $rootScope.title = current.$$route.title;
      }
  });

});

var controllers = angular.module('controllers', []);
var hashBangURLs = angular.module('hashBangURLs', []).config(['$locationProvider', function($location) {
}]);