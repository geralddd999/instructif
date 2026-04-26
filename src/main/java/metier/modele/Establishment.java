package metier.modele;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Establishment {
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
    private Double ips;
    private Double ipsEcartType;

    public Establishment() {}

    public Establishment(String codeEstablishment, String name, String sector,
                         String address, String postalCode, String commune,
                         Double latitude, Double longitude, String departmentCode,
                         String academyCode, String departmentName, String academyName) {
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

    public Long getId() { return id; }

    public String getCodeEstablishment() { return codeEstablishment; }
    public void setCodeEstablishment(String codeEstablishment) { this.codeEstablishment = codeEstablishment; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getAcademyCode() { return academyCode; }
    public void setAcademyCode(String academyCode) { this.academyCode = academyCode; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getAcademyName() { return academyName; }
    public void setAcademyName(String academyName) { this.academyName = academyName; }

    public Double getIps() { return ips; }
    public void setIps(Double ips) { this.ips = ips; }

    public Double getIpsEcartType() { return ipsEcartType; }
    public void setIpsEcartType(Double ipsEcartType) { this.ipsEcartType = ipsEcartType; }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.id, ((Establishment) obj).id);
    }

    @Override
    public String toString() {
        return "Establishment{code=" + codeEstablishment + ", name=" + name + ", ips=" + ips + '}';
    }
}
