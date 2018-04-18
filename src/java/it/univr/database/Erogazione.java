/**
 * Erogazioni
 */
package it.univr.database;

public class Erogazione {
	
	// === Properties ============================================================
	
	private String codice;
	private int[] dataInizio;
	private int[] dataFine;
	private String stato;
	private Integer posti;
	
	// === Methods ===============================================================
	
	public Erogazione(){
		codice=null;
		dataInizio=new int[3];
		dataFine=new int[3];
		stato=null;
		posti=-1;
	}
	
	public Erogazione(String code, int[] inizio, int[] fine, String state){
		codice=code;
		dataInizio=inizio;
		dataFine=fine;
		stato=state;
		posti=-1;
	}
	
	public Erogazione(String code, int[] inizio, int[] fine, String state, int seats){
		codice=code;
		dataInizio=inizio;
		dataFine=fine;
		stato=state;
		posti=seats;
	}
	
	public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public int[] getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(int[] dataInizio) {
        this.dataInizio = dataInizio;
    }

    public void setDataInizio(int part, int dataInizio) {
        this.dataInizio[part] = dataInizio;
    }

    public int[] getDataFine() {
        return dataFine;
    }

    public void setDataFine(int[] dataFine) {
        this.dataFine = dataFine;
    }

    public void setDataFine(int part,int dataFine) {
        this.dataFine[part] = dataFine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public String getPostiAv(){
    	String av = "";
    	if(stato.equals("IA")){
    		av="<li><b>Posti: </b>"+this.posti+"</li>";
    	}
    	return av;
    }

    public String getStringDataI(){
    	return dataInizio[0]+"/"+dataInizio[1]+"/"+dataInizio[2];
    }

    public String getStringDataF(){
    	return dataFine[0]+"/"+dataFine[1]+"/"+dataFine[2];
    }

    public String getIscORDip(){
    	String iod = "";
    	if(stato.equals("IA")){
    		iod="<li><b>Iscrizione</b></li>";
    	}
    	if(stato.equals("CH")){
    		iod="<li><b>Diplomati</b></li>";
    	}
    	return iod;
    }

     public String getIod(){
        String iod = "";
        if(stato.equals("IA")){
            iod="iscrizione";
        }
        if(stato.equals("CH")){
            iod="diplomati";
        }
        return iod;
    }
}