let HomeApp =  angular.module('HomeApp', ["ngRoute"]);
let labels = [new Date(new Date().setDate(new Date().getDate()-6)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate()-5)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate()-4)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate()-3)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate()-2)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate()-1)).toLocaleDateString("fr-CA"),new Date(new Date().setDate(new Date().getDate())).toLocaleDateString("fr-CA")];
let purchases = null;
let currPurchase = null;

HomeApp.config(($routeProvider) => {
    $routeProvider
        .when("/", {
            templateUrl : "resources/Home/ControllCash.htm"
        })
        .when("/operations", {
            templateUrl : "resources/Home/AllCashOps.htm"
        })
        .when("/singlePurchase",{
            templateUrl : "resources/Home/SingleOperationInfo.htm"
        })
        .when("/infoHome",{
            templateUrl : "resources/Home/InfoHome.htm"
        })
});
var options;

function error(err) {
    console.warn(`ERROR(${err.code}): ${err.message}`);
}
HomeApp.controller('map',($scope,$rootScope,$http)=> {

    $scope.hide_map = function () {
        $('#popup_place').hide();
    };
    angular.element(document).ready(function () {
        setTimeout(() => {
            $scope.options = $rootScope.options;

        }, 10000)

/**/});


        var map;
        var marker;
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        }

        function showPosition(position) {
            var myLatLng = {lat: position.coords.latitude, lng: position.coords.longitude};
            var mapProp = {
                center: myLatLng,
                zoom: 10,
            };
            $scope.map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
            $scope.marker = new google.maps.Marker({
                position: myLatLng,
                map:  $scope.map
            });

            $scope.map.addListener('click', function (e) {
                placeMarkerAndPanTo(e.latLng, map);
            });
        }

        function placeMarkerAndPanTo(latLng, map) {
            if ( $scope.marker &&  $scope.marker.setMap) {
                $scope.marker.setMap(null);
            }
            $scope.marker = new google.maps.Marker({
                position: latLng,
                map: $scope.map
            });
            $scope.map.panTo(latLng);
            $('#lat').val(latLng.lat().toFixed(4));
            $('#lng').val(latLng.lng().toFixed(4));

        }


/**/





        $scope.addplace = function () {
            $http.post('/places/create', {
                "title": $scope.place_name,
                "latitude": parseFloat($('#lat').val()),
                "longitude": parseFloat($('#lng').val())
            }).then(response => {
                swal({
                    position: 'top-end',
                    type: 'success',
                    title: 'added',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
        };

        $scope.update = function(){
            let lat = $scope.place_delete.latitude;
            let lng =   $scope.place_delete.longitude;
            let latLng = {lat: lat,lng: lng};
            $scope.map.panTo(latLng);
            console.log($scope.place_delete);
            if ( $scope.marker &&  $scope.marker.setMap) {
                $scope.marker.setMap(null);
            }
            $scope.marker = new google.maps.Marker({
                position: latLng,
                map:  $scope.map
            });
        };

        $scope.delete_place=  function(){
            $http.post('/places/delete',{
                "id":  $scope.place_delete.id
            }).then((response,err)=>{
                if(!err){
                    swal({
                        position: 'top-end',
                        type: 'success',
                        title: 'Deleted',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
                else {

                    swal({
                        position: 'top-end',
                        type: 'error',
                        title: 'error',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    });

HomeApp.controller('stats',($scope,$rootScope,$http)=>{
    angular.element(document).ready(() => {

            $scope.purchcount = 0;
            $scope.inccount = 0;
            $scope.purchases = purchases;

            $scope.types= {};
            $scope.types_chart = [];

            $scope.types_sum={};
            $scope.types_chart_sum=[];

            $scope.years={};
            $scope.years_chart=[];


            $scope.date = new Date().toLocaleDateString("fr-CA").substring(0,4);
            $scope.purchases.forEach(elem => {

                let transaction_date = new Date(elem.createdAt).toLocaleDateString("fr-CA").substring(0,4);

                if(!$scope.types[elem.image]){

                    $scope.types[elem.image]=1;

                    if(elem.cost>0) $scope.types_sum[elem.image]=parseInt(elem.cost);
                    else  $scope.types_sum[elem.image]=parseInt(elem.cost)*(-1);
                }
                else {
                    $scope.types[elem.image]++;

                    if(elem.cost>0) $scope.types_sum[elem.image]+=parseInt(elem.cost);
                    else $scope.types_sum[elem.image]+=parseInt(elem.cost*(-1));
                }

                if (elem.cost > 0) {
                    $scope.inccount += parseInt(elem.cost);
                }
                else {
                    $scope.purchcount += (parseInt(elem.cost) * (-1));
                }

                if(!$scope.years[transaction_date]){
                    if(elem.cost>0) $scope.years[transaction_date]=parseInt(elem.cost);
                    else  $scope.years[transaction_date]=parseInt(elem.cost)*(-1);
                }
                else{
                    if(elem.cost>0) $scope.years[transaction_date]+=parseInt(elem.cost);
                    else $scope.years[transaction_date]+=parseInt(elem.cost*(-1));
                }

            });

            $.each( $scope.types_sum, (key, value ) => {
                let color = getRandomColor();
                $('#types_sum_stats').append('<div class="col-md-4"><span style="background:'+color+';width:20px;margin-top:5px;height:16px;display:inline-block;padding-top:3px"></span> '+key+' </div>');
                $scope.types_chart_sum.push({
                    title: key,
                    value: value,
                    color: color
                })
            });

            $.each( $scope.types, ( key, value ) => {
                let color = getRandomColor();
                $('#types_stats').append('<div class="col-md-4"><span style="background:'+color+';width:20px;margin-top:5px;height:16px;display:inline-block;padding-top:3px"></span> '+key+' </div>');
                $scope.types_chart.push({
                    title: key,
                    value: value,
                    color: color
                })
            });

            $.each( $scope.years, ( key, value ) => {
                let color = getRandomColor();
                $('#dates_stats').append('<div class="col-md-4"><span style="background:'+color+';width:20px;margin-top:5px;height:16px;display:inline-block;padding-top:3px"></span> '+key+' </div>');
                $scope.years_chart.push({
                    title: key,
                    value: value,
                    color: color
                })
            });

            $(function(){
                $("#sum_stats").text(($scope.purchcount+$scope.inccount)+" grn");
                $("#pieChart").drawPieChart([
                    { title: "Purchases",    value:  $scope.purchcount,   color: "#fe4400" },
                    { title: "Incomes",          value:  $scope.inccount,   color: "#018ab6" }
                ]);
                $("#pieChartType").drawPieChart($scope.types_chart);
                $("#pieChartTypeSum").drawPieChart($scope.types_chart_sum);
                $("#pieChartDate").drawPieChart($scope.years_chart);
         });
    })

});
HomeApp.controller('addPurchase',($scope, $http,$rootScope, $location) => {

    angular.element(document).ready(function () {
        console.log($rootScope.options);
        if(!$rootScope.options){
            $http.get('/places/all').then(response=>{
                options=response.data;
                $rootScope.options=response.data;
                $scope.options=response.data;
            })
        }
        else {
            options=$rootScope.options;
            $scope.options=$rootScope.options;
        }
    });
    $scope.message = "added ur purchase! :)";
    $scope.addpurchase = function () {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        }

    };
    $scope.getCharts = function (){
        if(purchases) {
            getDates();
            getCountPurch();
        }
    };
    function showPosition(position) {
        $scope.latitude = position.coords.latitude;
        $scope.longtitude = position.coords.longitude;
        $http.post("/transactions/create", {
            "title": $scope.title_purchase,
            "cost": -parseInt($scope.ammount_purchase),
            "quantity": parseInt($scope.description_purchase),
            "image": $scope.type_purchase,
            "placeId": $scope.place_purchase.id

        }).then((response,err)=>{
            if(!err){
            swal({
                position: 'top-end',
                type: 'success',
                title: 'Added',
                showConfirmButton: false,
                timer: 2000
            });
            getPurchase($http);
            }
        });
    }
});

HomeApp.controller('userPurchase', ($scope, $http,$rootScope)=> {
    if (purchases == null) {
    angular.element(document).ready(function () {
        getPurchase($http);
    });
    }
    $scope.map = function(){
        $('#popup_place').show();
    }

});

HomeApp.controller('allPurchase', ($scope, $location, $http) => {
    if(purchases!=null){
    purchases.sort(function (a,b) {
        return b.createdAt - a.createdAt;
    })}
        $scope.purchases = purchases;
    $scope.sortBy = function(){
        $scope.purchases = purchases;
           if($scope.sort_by=="Price"){
               $scope.purchases.sort(function (a, b) {
                    return b.cost - a.cost;
                });
            }
            else if($scope.sort_by=="Type"){
               $scope.purchases.sort(function (a, b) {
                    return (b.type) - (a.type);
               });
            }
           else if($scope.sort_by=="Income"){
               $scope.purchases =[];
               purchases.forEach(elem=>{

                   if(elem.cost>0) $scope.purchases.push(elem);
               })
           }
           else if($scope.sort_by=="Spending"){
               $scope.purchases =[];
               purchases.forEach(elem=>{
                   if(elem.cost<0) $scope.purchases.push(elem);
               })
           }
            else{
               $scope.purchases.sort(function (a, b) {
                    return b.createdAt - a.createdAt;
                });
            }
    };

    $scope.getPurchasesUser = function(id){
        $location.path( "/singlePurchase" );
        purchases.forEach(item => {
            if(item.id == id) currPurchase = item;
        })




    }



});

HomeApp.controller('singlePurchase',($scope, $http,$rootScope,$location) =>{

    $scope.curMap = function(){
        $rootScope.options.forEach(elem=>{
            if(elem.id==$scope.purchase.placeId){
                myLatLng = {lat: elem.latitude, lng: elem.longitude};
                let map = new google.maps.Map(document.getElementById('map'), {
                    center: myLatLng,
                    zoom: 15
                });
                let marker = new google.maps.Marker({
                    position: myLatLng,
                    map: map,
                    title: 'purchase'
                });
            }
        });
    };

    $scope.img="https://a.suitsupplycdn.com/image/upload/v1519740025/suitsupply/homepage/ss18/week09/v2/newarrivals_858.jpg";

    $scope.getSinglePurchase = function(){
        $scope.purchase = currPurchase;
        if($scope.purchase.cost>0){
            $scope.right_word="Income"

        }
        else{
            $scope.right_word="Spending";
            $scope.purchase.cost =  $scope.purchase.cost*=(-1);
        }
        let myLatLng;

        if($rootScope.options){
            $scope.curMap();
        }
        else {
            $http.get('/places/all').then(response=>{
                options=response.data;
                $rootScope.options=response.data;
                $scope.options=response.data;
                $scope.curMap();
            })
        }



        switch($scope.purchase.image)    {
            case("Food"):{$scope.img="https://cdn2.hubspot.net/hubfs/322787/Mychefcom/images/BLOG/Header-Blog/photo-culinaire-pexels.jpg"; break;}
            case("Clothes"):{$scope.img="https://a.suitsupplycdn.com/image/upload/v1519740025/suitsupply/homepage/ss18/week09/v2/newarrivals_858.jpg"; break;}
            case("Electronics"):{$scope.img="https://www.partners-in-harvest.org/wp-content/uploads/2017/12/p1500669900108.jpg";break;}
            default:{$scope.img="https://a.suitsupplycdn.com/image/upload/v1519740025/suitsupply/homepage/ss18/week09/v2/newarrivals_858.jpg"; break;}
        }

    };

        $scope.delete= function(){
        $http.post('/transactions/delete',{
            "id": $scope.purchase.id
        }).then((response,err)=>{
            if(!err) {
                getPurchase($http);
                $location.path( "/operations" );
            }
            })
    }

});

HomeApp.controller('addIncome',($scope,$http,$rootScope) =>{
    $scope.message = "added ur income! :)";

    angular.element(document).ready(function () {
          setTimeout(()=>{
              $scope.options=options;
          },10000)

    });
    $scope.addincome = function () {
        $http.post("/transactions/create", {
            "title": $scope.title_income,
            "quantity": 1,
            "image": $scope.type_income,
            "cost": parseInt($scope.ammount_income),
            "placeId": $scope.place_income.id
        }).then(function(response,err) {

            if (!err) {
                swal({
                    position: 'top-end',
                    type: 'success',
                    title: 'Added',
                    showConfirmButton: false,
                    timer: 2000
                });
                getPurchase($http);
            }
        });
       $scope.update= function(){
           $scope.options=options;
        }
    }

});

function getPurchase($http){
    $http.get("/transactions/all", {}).then(function (response) {

        purchases = response.data;
        purchases.forEach(function(x){
            x.date = new Date(x.createdAt).toLocaleDateString("fr-CA");


            switch(x.image){
                case("Food"):{x.img="https://cdn2.hubspot.net/hubfs/322787/Mychefcom/images/BLOG/Header-Blog/photo-culinaire-pexels.jpg"; break;}
                case("Clothes"):{x.img="https://a.suitsupplycdn.com/image/upload/v1519740025/suitsupply/homepage/ss18/week09/v2/newarrivals_858.jpg"; break;}
                case("Electronics"):{x.img="https://www.partners-in-harvest.org/wp-content/uploads/2017/12/p1500669900108.jpg";break;}
                default:{x.img="https://cdn2.hubspot.net/hubfs/322787/Mychefcom/images/BLOG/Header-Blog/photo-culinaire-pexels.jpg"; break;}
            }
        });
        getDates();
        getCountPurch();
    });
}


function getDates(){
    let data  = [0,0,0,0,0,0,0];
    purchases.forEach(function(x){
        if(x.date){

        switch(x.date){
            case(new Date(new Date().setDate(new Date().getDate())).toLocaleDateString("fr-CA")):{data[6]+=parseInt(x.cost);  break;}
            case(new Date(new Date().setDate(new Date().getDate()-1)).toLocaleDateString("fr-CA")):{data[5]+=parseInt(x.cost); break;}
            case(new Date(new Date().setDate(new Date().getDate()-2)).toLocaleDateString("fr-CA")):{data[4]+=parseInt(x.cost); break;}
            case(new Date(new Date().setDate(new Date().getDate()-3)).toLocaleDateString("fr-CA")):{data[3]+=parseInt(x.cost); break;}
            case(new Date(new Date().setDate(new Date().getDate()-4)).toLocaleDateString("fr-CA")):{data[2]+=parseInt(x.cost); break;}
            case(new Date(new Date().setDate(new Date().getDate()-5)).toLocaleDateString("fr-CA")):{data[1]+=parseInt(x.cost); break;}
            case(new Date(new Date().setDate(new Date().getDate()-6)).toLocaleDateString("fr-CA")):{data[0]+=parseInt(x.cost); break;}
        }}
    });

   createGraph(data,"myChart","Money spent");
}

function getCountPurch(){
    let data  = [0,0,0,0,0,0,0];
    purchases.forEach(function(x){
        if(x.date){
            switch(x.date){
                case(new Date(new Date().setDate(new Date().getDate())).toLocaleDateString("fr-CA")):{data[6]++; break}
                case(new Date(new Date().setDate(new Date().getDate()-1)).toLocaleDateString("fr-CA")):{data[5]++; break;}
                case(new Date(new Date().setDate(new Date().getDate()-2)).toLocaleDateString("fr-CA")):{data[4]++; break;}
                case(new Date(new Date().setDate(new Date().getDate()-3)).toLocaleDateString("fr-CA")):{data[3]++; break;}
                case(new Date(new Date().setDate(new Date().getDate()-4)).toLocaleDateString("fr-CA")):{data[2]++; break;}
                case(new Date(new Date().setDate(new Date().getDate()-5)).toLocaleDateString("fr-CA")):{data[1]++; break;}
                case(new Date(new Date().setDate(new Date().getDate()-6)).toLocaleDateString("fr-CA")):{data[0]++; break;}
            }}
    });
    createGraph(data,"myChartCount","Purchases Count");
}

function createGraph(data,elementName,title){
    try {
        let ctx = document.getElementById(elementName).getContext('2d');
        let chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: title,
                    backgroundColor: 'rgba(255,255,255,0.6)',
                    borderColor: '#FFFFFF',
                    data: data,
                }]
            },

            /*options: {scales: {
                    yAxes: [
                        {
                            ticks: {beginAtZero:true,max:data.max()*1.3}
                        }
                    ]
                }}*/
        });
    }
    catch(err){

    }
}



function show(name) {
    let x = document.getElementById("col-menu");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}




/*


*/



function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}