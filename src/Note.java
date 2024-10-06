import java.io.Serializable;
import java.sql.Date;

public class Note implements Serializable {
    private static final long serialVersionUID = 1;

    int centroMonitoraggioId;
    int operatoreId;
    Date datarilevazione;
    String notaVento;
    String notaUmidita;
    String notaPressione;
    String notaTemperatura;
    String notaPrecipitazioni;
    String notaAltitudineGhiacciai;
    String NotaMassaGhiacciai;


    public Note(int centroMonitoraggioId, int operatoreId, Date datarilevazione, String notaVento, String notaUmidita, String notaPressione, String notaTemperatura, String notaPrecipitazioni, String notaAltitudineGhiacciai, String notaMassaGhiacciai) {
        this.centroMonitoraggioId = centroMonitoraggioId;
        this.operatoreId = operatoreId;
        this.datarilevazione = datarilevazione;
        this.notaVento = notaVento;
        this.notaUmidita = notaUmidita;
        this.notaPressione = notaPressione;
        this.notaTemperatura = notaTemperatura;
        this.notaPrecipitazioni = notaPrecipitazioni;
        this.notaAltitudineGhiacciai = notaAltitudineGhiacciai;
        NotaMassaGhiacciai = notaMassaGhiacciai;
    }

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
                ", NotaMassaGhiacciai='" + NotaMassaGhiacciai ;
    }

    public int getCentroMonitoraggioId() {
        return centroMonitoraggioId;
    }

    public void setCentroMonitoraggioId(int centroMonitoraggioId) {
        this.centroMonitoraggioId = centroMonitoraggioId;
    }

    public int getOperatoreId() {
        return operatoreId;
    }

    public void setOperatoreId(int operatoreId) {
        this.operatoreId = operatoreId;
    }

    public Date getDatarilevazione() {
        return datarilevazione;
    }

    public void setDatarilevazione(Date datarilevazione) {
        this.datarilevazione = datarilevazione;
    }

    public String getNotaVento() {
        return notaVento;
    }

    public void setNotaVento(String notaVento) {
        this.notaVento = notaVento;
    }

    public String getNotaUmidita() {
        return notaUmidita;
    }

    public void setNotaUmidita(String notaUmidita) {
        this.notaUmidita = notaUmidita;
    }

    public String getNotaPressione() {
        return notaPressione;
    }

    public void setNotaPressione(String notaPressione) {
        this.notaPressione = notaPressione;
    }

    public String getNotaTemperatura() {
        return notaTemperatura;
    }

    public void setNotaTemperatura(String notaTemperatura) {
        this.notaTemperatura = notaTemperatura;
    }

    public String getNotaPrecipitazioni() {
        return notaPrecipitazioni;
    }

    public void setNotaPrecipitazioni(String notaPrecipitazioni) {
        this.notaPrecipitazioni = notaPrecipitazioni;
    }

    public String getNotaAltitudineGhiacciai() {
        return notaAltitudineGhiacciai;
    }

    public void setNotaAltitudineGhiacciai(String notaAltitudineGhiacciai) {
        this.notaAltitudineGhiacciai = notaAltitudineGhiacciai;
    }

    public String getNotaMassaGhiacciai() {
        return NotaMassaGhiacciai;
    }

    public void setNotaMassaGhiacciai(String notaMassaGhiacciai) {
        NotaMassaGhiacciai = notaMassaGhiacciai;
    }


}
