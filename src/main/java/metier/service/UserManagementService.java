/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import dao.EstablishmentDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import dao.StudentDao;
import java.util.List;
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
            obj = "Bienvenue sur ...";
            
        } catch(Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            obj = "Echec";
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
    
    public List<Intervenant> findAllAvailableIntervenants()
    {
        IntervenantDao intervenantDao = new IntervenantDao();
        
        List<Intervenant> availableIntervenants = null;
        try
        {
            JpaUtil.creerContextePersistance();
            
            //availableIntervenants = intervenantDao.findAllAvailable();
        }
        catch(Exception ex)
        {
            
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return availableIntervenants;
    }

    // Manage user login's

    public Boolean authenticateUser(String login, String password){

        // get the user, if it doesn't exist, return false and say either say wrong passwd or email
        // if the user exists then compare the passwords

        // because we have two types of users, we would have to check if an account linked to the email exists
        // for a student, and then for a intervenant, but considering we would have two different login pages,
        // it could help us do to it differently, maybe add a different attribute? like from page

        // how do we assure unique emails if we two separate tables, when registering we would have to check if
        // the email is already present in the db?

        //check if

        return false;
    }
    //notify intervenant of a potential meeting
}
