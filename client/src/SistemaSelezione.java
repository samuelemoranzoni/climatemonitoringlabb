import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaSelezione {

    /**
     * Questo metodo legge un file CSV di dati climatici e restituisce le linee che corrispondono a un'area geografica specifica.
     * L'area geografica è specificata da un parametro di riferimento, che può essere il nome dell'area o lo stato.
     * Se non vengono trovati dati per l'area specificata, viene stampato un messaggio di errore.
     *
     * @param reference il nome dell'area geografica da cercare.
     * @return una lista di stringhe che rappresentano i dati dell'area geografica. Ogni stringa è una riga del file CSV.
     *@throws IOException se si verifica un errore durante la lettura del file CSV.
     *
     */

    public static List<String> visualizzaAreaGeografica(String reference) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data\\ParametriClimatici.dati.csv"))) {
            String line;
            int cont=0;
            lines.add("Climate Category;Explanation;Score;Notes (max 256 characters)");
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");


                if (fields.length > 1 && fields[1].toLowerCase().equals(reference.toLowerCase())) {
                    if (cont==0) {
                        lines.add(fields[0]+";"+fields[1]+";"+fields[2]);

                    }
                    else{
                        lines.add(fields[2]);
                    }
                    cont++;

                    for (int i = 0; i < 7 && (line = br.readLine()) != null; i++) {
                        lines.add(line);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines.isEmpty() || lines.size()==1) {
            System.out.println("dati non presenti");
        } else {
            printFirstAndLastEight(lines);

        }
        return lines;
    }



    /**
     * @param list
     */
    public static void printFirstAndLastEight(List<String> list) {
        if (list.size() > 1) {
            System.out.println(list.get(1));
        } else if (list.size() > 0) {
            System.out.println( list.get(0));
        } else {
            // System.out.println("La lista è vuota.");
            return;
        }

        System.out.println(list.get(0));

        for (int i = Math.max(0, list.size() - 7); i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
