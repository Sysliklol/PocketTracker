package com.mycompany.controllers;

import com.google.gson.Gson;
import com.mycompany.entities.User;
import com.mycompany.pojos.AuthPOJO;
import com.mycompany.pojos.UserPOJO;
import com.mycompany.services.UserService;
import com.mycompany.utils.ConstraintValidator;
import com.mycompany.utils.Response;
import com.mycompany.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @ResponseBody
    @RequestMapping(value={"/current"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    protected String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.isAuthenticated()){
            return Response.create(Status.Error, "No user into session");
        }

        return new Gson().toJson(authentication.getName());
    }


    @RequestMapping(value={"/verify/{userId}"}, method = RequestMethod.GET)
    protected String verifyUser(@PathVariable String userId, Model model){
        if(userId == null || userId.length() == 0) {
            return "redirect:../../error";
        }

        try {
            userService.verifyById(userId);

            model.addAttribute("msg", "You account was successfully verified");
            return "login";

        } catch (Error error){
            return "redirect:../../error";
        }

    }


    @ResponseBody
    @RequestMapping(value={"/signup"},method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    protected String signupUser(@RequestBody UserPOJO body){
        ConstraintValidator.ValidationResult result = ConstraintValidator.Instance().validateForJSON(body);

        if(result.validations.size() > 0){
            return Response.create(Status.Error, "Validation", result.message);
        }

        try {
            userService.createUser(body);

            return Response.create(Status.Success, "User was successfully created");
        }catch (Error error){
            return Response.create(Status.Error, error.getMessage());
        }
    }

}
