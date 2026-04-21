/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Establishment;
import metier.modele.Student;

/**
 *
 * @author gschambiram
 */
public class EstablishmentDao {
    
    public void create(Establishment establishment){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(establishment);
        
    }
    public Establishment findByCode(String establishmentCode){
        
        
        Establishment estab = null;
        try{
            EntityManager em = JpaUtil.obtenirContextePersistance();
            String jpql = "SELECT e FROM Establishment e WHERE e.codeEstablishment = :code";
        
            TypedQuery<Establishment> query = em.createQuery(jpql, Establishment.class);
            query.setParameter("code", establishmentCode);
            
            estab = query.getSingleResult();
        
        }
        catch(Exception e)
        {
            //
            estab = null;
        }
        
        return estab;
        
    }
    
    public List<Establishment> findAll(){
        
        List<Establishment> estab = null;
        try{
            EntityManager em = JpaUtil.obtenirContextePersistance();
            String jpql = "SELECT e FROM Establishment e ORDER BY e.name ASC";
            TypedQuery<Establishment> query = em.createQuery(jpql, Establishment.class);
            
            estab = query.getResultList();
        }catch(Exception ex){
            estab = null;
        }
        
        return estab;
    } 

}
