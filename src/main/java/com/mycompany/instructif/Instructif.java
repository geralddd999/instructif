/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.instructif;

import java.time.LocalDate;
import metier.modele.Student;
import dao.JpaUtil;
import metier.modele.Establishment;
import metier.service.UserManagementService;
/**
 *
 * @author gschambiram
 */
public class Instructif {

    public static void main(String[] args) {
        Student student = new Student("lol","lol",
                "lol@unique.com", "lol","lol",
                LocalDate.parse("2023-01-02"));
        
        System.out.println(student);
        
        JpaUtil.creerFabriquePersistance();
        
        UserManagementService mgmt = new UserManagementService();
        
        String msg = mgmt.registerStudent(student, "0691664J") ? "success" : "failed";
        
        Student s = mgmt.findStudentByEmail("lol@unique.com");
        Establishment est = s.getEstablishment();
        
        System.out.println(est);
        System.out.println(msg);

    }
}
