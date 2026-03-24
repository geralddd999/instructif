/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.instructif;

import java.time.LocalDate;
import metier.modele.Student;
import dao.JpaUtil;
import metier.modele.Establishment;
import metier.service.EstablishmentService;
import metier.service.StudentService;
import metier.service.SubjectsService;
/**
 *
 * @author gschambiram
 */
public class Instrutif {

    public static void main(String[] args) {
        /*Student student = new Student("lol","lol","lol","lol","lol",LocalDate.parse("2023-01-02"));
        String Code ="A12456";
        
        
        JpaUtil.creerFabriquePersistance();
        
        StudentService stuserv = new StudentService();
        
        String msg = stuserv.registerStudent(student, Code) ? "success" : "failed";
        
        System.out.println(msg);
        
        EstablishmentService estabServ = new EstablishmentService();
        //Establishment e = estabServ.fetchInfoEst("0691664J");
        
        //System.out.println(e);
        */
        
        SubjectsService.initializSubjectsTable();
        
    }
}
