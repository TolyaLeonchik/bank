package com.bank.domain;

import lombok.Data;

@Data
public class Test {
    private String names;
    private int numbers;

    public Test(String names, int numbers) {
        this.names = names;
        this.numbers = numbers;
    }
}
