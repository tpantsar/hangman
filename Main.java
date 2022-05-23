import java.util.Scanner;

public class Main {

    public static void main(String [] args) throws Exception {
        
        Scanner nappaimisto = new Scanner(System.in);
        Character ch;
        int valinta = 0, sananPituus = 0;
        String merkkijono;

        // Pelaajan arvausten lukumäärä
        final int ARVAUKSET = 6;

        // Tekstitiedosto, joka sisältää arvattavat sanat
        String tiedostonimi = "sanatiedosto.txt";

        // Sanalistan konstruktori saa parametrina sanoja sisältävän tekstitiedoston nimen
        Sanalista s = new Sanalista(tiedostonimi);

        System.out.println("\n| Tervetuloa Hirsipuu-peliin! |");
        System.out.println("Pelaa normaaleilla asetuksilla (1)");
        System.out.println("Pelaa valitsemasi pituisilla sanoilla (2)");
        System.out.println("Pelaa valitsemasi merkkijonon mukaisilla sanoilla (3)");

        do {
            System.out.print("\nValitse: [1, 2, 3] > ");
            valinta = nappaimisto.nextInt();

            if (valinta == 2) {
                do {
                    System.out.print("Syota sanan pituus > ");
                    sananPituus = nappaimisto.nextInt();
                } while (s.tarkistaListanSanojenPituus(sananPituus) == false);
                s.sanatJoidenPituusOn(sananPituus);
            }
            if (valinta == 3) {
                nappaimisto.nextLine(); // Puskurin tyhjennys
                System.out.println("\nVoit syottaa esim: _a_e__ (kameli, kaveri)");
                do {
                    System.out.print("Syota merkkijono > ");
                    merkkijono = nappaimisto.nextLine();
                } while (s.tarkistaListanSanojenPituus(merkkijono.length()) == false);           
                s.sanatJoissaMerkit(merkkijono);
            }
        } while (valinta < 1 || valinta > 3);

        // Hirsipuun konstruktori saa parametrinaan arvausten määrän ja sanalistan
        Hirsipuu hirsi = new Hirsipuu(ARVAUKSET, s.annaSanat());

        while (hirsi.arvauksiaOnJaljella() > 0) {
            hirsi.tulostaPelinTilanne();
            System.out.print("Syota kirjain > ");
            ch = nappaimisto.next().charAt(0);
            if (hirsi.arvaa(ch)) { // Jos merkki täsmää arvattavaan sanaan
                hirsi.lisaaMerkkiPeiteltyynSanaan(ch);
            }
            if (hirsi.onLoppu()) {
                break;
            }
        }
        nappaimisto.close();

        // Tulostaa lopputuloksen sen mukaan, voittiko pelaaja vai tietokone pelin.
        hirsi.tulostaLoppuTulos();
    }
}