/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
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
    public Subjects findbyId(Long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Subjects.class, id);
    }
    
    public List<Subjects> findAll() {
    EntityManager em = JpaUtil.obtenirContextePersistance();

    return em.createQuery(
        "SELECT DISTINCT s FROM Subjects s LEFT JOIN FETCH s.themes",
        Subjects.class
    ).getResultList();
}
    
}
