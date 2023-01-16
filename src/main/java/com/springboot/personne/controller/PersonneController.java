/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.controller;

import com.springboot.personne.constants.AppConstants;
import com.springboot.personne.entity.Personne;
import com.springboot.personne.exceptions.ResourceNotFoundExceptions;
import com.springboot.personne.payload.PersonneResponse;
import com.springboot.personne.repository.PersonneRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohamed
 */
@RestController
@RequestMapping("api/personnes")
public class PersonneController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonneRepository personneRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Personne> createPersonne(@Valid @RequestBody Personne personne) {
        final Personne createPersonne = personneSetCreate(personne);
        return new ResponseEntity<>(personneRepository.save(createPersonne), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PersonneResponse> getAllPersonnes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {

        PersonneResponse personnes = personnePagingSorting(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable(name = "id") Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));

        return new ResponseEntity<>(personne, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<List<Personne>> getPersonneByPrenomOrNom(@RequestParam(value = "prenom",required = false) String prenom,
            @RequestParam(value = "nom",required = false) String nom){
        
        List<Personne> personnes = new ArrayList<>();
        
        if(prenom == null && nom == null ){
            personneRepository.findAll().forEach(personnes::add);
        }
        else{
            personneRepository.findByPrenomOrNom(prenom, nom).forEach(personnes::add);
        }
        
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@Valid @RequestBody Personne personne,
            @PathVariable(name = "id") Long id) {

        final Personne updatePersonne = personneSetUpdate(personne, id);
        return new ResponseEntity<>(personneRepository.save(updatePersonne), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonne(@PathVariable(name = "id") Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));
        personneRepository.delete(personne);
        return new ResponseEntity<>(String.format("Personne with id %s deleted successfully", id), HttpStatus.OK);
    }

    public PersonneResponse personnePagingSorting(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Personne> personnes = personneRepository.findAll(pageable);

        List<Personne> content = personnes.getContent();
        PersonneResponse personneResponse = new PersonneResponse();
        personneResponse.setContent(content);
        personneResponse.setPageNo(personnes.getNumber());
        personneResponse.setPageSize(personnes.getSize());
        personneResponse.setTotalElements(personnes.getTotalElements());
        personneResponse.setTotalPages(personnes.getTotalPages());
        personneResponse.setLast(personnes.isLast());

        return personneResponse;

    }

    public Personne personneSetCreate(Personne personne) {
        Personne personneSet = new Personne();

        personneSet.setPrenom(personne.getPrenom());
        personneSet.setNom(personne.getNom());
        personneSet.setEmail(personne.getEmail());
        personneSet.setAge(personne.getAge());
        personneSet.setVoitures(personne.getVoitures());
        return personneSet;
    }

    public Personne personneSetUpdate(Personne personne, Long id) {
        Personne updatePersonne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));

        updatePersonne.setPrenom(personne.getPrenom());
        updatePersonne.setNom(personne.getNom());
        updatePersonne.setEmail(personne.getEmail());
        updatePersonne.setAge(personne.getAge());

        return updatePersonne;

    }

}
