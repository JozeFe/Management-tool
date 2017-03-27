/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
@Table(name = "odborna_prax")
public class OdbornaPrax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prax_id")
    private long praxId;

    @OneToMany(mappedBy = "odbornaPrax", cascade = CascadeType.ALL)
    private List<TypovaPozicia> typovePozicie = new ArrayList<>();

    @Column(name = "popis")
    private String popis;

    public OdbornaPrax() {
    }
    
    public OdbornaPrax(String popis) {
        this.popis = popis;
    }

    public OdbornaPrax(long praxId, String popis) {
        this.praxId = praxId;
        this.popis = popis;
    }

    public long getPraxId() {
        return praxId;
    }

    public void setPraxId(long praxId) {
        this.praxId = praxId;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public List<TypovaPozicia> getTypovePozicie() {
        return typovePozicie;
    }

    public void setTypovePozicie(List<TypovaPozicia> typovePozicie) {
        this.typovePozicie = typovePozicie;
    }

    @Override
    public String toString() {
        return popis;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.praxId ^ (this.praxId >>> 32));
        hash = 29 * hash + Objects.hashCode(this.popis);
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
        final OdbornaPrax other = (OdbornaPrax) obj;
        if (this.praxId != other.praxId) {
            return false;
        }
        if (!Objects.equals(this.popis, other.popis)) {
            return false;
        }
        return true;
    }
    
    
}
