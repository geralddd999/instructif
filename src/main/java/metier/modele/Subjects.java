/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.ArrayList;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @ElementCollection
    private ArrayList<String> themes;

    public Subjects(String name, ArrayList themes) {
        this.name = name;
        this.themes = themes;
    }

    public Subjects() {
    }
    

    public String getName() {
        return name;
    }

    public ArrayList getThemes() {
        return themes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThemes(ArrayList themes) {
        this.themes = themes;
    }

    @Override
    public String toString() {
        return "Subjects{" + "name=" + name + ", themes=" + themes + '}';
    }
    
    
    
}
