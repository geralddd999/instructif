package metier.modele;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Establishment;
import metier.modele.Intervenant;
import metier.modele.Student;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-04-22T22:54:08", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Demande.class)
public class Demande_ { 

    public static volatile SingularAttribute<Demande, LocalDateTime> date;
    public static volatile SingularAttribute<Demande, Integer> durationMinutes;
    public static volatile SingularAttribute<Demande, Student> student;
    public static volatile SingularAttribute<Demande, String> subject;
    public static volatile SingularAttribute<Demande, String> report;
    public static volatile SingularAttribute<Demande, Establishment> establishment;
    public static volatile SingularAttribute<Demande, Long> id;
    public static volatile SingularAttribute<Demande, Intervenant> intervenant;

}