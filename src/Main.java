import java.util.*;

public class Main {

    public static void main(String[] args) {
        GameField gameField = new GameField();
        Token[] tokens = new Token[5];
        int moves;
        int[] swapFields1 = new int[2];
        int[] swapFields2 = new int[2];

        gameField.initalizeTokens(5);
        Additional.printExample();

        gameField.setRows();
        gameField.setCollums();

        gameField.initializeGamefield();

        moves = Additional.getSafeInteger("Gib ein, wie viele Züge du haben möchtest: ");

        gameField.generateRandomGamefield();

        //Hauptschleife solane der Benutzer noch Züge hat
        while (moves > 0) {
            //Spielfeld reseten
            Additional.clearConsole();
            //Übriegen Züge und Punkte ausgeben
            System.out.print(moves + " Züge übrig");
            System.out.println("  " + gameField.getPoints() + "P");
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

