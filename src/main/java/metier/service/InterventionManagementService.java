/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import java.net.URI;
import metier.modele.Intervenant;
import utils.Message;

/**
 *
 * @author gschambiram
 */
public class InterventionManagementService {
    
    public void notifyIntervenant(Intervenant interv, Demande dmd)
    {
        String msg = "Bonjour " + interv.getFirstName() +". Merci de "
                + "prendre en charge la demande de soutien en" + "<<" + dmd + ">>"
                + "demandée à" + dmd.time + "par " + dmd.student + " en classe de "
                + dmd.student + ".";
        Message.envoyerNotification(interv.getPhoneNumber(), msg);
        
        //maybe log that a intervention notification has been sent
    }
    
    public String createVisioLink(Demande dmd)
    {
        return "https://servif.insa-lyon.fr/InteractIF/visio.html?eleve=" + 
                dmd.getStudent().getEmail() + "&intervenant=" + dmd.getIntervenant().getLogin();
    }
    
    public void saveInterventionReport(Demande dmd, String report)
    {
        dmd.setReport(report);
        
        try
        {
            dmd = DemandeDao.updateDemande(dmd);
            
        }
        catch(Exception ex)
        {
        
        }
        finally{
            //maybe log ts
        }
        
        
    }
    
    public void sendReportByEmail(Demande dmd){
        
        String objet = "Bilan de l'intervention de " + dmd.getSubject() + "(" + dmd.getDate() + ")";
        String msg = dmd.getReport;
        Message.envoyerMail(dmd.getIntervenant().getEmail(), dmd.getStudent().getEmail(), objet, msg);
        
    }
}
