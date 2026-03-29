/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Intervenant;

/**
 *
 * @author gschambiram
 */
public class IntervenantDao {
    
    public void create(Intervenant it)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(it);
    }
    
    public Intervenant findByLogin(String login)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        String jpql = "SELECT i FROM Intervenant i WHERE i.email = :searchLogin";
        
        TypedQuery<Intervenant> query = em.createQuery(jpql, Intervenant.class);
        query.setParameter("searchLogin", login);
        
        return query.getSingleResult();
    }
    
    public List<Intervenant> findAvailableIntervenants()
    {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        // change the critera, because showing all of them is not the best idea, might have to implement
        // a separate algorithm/index to make the query faster
        String jpql = "SELECT i FROM Intervenant i WHERE i.available = true";
        
        TypedQuery<Intervenant> query = em.createQuery(jpql, Intervenant.class);
        
        return query.getResultList();
    }
    
    public Intervenant update(Intervenant it)
    {
        return JpaUtil.obtenirContextePersistance().merge(it);
    }
}
