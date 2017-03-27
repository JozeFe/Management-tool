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
@Table(name = "vonkajsie_typy_hrozby")
public class VonkajsiTypBR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typ_br_id")
    private long typBrId;

    @Column(name = "nazov")
    private String nazov;
    
    @OneToMany(mappedBy = "typBR", cascade = CascadeType.ALL)
    private List<VonkajsieBR> VonkajsieBR = new ArrayList<>();

    public VonkajsiTypBR() {
    }

    public VonkajsiTypBR(String nazov) {
        this.nazov = nazov;
    }

    public VonkajsiTypBR(long typBrId, String nazov) {
        this.typBrId = typBrId;
        this.nazov = nazov;
    }

    public long getTypBrId() {
        return typBrId;
    }

    public void setTypBrId(long typBrId) {
        this.typBrId = typBrId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public List<VonkajsieBR> getVonkajsieBR() {
        return VonkajsieBR;
    }

    public void setVonkajsieBR(List<VonkajsieBR> VonkajsieBR) {
        this.VonkajsieBR = VonkajsieBR;
    }
}
