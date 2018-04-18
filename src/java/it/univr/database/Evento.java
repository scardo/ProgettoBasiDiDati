package it.univr.database;

public class Evento {

  // === Properties
  // ============================================================

  private String id;
  private String codiceErogazione;
  private String tipo;
  private int[] data;
  private int[] ora;
  private String docente;

  // === Methods ===============================================================

  public Evento() {
    data = new int[3];
    id = null;
    codiceErogazione = null;
    ora = new int[2];
    docente = null;
    tipo = null;
  }

  public Evento(String id, String codiceErogazione, String tipo, int[] data, int[] ora, String docente) {
    this.id = id;
    this.codiceErogazione = codiceErogazione;
    this.tipo = tipo;
    this.data = data;
    this.ora = ora;
    this.docente = docente;
  }

  public int[] getData() {
    return data;
  }

  public String getDataS() {
    return ""+data[0]+"/"+data[1]+"/"+data[2];
  }

  public void setData(int[] data) {
    this.data = data;
  }

  public void setData(int part, int data) {
    this.data[part] = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodiceErogazione() {
    return codiceErogazione;
  }

  public void setCodiceErogazione(String codiceErogazione) {
    this.codiceErogazione = codiceErogazione;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public int[] getOra() {
    return ora;
  }

  public String getOraS() {
    return ""+ora[0]+":"+ora[1];
  }

  public void setOra(int[] ora) {
    this.ora = ora;
  }

  public void setOra(int part, int ora) {
    this.ora[part] = ora;
  }

  public String getDocente() {
    return docente;
  }

  public void setDocente(String docente) {
    this.docente = docente;
  }

}