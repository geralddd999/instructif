package metier.modele;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Establishment;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-04-22T09:02:53", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, String> lastName;
    public static volatile SingularAttribute<Student, String> firstName;
    public static volatile SingularAttribute<Student, String> password;
    public static volatile SingularAttribute<Student, Establishment> establishment;
    public static volatile SingularAttribute<Student, Long> id;
    public static volatile SingularAttribute<Student, LocalDate> birthDate;
    public static volatile SingularAttribute<Student, String> email;
    public static volatile SingularAttribute<Student, Integer> studentClass;

}