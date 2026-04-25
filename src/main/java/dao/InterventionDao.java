/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import metier.modele.Intervention;

/**
 *
 * @author bbelkho
 */
public class InterventionDao {
   

    public void create(Intervention intervention) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(intervention);
    }

    public Intervention findById(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Intervention.class, id); 
    }
}
    

