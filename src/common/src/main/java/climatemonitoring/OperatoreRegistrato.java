package climatemonitoring;

import java.io.Serializable;


/**
 * Rappresenta un operatore registrato con un ID, un ID del centro di monitoraggio e un ID utente.
 * Questa classe implementa Serializable per consentire la serializzazione delle sue istanze.
 * @author Moranzoni Samuele
 * @author Di Tullio Edoardo
 */
public class OperatoreRegistrato implements Serializable {
    private static final long serialVersionUID = 1;

    private int id;
    private Integer centroMonitoraggioId;
    private String userid;

    /**
     * Restituisce l'ID dell'operatore.
     *
     * @return l'ID dell'operatore.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'operatore.
     *
     * @param id l'ID da impostare per l'operatore.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Imposta l'ID utente per l'operatore.
     *
     * @param userid l'ID utente da impostare per l'operatore.
     */
    public void setUserId(String userid) {
        this.userid = userid;
    }

    /**
     * Restituisce l'ID utente dell'operatore.
     *
     * @return l'ID utente dell'operatore.
     */
    public String getUserId() {
        return userid;
    }

    /**
     * Restituisce l'ID del centro di monitoraggio associato all'operatore.
     *
     * @return l'ID del centro di monitoraggio, o null se non impostato.
     */
    public Integer getCentroMonitoraggioId() {
        return centroMonitoraggioId;
    }

    /**
     * Imposta l'ID del centro di monitoraggio per l'operatore.
     *
     * @param centroMonitoraggioId l'ID del centro di monitoraggio da impostare per l'operatore.
     */
    public void setCentroMonitoraggioId(Integer centroMonitoraggioId) {
        this.centroMonitoraggioId = centroMonitoraggioId;
    }

    /**
     * Costruisce un oggetto climatemonitoring.climatemonitoring.model.climatemonitoring.OperatoreRegistrato con l'ID specificato.
     *
     * @param id l'ID dell'operatore.
     */
    public OperatoreRegistrato(int id) {
        this.id = id;
        this.centroMonitoraggioId = null;
    }

    /**
     * Costruisce un oggetto climatemonitoring.climatemonitoring.model.climatemonitoring.OperatoreRegistrato con l'ID specificato,
     * l'ID del centro di monitoraggio e l'ID utente.
     *
     * @param id l'ID dell'operatore.
     * @param centroMonitoraggioId l'ID del centro di monitoraggio dell'operatore.
     * @param userid l'ID utente dell'operatore.
     */
    public OperatoreRegistrato(int id, Integer centroMonitoraggioId, String userid) {
        this.id = id;
        this.centroMonitoraggioId = centroMonitoraggioId;
        this.userid = userid;
    }
}
