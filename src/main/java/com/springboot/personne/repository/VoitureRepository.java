/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.springboot.personne.repository;

import com.springboot.personne.entity.Voiture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mohamed
 */
public interface VoitureRepository extends JpaRepository<Voiture, Long>{
    
    List<Voiture> findByPersonneId(long personneId);
    
}
