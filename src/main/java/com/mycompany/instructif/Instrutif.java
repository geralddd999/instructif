/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.instructif;

import java.time.LocalDate;
import metier.modele.Student;
import dao.JpaUtil;
import metier.modele.Establishment;
import metier.service.EstablishmentManagementService;
import metier.service.UserManagementService;
/**
 *
 * @author gschambiram
 */
public class Instrutif {

    public static void main(String[] args) {
        Student student = new Student("lol","lol","lol","lol","lol",LocalDate.parse("2023-01-02"));
        
        System.out.println(student);
        
        JpaUtil.creerFabriquePersistance();
        
        UserManagementService stuserv = new UserManagementService();
        
        String msg = stuserv.registerStudent(student) ? "success" : "failed";
        
        System.out.println(msg);
        
        EstablishmentManagementService estabServ = new EstablishmentManagementService();
        Establishment e = estabServ.fetchInfoEst("0691664J");
        
        System.out.println(e);
        
        
    }
}
