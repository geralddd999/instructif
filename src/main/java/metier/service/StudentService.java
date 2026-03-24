/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import dao.EstablishmentDao;
import dao.JpaUtil;
import dao.StudentDao;
import metier.modele.Establishment;
import metier.modele.Student;
import utils.EstablishmentUtils;

/**
 *
 * @author gschambiram
 */
public class StudentService {
    public Boolean registerStudent(Student student, String establishmentCode){
        Boolean success = false;
        
        EstablishmentDao estabDao = new EstablishmentDao();
        try{
            JpaUtil.creerContextePersistance();
            
            StudentDao sDao = new StudentDao();
        
            
            JpaUtil.ouvrirTransaction();
            
            sDao.create(student);
            
            // fetch the establishment data from the API
            // if it's already present use it from the table
            
            Establishment estab = null;
            estab = estabDao.findByCode(establishmentCode);
            if(estab != null)
            {
                student.setEstablishment(estab);
            }
            else
            {
                // create one from the API
                estab = EstablishmentUtils.createEstablishmentFromRecord(establishmentCode);
                if(estab != null){
                    estabDao.create(estab);
                }
                else{
                    success = false;
                }
                
            }
            
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
