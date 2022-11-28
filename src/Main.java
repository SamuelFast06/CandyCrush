import java.util.*;

public class Main {
    static int punkte = 0;

    public static void main(String[] args) {
        GameField gamefield = new GameField();
        //Objekt[] objekte = new Objekt[5];
        int moves;
        int[] swapField1 = new int[2];
        int[] swapField2 = new int[2];
        int zwischenspeicher = 0;

        //Objekte initialisieren
        gamefield.initalizeTokens(5);

        //Beispiele ausgeben
        Additional.printExample();

        //Die Reihen des Spielfeldes bestimmen
        gamefield.setRows();

        //Die Spalten des Spielfeldes festlegen
        do {
            gamefield.setCollums();
            if (gamefield.getGamefield()[0].length > 26) {
                System.out.println("Gib eine Zahl ein, die kleiner als 27 ist!");
            }
        } while (gamefield.getGamefield()[0].length > 26);

        //Das gesamte Spielfeld initialisieren
        gamefield.initializeGamefield();

        moves = Additional.getSafeInteger("Gib ein, wie viele Züge du haben möchtest: ");

        //Spielfeld initialisieren
        gamefield.generateRandomGamefield();

        //Hauptschleife solane der Benutzer noch Züge hat
        while (moves > 0) {
            //Übriegen Züge und Punkte ausgeben
            System.out.print(moves + " Züge übrig");
            System.out.println("  " + punkte + "P");
            gamefield.printGamefield();

            //Zwei felder zu bewegen vom Benutzer erhalten
            do {
                swapField1 = gamefield.askMove1();
                swapField2 = gamefield.askMove2();
            } while (!gamefield.testMoves(swapField1, swapField2));

            gamefield.makeMove(swapField1, swapField2);

            moves--;

        }
    }
}