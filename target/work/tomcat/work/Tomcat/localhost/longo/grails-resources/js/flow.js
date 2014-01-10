
var controller;
var addItem;

$( document ).ready(function() {
    var flowModule = angular.module("flow", [])
    controller = flowModule.controller("flowController",
        function($scope) {
            $scope.items = [
                {product_name: "Product 1", price: 50},
                {product_name: "Product 2", price: 50},
            ];
            $scope.remove = function(index) {
                $scope.items.splice(index, 1);
            }
            // Add a Item to the list
            addItem = $scope.addItem = function () {

                $scope.items.push({
                    product_name: "Test",
                    price: "10"
                });

            };
        }
    );
});