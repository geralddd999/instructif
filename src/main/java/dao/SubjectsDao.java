/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import javax.security.auth.Subject;
import metier.modele.Student;
import metier.modele.Subjects;

/**
 *
 * @author DELL
 */
public class SubjectsDao {
    public void create(Subjects subject){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(subject);
        
    }
    
}
