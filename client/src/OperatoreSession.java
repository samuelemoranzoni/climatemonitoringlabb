
//design pattern singleton sull'istanza OperatoreRegistrato
public class OperatoreSession {
    private static OperatoreSession instance;
    private OperatoreRegistrato operatore;

    private OperatoreSession() {}

    public static OperatoreSession getInstance() {
        if (instance == null) {
            instance = new OperatoreSession();
        }
        return instance;
    }

    public void setOperatore(OperatoreRegistrato operatore) {
        this.operatore = operatore;
    }

    public OperatoreRegistrato getOperatore() {
        return this.operatore;
    }

    public void setCentroMonitoraggioIdOperatore(int idcentromonitoraggio){

        this.operatore.setCentroMonitoraggioId(idcentromonitoraggio);

    }
}
