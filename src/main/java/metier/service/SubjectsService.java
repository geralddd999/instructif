package metier.service;

import dao.JpaUtil;
import dao.SubjectsDao;
import java.util.List;
import metier.modele.Subjects;

public class SubjectsService {

    private SubjectsDao subjectsDao = new SubjectsDao();

    public List<Subjects> getAllSubjectsWithThemes() {
        JpaUtil.creerContextePersistance();

        try {
            return subjectsDao.findAll();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
}