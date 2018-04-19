package com.mycompany.pojos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class IdPOJO {
    @Length(min = 36, max = 36, message = "Id should have 36 symbols length")
    @NotNull public String id;
}
