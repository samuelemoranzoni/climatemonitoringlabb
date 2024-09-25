/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.io.*;
import java.util.*;
/**
 * classe per la gestione della ricerca delle aree
 */
public class SistemaRicerca {
    /**
     * Questo metodo cerca un'area geografica in base a una query fornita.
     * La query può essere un nome , uno stato o una coppia di coordinate.
     * Se la query è una coppia di coordinate, il metodo cerca un'area che corrisponde esattamente a quelle coordinate.
     * Se non trova un'area, cerca un'area entro un raggio di 0.5 gradi da quelle coordinate.
     * Se la query è un nome o uno stato, il metodo cerca un'area che contiene la query nel suo nome o stato.
     * I risultati della ricerca vengono quindi ordinati in base alla lunghezza del nome dell'area.
     *
      * @param query la query di ricerca. Può essere un nome di area, uno stato o una coppia di coordinate.
 * @return una lista di stringhe che rappresentano le aree che corrispondono alla query. Ogni stringa è una riga del file CSV.
 * @throws IOException se si verifica un errore durante la lettura del file CSV.
     */

    public List<String> cercaAreaGeografica(String query) {
        List<String> risultati = new ArrayList<>();
        String path = "data\\CoordinateMonitoraggio.dati.csv";
        boolean permesso=false,permesso2=false,permesso3=false;
        double latitudine=0.0,longitudine=0.0;
        try  {
            if (query.contains(",")) {

                String[] parti = query.split(",");
                latitudine = Double.parseDouble(parti[0].trim());
                longitudine = Double.parseDouble(parti[1].trim());
                permesso=true;
            }
            String riga;

            do {
                BufferedReader br = new BufferedReader(new FileReader(path));
                while ((riga = br.readLine()) != null) {
                    String[] campi = riga.split(";");


                    if (campi.length > 1 && !(permesso) && !(permesso2) ) {
                        String nomeArea = campi[0].toLowerCase();
                        permesso3=true;
                        String stato = campi[1].toLowerCase();
                        if (nomeArea.contains(query.toLowerCase()) || stato.contains(query.toLowerCase())  ) {
                            risultati.add(riga);
                        }
                    }
                    if(permesso && !permesso2){

                        String[] parti = campi[2].split(",");
                        double latArea = Double.parseDouble(parti[0].trim());
                        double longArea = Double.parseDouble(parti[1].trim());
                        if (latArea  == latitudine &&  longArea == longitudine) {
                            risultati.add(riga);
                        }
                    }
                    if (permesso2) {
                        permesso=false;
                        String[] parti = campi[2].split(",");
                        double latArea = Double.parseDouble(parti[0].trim());
                        double longArea = Double.parseDouble(parti[1].trim());
                        if (latArea  >= latitudine- 0.5 &&  longArea >= longitudine- 0.5 && latArea  <= latitudine + 0.5 &&  longArea  <= longitudine + 0.5) {
                            risultati.add(riga);
                        }
                    }
                }
                permesso2=false;
                if (permesso && risultati.isEmpty()) {
                    permesso2=true;

                }

            } while (permesso2);

            if (risultati.isEmpty()) {
                System.out.println("area non presente nel sistema\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (permesso3) {
            sortByFirstFieldLength(risultati);
        }
        if (risultati.size()<=5) {
            for (String string : risultati) {
                System.out.println(string);
            }
        }
        else{
            for (int i = 0; i < 6; i++) {
                System.out.println(risultati.get(i));
            }
        }


        return risultati;
    }
    
    /** 
     * @param list
     * @return una lista di stringhe contenente i risultati della ricerca
     */
    public static List<String> sortByFirstFieldLength(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.split(";")[0].length(), s2.split(";")[0].length());
            }
        });
        return list;
    }
}


