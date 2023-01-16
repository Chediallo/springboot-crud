/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.springboot.personne.repository;

import com.springboot.personne.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mohamed
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
