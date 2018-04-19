package com.mycompany.controllers;

import com.google.gson.Gson;
import com.mycompany.auth.RequestUser;
import com.mycompany.entities.Place;
import com.mycompany.entities.Transaction;
import com.mycompany.entities.User;
import com.mycompany.pojos.IdPOJO;
import com.mycompany.pojos.TransactionPOJO;
import com.mycompany.services.PlaceService;
import com.mycompany.services.TransactionService;
import com.mycompany.services.UserService;
import com.mycompany.utils.ConstraintValidator;
import com.mycompany.utils.Response;
import com.mycompany.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @Autowired
    PlaceService placeService;

    @ResponseBody
    @RequestMapping(value = {"/all"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    protected String getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((RequestUser) authentication.getPrincipal()).getUser();

        return new Gson().toJson(userService.loadTransactions(user));
    }

    @ResponseBody
    @RequestMapping(value = {"/create"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    protected String create(@RequestBody TransactionPOJO body){
        ConstraintValidator.ValidationResult result = ConstraintValidator.Instance().validateForJSON(body);

        if(result.validations.size() > 0){
            return Response.create(Status.Error, "Validation", result.message);
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((RequestUser) authentication.getPrincipal()).getUser();
        Place place = placeService.find(body.placeId);

        if(place == null){
            return Response.create(Status.Error, "There is no place with such id");
        }

        return new Gson().toJson(this.transactionService.create(user, place, body).toJson());
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    protected String delete(@RequestBody IdPOJO body){
        ConstraintValidator.ValidationResult result = ConstraintValidator.Instance().validateForJSON(body);

        if(result.validations.size() > 0){
            return Response.create(Status.Error, "Validation", result.message);
        }

        try {
            String id = this.transactionService.delete(body);

            return Response.create(Status.Success, "Successfully deleted", "{ \"id\": \""+ id +"\"}");
        }catch (Error error){
            return Response.create(Status.Error, error.getMessage());
        }
    }

}
