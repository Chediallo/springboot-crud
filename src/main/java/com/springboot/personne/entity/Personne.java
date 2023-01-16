/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



/**
 *
 * @author Mohamed
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
            @UniqueConstraint(name = "unique_email", columnNames = "email")
        }
)
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le prenom ne doit pas etre vide")
    @Size(min = 3, message = "Le prénom doit avoir minimum 3 caracteres")
    @Column(nullable = false)
    private String prenom;

    @NotEmpty(message = "Le nom ne doit pas etre vide")
    @Size(min = 5, message = "Le nom doit avoir minimum 5 caracteres")
    @Column(nullable = false)
    private String nom;

    @NotEmpty(message = "L'email ne doit pas etre vide")
    @Email
    @Column(nullable = false)
    private String email;

    //@NotEmpty(message = "L'age ne doit pas etre vide")
    @Column(nullable = false)
    private int age;

    @JsonManagedReference
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voiture> voitures;

    @CreationTimestamp
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    private LocalDateTime dateUpdate;
}
