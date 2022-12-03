public class GameField {
    private static Token[][] gamefield;
    private static Token[] tokens;
    private static int points = 0;


    public GameField(){
    }

    public void setGamefield(Token[][] gamefield) {
        GameField.gamefield = gamefield;
    }

    public Token[][] getGamefield(){
        return gamefield;
    }

    public void setTokens(Token[] tokens) {
        GameField.tokens = tokens;
    }

    public void setPoints(int points){
        GameField.points = points;
    }

    public int getPoints(){
        return points;
    }

    public void setRows(){
        GameField.gamefield = new Token[Additional.getSafeInteger("Gib die Reihen des Spielfeldes ein: ")][];
    }

    public void setCollums(){
        do{
            GameField.gamefield[0] = new Token[Additional.getSafeInteger("Gib die Spalten des Spielfeldes ein: ")];
            if(gamefield[0].length > 26){
                System.out.println("Die Zahl muss kleiner als 27 sein!");
            }
        }while(gamefield[0].length > 26);
    }

    public void initalizeTokens(int length){
        tokens = new Token[length];
        for(int i = 0; i < tokens.length; i++){
            tokens[i] = new Token();
            tokens[i].setId(i + 1);
            switch(i){
                case 0:
                    tokens[i].setText(Colors.getRed() + "O" + Colors.reset());
                    break;
                case 1:
                    tokens[i].setText(Colors.getBule() + "O" + Colors.reset());
                    break;
                case 2:
                    tokens[i].setText(Colors.getGreen() + "O" + Colors.reset());
                    break;
                case 3:
                    tokens[i].setText(Colors.getPurple() + "O" + Colors.reset());
                    break;
                case 4:
                    tokens[i].setText(Colors.getYellow() + "O" + Colors.reset());
                    break;
                default:
                    break;
            }
        }
    }

    public void initializeGamefield(){
        for (int i = 1; i < gamefield.length; i++) {
            gamefield[i] = new Token[gamefield[0].length];
        }
    }

    public int[] askMove1(){
        int[] feldZuBewegen1 = new int[2];
        do{
            feldZuBewegen1[0] = Additional.letterToNumber(Additional.getSafeLetter("Welches Feld möchtest du bewegen? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen1[0] > gamefield[0].length - 1);
        do{
            feldZuBewegen1[1] = Additional.getSafeInteger("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen1[1] > gamefield.length - 1);

        return feldZuBewegen1;
    }

    public int[] askMove2(){
        int[] feldZuBewegen2 = new int[2];
        do{
            feldZuBewegen2[0] = Additional.letterToNumber(Additional.getSafeLetter("Mit welchem Feld soll getauscht werden? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen2[0] > gamefield[0].length - 1);
        do{
            feldZuBewegen2[1] = Additional.getSafeInteger("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen2[0] > gamefield.length - 1);

        return feldZuBewegen2;
    }

    public boolean tryNextToEachOther(int[] swapFields1, int[] swapFields2){
        try{
            if(swapFields1[1] == (swapFields2[1] - 1) || swapFields1[1] == (swapFields2[1] + 1) || swapFields1[0] == (swapFields2[0] - 1) || swapFields1[0] == (swapFields2[0] + 1)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){System.out.println("Fehler");}

        return false;
    }

    public boolean rowOfThree(Token[][] field){
        //Nach oben, unten, rechts, links, mittig waagerecht, mittig senkrecht überprüfen, ob es eine dreierreihe ergibt, wenn die Werte über 2 sind
        for(int i = 0; i < field.length; i++) {
            for (int z = 0; z < field[0].length; z++) {
                try{
                    if(field[i][z].getId() == field[i - 1][z].getId() && field[i][z].getId() == field[i - 2][z].getId()) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(field[i][z].getId() == field[i][z - 1].getId() && field[i][z].getId() == field[i][z - 2].getId()) {
                        return true;
                    }
                }catch (Exception e){}
            }
        }

        return false;
    }

    public boolean testMoves(int[] swapFields1, int[] swapFields2){
        Token cache;

        if(!tryNextToEachOther(swapFields1, swapFields2)) {
            System.out.println("Die Felder liegen nicht nebeneinander");
            return false;
        }
        cache = gamefield[swapFields1[1]][swapFields1[0]];
        gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
        gamefield[swapFields2[1]][swapFields2[0]] = cache;
        if(rowOfThree(gamefield)){
            cache = gamefield[swapFields1[1]][swapFields1[0]];
            gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
            gamefield[swapFields2[1]][swapFields2[0]] = cache;
            return true;
        }
        cache = gamefield[swapFields1[1]][swapFields1[0]];
        gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
        gamefield[swapFields2[1]][swapFields2[0]] = cache;
        System.out.println("Die Felder ergeben keine Dreierreihe");
        return false;
    }

    private int verticalLine(int i, int z, String direction) {
        if(direction.equals("down")){
            try {
                if (gamefield[i][z].getId() == gamefield[i + 1][z].getId() && gamefield[i][z].getId() == gamefield[i + 2][z].getId()) {
                    if (i <= gamefield.length - 4) {
                        if (gamefield[i][z].getId() == gamefield[i + 3][z].getId()) {
                            if (i <= gamefield.length - 5) {
                                if (gamefield[i][z].getId() == gamefield[i + 4][z].getId()) {
                                    return 5;
                                }
                            }
                            return 4;
                        }
                    }
                    return 3;
                }
            } catch (Exception e) {}
        }else{
            try {
                if (gamefield[i][z].getId() == gamefield[i - 1][z].getId() && gamefield[i][z].getId() == gamefield[i - 2][z].getId()) {
                    if (i < gamefield.length + 4) {
                        if (gamefield[i][z].getId() == gamefield[i - 3][z].getId()) {
                            if (i < gamefield.length + 5) {
                                if (gamefield[i][z].getId() == gamefield[i - 4][z].getId()) {
                                    return 5;
                                }
                                return 4;
                            }
                        }
                    }
                    return 3;
                }
            } catch (Exception e) {}
        }

        return 0;
    }

    private int horizontalLine(int i, int z, String direction){
        if(direction.equals("right")){
            try{
                if(gamefield[i][z].getId() == gamefield[i][z + 1].getId() && gamefield[i][z].getId() == gamefield[i][z + 2].getId()){
                    if(z < gamefield[i].length - 4){
                        if(gamefield[i][z].getId() == gamefield[i][z + 3].getId()) {
                            if(z < gamefield[i].length - 5){
                                if(gamefield[i][z].getId() == gamefield[i][z + 4].getId()){
                                    return 5;
                                }
                                return 4;
                            }
                        }
                    }
                    return 3;
                }
            }catch (Exception e){}
        }else{
            try{
                if(gamefield[i][z].getId() == gamefield[i][z - 1].getId() && gamefield[i][z].getId() == gamefield[i][z - 2].getId()){
                    if(z < gamefield[i].length + 4){
                        if(gamefield[i][z].getId() == gamefield[i][z - 3].getId()) {
                            if(z < gamefield[i].length + 5){
                                if(gamefield[i][z].getId() == gamefield[i][z - 4].getId()){
                                    return 5;
                                }
                                return 4;
                            }
                        }
                    }
                    return 3;
                }
            }catch (Exception e){}
        }


        return 0;
    }

    public void deleteFields(){
        int cache;
        Token gapFiller = new Token(-1, " ");

        for(int i = 0; i < gamefield.length; i++){
            for(int z = 0; z < gamefield[0].length; z++){
                try{
                    if(verticalLine(i, z, "down") > 2){
                        if(verticalLine(i, z, "down") == 5){
                            if(horizontalLine(i + 2,z, "left") == 3){
                                for(int g = 0; g < 3; g++){
                                    gamefield[i + g][z] = gapFiller;
                                }
                                gamefield[i + 2][z - 1] = gapFiller;
                                gamefield[i + 2][z - 2] = gapFiller;
                                return;
                            }
                            if(horizontalLine(i + 2,z, "right") == 3){
                                for(int g = 0; g < 3; g++){
                                    gamefield[i + g][z] = gapFiller;
                                }
                                gamefield[i + 2][z + 1] = gapFiller;
                                gamefield[i + 2][z + 2] = gapFiller;
                                return;
                            }

                        } else if (verticalLine(i, z, "down") == 3) {
                            if(horizontalLine(i + 2, z, "left") == 3){
                                for(int g = 0; g < 3;g++){
                                    gamefield[i + g][z] = gapFiller;
                                    gamefield[i + 2][z - g] = gapFiller;
                                }
                                return;
                            }
                            if(horizontalLine(i + 2, z, "right") == 3){
                                for(int g = 0; g < 3;g++){
                                    gamefield[i + g][z] = gapFiller;
                                    gamefield[i + 2][z + g] = gapFiller;
                                }
                                return;
                            }
                            if(horizontalLine(i, z, "right") == 3){
                                for(int g = 0; g < 3;g++){
                                    gamefield[i + g][z] = gapFiller;
                                    gamefield[i][z + g] = gapFiller;
                                }
                                return;
                            }
                            if(horizontalLine(i + 2, z - 2, "right") == 5){
                                for(int g = 0; g < 5; g++){
                                    gamefield[i + 2][z - 2 + g] = gapFiller;
                                }
                                gamefield[i][z] = gapFiller;
                                gamefield[i - 1][z] = gapFiller;
                                return;
                            }
                        }
                        cache = verticalLine(i, z, "down");
                        for(int g = 0; g < cache; g++){
                            gamefield[i + g][z] = gapFiller;
                        }
                        return;
                    }
                }catch (Exception e){}

                try{
                    if(horizontalLine(i, z, "right") > 2) {
                        try{
                            if(horizontalLine(i, z, "right") == 5 && verticalLine(i, z + 2, "down") == 3){
                                for (int g = 0; g < 5; g++){
                                    gamefield[i][z + g] = gapFiller;
                                }
                                gamefield[i + 1][z + 2] = gapFiller;
                                gamefield[i + 2][z + 2] = gapFiller;
                                return;
                            } else if (horizontalLine(i, z, "right") == 3 && verticalLine(i, z + 2, "down") == 3) {
                                for (int g = 0; g < 3; g++){
                                    gamefield[i + g][z] = gapFiller;
                                    gamefield[i][z + g] = gapFiller;
                                }
                                return;
                            }
                        }catch (Exception e){}
                        cache = horizontalLine(i, z, "right");
                        for(int g = 0; g < cache; g++){
                            gamefield[i][z + g] = gapFiller;
                        }
                        return;
                    }
                }catch (Exception e){}
            }
        }
    }

    public void lowerAndFillFields(){
        Token gapFiller = new Token(-1, " ");

        for(int i = 0; i < gamefield.length; i++) {
            for (int z = 0; z < gamefield[0].length; z++) {
                if(gamefield[i][z].getId() == -1){
                    for(int g = i; g >= 1; g--){
                        gamefield[g][z] = gamefield[g - 1][z];
                        gamefield[g - 1][z] = gapFiller;
                        Additional.clearConsole();
                        printGamefield();
                        Additional.delay(250);
                    }
                }
            }
        }

        //Felder oben auffüllen
        for(int i = 0; i < gamefield.length; i++) {
            for (int z = 0; z < gamefield[0].length; z++) {
                if(gamefield[i][z].getId() == -1){
                    // Punkte hinzufügen
                    points++;
                    gamefield[i][z] = tokens[Additional.random.nextInt(5)];
                    Additional.clearConsole();
                    printGamefield();
                    Additional.delay(250);
                }
            }
        }

    }

    public void updateGamefield(int[] swapField1, int[] swapField2){
        Token cache;
        cache = gamefield[swapField1[1]][swapField1[0]];
        gamefield[swapField1[1]][swapField1[0]] = gamefield[swapField2[1]][swapField2[0]];
        gamefield[swapField2[1]][swapField2[0]] = cache;
    }

    public void generateRandomGamefield(){
        int randomNumber = Additional.random.nextInt(4);

        for(int i = 0; i < gamefield.length; i++){
            for(int z = 0; z < gamefield[i].length; z++){

                //objekt zum testen erstellen
                gamefield[i][z] = tokens[Additional.random.nextInt(5)];

                //Wenn der i oder z wert über 2 ist, muss überprüft werden ob das objekt platziert werden kann
                if(i >= 2 && z >= 2){
                    while((gamefield[i][z].getId() == gamefield[i - 2][z].getId() && gamefield[i][z].getId() == gamefield[i - 1][z].getId()) || (gamefield[i][z].getId() == gamefield[i][z - 2].getId() && gamefield[i][z].getId() == gamefield[i][z - 1].getId())){
                        gamefield[i][z] = tokens[Additional.random.nextInt(5)];
                    }
                }

                //Wenn der i Wert über 1 ist und der z Wert kleiner als 2 ist soll überprüft werden, ob das Objekt plaziert werden darf
                if(i > 1 && z < 2){
                    while(gamefield[i][z].getId() == gamefield[i - 2][z].getId() && gamefield[i][z].getId() == gamefield[i - 1][z].getId()){
                        gamefield[i][z] = tokens[Additional.random.nextInt(5)];
                    }
                }

                //Wenn der z Wert über 1 ist und der i Wert kleiner als 2 ist soll überprüft werden, ob das Objekt plaziert werden darf
                if(z > 1 && i < 2){
                    while(gamefield[i][z].getId() == gamefield[i][z - 2].getId() && gamefield[i][z].getId() == gamefield[i][z - 1].getId()){
                        gamefield[i][z] = tokens[Additional.random.nextInt(5)];
                    }
                }
                randomNumber = Additional.random.nextInt(5);
            }
        }
    }

     public void printGamefield(){

        System.out.print("   ");
        for(int i = 0; i < gamefield[0].length; i++){
            System.out.print(Additional.numberToLetter(i));
            System.out.print("  ");
        }

        System.out.println();

        for(int i = 0; i < gamefield.length; i++){
            System.out.print(i + 1);
            if(i < 9){
                System.out.print(" ");
            }
            for(int z = 0; z < gamefield[i].length; z++){
                System.out.print(" " + gamefield[i][z].getText() + " ");
            }
            System.out.println();
        }
    }

    public Token[][] swapFields(Token[][] field, int i, int z, int g, int h){
        Token cache;
        cache = field[i][z];
        field[i][z] = field[g][h];
        field[g][h] = cache;
        return field;
    }

    public boolean testMovePossible(){
        Token[][] testGamefield = new Token[gamefield.length][gamefield[0].length];
        for(int i = 0; i < testGamefield.length; i++) {
            for (int z = 0; z < testGamefield[0].length; z++) {
                testGamefield[i][z] = gamefield[i][z];
            }
        }

        Token cache;
        for(int i = 0; i < testGamefield.length; i++){
            for(int z = 0; z < testGamefield[0].length; z++){
                try{
                    testGamefield = swapFields(testGamefield, i, z, i, z + 1);
                    if(rowOfThree(testGamefield)){
                        return true;
                    }
                    testGamefield = swapFields(testGamefield, i, z, i, z + 1);
                }catch (Exception e){}
                try{
                    testGamefield = swapFields(testGamefield, i, z, i + 1, z);
                    if(rowOfThree(testGamefield)){
                        return true;
                    }
                    testGamefield = swapFields(testGamefield, i, z, i + 1, z);
                }catch (Exception e){}
            }
        }
        return false;
    }

    public void makeMove(int[] swapField1, int[] swapField2){
        updateGamefield(swapField1, swapField2);
        while(rowOfThree(gamefield)){
            deleteFields();
            printGamefield();
            Additional.clearConsole();
            Additional.delay(500);
            lowerAndFillFields();
            printGamefield();
        }
    }
}
