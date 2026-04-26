package metier.modele;

import java.time.LocalDateTime;
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
    private Student student;

    @ManyToOne
    private Intervenant intervenant;

    @ManyToOne
    private Establishment establishment;

    private Subjects subject;
    private Theme theme;

    private LocalDateTime date;
    private Integer durationMinutes;
    private String request;
    private String report;

    public Demande() {}

    public Long getId() { return id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Intervenant getIntervenant() { return intervenant; }
    public void setIntervenant(Intervenant intervenant) { this.intervenant = intervenant; }

    public Establishment getEstablishment() { return establishment; }
    public void setEstablishment(Establishment establishment) { this.establishment = establishment; }

    public Subjects getSubject() { return subject; }
    public void setSubject(Subjects subject) { this.subject = subject; }

    public Theme getTheme() {return theme;}
    public void setTheme(Theme theme) { this.theme = theme; }


    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getRequest() { return request; }
    public void setRequest(String request) { this.request = request; }


    public String getReport() { return report; }
    public void setReport(String report) { this.report = report; }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", subject=" + subject +
                ", theme='" + theme + '\'' +
                ", request='" + request + '\'' +
                ", date=" + date +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}

