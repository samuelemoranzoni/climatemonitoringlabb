import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteService extends Remote {

    public Integer get_id_centro(String nome) throws RemoteException;
    // funziona con zero : prende tutte le area registrate
    public List<String> getCentriRegistrati(int id) throws RemoteException;
    public int get_id_denominazione_area(String denominazione_ufficiale_area) throws RemoteException;
    public List<String> getTutteAreeInteresse (int id) throws RemoteException;
    public int insertAreeControllate(int centroId, int areaId) throws RemoteException;
    public List<String> getareeosservatedalcentro(int centromonitoraggioid)throws  RemoteException;
    String ottieniNomeCentro(Integer id) throws RemoteException;

    List<AreaGeografica> cercaAreaGeograficaPerDenominazioneeStato(String nomeArea,String nomeStato) throws RemoteException;

 //   List<AreaGeografica> cercaAreaGeograficaPerStato(String stato) throws RemoteException;

    List<AreaGeografica> cercaPerCoordinate(double latitudine, double longitudine) throws RemoteException;

  ParametriClimatici visualizzaDatiClimatici(String area) throws RemoteException;

    OperatoreRegistrato createOperatoreRegistrato(String nome, String cognome, String codiceFiscale, String mail, String user, String password, Integer id_monitoraggio) throws RemoteException;

    OperatoreRegistrato loginOperatore(String mailUser, String password) throws RemoteException;

    int createCentroMonitoraggio(String nome, String indirizzo, String CAP, String numero_civico, String provincia, String stato, int operatoreid) throws RemoteException;

    int insertParametriClimatici(int idMonitoraggio, String denominazioneArea, int idOperatore, String dataRilevazione, float velocitàVento, int scoreVento, String notaVento,
                                 float umidita, int scoreUmidita, String notaUmidita, float pressione, int scorePressione, String notaPressione, float temperatura, int scoreTemperatura,
                                 String notaTemperatura, float precipitazioni, int scorePrecipitazioni, String notaPrecipitazioni, float altitudineGhiacciai, int scoreAltitudineGhiacciai,
                                 String notaAltitudineGhiacciai, float MassaGhiacciai, int scoreMassaGhiacciai, String notaMassaGhiacciai) throws RemoteException;

    int insertAreeInteresse(float latitudine, float longitudine, String denominazioneArea, String stato,int centro_monitoraggio_id) throws RemoteException;
}

