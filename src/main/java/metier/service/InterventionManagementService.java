package metier.service;

import metier.modele.Demande;
import metier.modele.Intervenant;
import metier.modele.Student;
import metier.modele.Subjects;
import metier.modele.Theme;

import java.time.LocalDateTime;

import dao.DemandeDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import utils.Message;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class InterventionManagementService {

    public void notifyIntervenant(Intervenant interv, Demande dmd) {
        String subjectName = dmd.getSubject() != null ? dmd.getSubject().getName() : "matière inconnue";
        String msg = "Bonjour " + interv.getFirstName() + ". Merci de "
                + "prendre en charge la demande de soutien en " + "<<" + subjectName + ">>"
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
        IntervenantDao intervenantDao = new IntervenantDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            dmd.setReport(report);
            dmd = demandeDao.update(dmd);

            // free the intervenant so they can take new demandes.
            Intervenant interv = dmd.getIntervenant();
            interv.setAvailable(true);
            intervenantDao.update(interv);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void sendReportByEmail(Demande dmd) {
        String subjectName = dmd.getSubject() != null ? dmd.getSubject().getName() : "matière inconnue";
        String objet = "Bilan de l'intervention de " + subjectName + " (" + dmd.getDate() + ")";
        String msg = dmd.getReport();
        Message.envoyerMail(dmd.getIntervenant().getLogin(), dmd.getStudent().getEmail(), objet, msg);
    }

    public Demande createDemande(Student stu, Subjects subject, Theme theme, String request) {
        int maxRetries = 3;
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
                dmd.setTheme(theme);
                dmd.setRequest(request);
                dmd.setDate(LocalDateTime.now());
                demandeDao.create(dmd);

                JpaUtil.validerTransaction();

                notifyIntervenant(interv, dmd);
                return dmd;

            } catch (OptimisticLockException ex) {
                JpaUtil.annulerTransaction();
            } catch (RollbackException ex) {
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
        return null;
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
