/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import metier.modele.Theme;

/**
 *
 * @author bbelkho
 */
public class ThemeDao {
    
    public void create(Theme theme) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(theme);
    }

    public Theme findById(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Theme.class, id);
    }
    
}
