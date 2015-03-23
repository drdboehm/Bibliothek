/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thomas Papke
 */
@Entity
@Table(name = "kunde", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kunde.findAll", query = "SELECT k FROM Kunde k"),
    @NamedQuery(name = "Kunde.findByKundeId", query = "SELECT k FROM Kunde k WHERE k.kundeId = :kundeId"),
    @NamedQuery(name = "Kunde.findByKundeFname", query = "SELECT k FROM Kunde k WHERE k.kundeFname = :kundeFname"),
    @NamedQuery(name = "Kunde.findByKundeName", query = "SELECT k FROM Kunde k WHERE k.kundeName = :kundeName"),
    @NamedQuery(name = "Kunde.findByKundePlz", query = "SELECT k FROM Kunde k WHERE k.kundePlz = :kundePlz"),
    @NamedQuery(name = "Kunde.findByKundeOrt", query = "SELECT k FROM Kunde k WHERE k.kundeOrt = :kundeOrt"),
    @NamedQuery(name = "Kunde.findByKundeStr", query = "SELECT k FROM Kunde k WHERE k.kundeStr = :kundeStr")})
public class Kunde implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kunde_id", nullable = false)
    private Integer kundeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "kunde_fname", nullable = false, length = 255)
    private String kundeFname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "kunde_name", nullable = false, length = 255)
    private String kundeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "kunde_plz", nullable = false, length = 5)
    private String kundePlz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "kunde_ort", nullable = false, length = 255)
    private String kundeOrt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "kunde_str", nullable = false, length = 255)
    private String kundeStr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kundeId")
    private Collection<Leih> leihCollection;

    public Kunde() {
    }

    public Kunde(Integer kundeId) {
        this.kundeId = kundeId;
    }

    public Kunde(Integer kundeId, String kundeFname, String kundeName, String kundePlz, String kundeOrt, String kundeStr) {
        this.kundeId = kundeId;
        this.kundeFname = kundeFname;
        this.kundeName = kundeName;
        this.kundePlz = kundePlz;
        this.kundeOrt = kundeOrt;
        this.kundeStr = kundeStr;
    }

    public Integer getKundeId() {
        return kundeId;
    }

    public void setKundeId(Integer kundeId) {
        this.kundeId = kundeId;
    }

    public String getKundeFname() {
        return kundeFname;
    }

    public void setKundeFname(String kundeFname) {
        this.kundeFname = kundeFname;
    }

    public String getKundeName() {
        return kundeName;
    }

    public void setKundeName(String kundeName) {
        this.kundeName = kundeName;
    }

    public String getKundePlz() {
        return kundePlz;
    }

    public void setKundePlz(String kundePlz) {
        this.kundePlz = kundePlz;
    }

    public String getKundeOrt() {
        return kundeOrt;
    }

    public void setKundeOrt(String kundeOrt) {
        this.kundeOrt = kundeOrt;
    }

    public String getKundeStr() {
        return kundeStr;
    }

    public void setKundeStr(String kundeStr) {
        this.kundeStr = kundeStr;
    }

    @XmlTransient
    public Collection<Leih> getLeihCollection() {
        return leihCollection;
    }

    public void setLeihCollection(Collection<Leih> leihCollection) {
        this.leihCollection = leihCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kundeId != null ? kundeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kunde)) {
            return false;
        }
        Kunde other = (Kunde) object;
        if ((this.kundeId == null && other.kundeId != null) || (this.kundeId != null && !this.kundeId.equals(other.kundeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Kunde[ kundeId=" + kundeId + " ]";
    }
    
}
