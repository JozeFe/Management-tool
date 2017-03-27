/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "kompetencie_osob")
public class Kompetencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kompetencia_id")
    private long kompetenciaId;

    @Column(name = "nazov")
    private String nazov;
    @Column(name = "popis")
    private String popis;

    @ManyToOne
    @JoinColumn(name = "zdroje_hrozieb_id")
    private ZdrojHrozby zdrojHrozby;
    
    public Kompetencia() {
    }

    public Kompetencia(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
    }

    public Kompetencia(long kompetenciaId, String nazov, String popis) {
        this.kompetenciaId = kompetenciaId;
        this.nazov = nazov;
        this.popis = popis;
    }

    public long getkompetenciaId() {
        return kompetenciaId;
    }

    public void setkompetenciaId(long kompetenciaId) {
        this.kompetenciaId = kompetenciaId;
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

    public ZdrojHrozby getZdrojHrozby() {
        return zdrojHrozby;
    }

    public void setZdrojHrozby(ZdrojHrozby zdrojHrozby) {
        this.zdrojHrozby = zdrojHrozby;
    }
    
    
}
