/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * classe per la gestione dell'inserimento di centri e aree
 */
public class SistemaInserimento {
    
    /*
     * @param (via/piazza
     * @param civico
     * @param cap
     * @param comune
     * @return String
     */
    public void insCentroAree(String nomeCentro,String indirizzo, ArrayList<String> areeDiInteresse) { //metodo per l'inserimento dei centri di monitoraggio
        // Ottieni i dettagli del centro di monitoraggio
        /*
        Scanner sc1 = new Scanner(System.in);

        System.out.print("Inserisci il nome del Centro Monitoraggio: ");
        String nomeCentro = sc1.nextLine();

        System.out.print("Inserisci l'indirizzo fisico (via/piazza, numero civico, cap, comune, provincia): ");
        String indirizzo = sc1.nextLine();

        // Crea un elenco vuoto per le aree di interesse
        List<String> areeDiInteresse = new ArrayList<>();

        // Chiedi all'operatore di inserire le aree di interesse fino a quando desidera
        while (true) {
            System.out.print("Inserisci un'area di interesse (o digita 'fine' per terminare): ");
            String areaInteresse = sc1.nextLine();
            if (areaInteresse.toLowerCase().equals("fine")) {
                break;
            } else {
                areeDiInteresse.add(areaInteresse);
            }
        }

*/

        // Creazione della tupla con i dati del centro di monitoraggio
        CentroMonitoraggio centroDati = new CentroMonitoraggio(nomeCentro, indirizzo, areeDiInteresse);
        // Salvataggio dei dati nel file CentroMonitoraggio.dati

        try {
            FileWriter writer = new FileWriter("data\\CentroMonitoraggio.dati.csv", true);
            // Aggiungi i dati alla riga CSV nel formato "NomeCentro;IndirizzoFisico;AreeDiInteresse"
            writer.write(centroDati.toCsvString() + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/**
 * metodo per l'inserimento delle aree di interesse
 */
    public  void insArea(String nomeArea,String Nazione,String Coordinate){
        /*
        // Ottieni i dettagli dell'area d'interesse
        Scanner sc2= new Scanner(System.in);

        System.out.print("Inserisci il nome dell'Area d'interesse: ");
        String nomeArea = sc2.nextLine();

        System.out.print("Inserisci la Nazione dell'Area d'interesse: ");
        String Nazione= sc2.nextLine();

        System.out.println("inserisci la latitudine: ");
        String Latitudine=sc2.nextLine();

        System.out.println("inserisci la longitudine: ");
        String Longitudine=sc2.nextLine();

        String Coordinate=Latitudine.concat(", ".concat(Longitudine));

*/
      AreeInteresse areeDati= new AreeInteresse(nomeArea,Nazione,Coordinate);
        try {
            FileWriter writer = new FileWriter("data\\CoordinateMonitoraggio.dati.csv", true);
            // Aggiungi i dati alla riga CSV nel formato "NomeArea;Nazione;Coordinate;"
            writer.write(areeDati.toCsvString() + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  /**
 * Questo metodo permette all'utente di inserire vari parametri climatici.
 * L'utente inserisce i dati tramite input da console.
 * I dati vengono quindi salvati in un file CSV.
 */
  /*
public void inserisciParametriClimatici(){
    // Creazione di oggetti Scanner per la lettura dell'input
    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);

    // Inserimento del centro di monitoraggio
    System.out.println("inserisci il centro di monitoraggio: ");
    String centroMonitoraggio = sc.nextLine();
    
    // Inserimento dell'area di interesse
    System.out.println("inserisci l'area di interesse: ");
    String areaInteresse = sc.nextLine();
    
    // Inserimento della data di rilevazione
    System.out.println("inserisci la data di rilevazione dato: ");
    String dataRilevazione = sc.nextLine();
    
    // Inserimento della velocità del vento
    System.out.println("velocita del vento (km/h), suddivisa in fasce: ");
    int velocitavento = sc.nextInt();
    
    // Inserimento dello score del vento
    System.out.println("Inserisci lo score (1-5): ");
    int scorevento = sc.nextInt();
    while (scorevento < 1 || scorevento > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scorevento = sc.nextInt();
    }

    // Inserimento di una nota sul vento
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notavento = sc1.nextLine();
    while (notavento.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notavento = sc1.nextLine();
    } 
    
    // Inserimento della percentuale di umidità
    System.out.println("% di umidita: ");
    float percumidita = sc.nextFloat();
    
    // Inserimento dello score dell'umidità
    System.out.println("Inserisci lo score (1-5): ");
    int scoreumidita = sc.nextInt();
    while (scoreumidita < 1 || scoreumidita > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scoreumidita = sc.nextInt();
    }

    // Inserimento di una nota sull'umidità
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notaumidita = sc1.nextLine();
    while (notaumidita.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notaumidita = sc1.nextLine();
    } 
    
    // Inserimento della pressione in hPa
    System.out.println("Pressione in hPa: ");
    float hPa = sc.nextFloat();
    
    // Inserimento dello score della pressione
    System.out.println("Inserisci lo score (1-5): ");
    int scorepressione = sc.nextInt();
    while (scorepressione < 1 || scorepressione > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scorepressione = sc.nextInt();
    }
    
    // Inserimento di una nota sulla pressione
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notapressione = sc1.nextLine();
    while (notapressione.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notapressione = sc1.nextLine();
    } 
    
    // Inserimento della temperatura in gradi Celsius
    System.out.println("Temperatura in C°: ");
    float Ctemp = sc.nextFloat();
    
    // Inserimento dello score della temperatura
    System.out.println("Inserisci lo score (1-5): ");
    int scoretemp = sc.nextInt();
    while (scoretemp < 1 || scoretemp > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scoretemp = sc.nextInt();
    }
    
    // Inserimento di una nota sulla temperatura
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notatemp = sc1.nextLine();
    while (notatemp.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notatemp = sc1.nextLine();
    } 
    
    // Inserimento delle precipitazioni in mm di pioggia
    System.out.println("Precipitazioni in mm di pioggia: ");
    float mmprec = sc.nextFloat();
    
    // Inserimento dello score delle precipitazioni
    System.out.println("Inserisci lo score (1-5): ");
    int scoreprec = sc.nextInt();
    while (scoreprec < 1 || scoreprec > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scoreprec = sc.nextInt();
    }
    
    // Inserimento di una nota sulle precipitazioni
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notaprec = sc1.nextLine();
    while (notaprec.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notaprec = sc1.nextLine();
    } 
    
    // Inserimento dell'altitudine dei ghiacciai in metri
    System.out.println("Altitudine dei ghiacciai in m, suddivisa in piogge: ");
    float metrialtit = sc.nextFloat();
    
    // Inserimento dello score dell'altitudine dei ghiacciai
    System.out.println("Inserisci lo score (1-5): ");
    int scorealtit = sc.nextInt();
    while (scorealtit < 1 || scorealtit > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scorealtit = sc.nextInt();
    }
    
    // Inserimento di una nota sull'altitudine dei ghiacciai
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notaaltit = sc1.nextLine();
    while (notaaltit.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notaaltit = sc1.nextLine();
    } 
    
    // Inserimento della massa dei ghiacciai in Kg
    System.out.println("Massa dei ghiacciaiin Kg: ");
    float massaghiacc = sc.nextFloat();
    
    // Inserimento dello score della massa dei ghiacciai
    System.out.println("Inserisci lo score (1-5): ");
    int scoreghiacc = sc.nextInt();
    while (scoreghiacc < 1 || scoreghiacc > 5) {
        System.out.println("Errore lo score deve essere compreso tra 1 e 5. Riprova: ");
        scoreghiacc = sc.nextInt();
    }
    
    // Inserimento di una nota sulla massa dei ghiacciai
    System.out.println("Inserisci una nota (Max 256 characters): ");
    String notaghiacc = sc1.nextLine();
    while (notaghiacc.length() > 256) {
        System.out.println("Errore la nota non può superare i 256 caratteri. Riprova: ");
        notaghiacc = sc1.nextLine();
    } 
    
    // Scrittura dei dati inseriti in un file CSV
        try{
            FileWriter fileWritercsv = new FileWriter("data\\ParametriClimatici.dati.csv", true);
            fileWritercsv.append(centroMonitoraggio + ";" + areaInteresse + ";" + dataRilevazione + "\n");
            fileWritercsv.append("vento" + ";" + velocitavento + ";" + scorevento + ";" + notavento + "\n");
            fileWritercsv.append("umidità" + ";" + percumidita + ";" + scoreumidita + ";" + notaumidita + "\n");
            fileWritercsv.append("pressione" + ";" + hPa + ";" + scorepressione + ";" + notapressione + "\n");
            fileWritercsv.append("temperatura" + ";" + Ctemp + ";" + scoretemp + ";" + notatemp + "\n");
            fileWritercsv.append("precipitazioni" + ";" + mmprec + ";" + scoreprec + ";" + notaprec + "\n");
            fileWritercsv.append("attitudine dei ghiacciai" + ";" + metrialtit + ";" + scorealtit + ";" + notaaltit + "\n");
            fileWritercsv.append("massa dei ghiacciai" + ";" + massaghiacc + ";" + scoreghiacc + ";" + notaghiacc + "\n");
            fileWritercsv.close();
        }catch(Exception e){
            System.out.println(e);
        } */

        public void inserisciParametriClimatici(
                String centroMonitoraggio, String areaInteresse, String dataRilevazione,
                Integer velocitaVento, Integer scoreVento, String notaVento,
                Float umidita, Integer scoreUmidita, String notaUmidita,
                Float pressione, Integer scorePressione, String notaPressione,
                Float temperatura, Integer scoreTemperatura, String notaTemperatura,
                Float precipitazioni, Integer scorePrecipitazioni, String notaPrecipitazioni,
                Float altitudineGhiacciai, Integer scoreAltitudineGhiacciai, String notaAltitudineGhiacciai,
                Float massaGhiacciai, Integer scoreMassaGhiacciai, String notaMassaGhiacciai) {
/*
            // Gestione dei campi nulli
            centroMonitoraggio = (centroMonitoraggio != null) ? centroMonitoraggio : "Parametro non specificato nel momento della rivelazione";
            areaInteresse = (areaInteresse != null) ? areaInteresse : "Parametro non specificato nel momento della rivelazione";
            dataRilevazione = (dataRilevazione != null) ? dataRilevazione : "Parametro non specificato nel momento della rivelazione";

            velocitaVento = (velocitaVento != null) ? velocitaVento : -1; // Usando -1 per indicare valore non specificato
            scoreVento = (scoreVento != null) ? scoreVento : -1;
            notaVento = (notaVento != null) ? notaVento : "Parametro non specificato nel momento della rivelazione";

            umidita = (umidita != null) ? umidita : null; // Usando null per indicare valore non specificato
            scoreUmidita = (scoreUmidita != null) ? scoreUmidita : -1;
            notaUmidita = (notaUmidita != null) ? notaUmidita : "Parametro non specificato nel momento della rivelazione";

            pressione = (pressione != null) ? pressione : null; // Usando null per indicare valore non specificato
            scorePressione = (scorePressione != null) ? scorePressione : -1;
            notaPressione = (notaPressione != null) ? notaPressione : "Parametro non specificato nel momento della rivelazione";

            temperatura = (temperatura != null) ? temperatura : null; // Usando null per indicare valore non specificato
            scoreTemperatura = (scoreTemperatura != null) ? scoreTemperatura : -1;
            notaTemperatura = (notaTemperatura != null) ? notaTemperatura : "Parametro non specificato nel momento della rivelazione";

            precipitazioni = (precipitazioni != null) ? precipitazioni : null; // Usando null per indicare valore non specificato
            scorePrecipitazioni = (scorePrecipitazioni != null) ? scorePrecipitazioni : -1;
            notaPrecipitazioni = (notaPrecipitazioni != null) ? notaPrecipitazioni : "Parametro non specificato nel momento della rivelazione";

            altitudineGhiacciai = (altitudineGhiacciai != null) ? altitudineGhiacciai : null; // Usando null per indicare valore non specificato
            scoreAltitudineGhiacciai = (scoreAltitudineGhiacciai != null) ? scoreAltitudineGhiacciai : -1;
            notaAltitudineGhiacciai = (notaAltitudineGhiacciai != null) ? notaAltitudineGhiacciai : "Parametro non specificato nel momento della rivelazione";

            massaGhiacciai = (massaGhiacciai != null) ? massaGhiacciai : null; // Usando null per indicare valore non specificato
            scoreMassaGhiacciai = (scoreMassaGhiacciai != null) ? scoreMassaGhiacciai : -1;
            notaMassaGhiacciai = (notaMassaGhiacciai != null) ? notaMassaGhiacciai : "Parametro non specificato nel momento della rivelazione";
*/
            // Scrittura dei dati nel file CSV
            try (FileWriter fileWriter = new FileWriter("data/ParametriClimatici.dati.csv", true)) {
                fileWriter.append(centroMonitoraggio + ";" + areaInteresse + ";" + dataRilevazione + "\n");
                fileWriter.append("vento;" + (velocitaVento == -1 ? "Parametro non specificato nel momento della rivelazione" : velocitaVento) + ";" +
                        (scoreVento == -1 ? "Parametro non specificato nel momento della rivelazione" : scoreVento) + ";" + notaVento + "\n");
                fileWriter.append("umidità;" + (umidita != null ? umidita : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scoreUmidita == -1 ? "Parametro non specificato nel momento della rivelazione" : scoreUmidita) + ";" + notaUmidita + "\n");
                fileWriter.append("pressione;" + (pressione != null ? pressione : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scorePressione == -1 ? "Parametro non specificato nel momento della rivelazione" : scorePressione) + ";" + notaPressione + "\n");
                fileWriter.append("temperatura;" + (temperatura != null ? temperatura : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scoreTemperatura == -1 ? "Parametro non specificato nel momento della rivelazione" : scoreTemperatura) + ";" + notaTemperatura + "\n");
                fileWriter.append("precipitazioni;" + (precipitazioni != null ? precipitazioni : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scorePrecipitazioni == -1 ? "Parametro non specificato nel momento della rivelazione" : scorePrecipitazioni) + ";" + notaPrecipitazioni + "\n");
                fileWriter.append("altitudine dei ghiacciai;" + (altitudineGhiacciai != null ? altitudineGhiacciai : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scoreAltitudineGhiacciai == -1 ? "Parametro non specificato nel momento della rivelazione" : scoreAltitudineGhiacciai) + ";" + notaAltitudineGhiacciai + "\n");
                fileWriter.append("massa dei ghiacciai;" + (massaGhiacciai != null ? massaGhiacciai : "Parametro non specificato nel momento della rivelazione") + ";" +
                        (scoreMassaGhiacciai == -1 ? "Parametro non specificato nel momento della rivelazione" : scoreMassaGhiacciai) + ";" + notaMassaGhiacciai + "\n");
            } catch (IOException e) {
                System.out.println("Errore durante la scrittura del file: " + e.getMessage());
            }
        }
    }
    




