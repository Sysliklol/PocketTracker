package com.mycompany.pojos;

import com.mycompany.entities.TransactionImage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class TransactionPOJO {
    @Length(min = 36, max = 36, message = "Id should have 36 symbols length")
    @NotNull(message = "Should not be null") public String placeId;

    @NotNull(message = "Should not be null") public String title;
    @NotNull(message = "Should not be null") public long cost;
    @NotNull(message = "Should not be null") public short quantity;
    @NotNull(message = "Should not be null") public TransactionImage image;

    public String description;
}
