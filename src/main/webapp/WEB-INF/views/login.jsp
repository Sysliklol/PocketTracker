<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sql_rt" uri="http://java.sun.com/jstl/sql_rt" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-route.js"></script>
<script src="<c:url value="/resources/SignInAng.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />">
<link rel="stylesheet" href="<c:url value="/resources/FA/css/font-awesome.min.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<head>
    <title>Pocket tracker</title>
    <style>
        .msg {
            color: darkseagreen;
            font-weight: 600;
            font-size: 17px;
        }

        .error{
            color: darkred;
            font-weight: 600;
            font-size: 17px;
        }
    </style>

</head>
<body onload='document.loginForm.username.focus();' ng-app="SignInApp">




    <!--<c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>-->

    <form name='loginForm'
          action="<c:url value='j_spring_security_check' />" method='POST'>
        <div class="container autism">

            <div class="col-md-6" style="display:inline-block" ng-controller="signIn">
                <div class="col-md-12 "><h1>Login</h1> </div>
                <div class="col-md-12 text_above_login">
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="msg">${msg}</div>
                    </c:if>
                    <br>
                    <br>

                    Login or  <a href="register">Register</a>
                </div>
                <div class="col-md-12 " >
                    <div class="mdl-textfield mdl-js-textfield input-form-login" >
                        <input class="mdl-textfield__input " type="text" id="login-field" ng-model="name" name='email' >
                        <i class="fa fa-address-card login-4eliks" aria-hidden="true"></i>
                        <label class="mdl-textfield__label" for="login-field">Email </label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield input-form-login" >
                        <i class="fa fa-lock login-4eliks" aria-hidden="true"></i>
                        <input class="mdl-textfield__input" type="password" id="password-field" ng-model="pass" name='password'>
                        <label class="mdl-textfield__label" for="password-field">Password</label>
                    </div>
                </div>
                <div class="col-md-12 login-options">
                    <div class="col-md-12 remember-me-checkbox">
                        <span><input type="checkbox" id="CheckBoxRemember" name="CheckBoxRemember" ><label >Remember me</label></span>
                    </div>
                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored login-button" id="login-button" ng-click="signin()" name="submit" type="submit"
                            value="submit" >
                        Login
                    </button>
                </div>
                <div class="col-md-12 other-logins" >
                </div>
            </div>

            <div class="col-md-6 right-column-login"  > <div class="greeter"><h3>HELLO</h3>Welcome</div></div>
        </div>

    </form>

</body>
</html>