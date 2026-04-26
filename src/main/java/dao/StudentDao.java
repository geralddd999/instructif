/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import metier.modele.Student;
/**
 *
 * @author gschambiram
 */
public class StudentDao {
    
    public void create(Student stu){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(stu);
    }

    public Student findByEmail(String email) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s WHERE s.email = :searchEmail", Student.class);
            query.setParameter("searchEmail", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
