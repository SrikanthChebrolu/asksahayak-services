package com.asksahayak.contorllers.rest;

import com.asksahayak.domain.Transaction;
import com.asksahayak.services.TransactionService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by srikanthchebrolu on 1/24/18.
 */
@RestController
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    @GetMapping("/getUsers")
    public String home() {
        return "Hello World!";
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<?> Transaction(@RequestBody Transaction transaction) {

        String requestPayload = new Gson().toJson(transaction, Transaction.class);
        log.info("Transaction : ", requestPayload);

        return new ResponseEntity<>("Srikanth", HttpStatus.OK);
    }

}

