//memberBoardsViewCtrl

application.controller('memberBoardsViewCtrl', function($scope, $http) {

    $http.get("/index/api/boardsCanEdit").then(function(response) {
        $scope.boardsList = response.data;
    });
    $http.get("/index/api/boardsCanViewOnly").then(function(response) {
        $scope.boardsOnlyViewList = response.data;
    });


    $scope.showCreateBoardView = function() {
        //
    };
    $scope.hideCreateBoardView = function() {
        //
    };

});
