package com.mycompany.instructif;

import dao.JpaUtil;
import utils.DataInitializer;
import utils.SystemTest;

public class Instructif {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();  
        DataInitializer.init();      
        SystemTest.runAll();                  // runs all tests and prints results
        JpaUtil.fermerFabriquePersistance();
    }
}
