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
@Table(name = "hrozby")
public class Hrozba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hrozba_id")
    private long hrozbaId;

    @Column(name = "zdroj_hrozby")
    private String zdrojHrozby;
    
    @Column(name = "motivacia")
    private String motivacia;
    
    @Column(name = "mozne_dosledky")
    private String mozneDosledky;
    
    @OneToMany(mappedBy = "hrozba", cascade = CascadeType.ALL)
    private List<ZdrojHrozby> zdrojeHrozieb = new ArrayList<>();

    public Hrozba() {
    }

    public Hrozba(String zdrojHrozby, String motivacia, String mozneDosledky) {
        this.zdrojHrozby = zdrojHrozby;
        this.motivacia = motivacia;
        this.mozneDosledky = mozneDosledky;
    }

    public Hrozba(long hrozbaId, String zdrojHrozby, String motivacia, String mozneDosledky) {
        this.hrozbaId = hrozbaId;
        this.zdrojHrozby = zdrojHrozby;
        this.motivacia = motivacia;
        this.mozneDosledky = mozneDosledky;
    }

    public long getHrozbaId() {
        return hrozbaId;
    }

    public void setHrozbaId(long hrozbaId) {
        this.hrozbaId = hrozbaId;
    }

    public String getZdrojHrozby() {
        return zdrojHrozby;
    }

    public void setZdrojHrozby(String zdrojHrozby) {
        this.zdrojHrozby = zdrojHrozby;
    }

    public String getMotivacia() {
        return motivacia;
    }

    public void setMotivacia(String motivacia) {
        this.motivacia = motivacia;
    }

    public String getMozneDosledky() {
        return mozneDosledky;
    }

    public void setMozneDosledky(String mozneDosledky) {
        this.mozneDosledky = mozneDosledky;
    }

    public List<ZdrojHrozby> getZdrojeHrozieb() {
        return zdrojeHrozieb;
    }

    public void setZdrojeHrozieb(List<ZdrojHrozby> zdrojeHrozieb) {
        this.zdrojeHrozieb = zdrojeHrozieb;
    }
    
    
    

}
