/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import dao.EstablishmentDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import dao.StudentDao;
import metier.modele.Establishment;
import metier.modele.Intervenant;
import metier.modele.Student;
import utils.EstablishmentsRecordAPI;
import utils.Message;

/**
 *
 * @author gschambiram
 */
public class UserManagementService {
    
    public Boolean registerStudent(Student student, String establishmentCode){
        EstablishmentDao establishmentDao = new EstablishmentDao();
        StudentDao sDao = new StudentDao();
        
        Boolean success = false;

        String mailExp = "contact@instruct.if";
        String mailDest = student.getEmail();

        String obj = "";
        String corps = "";
        try{
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            sDao.create(student);
            
            // associate the establishment to the student
            Establishment establishment = null;
            
            establishment = establishmentDao.findByCode(establishmentCode);
            if(establishment != null)
            {
                // establishment already present in the db
                student.setEstablishment(establishment);
                success = true;
            }
            else
            {
                // fetch the establishment from the API
                establishment = EstablishmentsRecordAPI.buildEstablishmentFromAPI(establishmentCode);
                
                if(establishment != null)
                {
                    success = true;
                    // the establishment exits, persist to the table
                    establishmentDao.create(establishment);
                    student.setEstablishment(establishment);
                }
            }
            
            JpaUtil.validerTransaction();
            obj = "Bienvenue sur le reseau INSTRUCT'IF";
            corps = "Bonjour" + student.getFirstName() + ",nous te confirmons ton inscription" 
                    + "sur le reseau INSTRUCT'IF, Si tu as besoin d'un soutien pour tes lecons ou tes devoirs"
                    + ", rends-toi sur notre site pour une mise en relation avec un intervenant";
            
        } catch(Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            obj = "Echec de l'inscription sur le reseau INSTRUCT'IF";
            corps = "Bonjour" + student.getFirstName() + "ton inscription sur le réseau INSTRUCTIF a"
                    + "malencontreusement echoue... Merci de recommencer ulterieurement" ;
        } finally{
            JpaUtil.fermerContextePersistance();
            Message.envoyerMail(mailExp, mailDest, obj, corps);
        }
    
        return success;
    
    } 
    
    public Boolean registerIntervenant(Intervenant it)
    {
        IntervenantDao intervenantDao = new IntervenantDao();
        
        Boolean success = true;
        try
        {
            JpaUtil.creerContextePersistance();
            
            JpaUtil.ouvrirTransaction();
            
            intervenantDao.create(it);
            
            JpaUtil.validerTransaction();
        }
        catch(Exception ex)
        {
            JpaUtil.annulerTransaction();
            success = false;
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return success;
    }

    
    public Student findStudentByEmail(String email)
    {
        Student stu = null;
        StudentDao sDao = new StudentDao();
        try{
            JpaUtil.creerContextePersistance();
            stu = sDao.findByEmail(email);
        }
        catch(Exception e){
            stu = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return stu;
    }

}
