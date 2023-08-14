package com.bank.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "bank_users")
public class Users {

    @Id
    @SequenceGenerator(name = "SeqGen", sequenceName = "bank_users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "SeqGen")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "type_card")
    @Enumerated(EnumType.STRING)
    private TypeOfCard typeOfCard;

    @Digits(integer = 16, fraction = 0)
    @Size(min = 16, max = 16)
    @Column(name = "number_card")
    private String numberCard;

    @Column(name = "balance")
    private int balance;
}
