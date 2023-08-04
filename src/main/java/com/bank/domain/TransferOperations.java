package com.bank.domain;

import lombok.Data;

@Data
public class TransferOperations {

    private String numberCardFrom;
    private String numberCardTo;
    private Integer summa;
}
