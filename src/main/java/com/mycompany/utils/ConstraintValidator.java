package com.mycompany.utils;

import com.google.gson.Gson;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConstraintValidator {
    private Validator validator;
    private static ConstraintValidator instance;


    private ConstraintValidator(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    public static ConstraintValidator Instance(){
        if(ConstraintValidator.instance == null){
            ConstraintValidator.instance = new ConstraintValidator();
        }

        return  ConstraintValidator.instance;
    }

    public ValidationResult validate(Object obj){
        Set<ConstraintViolation<Object>> set = this.validator.validate(obj);
        StringBuilder message = new StringBuilder();

        for (ConstraintViolation<Object> s: set) {
            message.append(s.getInvalidValue()).append(" - ").append(s.getMessage()).append("\n");
        }

        return new ValidationResult(set, message.toString());
    }

    public ValidationResult validateForJSON(Object obj){
        Set<ConstraintViolation<Object>> set = this.validator.validate(obj);
        Map<String,String> map = new HashMap<>();

        for (ConstraintViolation<Object> s: set) {
            map.put(s.getPropertyPath().toString(), s.getMessage());
        }

        return new ValidationResult(set,  new Gson().toJson(map));
    }



    public Validator getValidator() {
        return validator;
    }

    public class ValidationResult{

        ValidationResult(Set<ConstraintViolation<Object>> validations, String message){
            this.validations = validations;
            this.message = message;
        }

        public Set<ConstraintViolation<Object>> validations;
        public String message;
    }
}
