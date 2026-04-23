package metier.service;

import metier.modele.Demande;
import metier.modele.Establishment;
import metier.modele.Intervenant;
import metier.modele.Student;

import java.util.List;

import dao.DemandeDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import utils.Message;

public class InterventionManagementService {

    public void notifyIntervenant(Intervenant interv, Demande dmd) {
        String msg = "Bonjour " + interv.getFirstName() + ". Merci de "
                + "prendre en charge la demande de soutien en " + "<<" + dmd.getSubject() + ">>"
                + " demandée à " + dmd.getDate() + " par " + dmd.getStudent().getFirstName()
                + " " + dmd.getStudent().getLastName() + " en classe de "
                + dmd.getStudent().getStudentClass() + ".";
        Message.envoyerNotification(interv.getPhoneNumber(), msg);
    }

    public String createVisioLink(Demande dmd) {
        return "https://servif.insa-lyon.fr/InteractIF/visio.html?eleve="
                + dmd.getStudent().getEmail() + "&intervenant=" + dmd.getIntervenant().getLogin();
    }

    public void saveInterventionReport(Demande dmd, String report) {
        DemandeDao demandeDao = new DemandeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dmd.setReport(report);
            dmd = demandeDao.update(dmd);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void sendReportByEmail(Demande dmd) {
        String objet = "Bilan de l'intervention de " + dmd.getSubject() + " (" + dmd.getDate() + ")";
        String msg = dmd.getReport();
        Message.envoyerMail(dmd.getIntervenant().getLogin(), dmd.getStudent().getEmail(), objet, msg);
    }

    
    public Demande createDemande(Student stu, String subject) {
        // Creates a demand for a student on a given subject.
        // Returns null if no intervenant is available (demand is cancelled).
        Demande dmd = null;
        IntervenantDao intervenantDao = new IntervenantDao();
        DemandeDao demandeDao = new DemandeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Intervenant interv = findAndReserveIntervenant(stu.getStudentClass(), intervenantDao);

            if (interv == null) {
                // Nobody available — cancel the demand
                JpaUtil.validerTransaction();
                return null;
            }

            // change to either use the full constructor and all the details
            dmd = new Demande();
            dmd.setStudent(stu);
            dmd.setEstablishment(stu.getEstablishment());
            dmd.setIntervenant(interv);
            dmd.setSubject(subject);
            demandeDao.create(dmd);

            JpaUtil.validerTransaction();

            notifyIntervenant(interv, dmd);

        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            dmd = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return dmd;
    }

    private Intervenant findAndReserveIntervenant(Integer level, IntervenantDao intervenantDao) {
        Intervenant interv = intervenantDao.findAvailableIntervenant(level, level);
        if (interv != null) {
            interv.setAvailable(false);
            interv.setNbInterventions(interv.getNbInterventions() + 1);
            intervenantDao.update(interv);
        }
        return interv;
    }

    
}
