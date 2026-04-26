/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import jakarta.json.JsonObject;
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
    private String sector;
    private String address;
    private String postalCode;
    private String commune;
    private Double latitude;
    private Double longitude;
    private String departmentCode;
    private String academyCode;
    private String departmentName;
    private String academyName;

    public Establishment() {
    }

    public Establishment(String codeEstablishment, String name, String sector,
                         String address, String postalCode, String commune,
                         Double latitude, Double longitude, String departmentCode,
                         String academyCode, String departmentName, String academyName)
    {
        this.codeEstablishment = codeEstablishment;
        this.name = name;
        this.sector = sector;
        this.address = address;
        this.postalCode = postalCode;
        this.commune = commune;
        this.latitude = latitude;
        this.longitude = longitude;
        this.departmentCode = departmentCode;
        this.academyCode = academyCode;
        this.departmentName = departmentName;
        this.academyName = academyName;
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
