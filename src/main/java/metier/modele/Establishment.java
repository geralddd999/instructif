/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author gschambiram
 */
@Entity
public class Establishment{

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codeEstablishment;
    private String name;
    //private String sector;
    //private String address;
    //private Integer postalCode;
    //private String commune;
    //private Double latitute;
    //private Double longitude;
    //private Integer departmentCode;
    //private Integer academyCode;
    //private String department;
    //private String academy;

    public Establishment() {
    }

    public Establishment(String codeEstablishment, String nameEstablishment) {
        this.codeEstablishment = codeEstablishment;
        this.name = nameEstablishment;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Establishment other = (Establishment) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Establishment{" + "codeEstablishment=" + codeEstablishment + ", name=" + name + '}';
    }

    
    
}
