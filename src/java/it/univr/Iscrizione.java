package it.univr;

import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "isc")
@SessionScoped
public class Iscrizione implements Serializable {

	private DataSource ds;
	/*
	private String erogazione;
	private String studente;
	*/
	public Iscrizione(){
		this.ds=null;
		/*
		this.erogazione=null;
		this.studente=null;
		*/
	}

	@PostConstruct
    public void initialize() {
        try {
            this.ds = new DataSource();
        } catch (ClassNotFoundException e) {
            this.ds = null;
        }
    } 
	/*
	public void setStudente(String codiceStudente){
		this.studente=codiceStudente;
	}


	public String getStudente(){
		return this.studente;
	}


	public void setErogazione(String codiceErogazione){
		this.erogazione=codiceErogazione;
	}


	public String getErogazione(){
		return this.erogazione;
	}
	*/
	public String iscrivi(String er, String st){
		if(this.ds != null){
        	boolean valid = this.ds.doIscrizione(er, st);
        	if (valid) {            
            return "/prog/iscritto.jsf";
        	}else{ 
                return "/prog/alexist.jsf";
        	}
    	}
   		return "/prog/erroe.jsf";
	}

}