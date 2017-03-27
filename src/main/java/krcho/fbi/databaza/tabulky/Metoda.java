/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import krcho.fbi.databaza.tabulky.enumy.TypMetody;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name="metody_fyzickej_ochrany")
public class Metoda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metoda_id")
    private long metodaId;

    @Column(name = "nazov")
    private String nazov;
    
    @Column(name = "popis")
    private String popis;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "typ")
    private TypMetody typMetody;
    
    @ManyToOne
    @JoinColumn(name = "zdroje_hrozieb_id")
    private ZdrojHrozby zdrojHrozby;

    public Metoda() {
    }

    public Metoda(String nazov, String popis, TypMetody typMetody) {
        this.nazov = nazov;
        this.popis = popis;
        this.typMetody = typMetody;
    }

    public Metoda(long metodaId, String nazov, String popis, TypMetody typMetody) {
        this.metodaId = metodaId;
        this.nazov = nazov;
        this.popis = popis;
        this.typMetody = typMetody;
    }

    public long getMetodaId() {
        return metodaId;
    }

    public void setMetodaId(long metodaId) {
        this.metodaId = metodaId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public TypMetody getTypMetody() {
        return typMetody;
    }

    public void setTypMetody(TypMetody typMetody) {
        this.typMetody = typMetody;
    }

    public ZdrojHrozby getZdrojHrozby() {
        return zdrojHrozby;
    }

    public void setZdrojHrozby(ZdrojHrozby zdrojHrozby) {
        this.zdrojHrozby = zdrojHrozby;
    }
    
    
}
