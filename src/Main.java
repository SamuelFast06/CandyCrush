import java.util.*;

public class Main {
    static int punkte = 0;

    public static void main(String[] args) {
        GameField gameField = new GameField();
        Token[] tokens = new Token[5];
        int moves;
        int[] swapFields1 = new int[2];
        int[] swapFields2 = new int[2];
        int zwischenspeicher = 0;

        //Objekte initialisieren
        gameField.initalizeTokens(5);
        //Beispiele ausgeben
        Additional.printExample();

        //Die Reihen des Spielfeldes bestimmen
        gameField.setRows();
        //Die Spalten des Spielfeldes festlegen
        do {
            gameField.setCollums();
            if (gameField.getGamefield()[0].length > 26) {
                System.out.println("Gib eine Zahl ein, die kleiner als 27 ist!");
            }
        } while (gameField.getGamefield()[0].length > 26);

        //Das gesamte Spielfeld initialisieren
        gameField.initializeGamefield();

        moves = Additional.getSafeInteger("Gib ein, wie viele Züge du haben möchtest: ");

        //Spielfeld initialisieren
        gameField.generateRandomGamefield();

        //Hauptschleife solane der Benutzer noch Züge hat
        while (moves > 0) {
            //Übriegen Züge und Punkte ausgeben
            System.out.print(moves + " Züge übrig");
            System.out.println("  " + punkte + "P");
            gameField.printGamefield();

            //Zwei felder zu bewegen vom Benutzer erhalten
            do {
                swapFields1 = gameField.askMove1();
                swapFields2 = gameField.askMove2();
            } while (!gameField.testMoves(swapFields1, swapFields2));

            gameField.makeMove(swapFields1, swapFields2);

            moves--;


        }
    }
}

