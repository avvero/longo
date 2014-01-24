var flowModule = angular.module("flow", [])
flowModule.controller("flowController",
    function($scope) {
        $scope.selected = {}
        // События
        $scope.items = [
            {id: 1, name: "file1"},
            {id: 2, name: "file2"},
            {id: 3, name: "file3"}
        ];
        $scope.upload = function () {
            $scope.items.push({id: 1, name: "file1"});
        }
        $scope.select = function (obj, $event) {
            $scope.selected = obj
        }
    }
);