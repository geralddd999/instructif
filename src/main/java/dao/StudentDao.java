/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
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
}
