/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.repository;

import com.springboot.personne.entity.Personne;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Mohamed
 */
@SpringBootTest
public class PersonneRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonneRepository personneRepository;

    /*
    @Test
    @Transactional
    public void findByPrenomOrNom(){
        
        List<Personne> personne = personneRepository.findByPrenomOrNom("Mohamed", "Diallo");
        logger.info("Personne FindByPrenom OR Nom ");
        personne.forEach(p -> System.out.println(p));
    }
     
*/
}
