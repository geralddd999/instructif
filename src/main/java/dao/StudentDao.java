/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
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

    public Student findByEmail(String email){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String jpql = "SELECT s FROM Student s WHERE s.email = :searchEmail ";
        TypedQuery<Student> query = em.createQuery(jpql, Student.class);
        query.setParameter("searchEmail", email);

        return query.getSingleResult();
    }

}
