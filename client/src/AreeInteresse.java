/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

/**
 * Classe AreeInteresse per rappresentare un'area di interesse nel monitoraggio del clima.
 */
public class AreeInteresse {
    private String nome;
    private String Nazione;
    private String coordinate;

    /**
     * Costruttore per la classe AreeInteresse.
     *
     * @param nome Il nome dell'area di interesse.
     * @param Nazione La nazione in cui si trova l'area di interesse.
     * @param coordinate Le coordinate dell'area di interesse.
     */
    public AreeInteresse(String nome,String Nazione,String coordinate){
        this.nome=nome;
        this.Nazione=Nazione;
        this.coordinate=coordinate;
    }

    /**
     * Ottiene il nome dell'area di interesse.
     *
     * @return Il nome dell'area di interesse.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ottiene la nazione dell'area di interesse.
     *
     * @return La nazione dell'area di interesse.
     */
    public String getNazione() {
        return Nazione;
    }

    /**
     * Ottiene le coordinate dell'area di interesse.
     *
     * @return Le coordinate dell'area di interesse.
     */
    public String getCoordinate() {
        return coordinate;
    }

    /**
     * Converte i dati dell'area di interesse in una stringa CSV.
     *
     * @return Una stringa CSV che rappresenta l'area di interesse.
     */
    public String toCsvString() {
        StringBuilder csvString = new StringBuilder();
        csvString.append(nome).append(";").append(Nazione).append(";").append(coordinate).append(";");
        return csvString.toString();
    }
}

