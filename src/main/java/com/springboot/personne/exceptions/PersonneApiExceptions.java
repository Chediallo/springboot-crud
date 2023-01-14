/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Mohamed
 */
@Getter
public class PersonneApiExceptions extends RuntimeException {
    
    private HttpStatus status;
    private String message;

    public PersonneApiExceptions(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public PersonneApiExceptions(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message;
    }
    
    
    
}
