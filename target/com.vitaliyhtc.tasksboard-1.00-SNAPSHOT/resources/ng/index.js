var application = angular.module('application', ['ngRoute']);

application.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when("/", {templateUrl : "/resources/ng/member-boards.html"})
        .when('/board/:boardID',{templateUrl : "/resources/ng/board-main.html"})
        .when('/printers',{template:'This is the printers Route'})
        .otherwise({redirectTo:"/"});
}]);
