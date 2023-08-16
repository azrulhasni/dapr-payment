/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.azrul.banking.account;

import com.azrul.banking.domain.Transaction;
import java.util.logging.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author azrul
 */
@RestController
public class AccounController {
  
    
    @PostMapping(path = "/account", consumes = MediaType.ALL_VALUE)
    public String processTransaction(@RequestBody Transaction body) {
        System.out.println("Transaction received: "+ body.toString());
        return "GOOD";
    }


}
