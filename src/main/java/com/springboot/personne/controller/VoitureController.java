/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.controller;

import com.springboot.personne.entity.Personne;
import com.springboot.personne.entity.Voiture;
import com.springboot.personne.exceptions.PersonneApiExceptions;
import com.springboot.personne.exceptions.ResourceNotFoundExceptions;
import com.springboot.personne.payload.VoitureResponse;
import com.springboot.personne.repository.PersonneRepository;
import com.springboot.personne.repository.VoitureRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api")
public class VoitureController {

    @Autowired
    private PersonneRepository personnerepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @PostMapping("/personnes/{personneId}/voitures")
    public ResponseEntity<Voiture> createVoiture(@PathVariable(value = "personneId") Long personneId,
            @RequestBody Voiture voiture) {
        final Voiture setVoiture = setVoiture(voiture, personneId);
        final Voiture saveVoiture = voitureRepository.save(setVoiture);

        return new ResponseEntity<>(saveVoiture, HttpStatus.CREATED);

    }

    @GetMapping("/personnes/{personneId}/voitures")
    public ResponseEntity<List<Voiture>> getVoituresByPostId(@PathVariable(value = "personneId") long personneId) {
        List<Voiture> voitures = voitureRepository.findByPersonneId(personneId);

        return new ResponseEntity<>(voitures, HttpStatus.OK);

    }

    @GetMapping("/personnes/{personneId}/voitures/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable(value = "personneId") long personneId,
            @PathVariable(value = "id") long id) {

        Personne personne = personnerepository.findById(personneId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", personneId));

        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Voiture", "id", id));

        if (!voiture.getPersonne().getId().equals(personne.getId())) {
            throw new PersonneApiExceptions(HttpStatus.NOT_FOUND, String.format("Voiture with id %s does not belong to a personne", id));
        }

        return new ResponseEntity<>(voiture, HttpStatus.OK);
    }

    @GetMapping("/personnes/voitures")
    public ResponseEntity<VoitureResponse> getAllVoitures(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {

        final VoitureResponse voiturePagingSorting = voiturePagingSorting(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(voiturePagingSorting, HttpStatus.OK);

    }

    @PutMapping("/personnes/{personneId}/voitures/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable(value = "personneId") long personneId,
            @PathVariable(value = "id") long id, @RequestBody Voiture voiture) {

        Personne personne = personnerepository.findById(personneId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", personneId));

        Voiture updateVoiture = voitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Voiture", "id", id));

        if (!updateVoiture.getPersonne().getId().equals(personne.getId())) {
            throw new PersonneApiExceptions(HttpStatus.NOT_FOUND, String.format("Voiture with id %s does not belong to a personne", id));
        }

        updateVoiture.setNom(voiture.getNom());
        updateVoiture.setModele(voiture.getModele());
        updateVoiture.setConstructeur(voiture.getConstructeur());
        updateVoiture.setAnnee(voiture.getAnnee());

        final Voiture saveUpdateVoiture = voitureRepository.save(updateVoiture);

        return new ResponseEntity<>(saveUpdateVoiture, HttpStatus.OK);
    }

    @DeleteMapping("/personnes/{personneId}/voitures/{id}")
    public ResponseEntity<String> deleteVoitureByPersonneId(@PathVariable(value = "personneId") long personneId,
            @PathVariable(value = "id") long id) {
        Personne personne = personnerepository.findById(personneId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", personneId));

        Voiture deleteVoiture = voitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Voiture", "id", id));

        if (!deleteVoiture.getPersonne().getId().equals(personne.getId())) {
            throw new PersonneApiExceptions(HttpStatus.NOT_FOUND, String.format("Comment with id %s does not belong to a post", id));
        }
        
        voitureRepository.delete(deleteVoiture);
        
        return new ResponseEntity<>(String.format("Voiture with id %s deleted successfully", id),HttpStatus.OK);
    }

    private Voiture setVoiture(Voiture voiture, Long personneId) {
        Voiture voiturePersonne = new Voiture();
        voiturePersonne.setNom(voiture.getNom());
        voiturePersonne.setModele(voiture.getModele());
        voiturePersonne.setConstructeur(voiture.getConstructeur());
        voiturePersonne.setAnnee(voiture.getAnnee());

        Personne personne = personnerepository.findById(personneId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", personneId));

        voiturePersonne.setPersonne(personne);

        return voiturePersonne;
    }

    private VoitureResponse voiturePagingSorting(int pageNo, int pageSize, String sortBy) {

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        Page<Voiture> voitures = voitureRepository.findAll(pageable);

        List<Voiture> content = voitures.getContent();
        VoitureResponse voitureResponse = new VoitureResponse();
        voitureResponse.setContent(content);
        voitureResponse.setPageNo(voitures.getNumber());
        voitureResponse.setPageSize(voitures.getSize());
        voitureResponse.setTotalElements(voitures.getTotalElements());
        voitureResponse.setTotalPages(voitures.getTotalPages());
        voitureResponse.setLast(voitures.isLast());

        return voitureResponse;

    }
}
