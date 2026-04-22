/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author gschambiram
 */
@Entity
@Inheritance (strategy=InheritanceType.JOINED)
public class Intervenant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(unique=true)
    private String login;
    private String password;
    private Integer minLevel ;
    private Integer maxLevel ;
    private String phoneNumber;
    private Integer nbInterventions;
    private Boolean available ; 

    public Intervenant(String lastName, String firstName, String login,
                       String password, Integer minLevel, Integer maxLevel, String phoneNumber,
                       Integer nbInterventions, Boolean available) 
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.phoneNumber = phoneNumber;
        this.nbInterventions = nbInterventions;
        this.available = available;
    }

    public Intervenant() {
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getNbInterventions() {
        return nbInterventions;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNbInterventions(Integer nbInterventions) {
        this.nbInterventions = nbInterventions;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Intervenant{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", login=" + login + ", password=" + password + ", level=" + minLevel + ", phoneNumber=" + phoneNumber + ", nbInterventions=" + nbInterventions + ", available=" + available + '}';
    }
    
    
    
}
