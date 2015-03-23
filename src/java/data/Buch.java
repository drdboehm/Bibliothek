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
@Table(name = "buch", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buch.findAll", query = "SELECT b FROM Buch b"),
    @NamedQuery(name = "Buch.findByBuchId", query = "SELECT b FROM Buch b WHERE b.buchId = :buchId"),
    @NamedQuery(name = "Buch.findByBuchTitel", query = "SELECT b FROM Buch b WHERE b.buchTitel = :buchTitel")})
public class Buch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "buch_id", nullable = false)
    private Integer buchId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "buch_titel", nullable = false, length = 256)
    private String buchTitel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buchId")
    private Collection<Verlagbuch> verlagbuchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buchId")
    private Collection<Buchauthor> buchauthorCollection;

    public Buch() {
    }

    public Buch(Integer buchId) {
        this.buchId = buchId;
    }

    public Buch(Integer buchId, String buchTitel) {
        this.buchId = buchId;
        this.buchTitel = buchTitel;
    }

    public Integer getBuchId() {
        return buchId;
    }

    public void setBuchId(Integer buchId) {
        this.buchId = buchId;
    }

    public String getBuchTitel() {
        return buchTitel;
    }

    public void setBuchTitel(String buchTitel) {
        this.buchTitel = buchTitel;
    }

    @XmlTransient
    public Collection<Verlagbuch> getVerlagbuchCollection() {
        return verlagbuchCollection;
    }

    public void setVerlagbuchCollection(Collection<Verlagbuch> verlagbuchCollection) {
        this.verlagbuchCollection = verlagbuchCollection;
    }

    @XmlTransient
    public Collection<Buchauthor> getBuchauthorCollection() {
        return buchauthorCollection;
    }

    public void setBuchauthorCollection(Collection<Buchauthor> buchauthorCollection) {
        this.buchauthorCollection = buchauthorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buchId != null ? buchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buch)) {
            return false;
        }
        Buch other = (Buch) object;
        if ((this.buchId == null && other.buchId != null) || (this.buchId != null && !this.buchId.equals(other.buchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Buch[ buchId=" + buchId + " ]";
    }
    
}
