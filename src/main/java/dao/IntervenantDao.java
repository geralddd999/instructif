/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Intervenant> query = em.createQuery(
                "SELECT i FROM Intervenant i WHERE i.login = :searchLogin", Intervenant.class);
            query.setParameter("searchLogin", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Intervenant findAvailableIntervenant(Integer minLevel, Integer maxLevel)
    {
        Intervenant interv = null;
        
        try
        {
            EntityManager em = JpaUtil.obtenirContextePersistance();
     
            String jpql = "SELECT i FROM Intervenant i WHERE i.available = true " +
                "AND i.minLevel <= :minLevel AND i.maxLevel >= :maxLevel " +
                "ORDER BY i.nbInterventions ASC";
        
            TypedQuery<Intervenant> query = em.createQuery(jpql, Intervenant.class);

            query.setParameter("minLevel", minLevel);
            query.setParameter("maxLevel",maxLevel);
            
            List<Intervenant> results = query.getResultList();
            if (!results.isEmpty())
            {
                interv = results.get(0);
            }
            
        }
        catch(Exception e)
        {
            interv = null;
        }
        // exception thrown when there are no intervenant
        return interv;
        
    }
    
    public Intervenant update(Intervenant it)
    {

        return JpaUtil.obtenirContextePersistance().merge(it);
    }

}
