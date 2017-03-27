/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "popis_cinnosti")
public class PopisCinnosti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinnost_id")
    private long cinnostId;

    @Column(name = "popis")
    private String popis;
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy="popisyCinnosti")
    private Set<TypovaPozicia> typovePozicie = new HashSet<>();

    public PopisCinnosti() {
    }

    public PopisCinnosti(String popis) {
        this.popis = popis;
    }

    public PopisCinnosti(long cinnostId, String popis) {
        this.cinnostId = cinnostId;
        this.popis = popis;
    }

    public long getCinnostId() {
        return cinnostId;
    }

    public void setCinnostId(long cinnostId) {
        this.cinnostId = cinnostId;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Set<TypovaPozicia> getTypovePozicie() {
        return typovePozicie;
    }

    public void setTypovePozicie(Set<TypovaPozicia> typovePozicie) {
        this.typovePozicie = typovePozicie;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.cinnostId ^ (this.cinnostId >>> 32));
        hash = 89 * hash + Objects.hashCode(this.popis);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PopisCinnosti other = (PopisCinnosti) obj;
        if (this.cinnostId != other.cinnostId) {
            return false;
        }
        if (!Objects.equals(this.popis, other.popis)) {
            return false;
        }
        return true;
    }
    
    
}
