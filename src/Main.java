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
        int zuege ;
        int[] feldZuBewegen1 = new int[2];
        int[] feldZuBewegen2 = new int[2];
        int zwischenspeicher = 0;

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
        //Beispiele ausgeben
        beispieleDrucken();

        //Die Reihen des Spielfeldes bestimmen
        spielfeld = new Objekt[sichernenIntegerInput("Gib die Reihen des Spielfeldes ein: ")][];

        //Die Spalten des Spielfeldes festlegen
        do{
            spielfeld[0] = new Objekt[sichernenIntegerInput("Gib die Spalten des Spielfeldes ein: ")];
            if(spielfeld[0].length > 26){
                System.out.println("Gib eine Zahl ein, die kleiner als 27 ist!");
            }
        }while (spielfeld[0].length > 26);

        //Das gesamte Spielfeld initialisieren
        for(int i = 1; i < spielfeld.length; i++){
            spielfeld[i] = new Objekt[spielfeld[0].length];
        }

        zuege = sichernenIntegerInput("Gib ein, wie viele Züge du haben möchtest: ");

        //Spielfeld initialisieren
        spielfeld = randomSpielfeldGenerieren(spielfeld, objekte);

        //Hauptschleife solane der Benutzer noch Züge hat
        while(zuege > 0){
            //Übriegen Züge und Punkte ausgeben
            System.out.print(zuege + " Züge übrig");
            System.out.println("  " + punkte + "P");
            spielfeldDrucken(spielfeld);

            //Zwei felder zu bewegen vom Benutzer erhalten
            do{
                feldZuBewegen1 = bewegungAbfragen1(spielfeld);
                feldZuBewegen2 = bewegungAbfragen2(spielfeld);
            }while(!bewegungsmoeglichkeitTesten(spielfeld, feldZuBewegen1, feldZuBewegen2));

            spielfeld = zugDurchfuehren(spielfeld, objekte, feldZuBewegen1, feldZuBewegen2);
            felderSenkenUndAusfuellen(spielfeld, objekte);

            zuege--;


        }
    }

    static int sichernenIntegerInput(String nachricht){
        int zwischenspeicher = 0;
        while(zwischenspeicher == 0){
            try{
                System.out.print(nachricht);
                zwischenspeicher = input.nextInt();
            }catch(Exception e){System.out.println("Gib eine Zahl ein!"); input.nextLine();}
        }
        return zwischenspeicher;
    }

    static String sicherenBuchstabenInput(String nachricht){
        String zwischenspeicher = "";
        while(zwischenspeicher.length() != 1){
            try{
                System.out.print(nachricht);
                zwischenspeicher = input.nextLine();
            }catch(Exception e){System.out.println("Gib einen Text"); input.nextLine();}
            try{
                int zwischenspeicherInt = Integer.parseInt(zwischenspeicher);
                zwischenspeicher = "";
            }catch(Exception e){}
        }
        return zwischenspeicher;
    }

    static int[] bewegungAbfragen1(Objekt[][] feld){
        int[] feldZuBewegen1 = new int[2];
        do{
            feldZuBewegen1[0] = buchstabeZuZahl(sicherenBuchstabenInput("Welches Feld möchtest du bewegen? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen1[0] > feld[0].length);
        do{
            feldZuBewegen1[1] = sichernenIntegerInput("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen1[0] > feld.length);

        return feldZuBewegen1;
    }

    static  int[] bewegungAbfragen2(Objekt[][] feld){
        int[] feldZuBewegen2 = new int[2];
        do{
            feldZuBewegen2[0] = buchstabeZuZahl(sicherenBuchstabenInput("Mit welchem Feld soll getauscht werden? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen2[0] > feld[0].length);
        do{
            feldZuBewegen2[1] = sichernenIntegerInput("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen2[0] > feld.length);

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

    static boolean mindestensDreierReihe(Objekt[][] feld){
        //Nach oben, unten, rechts, links, mittig waagerecht, mittig senkrecht überprüfen, ob es eine dreierreihe ergibt, wenn die Werte über 2 sind
        for(int i = 0; i < feld.length; i++) {
            for (int z = 0; z < feld[0].length; z++) {
                try{
                    if(feld[i][z].id == feld[i - 1][z].id && feld[i][z].id == feld[i - 2][z].id) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(feld[i][z].id == feld[i][z - 1].id && feld[i][z].id == feld[i][z - 2].id) {
                        return true;
                    }
                }catch (Exception e){}
            }
        }

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
        if(mindestensDreierReihe(feld)){
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

    public class FelderTesten{

        static boolean nachObenTesten(Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i - 1][z].id && feld[i][z].id == feld[i - 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachUntenTesten(Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachRechtsTesten(Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i][z - 1].id && feld[i][z].id == feld[i][z - 2].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachLinksTesten(Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static int anzahlFelder(Objekt[][] feld, int i, int z){
            if(nachUntenTesten(feld, i, z)){
                try{
                    if(feld[i][z].id == feld[i + 3][z].id){
                        try{
                            if(feld[i][z].id == feld[i + 4][z].id){
                                return 5;
                            }
                        }catch (Exception e){}
                        return 4;
                    }
                }catch (Exception e){}
            }
            if(nachRechtsTesten(feld, i, z)){
                try{
                    if(feld[i][z].id == feld[i][z + 3].id){
                        try{
                            if(feld[i][z].id == feld[i][z + 4].id){
                                return 5;
                            }
                        }catch (Exception e){}
                        return 4;
                    }
                }catch (Exception e){}
            }
            return 3;
        }
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

                                        try{
                                            if(feld[i + 2][z].id == feld[i + 2][z + 1].id){
                                                feld[i + 2][z + 1] = lueckenfueller;
                                                if(feld[i + 2][z].id == feld[i + 2][z + 2].id){
                                                    feld[i + 2][z + 2] = lueckenfueller;
                                                }
                                            }
                                        }catch (Exception e){}

                                        try{
                                            if(feld[i + 2][z].id == feld[i + 2][z - 1].id){
                                                feld[i + 2][z - 1] = lueckenfueller;
                                                if(feld[i + 2][z].id == feld[i + 2][z - 2].id){
                                                    feld[i + 2][z - 2] = lueckenfueller;
                                                }
                                            }
                                        }catch (Exception e){}

                                        feld[i + 4][z] = lueckenfueller;
                                    }
                                }
                            }
                        }
                        try{
                            if(feld[i][z].id == feld[i][z + 1].id){
                                feld[i][z + 1] = lueckenfueller;
                                if(feld[i][z].id == feld[i][z + 2].id){
                                    feld[i][z + 2] = lueckenfueller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(feld[i][z].id == feld[i][z - 1].id){
                                feld[i][z - 1] = lueckenfueller;
                                if(feld[i][z].id == feld[i][z - 2].id){
                                    feld[i][z - 2] = lueckenfueller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(feld[i][z].id == feld[i][z + 1].id){
                                feld[i][z + 1] = lueckenfueller;
                                if(feld[i][z].id == feld[i][z + 2].id){
                                    feld[i][z + 2] = lueckenfueller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(feld[i][z].id == feld[i][z - 1].id){
                                feld[i][z - 1] = lueckenfueller;
                                if(feld[i][z].id == feld[i][z - 2].id){
                                    feld[i][z - 2] = lueckenfueller;
                                }
                            }
                        }catch (Exception e){}
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


    static Objekt[][] zugDurchfuehren(Objekt[][] feld, Objekt[] objekte, int[] feldTauschen1, int[] feldTauschen2){
        spielfeldAktualisieren(feld, feldTauschen1, feldTauschen2);
        while(mindestensDreierReihe(feld)){
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

    static void beispieleDrucken(){
        System.out.println("Beispiele für Kombinationsmöglichkeiten. Es geht immer bis maximal eine Länge von 5.");
        System.out.println(" O  O  O");
        System.out.println();
        System.out.println(" O");
        System.out.println(" O");
        System.out.println(" O");
        System.out.println();
        System.out.println(" O");
        System.out.println(" O  O  O");
        System.out.println(" O");
        System.out.println();
        System.out.println("       O");
        System.out.println(" O  O  O  O  O");
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

            case "a": zahl = 0;
                break;
            case "b": zahl = 1;
                break;
            case "c": zahl = 2;
                break;
            case "d": zahl = 3;
                break;
            case "e": zahl = 4;
                break;
            case "f": zahl = 5;
                break;
            case "g": zahl = 6;
                break;
            case "h": zahl = 7;
                break;
            case "i": zahl = 8;
                break;
            case "j": zahl = 9;
                break;
            case "k": zahl = 10;
                break;
            case "l": zahl = 11;
                break;
            case "m": zahl = 12;
                break;
            case "n": zahl = 13;
                break;
            case "o": zahl = 14;
                break;
            case "p": zahl = 15;
                break;
            case "q": zahl = 16;
                break;
            case "r": zahl = 17;
                break;
            case "s": zahl = 18;
                break;
            case "t": zahl = 19;
                break;
            case "u": zahl = 20;
                break;
            case "v": zahl = 21;
                break;
            case "w": zahl = 22;
                break;
            case "x": zahl = 23;
                break;
            case "y": zahl = 24;
                break;
            case "z": zahl = 25;
                break;
            default:
                zahl = 0;
                System.out.println("Ein Fehler ist passiert");
                break;
        }
        return zahl;
    }

}