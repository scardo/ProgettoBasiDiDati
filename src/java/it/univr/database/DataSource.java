package it.univr.database;


import it.univr.database.PasswordHash;
import java.util.Calendar;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Questa classe mette a disposizione i metodi per effettuare interrogazioni
 * sulla base di dati.
 */
public class DataSource implements Serializable {
  
  // === Properties ============================================================

  // dati di identificazione dell'utente (da personalizzare)
  private String user = "userlab41";
  private String passwd = "quarantaunoUd";

  // URL per la connessione alla base di dati e' formato dai seguenti
  // componenti: <protocollo>://<host del server>/<nome base di dati>.
  private String url = "jdbc:postgresql://dbserver.scienze.univr.it/dblab41";

  // Driver da utilizzare per la connessione e l'esecuzione delle query.
  private String driver = "org.postgresql.Driver";


  // -- definizione delle query ------------------------------------------------


  private String psw
  	="SELECT password "
  	+"FROM Studente "
  	+"WHERE codice=?";

  private String cca = "SELECT Corso.* "
    +"FROM Corso INNER JOIN Erogato "
    +"ON (nome = nomeCorso) INNER JOIN Erogazione "
    +"ON (codiceErogazione = codice) "
    +"WHERE Erogazione.stato != 'CH' ";

  private String cs = "SELECT * "
    +"FROM Corso "
    +"WHERE nome =? ";
	
  private String cle = "SELECT Erogazione.codice, "
    +"date_part('Day', Erogazione.dataInizio) AS GiornoInizio, "
    +"date_part('Month', Erogazione.dataInizio) AS meseInizio, "
    +"date_part('Year', Erogazione.dataInizio) AS annoInizio, "
    +"date_part('Day', Erogazione.dataFine) AS giornoFine, "
    +"date_part('Month', Erogazione.dataFine) AS meseFine, "
    +"date_part('Year', Erogazione.dataFine) AS annoFine, "
    +"Erogazione.stato, Erogazione.postiDisp "
    +"FROM Corso INNER JOIN Erogato "
    +"ON (nome = nomeCorso) INNER JOIN Erogazione "
    +"ON (codiceErogazione = codice) "
    +"WHERE Corso.nome =? ";
	
  private String ce ="SELECT Erogazione.codice, "
    +"date_part('Day', Erogazione.dataInizio) AS GiornoInizio, "
    +"date_part('Month', Erogazione.dataInizio) AS meseInizio, "
    +"date_part('Year', Erogazione.dataInizio) AS annoInizio, "
    +"date_part('Day', Erogazione.dataFine) AS giornoFine, "
    +"date_part('Month', Erogazione.dataFine) AS meseFine, "
    +"date_part('Year', Erogazione.dataFine) AS annoFine, "
    +"Erogazione.stato, Erogazione.postiDisp "
    +"FROM Erogazione "
    +"WHERE codice =? ";

  private String cld = "SELECT Docente.* "
    +"FROM Docente INNER JOIN Evento ON (CF = CFDoc) " 
    +"WHERE Evento.CodiceErogazione = ? ";

  private String cde = "SELECT Docente.* "
    +"FROM Docente INNER JOIN Evento ON (CF = CFDoc) " 
    +"WHERE Evento.ID = ? ";

  private String clevt = "SELECT Evento.ID, Evento.CodiceErogazione, "
    +"Evento.Tipo, date_part('Day', Evento.data) AS giorno, date_part('Month', Evento.data) AS mese, date_part('Year', Evento.data) AS anno, "
    +"date_part('Hour', Evento.ora) AS ora, date_part('Minute', Evento.ora) AS minuti, Evento.CFDoc "
    +"FROM Corso INNER JOIN Erogato ON (nome = nomeCorso) INNER JOIN Erogazione ON (codiceErogazione = codice) "
    +"INNER JOIN Evento ON (Erogazione.codice = Evento.CodiceErogazione) "
    +"WHERE Corso.nome = ? ";

  private String cevt = "SELECT Evento.ID, Evento.CodiceErogazione, "
    +"Evento.Tipo, date_part('Day', Evento.data) AS giorno, date_part('Month', Evento.data) AS mese, date_part('Year', Evento.data) AS anno, "
    +"date_part('Hour', Evento.ora) AS ora, date_part('Minute', Evento.ora) AS minuti, Evento.CFDoc "
    +"FROM Evento "
    +"WHERE Evento.ID = ?";

  private String ncd = "SELECT Docente.Nome, Docente.Cognome "
    +"FROM Docente "
    +"WHERE Docente.CF = ? ";

  private String stud = "SELECT s.codice, s.nome, s.cognome, s.cittaRes, s.Email "
    +"FROM Studente s JOIN diploma d ON (s.codice=d.codiceStudente) "
    +"WHERE d.CodiceErogazione=? ";
  
  private String aExist="SELECT 1 FROM Iscrizione WHERE CodiceErogazione=? AND CodiceStudente=?";

  private String iscrivi="INSERT INTO Iscrizione VALUES (?,?,?,?)";



  // === Methods ===============================================================

  /**
   * Costruttore della classe. Carica i driver da utilizzare per la connessione
   * alla base di dati.
   *
   * @throws ClassNotFoundException Eccezione generata nel caso in cui i driver
   * per la connessione non siano trovati nel CLASSPATH.
   */
  public DataSource() throws ClassNotFoundException {
    Class.forName( driver );
  }

  /**
   * Il metodo costruisce un bean a partire dal record attuale del ResultSet
   * passato come parametro.
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  private CorsoStudi makeCorsoStudiBean( ResultSet rs ) throws SQLException {
    CorsoStudi bean = new CorsoStudi();
    bean.setNome( rs.getString( "Nome" ) );
    bean.setCategoria( rs.getString( "Categoria" ) );
    bean.setProgramma( rs.getString( "Programma" ) );
    bean.setNMaxStud( rs.getInt( "NMaxStud" ) );
    bean.setNOreLez( rs.getInt( "NOreLez" ) );

    return bean;
  }

  /**
   * Il metodo costruisce un bean a partire dal record attuale del ResultSet
   * passato come parametro.
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  private Erogazione makeErogazioneBean( ResultSet rs ) throws SQLException {
    Erogazione bean = new Erogazione();
    bean.setCodice( rs.getString( "Codice" ) );
    bean.setDataInizio(0, rs.getInt( "GiornoInizio" ) );
    bean.setDataInizio(1, rs.getInt( "meseInizio" ) );
    bean.setDataInizio(2, rs.getInt( "annoInizio" ) );
    bean.setDataFine(0, rs.getInt( "giornoFine" ) );
    bean.setDataFine(1, rs.getInt( "meseFine" ) );
    bean.setDataFine(2, rs.getInt( "annoFine" ) );
    bean.setStato( rs.getString( "stato" ) );
    if(rs.getObject( "postiDisp" ) instanceof Integer){
      bean.setPosti( (Integer) rs.getObject( "postiDisp" ) );
    }

    return bean;
  }

  /**
   * Il metodo costruisce un bean a partire dal record attuale del ResultSet
   * passato come parametro.
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  private Evento makeEvtBean( ResultSet rs ) throws SQLException {
    Evento bean = new Evento();
    bean.setId( rs.getString( "ID" ) );
    bean.setCodiceErogazione( rs.getString( "CodiceErogazione" ) );
    bean.setTipo( rs.getString( "Tipo" ) );
    bean.setData(0, rs.getInt( "giorno" ) );
    bean.setData(1, rs.getInt( "mese" ) );
    bean.setData(2, rs.getInt( "anno" ) );
    bean.setOra(0, rs.getInt( "ora" ) );
    bean.setOra(1, rs.getInt( "minuti" ) );
    bean.setDocente( rs.getString( "CFDoc" ) );

    return bean;
  }

  /**
   * Il metodo costruisce un bean a partire dal record attuale del ResultSet
   * passato come parametro.
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  private Docente makeDocBean( ResultSet rs ) throws SQLException {
    Docente bean = new Docente();
    bean.setCf( rs.getString( "CF" ) );
    bean.setNome( rs.getString( "Nome" ) );
    bean.setCognome( rs.getString( "Cognome" ) );
    bean.setEmail( rs.getString( "Email" ) );
    bean.setTelefono( rs.getString( "Telefono" ) );

    return bean;
  }

  /**
   * Il metodo costruisce un bean a partire dal record attuale del ResultSet
   * passato come parametro.
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  private Studente makeStudenteBean( ResultSet rs ) throws SQLException {
    Studente bean = new Studente();
    bean.setCodice( rs.getString( "Codice" ) );
    bean.setNome( rs.getString( "Nome" ) );
    bean.setCognome( rs.getString( "Cognome" ) );
    bean.setCittaRes( rs.getString( "CittaRes" ) );
    bean.setEmail( rs.getString( "Email" ) );
    return bean;
  }

  // ===========================================================================

  /**
   * Metodo per il recupero di tutti i corsi con almeno un erogazione che non è nello stato chiuso
   * il risultato può essere filtrato secondo alcuni valori
   *
   * @param categoriacerca
   * @param programmacerca
   * @param sottostringanelnome
   * @param maxStud
   * @param oreLezTot
   *
   * @return result
   */
  public List<CorsoStudi> getCorsiStudiAttivi(String categoriacerca, String programmacerca, String sottostringanelnome, int maxStud, int oreLezTot) {

    // dichiarazione delle variabili
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    List<CorsoStudi> result = new ArrayList<CorsoStudi>();

    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      stmt = con.createStatement();
      // eseguo l'interrogazione desiderata

      String query = cca;

			if(categoriacerca != null && categoriacerca !=""){ 
				query=query+" AND corso.categoria ILIKE '"+categoriacerca+"'";
			}
			if(programmacerca != null && programmacerca !=""){ 
				query=query+" AND corso.programma ILIKE '"+programmacerca+"'";
			}
			if(sottostringanelnome != null && sottostringanelnome !=""){ 
				query=query+" AND corso.nome ILIKE '%"+sottostringanelnome+"%'";
			}
			if(maxStud >= 0){ 
				query=query+" AND corso.NMaxStud ="+maxStud+"";
			}
      if(oreLezTot >= 0){ 
        query=query+" AND corso.NOreLez ="+oreLezTot+"";
      }
			query=query+" GROUP BY corso.nome ";



      rs = stmt.executeQuery( query );
      

			
      // memorizzo il risultato dell'interrogazione nel Vector
      while( rs.next() ) {
        
        
        result.add( makeCorsoStudiBean( rs ) );

      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  /**
   * Metodo per il recupero delle informazioni del corso di studi con il nome
   * specificato.
   *
   * @param nome
   * @return result
   */
  public CorsoStudi getCorsoStudi( String nome ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    CorsoStudi result = null;
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( cs );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, nome );
      // eseguo la query
      rs = pstmt.executeQuery();
      // memorizzo il risultato dell'interrogazione in Vector di Bean
      if( rs.next() ) {
        result = makeCorsoStudiBean( rs );
      }

    } catch( SQLException sqle ) { // Catturo le eventuali eccezioni
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    
    return result;
  }


public List<Erogazione> getListaErogazioni( String nome ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Erogazione> result = new ArrayList<Erogazione>();
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( cle );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, nome );
      // eseguo la query
      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      while( rs.next() ) {
        result.add( makeErogazioneBean( rs ) );
      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }


  public Erogazione getErogazione( String codice ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Erogazione result = null;
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( ce );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, codice );
      // eseguo la query
      rs = pstmt.executeQuery();
      // memorizzo il risultato dell'interrogazione in Vector di Bean
      if( rs.next() ) {
        result = makeErogazioneBean( rs );
      }

    } catch( SQLException sqle ) { // Catturo le eventuali eccezioni
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public List<Docente> getListaDocente( String codice ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Docente> result = new ArrayList<Docente>();
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( cld );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, codice );
      // eseguo la query
      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      while( rs.next() ) {
        result.add( makeDocBean( rs ) );
      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public Docente getDocente( String id ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Docente result = null;
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( cde );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, id );
      // eseguo la query
      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      while( rs.next() ) {
        result = makeDocBean( rs );
      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public List<Evento> getListaEvento(String nome, int anno, String stato) {

    // dichiarazione delle variabili
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Evento> result = new ArrayList<Evento>();


    String query = clevt;

    if(anno >= 0){ 
       query=query+" AND  date_part('Year', Evento.data) = "+anno+"";
    }
    if(stato != null && stato !=""){ 
       query=query+" AND Evento.Stato ILIKE '"+stato+"'";
    }
    
    query=query+" GROUP BY Evento.ID ORDER BY Evento.data, Evento.ora ";

    

    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );

      

      pstmt = con.prepareStatement( query );

     // rs = stmt.executeQuery( query );
      pstmt.clearParameters();

      // imposto i parametri della query
      pstmt.setString( 1, nome );
      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione nel Vector
      while( rs.next() ) {       
        
        result.add( makeEvtBean( rs ) );

      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public Evento getEvento( String id ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Evento result = null;
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( cevt );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, id );
      // eseguo la query
      rs = pstmt.executeQuery();
      // memorizzo il risultato dell'interrogazione in Vector di Bean
      if( rs.next() ) {
        result = makeEvtBean( rs );
      }

    } catch( SQLException sqle ) { // Catturo le eventuali eccezioni
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public String getNeCDoc( String cf ){
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = null;
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( ncd );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, cf );
      // eseguo la query

      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      if( rs.next() ) {

        result = ""+rs.getString("Nome")+" "+rs.getString("Cognome");
      }

    } catch( SQLException sqle ) { // Catturo le eventuali eccezioni
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public boolean doLogin(String us, String pass){

  	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean out=false;
    String hashdb="";
    try {

      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( psw );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, us );
      // eseguo la query

      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      if( rs.next() ) {
        hashdb =rs.getString(1);
      }
    } catch( SQLException sqle ) { // Catturo le eventuali eccezioni
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }

    try{
    	out=PasswordHash.validatePassword(pass,hashdb);	
    }catch(Exception e){}

    return out;

  }

  public List<Studente> getListaStudenti( String codice ) {
    // Dichiarazione delle variabili necessarie
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Studente> result = new ArrayList<Studente>();
    try {
      // tentativo di connessione al database
      con = DriverManager.getConnection( url, user, passwd );
      // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
      pstmt = con.prepareStatement( stud );
      pstmt.clearParameters();
      // imposto i parametri della query
      pstmt.setString( 1, codice );
      // eseguo la query
      rs = pstmt.executeQuery();

      // memorizzo il risultato dell'interrogazione in Vector di Bean
      while( rs.next() ) {
        result.add( makeStudenteBean( rs ) );
      }

    } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
      sqle.printStackTrace();

    } finally { // alla fine chiudo la connessione.
      try {
        con.close();
      } catch( SQLException sqle1 ) {
        sqle1.printStackTrace();
      }
    }
    return result;
  }

  public boolean alreadyIsc(String er, String stud){
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      boolean result = false;
      try {
        // tentativo di connessione al database
        con = DriverManager.getConnection( url, user, passwd );
        // connessione riuscita, ottengo l'oggetto per l'esecuzione dell'interrogazione.
        pstmt = con.prepareStatement( aExist );
        pstmt.clearParameters();
        // imposto i parametri della query
        pstmt.setString( 1, er );
        pstmt.setString( 2, stud );
        // eseguo la query
        rs = pstmt.executeQuery();

        // memorizzo il risultato dell'interrogazione in Vector di Bean
        if( rs.next() ) {
          
          result=true;
        }

      } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
        sqle.printStackTrace();

      } finally { // alla fine chiudo la connessione.
        try {
          con.close();
        } catch( SQLException sqle1 ) {
          sqle1.printStackTrace();
        }
      }
      return result;
  }

  public boolean doIscrizione(String er, String st){
      if(alreadyIsc(er,st)){
        
        return false;
      }
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      boolean result = false;
      try {
        // tentativo di connessione al database
        con = DriverManager.getConnection( url, user, passwd );
        pstmt = con.prepareStatement(iscrivi);
        pstmt.clearParameters();

        // imposto i parametri della query
        pstmt.setString(1, st);
       
        pstmt.setString(2, er);

        Calendar cal = Calendar.getInstance();
        java.sql.Date sql = new java.sql.Date(cal.getTimeInMillis());
        java.sql.Time sql1 = new java.sql.Time(cal.getTimeInMillis());
        pstmt.setTime(3, sql1);
        pstmt.setDate(4, sql);
        
        int i = pstmt.executeUpdate();
        
        result=true;
      } catch( SQLException sqle ) { // catturo le eventuali eccezioni!
        
        sqle.printStackTrace();

      } finally { // alla fine chiudo la connessione.
        try {
          con.close();
        } catch( SQLException sqle1 ) {
          sqle1.printStackTrace();
        }
      }
      return result;

  }

}