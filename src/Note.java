import java.io.Serializable;
import java.sql.Date;


/**
 * La classe Note rappresenta una nota di rilevazione meteorologica con informazioni su vari parametri
 * come vento, umidità, pressione, temperatura, e condizioni dei ghiacciai.
 * Implementa l'interfaccia Serializable per poter essere utilizzata dal client che comunica col server
 * @author Moranzoni Samuele
 * @author Di Tullio Edoardo.
 */
public class Note implements Serializable {
    private static final long serialVersionUID = 1;

    private int centroMonitoraggioId;
    private int operatoreId;
    private Date datarilevazione;
    private String notaVento;
    private String notaUmidita;
    private String notaPressione;
    private String notaTemperatura;
    private String notaPrecipitazioni;
    private String notaAltitudineGhiacciai;
    private String notaMassaGhiacciai;

    /**
     * Costruttore della classe Note.
     *
     * @param centroMonitoraggioId ID del centro di monitoraggio.
     * @param operatoreId ID dell'operatore che ha inserito la nota.
     * @param datarilevazione Data in cui è stata effettuata la rilevazione.
     * @param notaVento Nota riguardante le condizioni del vento.
     * @param notaUmidita Nota riguardante le condizioni di umidità.
     * @param notaPressione Nota riguardante la pressione atmosferica.
     * @param notaTemperatura Nota riguardante la temperatura.
     * @param notaPrecipitazioni Nota riguardante le precipitazioni.
     * @param notaAltitudineGhiacciai Nota riguardante l'altitudine dei ghiacciai.
     * @param notaMassaGhiacciai Nota riguardante la massa dei ghiacciai.
     */
    public Note(int centroMonitoraggioId, int operatoreId, Date datarilevazione, String notaVento, String notaUmidita,
                String notaPressione, String notaTemperatura, String notaPrecipitazioni,
                String notaAltitudineGhiacciai, String notaMassaGhiacciai) {
        this.centroMonitoraggioId = centroMonitoraggioId;
        this.operatoreId = operatoreId;
        this.datarilevazione = datarilevazione;
        this.notaVento = notaVento;
        this.notaUmidita = notaUmidita;
        this.notaPressione = notaPressione;
        this.notaTemperatura = notaTemperatura;
        this.notaPrecipitazioni = notaPrecipitazioni;
        this.notaAltitudineGhiacciai = notaAltitudineGhiacciai;
        this.notaMassaGhiacciai = notaMassaGhiacciai;
    }

    /**
     * Restituisce una stringa rappresentativa della nota.
     *
     * @return Una stringa che rappresenta la nota con tutti i suoi parametri.
     */
    @Override
    public String toString() {
        return
                "centroMonitoraggioId=" + centroMonitoraggioId +
                        ", operatoreId=" + operatoreId +
                        ", datarilevazione=" + datarilevazione +
                        ", notaVento='" + notaVento + '\'' +
                        ", notaUmidita='" + notaUmidita + '\'' +
                        ", notaPressione='" + notaPressione + '\'' +
                        ", notaTemperatura='" + notaTemperatura + '\'' +
                        ", notaPrecipitazioni='" + notaPrecipitazioni + '\'' +
                        ", notaAltitudineGhiacciai='" + notaAltitudineGhiacciai + '\'' +
                        ", notaMassaGhiacciai='" + notaMassaGhiacciai + '\'';
    }

    /**
     * Restituisce l'ID del centro di monitoraggio.
     *
     * @return L'ID del centro di monitoraggio.
     */
    public int getCentroMonitoraggioId() {
        return centroMonitoraggioId;
    }

    /**
     * Imposta l'ID del centro di monitoraggio.
     *
     * @param centroMonitoraggioId L'ID del centro di monitoraggio da impostare.
     */
    public void setCentroMonitoraggioId(int centroMonitoraggioId) {
        this.centroMonitoraggioId = centroMonitoraggioId;
    }

    /**
     * Restituisce l'ID dell'operatore.
     *
     * @return L'ID dell'operatore.
     */
    public int getOperatoreId() {
        return operatoreId;
    }

    /**
     * Imposta l'ID dell'operatore.
     *
     * @param operatoreId L'ID dell'operatore da impostare.
     */
    public void setOperatoreId(int operatoreId) {
        this.operatoreId = operatoreId;
    }

    /**
     * Restituisce la data della rilevazione.
     *
     * @return La data della rilevazione.
     */
    public Date getDatarilevazione() {
        return datarilevazione;
    }

    /**
     * Imposta la data della rilevazione.
     *
     * @param datarilevazione La data della rilevazione da impostare.
     */
    public void setDatarilevazione(Date datarilevazione) {
        this.datarilevazione = datarilevazione;
    }

    /**
     * Restituisce la nota sul vento.
     *
     * @return La nota sul vento.
     */
    public String getNotaVento() {
        return notaVento;
    }

    /**
     * Imposta la nota sul vento.
     *
     * @param notaVento La nota sul vento da impostare.
     */
    public void setNotaVento(String notaVento) {
        this.notaVento = notaVento;
    }

    /**
     * Restituisce la nota sull'umidità.
     *
     * @return La nota sull'umidità.
     */
    public String getNotaUmidita() {
        return notaUmidita;
    }

    /**
     * Imposta la nota sull'umidità.
     *
     * @param notaUmidita La nota sull'umidità da impostare.
     */
    public void setNotaUmidita(String notaUmidita) {
        this.notaUmidita = notaUmidita;
    }

    /**
     * Restituisce la nota sulla pressione atmosferica.
     *
     * @return La nota sulla pressione.
     */
    public String getNotaPressione() {
        return notaPressione;
    }

    /**
     * Imposta la nota sulla pressione atmosferica.
     *
     * @param notaPressione La nota sulla pressione da impostare.
     */
    public void setNotaPressione(String notaPressione) {
        this.notaPressione = notaPressione;
    }

    /**
     * Restituisce la nota sulla temperatura.
     *
     * @return La nota sulla temperatura.
     */
    public String getNotaTemperatura() {
        return notaTemperatura;
    }

    /**
     * Imposta la nota sulla temperatura.
     *
     * @param notaTemperatura La nota sulla temperatura da impostare.
     */
    public void setNotaTemperatura(String notaTemperatura) {
        this.notaTemperatura = notaTemperatura;
    }

    /**
     * Restituisce la nota sulle precipitazioni.
     *
     * @return La nota sulle precipitazioni.
     */
    public String getNotaPrecipitazioni() {
        return notaPrecipitazioni;
    }

    /**
     * Imposta la nota sulle precipitazioni.
     *
     * @param notaPrecipitazioni La nota sulle precipitazioni da impostare.
     */
    public void setNotaPrecipitazioni(String notaPrecipitazioni) {
        this.notaPrecipitazioni = notaPrecipitazioni;
    }

    /**
     * Restituisce la nota sull'altitudine dei ghiacciai.
     *
     * @return La nota sull'altitudine dei ghiacciai.
     */
    public String getNotaAltitudineGhiacciai() {
        return notaAltitudineGhiacciai;
    }

    /**
     * Imposta la nota sull'altitudine dei ghiacciai.
     *
     * @param notaAltitudineGhiacciai La nota sull'altitudine dei ghiacciai da impostare.
     */
    public void setNotaAltitudineGhiacciai(String notaAltitudineGhiacciai) {
        this.notaAltitudineGhiacciai = notaAltitudineGhiacciai;
    }

    /**
     * Restituisce la nota sulla massa dei ghiacciai.
     *
     * @return La nota sulla massa dei ghiacciai.
     */
    public String getNotaMassaGhiacciai() {
        return notaMassaGhiacciai;
    }

    /**
     * Imposta la nota sulla massa dei ghiacciai.
     *
     * @param notaMassaGhiacciai La nota sulla massa dei ghiacciai da impostare.
     */
    public void setNotaMassaGhiacciai(String notaMassaGhiacciai) {
        this.notaMassaGhiacciai = notaMassaGhiacciai;
    }
}
