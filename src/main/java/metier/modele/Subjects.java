/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;
import javax.persistence.*;
import java.util.ArrayList;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author DELL
 */
@Entity
public class Subjects {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST)
    private ArrayList<Theme> themes = new ArrayList<Theme>();
    
    
    
    public Subjects(String name) {
        this.name = name;
    }

    public Subjects() {
    }

    public Long getId() {
        return id;
    }
    
    
    public String getName() {
        return name;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTheme(Theme theme) {
        themes.add(theme);
        theme.setSubjects(this);
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    @Override
    public String toString() {
        return "Subjects{" + "id=" + id + ", name=" + name + '}';
    }
    

    
    
    
    
}
