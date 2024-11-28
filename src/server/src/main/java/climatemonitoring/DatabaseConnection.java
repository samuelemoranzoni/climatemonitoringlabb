package climatemonitoring;


import climatemonitoring.extensions.ConnessioneNonAttivaException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe per la gestione della connessione e interazione con il database di ClimateMonitoring.
 *@author Moranzoni Samuele
 *@author Di Tullio Edoardo
 *
 */
public class DatabaseConnection {

    private static Connection connection = null;

     private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private  static final String USER = "postgres";
    private  static final String PASSWORD = "labb18";

     private static final String HOST=null;
    private static OperatoreRegistrato op;

    /**
     * Metodo per richiedere l'accesso al database tramite credenziali fornite dall'utente.
     * Chiede host e password, verifica la connessione e restituisce true se l'accesso ha successo.
     *
     * @return true se l'accesso al database è riuscito, false altrimenti.
     */
    public boolean checkcredentials(String host,String password) {
        String dbHost=host;
        String dbPassword=password;
        if(dbHost.equals("localhost:5432") && dbPassword.equals(PASSWORD)){
                return true;
        } else {
            return false;
        }

    }

    public Connection connetti() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database stabilita.");
            } catch (SQLException e) {
                System.err.println("Errore di connessione: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Driver non trovato: " + e.getMessage());
            }
        }
        return connection;
    }
    /**
     * Stabilisce una connessione con il database.
     *
     * @return una connessione al database o null se si è verificato un errore.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Accesso al database ...");
        } catch (SQLException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Driver non trovato: " + e.getMessage());
        }
        return conn;

    }

    /**
     * Chiude la connessione al database se è aperta.
     */
    public  void disconnetti() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione al database chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            } finally {
                connection = null; // Resetta la connessione
            }
        }
    }

    /**
     * Crea un nuovo operatore registrato.
     *
     * @param nome                 il nome dell'operatore.
     * @param cognome              il cognome dell'operatore.
     * @param codiceFiscale        il codice fiscale dell'operatore.
     * @param email                l'email dell'operatore.
     * @param userid               l'username dell'operatore.
     * @param password             la password dell'operatore.
     * @param centroMonitoraggio_id l'id del centro di monitoraggio associato.
     * @return l'oggetto {@link OperatoreRegistrato} creato, o un oggetto Operatore con id negativo in caso di errore.
     */
    public synchronized OperatoreRegistrato createOperatoreRegistrato(String nome, String cognome, String codiceFiscale,
                                                                      String email, String userid, String password, Integer centroMonitoraggio_id) {
        // controlla che non esistano gia registrato questi valori , sono campi unique
        if (recordExists(codiceFiscale, userid)) {
            System.err.println("Errore: Il codice fiscale o l'userid esistono già.");
            return new OperatoreRegistrato(-2); // codice errore per codice fiscale o userid già esistenti: vincolo unique
        }

        // se il centro di monitoraggio è fornito, controllare se esiste
        if (centroMonitoraggio_id != null && centroMonitoraggio_id > 0 && !centroMonitoraggioExists(centroMonitoraggio_id)) {
            System.err.println("Errore: Il centro di monitoraggio con ID " + centroMonitoraggio_id + " non esiste.");
            return new OperatoreRegistrato(-3); // codice errore per centro di monitoraggio non esistente
        }

        // Controllo che l'email contenga il simbolo @
        if (email == null || !email.contains("@")) {
            System.err.println("Errore: Email non valida, deve contenere il simbolo '@'.");
            return new OperatoreRegistrato(-4); // Codice errore per email non valida
        }

        // Controllo che il codice fiscale abbia esattamente 16 caratteri
        if (codiceFiscale == null || codiceFiscale.length() != 16) {
            System.err.println("Errore: Il codice fiscale deve essere esattamente di 16 caratteri.");
            return new OperatoreRegistrato(-5); // Codice errore per codice fiscale non valido
        }

        String sql = "INSERT INTO OperatoriRegistrati (nome, cognome, codice_fiscale, email, userid, password, centro_monitoraggio_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.setString(3, codiceFiscale);
            pstmt.setString(4, email);
            pstmt.setString(5, userid);
            pstmt.setString(6, password);

            if (centroMonitoraggio_id != null && centroMonitoraggio_id > 0) {
                pstmt.setInt(7, centroMonitoraggio_id);
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int operatoreId = generatedKeys.getInt(1);
                        System.out.println("Operatore registrato con successo! ID: " + operatoreId);
                        op = new OperatoreRegistrato(operatoreId, centroMonitoraggio_id, userid);
                        return op;

                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore durante la registrazione dell'operatore: " + e.getMessage());
            e.printStackTrace();
            return new OperatoreRegistrato(-1);

        }

        return new OperatoreRegistrato(-1);
    }

    /**
     * Ottiene l'ID del centro di monitoraggio dato il nome.
     *
     * @param nome il nome del centro di monitoraggio.
     * @return l'ID del centro di monitoraggio oppure null se non esistente.
     */
    public Integer get_id_centro(String nome) {
        if (nome == null) {
            return null;
        }
        String sql = "select id from centrimonitoraggio where nome ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.err.println("Errore nel reperire l'id del centro");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Ottiene il nome di un centro data l'ID.
     *
     * @param id l'ID del centro di monitoraggio.
     * @return il nome del centro oppure null se non esistente.
     */
    public String ottieniNomeCentro(Integer id) {
        if (id != null) {
            String sql = "SELECT * FROM CentriMonitoraggio WHERE id = ?";
            try (
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("nome");
                } else {
                    System.err.println("ID di un centro: " + id + " non esistente  ");
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Verifica se un operatore esiste nel database. Metodo ridondante ma funge da ulteriore controllo di sicurezza.
     *
     * @param operatore_id l'ID dell'operatore.
     * @return true se l'operatore esiste, false altrimenti.
     */
    private boolean operatoreExists(int operatore_id) {
        String sql = "SELECT COUNT(*) FROM OperatoriRegistrati WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, operatore_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica se un centro di monitoraggio esiste. Metodo ridondante ma funge da ulteriore controllo di sicurezza.
     *
     * @param centroMonitoraggio_id l'ID del centro di monitoraggio.
     * @return true se il centro esiste, false altrimenti.
     */
    private boolean centroMonitoraggioExists(int centroMonitoraggio_id) {
        String sql = "SELECT COUNT(*) FROM CentriMonitoraggio WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, centroMonitoraggio_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Controlla se un record con il codice fiscale o userid esiste.
     *
     * @param codiceFiscale il codice fiscale da verificare.
     * @param userid        l'userid da verificare.
     * @return true se esiste, false altrimenti.
     */
    private boolean recordExists(String codiceFiscale, String userid) {
        String sql = "SELECT COUNT(*) FROM OperatoriRegistrati WHERE codice_fiscale ILIKE ? OR userid ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, codiceFiscale);
            pstmt.setString(2, userid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Permette il login di un operatore, restituendo l'istanza di climatemonitoring.OperatoreRegistrato.
     *
     * @param userid   l'userid dell'operatore.
     * @param password la password dell'operatore.
     * @return l'oggetto climatemonitoring.OperatoreRegistrato in caso di successo,
     *         un oggetto con id negativo in caso di errore.
     */
    public synchronized OperatoreRegistrato loginOperatore(String userid, String password) {
        String sql = "SELECT id, centro_monitoraggio_id FROM OperatoriRegistrati WHERE userid = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    Integer centro_monitoraggio_id = (Integer) rs.getObject("centro_monitoraggio_id");

                    System.out.println("Login effettuato con successo per l'operatore: " + userid + " con ID: " + id +
                            ", Centro Monitoraggio ID: " + (centro_monitoraggio_id != null ? centro_monitoraggio_id : "non assegnato ( di tipo null )"));

                    return new OperatoreRegistrato(id, centro_monitoraggio_id, userid);
                } else {
                    System.err.println("Login fallito: userid o password errati.");
                    return new OperatoreRegistrato(-1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore nel tentativo di connessione per effettuare il login: " + e.getMessage());
            e.printStackTrace();
            return new OperatoreRegistrato(-2);
        }
    }

    /**
     * Crea un centro di monitoraggio e restituisce il suo ID.
     *
     * @param nome                 il nome del centro.
     * @param indirizzo            l'indirizzo del centro.
     * @param cap                  il CAP del centro.
     * @param numero_civico        il numero civico del centro.
     * @param provincia            la provincia del centro.
     * @param stato                lo stato del centro.
     * @param operatoreid          l'ID dell'operatore associato.
     * @return l'ID del centro di monitoraggio, o valori negativi in caso di errore.
     */
    public synchronized int createCentroMonitoraggio(String nome, String indirizzo, String cap, String numero_civico, String provincia, String stato, int operatoreid) {
        String sql = "INSERT INTO CentriMonitoraggio (nome, indirizzo , cap , numero_civico , provincia , stato ) VALUES (?, ?, ? , ? , ? , ?)";

        if (nome == null || indirizzo == null || cap == null || numero_civico == null || provincia == null || stato == null) {
            System.err.println("I campi non possono essere vuoti");
            return -1; // errore codice 1, l'utente ha inserito valori nulli per campi di tipo not null
        }
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, indirizzo);
            pstmt.setString(3, cap);
            pstmt.setString(4, numero_civico);
            pstmt.setString(5, provincia);
            pstmt.setString(6, stato);

            pstmt.executeUpdate();
            System.out.println("Centro di monitoraggio creato con successo!");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int centroId = generatedKeys.getInt(1);
                    System.out.println("ID del centro di monitoraggio generato: " + centroId + " , memorizzalo, ti servirà in seguito per eventuali operazioni");
                    // idoperatore fornito dallo stesso client
                    updateOperatoreCentro(operatoreid, centroId);

                    return centroId;
                }
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL unique violation error code
                System.out.println("Errore: Un centro di monitoraggio con questo indirizzo esiste già.");
                return -2; // errore causato dal vincolo unique: indirizzo del centro inserito esiste già
            } else {
                System.out.println("Errore durante la creazione del centro di monitoraggio: " + e.getMessage());
                e.printStackTrace();
                return -3; // Errore generale
            }
        }
        return -3; // Non dovrebbe mai arrivarci, ma è incluso per completezza
    }

    /**
     * Modifica il campo centro_monitoraggio di un operatore registrato.
     *
     * @param idOperatore l'ID dell'operatore da aggiornare.
     * @param centroId    l'ID del centro di monitoraggio da assegnare.
     */
    public void updateOperatoreCentro(int idOperatore, int centroId) {
        String sql = "UPDATE OperatoriRegistrati SET centro_monitoraggio_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, centroId);
            pstmt.setInt(2, idOperatore);

            pstmt.executeUpdate();
            System.out.println("Operatore aggiornato con il centro di monitoraggio!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce i parametri climatici . NB:i parametri di tipo float non possono avere piu di 3 cifre prima della virgola
     *
     * @param centroMonitoraggio_id ID del centro di monitoraggio.
     * @param denominazione_ufficiale_area nome dell'area monitorata.
     * @param operatore_id ID dell'operatore.
     * @param dataRilevazione data della rilevazione.
     * @param velocitaVento velocità del vento.
     * @param scoreVento punteggio della velocità del vento.
     * @param notaVento nota sulla velocità del vento.
     * @param umidita umidità.
     * @param scoreUmidita punteggio dell'umidità.
     * @param notaUmidita nota sull'umidità.
     * @param pressione pressione.
     * @param scorePressione punteggio della pressione.
     * @param notaPressione nota sulla pressione.
     * @param temperatura temperatura.
     * @param scoreTemperatura punteggio della temperatura.
     * @param notaTemperatura nota sulla temperatura.
     * @param precipitazioni precipitazioni.
     * @param scorePrecipitazioni punteggio delle precipitazioni.
     * @param notaPrecipitazioni nota sulle precipitazioni.
     * @param altitudineGhiacciai altitudine dei ghiacciai.
     * @param scoreAltitudineGhiacciai punteggio dell'altitudine dei ghiacciai.
     * @param notaAltitudineGhiacciai nota sull'altitudine dei ghiacciai.
     * @param massaGhiacciai massa dei ghiacciai.
     * @param scoreMassaGhiacciai punteggio della massa dei ghiacciai.
     * @param notaMassaGhiacciai nota sulla massa dei ghiacciai.
     * @return 1 se l'inserimento è avvenuto con successo, valori negativi in caso di errore.
     */
    public synchronized int insertParametriClimatici(int centroMonitoraggio_id, String denominazione_ufficiale_area, int operatore_id, String dataRilevazione,
                                                     float velocitaVento, int scoreVento, String notaVento,
                                                     float umidita, int scoreUmidita, String notaUmidita,
                                                     float pressione, int scorePressione, String notaPressione,
                                                     float temperatura, int scoreTemperatura, String notaTemperatura,
                                                     float precipitazioni, int scorePrecipitazioni, String notaPrecipitazioni,
                                                     float altitudineGhiacciai, int scoreAltitudineGhiacciai, String notaAltitudineGhiacciai,
                                                     float massaGhiacciai, int scoreMassaGhiacciai, String notaMassaGhiacciai) {
        // per praticità, l'utente deve inserire soltanto la denominazione ufficiale dell'area che monitora, delegando la ricerca dell'id di riferimento al sistema
        int coordinate_monitoraggio_id = get_id_denominazione_area(denominazione_ufficiale_area);
        // assicuriamoci che l'utente inserisca un'area esistente in coordinate monitoraggio
        if (coordinate_monitoraggio_id == -1) {
            System.err.println("Errore: denominazione ufficiale area '" + denominazione_ufficiale_area + "' non trovata.");
            return -1;

        }
        // assicuriamoci che l'id del centro di monitoraggio esista:
        if (!centroMonitoraggioExists(centroMonitoraggio_id)) {
            System.err.println("Errore: centro monitoraggio " + centroMonitoraggio_id + " non esiste ");
            return -2;
        }
        // assicuriamoci che l'id dell'operatore esista:
        if (!operatoreExists(operatore_id)) {
            System.err.println("Errore: operatore " + operatore_id + " non esiste ");
            return -3;
        }

        // controllo che l'operatore lavori effettivamente per il centro che sta inserendo come parametri (integrità dei dati)

        if (!verificaOperatoreCentro(operatore_id, centroMonitoraggio_id)) {
            System.err.println("Errore: l'operatore " + operatore_id + " non è associato al centro di monitoraggio " + centroMonitoraggio_id);
            return -4; // Nuovo codice di errore per questa situazione: l'operatore non lavora per questo centro
        }

        // controllo che i valori di score siano compresi tra 1 e 5: vincolo check

        if (scoreAltitudineGhiacciai > 5 || scoreAltitudineGhiacciai < 1 || scoreMassaGhiacciai > 5 || scoreMassaGhiacciai < 1 || scorePrecipitazioni > 5 || scorePrecipitazioni < 1 || scorePressione > 5 || scorePressione < 1 || scoreTemperatura > 5 || scoreTemperatura < 1 || scoreUmidita > 5 || scoreUmidita < 1 || scoreVento > 5 || scoreVento < 1) {
            System.out.println("Errore: lo score relativo ai parametri deve essere compreso tra 1 e 5 ");
            return -5;
        }
        // vincolo not null su data rilevazione
        if (dataRilevazione == null) {
            System.err.println("Errore: la data di rilevazione non può essere vuota.");
            return -6;
        }

        // consistenza dati di natura ambientale
        if (velocitaVento <= 0 || pressione <= 0 || precipitazioni < 0 || massaGhiacciai <= 0) {
            System.err.println("Errore: tutti i parametri climatici devono essere valori positivi.");
            return -7;
        }

        String sql = "INSERT INTO parametriclimatici (centro_monitoraggio_id, coordinate_monitoraggio_id , operatore_id , data_rilevazione, " +
                "velocita_vento, score_vento, nota_vento, umidita, score_umidita, nota_umidita, " +
                "pressione, score_pressione, nota_pressione, temperatura, score_temperatura, nota_temperatura, " +
                "precipitazioni, score_precipitazioni, nota_precipitazioni, altitudine_ghiacciai, " +
                "score_altitudine_ghiacciai, nota_altitudine_ghiacciai, massa_ghiacciai, score_massa_ghiacciai, nota_massa_ghiacciai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, centroMonitoraggio_id);
            pstmt.setInt(2, coordinate_monitoraggio_id);  // Questo valore ora è garantito valido
            pstmt.setInt(3, operatore_id); //
            pstmt.setDate(4, Date.valueOf(dataRilevazione));

            // Impostazione dei parametri rimanenti
            pstmt.setFloat(5, velocitaVento);
            pstmt.setInt(6, scoreVento);
            pstmt.setString(7, notaVento);
            pstmt.setFloat(8, umidita);
            pstmt.setInt(9, scoreUmidita);
            pstmt.setString(10, notaUmidita);
            pstmt.setFloat(11, pressione);
            pstmt.setInt(12, scorePressione);
            pstmt.setString(13, notaPressione);
            pstmt.setFloat(14, temperatura);
            pstmt.setInt(15, scoreTemperatura);
            pstmt.setString(16, notaTemperatura);
            pstmt.setFloat(17, precipitazioni);
            pstmt.setInt(18, scorePrecipitazioni);
            pstmt.setString(19, notaPrecipitazioni);
            pstmt.setFloat(20, altitudineGhiacciai);
            pstmt.setInt(21, scoreAltitudineGhiacciai);
            pstmt.setString(22, notaAltitudineGhiacciai);
            pstmt.setFloat(23, massaGhiacciai);
            pstmt.setInt(24, scoreMassaGhiacciai);
            pstmt.setString(25, notaMassaGhiacciai);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.err.println("Parametri climatici inseriti con successo!");
                return 1;
            } else {
                System.err.println("Nessuna riga inserita.");
                return -8; // errore inserimento di origine non specifica
            }

        } catch (SQLException e) {

            System.err.println("Errore nell'inserimento di parametri: " + e.getMessage());
            e.printStackTrace();
            return -8;
        }
    }

    /**
     * Ottiene l'ID della denominazione ufficiale dell'area.
     *
     * @param denominazione_ufficiale_area nome dell'area di interesse.
     * @return l'ID dell'area o -1 se non esistente.
     */
    public int get_id_denominazione_area(String denominazione_ufficiale_area) {
        int risultato = -1;
        String sql = "SELECT id FROM areeinteresse WHERE denominazione_ufficiale = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, denominazione_ufficiale_area);
            ResultSet rs = pstmt.executeQuery();

            // Sposta il cursore sulla prima riga del ResultSet
            if (rs.next()) {
                risultato = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return risultato;
        }

        return risultato;
    }

    /**
     * Verifica che l'operatore lavori per il centro di monitoraggio specificato.Metodo ridondante ma funge da ulteriore controllo di sicurezza.
     *
     * @param operatore_id ID dell'operatore.
     * @param centroMonitoraggio_id ID del centro di monitoraggio.
     * @return true se l'operatore è associato al centro, false altrimenti.
     */
    private boolean verificaOperatoreCentro(int operatore_id, int centroMonitoraggio_id) {
        String sql = "SELECT COUNT(*) FROM operatoriregistrati WHERE id = ? AND centro_monitoraggio_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, operatore_id);
            pstmt.setInt(2, centroMonitoraggio_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la verifica dell'associazione operatore-centro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return false;
    }

    /**
     * Inserisce dati nella tabella AreeInteresse.
     *
     * @param latitudine               latitudine dell'area.
     * @param longitudine              longitudine dell'area.
     * @param denominazioneUfficiale   denominazione ufficiale dell'area.
     * @param stato                    stato dell'area.
     * @param centro_monitoraggio_id    ID del centro di monitoraggio.
     * @return l'ID dell'area di interesse, o valori negativi in caso di errore.
     */
    public synchronized int insertAreeInteresse(float latitudine, float longitudine,
                                                String denominazioneUfficiale, String stato, int centro_monitoraggio_id) {
        String sql = "INSERT INTO AreeInteresse (latitudine, longitudine, denominazione_ufficiale, stato) " +
                "VALUES (?, ?, ?, ?)";

        if (denominazioneUfficiale == null || stato == null) {
            System.err.println("I campi denominazione ufficiale e stato devono essere non nulli");
            return -2;
        }
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setFloat(1, latitudine);
            pstmt.setFloat(2, longitudine);
            pstmt.setString(3, denominazioneUfficiale);
            pstmt.setString(4, stato);

            pstmt.executeUpdate();
            System.out.println("Coordinate di monitoraggio inserite con successo!");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int areainteresseid = generatedKeys.getInt(1);
                    System.out.println("ID dell'area di interesse generato: " + areainteresseid);
                    int risposta = insertAreeControllate(centro_monitoraggio_id, areainteresseid);
                    if (risposta > 0) {
                        System.out.println("Aggiorno il db areecontrollate " + "id : " + risposta + " unione di : " + centro_monitoraggio_id + " e " + areainteresseid);
                    } else {
                        System.out.println("Problema di connessione, associazione centro e area fallita");
                    }
                    return areainteresseid;
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL unique violation error code: vincolo unique su denominazione ufficiale
                System.err.println("Errore: Coordinate di monitoraggio già esistenti per questa denominazione ufficiale ");
                return -3;
            } else {
                System.err.println("Errore nell'inserimento delle coordinate di monitoraggio: " + e.getMessage());
            }
            e.printStackTrace();
            return -1; // errore di connessione
        }
        return -1; // Indica un fallimento
    }

    /**
     * Inserisce nella tabella AreeControllate la coppia di ID centro e ID area.
     *
     * @param centroId ID del centro di monitoraggio.
     * @param areaId   ID dell'area di interesse.
     * @return 1 se l'inserimento è avvenuto con successo, o -2/-1 in caso di errore.
     */
    public synchronized int insertAreeControllate(int centroId, int areaId) {
        String sql = "INSERT INTO AreeControllate (centro_id, area_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, centroId);
            pstmt.setInt(2, areaId);

            pstmt.executeUpdate();
            System.out.println("Associazione tra centro di monitoraggio e area di interesse inserita con successo!");
            return 1;

        } catch (SQLException e) {
            // Controlla se l'errore è dovuto a una violazione del vincolo UNIQUE
            if (e.getSQLState().equals("23505")) { // SQL state 23505 è per la violazione UNIQUE
                System.err.println("Violazione del vincolo UNIQUE: " + e.getMessage());
                return -2;
            } else {
                System.err.println("Errore durante l'inserimento dei dati in AreeControllate: " + e.getMessage());
                e.printStackTrace();
                return -1;
            }
        }
    }

    /**
     * Cerca un'area geografica in base alla denominazione ufficiale e stato.
     *
     * @param denominazione_ufficiale la denominazione ufficiale dell'area.
     * @param stato                   lo stato dell'area.
     * @return un'istanza di climatemonitoring.AreaGeografica o null se non trovata.
     */
    public synchronized AreaGeografica cercaAreaGeograficaPerDenominazioneeStato(String denominazione_ufficiale, String stato) {
        AreaGeografica a = null;

        String sql = "SELECT * FROM areeinteresse WHERE denominazione_ufficiale ILIKE ? AND stato ILIKE ?";

        try {PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, denominazione_ufficiale);
            pstmt.setString(2, stato);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                a = new AreaGeografica(
                        rs.getString("denominazione_ufficiale"),
                        rs.getString("stato"),
                        rs.getDouble("latitudine"),
                        rs.getDouble("longitudine")
                );
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new AreaGeografica("-1","-1",-1,-1);
        }
        return a; // ritorna null se non trovata
    }

    /**
     * Cerca un'area geografica in base a coordinate geografiche, restituendo la più vicina(sfruttando la distanza quadratica).
     *
     * @param latitudine  la latitudine di ricerca.
     * @param longitudine la longitudine di ricerca.
     * @return un'istanza di climatemonitoring.AreaGeografica o null se non trovata.
     */
    public synchronized AreaGeografica cercaPerCoordinate(double latitudine, double longitudine) throws ConnessioneNonAttivaException {
        AreaGeografica a = null;
        String sql = "SELECT * " +
                "FROM areeinteresse " +
                "ORDER BY POWER(latitudine - ?, 2) + POWER(longitudine - ?, 2) " +
                "LIMIT 1";


        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, latitudine);
            pstmt.setDouble(2, longitudine);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                a = new AreaGeografica(
                        rs.getString("denominazione_ufficiale"),
                        rs.getString("stato"),
                        rs.getDouble("latitudine"),
                        rs.getDouble("longitudine"));

                return a;
            }

        } catch (SQLException e ) {
            e.printStackTrace();
            return new AreaGeografica("-1","-1",-1,-1);

        }
        return a; // ritorna null se non trovata
    }

    /**
     * Visualizza le informazioni climatiche di un'area di interesse.
     *
     * @param areaInteresse il nome dell'area di interesse.
     * @return i parametri climatici dell'area oppure null se non trovati.
     */
    public synchronized ParametriClimatici visualizzaDatiClimatici(String areaInteresse) {
        ParametriClimatici pc = null;

        // Verifica se l'area esiste prima di eseguire la query principale
        int id_area_ricercata = get_id_denominazione_area(areaInteresse);
        if (id_area_ricercata == -1) {
            // L'area non esiste, restituisci un oggetto null
            return pc;
        }

        String sql = "SELECT " +
                "STRING_AGG(TO_CHAR(data_rilevazione, 'YYYY-MM-DD'), '; ') AS date_rilevazioni, " +
                "AVG(velocita_vento) AS media_velocita_vento, AVG(score_vento) AS score_medio_vento , COUNT(velocita_vento) AS num_vento, " +
                "AVG(umidita) AS media_umidita, AVG(score_umidita) AS score_medio_umidita , COUNT(umidita) AS num_umidita, " +
                "AVG(pressione) AS media_pressione, AVG(score_pressione) AS score_medio_pressione , COUNT(pressione) AS num_pressione, " +
                "AVG(temperatura) AS media_temperatura, AVG(score_temperatura) AS score_medio_temperatura , COUNT(temperatura) AS num_temperatura, " +
                "AVG(precipitazioni) AS media_precipitazioni, AVG(score_precipitazioni) AS score_medio_precipitazioni , COUNT(precipitazioni) AS num_precipitazioni, " +
                "AVG(altitudine_ghiacciai) AS media_altitudine_ghiacciai, AVG(score_altitudine_ghiacciai) AS score_medio_altitudine_ghiacciai , COUNT(altitudine_ghiacciai) AS num_altitudine_ghiacciai, " +
                "AVG(massa_ghiacciai) AS media_massa_ghiacciai, AVG(score_massa_ghiacciai) AS score_medio_massa_ghiacciai , COUNT(massa_ghiacciai) AS num_massa_ghiacciai  " +
                "FROM ParametriClimatici WHERE coordinate_monitoraggio_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id_area_ricercata);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    pc = new ParametriClimatici(
                            rs.getFloat("media_velocita_vento"),
                            rs.getFloat("score_medio_vento"),
                            rs.getInt("num_vento"),
                            rs.getFloat("media_umidita"),
                            rs.getFloat("score_medio_umidita"),
                            rs.getInt("num_umidita"),
                            rs.getFloat("media_pressione"),
                            rs.getFloat("score_medio_pressione"),
                            rs.getInt("num_pressione"),
                            rs.getFloat("media_temperatura"),
                            rs.getFloat("score_medio_temperatura"),
                            rs.getInt("num_temperatura"),
                            rs.getFloat("media_precipitazioni"),
                            rs.getFloat("score_medio_precipitazioni"),
                            rs.getInt("num_precipitazioni"),
                            rs.getFloat("media_altitudine_ghiacciai"),
                            rs.getFloat("score_medio_altitudine_ghiacciai"),
                            rs.getInt("num_altitudine_ghiacciai"),
                            rs.getFloat("media_massa_ghiacciai"),
                            rs.getFloat("score_medio_massa_ghiacciai"),
                            rs.getInt("num_massa_ghiacciai")
                    );

                    return pc;
                } else {
                    System.out.println("Nessun dato trovato per l'area di interesse: " + areaInteresse);
                    return pc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pc; // ritorna null se non trovati
    }

    /**
     * Ottiene una lista di aree osservate da un centro di monitoraggio.
     *
     * @param centromonitoraggioid ID del centro di monitoraggio.
     * @return una lista di nomi delle aree osservate.
     */
    public List<String> getareeosservatedalcentro(int centromonitoraggioid) {
        String sql = "SELECT DISTINCT ai.denominazione_ufficiale " +
                "FROM areeinteresse ai " +
                "JOIN areecontrollate ac ON ai.id = ac.area_id " +
                "WHERE ac.centro_id = ?";

        List<String> lista = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, centromonitoraggioid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("denominazione_ufficiale");
                lista.add(nome);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Ottiene tutte le aree di interesse.
     *
     * @param id ID minimo da cui iniziare la ricerca. Se id = 0 restituirà tutte le aree di interesse.
     * @return una lista di nomi delle aree di interesse.
     */
    public List<String> getTutteAreeInteresse(int id) {
        String sql = "select DISTINCT denominazione_ufficiale from areeinteresse where id > ? ";
        List<String> lista = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("denominazione_ufficiale");
                lista.add(nome);
                Collections.sort(lista);
            }
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Ottiene tutti i centri registrati.
     *
     * @param id ID minimo da cui iniziare la ricerca. Se si associa 0 , restituirà tutti i centri registrati.
     * @return una lista di nomi dei centri registrati.
     */
    public List<String> getCentriRegistrati(int id) {
        String sql = "select DISTINCT nome from centrimonitoraggio where id > ? ";
        List<String> lista = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                lista.add(nome);
                Collections.sort(lista);
            }
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Ottiene una lista di note associate a un'area di interesse specificata.
     *
     * @param area il nome dell'area di interesse.
     * @return una lista di oggetti climatemonitoring.Note associati all'area specificata.
     */
    public List<Note> getNote(String area) {
        String sql = "SELECT centro_monitoraggio_id, operatore_id, data_rilevazione, nota_vento, nota_umidita, nota_pressione, nota_temperatura, nota_precipitazioni, " +
                "nota_altitudine_ghiacciai, nota_massa_ghiacciai " +
                "FROM public.parametriclimatici " +
                "WHERE coordinate_monitoraggio_id = ?";

        List<Note> listaNote = new ArrayList<>(); // Initialize the list to avoid null pointer exceptions
        int id_area = this.get_id_denominazione_area(area); // Assuming this method returns the correct id

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id_area); // Set the parameter value for the prepared statement

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
               //crea l'oggetto di tipo Nota
                Note note = new Note(
                        rs.getInt("centro_monitoraggio_id"),
                        rs.getInt("operatore_id"),
                        rs.getDate("data_rilevazione"),
                        rs.getString("nota_vento"),
                        rs.getString("nota_umidita"),
                        rs.getString("nota_pressione"),
                        rs.getString("nota_temperatura"),
                        rs.getString("nota_precipitazioni"),
                        rs.getString("nota_altitudine_ghiacciai"),
                        rs.getString("nota_massa_ghiacciai")
                );

                // aggiunge l'oggetto nota alla lista
                listaNote.add(note);
            }

            return listaNote; // ritorna la lista

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // returna null in caso di eccezione
        }
    }

    public static void main(String[] args) throws ConnessioneNonAttivaException {


    }
}
