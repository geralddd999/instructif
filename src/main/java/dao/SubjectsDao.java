/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
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
    public Subjects findbyId(Long id) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            return em.find(Subjects.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Subjects> findAll() {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            return em.createQuery(
                "SELECT DISTINCT s FROM Subjects s LEFT JOIN FETCH s.themes",
                Subjects.class
            ).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
