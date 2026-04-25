package com.mycompany.instructif;

import dao.JpaUtil;
import utils.DataInitializer;

public class Instructif {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();  // drops and recreates the schema
        DataInitializer.initialize();         // seeds establishment, intervenants, students
        SystemTest.runAll();                  // runs all tests and prints results
        JpaUtil.fermerFabriquePersistance();
    }
}
