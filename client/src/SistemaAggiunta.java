/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.io.*;

/**
 * Classe SistemaAggiunta per gestire l'aggiunta di nuovi centri.
 */
public class SistemaAggiunta {

    /**
     * Metodo per aggiungere un centro ad un operatore.
     *
     * @param riferimento Il riferimento all'area da aggiungere.
     * @param nuovaStringa La nuova stringa da aggiungere al file.
     */
    public void AggiungiCentro(String riferimento, String nuovaStringa) {
        // Percorso del file contenente gli operatori registrati
        String filePath = "data\\OperatoriRegistrati.dati.txt";
        // Percorso del file temporaneo
        String tempFilePath = "data\\temp.txt";
        File oldFile = new File(filePath);
        File newFile = new File(tempFilePath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(oldFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            String currentLine;

            // Legge ogni riga del file
            while ((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.split(" ");
                // Se il riferimento corrisponde, aggiunge la nuova stringa alla riga
                if (words.length >= 5 && words[4].equals(riferimento)) {
                    currentLine += " " + nuovaStringa;
                }
                // Scrive la riga nel nuovo file
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            // Chiude i flussi e sostituisce il vecchio file con il nuovo
            writer.close();
            reader.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);
        } catch (IOException e) {
            // Gestisce le eccezioni di I/O
            e.printStackTrace();
        }
    }
}


