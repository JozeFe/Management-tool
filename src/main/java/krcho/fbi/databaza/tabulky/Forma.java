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
@Table(name = "formy_fyzickej_ochrany")
public class Forma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forma_id")
    private long formaId;

    @Column(name = "nazov")
    private String nazov;
    @Column(name = "obsah")
    private String obsah;

    @ManyToOne
    @JoinColumn(name = "zdroje_hrozieb_id")
    private ZdrojHrozby zdrojHrozby;

    public Forma() {
    }

    public Forma(String nazov, String obsah) {
        this.nazov = nazov;
        this.obsah = obsah;
    }

    public Forma(long formaId, String nazov, String obsah) {
        this.formaId = formaId;
        this.nazov = nazov;
        this.obsah = obsah;
    }

    public long getFormaId() {
        return formaId;
    }

    public void setFormaId(long formaId) {
        this.formaId = formaId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getObsah() {
        return obsah;
    }

    public void setObsah(String obsah) {
        this.obsah = obsah;
    }

    public ZdrojHrozby getZdrojHrozby() {
        return zdrojHrozby;
    }

    public void setZdrojHrozby(ZdrojHrozby zdrojHrozby) {
        this.zdrojHrozby = zdrojHrozby;
    }

}
