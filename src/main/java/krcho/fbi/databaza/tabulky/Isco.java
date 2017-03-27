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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "ISCO")
public class Isco {

    @Id
    @Column(name = "ISCO_kod")
    private String iscoKod;

    @Column(name = "popis_klasifikacie")
    private String popis;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy="Isco")
    private Set<TypovaPozicia> typovePozicie = new HashSet<>();
    
    public Isco() {
    }

    public Isco(String iscoKod, String popis) {
        this.iscoKod = iscoKod;
        this.popis = popis;
    }

    public String getIscoKod() {
        return iscoKod;
    }

    public void setIscoKod(String iscoKod) {
        this.iscoKod = iscoKod;
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
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.iscoKod);
        hash = 97 * hash + Objects.hashCode(this.popis);
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
        final Isco other = (Isco) obj;
        if (!Objects.equals(this.iscoKod, other.iscoKod)) {
            return false;
        }
        if (!Objects.equals(this.popis, other.popis)) {
            return false;
        }
        return true;
    }
    
    
}
