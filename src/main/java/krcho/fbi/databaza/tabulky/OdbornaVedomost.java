/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.List;
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
@Table(name = "odborne_vedomosti")
public class OdbornaVedomost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vedomost_id")
    private long vedomostId;

    @Column(name = "popis")
    private String popis;

    @OneToMany(mappedBy = "pozVedomostId.vedomost")
    private List<PozadovanaVedomost> pozadovaneVedomosti = new ArrayList<>();

    public OdbornaVedomost() {
    }

    public OdbornaVedomost(String popis) {
        this.popis = popis;
    }

    public OdbornaVedomost(long vedomostId, String popis) {
        this.vedomostId = vedomostId;
        this.popis = popis;
    }

    public long getVedomostId() {
        return vedomostId;
    }

    public void setVedomostId(long vedomostId) {
        this.vedomostId = vedomostId;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public List<PozadovanaVedomost> getPozadovaneVedomosti() {
        return pozadovaneVedomosti;
    }

    public void setPozadovaneVedomosti(List<PozadovanaVedomost> pozadovaneVedomosti) {
        this.pozadovaneVedomosti = pozadovaneVedomosti;
    }

}
