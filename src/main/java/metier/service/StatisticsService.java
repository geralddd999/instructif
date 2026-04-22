package metier.service;

import dao.DemandeDao;
import dao.EstablishmentDao;
import dao.JpaUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metier.modele.Demande;
import metier.modele.Establishment;
import metier.modele.EstablishmentStats;
import metier.modele.Student;

public class StatisticsService {

    /* Use of a cache for the stats, to be defined in the first call, and updated
     "au fur a mesure" or however tf you write that shyt */
    private static Map<Establishment, EstablishmentStats> statsCache = null;

    public List<Establishment> getEstablishmentsList() {
        List<Establishment> establishments = null;
        try {
            JpaUtil.creerContextePersistance();
            EstablishmentDao estabDao = new EstablishmentDao();
            establishments = estabDao.findAll();
        } catch (Exception ex) {
            establishments = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return establishments;
    }

    public Map<Establishment, EstablishmentStats> getEstablishmentStats() {
        if (statsCache == null) {
            buildCache();
        }
        return Collections.unmodifiableMap(statsCache);
    }

    public void refreshStatsForEstablishment(Establishment estab) {
        /* To be called at every demande modif/creation */
        if (statsCache == null) {
            buildCache();
            return;
        }
        try {
            JpaUtil.creerContextePersistance();
            DemandeDao demandeDao = new DemandeDao();

            long totalDemands        = demandeDao.countByEstablishment(estab);
            double avgDuration       = demandeDao.avgDurationByEstablishment(estab);
            List<String> topSubjects = demandeDao.topSubjectsByEstablishment(estab);

            statsCache.put(estab, new EstablishmentStats(estab, totalDemands, avgDuration, topSubjects));
        } catch (Exception ex) {
            statsCache = null; // force full rebuild on next getEstablishmentStats()
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    private void buildCache() {
        Map<Establishment, EstablishmentStats> cache = new HashMap<>();
        try {
            JpaUtil.creerContextePersistance();
            EstablishmentDao estabDao = new EstablishmentDao();
            DemandeDao demandeDao = new DemandeDao();

            List<Establishment> establishments = estabDao.findAll();
            for (Establishment estab : establishments) {
                long totalDemands        = demandeDao.countByEstablishment(estab);
                double avgDuration       = demandeDao.avgDurationByEstablishment(estab);
                List<String> topSubjects = demandeDao.topSubjectsByEstablishment(estab);
                cache.put(estab, new EstablishmentStats(estab, totalDemands, avgDuration, topSubjects));
            }
            statsCache = cache;
        } catch (Exception ex) {
            statsCache = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }


    public List<Demande> getDemandeListForStudent(Student stu){
        
        List<Demande> res;
        
        try
        {
            DemandeDao demandeDao = new DemandeDao();
            
            JpaUtil.creerContextePersistance();
            res = demandeDao.getDemandeListByStudent(stu);

            
        }catch(Exception ex){
            res = null;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
}
