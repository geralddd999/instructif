package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Demande;
import metier.modele.Establishment;
import metier.modele.Intervenant;
import metier.modele.Student;


public class DemandeDao {

    public void create(Demande demande) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(demande);
    }

    public Demande update(Demande demande) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.merge(demande);
    }

    public List<Demande> getDemandeListByStudent(Student stu){
        
        EntityManager em = JpaUtil.obtenirContextePersistance();
        List<Demande> res = null;

        try{
            String jpql = "SELECT d FROM Demande d WHERE d.student = :searchStudent";

            TypedQuery<Demande> query = em.createQuery(jpql, Demande.class);
            query.setParameter("searchStudent", stu);

            res = query.getResultList();


        }catch(Exception ex){
            //res still is null??
        }
        
        return res;
    }

    public List<Demande> getDemandeListByIntervenant(Intervenant it){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        List<Demande> res = null;

        try{
            String jpql = "SELECT d FROM Demande d WHERE d.intervenant = :searchIntervenant";

            TypedQuery<Demande> query = em.createQuery(jpql, Demande.class);
            query.setParameter("searchIntervenant", it);

            res = query.getResultList();


        }catch(Exception ex){
            //res still is null??
        }
        
        return res;
    }

    public long countByEstablishment(Establishment estab) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery(
                "SELECT COUNT(d) FROM Demande d WHERE d.establishment = :estab", Long.class)
                .setParameter("estab", estab)
                .getSingleResult();
    }

    public double avgDurationByEstablishment(Establishment estab) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Double avg = em.createQuery(
                "SELECT AVG(d.durationMinutes) FROM Demande d WHERE d.establishment = :estab", Double.class)
                .setParameter("estab", estab)
                .getSingleResult();
        return avg != null ? avg : 0.0;
    }
    
    public List<String> topSubjectsByEstablishment(Establishment estab) {
        // Returns subjects ordered from most to least requested for that establishment.
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery(
                "SELECT d.subject FROM Demande d WHERE d.establishment = :estab "
                + "GROUP BY d.subject ORDER BY COUNT(d) DESC", String.class)
                .setParameter("estab", estab)
                .getResultList();
    }
}
