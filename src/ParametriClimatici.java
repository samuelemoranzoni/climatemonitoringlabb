import java.io.Serializable;
import java.io.Serializable;

/**
 * Classe per rappresentare i parametri climatici registrati.
 * Questa classe è serializzata e contiene variabili per memorizzare i dati climatici principali.
 * @author Moranzoni Samuele
 * @author Di Tullio Edoardo
 */
public class ParametriClimatici implements Serializable {
    private static final long serialVersionUID = 1;

    private float mediaVelocitaVento;
    private float scoreMedioVento;
    private int numVento;

    private float mediaUmidita;
    private float scoreMedioUmidita;
    private int numUmidita;

    private float mediaPressione;
    private float scoreMedioPressione;
    private int numPressione;

    private float mediaTemperatura;
    private float scoreMedioTemperatura;
    private int numTemperatura;

    private float mediaPrecipitazioni;
    private float scoreMedioPrecipitazioni;
    private int numPrecipitazioni;

    private float mediaAltitudineGhiacciai;
    private float scoreMedioAltitudineGhiacciai;
    private int numAltitudineGhiacciai;

    private float mediaMassaGhiacciai;
    private float scoreMedioMassaGhiacciai;
    private int numMassaGhiacciai;

    /**
     * Costruttore della classe <code>ParametriClimatici</code> che inizializza tutti i parametri climatici.
     *
     * @param mediaVelocitaVento la media della velocità del vento
     * @param scoreMedioVento il punteggio medio del vento
     * @param numVento il numero di rilevazioni della velocità del vento
     * @param mediaUmidita la media dell'umidità
     * @param scoreMedioUmidita il punteggio medio dell'umidità
     * @param numUmidita il numero di rilevazioni dell'umidità
     * @param mediaPressione la media della pressione atmosferica
     * @param scoreMedioPressione il punteggio medio della pressione
     * @param numPressione il numero di rilevazioni della pressione
     * @param mediaTemperatura la media della temperatura
     * @param scoreMedioTemperatura il punteggio medio della temperatura
     * @param numTemperatura il numero di rilevazioni della temperatura
     * @param mediaPrecipitazioni la media delle precipitazioni
     * @param scoreMedioPrecipitazioni il punteggio medio delle precipitazioni
     * @param numPrecipitazioni il numero di rilevazioni delle precipitazioni
     * @param mediaAltitudineGhiacciai la media dell'altitudine dei ghiacciai
     * @param scoreMedioAltitudineGhiacciai il punteggio medio dell'altitudine dei ghiacciai
     * @param numAltitudineGhiacciai il numero di rilevazioni dell'altitudine dei ghiacciai
     * @param mediaMassaGhiacciai la media della massa dei ghiacciai
     * @param scoreMedioMassaGhiacciai il punteggio medio della massa dei ghiacciai
     * @param numMassaGhiacciai il numero di rilevazioni della massa dei ghiacciai
     */
    public ParametriClimatici(float mediaVelocitaVento, float scoreMedioVento, int numVento,
                              float mediaUmidita, float scoreMedioUmidita, int numUmidita,
                              float mediaPressione, float scoreMedioPressione, int numPressione,
                              float mediaTemperatura, float scoreMedioTemperatura, int numTemperatura,
                              float mediaPrecipitazioni, float scoreMedioPrecipitazioni, int numPrecipitazioni,
                              float mediaAltitudineGhiacciai, float scoreMedioAltitudineGhiacciai, int numAltitudineGhiacciai,
                              float mediaMassaGhiacciai, float scoreMedioMassaGhiacciai, int numMassaGhiacciai) {
        this.mediaVelocitaVento = mediaVelocitaVento;
        this.scoreMedioVento = scoreMedioVento;
        this.numVento = numVento;

        this.mediaUmidita = mediaUmidita;
        this.scoreMedioUmidita = scoreMedioUmidita;
        this.numUmidita = numUmidita;

        this.mediaPressione = mediaPressione;
        this.scoreMedioPressione = scoreMedioPressione;
        this.numPressione = numPressione;

        this.mediaTemperatura = mediaTemperatura;
        this.scoreMedioTemperatura = scoreMedioTemperatura;
        this.numTemperatura = numTemperatura;

        this.mediaPrecipitazioni = mediaPrecipitazioni;
        this.scoreMedioPrecipitazioni = scoreMedioPrecipitazioni;
        this.numPrecipitazioni = numPrecipitazioni;

        this.mediaAltitudineGhiacciai = mediaAltitudineGhiacciai;
        this.scoreMedioAltitudineGhiacciai = scoreMedioAltitudineGhiacciai;
        this.numAltitudineGhiacciai = numAltitudineGhiacciai;

        this.mediaMassaGhiacciai = mediaMassaGhiacciai;
        this.scoreMedioMassaGhiacciai = scoreMedioMassaGhiacciai;
        this.numMassaGhiacciai = numMassaGhiacciai;
    }

    // Getter e Setter

    public float getMediaVelocitaVento() { return mediaVelocitaVento; }
    public float getScoreMedioVento() { return scoreMedioVento; }
    public int getNumVento() { return numVento; }

    public float getMediaUmidita() { return mediaUmidita; }
    public float getScoreMedioUmidita() { return scoreMedioUmidita; }
    public int getNumUmidita() { return numUmidita; }

    public float getMediaPressione() { return mediaPressione; }
    public float getScoreMedioPressione() { return scoreMedioPressione; }
    public int getNumPressione() { return numPressione; }

    public float getMediaTemperatura() { return mediaTemperatura; }
    public float getScoreMedioTemperatura() { return scoreMedioTemperatura; }
    public int getNumTemperatura() { return numTemperatura; }

    public float getMediaPrecipitazioni() { return mediaPrecipitazioni; }
    public float getScoreMedioPrecipitazioni() { return scoreMedioPrecipitazioni; }
    public int getNumPrecipitazioni() { return numPrecipitazioni; }

    public float getMediaAltitudineGhiacciai() { return mediaAltitudineGhiacciai; }
    public float getScoreMedioAltitudineGhiacciai() { return scoreMedioAltitudineGhiacciai; }
    public int getNumAltitudineGhiacciai() { return numAltitudineGhiacciai; }

    public float getMediaMassaGhiacciai() { return mediaMassaGhiacciai; }
    public float getScoreMedioMassaGhiacciai() { return scoreMedioMassaGhiacciai; }
    public int getNumMassaGhiacciai() { return numMassaGhiacciai; }

    /**
     * Restituisce una rappresentazione in forma di stringa di <code>ParametriClimatici</code>.
     *
     * @return una stringa con i dettagli dei parametri climatici
     */
    @Override
    public String toString() {
        return "ParametriClimatici{\n" +
                "media_velocita_vento=" + mediaVelocitaVento + "\n" +
                "score_medio_vento=" + scoreMedioVento + "\n" +
                "num_vento=" + numVento + "\n" +
                "media_umidita=" + mediaUmidita + "\n" +
                "score_medio_umidita=" + scoreMedioUmidita + "\n" +
                "num_umidita=" + numUmidita + "\n" +
                "media_pressione=" + mediaPressione + "\n" +
                "score_medio_pressione=" + scoreMedioPressione + "\n" +
                "num_pressione=" + numPressione + "\n" +
                "media_temperatura=" + mediaTemperatura + "\n" +
                "score_medio_temperatura=" + scoreMedioTemperatura + "\n" +
                "num_temperatura=" + numTemperatura + "\n" +
                "media_precipitazioni=" + mediaPrecipitazioni + "\n" +
                "score_medio_precipitazioni=" + scoreMedioPrecipitazioni + "\n" +
                "num_precipitazioni=" + numPrecipitazioni + "\n" +
                "media_altitudine_ghiacciai=" + mediaAltitudineGhiacciai + "\n" +
                "score_medio_altitudine_ghiacciai=" + scoreMedioAltitudineGhiacciai + "\n" +
                "num_altitudine_ghiacciai=" + numAltitudineGhiacciai + "\n" +
                "media_massa_ghiacciai=" + mediaMassaGhiacciai + "\n" +
                "score_medio_massa_ghiacciai=" + scoreMedioMassaGhiacciai + "\n" +
                "num_massa_ghiacciai=" + numMassaGhiacciai + "\n" +
                '}';
    }
}
