//boardMainViewCtrl

application.controller('boardMainViewCtrl', function($scope, $http, $route) {

    $http.get("/index/api/board/"+$route.current.params.boardID).then(function(response) {
        $scope.board = response.data;
    });

});
