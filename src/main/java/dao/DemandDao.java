/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import metier.modele.Demande;

/**
 *
 * @author bbelkho
 */
public class DemandDao {
    

    public void create(Demande demande) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(demande);
    }

    public Demande findById(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Demande.class, id);
    }
}

