/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.payload;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Mohamed
 */
@AllArgsConstructor
@Getter
public class ErrorDetails {
    
    private Date timestamp;
    private String message;
    private String details;
    
    
}
