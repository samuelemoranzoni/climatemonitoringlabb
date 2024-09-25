/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.util.ArrayList;
import java.util.List;

/**
 * Classe CentroMonitoraggio per rappresentare un centro di monitoraggio del clima.
 */
public class CentroMonitoraggio {
    private String nomeCentro;
    private String indirizzoFisico;
    private List<String> areeDiInteresse;

    /**
     * Costruttore per la classe CentroMonitoraggio.
     *
     * @param nomeCentro Il nome del centro di monitoraggio.
     * @param indirizzo  L'indirizzo fisico del centro di monitoraggio.
     */
    public CentroMonitoraggio(String nomeCentro, String indirizzo,List<String> areeDiInteresse) {
        this.nomeCentro = nomeCentro;
        this.indirizzoFisico = indirizzo;
        this.areeDiInteresse = areeDiInteresse != null ? areeDiInteresse : new ArrayList<>();
    }

    /**
     * Ottiene il nome del centro di monitoraggio.
     *
     * @return Il nome del centro di monitoraggio.
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * Ottiene l'indirizzo fisico del centro di monitoraggio.
     *
     * @return L'indirizzo fisico del centro di monitoraggio.
     */
    public String getIndirizzoFisico() {
        return indirizzoFisico;
    }

    /**
     * Ottiene le aree di interesse monitorate dal centro di monitoraggio.
     *
     * @return Una lista delle aree di interesse.
     */
    public List<String> getAreeDiInteresse() {
        return areeDiInteresse;
    }

    /**
     * Converte i dati del centro di monitoraggio in una stringa CSV.
     *
     * @return Una stringa CSV che rappresenta il centro di monitoraggio.
     */
    public String toCsvString() {
        StringBuilder csvString = new StringBuilder();
        csvString.append(nomeCentro).append(";").append(indirizzoFisico).append(";");

        for (String area : areeDiInteresse) {
            csvString.append(area).append(",");
        }
        csvString.deleteCharAt(csvString.length() - 1);
        csvString.append(";");

        return csvString.toString();
    }
}