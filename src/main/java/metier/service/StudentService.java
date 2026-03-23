/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import dao.JpaUtil;
import dao.StudentDao;
import metier.modele.Student;

/**
 *
 * @author gschambiram
 */
public class StudentService {
    public Boolean registerStudent(Student student){
        Boolean success = false;
        try{
            JpaUtil.creerContextePersistance();
            
            StudentDao sDao = new StudentDao();
            
            JpaUtil.ouvrirTransaction();
            sDao.create(student);
            
            success = true;
            JpaUtil.validerTransaction();
            
        } catch(Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally{
            JpaUtil.fermerContextePersistance();
        }
    
        return success;
    
    } 
}
