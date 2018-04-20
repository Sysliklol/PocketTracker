<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sql_rt" uri="http://java.sun.com/jstl/sql_rt" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<html lang="en">
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-route.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="<c:url value="/resources/HomeAng.js"/>"></script>
<script src="<c:url value="/resources/sweetalert2.all.min.js"/>"></script>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/chartist/0.11.0/chartist.min.css">
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/home.css" />">
<link rel="stylesheet" href="<c:url value="/resources/FA/css/font-awesome.min.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<title>Pocket tracker</title>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyASUPM6DDH2ce4YFPy_v95lCrlcybs71hs&callback=myMap"
        type="text/javascript"></script>
<head>

</head>

<body ng-app="HomeApp" >
<div ng-view>


</div>
<div class="popup_place" id="popup_place" ng-controller="map" style="display:none">
    <div class="container">
    <div class="close" id="close_place" ng-click="hide_map()">X</div>
    <h1>Add place</h1>

    <input type="text" class="form-control col-md-5" placeholder="Place name" ng-model="place_name">
    <input type="text" class="form-control col-md-3"  id="lat" ng-model="lat" disabled >
    <input type="text" class="form-control col-md-3" id="lng" ng-model="lng" disabled >
    <div class="col-md-12" id="googleMap" style="width:100%;height:400px;"></div>
        <button type="button"  class="btn btn-primary mrg"  data-toggle="modal" data-target="#myModal"  ng-click="addplace()">
            Add
        </button>
    </div>
</div>
</div>
</body>
<script>
    function myMap() {
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
            var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
             marker = new google.maps.Marker({
                position: myLatLng,
                map: map
            });

            map.addListener('click', function(e) {
                placeMarkerAndPanTo(e.latLng, map);
            });
            }

            function placeMarkerAndPanTo(latLng, map) {
                if (marker && marker.setMap) {
                    marker.setMap(null);
                }
                marker = new google.maps.Marker({
                    position: latLng,
                    map: map
                });
                 map.panTo(latLng);
                $('#lat').val(latLng.lat().toFixed(4));
                $('#lng').val(latLng.lng().toFixed(4));

             }
    }
</script>
</html>