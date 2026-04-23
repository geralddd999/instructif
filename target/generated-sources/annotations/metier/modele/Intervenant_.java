package metier.modele;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-04-23T17:09:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Intervenant.class)
public class Intervenant_ { 

    public static volatile SingularAttribute<Intervenant, Integer> minLevel;
    public static volatile SingularAttribute<Intervenant, Integer> maxLevel;
    public static volatile SingularAttribute<Intervenant, String> lastName;
    public static volatile SingularAttribute<Intervenant, String> firstName;
    public static volatile SingularAttribute<Intervenant, String> password;
    public static volatile SingularAttribute<Intervenant, String> phoneNumber;
    public static volatile SingularAttribute<Intervenant, Integer> nbInterventions;
    public static volatile SingularAttribute<Intervenant, Boolean> available;
    public static volatile SingularAttribute<Intervenant, Long> id;
    public static volatile SingularAttribute<Intervenant, String> login;

}