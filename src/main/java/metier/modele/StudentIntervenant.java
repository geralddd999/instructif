package metier.modele;

import javax.persistence.Entity;

@Entity
public class StudentIntervenant extends Intervenant {
    private String university;
    private String speciality;

    public StudentIntervenant() {}

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
}
