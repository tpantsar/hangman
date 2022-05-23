import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hirsipuu {
    
    // Jäsenmuuttujat
    private int arvauksetLkm = 0;
    private String peittelematonSana;
    private String peiteltySana;
    private List<Character> arvauksetLista = new ArrayList<>();

    // Konstruktori
    public Hirsipuu(int arvauksetLkm, List<String> sanat) {
        
        this.arvauksetLkm = arvauksetLkm;
        Random rand = new Random();

        // Valitsee satunnaisen sanan tekstitiedostosta
        String peittelematonSana = sanat.get(rand.nextInt(sanat.size()));
        this.peittelematonSana = peittelematonSana;

        // Korvaa arvattavan sanan kaikki kirjaimet alaviivoilla
        this.peiteltySana = peittelematonSana.replaceAll(".", "_");
    }

    // Palauttaa jäljellä olevien arvausten määrän.
    public int arvauksiaOnJaljella() {
        return arvauksetLkm;
    }

    // Palauttaa tehdyt arvaukset lista-oliona.
    public List<Character> arvauksetLista() {
        return arvauksetLista;
    }

    // Palauttaa arvattavan sanan peittelemättömänä.
    public String getPeittelematonSana() {
        return peittelematonSana;
    }

    // Palauttaa arvattavan sanan peiteltynä.
    public String getPeiteltySana() {
        return peiteltySana;
    }
    
    public void setPeiteltySana(String peiteltySana) {
        this.peiteltySana = peiteltySana;
    }

    /* Vertaa parametrina annettua merkkiä arvattavaan sanaan ja lisää arvauksen arvauslistalle.
     * Jos merkki löytyy arvattavasta sanasta, palautetaan arvo true.
     * Jos merkkiä ei löydy, vähennetään arvausten määrää yhdellä ja palautetaan false. */
    public boolean arvaa(Character ch) {

        // Tarkistaa, onko syötetty merkki jo entuudestaan arvauslistassa
        for (int i = 0; i < arvauksetLista().size(); i++) {
            if (ch == arvauksetLista().get(i)) {
                System.out.println("Olet jo syottanyt kirjaimen: " + ch + "\n");
                return false;
            }
        }
        // Lisää arvauksen arvauslistalle
        arvauksetLista().add(ch);

        // Tarkistaa, täsmääkö syötetty merkki peittelemättömään sanaan
        for (int i = 0; i < getPeittelematonSana().length(); i++) {
            if (Character.toLowerCase(ch) == getPeittelematonSana().toLowerCase().charAt(i)) {
                return true;
            }
        }
        arvauksetLkm--;
        return false;
    }

    // Korvaa peitellystä sanasta alaviivat käyttäjän oikein arvaamaan kirjaimeen
    public void lisaaMerkkiPeiteltyynSanaan (Character ch) {
        for (int i = 0; i < getPeittelematonSana().length(); i++) {
            if (Character.toLowerCase(ch) == getPeittelematonSana().toLowerCase().charAt(i)) {
                String string = korvaaMerkki(getPeiteltySana(), ch, i);
                setPeiteltySana(string);
            }
        }
    }

    /* Tulostaa pelaajalle peitellyn sanan, joka sisältää arvatut kirjaimet.
     * Esimerkiksi: p_og__mm_ng (programming) */
    public void tulostaPelinTilanne() {

        System.out.println("\n********************");
        System.out.println("________");
        System.out.println("|      |");
        
        if (arvauksiaOnJaljella() < 6) {
            System.out.println("       O");
        }
        if (arvauksiaOnJaljella() == 4 && arvauksiaOnJaljella() != 3) {
            System.out.println("      \\");
        }
        if (arvauksiaOnJaljella() < 4) {
            System.out.println("      \\ /");
        }
        if (arvauksiaOnJaljella() < 3) {
            System.out.println("       |");
        }
        if (arvauksiaOnJaljella() == 1 && arvauksiaOnJaljella() != 0) {
            System.out.println("      /");
        }
        if (arvauksiaOnJaljella() < 1) {
            System.out.println("      / \\");
        }

        // Tulostaa jäljellä olevien arvausten lukumäärän
        System.out.println("\nArvauksia jaljella: " + arvauksiaOnJaljella());

        // Tulostaa peitellyn sanan alaviivojen kanssa
        System.out.println("Peitelty sana: " + getPeiteltySana());

        // Tulostaa ne kirjaimet, joita pelaaja on jo arvannut
        System.out.print("\nArvaamasi kirjaimet: ");
        for (int i = 0; i < arvauksetLista().size(); i++) {
            System.out.print(arvauksetLista().get(i));
            if (i != (arvauksetLista().size() - 1)) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");
    }

    // Korvaa merkkijonosta kirjaimen annetussa indeksissä ja palauttaa sen.
    public String korvaaMerkki(String str, char ch, int index) {
        StringBuilder string = new StringBuilder(str);
        string.setCharAt(index, ch);
        return string.toString();
    }

    /* Tarkistaa, onko peli loppunut vai ei.
     * Peli loppuu, jos arvattavan sanan kaikki merkit on arvattu.
     * Toisinsanoen, jos arvattava sana ei sisällä lainkaan alaviivoja. */
    public boolean onLoppu() {
        for (int i = 0; i < getPeiteltySana().length(); i++) {
            if (getPeiteltySana().charAt(i) == '_') {
                return false; // Peli jatkuu (merkkejä ei ole arvattu)
            }
        }
        return true; // Peli päättyy (merkit on arvattu)
    }

    
    // Tulostaa lopputuloksen sen mukaan, voittiko pelaaja vai tietokone pelin.
    public void tulostaLoppuTulos() {
        if (arvauksiaOnJaljella() > 0) {
            tulostaPelinTilanne();
            System.out.println("\nVoitit pelin! :)");
        }
        else {
            tulostaPelinTilanne();
            System.out.println("Oikea sana oli: " + getPeittelematonSana());
            System.out.println("\nHavisit pelin! :(");
        }
    }
}
