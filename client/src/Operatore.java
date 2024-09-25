/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

/**
 * Classe Operatore per rappresentare un'operatore.
 */
public class Operatore {
    //caratteristiche dell'operatore
    private String nome; 
    private String cognome;
    private String codiceFiscale;
    private String email;
    private String userid;
    private String password;
    private String centroMonitoraggio;
    //costruttoere dell'operatore
    public Operatore(String nome, String cognome, String codiceFiscale, String email, String userid, String password, String centroMonitoraggio) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.centroMonitoraggio = centroMonitoraggio;
    }

    
    /** 
     * @return String
     */
    // Metodi getter e setter per ciascun campo
    public String getNome() {
        return nome;
    }

    
    /** 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCentroMonitoraggio() {
        return centroMonitoraggio;
    }

    public void setCentroMonitoraggio(String centroMonitoraggio) {
        this.centroMonitoraggio = centroMonitoraggio;
    }
    //stampa  i dati dell'operatore in formato stringa
    @Override
    public String toString() {

        return getNome() + " " + getCognome() + " " + getCodiceFiscale() + " " + getEmail() + " " + getUserid() + " " + getPassword() + " " + getCentroMonitoraggio();
    }
}