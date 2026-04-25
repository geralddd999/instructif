/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author bbelkho
 */
@Entity
public class Intervention {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    @ManyToOne
    private Intervenant intervenant ;
    @OneToOne
    private Demande demande ; 
    private String lienvisio;
    private String bilan ;

    public Intervention() {
    }

    public Intervention(Long id, Intervenant intervenant, Demande demande, String lienvisio, String bilan) {
        this.id = id;
        this.intervenant = intervenant;
        this.demande = demande;
        this.lienvisio = lienvisio;
        this.bilan = bilan;
    }

    public Long getId() {
        return id;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public Demande getDemande() {
        return demande;
    }

    public String getLienvisio() {
        return lienvisio;
    }

    public String getBilan() {
        return bilan;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public void setLienvisio(String lienvisio) {
        this.lienvisio = lienvisio;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    @Override
    public String toString() {
        return "Intervention{" + "id=" + id + ", intervenant=" + intervenant + ", demande=" + demande + ", lienvisio=" + lienvisio + ", bilan=" + bilan + '}';
    }
    
    
    
}
