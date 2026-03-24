package com.mycompany.instructif;

import dao.JpaUtil;
import java.time.LocalDate;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Intervention;
import metier.modele.Status;
import metier.modele.Subjects;
import metier.modele.Theme;
import metier.service.SubjectsService;

public class Instrutif {

    public static void main(String[] args) {

        JpaUtil.creerFabriquePersistance();
        try {

            SubjectsService service = new SubjectsService();

            // 1. Initialisation DB
            SubjectsService.initializeSubjectsTable();

            // 2. Création nouvelle matière
            Subjects francais = new Subjects("francais");

            Theme grammaire = new Theme("grammaire", francais);

            francais.addTheme(grammaire);

            service.createSubject(francais);

            System.out.println("New subject + theme created");

            // 4. Test récupération Subjects + Themes
            List<Subjects> subjects = service.getAllSubjectsWithThemes();

            System.out.println("===== SUBJECTS + THEMES =====");

            for (Subjects subject : subjects) {
                System.out.println("Subject: " + subject.getName());

                for (Theme theme : subject.getThemes()) {
                    System.out.println("   Theme: " + theme.getName());
        }
    }

            // 5. Création Demande
            Subjects selectedSubject = subjects.get(0);
            Theme selectedTheme = selectedSubject.getThemes().get(0);

            Demande demande = new Demande();

            demande.setSubject(selectedSubject);
            demande.setTheme(selectedTheme);
            demande.setRequest("Test algebra");
            demande.setStatus(Status.DONE);
            demande.setDate(LocalDate.now());
            demande.setDuration(30);

            service.createDemand(demande);

            System.out.println("Demande created");

            // 6. Création Intervention
            Intervention intervention = new Intervention();

            intervention.setDemande(demande);
            intervention.setLienvisio("https://visio.test");
            intervention.setBilan("Session OK");

            service.createIntervention(intervention);

            System.out.println("Intervention created");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerFabriquePersistance();
        }
    }
}