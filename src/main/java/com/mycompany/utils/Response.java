package com.mycompany.utils;



public class Response {

    public static String create(Status status, String message){
        return "{ \"status\": \""+ status.toString() +"\", \"message\":\"" + message +"\" }" ;
    }

    public static String create(Status status, String message, String body){
        return "{ \"status\": \""+ status.toString() +"\", \"message\":\"" + message +"\", \"body\":" + body +"}";
    }

}
