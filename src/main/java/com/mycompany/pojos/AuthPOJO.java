package com.mycompany.pojos;

import com.mycompany.utils.Validations;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AuthPOJO {
    @Pattern(regexp = Validations.emailRegex, message = "Email should have valid format")
    @NotNull(message = "Should not be null") public String email;
    @NotNull(message = "Should not be null") public String password;
}
