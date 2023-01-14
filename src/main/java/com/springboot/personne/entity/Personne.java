/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
