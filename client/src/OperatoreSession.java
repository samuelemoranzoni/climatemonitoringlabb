/**
 * Classe che rappresenta una sessione per un operatore registrato.
 * Implementa il pattern Singleton per garantire che ci sia solo un'istanza di OperatoreSession.
 * @author Moranzoni Samuele
 * @author Di Tullio Edoardo
 */
public class OperatoreSession {
    private static OperatoreSession instance;  // Istanza unica della classe OperatoreSession
    private OperatoreRegistrato operatore;     // Riferimento all'operatore registrato

    /**
     * Costruttore privato per impedire la creazione di nuove istanze dall'esterno.
     */
    private OperatoreSession() {}

    /**
     * Restituisce l'istanza unica di OperatoreSession.
     * Se l'istanza non esiste, viene creata una nuova istanza.
     *
     * @return istanza corrente di OperatoreSession
     */
    public static OperatoreSession getInstance() {
        if (instance == null) {
            instance = new OperatoreSession();
        }
        return instance;
    }

    /**
     * Imposta l'operatore registrato per la sessione.
     *
     * @param operatore l'operatore registrato da associare alla sessione
     */
    public void setOperatore(OperatoreRegistrato operatore) {
        this.operatore = operatore;
    }

    /**
     * Restituisce l'operatore registrato associato alla sessione.
     *
     * @return l'operatore registrato della sessione
     */
    public OperatoreRegistrato getOperatore() {
        return this.operatore;
    }

    /**
     * Imposta l'ID del centro di monitoraggio per l'operatore registrato.
     *
     * @param idcentromonitoraggio l'ID del centro di monitoraggio da assegnare
     */
    public void setCentroMonitoraggioIdOperatore(int idcentromonitoraggio) {
        this.operatore.setCentroMonitoraggioId(idcentromonitoraggio);
    }
}
