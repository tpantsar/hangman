import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Sanalista {

    // Jäsenmuuttujat
    private List<String> sanat = new ArrayList<String>();
    private String tiedostonimi;

    // Konstruktori
    public Sanalista(String tiedostonimi) {

        List<String> sanat = new ArrayList<String>(); /* List-rakenne sanoille */

        // Sanat luetaan tiedostosta List -rakenteeseen.
        try (BufferedReader lukija = new BufferedReader(new FileReader(tiedostonimi))) {
            String rivi;
            while ((rivi = lukija.readLine()) != null) {
                sanat.add(rivi);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Virhe tiedoston avaamisessa!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Virhe tiedoston avaamisessa!");
            e.printStackTrace();
        }
        this.sanat = sanat;
        this.tiedostonimi = tiedostonimi;
    }


    // Palauttaa alkuperäisen sanalistan sanat.
    public List<String> annaSanat() {
        return sanat;
    }


    /* Palauttaa tekstitiedoston nimen, joka sisältää yhtä pitkät sanat,
     * kuin käyttäjän syöttämä merkkijono (metodista sanatJoidenPituusOn) */
    public String getTiedostonimi() {
        return tiedostonimi;
    }


    /* Tarkistaa, esiintyykö sanalistassa sanoja,
     * joiden pituus on parametrina annetun muuttujan arvon mukaisia.
     * Jos listassa esiintyy edes yksi täsmäävä sana, palautetaan true. Muutoin false. */
    public boolean tarkistaListanSanojenPituus(int pituus) {
        for (int i = 0; i < annaSanat().size(); i++) {
            if (annaSanat().get(i).length() == pituus) {
                return true;
            }
        }
        System.out.println("Sanalistassa ei ole yhtaan " + pituus + " merkin pituista sanaa.");
        return false;
    }


    /* Palauttaa uuden sanalista-olion, jossa on vain ne sanat,
     * joiden pituus on parametrina annetun muuttujan arvon mukaisia. */
    public Sanalista sanatJoidenPituusOn(int pituus) {
        
        List<String> uudetSanat = new ArrayList<String>(); /* List-rakenne sanoille */
        String tiedostonimi = "sanatJoidenPituusOn.txt";
        
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(tiedostonimi))) {
            /* Kirjoitetaan samanpituiset sanat uuteen listaan */
            for (int i = 0; i < annaSanat().size(); i++) {
                // Tarkistaa, täsmääkö parametrin "pituus" arvo sanan pituuteen (int)
                if (annaSanat().get(i).length() == pituus) {
                    uudetSanat.add(annaSanat().get(i));
                    outFile.write(annaSanat().get(i) + "\n"); /* Kirjoittaa sanan tiedostoon */
                }
            }
            System.out.println("Kirjoitettiin tiedostoon: " + tiedostonimi);
        } catch (IOException e) {
            System.out.println("Virhe tiedoston käsittelyssä!");
        }
        sanat = uudetSanat;
        this.tiedostonimi = tiedostonimi;
        return new Sanalista(tiedostonimi);
    }


    /* Palauttaa uuden sanalista-olion, jossa on vain ne sanat, joissa on merkit
     * parametrina annetun merkkijonon määräämissä kohdissa. Annettu merkkijono on muotoa
     * _a_e__ (esimerkiksi kameli, kaveri) missä alaviivat kuvaavat mitä tahansa merkkiä ja
     * kirjaimet merkkejä, joiden täytyy olla sanassa juuri annetulla paikalla. */
    public Sanalista sanatJoissaMerkit(String mjono) {

        List<String> uudetSanat = new ArrayList<String>(); /* List-rakenne sanoille */
        String tiedostonimi = "sanatJoissaMerkit.txt";

        // Kirjoittaa tiedostoon (sanatJoissaMerkit)
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(tiedostonimi))) {
            // Lukee tiedostosta (sanatJoidenPituusOn)
            try (BufferedReader teksti = new BufferedReader(new FileReader(getTiedostonimi()))) {
                String rivi;       
                while ((rivi = teksti.readLine()) != null) {
                    int validFlag = 1; /* Tarkistuslippu täsmääville sanoille */
                    int i = 0;
                    while (i < rivi.length() && validFlag == 1) {
                        if (mjono.length() != rivi.length()) {
                            validFlag = 0;
                        }
                        // Kirjoitetaan tiedostoon ne sanat, jotka täsmäävät merkkijonon paikkojen kanssa
                        if (Character.toLowerCase(mjono.charAt(i)) != '_') {
                            if (Character.toLowerCase(mjono.charAt(i)) != Character.toLowerCase(rivi.charAt(i))) {
                                validFlag = 0;
                            }
                        }
                        i++;
                    }
                    if (validFlag == 1) {
                        uudetSanat.add(rivi);
                        outFile.write(rivi + "\n"); /* Kirjoittaa sanan tiedostoon */
                    }
                } 
            } catch (FileNotFoundException e) {
                System.out.println("Virhe tiedoston käsittelyssä!");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Virhe tiedoston käsittelyssä!");
                e.printStackTrace();
            }
            System.out.println("Kirjoitettiin tiedostoon: " + tiedostonimi);
        } catch (IOException e) {
            System.out.println("Virhe tiedoston käsittelyssä!");
        }
        sanat = uudetSanat;
        this.tiedostonimi = tiedostonimi;
        return new Sanalista(tiedostonimi);
    }
}