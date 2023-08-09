package com.bank.controller;

import com.bank.domain.CardInfo;
import com.bank.domain.TransferOperations;
import com.bank.domain.Users;
import com.bank.domain.Users2;
import com.bank.service.BankService;
import com.bank.service.BankService2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final BankService2 bankService;

    public TestController(BankService2 bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<Users2>> getUsers() {
        List<Users2> users = bankService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users2> getUser(@PathVariable Integer id) {
        Users2 user = bankService.getUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/card-info/{id}")
    public ResponseEntity<CardInfo> getCardInfo(@PathVariable Integer id) {
        CardInfo cardInfo = bankService.getCardInfo(id);
        if (cardInfo != null) {
            return new ResponseEntity<>(cardInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Добавляем пользователя", description = "Мы добавляем пользователя, нужно на вход передать json UserInfo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Мы успешно создали пользователя"),
            @ApiResponse(responseCode = "409", description = "Не получилось создать пользователя"),
            @ApiResponse(responseCode = "400", description = "Ошибка со стороны клиента"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createBankingUser(@Valid @RequestBody Users2 user, BindingResult bindingResult) {

        Users2 userSave;
        Users2 userResult;

        if (!bindingResult.hasErrors()) {
            userSave = bankService.createUser(user);
            userResult = bankService.getUser(userSave.getId());
        } else {
            System.out.println(bindingResult.getAllErrors());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (userResult != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody Users2 user) {
        bankService.updateUser(user);
        Users2 userResult = bankService.getUser(user.getId());
        if (user.equals(userResult)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        Users2 user = bankService.getUser(id);
        bankService.deleteUser(id);
        Users2 userResult = bankService.getUser(id);
        if (userResult == null && user != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Tag(name = "Transfer money", description = "This is second method to transfer the money")
    @PutMapping("/trans")
    public ResponseEntity<HttpStatus> transUser(@RequestBody TransferOperations trans) {

        Users2 userFrom = bankService.findUser(trans.getNumberCardFrom());
        Users2 userTo = bankService.findUser(trans.getNumberCardTo());

        boolean flag = false;
        if (trans.getSumma() <= userFrom.getBalance() && userFrom != null && userTo != null) {
            flag = true;
        }

        if (flag) {
            bankService.transferMoneyTwo(userFrom, userTo, trans);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
