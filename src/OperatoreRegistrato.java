import java.io.Serializable;

public class OperatoreRegistrato implements Serializable {
    private static final long serialVersionUID = 1;
    int id;
    Integer centroMonitoraggioId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCentroMonitoraggioId() {
        return centroMonitoraggioId;
    }

    public void setCentroMonitoraggioId(Integer centroMonitoraggioId) {
        this.centroMonitoraggioId = centroMonitoraggioId;
    }
OperatoreRegistrato(int id){
        this.id=id;
        this.centroMonitoraggioId=null;


}
    OperatoreRegistrato(int id, Integer idcentromonitoraggio){
        this.id=id;
        this.centroMonitoraggioId=idcentromonitoraggio;
    }
}
