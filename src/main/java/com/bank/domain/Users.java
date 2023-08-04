package com.bank.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Users {
    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private TypeOfCard typeOfCard;

    @Digits(integer = 16, fraction = 0)
    @Size(min = 16, max = 16)
    private String numberCard;

    @Min(1)
    @Max(12)
    private Integer expirationMonth;

    @Min(23)
    @Max(99)
    private Integer expirationYear;
    private int balance;
}
