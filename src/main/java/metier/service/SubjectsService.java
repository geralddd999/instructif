package metier.service;

import dao.DemandDao;
import dao.InterventionDao;
import dao.JpaUtil;
import dao.SubjectsDao;
import dao.ThemeDao;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Intervention;
import metier.modele.Subjects;
import metier.modele.Theme;

public class SubjectsService {

    private static SubjectsDao subjectsDao = new SubjectsDao();
    private ThemeDao themeDao = new ThemeDao();
    private DemandDao demandDao = new DemandDao();
    private InterventionDao interventionDao = new InterventionDao();

    public static void initializeSubjectsTable() {
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Subjects math = new Subjects("math");
            math.addTheme(new Theme("algebra", math));

            subjectsDao.create(math);

            JpaUtil.validerTransaction();
            System.out.println("Subjects inserted successfully");

        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void createSubject(Subjects subject) throws Exception {
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            subjectsDao.create(subject);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            throw e;
        } finally {
        JpaUtil.fermerContextePersistance();
    }
    }

    public void createTheme(Theme theme) throws Exception {
        
        JpaUtil.creerContextePersistance();
        
        try {
            JpaUtil.ouvrirTransaction();
            themeDao.create(theme);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            throw e;
        }
         finally {
        JpaUtil.fermerContextePersistance();
    }
    }

    public void createDemand(Demande demande) throws Exception {
    JpaUtil.creerContextePersistance();

    try {
        JpaUtil.ouvrirTransaction();

        demandDao.create(demande);

        JpaUtil.validerTransaction();

    } catch (Exception e) {
        JpaUtil.annulerTransaction();
        throw e;

    } finally {
        JpaUtil.fermerContextePersistance();
    }
}

    public void createIntervention(Intervention intervention) throws Exception {
    JpaUtil.creerContextePersistance();

    try {
        JpaUtil.ouvrirTransaction();

        interventionDao.create(intervention);

        JpaUtil.validerTransaction();

    } catch (Exception e) {
        JpaUtil.annulerTransaction();
        throw e;

    } finally {
        JpaUtil.fermerContextePersistance();
    }
}
    
    public List<Subjects> getAllSubjectsWithThemes() {
    JpaUtil.creerContextePersistance();

    try {
        
        return subjectsDao.findAll();

    } finally {
        JpaUtil.fermerContextePersistance();
    }
}
}