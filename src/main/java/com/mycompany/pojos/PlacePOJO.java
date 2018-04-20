package com.mycompany.pojos;

import com.mycompany.utils.Validations;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PlacePOJO {
    @NotNull(message = "Should not be null") public String title;
    @NotNull(message = "Should not be null") public double latitude;
    @NotNull(message = "Should not be null") public double longitude;
}
