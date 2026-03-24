/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Subjects subject;

    private Theme theme;
    private String request;

    @ManyToOne
    private Student student;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate date;

    private Integer duration;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.PERSIST)
    private Intervention intervention;

    public Demande() {
    }

    public Demande(Subjects subject, Theme theme, String request,
                   Student student, Status status,
                   LocalDate date, Integer duration) {
        this.subject = subject;
        this.theme = theme;
        this.request = request;
        this.student = student;
        this.status = status;
        this.date = date;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public Subjects getSubject() {
        return subject;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getRequest() {
        return request;
    }

    public Student getStudent() {
        return student;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getDuration() {
        return duration;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", subject=" + subject +
                ", theme='" + theme + '\'' +
                ", request='" + request + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}