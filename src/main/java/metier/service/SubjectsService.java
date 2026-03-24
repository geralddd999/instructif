/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.JpaUtil;
import dao.SubjectsDao;
import java.io.InputStream;
import metier.modele.Subjects;

/**
 *
 * @author DELL
 */
public class SubjectsService {
    private static SubjectsDao subjectsDao = new SubjectsDao();
    public static void initializSubjectsTable(){
        
        
        try {

            // 1️⃣ Create persistence context
            JpaUtil.creerContextePersistance();

            // 2️⃣ Open transaction
            JpaUtil.ouvrirTransaction();

            // 3️⃣ Load JSON file
            ObjectMapper mapper = new ObjectMapper();

            InputStream input =
                    SubjectsService.class.getClassLoader()
                    .getResourceAsStream("Subjects.json");

            Subjects[] subjects =
                    mapper.readValue(input, Subjects[].class);

            // 4️⃣ Persist subjects
            for (Subjects subject : subjects) {

                subjectsDao.create(subject);

            }

            // 5️⃣ Commit transaction
            JpaUtil.validerTransaction();

            System.out.println("Subjects inserted successfully ✅");

        } catch (Exception e) {

            // rollback if error
            JpaUtil.annulerTransaction();

            e.printStackTrace();

        } finally {

            // close persistence context
            JpaUtil.fermerContextePersistance();

        }
        
        
        
        
        
        
    }
    
}




