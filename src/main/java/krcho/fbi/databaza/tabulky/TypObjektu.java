/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "typ_objektu")
public class TypObjektu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "objekt_id")
    private long objektId;

    @Column(name = "nazov")
    private String nazov;

    @Column(name = "popis")
    private String popis;
    
    @OneToMany(mappedBy = "typObjektu", cascade = CascadeType.ALL)
    private List<RizikoObjektu> rizikaObjektu = new ArrayList<>();

    public TypObjektu() {
    }

    public TypObjektu(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
    }

    public TypObjektu(long objektId, String nazov, String popis) {
        this.objektId = objektId;
        this.nazov = nazov;
        this.popis = popis;
    }

    public long getObjektId() {
        return objektId;
    }

    public void setObjektId(long objektId) {
        this.objektId = objektId;
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

    public List<RizikoObjektu> getRizikaObjektu() {
        return rizikaObjektu;
    }

    public void setRizikaObjektu(List<RizikoObjektu> rizikaObjektu) {
        this.rizikaObjektu = rizikaObjektu;
    }
}
