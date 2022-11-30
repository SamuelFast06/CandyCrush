import java.util.*;
import java.io.*;

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
        while (!tryLetter(cache)){
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
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for(int i = 0; i < letters.length; i++){
            if(letters[i] == letter){
                return i;
            }
        }
        return 0;
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {}
    }

}

