import dao.DemandeDao;
import dao.JpaUtil;
import metier.modele.Demande;
import metier.modele.Intervenant;
import metier.modele.Student;
import metier.service.AuthenticationService;
import metier.service.InterventionManagementService;
import metier.service.UserManagementService;
import utils.DataInitializer;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SystemTest {

    private static final UserManagementService        userService         = new UserManagementService();
    private static final AuthenticationService        authService         = new AuthenticationService();
    private static final InterventionManagementService interventionService = new InterventionManagementService();

    private static int passed = 0;
    private static int failed = 0;

    // ─── Entry point ──────────────────────────────────────────────────────────

    public static void runAll() {
        passed = 0;
        failed = 0;
        System.out.println("\n========= RUNNING SYSTEM TESTS =========\n");

        // 1. Authentication
        run("Student login — correct credentials",     SystemTest::test_studentLogin_success);
        run("Student login — wrong password",          SystemTest::test_studentLogin_wrongPassword);
        run("Student login — unknown email",           SystemTest::test_studentLogin_unknownEmail);
        run("Intervenant login — correct credentials", SystemTest::test_intervenantLogin_success);
        run("Intervenant login — wrong password",      SystemTest::test_intervenantLogin_wrongPassword);

        // 2. Intervention
        run("Create demande — success",                SystemTest::test_createDemande_success);
        run("Create demande — no intervenant match",   SystemTest::test_createDemande_noMatch);
        run("Create demande — load balancing",         SystemTest::test_loadBalancing);
        run("Save intervention report",                SystemTest::test_saveReport);

        // 3. Concurrency
        run("Concurrent demandes — one available slot",SystemTest::test_concurrent);

        // 4. Full workflow
        run("Full end-to-end workflow",                SystemTest::test_fullWorkflow);

        System.out.println("\n========= " + passed + " passed  |  " + failed + " failed =========\n");
    }

    // ─── Mini test runner ─────────────────────────────────────────────────────

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

    // ─── Assertion helpers ────────────────────────────────────────────────────

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

    /** Persists a fresh intervenant at the given level range with a preset intervention count. */
    private static Intervenant seedIntervenant(String login, int minLevel, int maxLevel, int nbInterventions) {
        Intervenant i = new Intervenant(
            "Test", "Test", login, "testpass",
            minLevel, maxLevel, "0699999999", nbInterventions, true
        );
        userService.registerIntervenant(i);
        return i;
    }

    /** Persists a fresh student at the given level, linked to the test establishment. */
    private static Student seedStudent(String email, int level) {
        Student s = new Student("Test", "Test", email, "testpass", level, LocalDate.of(2006, 1, 1));
        userService.registerStudent(s, DataInitializer.EST_CODE);
        return s;
    }

    /** Reads all demandes for a student from a fresh persistence context. */
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

    // ─── 1. Authentication tests ──────────────────────────────────────────────

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

    // ─── 2. Intervention tests ────────────────────────────────────────────────

    private static void test_createDemande_success() {
        // Level 6 is exclusive to this test — no seeded intervenants cover it.
        seedIntervenant("interv.l6@instructif.fr", 6, 6, 0);
        Student stu = seedStudent("stu.l6@student.fr", 6);

        Demande dmd = interventionService.createDemande(stu, "Mathématiques");

        assertNotNull(dmd, "Should return a Demande when an intervenant is available");
        assertNotNull(dmd.getIntervenant(), "Demande should have an assigned intervenant");
        assertEquals("interv.l6@instructif.fr", dmd.getIntervenant().getLogin(), "Assigned intervenant should be the one at level 6");
        assertEquals("Mathématiques", dmd.getSubject(), "Subject should be saved on the demande");
    }

    private static void test_createDemande_noMatch() {
        // Level 8 — no intervenants seeded for this level.
        Student stu = seedStudent("stu.l8@student.fr", 8);

        Demande dmd = interventionService.createDemande(stu, "Histoire");

        assertNull(dmd, "Should return null when no intervenant covers the student's level");
    }

    private static void test_loadBalancing() {
        // Two intervenants at level 7: one with 0 prior interventions, one with 5.
        // The query sorts by nbInterventions ASC, so the fresh one must be chosen first.
        Intervenant fresh = seedIntervenant("fresh.l7@instructif.fr", 7, 7, 0);
        seedIntervenant("busy.l7@instructif.fr", 7, 7, 5);
        Student stu = seedStudent("stu.l7@student.fr", 7);

        Demande dmd = interventionService.createDemande(stu, "Physique");

        assertNotNull(dmd, "Should create a demande when intervenants are available");
        assertEquals(fresh.getId(), dmd.getIntervenant().getId(),
            "Load balancing should assign the intervenant with the fewest prior interventions");
    }

    private static void test_saveReport() {
        seedIntervenant("interv.report@instructif.fr", 10, 10, 0);
        Student stu = seedStudent("stu.report@student.fr", 10);

        Demande dmd = interventionService.createDemande(stu, "Chimie");
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
        // Exactly ONE intervenant at level 9. Two students race to get the slot.
        // With optimistic locking, the loser retries, finds nobody available, and returns null.
        seedIntervenant("interv.race@instructif.fr", 9, 9, 0);
        Student racer1 = seedStudent("racer1@student.fr", 9);
        Student racer2 = seedStudent("racer2@student.fr", 9);

        CountDownLatch startGun  = new CountDownLatch(1);
        CountDownLatch bothDone  = new CountDownLatch(2);
        AtomicInteger successCount = new AtomicInteger(0);

        for (Student racer : new Student[]{ racer1, racer2 }) {
            new Thread(() -> {
                try { startGun.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                Demande d = interventionService.createDemande(racer, "Mathématiques");
                if (d != null) successCount.incrementAndGet();
                bothDone.countDown();
            }).start();
        }

        startGun.countDown(); // release both threads at the exact same instant
        try {
            boolean finished = bothDone.await(10, TimeUnit.SECONDS);
            assertTrue(finished, "Both threads should complete within 10 seconds");
        } catch (InterruptedException e) {
            throw new AssertionError("Test was interrupted: " + e.getMessage());
        }

        assertEquals(1, successCount.get(),
            "Exactly one demande should succeed when only one intervenant slot is available");
    }

    // ─── 4. Full end-to-end workflow ──────────────────────────────────────────

    private static void test_fullWorkflow() {
        // Level 11 — isolated level for this test.
        seedIntervenant("interv.wf@instructif.fr", 11, 11, 0);
        seedStudent("stu.wf@student.fr", 11);

        // Authenticate (simulates the login page)
        Student loggedIn = authService.loginStudent("stu.wf@student.fr", "testpass");
        assertNotNull(loggedIn, "Student should be able to log in after registration");

        // Submit a demande
        Demande dmd = interventionService.createDemande(loggedIn, "Informatique");
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

        // Send the report by email (just verify no exception is thrown)
        try {
            interventionService.sendReportByEmail(history.get(0));
        } catch (Exception e) {
            throw new AssertionError("sendReportByEmail should not throw: " + e.getMessage());
        }
    }
}
