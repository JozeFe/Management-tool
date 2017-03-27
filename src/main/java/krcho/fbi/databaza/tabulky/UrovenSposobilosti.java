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
@Table(name = "uroven_sposobilosti")
public class UrovenSposobilosti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uroven_id")
    private long urovenId;

    @Column(name = "stupen_narocnosti")
    private String stupenNarocnosti;

    @OneToMany(mappedBy = "urovenSposobilosti", cascade = CascadeType.ALL)
    private List<Sposobilost> sposobilosti = new ArrayList<>();

    public UrovenSposobilosti() {
    }

    public UrovenSposobilosti(String stupenNarocnosti) {
        this.stupenNarocnosti = stupenNarocnosti;
    }

    public UrovenSposobilosti(long urovenId, String stupenNarocnosti) {
        this.urovenId = urovenId;
        this.stupenNarocnosti = stupenNarocnosti;
    }

    public long getUrovenId() {
        return urovenId;
    }

    public void setUrovenId(long urovenId) {
        this.urovenId = urovenId;
    }

    public String getStupenNarocnosti() {
        return stupenNarocnosti;
    }

    public void setStupenNarocnosti(String stupenNarocnosti) {
        this.stupenNarocnosti = stupenNarocnosti;
    }

    public List<Sposobilost> getSposobilosti() {
        return sposobilosti;
    }

    public void setSposobilosti(List<Sposobilost> sposobilosti) {
        this.sposobilosti = sposobilosti;
    }

    @Override
    public String toString() {
        return stupenNarocnosti;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (this.urovenId ^ (this.urovenId >>> 32));
        hash = 73 * hash + Objects.hashCode(this.stupenNarocnosti);
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
        final UrovenSposobilosti other = (UrovenSposobilosti) obj;
        if (this.urovenId != other.urovenId) {
            return false;
        }
        if (!Objects.equals(this.stupenNarocnosti, other.stupenNarocnosti)) {
            return false;
        }
        return true;
    }
    
    
}
