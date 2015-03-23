/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappe;

import data.Author;
import data.Buch;
import data.Buchauthor;
import data.Exemplar;
import data.Verlag;
import data.Verlagbuch;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author dboehm
 */
@ManagedBean
@SessionScoped
@Named(value = "buchBean")
public class BuchBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 
     the Kunden - Map for the  <h:selectOneMenu ... 
     */
    private final Map<String, Integer> alleAuthorMap = new HashMap();
    /* 
     /* 
     primary keyIds as Integer from relevant Entities
     */
    private Integer authorId;
    private Integer buchId;
    private Author author;
    private String authorFname;
    private String authorLname;
    private String authorGeb;
    private List<Author> autorenList = new ArrayList<>();
    private String buchTitel;
    private String verlagName;
    private String verlagBuchISBN;
    private String exampleNr;
    private Verlag verlag;
    private Buch buch;
    private Verlagbuch verlagBuch;
    private Exemplar exemplar;
    private Buchauthor buchauthor;

    public String getAuthorFname() {
        return authorFname;
    }

    public String getBuchTitel() {
        return buchTitel;
    }

    public void setBuchTitel(String buchTitel) {
        this.buchTitel = buchTitel;
    }

    public String getVerlagName() {
        return verlagName;
    }

    public void setVerlagName(String verlagName) {
        this.verlagName = verlagName;
    }

    public String getVerlagBuchISBN() {
        return verlagBuchISBN;
    }

    public void setVerlagBuchISBN(String verlagBuchISBN) {
        this.verlagBuchISBN = verlagBuchISBN;
    }

    public String getExampleNr() {
        return exampleNr;
    }

    public void setExampleNr(String exampleNr) {
        this.exampleNr = exampleNr;
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

    public String getAuthorGeb() {
        return authorGeb;
    }

    public void setAuthorGeb(String authorGeb) {
        this.authorGeb = authorGeb;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getBuchId() {
        return buchId;
    }

    public void setBuchId(Integer buchId) {
        this.buchId = buchId;
    }

    /*
     injects the session bean
     */
    @EJB
    MySessionBean ms;

    /**
     * Creates a new instance of BuchBean
     */
    public BuchBean() {
    }

    public List<Author> getAllAuthors() {
        return ms.getAllAuthors();
    }

    /**
     * create and returns a Map with concatenated String of all Kunde -Objects
     * to show in the <h:selectOneMenu ...>
     * and the kundeId as key
     *
     * @return
     */
    public Map<String, Integer> getAllAuthorMap() {
        for (Author next : ms.getAllAuthors()) {
            this.alleAuthorMap.put(next.getAuthorLname() + ", "
                    + next.getAuthorFname() + ", " + next.getAuthorGeb().toLocaleString().substring(0, 10), next.getAuthorId());
        }
        //this can be done, because there is no authorId 0 in database 
        this.alleAuthorMap.put("neuen Autor anlegen", 0);
        return alleAuthorMap;
    }

    public Author getAuthor() {
        return author;
    }

//
//    public Author getAuthorById() {
//        System.out.println("autorId" + this.authorId);
//           List<Author> a = ms.getAuthorById(authorId);
//           return a.get(0);
    public void setAuthor(Author author) {
        this.author = author;
    }

//    }
    public String evaluateAuthor() {
        if (this.authorId == 0) {
            return "./newBuchPage.xhtml";
        } else {
            System.out.println("The authorId = " + authorId);
            author = ms.getAuthorById(authorId);
            ms.setNewAuthor(author.getAuthorFname(), author.getAuthorLname(), author.getAuthorGeb());
            return "./index.xhtml";
        }
    }

    public String resetAuthors() {
        autorenList.clear();
        return "./index.xhtml";
    }

    public String addNewAuthor() {
        System.out.println("authorId: " + this.authorId);
        /*  we have the name and geb fields 
         now find the autorId if exists or create authorId if not
         fine that ms.setnewAuthor(,,) give that back, either the existing author object or the new object
         */
        Date gebDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            gebDate = df.parse(authorGeb);
        } catch (ParseException ex) {
            Logger.getLogger(BuchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("authorFname: " + this.getAuthorFname());
        System.out.println("authorLname: " + this.getAuthorLname());
        System.out.println("das Datum: " + gebDate);

        // Bring new Autor to persistence or get authorId from persistense by LName
        author = ms.setNewAuthor(this.getAuthorFname(), this.getAuthorLname(), gebDate);
        autorenList.add(author);

//       hardcoded test
//        author = ms.setNewAuthor("TEst", "Crichton", new Date());
        System.out.println("authorId: " + author.getAuthorId());
        System.out.println("authorFname: " + author.getAuthorFname());
        System.out.println("authorLname: " + author.getAuthorLname());
        System.out.println("authorGeb: " + author.getAuthorGeb());

        return "./index.xhtml";
    }

    public String submitBuchEntry() {
        // all data shoud now be here
        // check
        for (Author a : getAutorenList()) {
            System.out.println("Autor : " + a.getAuthorLname());
        }
        System.out.println("Buchtitel :" + getBuchTitel());
        System.out.println("Verlagname :" + getVerlagName());
        System.out.println("ISBN  :" + getVerlagBuchISBN());
        System.out.println("ExemplarNr :" + getExampleNr());
        // yes, it is all together
        verlag = ms.setNewVerlag(verlagName);
        // check
        System.out.println("The verlagId= " + verlag.getVerlagId());
        System.out.println("The verlagName= " + verlag.getVerlagName());
        buch = ms.setNewBuch(buchTitel);
        // check
        System.out.println("The buchId= " + buch.getBuchId());
        System.out.println("The buchTitel= " + buch.getBuchTitel());
        verlagBuch = ms.setNewVerlagBuch(verlag, buch, getVerlagBuchISBN());
        // check
        System.out.println("The verlagBuchId= " + verlagBuch.getVerlagbuchId());
        System.out.println("The VerlagId= " + verlagBuch.getVerlagId());
        System.out.println("The buchId= " + verlagBuch.getBuchId());
        System.out.println("The VerlagbuchIsbn= " + verlagBuch.getVerlagbuchIsbn());
        exemplar = ms.setNewExemplar(verlagBuch, exampleNr);
        //check 
        System.out.println("The exemplarId= " + exemplar.getExemplarId());
        System.out.println("The verlagsBuchId= " + exemplar.getVerlagbuchId());
        System.out.println("The exemplarNr= " + exemplar.getExemplarNr());

        // finally stote the author list is the buchauthor table
        for (Author a : getAutorenList()) {
            buchauthor = ms.setNewBuchAuthor(a, buch);
            // check 
            System.out.println("The buchauthorId= " + buchauthor.getBuchauthorId());
            System.out.println("The authorId= " + buchauthor.getAuthorId());
            System.out.println("The buchId= " + buchauthor.getBuchId());

        }

        return "./success.xhtml";
    }

    public String setAllAuthors() {

        return addNewAuthor();
    }

    public List<Author> getAutorenList() {

        return autorenList;
    }

    public void setAutorenList(List<Author> autorenList) {
        this.autorenList = autorenList;
    }

}
