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
@Table(name = "odborne_zrucnosti")
public class OdbornaZrucnost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zrucnost_id")
    private long zrucnostId;

    @Column(name = "popis")
    private String popis;

    @OneToMany(mappedBy = "pozZrucnostId.zrucnost")
    private List<PozadovanaZrucnost> pozadovaneZrucnosti = new ArrayList<>();

    public OdbornaZrucnost() {
    }

    public OdbornaZrucnost(String popis) {
        this.popis = popis;
    }

    public OdbornaZrucnost(long zrucnostId, String popis) {
        this.zrucnostId = zrucnostId;
        this.popis = popis;
    }

    public long getZrucnostId() {
        return zrucnostId;
    }

    public void setZrucnostId(long zrucnostId) {
        this.zrucnostId = zrucnostId;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public List<PozadovanaZrucnost> getPozadovaneZrucnosti() {
        return pozadovaneZrucnosti;
    }

    public void setPozadovaneZrucnosti(List<PozadovanaZrucnost> pozadovaneZrucnosti) {
        this.pozadovaneZrucnosti = pozadovaneZrucnosti;
    }

    @Override
    public String toString() {
        return popis;
    }
}
