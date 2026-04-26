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
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import metier.modele.Subjects;

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

    
    public Demande createDemande(Student stu, Subjects subject) {
        int maxRetries = 3; //maybe make it a global variable configurable elsewhere idk
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            Demande dmd = null;
            IntervenantDao intervenantDao = new IntervenantDao();
            DemandeDao demandeDao = new DemandeDao();
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();

                Intervenant interv = findAndReserveIntervenant(stu.getStudentClass(), intervenantDao);

                if (interv == null) {
                    JpaUtil.validerTransaction();
                    return null;
                }

                dmd = new Demande();
                dmd.setStudent(stu);
                dmd.setEstablishment(stu.getEstablishment());
                dmd.setIntervenant(interv);
                dmd.setSubject(subject);
                demandeDao.create(dmd);

                JpaUtil.validerTransaction();

                notifyIntervenant(interv, dmd);
                return dmd;

            } catch (OptimisticLockException ex) {
                // Another demande got the intervenant we wanted
                JpaUtil.annulerTransaction();
            } catch (RollbackException ex) {
                // this is done for some reason that i still dont understand,
                // something about a RollbackException getting wrapped by the commit call
                if (ex.getCause() instanceof OptimisticLockException) {
                    JpaUtil.annulerTransaction();
                } else {
                    JpaUtil.annulerTransaction();
                    return null;
                }
            } catch (Exception ex) {
                JpaUtil.annulerTransaction();
                return null;
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        return null; // we got shyt vroda
    }

    private Intervenant findAndReserveIntervenant(Integer level, IntervenantDao intervenantDao) {
        // add the try catch
        Intervenant interv = intervenantDao.findAvailableIntervenant(level, level);
        if (interv != null) {
            interv.setAvailable(false);
            interv.setNbInterventions(interv.getNbInterventions() + 1);
            intervenantDao.update(interv);
        }
        return interv;
    }

    
}
