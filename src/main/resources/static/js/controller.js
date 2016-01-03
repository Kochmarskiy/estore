
function LocationController($rootScope, $http, $interval,$location) {
$rootScope.loc = $location.search();
    $rootScope.func = function () {
        $http({
            method: 'POST',
            url: "/addnumber",
            data: 'number=12',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
    }
}
LocationController.$inject=['$rootScope','$http','$interval','$location'];
function categorylist($scope,$http){

    $scope.list = [];
    $http.get('/category/list').
        success(function(data) {
            $scope.list = data;

        })

}
categorylist.$inject=['$scope','$http'];
function show(state){
    document.getElementById('basket').style.display = state;

}
function booklist($scope, $http, $location, $rootScope) {


if($rootScope.listOfIdItems===undefined)
    $rootScope.listOfIdItems = [];
    $rootScope.addItemForBasket = function(name,linkItem,pathToImage,price){
        var item = {"name":name,
                    "linkItem": linkItem,
                    "pathToImage":pathToImage,
                    "price": price};
        $rootScope.listOfIdItems.push(item);
    }
$scope.category = $location.search().category;
    $scope.countPageForItems = [];
    $http.get('/count?category='+$scope.category).
        success(function(data) {
            var count = data;
            var c = 12;
            if(count%c==0)
                count = Math.floor(count/c);
            else count = Math.floor(count/c)+1;
            var arr = [];
            for(var i =0;i<count;i++)
                arr.push(i);
            $scope.countPageForItems = arr;
        });
    $scope.list="";
    $http.get('/databooks?category='+$location.search().category+"&c="+$location.search().c).
        success(function(data, status, headers, config) {
            $scope.list = data;

        })
}
booklist.$inject=['$scope','$http','$location','$rootScope'];
function basket($scope,$rootScope, $http){
$scope.list = [];

}
basket.$inject=['$scope','$rootScope','$http'];
function item($scope,$http,$location,$rootScope)
{
    $scope.item = {};
    $scope.id = $location.search().id;
    $scope.c = $location.search().c;
    $http.get('/dataitem?id='+$scope.id).
        success(function(data, status, headers, config) {
            $scope.item = data;
        })
}
function draw(){
   var search =  window.location.search;
    var reg = new RegExp("c=\d+","i");
    alert(req.exec(search));
}
item.$inject=['$scope','$http','$location','$rootScope'];
function signIn($scope,$rootScope,$http,$route){
    $scope.login ="";
    $scope.password="";
    // 'Authorization': 'Basic Y2xpZW50YXBwOjEyMzQ1Ng=='
    $scope.signOut = function(){
        $rootScope.token=undefined;
        $route.reload();
    }
    $scope.signIn = function(){
        $http({
            method: 'POST',
            url: "http://clientapp:123456@localhost:8080/oauth/token",
            data: "password="+$scope.password+"&username="+$scope.login+"&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'
                        }
        }).success(function(data) {
            if(data.access_token!=null){ $rootScope.token = data.access_token;
                $http({
                    method: 'GET',
                    url: "/user",

                    headers: {'Content-Type': 'application/x-www-form-urlencoded',
                                'Authorization': 'Bearer '+$rootScope.token}
                }).success(function(user){
                    $rootScope.user=user;;
                })
            }
            $route.reload();

        })

    }
}
signIn.$inject=['$scope','$rootScope','$http','$route'];
function comment($scope,$http,$location,$interval,$rootScope)
{
    $scope.loc = $location.search();
    $scope.delete = function(id,idOnPage){
        $http({
            method: 'POST',
            url: "/comment/delete",
             headers: {'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Bearer '+$rootScope.token},
              data : "id="+id
        });

        document.getElementById("comment"+idOnPage).innerHTML="<b>Удалено</b>";

    }
    $scope.getcomments = function(){
    $http.get('/comment/datacomment?id='+$location.search().id+'&c='+$location.search().c).
        success(function(data, status, headers, config) {
            $scope.comments = data;
        })
        $scope.countPageForComments = 0;
        $http.get('/comment/count?item='+$location.search().id).
            success(function(data, status, headers, config) {
                var count = data;
                if(count%10==0)
                count = Math.floor(count/10);
                else count = Math.floor(count/10)+1;
                var arr = [];
                for(var i =0;i<count;i++)
                arr.push(i);
                $scope.countPageForComments = arr;
            })


}
    $scope.message = {
        comment : "",
        nickName : "",
        itemId : $location.search().id
}
    $scope.comments = [];

    $scope.addcomment = function(){
        if($rootScope.token!=undefined)
            $scope.message.nickName=$rootScope.user.login;
        $http({
            method: 'POST',
            url: "/comment/addcomment",
            data: JSON.stringify($scope.message),
            headers: {'Content-Type': 'application/json'}
        }).then($scope.getcomments)
        $scope.message.comment="";
    }

    $scope.responses = [];
   $scope.getcomments();

}
comment.$inject=['$scope','$http','$location','$interval','$rootScope'];
angular.module('app',['ngRoute']).controller('booklist',booklist)
    .controller('LocationController',LocationController)
    .controller('item',item)
    .controller('comment',comment)
    .controller('categorylist',categorylist)
    .controller('basket',basket)
    .controller('signIn',signIn)
    .config(function($routeProvider, $locationProvider) {

        $routeProvider
            .when('/', {
                controller: 'LocationController',
                templateUrl: '/static/start.html'
            })
            .when('/items', {
                controller: 'LocationController',
                templateUrl: '/static/computersliterature.html'
            })
            .when('/item', {
                controller: 'LocationController',
                templateUrl: '/static/item1.html'
            }
        )
            .when('/additem', {
                controller: 'LocationController',
                templateUrl: '/static/additem.html'
            })
        ;
        $locationProvider.html5Mode(true);
    }
    );



