package climatemonitoring;

import java.io.Serializable;


/**
 * Rappresenta un'area geografica con dettagli relativi alla sua posizione.
 * Questa classe implementa l'interfaccia {@link Serializable} per permettere la serializzazione degli oggetti.
 *  @author Moranzoni Samuele
 *   @author Di Tullio Edoardo
 */
public class AreaGeografica implements Serializable {
    private static final long serialVersionUID = 1;

    /** La denominazione dell'area geografica. */
    public String denominazione;

    /** Lo stato in cui si trova l'area geografica. */
    public String stato;

    /** La latitudine dell'area geografica. */
    public double latitudine;

    /** La longitudine dell'area geografica. */
    public double longitudine;

    /**
     * Costruisce un'istanza di climatemonitoring.climatemonitoring.model.climatemonitoring.AreaGeografica con i valori specificati.
     *
     * @param denominazione La denominazione dell'area geografica.
     * @param stato Lo stato in cui si trova l'area geografica.
     * @param latitudine La latitudine dell'area geografica.
     * @param longitudine La longitudine dell'area geografica.
     */
    public AreaGeografica(String denominazione, String stato, double latitudine, double longitudine) {
        this.denominazione = denominazione;
        this.stato = stato;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    /**
     * Restituisce una stringa che rappresenta l'area geografica.
     *
     * @return Una stringa contenente la denominazione, lo stato, la latitudine e la longitudine dell'area.
     */
    @Override
    public String toString() {
        return
                "Denominazione='" + denominazione + '\'' +
                        ", stato='" + stato + '\'' +
                        ", latitudine=" + latitudine +
                        ", longitudine=" + longitudine;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public String getStato() {
        return stato;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }
}
