package com.bank.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardInfo {
    private String firstName;
    private String lastName;
    @Digits(integer = 16, fraction = 0)
    @Size(min = 16, max = 16)
    private String numberCard;

    @Size(min = 1, max = 2)
    @Min(1)
    @Max(12)
    private Integer expirationMonth;

    @Size(min = 2, max = 2)
    @Min(23)
    private Integer expirationYear;
}
