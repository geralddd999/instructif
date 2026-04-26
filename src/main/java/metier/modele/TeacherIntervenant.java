package metier.modele;

import javax.persistence.Entity;

@Entity
public class TeacherIntervenant extends Intervenant {
    private String institutionType;

    public TeacherIntervenant() {}

    public String getInstitutionType() { return institutionType; }
    public void setInstitutionType(String institutionType) { this.institutionType = institutionType; }
}
