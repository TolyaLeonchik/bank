package com.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

    static final Logger log = LoggerFactory.getLogger(BankApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
        log.debug("After starting application DEBUG");
        log.info("After starting application");
        log.error("Error log!!!");
    }
}
