package it.univr;

import it.univr.database.CorsoStudi;
import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "cs")
@SessionScoped

public class CorsoStudiView implements Serializable {

  // === Properties ============================================================

  // Alcune variabili 

  private DataSource ds;
  private List<CorsoStudi> listaCorsi;
  private CorsoStudi corsoSelezionato;
  private String nomeric;
  private String categoriaric;
  private String programmaric;
  private int nMaxStudRic;
  private int nOreLezRic;


  // === Methods ===============================================================


  public CorsoStudiView() {
     this.listaCorsi = null;
     this.corsoSelezionato = null;
	   this.nomeric = null;
	   this.categoriaric = null;
	   this.programmaric = null;
	   this.nMaxStudRic = -1;
	   this.nOreLezRic = -1;

  }

  @PostConstruct
  public void initialize() {
    try {
      this.ds = new DataSource();
    } catch( ClassNotFoundException e ){
      this.ds = null;
    }
  }

  public List<CorsoStudi> getCorsi(String categoriacerca, String programmacerca, String sottostringanelnome, int maxStud, int oreLezTot) {
    if( this.ds != null ){
			
      listaCorsi = ds.getCorsiStudiAttivi(categoriacerca, programmacerca, sottostringanelnome, maxStud, oreLezTot) ;
    }
    return listaCorsi;
  }

  public String recuperaCorso( String nome){
    if( this.ds != null ){

      
      corsoSelezionato = ds.getCorsoStudi( nome );
      
    }
    else{
    	if(listaCorsi!= null && !listaCorsi.isEmpty()){
    		for(CorsoStudi corso : listaCorsi){
    			if(corso.getNome()!=null){
    				if(corso.getNome().equalsIgnoreCase(nome)){
    					corsoSelezionato = corso;
    				}
    			}
    		}
    	}
    }
    return "/Template/corsoPrev";
  }

  public CorsoStudi getCorso(){
    return corsoSelezionato;
  }

  public String getNome() {
    return this.nomeric;
  }

  public void setNome( String nomeric ) {
    this.nomeric = nomeric;
  }

  public String getCategoria() {
    return this.categoriaric;
  }

  public void setCategoria( String categoriaric ) {
  	this.categoriaric = categoriaric;
  }

  public String getProgramma() {
    return this.programmaric;
  }

  public void setProgramma( String programmaric ) {
    this.programmaric = programmaric;
  }

  public int getNMaxStud() {
    return this.nMaxStudRic;
  }

  public void setNMaxStud( int nMaxStudRic ) {
    this.nMaxStudRic = nMaxStudRic;
  }

  public int getNOreLez() {
    return this.nOreLezRic;
  }

  public void setNOreLez( int nOreLezRic ) {
    this.nOreLezRic = nOreLezRic;
  }
}