import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Skeleton extends Thread{
    private DatabaseConnection database;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Skeleton(Socket socket, DatabaseConnection db) {
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.socket = socket;
            this.database = db;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Faield to initialize I/O channels");
        }
    }

    public void run() {
        try {
            while(true) {
                String richiesta = (String) in.readObject();
                //nel caso in cui il client vuole cercare un'area di interesse per il suo nome
                if(richiesta.equals("Cerca area per nome")) {
                    String nomeArea = (String) in.readObject();
                    List<AreaGeografica> area = database.cercaAreaGeograficaPerDenominazione(nomeArea);
                    out.writeObject(area);
                }
                //nel caso in cui il client vuole cercare un'area di interesse per il suo stato
                if(richiesta.equals("Cerca area per stato")) {
                    String stato = (String) in.readObject();
                    List<AreaGeografica> area = database.cercaAreaGeograficaPerStato(stato);
                    out.writeObject(area);
                }
                //nel caso in cui il client vuole cercare un'area di interesse per latitudine e longitudine
                if(richiesta.equals("Cerca area per latitudine e longitudine")) {
                    double latidudine = (double) in.readObject();
                    double longitudine = (double) in.readObject();
                    List<AreaGeografica> area = database.cercaPerCoordinate(latidudine, longitudine);
                    out.writeObject(area);
                }
                //nel caso in cui il client vuole visualizzare i para,etri di un'area di interesse per il suo nome
                if(richiesta.equals("Visualizza parametri per area")) {
                    String area = (String) in.readObject();
                    List<ParametriClimatici> parametri = database.visualizzaDatiClimatici(area);
                    out.writeObject(parametri);
                }
                //nel caso in cui il client voglia registrarsi all'applicazione
                if(richiesta.equals("Registrazione")) {
                    int risposta = -1;
                    OperatoreRegistrato op = null;
                    while(risposta == -1) {
                        String nome = (String) in.readObject();
                        String cognome = (String) in.readObject();
                        String codiceFiscale = (String) in.readObject();
                        String mail = (String) in.readObject();
                        String user = (String) in.readObject();
                        String password = (String) in.readObject();
                        Integer id_monitoraggio = (Integer) in.readObject();
                        op = database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
                        risposta = op.getId();
                        //error 3:centromonitoraggio non esiste
                        while(risposta == -3) {
                            out.writeObject(risposta);
                            id_monitoraggio = (Integer) in.readObject();
                            op = database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
                            risposta = op.getId();
                        }
                        //error 2 :cf e/o user gia esistono
                        while(risposta == -2) {
                            out.writeObject(risposta);
                            codiceFiscale = (String) in.readObject(); //sovrascritto
                            user = (String) in.readObject();//sovrascritto
                            op = database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
                            risposta = op.getId();
                        }
                        //error 4:email non valida: se ne chiede un 'altra e si controlla che non esista già (errore : -2)
                        while(risposta == -4 || risposta== -2){
                            out.writeObject(risposta);
                            mail=(String) in.readObject();
                            op=database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
                            risposta= op.getId();


                        }
                        //error 5 :cf non valido:non ha 16 caratteri
                        while(risposta == -5 || risposta == -2){
                            out.writeObject(risposta);
                            codiceFiscale=(String) in.readObject();
                            op=database.createOperatoreRegistrato(nome, cognome, codiceFiscale, mail, user, password, id_monitoraggio);
                            risposta= op.getId();
                        }

                    }
                    out.writeObject(op);
                }
                //nel caso in cui il client vuole accedere al proprio account
                if(richiesta.equals("Login")) {
                    boolean controllo = false;
                    OperatoreRegistrato op = null;
                    while(!(controllo)) {
                        String mailUser = (String) in.readObject();
                        String password = (String) in.readObject();
                        op = (OperatoreRegistrato) database.loginOperatore(mailUser, password);
                        int idOperatore = op.getId();
                        if(idOperatore>0)
                            controllo = true;
                    }
                    out.writeObject(op);
                }
                //nel caso in cui il client registrato voglia creare un centro di monitoraggio
                if(richiesta.equals("Crea centro monitoraggio")) {
                    int risposta = -1;
                    OperatoreRegistrato op = null;
                    int idOperatore;
                    //-1 : general error
                    while(risposta == -1) {
                        String nomeCentro = (String) in.readObject();
                        String indirizzo = (String) in.readObject();
                        String[] areeInteresse = (String[]) in.readObject(); //dubbio se il vettore può essere passato in questa maniera
                        op = (OperatoreRegistrato) in.readObject();
                        idOperatore = op.getId();
                        risposta = database.createCentroMonitoraggio(nomeCentro, indirizzo, areeInteresse, idOperatore);
                        risposta = op.getId();
                        //area non esiste: error -3
                        while(risposta == -3) {
                            out.writeObject(risposta);
                            op = (OperatoreRegistrato) in.readObject(); //si rimette in attesa di ricevere nuovamente l'oggetto di tipo operatoreRegistrato perchè con la chiamata al db l'id viene aggiornato
                            idOperatore = op.getId();
                            areeInteresse = (String[]) in.readObject(); //dubbio se il vettore può essere passato in questa maniera
                            risposta = database.createCentroMonitoraggio(nomeCentro, indirizzo, areeInteresse, idOperatore);
                            risposta = op.getId();
                        }
                        //operatore non esiste
                        while(risposta == -2) {
                            out.writeObject(risposta);
                            op = (OperatoreRegistrato) in.readObject();
                            idOperatore = op.getId();
                        //    indirizzo = (String) in.readObject();
                            risposta = database.createCentroMonitoraggio(nomeCentro, indirizzo, areeInteresse, idOperatore);
                            risposta = op.getId();
                        }
                    }
                    out.writeObject(op);
                }
                //nel caso in cui il client registrato vuole inserire dei parametri per un'area di interesse
                if(richiesta.equals("Inserisci valori parametri per area di interesse")) {
                    int risposta = -8;
                    //general error
                    while(risposta == -8) {
                        OperatoreRegistrato op = (OperatoreRegistrato) in.readObject();
                        int idMonitoraggio = op.getCentroMonitoraggioId();
                        int idOperatore = op.getId();
                        String denominazioneArea = (String) in.readObject();
                        String dataRilevazione = (String) in.readObject();
                        float velocitàVento = (float) in.readObject();
                        int scoreVento = (int) in.readObject();
                        String notaVento = (String) in.readObject();
                        float umidita = (float) in.readObject();
                        int scoreUmidita = (int) in.readObject();
                        String notaUmidita = (String) in.readObject();
                        float pressione = (float) in.readObject();
                        int scorePressione = (int) in.readObject();
                        String notaPressione = (String) in.readObject();
                        float temperatura = (float) in.readObject();
                        int scoreTemperatura = (int) in.readObject();
                        String notaTemperatura = (String) in.readObject();
                        float precipitazioni = (float) in.readObject();
                        int scorePrecipitazioni = (int) in.readObject();
                        String notaPrecipitazioni = (String) in.readObject();
                        float altitudineGhiacciai= (float) in.readObject();
                        int scoreAltitudineGhiacciai = (int) in.readObject();
                        String notaAltitudineGhiacciai= (String) in.readObject();
                        float MassaGhiacciai= (float) in.readObject();
                        int scoreMassaGhiacciai = (int) in.readObject();
                        String notaMassaGhiacciai= (String) in.readObject();
                        risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        while(risposta == -1) {
                            out.writeObject(risposta);
                            denominazioneArea = (String) in.readObject();
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        //-2,-3 error: operatoreid o centroid della rilevazione di cui si vogliono riportare le info non esistono
                        while(risposta == -2 || risposta == -3) {
                            out.writeObject(risposta);
                            op = (OperatoreRegistrato) in.readObject();
                            idMonitoraggio = op.getCentroMonitoraggioId();
                            idOperatore = op.getId();
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        //error 4 : l'oeratore non lavora per il centrospecificatp
                        while(risposta ==- 4 || risposta == -2 || risposta == -3 ){
                            out.writeObject(risposta);
                            op = (OperatoreRegistrato) in.readObject();
                            idMonitoraggio = op.getCentroMonitoraggioId();
                            idOperatore = op.getId();
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        //error 5:score non compresi tra gi interi 1 e 5
                        while(risposta==-5){
                            out.writeObject(risposta);
                            scoreAltitudineGhiacciai = (int) in.readObject() ;
                            scorePrecipitazioni=(int) in.readObject() ;
                            scoreTemperatura = (int) in.readObject() ;
                            scorePressione=(int) in.readObject() ;
                            scoreUmidita = (int) in.readObject() ;
                            scoreVento=    (int) in.readObject() ;
                            scoreMassaGhiacciai= (int) in.readObject() ;
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        //error -6:data rilevazione è nulla
                        while(risposta==-6){
                            out.writeObject(risposta);
                            dataRilevazione=(String) in.readObject();
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        //error -7: dati incosistenti
                        while(risposta==-7){
                            out.writeObject(risposta);
                            pressione=(float)in.readObject();
                            MassaGhiacciai= (float)in.readObject();
                            precipitazioni=(float)in.readObject();
                            velocitàVento=(float)in.readObject();
                            risposta = database.insertParametriClimatici(idMonitoraggio, denominazioneArea, idOperatore, dataRilevazione, velocitàVento, scoreVento, notaVento, umidita, scoreUmidita, notaUmidita, pressione, scorePressione, notaPressione, temperatura, scoreTemperatura, notaTemperatura, precipitazioni, scorePrecipitazioni, notaPrecipitazioni, altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai, MassaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);
                        }
                        out.writeObject(risposta);

                    }
                    out.writeObject(risposta);
                }

                if(richiesta.equals("inserisci coordinate di monitoraggio")) {
                    int risposta = -1;
                    //-1: general error o il campo denominazione ufficiale è già presente
                    while(risposta == -1) {
                        out.writeObject(risposta);
                        float latitudine = (float) in.readObject();
                        float longitudine = (float) in.readObject();
                        String denominazioneArea = (String) in.readObject();
                        String stato = (String) in.readObject();
                        risposta = database.insertCoordinateMonitoraggio(latitudine, longitudine, denominazioneArea, stato);
                       //error 2:denominazione area o stato sono nulli
                         while(risposta==-2){
                             out.writeObject(risposta);
                             denominazioneArea = (String) in.readObject();
                             stato = (String) in.readObject();
                             risposta = database.insertCoordinateMonitoraggio(latitudine, longitudine, denominazioneArea, stato);
                         }
                    }
                    out.writeObject(risposta);
                }
            }
        }catch(IOException | ClassNotFoundException e) {}
        finally {
            //chiusare stream e socket
            try {
                out.flush();
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.err.println("I/O stream closure failed");
            }
        }
    }
}
