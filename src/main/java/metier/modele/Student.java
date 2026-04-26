/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author gschambiram
 */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(unique=true)
    private String email;
    private String password;
    private Integer studentClass;
    private LocalDate birthDate;

    // would have to change this one to either many to many or many to one, cause a student
    // can only be registered at one lycee (logic constraint ig?)
    @ManyToOne
    private Establishment establishment;
    @OneToMany(mappedBy = "student")
    private List<Demande> demandes = new ArrayList<>();
    // would have to change this one to either many to many or many to one, cause a student
    // can only be registered at one lycee (logic con  

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

    public Integer getStudentClass() {
        return studentClass;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }


    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    

    
    

   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Student other = (Student) obj;
        return Objects.equals(this.id, other.id);
    }
    
    public Student() {
    }

    public Student(String lastName, String firstName, String email, String password, Integer studentClass, LocalDate birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.studentClass = studentClass;
        this.birthDate = birthDate;
        
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", password=" + password + ", studentClass=" + studentClass + ", birthDate=" + birthDate + '}';
    }

    public Establishment getEstablishment() {
       return this.establishment;
    }
    
    
}
