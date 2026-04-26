package utils;
import dao.DemandeDao;
import dao.JpaUtil;
import metier.modele.Demande;
import metier.modele.Intervenant;
import metier.modele.Student;
import metier.modele.Subjects;
import metier.service.AuthenticationService;
import metier.service.InterventionManagementService;
import metier.service.UserManagementService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SystemTest {

    private static final UserManagementService userService = new UserManagementService();
    private static final AuthenticationService authService = new AuthenticationService();
    private static final InterventionManagementService interventionService = new InterventionManagementService();

    private static int passed = 0;
    private static int failed = 0;

    public static void runAll() {
        passed = 0;
        failed = 0;
        System.out.println("\n========= RUNNING SYSTEM TESTS =========\n");

        // Auth
        run("Student login — correct credentials",     SystemTest::test_studentLogin_success);
        run("Student login — wrong password",          SystemTest::test_studentLogin_wrongPassword);
        run("Student login — unknown email",           SystemTest::test_studentLogin_unknownEmail);
        run("Intervenant login — correct credentials", SystemTest::test_intervenantLogin_success);
        run("Intervenant login — wrong password",      SystemTest::test_intervenantLogin_wrongPassword);

        // Intervention - creation demande
        run("Create demande — success",                SystemTest::test_createDemande_success);
        run("Create demande — no intervenant match",   SystemTest::test_createDemande_noMatch);
        run("Create demande — load balancing",         SystemTest::test_loadBalancing);
        run("Save intervention report",                SystemTest::test_saveReport);

        // Concurrency
        run("Concurrent demandes — one available slot",SystemTest::test_concurrent);

        // Full "workflow"
        run("Full end-to-end workflow",                SystemTest::test_fullWorkflow);

        System.out.println("\n========= " + passed + " passed  |  " + failed + " failed =========\n");
    }

    private static void run(String name, Runnable body) {
        try {
            body.run();
            System.out.println("  [PASS] " + name);
            passed++;
        } catch (AssertionError e) {
            System.out.println("  [FAIL] " + name + " — " + e.getMessage());
            failed++;
        } catch (Exception e) {
            System.out.println("  [ERR ] " + name + " — " + e.getClass().getSimpleName() + ": " + e.getMessage());
            failed++;
        }
    }

    // Helpers ────────────────────────────────────────────────────

    private static void assertNotNull(Object obj, String msg) {
        if (obj == null) throw new AssertionError(msg);
    }

    private static void assertNull(Object obj, String msg) {
        if (obj != null) throw new AssertionError(msg + " (got: " + obj + ")");
    }

    private static void assertEquals(Object expected, Object actual, String msg) {
        boolean equal = (expected == null) ? (actual == null) : expected.equals(actual);
        if (!equal) throw new AssertionError(msg + " — expected <" + expected + "> but got <" + actual + ">");
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition) throw new AssertionError(msg);
    }

    private static void assertFalse(boolean condition, String msg) {
        if (condition) throw new AssertionError(msg);
    }

    // ─── Data helpers ─────────────────────────────────────────────────────────

    private static Intervenant addIntervenant(String login, int minLevel, int maxLevel, int nbInterventions) {
        Intervenant i = new Intervenant(
            "Test", "Test", login, "testpass",
            minLevel, maxLevel, "0699999999", nbInterventions, true
        );
        userService.registerIntervenant(i);
        return i;
    }
    
    private static Student addStudent(String email, int level) {
        Student s = new Student("Test", "Test", email, "testpass", level, LocalDate.of(2006, 1, 1));
        userService.registerStudent(s, DataInitializer.EST_CODE);
        return s;
    }

    private static List<Demande> fetchDemandes(Student stu) {
        DemandeDao dao = new DemandeDao();
        List<Demande> result = null;
        try {
            JpaUtil.creerContextePersistance();
            result = dao.getDemandeListByStudent(stu);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    // ─── Authentication tests ──────────────────────────────────────────────

    private static void test_studentLogin_success() {
        Student stu = authService.loginStudent("luc@student.fr", "s1pass");
        assertNotNull(stu, "Valid credentials should return the student");
        assertEquals("luc@student.fr", stu.getEmail(), "Returned student should have the correct email");
    }

    private static void test_studentLogin_wrongPassword() {
        Student stu = authService.loginStudent("luc@student.fr", "wrongpassword");
        assertNull(stu, "Wrong password should return null");
    }

    private static void test_studentLogin_unknownEmail() {
        Student stu = authService.loginStudent("nobody@nowhere.fr", "anypass");
        assertNull(stu, "Unknown email should return null");
    }

    private static void test_intervenantLogin_success() {
        Intervenant interv = authService.loginIntervenant("alice@instructif.fr", "pass123");
        assertNotNull(interv, "Valid credentials should return the intervenant");
        assertEquals("alice@instructif.fr", interv.getLogin(), "Returned intervenant should have the correct login");
    }

    private static void test_intervenantLogin_wrongPassword() {
        Intervenant interv = authService.loginIntervenant("alice@instructif.fr", "badpass");
        assertNull(interv, "Wrong password should return null");
    }

    // ───Intervention tests ────────────────────────────────────────────────

    private static void test_createDemande_success() {
        
        addIntervenant("interv.l6@instructif.fr", 6, 6, 0);
        Student stu = addStudent("stu.l6@student.fr", 6);

        Demande dmd = interventionService.createDemande(stu, new Subjects("Mathématiques"), null, null);

        assertNotNull(dmd, "Should return a Demande when an intervenant is available");
        assertNotNull(dmd.getIntervenant(), "Demande should have an assigned intervenant");
        assertEquals("interv.l6@instructif.fr", dmd.getIntervenant().getLogin(), "Assigned intervenant should be the one at level 6");
        assertEquals("Mathématiques", dmd.getSubject().getName(), "Subject should be saved on the demande");
    }

    private static void test_createDemande_noMatch() {
        
        Student stu = addStudent("stu.l8@student.fr", 8);

        Demande dmd = interventionService.createDemande(stu, new Subjects("Histoire"), null, null);

        assertNull(dmd, "Should return null when no intervenant covers the student's level");
    }

    private static void test_loadBalancing() {
        // tests is the interv with the lowest num of interventions gets assigned
        Intervenant fresh = addIntervenant("fresh.l7@instructif.fr", 7, 7, 0);
        addIntervenant("busy.l7@instructif.fr", 7, 7, 5);
        Student stu = addStudent("stu.l7@student.fr", 7);

        Demande dmd = interventionService.createDemande(stu, new Subjects("Physique"), null, null);

        assertNotNull(dmd, "Should create a demande when intervenants are available");
        assertEquals(fresh.getId(), dmd.getIntervenant().getId(),
            "Load balancing should assign the intervenant with the fewest prior interventions");
    }

    private static void test_saveReport() {
        addIntervenant("interv.report@instructif.fr", 10, 10, 0);
        Student stu = addStudent("stu.report@student.fr", 10);

        Demande dmd = interventionService.createDemande(stu, new Subjects("Chimie"), null, null);
        assertNotNull(dmd, "Precondition: demande must be created");

        String reportText = "L'élève a bien progressé sur la résolution d'équations.";
        interventionService.saveInterventionReport(dmd, reportText);

        List<Demande> demandes = fetchDemandes(stu);
        assertNotNull(demandes, "Demande list should not be null");
        assertFalse(demandes.isEmpty(), "Demande list should contain at least one entry");
        assertEquals(reportText, demandes.get(0).getReport(), "Saved report should be readable back from the database");
    }

    // ─── 3. Concurrency test ──────────────────────────────────────────────────

    private static void test_concurrent() {
        // Exactly one intervenant available. two students race to get the slot. only one should get it
        addIntervenant("interv@instructif.fr", 9, 9, 0);
        Student racer1 = addStudent("r1@student.fr", 9);
        Student racer2 = addStudent("r2@student.fr", 9);

        CountDownLatch startGun  = new CountDownLatch(1);
        CountDownLatch bothDone  = new CountDownLatch(2);
        AtomicInteger successCount = new AtomicInteger(0);

        for (Student racer : new Student[]{ racer1, racer2 }) {
            new Thread(() -> {
                try { startGun.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                Demande d = interventionService.createDemande(racer, new Subjects("Mathématiques"), null, null);
                if (d != null) successCount.incrementAndGet();
                bothDone.countDown();
            }).start();
        }

        startGun.countDown(); // release both threads at the exact same instant
        try {
            boolean finished = bothDone.await(10, TimeUnit.SECONDS);
            assertTrue(finished, "it should finish by ow");
        } catch (InterruptedException e) {
            throw new AssertionError("Test was interrupted: " + e.getMessage());
        }

        assertEquals(1, successCount.get(),
            "Exactly one demande should succeed when only one intervenant slot is available");
    }

    // ─── Full "system" workflow ──────────────────────────────────────────

    private static void test_fullWorkflow() {
        
        addIntervenant("interv.wf@instructif.fr", 11, 11, 0);
        addStudent("stu.wf@student.fr", 11);

        Student loggedIn = authService.loginStudent("stu.wf@student.fr", "testpass");
        assertNotNull(loggedIn, "Student should be able to log in after registration");

        // Submit a demande
        Demande dmd = interventionService.createDemande(loggedIn, new Subjects("Informatique"), null, null);
        assertNotNull(dmd, "Demande should be created for the logged-in student");
        assertNotNull(dmd.getIntervenant(), "Demande should have an assigned intervenant");

        // Save the session report
        String report = "Séance productive — objectifs atteints.";
        interventionService.saveInterventionReport(dmd, report);

        // Verify the report is persisted
        List<Demande> history = fetchDemandes(loggedIn);
        assertNotNull(history, "History should not be null");
        assertEquals(1, history.size(), "Student should have exactly one demande in history");
        assertEquals(report, history.get(0).getReport(), "Persisted report must match");

        // Send the report by email 
        try {
            interventionService.sendReportByEmail(history.get(0));
        } catch (Exception e) {
            throw new AssertionError("sendReportByEmail should not throw: " + e.getMessage());
        }
    }
}
