import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    static Random zufall = new Random();
    static int punkte = 0;

    class Farben{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";
    }

    static class Objekt{

        //Id für jedes einzelne objekt
        int id;
        String text;

        //Alle Objekte anlegen
        public static String rot = Farben.ANSI_RED + "O" + Farben.ANSI_RESET;
        public static String blau = Farben.ANSI_BLUE + "O" + Farben.ANSI_RESET;
        public static String gruen = Farben.ANSI_GREEN + "O" + Farben.ANSI_RESET;
        public static String lila = Farben.ANSI_PURPLE + "O" + Farben.ANSI_RESET;
        public static String gelb = Farben.ANSI_YELLOW + "O" + Farben.ANSI_RESET;
    }

    public static void main(String[] args) {
        Objekt[][] spielfeld;
        Objekt[] objekte = new Objekt[5];
        int zuege = 2;
        int[] feldZuBewegen1 = new int[2];
        int[] feldZuBewegen2 = new int[2];

        //Objekte initialisieren
        for(int i = 0; i < objekte.length; i++){
            objekte[i] = new Objekt();
            objekte[i].id = i + 1;
            switch(i){
                case 0:
                    objekte[i].text = Objekt.rot;
                    break;
                case 1:
                    objekte[i].text = Objekt.blau;
                    break;
                case 2:
                    objekte[i].text = Objekt.gruen;
                    break;
                case 3:
                    objekte[i].text = Objekt.lila;
                    break;
                case 4:
                    objekte[i].text = Objekt.gelb;
                    break;
                default:
                    break;
            }
        }

        System.out.print("Reihen des Spielfeldes: ");
        spielfeld = new Objekt[input.nextInt()][];

        System.out.print("Spalten des Spielfeldes: ");
        spielfeld[0] = new Objekt[input.nextInt()];
        for(int i = 1; i < spielfeld.length; i++){
            spielfeld[i] = new Objekt[spielfeld[0].length];
        }
        System.out.print("Wie viele Züge möchtest du haben?: ");
        try{
            zuege = input.nextInt();
        }catch (Exception e){System.out.println("Gib eine Ganzzahl ein!");}

        //Spielfeld initialisieren
        spielfeld = randomSpielfeldGenerieren(spielfeld, objekte);

        //Hauptschleife solane der Benutzer noch Züge hat
        while(zuege > 0){
            System.out.print(zuege + " Züge übrig");
            System.out.println("  " + punkte + "P");
            spielfeldDrucken(spielfeld);

            do{
                feldZuBewegen1 = bewegungAbfragen1();
                feldZuBewegen2 = bewegungAbfragen2();
            }while(!bewegungsmoeglichkeitTesten(spielfeld, feldZuBewegen1, feldZuBewegen2));

            spielfeld = zugDurchfuehren(spielfeld, objekte, feldZuBewegen1, feldZuBewegen2);
            felderSenkenUndAusfuellen(spielfeld, objekte);

            zuege--;


        }
    }

    static int[] bewegungAbfragen1(){
        int[] feldZuBewegen1 = new int[2];

        System.out.print("Welches Feld möchtest du bewegen? Gib als erstes den Buchstaben ein: ");
        feldZuBewegen1[0] = buchstabeZuZahl(input.next());
        System.out.print("Gib jetzt die Zahl ein: ");
        feldZuBewegen1[1] = input.nextInt() - 1;

        return feldZuBewegen1;

    }

    static  int[] bewegungAbfragen2(){
        int[] feldZuBewegen2 = new int[2];

        System.out.print("Mit welchem Feld soll getauscht werden? Gib als erstes den Buchstaben ein: ");
        feldZuBewegen2[0] = buchstabeZuZahl(input.next());
        System.out.print("Gib jetzt die Zahl ein: ");
        feldZuBewegen2[1] = input.nextInt() - 1;

        return feldZuBewegen2;
    }

    static boolean testenObNebeneinander(int[] feldTauschen1, int[] feldtauschen2){
        try{
          if(feldTauschen1[1] == (feldtauschen2[1] - 1) || feldTauschen1[1] == (feldtauschen2[1] + 1) || feldTauschen1[0] == (feldtauschen2[0] - 1) || feldTauschen1[0] == (feldtauschen2[0] + 1)){
              return true;
          }else{
              return false;
          }
        }catch (Exception e){System.out.println("Fehler");}

        return false;
    }

    static boolean mindestensDreierReihe(Objekt[][] feld, int[] feldTauschen){
        //Nach oben, unten, rechts, links, mittig waagerecht, mittig senkrecht überprüfen, ob es eine dreierreihe ergibt, wenn die Werte über 2 sind
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] - 1][feldTauschen[0]].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] - 2][feldTauschen[0]].id) {
                return true;
            }
        }catch (Exception e){}
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] + 1][feldTauschen[0]].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] + 2][feldTauschen[0]].id) {
                return true;
            }
        }catch (Exception e){}
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] + 1].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] + 2].id) {
                return true;
            }
        }catch (Exception e){}
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] - 1].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] - 2].id) {
                return true;
            }
        }catch (Exception e){}
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] - 1][feldTauschen[0]].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1] + 1][feldTauschen[0]].id) {
                return true;
            }
        }catch (Exception e){}
        try{
            if(feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] - 1].id && feld[feldTauschen[1]][feldTauschen[0]].id == feld[feldTauschen[1]][feldTauschen[0] + 1].id) {
                return true;
            }
        }catch (Exception e){}

        return false;
    }

    static boolean bewegungsmoeglichkeitTesten(Objekt[][] feld, int[] feldTauschen1, int[] feldTauscehn2){
        Objekt zwischenspeicher;

        if(!testenObNebeneinander(feldTauschen1, feldTauscehn2)) {
            System.out.println("Die Felder liegen nicht nebeneinander");
            return false;
        }
        zwischenspeicher = feld[feldTauschen1[1]][feldTauschen1[0]];
        feld[feldTauschen1[1]][feldTauschen1[0]] = feld[feldTauscehn2[1]][feldTauscehn2[0]];
        feld[feldTauscehn2[1]][feldTauscehn2[0]] = zwischenspeicher;
        if(mindestensDreierReihe(feld, feldTauschen1) || mindestensDreierReihe(feld, feldTauscehn2)){
            zwischenspeicher = feld[feldTauschen1[1]][feldTauschen1[0]];
            feld[feldTauschen1[1]][feldTauschen1[0]] = feld[feldTauscehn2[1]][feldTauscehn2[0]];
            feld[feldTauscehn2[1]][feldTauscehn2[0]] = zwischenspeicher;
            return true;
        }
        zwischenspeicher = feld[feldTauschen1[1]][feldTauschen1[0]];
        feld[feldTauschen1[1]][feldTauschen1[0]] = feld[feldTauscehn2[1]][feldTauscehn2[0]];
        feld[feldTauscehn2[1]][feldTauscehn2[0]] = zwischenspeicher;
        System.out.println("Die Felder ergeben keine Dreierreihe");
        return false;
    }

    static Objekt[][] felderEntfernen(Objekt[][] feld){
        Objekt lueckenfueller = new Objekt();
        lueckenfueller.text = " ";
        lueckenfueller.id = -1;

        for(int i = 0; i < feld.length; i++){
            for(int z = 0; z < feld[0].length; z++){
                try{
                    if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id){
                        if(i < feld.length - 4){
                            if(feld[i][z].id == feld[i + 3][z].id) {
                                feld[i + 3][z] = lueckenfueller;
                                if(i < feld.length - 5) {
                                    if (feld[i][z].id == feld[i + 4][z].id) {
                                        feld[i + 4][z] = lueckenfueller;
                                    }
                                }
                            }
                        }
                        feld[i][z] = lueckenfueller;
                        feld[i + 1][z] = lueckenfueller;
                        feld[i + 2][z] = lueckenfueller;
                    }
                }catch (Exception e){}
                try{
                    if(feld[i][z].id == feld[i][z + 1].id && feld[i][z].id == feld[i][z + 2].id){
                        if(z < feld[i].length - 4){
                            if(feld[i][z].id == feld[i][z + 3].id) {
                                feld[i][z + 3] = lueckenfueller;
                                if(z < feld[i].length - 5){
                                    if(feld[i][z].id == feld[i][z + 4].id){
                                        feld[i][z + 4] = lueckenfueller;
                                    }
                                }
                            }
                        }
                        feld[i][z] = lueckenfueller;
                        feld[i][z + 1] = lueckenfueller;
                        feld[i][z + 2] = lueckenfueller;
                    }
                }catch (Exception e){}
            }
        }

        return feld;
    }

    static Objekt[][] felderSenkenUndAusfuellen(Objekt[][] feld, Objekt[] objekte){
        Objekt lueckenfueller = new Objekt();
        lueckenfueller.text = " ";
        lueckenfueller.id = -1;

        //Felder senken
        for(int i = 0; i < feld.length; i++) {
            for (int z = 0; z < feld[0].length; z++) {
                if(feld[i][z].id == -1){
                    for(int g = i; g >= 1; g--){
                        feld[g][z] = feld[g - 1][z];
                        feld[g - 1][z] = lueckenfueller;
                    }
                }
            }
        }

        //Felder oben auffüllen
        for(int i = 0; i < feld.length; i++) {
            for (int z = 0; z < feld[0].length; z++) {
                if(feld[i][z].id == -1){
                    // Punkte hinzufügen
                    punkte++;

                    feld[i][z] = objekte[zufall.nextInt(5)];
                }
            }
        }

        return feld;
    }

    static boolean weitereZuegeMoeglich(Objekt[][] feld){
        for(int i = 0; i < feld.length; i++) {
            for (int z = 0; z < feld[0].length; z++) {
                try{
                    if(feld[i][z].id == feld[i - 1][z].id && feld[i][z].id == feld[i - 2][z].id) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(feld[i][z].id == feld[i][z - 1].id && feld[i][z].id == feld[i][z - 2].id) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                        return true;
                    }
                }catch (Exception e){}
            }
        }

        return false;
    }

    static Objekt[][] zugDurchfuehren(Objekt[][] feld, Objekt[] objekte, int[] feldTauschen1, int[] feldTauschen2){
        spielfeldAktualisieren(feld, feldTauschen1, feldTauschen2);
        while(weitereZuegeMoeglich(feld)){
            feld = felderEntfernen(feld);
            feld = felderSenkenUndAusfuellen(feld, objekte);
            spielfeldDrucken(feld);
        }
        return feld;
    }

    static Objekt[][] spielfeldAktualisieren(Objekt[][] feld, int[] feldTauschen1, int[] feldTauschen2){
        Objekt zwischenspeicher;
        zwischenspeicher = feld[feldTauschen1[1]][feldTauschen1[0]];
        feld[feldTauschen1[1]][feldTauschen1[0]] = feld[feldTauschen2[1]][feldTauschen2[0]];
        feld[feldTauschen2[1]][feldTauschen2[0]] = zwischenspeicher;
        return feld;
    }

    static Objekt[][] randomSpielfeldGenerieren(Objekt[][] feld, Objekt[] objekte){
        int zufallsZahl = zufall.nextInt(4);

        for(int i = 0; i < feld.length; i++){
            for(int z = 0; z < feld[i].length; z++){

                //objekt zum testen erstellen
                feld[i][z] = objekte[zufall.nextInt(5)];

                //Wenn der i oder z wert über 2 ist, muss überprüft werden ob das objekt platziert werden kann
                if(i >= 2 && z >= 2){
                    while((feld[i][z].id == feld[i - 2][z].id && feld[i][z].id == feld[i - 1][z].id) || (feld[i][z].id == feld[i][z - 2].id && feld[i][z].id == feld[i][z - 1].id)){
                        feld[i][z] = objekte[zufall.nextInt(5)];
                    }
                }

                //Wenn der i Wert über 1 ist und der z Wert kleiner als 2 ist soll überprüft werden, ob das Objekt plaziert werden darf
                if(i > 1 && z < 2){
                    while(feld[i][z].id == feld[i - 2][z].id && feld[i][z].id == feld[i - 1][z].id){
                        feld[i][z] = objekte[zufall.nextInt(5)];
                    }
                }

                //Wenn der z Wert über 1 ist und der i Wert kleiner als 2 ist soll überprüft werden, ob das Objekt plaziert werden darf
                if(z > 1 && i < 2){
                    while(feld[i][z].id == feld[i][z - 2].id && feld[i][z].id == feld[i][z - 1].id){
                        feld[i][z] = objekte[zufall.nextInt(5)];
                    }
                }
                zufallsZahl = zufall.nextInt(5);
            }
        }

        return feld;
    }

    static void spielfeldDrucken(Objekt[][] feld){

        System.out.print("   ");
        for(int i = 0; i < feld[0].length; i++){
            System.out.print(zahlZuBuchstabe(i));
            System.out.print("  ");
        }

        System.out.println();

        for(int i = 0; i < feld.length; i++){
            System.out.print(i + 1);
            if(i < 9){
                System.out.print(" ");
            }
            for(int z = 0; z < feld[i].length; z++){
                System.out.print(" " + feld[i][z].text + " ");
            }
            System.out.println();
        }
    }

    static String zahlZuBuchstabe(int zahl){
        String buchstabe;

        switch(zahl){
            case 0: buchstabe = "A";
                break;
            case 1: buchstabe = "B";
                break;
            case 2: buchstabe = "C";
                break;
            case 3: buchstabe = "D";
                break;
            case 4: buchstabe = "E";
                break;
            case 5: buchstabe = "F";
                break;
            case 6: buchstabe = "G";
                break;
             case 7: buchstabe = "H";
                 break;
             case 8: buchstabe = "I";
                 break;
             case 9: buchstabe = "J";
                 break;
             case 10: buchstabe = "K";
                 break;
             case 11: buchstabe = "L";
                 break;
             case 12: buchstabe = "M";
                 break;
             case 13: buchstabe = "N";
                 break;
             case 14: buchstabe = "O";
                 break;
             case 15: buchstabe = "P";
                 break;
             case 16: buchstabe = "Q";
                 break;
             case 17: buchstabe = "R";
                 break;
             case 18: buchstabe = "S";
                 break;
             case 19: buchstabe = "T";
                 break;
             case 20: buchstabe = "U";
                 break;
             case 21: buchstabe = "V";
                 break;
             case 22: buchstabe = "W";
                 break;
             case 23: buchstabe = "X";
                 break;
             case 24: buchstabe = "Y";
                 break;
             case 25: buchstabe = "Z";
                 break;
             default:
                 buchstabe = "";
                 System.out.println("Ein Fehler ist passiert");
                 break;
        }
        return buchstabe;
    }

    static int buchstabeZuZahl(String buchstabe){
        int zahl;

        switch(buchstabe){
            case "A": zahl = 0;
                break;
            case "B": zahl = 1;
                break;
            case "C": zahl = 2;
                break;
            case "D": zahl = 3;
                break;
            case "E": zahl = 4;
                break;
            case "F": zahl = 5;
                break;
            case "G": zahl = 6;
                break;
            case "H": zahl = 7;
                break;
            case "I": zahl = 8;
                break;
            case "J": zahl = 9;
                break;
            case "K": zahl = 10;
                break;
            case "L": zahl = 11;
                break;
            case "M": zahl = 12;
                break;
            case "N": zahl = 13;
                break;
            case "O": zahl = 14;
                break;
            case "P": zahl = 15;
                break;
            case "Q": zahl = 16;
                break;
            case "R": zahl = 17;
                break;
            case "S": zahl = 18;
                break;
            case "T": zahl = 19;
                break;
            case "U": zahl = 20;
                break;
            case "V": zahl = 21;
                break;
            case "W": zahl = 22;
                break;
            case "X": zahl = 23;
                break;
            case "Y": zahl = 24;
                break;
            case "Z": zahl = 25;
                break;
            default:
                zahl = 0;
                System.out.println("Ein Fehler ist passiert");
                break;
        }
        return zahl;
    }

}