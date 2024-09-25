public class AreaGeografica {
    public String denominazione;
    public String stato;
    public double latitudine;
    public double longitudine;

    public AreaGeografica(String denominazione, String stato, double latitudine, double longitudine) {
        this.denominazione = denominazione;
        this.stato = stato;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "AreaGeografica {" +
                "denominazione='" + denominazione + '\'' +
                ", stato='" + stato + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                " }";
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
