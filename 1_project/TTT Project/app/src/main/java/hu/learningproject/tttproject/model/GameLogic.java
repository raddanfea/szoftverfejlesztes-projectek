package hu.learningproject.tttproject.model;

public class GameLogic {


    // original main function to provide example for testers and later integration
    /*
    public static void main(String []args){

        Scanner scanner = new Scanner(System.in);
        GameData data = new GameData();
        data.currentMap = new byte[data.defaultSize][data.defaultSize];


        while(data.turn < 3){
            data.currentMap = GetNextStep(scanner.nextInt(),scanner.nextInt(),data.turn,data.currentMap.length,data.currentMap);
            data.turn +=1;
            print(data.currentMap);
        }

    }*/

    //boolean to determine a valid map placement
    public static boolean isValidPos(int x, int y, byte[][] map){
        return map[x][y] == 0;
    }

    // creates an empty (filled with 0's) array at a given size.
    private static byte[][] createMapToSize(int s){
        return new byte[s][s];
    }

    // calculate the matrix changes from input. needs changed coordinates and GameData
    public static byte[][] GetNextStep(int x, int y,int t,int s, byte[][] currentMap){

        byte player = 1;
        if(t%2 == 0){
            player = 2;
        }

        if(x == 0 || x == s-1 | y == 0 || y == s-1){
            byte[][] newMap = createMapToSize(s+2);

            for (int i = 0; i < s-1; i++) {
                for (int k = 0; k < s-1; k++) {
                    newMap[i+1][k+1] = currentMap[i][k];
                }
            }
            currentMap = newMap;
            currentMap[x+1][y+1] = player;
        }
        else{
            currentMap[x][y] = player;
        }

        return currentMap;

    }

    /* Function to find win condition.
    * Gets x and y coordinates, turn number and current map.
    * Should be called before getNextStep and after isValidPos!
    * returns True or False currently. */

    public static boolean isWinner(int x, int y, int t, byte[][] currentMap) {

        byte player = 1;
        if(t%2 == 0){
            player = 2;
        }

        byte winhas = 1;
        byte wincon = 5;

        for(int jfl = 1; jfl < wincon; jfl++)
            try {
                if(currentMap[x-jfl][y-jfl] == player){
                    System.out.println("WIN++");
                    winhas += 1;
                }
                else{break;}}
            catch (Exception e){break;}

        for(int jfl = 1; jfl < wincon; jfl++)
            try {
                if(currentMap[x+jfl][y+jfl] == player){
                    winhas += 1;
                }
                else{break;}}
            catch (Exception e){break;}
        //System.out.println("has win: " + winhas);

        if(winhas < 5){

            winhas = 1;

            for(int jfl = 1; jfl < wincon; jfl++)
                try {
                    if(currentMap[x+jfl][y-jfl] == player){
                        winhas += 1;
                    }
                    else{break;}}
                catch (Exception e){break;}

            for(int jfl = 1; jfl < wincon; jfl++)
                try {
                    if(currentMap[x-jfl][y+jfl] == player){
                        winhas += 1;
                    }
                    else{break;}}
                catch (Exception e){break;}
        }

        //System.out.println("has win: " + winhas);
        return winhas > 4;
    }

    // print array for testing <3
    /*
    public static void print(final byte[][] array) {
        for (int l = 0; l < array.length; l++) {
            for (int j = 0; j < array[l].length; j++) {
                System.out.printf("%s", array[l][j]);
            }
            System.out.println();
        }
    }*/
}