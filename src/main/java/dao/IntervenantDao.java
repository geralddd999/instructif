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
    
    public Intervenant findByEmail(String login)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        String jpql = "SELECT i FROM Intervenant i WHERE i.email = :searchLogin";
        
        TypedQuery<Intervenant> query = em.createQuery(jpql, Intervenant.class);
        query.setParameter("searchLogin", login);
        
        return query.getSingleResult();
    }
    
    public List<Intervenant> findAvailableIntervenant(Integer minLevel, Integer maxLevel)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        // change the critera, because showing all of them is not the best idea, might have to implement
        // a separate algorithm/index to make the query faster
        String jpql = "SELECT i FROM Intervenant i WHERE i.available = true " +
                "AND i.minLevel <= :minLevel AND i.maxLevel >= :maxLevel " +
                "ORDER BY i.nbInterventions ASC";
        
        TypedQuery<Intervenant> query = em.createQuery(jpql, Intervenant.class);

        query.setParameter("minLevel", minLevel);
        query.setParameter("maxLevel",maxLevel);
        
        return query.getResultList(); // do we get an exception when the result is an empty list?? or what
    }
    
    public Intervenant update(Intervenant it)
    {

        return JpaUtil.obtenirContextePersistance().merge(it);
    }

}
