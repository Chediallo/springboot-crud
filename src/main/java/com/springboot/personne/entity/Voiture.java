/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mohamed
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voiture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Le nom de la voiture ne doit pas etre vide")
    @Size(min = 3, message = "Le nom de la voiture doit avoir minimum 3 caracteres" )
    private String nom;
    
    @NotEmpty(message = "Le modele de la voiture ne doit pas etre vide")
    @Size(min = 5, message = "Le modele de la voiture doit avoir minimum 3 caracteres" )
    private String modele;
    
    @NotEmpty(message = "Le constructeur de la voiture ne doit pas etre vide")
    @Size(min = 3, message = "Le constructeur de la voiture doit avoir minimum 3 caracteres" )
    private String constructeur;
    
    private int annee;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id",nullable = false)
    private Personne personne;
    
    
    
    
    
}
