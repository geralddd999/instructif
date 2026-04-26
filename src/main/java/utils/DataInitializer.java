package utils;

import dao.SubjectsDao;
import metier.modele.Subjects;
import metier.modele.Theme;
import dao.EstablishmentDao;
import dao.JpaUtil;
import metier.modele.Establishment;
import metier.modele.Intervenant;
import metier.modele.Student;
import metier.service.UserManagementService;
import java.time.LocalDate;

public class DataInitializer {
    
    // simulated establishment code 
    public static final String EST_CODE = "TEST001";

    public static void init() {
        initEstablishment();
        initIntervenants();
        initStudents();
        initSubjectsAndThemes();
    }
    
    private static void initSubjectsAndThemes() {
        SubjectsDao dao = new SubjectsDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Subjects math = new Subjects("Mathématiques");
            math.addTheme(new Theme("Algèbre", math));
            math.addTheme(new Theme("Géométrie", math));

            Subjects francais = new Subjects("Français");
            francais.addTheme(new Theme("Grammaire", francais));
            francais.addTheme(new Theme("Expression écrite", francais));

            Subjects histoire = new Subjects("Histoire-Géographie");
            histoire.addTheme(new Theme("Moyen Âge", histoire));
            histoire.addTheme(new Theme("Révolution française", histoire));

            dao.create(math);
            dao.create(francais);
            dao.create(histoire);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
    }
}

    private static void initEstablishment() {
        EstablishmentDao dao = new EstablishmentDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.create(new Establishment(
                EST_CODE, "Lycée Jean Moulin Test", "Public",
                "1 Rue de la Paix", "69001", "Lyon",
                45.7578, 4.8320, "69", "AUR", "Rhône", "Auvergne-Rhône-Alpes"
            ));
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    private static void initIntervenants() {
        UserManagementService svc = new UserManagementService();
        svc.registerIntervenant(new Intervenant("Martin",   "Alice", "alice@instructif.fr",  "pass123", 1, 3, "0600000001", 0, true));
        svc.registerIntervenant(new Intervenant("Bernard",  "Bob",   "bob@instructif.fr",    "pass123", 2, 5, "0600000002", 0, true));
        svc.registerIntervenant(new Intervenant("Dubois",   "Carol", "carol@instructif.fr",  "pass123", 1, 2, "0600000003", 0, true));
        svc.registerIntervenant(new Intervenant("Lambert",  "Dave",  "dave@instructif.fr",   "pass123", 4, 5, "0600000004", 0, true));
    }

    private static void initStudents() {
        UserManagementService svc = new UserManagementService();
        svc.registerStudent(new Student("Lefebvre", "Luc",    "luc@student.fr",     "s1pass",  1, LocalDate.of(2007,  3, 12)), EST_CODE);
        svc.registerStudent(new Student("Garnier",  "Emma",   "emma@student.fr",    "s2pass",  1, LocalDate.of(2007,  6,  5)), EST_CODE);
        svc.registerStudent(new Student("Rousseau", "Nathan", "nathan@student.fr",  "s3pass",  2, LocalDate.of(2006,  9, 20)), EST_CODE);
        svc.registerStudent(new Student("Simon",    "Julie",  "julie@student.fr",   "s4pass",  2, LocalDate.of(2006, 11, 14)), EST_CODE);
        svc.registerStudent(new Student("Michel",   "Hugo",   "hugo@student.fr",    "s5pass",  3, LocalDate.of(2005,  2, 28)), EST_CODE);
        svc.registerStudent(new Student("Leroy",    "Camille","camille@student.fr", "s6pass",  3, LocalDate.of(2005,  7, 17)), EST_CODE);
        svc.registerStudent(new Student("David",    "Theo",   "theo@student.fr",    "s7pass",  4, LocalDate.of(2004,  4,  3)), EST_CODE);
        svc.registerStudent(new Student("Bertrand", "Lea",    "lea@student.fr",     "s8pass",  4, LocalDate.of(2004,  8, 22)), EST_CODE);
        svc.registerStudent(new Student("Moreau",   "Tom",    "tom@student.fr",     "s9pass",  5, LocalDate.of(2003,  1,  7)), EST_CODE);
        svc.registerStudent(new Student("Fontaine", "Jade",   "jade@student.fr",    "s10pass", 5, LocalDate.of(2003, 12, 30)), EST_CODE);
    }
}
