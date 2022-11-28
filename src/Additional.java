import java.util.*;

public class Additional {

    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    public static int getSafeInteger(String nachricht){
        int cache = 0;
        while(cache == 0){
            try{
                System.out.print(nachricht);
                cache = input.nextInt();
            }catch(Exception e){System.out.println("Gib eine Zahl ein!");}
        }
        return cache;
    }

    public static String getSafeLetter(String nachricht){
        String cache = "";
        while(cache.length() != 1){
            try{
                System.out.print(nachricht);
                cache = input.nextLine();
            }catch(Exception e){System.out.println("Gib einen Text");}
            try{
                int zwischenspeicherInt = Integer.parseInt(cache);
                cache = "";
            }catch(Exception e){}
        }
        return cache;
    }

    static void printExample(){
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

    public static String numberToLetter(int zahl){
        String letter;

        switch(zahl){
            case 0: letter = "A";
                break;
            case 1: letter = "B";
                break;
            case 2: letter = "C";
                break;
            case 3: letter = "D";
                break;
            case 4: letter = "E";
                break;
            case 5: letter = "F";
                break;
            case 6: letter = "G";
                break;
            case 7: letter = "H";
                break;
            case 8: letter = "I";
                break;
            case 9: letter = "J";
                break;
            case 10: letter = "K";
                break;
            case 11: letter = "L";
                break;
            case 12: letter = "M";
                break;
            case 13: letter = "N";
                break;
            case 14: letter = "O";
                break;
            case 15: letter = "P";
                break;
            case 16: letter = "Q";
                break;
            case 17: letter = "R";
                break;
            case 18: letter = "S";
                break;
            case 19: letter = "T";
                break;
            case 20: letter = "U";
                break;
            case 21: letter = "V";
                break;
            case 22: letter = "W";
                break;
            case 23: letter = "X";
                break;
            case 24: letter = "Y";
                break;
            case 25: letter = "Z";
                break;
            default:
                letter = "";
                System.out.println("Ein Fehler ist passiert");
                break;
        }
        return letter;
    }

    public static int letterToNumber(String buchstabe){
        int number;

        switch(buchstabe){
            case "A": number = 0;
                break;
            case "B": number = 1;
                break;
            case "C": number = 2;
                break;
            case "D": number = 3;
                break;
            case "E": number = 4;
                break;
            case "F": number = 5;
                break;
            case "G": number = 6;
                break;
            case "H": number = 7;
                break;
            case "I": number = 8;
                break;
            case "J": number = 9;
                break;
            case "K": number = 10;
                break;
            case "L": number = 11;
                break;
            case "M": number = 12;
                break;
            case "N": number = 13;
                break;
            case "O": number = 14;
                break;
            case "P": number = 15;
                break;
            case "Q": number = 16;
                break;
            case "R": number = 17;
                break;
            case "S": number = 18;
                break;
            case "T": number = 19;
                break;
            case "U": number = 20;
                break;
            case "V": number = 21;
                break;
            case "W": number = 22;
                break;
            case "X": number = 23;
                break;
            case "Y": number = 24;
                break;
            case "Z": number = 25;
                break;

            case "a": number = 0;
                break;
            case "b": number = 1;
                break;
            case "c": number = 2;
                break;
            case "d": number = 3;
                break;
            case "e": number = 4;
                break;
            case "f": number = 5;
                break;
            case "g": number = 6;
                break;
            case "h": number = 7;
                break;
            case "i": number = 8;
                break;
            case "j": number = 9;
                break;
            case "k": number = 10;
                break;
            case "l": number = 11;
                break;
            case "m": number = 12;
                break;
            case "n": number = 13;
                break;
            case "o": number = 14;
                break;
            case "p": number = 15;
                break;
            case "q": number = 16;
                break;
            case "r": number = 17;
                break;
            case "s": number = 18;
                break;
            case "t": number = 19;
                break;
            case "u": number = 20;
                break;
            case "v": number = 21;
                break;
            case "w": number = 22;
                break;
            case "x": number = 23;
                break;
            case "y": number = 24;
                break;
            case "z": number = 25;
                break;
            default:
                number = 0;
                System.out.println("Ein Fehler ist passiert");
                break;
        }
        return number;
    }

}

