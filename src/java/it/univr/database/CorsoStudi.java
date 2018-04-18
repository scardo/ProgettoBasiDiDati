/**
 * Bean per la tabella (campi principali) CorsoStudi
 */
package it.univr.database;

public class CorsoStudi {

  // === Properties ============================================================

  private String nome;
  private String categoria;
  private String programma;
  private int nMaxStud;
  private int nOreLez;

  // === Methods ===============================================================

  public CorsoStudi() {
    nome = null;
  	categoria = null;
  	programma =null;
  	nMaxStud = -1;
  	nOreLez = -1;
  }

  public CorsoStudi(String nome, String categoria, String programma, int nMaxStud, int nOreLez) {
    this.nome = nome;
  	this.categoria = categoria;
  	this.programma = programma;
  	this.nMaxStud = nMaxStud;
  	this.nOreLez = nOreLez;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome( String nome ) {
    this.nome = nome;
  }

  public String getCategoria() {
    return this.categoria;
  }

  public void setCategoria( String categoria ) {
  	this.categoria = categoria;
  }

  public String getProgramma() {
    return this.programma;
  }

  public void setProgramma( String programma ) {
    this.programma = programma;
  }

  public int getNMaxStud() {
    return this.nMaxStud;
  }

  public void setNMaxStud( int nMaxStud ) {
    this.nMaxStud = nMaxStud;
  }

  public int getNOreLez() {
    return this.nOreLez;
  }

  public void setNOreLez( int nOreLez ) {
    this.nOreLez = nOreLez;
  }

  public String toString(){
    return ""+nome;
  }
}