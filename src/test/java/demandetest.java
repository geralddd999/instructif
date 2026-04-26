import dao.JpaUtil;
import java.time.LocalDate;
import metier.modele.Demande;
import metier.modele.Status;
import metier.service.SubjectsService;

public class demandetest {

    public static void main(String[] args) {

        JpaUtil.creerFabriquePersistance();
        JpaUtil.creerContextePersistance();

        try {
            SubjectsService service = new SubjectsService();

            Demande demande = new Demande();
            demande.setRequest("Je ne comprends pas l'algebre");
            demande.setStatus(Status.DONE);
            demande.setDate(LocalDate.now());
            demande.setDuration(30);

            service.createDemand(demande);

            System.out.println("Demand inserted successfully");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JpaUtil.fermerContextePersistance();
            JpaUtil.fermerFabriquePersistance();
        }
    }
}