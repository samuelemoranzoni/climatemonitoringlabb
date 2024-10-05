import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RemoteServiceImpl extends UnicastRemoteObject implements RemoteService {

    private DatabaseConnection database;

    protected RemoteServiceImpl(DatabaseConnection db) throws RemoteException {
        super();
        this.database = db;
    }

    @Override
    public int get_id_denominazione_area(String denominazione_ufficiale_area) throws RemoteException {
        return database.get_id_denominazione_area(denominazione_ufficiale_area);
    }

    @Override
    public List<String> getTutteAreeInteresse(int id) throws RemoteException {
        return database.getTutteAreeInteresse(id);
    }

    @Override
    public int insertAreeControllate(int centroId, int areaId) throws RemoteException {
       return database.insertAreeControllate(centroId,areaId);
    }

    @Override
    public List<String> getareeosservatedalcentro(int centromonitoraggioid) throws RemoteException {
        return database.getareeosservatedalcentro(centromonitoraggioid);
    }

    @Override
    public String ottieniNomeCentro(Integer id) throws RemoteException {
        return database.ottieniNomeCentro( id);
    }

    @Override
    public List<AreaGeografica> cercaAreaGeograficaPerDenominazione(String nomeArea) throws RemoteException {
        return database.cercaAreaGeograficaPerDenominazione(nomeArea);
    }

    @Override
    public List<AreaGeografica> cercaAreaGeograficaPerStato(String stato) throws RemoteException {
        return database.cercaAreaGeograficaPerStato(stato);
    }

    @Override
    public List<AreaGeografica> cercaPerCoordinate(double latitudine, double longitudine) throws RemoteException {
        return database.cercaPerCoordinate(latitudine, longitudine);
    }

    @Override
    public List<ParametriClimatici> visualizzaDatiClimatici(String area) throws RemoteException {
        return database.visualizzaDatiClimatici(area);
    }

    @Override
    public OperatoreRegistrato createOperatoreRegistrato(String nome, String cognome, String codiceFiscale, String mail, String user, String password, Integer id_monitoraggio) throws RemoteException {
        return database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
    }

    @Override
    public OperatoreRegistrato loginOperatore(String mailUser, String password) throws RemoteException {
        return database.loginOperatore(mailUser, password);
    }



    @Override
    public int createCentroMonitoraggio(String nome, String indirizzo, String CAP, String numero_civico, String provincia, String stato, int operatoreid) throws RemoteException {
        return database.createCentroMonitoraggio(nome, indirizzo ,  CAP , numero_civico , provincia ,stato ,operatoreid);
    }

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

    @Override
    public int insertAreeInteresse(float latitudine, float longitudine, String denominazioneArea, String stato,int centro_monitoraggio_id) throws RemoteException {
        return database.insertAreeInteresse(latitudine, longitudine, denominazioneArea, stato,centro_monitoraggio_id);
    }
}

