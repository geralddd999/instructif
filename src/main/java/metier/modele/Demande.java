/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author DELL
 */

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    private String matiere;
    private String theme;
    private String request;
    private Student student;
    private Enum Status;
    private LocalDate Date;
    private Integer Duration;

    public Demande() {
    }

    public Demande(Long id, String matiere, String theme, String request, Student student, LocalDate Date, Integer Duration) {
        this.id = id;
        this.matiere = matiere;
        this.theme = theme;
        this.request = request;
        this.student = student;
        this.Date = Date;
        this.Duration = Duration;
    }

    public Long getId() {
        return id;
    }

    public String getMatiere() {
        return matiere;
    }

    public String getTheme() {
        return theme;
    }

    public String getRequest() {
        return request;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getDate() {
        return Date;
    }

    public Integer getDuration() {
        return Duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setDate(LocalDate Date) {
        this.Date = Date;
    }

    public void setDuration(Integer Duration) {
        this.Duration = Duration;
    }

    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", matiere=" + matiere + ", theme=" + theme + ", request=" + request + ", student=" + student + ", Date=" + Date + ", Duration=" + Duration + '}';
    }

    
    
    
    
    
}
