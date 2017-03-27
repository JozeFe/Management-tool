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
@Table(name = "zdroje_hrozieb")
public class ZdrojHrozby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zdroje_hrozieb_id")
    private long zdrojHrozbyId;
    
    @ManyToOne
    @JoinColumn(name = "riziko_objektu_id")
    private RizikoObjektu rizikoObjektu;
    
    @ManyToOne
    @JoinColumn(name = "hrozba_id")
    private Hrozba hrozba;
    
    @OneToMany(mappedBy = "zdrojHrozby", cascade = CascadeType.ALL)
    private List<Forma> formy = new ArrayList<>();
    
    @OneToMany(mappedBy = "zdrojHrozby", cascade = CascadeType.ALL)
    private List<Metoda> metody = new ArrayList<>();
    
    @OneToMany(mappedBy = "zdrojHrozby", cascade = CascadeType.ALL)
    private List<Kompetencia> kompetencie = new ArrayList<>();

    public ZdrojHrozby() {
    }

    public ZdrojHrozby(long zdrojHrozbyId) {
        this.zdrojHrozbyId = zdrojHrozbyId;
    }

    public long getZdrojHrozbyId() {
        return zdrojHrozbyId;
    }

    public void setZdrojHrozbyId(long zdrojHrozbyId) {
        this.zdrojHrozbyId = zdrojHrozbyId;
    }

    public Hrozba getHrozba() {
        return hrozba;
    }

    public void setHrozba(Hrozba hrozba) {
        this.hrozba = hrozba;
    }

    public RizikoObjektu getRizikoObjektu() {
        return rizikoObjektu;
    }

    public void setRizikoObjektu(RizikoObjektu rizikoObjektu) {
        this.rizikoObjektu = rizikoObjektu;
    }

    public List<Forma> getFormy() {
        return formy;
    }

    public void setFormy(List<Forma> formy) {
        this.formy = formy;
    }

    public List<Metoda> getMetody() {
        return metody;
    }

    public void setMetody(List<Metoda> metody) {
        this.metody = metody;
    }

    public List<Kompetencia> getKompetencie() {
        return kompetencie;
    }

    public void setKompetencie(List<Kompetencia> kompetencie) {
        this.kompetencie = kompetencie;
    }    
    
    
}
