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
import javax.persistence.ManyToOne;
import javax.security.auth.Subject;

/**
 *
 * @author bbelkho
 */
@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Subjects subject;

    public Theme() {
    }

    public Theme(String name, Subjects subject) {
        this.name = name;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Theme other = (Theme) obj;
        return true;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjects(Subjects subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Theme{" + "name=" + name + '}';
    }
    
    
    
    
    
}
