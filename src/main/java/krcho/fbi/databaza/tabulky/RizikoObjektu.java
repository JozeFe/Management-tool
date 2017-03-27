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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import krcho.fbi.databaza.tabulky.enumy.TypRizika;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "rizika_objektu")
public class RizikoObjektu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "riziko_objektu_id")
    private long rizikoObjektuId;

    @Enumerated(EnumType.STRING)
    @Column(name = "typ_rizika")
    private TypRizika typRizika;
    
    @ManyToOne
    @JoinColumn(name = "objekt_id")
    private TypObjektu typObjektu;
    
    @ManyToOne
    @JoinColumn(name = "vnutorne_br_id")
    private VnutorneBR vnutorneRiziko;
    
    @ManyToOne
    @JoinColumn(name = "vonkajsie_br_id")
    private VonkajsieBR vonkajsieRiziko;
    
    @OneToMany(mappedBy = "rizikoObjektu", cascade = CascadeType.ALL)
    private List<ZdrojHrozby> zdrojeHrozieb = new ArrayList<>();

    public RizikoObjektu() {
    }

    public RizikoObjektu(TypRizika typRizika) {
        this.typRizika = typRizika;
    }

    public RizikoObjektu(long rizikoObjektuId, TypRizika typRizika) {
        this.rizikoObjektuId = rizikoObjektuId;
        this.typRizika = typRizika;
    }

    public long getRizikoObjektuId() {
        return rizikoObjektuId;
    }

    public void setRizikoObjektuId(long rizikoObjektuId) {
        this.rizikoObjektuId = rizikoObjektuId;
    }

    public TypRizika getTypRizika() {
        return typRizika;
    }

    public void setTypRizika(TypRizika typRizika) {
        this.typRizika = typRizika;
    }

    public TypObjektu getTypObjektu() {
        return typObjektu;
    }

    public void setTypObjektu(TypObjektu typObjektu) {
        this.typObjektu = typObjektu;
    }

    public VnutorneBR getVnutorneRiziko() {
        return vnutorneRiziko;
    }

    public void setVnutorneRiziko(VnutorneBR vnutorneRiziko) {
        this.vnutorneRiziko = vnutorneRiziko;
    }

    public VonkajsieBR getVonkajsieRiziko() {
        return vonkajsieRiziko;
    }

    public void setVonkajsieRiziko(VonkajsieBR vonkajsieRiziko) {
        this.vonkajsieRiziko = vonkajsieRiziko;
    }

    public List<ZdrojHrozby> getZdrojeHrozieb() {
        return zdrojeHrozieb;
    }

    public void setZdrojeHrozieb(List<ZdrojHrozby> zdrojeHrozieb) {
        this.zdrojeHrozieb = zdrojeHrozieb;
    }

    @Override
    public String toString() {
        if (typRizika == TypRizika.VNUTORNE) {
            return vnutorneRiziko.getSkupinaBR().getNazov() + " - " + vnutorneRiziko.getPrikladZranitelnosti();
        } 
        return vonkajsieRiziko.getTypBR().getNazov() + " - " + vonkajsieRiziko.getHrozba();
    }
}
