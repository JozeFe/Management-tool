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
@Table(name="vnutorne_skupiny_br")
public class VnutorneSkupinyBR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skupina_id")
    private long skupinaId;

    @Column(name = "nazov")
    private String nazov;
    
    @OneToMany(mappedBy = "skupinaBR", cascade = CascadeType.ALL)
    private List<VnutorneBR> VnutorneBR = new ArrayList<>();

    public VnutorneSkupinyBR() {
    }

    public VnutorneSkupinyBR(String nazov) {
        this.nazov = nazov;
    }

    public VnutorneSkupinyBR(long skupinaId, String nazov) {
        this.skupinaId = skupinaId;
        this.nazov = nazov;
    }

    public long getSkupinaId() {
        return skupinaId;
    }

    public void setSkupinaId(long skupinaId) {
        this.skupinaId = skupinaId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public List<VnutorneBR> getVnutorneBR() {
        return VnutorneBR;
    }

    public void setVnutorneBR(List<VnutorneBR> VnutorneBR) {
        this.VnutorneBR = VnutorneBR;
    }
}
