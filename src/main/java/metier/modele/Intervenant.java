/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author gschambiram
 */
public class Intervenant {
   @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(unique=true)
    private String email;
    private String password;
    private ArrayList<Integer> level ;
    private String phoneNumber;
    private Integer nbInterventions;
    private Boolean available ; 

    public Intervenant(String lastName, String firstName, String email,
                       String password, ArrayList<Integer> level, String phoneNumber,
                       Integer nbInterventions, Boolean available) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.level = level;
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

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLevel(ArrayList<Integer> level) {
        this.level = level;
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
        return "Intervenant{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", password=" + password + ", level=" + level + ", phoneNumber=" + phoneNumber + ", nbInterventions=" + nbInterventions + ", available=" + available + '}';
    }
    
    
    
}
