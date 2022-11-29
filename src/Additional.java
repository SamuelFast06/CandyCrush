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
            }catch(Exception e){System.out.println("Gib eine Zahl ein!");input.nextLine();}
        }
        return cache;
    }

    public static char getSafeLetter(String nachricht){
        char cache = '#';
        while (!tryLetter(cache)) {

            if(tryLetter(cache)){
                System.out.println("Debug Info1");
            }else{
                System.out.println("Debug Info2");
            }
            try{
                System.out.print(nachricht);
                cache = input.next().charAt(0);
                cache = Character.toUpperCase(cache);
            }catch(Exception e){System.out.println("Gib einen Buchstaben ein!");input.nextLine();}
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

    public static char numberToLetter(int number){
        char letter;
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        letter = letters[number];

        return letter;
    }

    public static boolean tryLetter(char letter){
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for(int i = 0; i < letters.length; i++){
            System.out.println(letter + " " +  letters[i]);
            if(letter == letters[i]){
                return true;
            }
        }
        return false;
    }

    public static boolean tryLetterToNumber(char letter){
        letter = Character.toUpperCase(letter);
        int number;
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for(int i = 0; i < letters.length; i++){
            if(letter == letters[i]){
                return true;
            }
        }
        return false;
    }

    public static int letterToNumber(char letter){
        letter = Character.toUpperCase(letter);
        int number;
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for(int i = 0; i < letters.length; i++){
            if(letters[i] == letter){
                return letters[i];
            }
        }
        return 0;

        /*switch(buchstabe){
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
        }*/
    }

}

