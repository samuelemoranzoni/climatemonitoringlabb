package climatemonitoring;

import climatemonitoring.extensions.ConnessioneNonAttivaException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Implementazione del servizio remoto per il progetto di monitoraggio . Il client si avvale dell'utilizzo di un oggetto
 * remoto per operare (in lettura e scrittura) sul database a seconda dei dati che l' utente inserirà  tramite GUI.
 * Utilizza una connessione a un database per recuperare e gestire i dati.
 *@author Moranzoni Samuele
* @author Di Tullio Edoardo
 */
public class RemoteServiceImpl extends UnicastRemoteObject implements RemoteService {

    private DatabaseConnection database;

    /**
     * Costruttore della classe climatemonitoring.RemoteServiceImpl.
     *
     * @param db Il collegamento al database da utilizzare.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    protected RemoteServiceImpl(DatabaseConnection db) throws RemoteException {
        super();
        this.database = db;
    }

    /**
     * Recupera una lista di note associate a un'area specificata.
     *
     * @param area L'area per cui si desidera ottenere le note.
     * @return Una lista di note associate all'area specificata.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public List<Note> getNote(String area) throws RemoteException {
        return database.getNote(area);
    }

    /**
     * Ottiene l'ID di un centro in base al nome fornito.
     *
     * @param nome Il nome del centro di cui si desidera ottenere l'ID.
     * @return L'ID del centro corrispondente.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public Integer get_id_centro(String nome) throws RemoteException {
        return database.get_id_centro(nome);
    }

    /**
     * Recupera una lista di centri registrati per un dato ID.
     *
     * @param id L'ID del centro di monitoraggio.
     * @return Una lista di centri registrati.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public List<String> getCentriRegistrati(int id) throws RemoteException {
        return database.getCentriRegistrati(0);
    }

    /**
     * Ottiene l'ID della denominazione di un'area.
     *
     * @param denominazione_ufficiale_area La denominazione ufficiale dell'area.
     * @return L'ID corrispondente all'area.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public int get_id_denominazione_area(String denominazione_ufficiale_area) throws RemoteException {
        return database.get_id_denominazione_area(denominazione_ufficiale_area);
    }

    /**
     * Recupera tutte le aree di interesse per un dato ID.
     *
     * @param id L'ID dell'operatore.
     * @return Una lista di tutte le aree di interesse.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public List<String> getTutteAreeInteresse(int id) throws RemoteException {
        return database.getTutteAreeInteresse(id);
    }

    /**
     * Inserisce un'area controllata per un centro specifico.
     *
     * @param centroId L'ID del centro di monitoraggio.
     * @param areaId L'ID dell'area da controllare.
     * @return Un valore indicante il risultato dell'inserimento.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public int insertAreeControllate(int centroId, int areaId) throws RemoteException {
        return database.insertAreeControllate(centroId, areaId);
    }

    /**
     * Recupera le aree osservate da un centro di monitoraggio specificato.
     *
     * @param centromonitoraggioid L'ID del centro di monitoraggio.
     * @return Una lista delle aree osservate.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public List<String> getareeosservatedalcentro(int centromonitoraggioid) throws RemoteException {
        return database.getareeosservatedalcentro(centromonitoraggioid);
    }

    /**
     * Ottiene il nome di un centro in base al suo ID.
     *
     * @param id L'ID del centro.
     * @return Il nome del centro corrispondente.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public String ottieniNomeCentro(Integer id) throws RemoteException {
        return database.ottieniNomeCentro(id);
    }

    /**
     * Cerca un'area geografica per denominazione e stato.
     *
     * @param nomeArea Il nome dell'area da cercare.
     * @param nomeStato Il nome dello stato in cui cercare.
     * @return L'area geografica corrispondente se trovata, altrimenti null.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public AreaGeografica cercaAreaGeograficaPerDenominazioneeStato(String nomeArea, String nomeStato) throws RemoteException {
        return database.cercaAreaGeograficaPerDenominazioneeStato(nomeArea, nomeStato);
    }

    /**
     * Cerca un'area geografica in base alle coordinate fornite.
     *
     * @param latitudine La latitudine dell'area da cercare.
     * @param longitudine La longitudine dell'area da cercare.
     * @return L'area geografica corrispondente se trovata, altrimenti null.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public AreaGeografica cercaPerCoordinate(double latitudine, double longitudine) throws RemoteException, ConnessioneNonAttivaException {
        return database.cercaPerCoordinate(latitudine, longitudine);
    }

    /**
     * Visualizza i dati climatici di un'area specificata.
     *
     * @param area L'area per cui si desidera visualizzare i dati climatici.
     * @return I parametri climatici dell'area richiesta.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public ParametriClimatici visualizzaDatiClimatici(String area) throws RemoteException {
        return database.visualizzaDatiClimatici(area);
    }

    /**
     * Crea un operatore registrato con le informazioni specificate.
     *
     * @param nome Il nome dell'operatore.
     * @param cognome Il cognome dell'operatore.
     * @param codiceFiscale Il codice fiscale dell'operatore.
     * @param mail L'indirizzo email dell'operatore.
     * @param user Il nome utente dell'operatore.
     * @param password La password dell'operatore.
     * @param id_monitoraggio L'ID di monitoraggio associato.
     * @return L'operatore registrato creato.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public OperatoreRegistrato createOperatoreRegistrato(String nome, String cognome, String codiceFiscale, String mail, String user, String password, Integer id_monitoraggio) throws RemoteException {
        return database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
    }

    /**
     * Effettua il login di un operatore utilizzando le credenziali fornite.
     *
     * @param mailUser L'indirizzo email o il nome utente dell'operatore.
     * @param password La password dell'operatore.
     * @return L'operatore registrato se il login ha avuto successo, altrimenti null.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public OperatoreRegistrato loginOperatore(String mailUser, String password) throws RemoteException {
        return database.loginOperatore(mailUser, password);
    }

    /**
     * Crea un nuovo centro di monitoraggio con le informazioni specificate.
     *
     * @param nome Il nome del centro di monitoraggio.
     * @param indirizzo L'indirizzo del centro di monitoraggio.
     * @param CAP Il codice di avviamento postale del centro.
     * @param numero_civico Il numero civico del centro.
     * @param provincia La provincia in cui si trova il centro.
     * @param stato Lo stato in cui si trova il centro.
     * @param operatoreid L'ID dell'operatore associato al centro.
     * @return L'ID del nuovo centro di monitoraggio creato.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public int createCentroMonitoraggio(String nome, String indirizzo, String CAP, String numero_civico, String provincia, String stato, int operatoreid) throws RemoteException {
        return database.createCentroMonitoraggio(nome, indirizzo, CAP, numero_civico, provincia, stato, operatoreid);
    }

    /**
     * Inserisce i parametri climatici registrati per un centro di monitoraggio specificato.
     *
     * @param idCentroMonitoraggio L'ID del centro di monitoraggio.
     * @param denominazioneArea La denominazione dell'area associata.
     * @param idOperatore L'ID dell'operatore che registra i dati.
     * @param dataRilevazione La data di rilevamento dei dati.
     * @param velocitàVento La velocità del vento registrata.
     * @param scoreVento Il punteggio del vento.
     * @param notaVento climatemonitoring.Note aggiuntive sul vento.
     * @param umidita L'umidità registrata.
     * @param scoreUmidita Il punteggio dell'umidità.
     * @param notaUmidita climatemonitoring.Note aggiuntive sull'umidità.
     * @param pressione La pressione atmosferica registrata.
     * @param scorePressione Il punteggio della pressione.
     * @param notaPressione climatemonitoring.Note aggiuntive sulla pressione.
     * @param temperatura La temperatura registrata.
     * @param scoreTemperatura Il punteggio della temperatura.
     * @param notaTemperatura climatemonitoring.Note aggiuntive sulla temperatura.
     * @param precipitazioni Le precipitazioni registrate.
     * @param scorePrecipitazioni Il punteggio delle precipitazioni.
     * @param notaPrecipitazioni climatemonitoring.Note aggiuntive sulle precipitazioni.
     * @param altitudineGhiacciai L'altitudine dei ghiacciai.
     * @param scoreAltitudineGhiacciai Il punteggio dell'altitudine dei ghiacciai.
     * @param notaAltitudineGhiacciai climatemonitoring.Note aggiuntive sull'altitudine dei ghiacciai.
     * @param MassaGhiacciai La massa dei ghiacciai.
     * @param scoreMassaGhiacciai Il punteggio della massa dei ghiacciai.
     * @param notaMassaGhiacciai climatemonitoring.Note aggiuntive sulla massa dei ghiacciai.
     * @return Un valore indicante il risultato dell'inserimento.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public int insertParametriClimatici(int idCentroMonitoraggio, String denominazioneArea, int idOperatore, String dataRilevazione, float velocitàVento, int scoreVento, String notaVento,
                                        float umidita, int scoreUmidita, String notaUmidita, float pressione, int scorePressione, String notaPressione, float temperatura, int scoreTemperatura,
                                        String notaTemperatura, float precipitazioni, int scorePrecipitazioni, String notaPrecipitazioni, float altitudineGhiacciai, int scoreAltitudineGhiacciai,
                                        String notaAltitudineGhiacciai, float MassaGhiacciai, int scoreMassaGhiacciai, String notaMassaGhiacciai) throws RemoteException {
        return database.insertParametriClimatici(idCentroMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita,
                pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni,
                notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai,
                notaMassaGhiacciai);
    }

    /**
     * Inserisce un'area di interesse con le coordinate e le informazioni specificate.
     *
     * @param latitudine La latitudine dell'area di interesse.
     * @param longitudine La longitudine dell'area di interesse.
     * @param denominazioneArea La denominazione dell'area.
     * @param stato Lo stato dell'area.
     * @param centro_monitoraggio_id L'ID del centro di monitoraggio associato.
     * @return Un valore indicante il risultato dell'inserimento.
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    @Override
    public int insertAreeInteresse(float latitudine, float longitudine, String denominazioneArea, String stato, int centro_monitoraggio_id) throws RemoteException {
        return database.insertAreeInteresse(latitudine, longitudine, denominazioneArea, stato, centro_monitoraggio_id);
    }
}
