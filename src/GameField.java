public class GameField {
    private static Token[][] gamefield;
    private static  Token[] tokens;
    private static int points = 0;


    public GameField(){
    }

    public static void setGamefield(Token[][] gamefield) {
        GameField.gamefield = gamefield;
    }

    public static Token[][] getGamefield(){
        return gamefield;
    }

    public static void setTokens(Token[] tokens) {
        GameField.tokens = tokens;
    }

    public static void setPoints(int points){
        GameField.points = points;
    }

    public static int getPoints(){
        return points;
    }

    public static void setRows(){
        GameField.gamefield = new Token[Additional.getSafeInteger("Gib die Reihen des Spielfeldes ein: ")][];
    }

    public static void setCollums(){
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

    public static void initializeGamefield(){
        for (int i = 1; i < gamefield.length; i++) {
            gamefield[i] = new Token[gamefield[0].length];
        }
    }

    public static int[] askMove1(){
        int[] feldZuBewegen1 = new int[2];
        do{
            feldZuBewegen1[0] = Additional.letterToNumber(Additional.getSafeLetter("Welches Feld möchtest du bewegen? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen1[0] > gamefield[0].length);
        do{
            feldZuBewegen1[1] = Additional.getSafeInteger("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen1[0] > gamefield.length);

        return feldZuBewegen1;
    }

    public static int[] askMove2(){
        int[] feldZuBewegen2 = new int[2];
        do{
            feldZuBewegen2[0] = Additional.letterToNumber(Additional.getSafeLetter("Mit welchem Feld soll getauscht werden? Gib als erstes den Buchstaben ein: "));
        }while(feldZuBewegen2[0] > gamefield[0].length);
        do{
            feldZuBewegen2[1] = Additional.getSafeInteger("Gib jetzt die Zahl ein: ") - 1;
        }while(feldZuBewegen2[0] > gamefield.length);

        return feldZuBewegen2;
    }

    public static boolean tryNextToEachOther(int[] swapFields1, int[] swapFields2){
        try{
            if(swapFields1[1] == (swapFields2[1] - 1) || swapFields1[1] == (swapFields2[1] + 1) || swapFields1[0] == (swapFields2[0] - 1) || swapFields1[0] == (swapFields2[0] + 1)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){System.out.println("Fehler");}

        return false;
    }

    public static boolean rowOfThree(){
        //Nach oben, unten, rechts, links, mittig waagerecht, mittig senkrecht überprüfen, ob es eine dreierreihe ergibt, wenn die Werte über 2 sind
        for(int i = 0; i < gamefield.length; i++) {
            for (int z = 0; z < gamefield[0].length; z++) {
                try{
                    if(gamefield[i][z].getId() == gamefield[i - 1][z].getId() && gamefield[i][z].getId() == gamefield[i - 2][z].getId()) {
                        return true;
                    }
                }catch (Exception e){}
                try{
                    if(gamefield[i][z].getId() == gamefield[i][z - 1].getId() && gamefield[i][z].getId() == gamefield[i][z - 2].getId()) {
                        return true;
                    }
                }catch (Exception e){}
            }
        }

        return false;
    }

    public static boolean testMoves(int[] swapFields1, int[] swapFields2){
        Token zwischenspeicher;

        if(!tryNextToEachOther(swapFields1, swapFields2)) {
            System.out.println("Die Felder liegen nicht nebeneinander");
            return false;
        }
        zwischenspeicher = gamefield[swapFields1[1]][swapFields1[0]];
        gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
        gamefield[swapFields2[1]][swapFields2[0]] = zwischenspeicher;
        if(rowOfThree()){
            zwischenspeicher = gamefield[swapFields1[1]][swapFields1[0]];
            gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
            gamefield[swapFields2[1]][swapFields2[0]] = zwischenspeicher;
            return true;
        }
        zwischenspeicher = gamefield[swapFields1[1]][swapFields1[0]];
        gamefield[swapFields1[1]][swapFields1[0]] = gamefield[swapFields2[1]][swapFields2[0]];
        gamefield[swapFields2[1]][swapFields2[0]] = zwischenspeicher;
        System.out.println("Die Felder ergeben keine Dreierreihe");
        return false;
    }

        /*static boolean nachObenTesten(Main.Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i - 1][z].id && feld[i][z].id == feld[i - 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachUntenTesten(Main.Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachRechtsTesten(Main.Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i][z - 1].id && feld[i][z].id == feld[i][z - 2].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static boolean nachLinksTesten(Main.Objekt[][] feld, int i, int z){
            try{
                if(feld[i][z].id == feld[i + 1][z].id && feld[i][z].id == feld[i + 2][z].id) {
                    return true;
                }
            }catch (Exception e){}
            return false;
        }
        static int anzahlFelder(Main.Objekt[][] feld, int i, int z){
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
        }*/

    public static void deleteFields(){
        Token gapFiller = new Token(-1, " ");

        for(int i = 0; i < gamefield.length; i++){
            for(int z = 0; z < gamefield[0].length; z++){
                try{
                    if(gamefield[i][z].getId() == gamefield[i + 1][z].getId() && gamefield[i][z].getId() == gamefield[i + 2][z].getId()){

                        if(i < gamefield.length - 4){
                            if(gamefield[i][z].getId() == gamefield[i + 3][z].getId()) {
                                gamefield[i + 3][z] = gapFiller;
                                if(i < gamefield.length - 5) {
                                    if (gamefield[i][z].getId() == gamefield[i + 4][z].getId()) {

                                        try{
                                            if(gamefield[i + 2][z].getId() == gamefield[i + 2][z + 1].getId()){
                                                gamefield[i + 2][z + 1] = gapFiller;
                                                if(gamefield[i + 2][z].getId() == gamefield[i + 2][z + 2].getId()){
                                                    gamefield[i + 2][z + 2] = gapFiller;
                                                }
                                            }
                                        }catch (Exception e){}

                                        try{
                                            if(gamefield[i + 2][z].getId() == gamefield[i + 2][z - 1].getId()){
                                                gamefield[i + 2][z - 1] = gapFiller;
                                                if(gamefield[i + 2][z].getId() == gamefield[i + 2][z - 2].getId()){
                                                    gamefield[i + 2][z - 2] = gapFiller;
                                                }
                                            }
                                        }catch (Exception e){}

                                        gamefield[i + 4][z] = gapFiller;
                                    }
                                }
                            }
                        }
                        try{
                            if(gamefield[i][z].getId() == gamefield[i][z + 1].getId()){
                                gamefield[i][z + 1] = gapFiller;
                                if(gamefield[i][z].getId() == gamefield[i][z + 2].getId()){
                                    gamefield[i][z + 2] = gapFiller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(gamefield[i][z].getId() == gamefield[i][z - 1].getId()){
                                gamefield[i][z - 1] = gapFiller;
                                if(gamefield[i][z].getId() == gamefield[i][z - 2].getId()){
                                    gamefield[i][z - 2] = gapFiller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(gamefield[i][z].getId() == gamefield[i][z + 1].getId()){
                                gamefield[i][z + 1] = gapFiller;
                                if(gamefield[i][z].getId() == gamefield[i][z + 2].getId()){
                                    gamefield[i][z + 2] = gapFiller;
                                }
                            }
                        }catch (Exception e){}
                        try{
                            if(gamefield[i][z].getId() == gamefield[i][z - 1].getId()){
                                gamefield[i][z - 1] = gapFiller;
                                if(gamefield[i][z].getId() == gamefield[i][z - 2].getId()){
                                    gamefield[i][z - 2] = gapFiller;
                                }
                            }
                        }catch (Exception e){}
                        gamefield[i][z] = gapFiller;
                        gamefield[i + 1][z] = gapFiller;
                        gamefield[i + 2][z] = gapFiller;
                    }
                }catch (Exception e){}
                try{
                    if(gamefield[i][z].getId() == gamefield[i][z + 1].getId() && gamefield[i][z].getId() == gamefield[i][z + 2].getId()){
                        if(z < gamefield[i].length - 4){
                            if(gamefield[i][z].getId() == gamefield[i][z + 3].getId()) {
                                gamefield[i][z + 3] = gapFiller;
                                if(z < gamefield[i].length - 5){
                                    if(gamefield[i][z].getId() == gamefield[i][z + 4].getId()){
                                        gamefield[i][z + 4] = gapFiller;
                                    }
                                }
                            }
                        }
                        gamefield[i][z] = gapFiller;
                        gamefield[i][z + 1] = gapFiller;
                        gamefield[i][z + 2] = gapFiller;
                    }
                }catch (Exception e){}
            }
        }
    }

    public static void lowerAndFillFields(){
        Token gapFiller = new Token(-1, " ");

        for(int i = 0; i < gamefield.length; i++) {
            for (int z = 0; z < gamefield[0].length; z++) {
                if(gamefield[i][z].getId() == -1){
                    for(int g = i; g >= 1; g--){
                        gamefield[g][z] = gamefield[g - 1][z];
                        gamefield[g - 1][z] = gapFiller;
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
                }
            }
        }

    }

    static void updateGamefield(int[] swapField1, int[] swapField2){
        Token cache;
        cache = gamefield[swapField1[1]][swapField1[0]];
        gamefield[swapField1[1]][swapField1[0]] = gamefield[swapField2[1]][swapField2[0]];
        gamefield[swapField2[1]][swapField2[0]] = cache;
    }

    static void generateRandomGamefield(){
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

    static void printGamefield(){

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

    public static void makeMove(int[] swapField1, int[] swapField2){
        updateGamefield(swapField1, swapField2);
        while(rowOfThree()){
            deleteFields();
            lowerAndFillFields();
            printGamefield();
        }
    }
}
