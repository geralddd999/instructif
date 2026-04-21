/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;


import dao.EstablishmentDao;
import java.util.ArrayList;
import java.util.List;
import metier.modele.Establishment;

/**
 *
 * @author gschambiram
 */

//could also be called Statistics service, but nevertheless it will be mostly used in the dashboards for the IHM
public class StatisticsService {
    
    public List<Establishment> getEstablishmentsList()
    {
        //from the dao get the list so it can be passed to the IHM
        List<Establishment> establishments = null;
        try{
            EstablishmentDao estabDao = new EstablishmentDao();
            establishments = estabDao.findAll();
            
        }
        catch(Exception ex){
            establishments = null;
        }
        
        return establishments;
    } 

    // something for intervenant stats
    public void getEstablishmentStats()
    {
        //from the dao get a JSON file for the stats?
    }
    
    private List<Demande> getAllDemands()
    {
      //get all the Interventions done until date, maybe do a cache?
      // design all the necessary data
      // from all the data, put them in a relevant JSON file?
        
    }
    //function for a single establishment statistics

}
