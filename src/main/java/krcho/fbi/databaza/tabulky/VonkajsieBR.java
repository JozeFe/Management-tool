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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "vonkajsie_bezpecnostne_rizika")
public class VonkajsieBR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vonkajsie_br_id")
    private long vonkajsieBrId;

    @Column(name = "hrozba")
    private String hrozba;
    
    @ManyToOne
    @JoinColumn(name = "typ_br_id")
    private VonkajsiTypBR typBR;
    
    @OneToMany(mappedBy = "vonkajsieRiziko", cascade = CascadeType.ALL)
    private List<RizikoObjektu> rizikaObjektu = new ArrayList<>();

    public VonkajsieBR() {
    }

    public VonkajsieBR(String hrozba) {
        this.hrozba = hrozba;
    }

    public VonkajsieBR(long vonkajsieBrId, String hrozba) {
        this.vonkajsieBrId = vonkajsieBrId;
        this.hrozba = hrozba;
    }

    public long getVonkajsieBrId() {
        return vonkajsieBrId;
    }

    public void setVonkajsieBrId(long vonkajsieBrId) {
        this.vonkajsieBrId = vonkajsieBrId;
    }

    public String getHrozba() {
        return hrozba;
    }

    public void setHrozba(String hrozba) {
        this.hrozba = hrozba;
    }

    public VonkajsiTypBR getTypBR() {
        return typBR;
    }

    public void setTypBR(VonkajsiTypBR typBR) {
        this.typBR = typBR;
    }

    public List<RizikoObjektu> getRizikaObjektu() {
        return rizikaObjektu;
    }

    public void setRizikaObjektu(List<RizikoObjektu> rizikaObjektu) {
        this.rizikaObjektu = rizikaObjektu;
    }
}
