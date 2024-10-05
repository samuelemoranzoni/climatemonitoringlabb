import java.io.Serializable;

public class OperatoreRegistrato implements Serializable {
    private static final long serialVersionUID = 1;
    int id;
    Integer centroMonitoraggioId;

    String userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userid){
        this.userid=userid;
    }
public String getUserId() {
     return userid;

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
    OperatoreRegistrato(int id, Integer idcentromonitoraggio , String userId){
        this.id=id;
        this.centroMonitoraggioId=idcentromonitoraggio;
        this.userid=userId;
    }
}
