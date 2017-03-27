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
@Table(name = "vnutorne_bezpecnostne_rizika")
public class VnutorneBR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vnutorne_br_id")
    private long vnutorneBrId;

    @Column(name = "priklady_zranitelnosti")
    private String prikladZranitelnosti;
    @Column(name = "priklady_hrozieb")
    private String prikladHrozby;
    
    @ManyToOne
    @JoinColumn(name = "skupina_id")
    private VnutorneSkupinyBR skupinaBR;
    
    @OneToMany(mappedBy = "vnutorneRiziko", cascade = CascadeType.ALL)
    private List<RizikoObjektu> rizikaObjektu = new ArrayList<>();

    public VnutorneBR() {
    }

    public VnutorneBR(String prikladZranitelnosti, String prikladHrozby) {
        this.prikladZranitelnosti = prikladZranitelnosti;
        this.prikladHrozby = prikladHrozby;
    }

    public VnutorneBR(long vnutorneBrId, String prikladZranitelnosti, String prikladHrozby) {
        this.vnutorneBrId = vnutorneBrId;
        this.prikladZranitelnosti = prikladZranitelnosti;
        this.prikladHrozby = prikladHrozby;
    }

    public long getVnutorneBrId() {
        return vnutorneBrId;
    }

    public void setVnutorneBrId(long vnutorneBrId) {
        this.vnutorneBrId = vnutorneBrId;
    }

    public String getPrikladZranitelnosti() {
        return prikladZranitelnosti;
    }

    public void setPrikladZranitelnosti(String prikladZranitelnosti) {
        this.prikladZranitelnosti = prikladZranitelnosti;
    }

    public String getPrikladHrozby() {
        return prikladHrozby;
    }

    public void setPrikladHrozby(String prikladHrozby) {
        this.prikladHrozby = prikladHrozby;
    }

    public VnutorneSkupinyBR getSkupinaBR() {
        return skupinaBR;
    }

    public void setSkupinaBR(VnutorneSkupinyBR skupinaBR) {
        this.skupinaBR = skupinaBR;
    }

    public List<RizikoObjektu> getRizikaObjektu() {
        return rizikaObjektu;
    }

    public void setRizikaObjektu(List<RizikoObjektu> rizikaObjektu) {
        this.rizikaObjektu = rizikaObjektu;
    }
}
