
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
function categorylist($scope,$http,$window,$rootScope){
   /* var roleUser = $window.localStorage["user"];
    if(roleUser!=undefined)
        $rootScope.user = JSON.parse(roleUser);*/
    $scope.choice = "";
    $scope.file="";
    $scope.price = "";
    $scope.addChar = function(){
       var char =  document.getElementById("char")
var tr = document.createElement("tr");
        tr.innerHTML = "<td><input  type='text' class='denomination'/></td>" +
                        "<td><input  type='text' class='character'/></td>"
        char.appendChild(tr);
    }
    $scope.send = function(){


       var obj = {
           files : $scope.file,
           name : $scope.name,
           price: $scope.price,
           describe : $scope.describe,
           characteristics : [],
           category : $scope.choice
       }
        var characters = document.getElementsByClassName("character");
        var denomination = document.getElementsByClassName("denomination");
        for(var i=0;i<characters.length;i++) {
            var characteristic = {
                denomination : denomination[i].value,
                content : characters[i].value
            }
            obj.characteristics.push(characteristic);

        };




        console.dir($scope.file)
        $http.post("/addItem", JSON.stringify(obj), {

            headers: {'Content-Type': 'application/json',
                        'Authorization' : 'Bearer '+$rootScope.token

            }
        })
            .success(function(){

            })
            .error(function(){
            });
    }
    $scope.list = [];
    $http.get('/category/list').
        success(function(data) {
            $scope.list = data;

        })

}
categorylist.$inject=['$scope','$http','$window','$rootScope'];
function show(state){
    document.getElementById('basket').style.display = state;

}
function signUp(){
    var name = document.getElementById("name").value;
    var surName = document.getElementById("surName").value;
    var secondName = document.getElementById("secondName").value;
    var logName = document.getElementById("logName").value;
    var passwordIn = document.getElementById("passwordIn").value;
    if(name=="" || surName=="" || secondName=="" || logName=="" || passwordIn=="") {
        alert("Заполните все поля");
        return false;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST","/signUp",true)
    xhr.setRequestHeader('Content-Type','application/json');
    var bodyRequest = {
        name : name,
        surName : surName,
        secondName : secondName,
        login : logName,
        password : passwordIn
    };
    xhr.send(JSON.stringify(bodyRequest));
    xhr.onreadystatechange = function(){
        if(xhr.responseText==='-1')
        alert("Пользователь с таким логином уже существует");
        if(xhr.responseText==='0'){
            showReg("none");
            alert("Спасибо за регистрацию, теперь вы можете войти под своим именем");
        }
    }


}
function showReg(state){
    document.getElementById('registration').style.display = state;

}
function booklist($scope, $http, $location, $rootScope,$window) {



    $rootScope.addItemForBasket = function(name,linkItem,pathToImage,price){
        var item = {"name": name,
                    "linkItem": linkItem,
                    "pathToImage":pathToImage,
                    "price": price};

        $rootScope.listOfIdItems.push(item);

       $window.localStorage["isItemInBasket"] = true;

var session = {
            'basketContent': []
        }
        session.basketContent = $rootScope.listOfIdItems.slice(0,$rootScope.listOfIdItems.length);

        $window.localStorage["basket"] = JSON.stringify(session);

       //alert( JSON.stringify($window.localStorage["basket"]));


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
booklist.$inject=['$scope','$http','$location','$rootScope','$window'];
function basket($scope,$rootScope, $http,$window){
$scope.list = [];
    $scope.customer = {
        name : "",
        surName: "",
        secondName: "",
        phone: "",
        address : "",
        items : []


    }

    if($rootScope.user!==undefined) {
        $scope.customer.name = $rootScope.user.name;
        $scope.customer.surName = $rootScope.user.surName;
        $scope.customer.secondName = $rootScope.user.secondName;

    }
    $scope.customer.items = $rootScope.listOfIdItems;
    $scope.order= function(){

        $http({
            method: 'POST',
            url: "/order",
            data: $scope.customer,
            headers: {'Content-Type': 'application/json'}
        }).success(function(data) {
            $rootScope.listOfIdItems = [];
            $window.localStorage["basket"] = [];

        })


    }

    $scope.delete = function(id){
        $rootScope.listOfIdItems.splice(id,1);

        var session = {
            'basketContent': []
        }
        session.basketContent = $rootScope.listOfIdItems.slice(0,$rootScope.listOfIdItems.length);

        $window.localStorage["basket"] = JSON.stringify(session);
    }

}
basket.$inject=['$scope','$rootScope','$http','$window'];
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
//---------------------------------------------------------------------------------//
function signIn($scope,$rootScope,$http,$route,$window) {
    $scope.login = "";
    $scope.password = "";
    var user = $window.localStorage["user"];
    var token = $window.localStorage["token"];
    /*************************************************************************/
   if (typeof user ==="string" && user.length>30) {

             console.log(token);
         $rootScope.token = token;
        $rootScope.user = JSON.parse(user);


   }
    /*************************************************************************/
var items = $window.localStorage["basket"];


   // if ($window.localStorage["isItemInBasket"])
  //  if($window.localStorage["isItemInBasket"]===true)
    if(typeof $window.localStorage["basket"] =='string' && $window.localStorage["basket"].length>30)
    {
        console.log( typeof[$window.localStorage["basket"]]);
        $rootScope.listOfIdItems = JSON.parse($window.localStorage["basket"]).basketContent;
        console.log($rootScope.listOfIdItems);
    }
    else{

        $rootScope.listOfIdItems = new Array();
        $window.localStorage["basket"] = new Array();
    }





    // 'Authorization': 'Basic Y2xpZW50YXBwOjEyMzQ1Ng=='
    $scope.signOut = function(){
        $rootScope.token=undefined;
        $window.localStorage["token"]=undefined;
        $window.localStorage["user"]=undefined;
        $rootScope.user=undefined;
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
                $window.localStorage["token"] = $rootScope.token;
                $http({
                    method: 'GET',
                    url: "/user",

                    headers: {'Content-Type': 'application/x-www-form-urlencoded',
                                'Authorization': 'Bearer '+$rootScope.token}
                }).success(function(user){
                    $rootScope.user=user;
                    $window.localStorage["user"] = JSON.stringify(user);





                })
            }
            $route.reload();

        })

    }
}
signIn.$inject=['$scope','$rootScope','$http','$route','$window'];
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
angular.module('app',['ngRoute'])
    .directive('appFilereader', function(
        $q
    ) {
        /*
         made by elmerbulthuis@gmail.com WTFPL licensed
         */
        var slice = Array.prototype.slice;

        return {
            restrict: 'A',
            require: '?ngModel',
            link: function(scope, element, attrs, ngModel) {
                if (!ngModel) return;

                ngModel.$render = function() {}

                element.bind('change', function(e) {
                    var element = e.target;
                    if(!element.value) return;

                    element.disabled = true;
                    $q.all(slice.call(element.files, 0).map(readFile))
                        .then(function(values) {
                            if (element.multiple) ngModel.$setViewValue(values);
                            else ngModel.$setViewValue(values.length ? values[0] : null);
                            element.value = null;
                            element.disabled = false;
                        });

                    function readFile(file) {
                        var deferred = $q.defer();

                        var reader = new FileReader()
                        reader.onload = function(e) {
                            deferred.resolve(e.target.result);
                        }
                        reader.onerror = function(e) {
                            deferred.reject(e);
                        }
                        reader.readAsDataURL(file);

                        return deferred.promise;
                    }

                }); //change

            } //link

        }; //return

    }) //appFilereader
    .controller('booklist',booklist)
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
            .when('/additemIn', {
                controller: 'LocationController',
                templateUrl: '/static/additem.html'
            })
            .when('/order', {
                controller: 'LocationController',
                templateUrl: '/static/Order.html'
            })
        ;
        $locationProvider.html5Mode(true);
    }
    );



