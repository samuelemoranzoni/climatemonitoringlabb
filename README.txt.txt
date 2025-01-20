ClimateMonitoring lab b:
N.B.: la classe main del client richiesta da specifiche è ClimateMonitoringGui
ESECUZIONE:
Prima di avviare l’applicazione, è necessario configurare il database PostgreSQL. Seguire i passaggi descritti nel Manuale Tecnico per creare il database e le relative tabelle utilizzando gli script forniti
 nella sezione script SQL.
 • Creare un database dedicato per l’applicazione.
 • Eseguire gli script SQL per ricreare le tabelle necessarie ( o alternativamente il backup della directory doc) .
 • Memorizzare le seguenti informazioni: host, user admin, password, e nome database, poichè saranno richieste durante l’esecuzione del programma.
 Il programma applicativo cercherà automaticamente di stabilire una connessione con il database creato.E' fondamentale avere a disposizione i dati di accesso al database.
2.3 Avvio del server
 Estrarre il file Progettob da qualche parte sul computer .
 Ora il primo passo per avviare il programma applicativo è configurare ed eseguire il server.
 1. Aprire il Prompt dei Comandi (CDM) e navigare fino alla directory del progetto:
 cd C:\percorso\Progettob\bin
 2. Una volta nella directory corretta, eseguire il comando per avviare il server:
 java −jar serverCM. jar
 3. Verranno richieste le credenziali di amministratore del server. Si avranno al massimo 3 tentativi
 per autenticarsi correttamente. Le credenziali richieste includeranno:
 • Local Host .
 • Nome utente admin.
 • Password.
 • Nome database.
 Se le credenziali sono corrette, il serverCM rimarrà attivo e inizializzerà la connessione con il database. E' possibile interrompere il server in qualsiasi momento digitando il comando stop.
 Alternativamente sarà sufficiente fare doppio clic sul file serverCM.bat per avviare automaticamente il server . I file .bat sono configurati per eseguire i comandi Java necessari per avviare il server e il client, senza la necessità di aprire il Prompt dei Comandi.

2.4 Avvio del client
 Una volta avviato correttamente il server , sarà possibile avviare il client.
 1. Rimanendo nella directory bin, eseguire il client utilizzando il seguente comando:
 java −jar clientCM. jar
 2. Il client si avvierà e mostrerà la schermata principale dell’applicazione.
 Alternativamente sarà sufficiente fare doppio clic sul file clientCM.bat per avviare automaticamente il client . I file .bat sono configurati per eseguire i comandi Java necessari per avviare il server e il client, senza la necessità di aprire il Prompt dei Comandi.
 E' importante notare che, se il server non ` e attivo, il client non sarà in grado di eseguire alcuna operazione. L’applicazione non sarà in grado di connettersi al database senza il server in esecuzione.

MAVEN
INSTALLAZIONE:
Installazione di Maven su Windows
a) Sul sito ufficiale https://maven.apache.org/download.cgi 
scegliere la versione binaria di Maven (ad es., "Binary zip archive") e scaricare il file .zip.

b) Estrarre il contenuto della cartella compressa in una directory a vostra scelta.

c) Andare su Pannello di controllo > Sistema e sicurezza > Sistema > Impostazioni di sistema avanzate > Variabili d'ambiente, e creare due nuove variabili d'ambiente: I) La prima chiamata "M2_HOME" che punta alla directory appena estratta II) La seconda chiamata "PATH" che punta alla medesima cartella del punto I

d) Verificare l'installazione aprendo il Prompt dei Comandi e digitare "mvn -v", se avete fatto tutto correttamente il risultato dovrebbe essere simile a questo:  
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Users\samuele\OneDrive\Desktop\apache-maven-3.9.9
Java version: 19, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-19
Default locale: en_GB, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"

COMANDI:
Comandi Maven per Compilare ed Eseguire il Progetto:
 a) Pulizia del Progetto
Prima di compilare, puoi eseguire un comando di pulizia per rimuovere tutti i file compilati in precedenza: 
mvn clean

b) Compilazione del Progetto Per compilare il progetto, esegui il comando: 
mvn test 
Questo comando compilerà tutte le classi Java nel progetto

c) Esecuzione dei Test (Opzionale) Se hai dei test nel progetto, puoi eseguirli con: mvn test

d) Creazione del Pacchetto (Jar) Per creare un file JAR eseguibile del progetto: 
mvn package 
Questo comando creerà un file JAR nella cartella target del progetto.

e) Esecuzione del Progetto Per eseguire il progetto, puoi usare il comando seguente, specificando il file JAR generato (ad esempio): 
java -jar target/ServerCM.jar

FILE DI BUILD 
I file di build di maven(POM.xml) dei vari moduli sono presenti nella directory \src di ogni rispettivo modulo .








