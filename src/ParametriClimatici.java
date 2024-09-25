public class ParametriClimatici {
    private String dateRilevazioni;
    private float mediaVelocitaVento;
    private float scoreMedioVento;
    private int numVento;
    private String noteVento;
    private float mediaUmidita;
    private float scoreMedioUmidita;
    private int numUmidita;
    private String noteUmidita;
    private float mediaPressione;
    private float scoreMedioPressione;
    private int numPressione;
    private String notePressione;
    private float mediaTemperatura;
    private float scoreMedioTemperatura;
    private int numTemperatura;
    private String noteTemperatura;
    private float mediaPrecipitazioni;
    private float scoreMedioPrecipitazioni;
    private int numPrecipitazioni;
    private String notePrecipitazioni;
    private float mediaAltitudineGhiacciai;
    private float scoreMedioAltitudineGhiacciai;
    private int numAltitudineGhiacciai;
    private String noteAltitudineGhiacciai;
    private float mediaMassaGhiacciai;
    private float scoreMedioMassaGhiacciai;
    private int numMassaGhiacciai;
    private String noteMassaGhiacciai;

    // Costruttore aggiornato
    public ParametriClimatici(String dateRilevazioni, float mediaVelocitaVento, float scoreMedioVento, int numVento, String noteVento,
                              float mediaUmidita, float scoreMedioUmidita, int numUmidita, String noteUmidita,
                              float mediaPressione, float scoreMedioPressione, int numPressione, String notePressione,
                              float mediaTemperatura, float scoreMedioTemperatura, int numTemperatura, String noteTemperatura,
                              float mediaPrecipitazioni, float scoreMedioPrecipitazioni, int numPrecipitazioni, String notePrecipitazioni,
                              float mediaAltitudineGhiacciai, float scoreMedioAltitudineGhiacciai, int numAltitudineGhiacciai, String noteAltitudineGhiacciai,
                              float mediaMassaGhiacciai, float scoreMedioMassaGhiacciai, int numMassaGhiacciai, String noteMassaGhiacciai) {
        this.dateRilevazioni = dateRilevazioni;
        this.mediaVelocitaVento = mediaVelocitaVento;
        this.scoreMedioVento = scoreMedioVento;
        this.numVento = numVento;
        this.noteVento = noteVento;
        this.mediaUmidita = mediaUmidita;
        this.scoreMedioUmidita = scoreMedioUmidita;
        this.numUmidita = numUmidita;
        this.noteUmidita = noteUmidita;
        this.mediaPressione = mediaPressione;
        this.scoreMedioPressione = scoreMedioPressione;
        this.numPressione = numPressione;
        this.notePressione = notePressione;
        this.mediaTemperatura = mediaTemperatura;
        this.scoreMedioTemperatura = scoreMedioTemperatura;
        this.numTemperatura = numTemperatura;
        this.noteTemperatura = noteTemperatura;
        this.mediaPrecipitazioni = mediaPrecipitazioni;
        this.scoreMedioPrecipitazioni = scoreMedioPrecipitazioni;
        this.numPrecipitazioni = numPrecipitazioni;
        this.notePrecipitazioni = notePrecipitazioni;
        this.mediaAltitudineGhiacciai = mediaAltitudineGhiacciai;
        this.scoreMedioAltitudineGhiacciai = scoreMedioAltitudineGhiacciai;
        this.numAltitudineGhiacciai = numAltitudineGhiacciai;
        this.noteAltitudineGhiacciai = noteAltitudineGhiacciai;
        this.mediaMassaGhiacciai = mediaMassaGhiacciai;
        this.scoreMedioMassaGhiacciai = scoreMedioMassaGhiacciai;
        this.numMassaGhiacciai = numMassaGhiacciai;
        this.noteMassaGhiacciai = noteMassaGhiacciai;
    }

    // Getter e setter per tutti i campi

    // Metodi getter per esempio
    public String getDateRilevazioni() { return dateRilevazioni; }
    public float getMediaVelocitaVento() { return mediaVelocitaVento; }
    public float getScoreMedioVento() { return scoreMedioVento; }
    public int getNumVento() { return numVento; }
    public String getNoteVento() { return noteVento; }
    public float getMediaUmidita() { return mediaUmidita; }
    public float getScoreMedioUmidita() { return scoreMedioUmidita; }
    public int getNumUmidita() { return numUmidita; }
    public String getNoteUmidita() { return noteUmidita; }
    public float getMediaPressione() { return mediaPressione; }
    public float getScoreMedioPressione() { return scoreMedioPressione; }
    public int getNumPressione() { return numPressione; }
    public String getNotePressione() { return notePressione; }
    public float getMediaTemperatura() { return mediaTemperatura; }
    public float getScoreMedioTemperatura() { return scoreMedioTemperatura; }
    public int getNumTemperatura() { return numTemperatura; }
    public String getNoteTemperatura() { return noteTemperatura; }
    public float getMediaPrecipitazioni() { return mediaPrecipitazioni; }
    public float getScoreMedioPrecipitazioni() { return scoreMedioPrecipitazioni; }
    public int getNumPrecipitazioni() { return numPrecipitazioni; }
    public String getNotePrecipitazioni() { return notePrecipitazioni; }
    public float getMediaAltitudineGhiacciai() { return mediaAltitudineGhiacciai; }
    public float getScoreMedioAltitudineGhiacciai() { return scoreMedioAltitudineGhiacciai; }
    public int getNumAltitudineGhiacciai() { return numAltitudineGhiacciai; }
    public String getNoteAltitudineGhiacciai() { return noteAltitudineGhiacciai; }
    public float getMediaMassaGhiacciai() { return mediaMassaGhiacciai; }
    public float getScoreMedioMassaGhiacciai() { return scoreMedioMassaGhiacciai; }
    public int getNumMassaGhiacciai() { return numMassaGhiacciai; }
    public String getNoteMassaGhiacciai() { return noteMassaGhiacciai; }

    // Altri metodi setter possono essere aggiunti allo stesso modo
    @Override
    public String toString() {
        return "ParametriClimatici{\n" +
                "date_rilevazioni='" + dateRilevazioni + '\'' + "\n" +
                "media_velocita_vento=" + mediaVelocitaVento + "\n" +
                "score_medio_vento=" + scoreMedioVento + "\n" +
                "num_vento=" + numVento + "\n" +
                "note_vento='" + noteVento + '\'' + "\n" +
                "media_umidita=" + mediaUmidita + "\n" +
                "score_medio_umidita=" + scoreMedioUmidita + "\n" +
                "num_umidita=" + numUmidita + "\n" +
                "note_umidita='" + noteUmidita + '\'' + "\n" +
                "media_pressione=" + mediaPressione + "\n" +
                "score_medio_pressione=" + scoreMedioPressione + "\n" +
                "num_pressione=" + numPressione + "\n" +
                "note_pressione='" + notePressione + '\'' + "\n" +
                "media_temperatura=" + mediaTemperatura + "\n" +
                "score_medio_temperatura=" + scoreMedioTemperatura + "\n" +
                "num_temperatura=" + numTemperatura + "\n" +
                "note_temperatura='" + noteTemperatura + '\'' + "\n" +
                "media_precipitazioni=" + mediaPrecipitazioni + "\n" +
                "score_medio_precipitazioni=" + scoreMedioPrecipitazioni + "\n" +
                "num_precipitazioni=" + numPrecipitazioni + "\n" +
                "note_precipitazioni='" + notePrecipitazioni + '\'' + "\n" +
                "media_altitudine_ghiacciai=" + mediaAltitudineGhiacciai + "\n" +
                "score_medio_altitudine_ghiacciai=" + scoreMedioAltitudineGhiacciai + "\n" +
                "num_altitudine_ghiacciai=" + numAltitudineGhiacciai + "\n" +
                "note_altitudine_ghiacciai='" + noteAltitudineGhiacciai + '\'' + "\n" +
                "media_massa_ghiacciai=" + mediaMassaGhiacciai + "\n" +
                "score_medio_massa_ghiacciai=" + scoreMedioMassaGhiacciai + "\n" +
                "num_massa_ghiacciai=" + numMassaGhiacciai + "\n" +
                "note_massa_ghiacciai='" + noteMassaGhiacciai + '\'' + "\n" +
                '}';
    }

}
