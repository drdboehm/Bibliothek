/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thomas Papke
 */
@Entity
@Table(name = "author", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
    @NamedQuery(name = "Author.findByAuthorId", query = "SELECT a FROM Author a WHERE a.authorId = :authorId"),
    @NamedQuery(name = "Author.findByAuthorFname", query = "SELECT a FROM Author a WHERE a.authorFname = :authorFname"),
    @NamedQuery(name = "Author.findByAuthorLname", query = "SELECT a FROM Author a WHERE a.authorLname = :authorLname"),
    @NamedQuery(name = "Author.findByAuthorGeb", query = "SELECT a FROM Author a WHERE a.authorGeb = :authorGeb")})
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "author_id", nullable = false)
    private Integer authorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "author_fname", nullable = false, length = 255)
    private String authorFname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "author_lname", nullable = false, length = 255)
    private String authorLname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "author_geb", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date authorGeb;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorId")
    private Collection<Buchauthor> buchauthorCollection;

    public Author() {
    }

    public Author(Integer authorId) {
        this.authorId = authorId;
    }

    public Author(Integer authorId, String authorFname, String authorLname, Date authorGeb) {
        this.authorId = authorId;
        this.authorFname = authorFname;
        this.authorLname = authorLname;
        this.authorGeb = authorGeb;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFname() {
        return authorFname;
    }

    public void setAuthorFname(String authorFname) {
        this.authorFname = authorFname;
    }

    public String getAuthorLname() {
        return authorLname;
    }

    public void setAuthorLname(String authorLname) {
        this.authorLname = authorLname;
    }

    public Date getAuthorGeb() {
        return authorGeb;
    }

    public void setAuthorGeb(Date authorGeb) {
        this.authorGeb = authorGeb;
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
        hash += (authorId != null ? authorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.authorId == null && other.authorId != null) || (this.authorId != null && !this.authorId.equals(other.authorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return authorLname;
    }
    
}
