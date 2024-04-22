import java.awt.*;

public class MapGenerator {
    public int map[][];

    public int dummyMap[][];
    public int brickHeight;
    public int brickWidth;


    public MapGenerator(int row, int col){
        System.out.println("Row: " + row + "   Col: " + col);
        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){



                // 5 by 5 with the middle missing

                if (row == col)  {

                    if ((i >= 1 && i <= 3) && (j >= 1 && j <= 3)) {
                        map[i][j] = 0;
                    } else {
                        map[i][j] = 1;
                    }

                }



                // row == 3 && col == 7
                // unique layout for 3 by 7

                if (row == 3 && col == 7) {

                    if (i == j) {
                        map[i][j] = 0;
                    }

                    else if ((j == 4 && i == 2) || (j == 5 && i == 1) || (j == 6 && i == 0) ) {
                        map[i][j] = 0;
                    }

                    else if ((j == 3 && i == 0) || (j == 3 && i == 1)){
                        map[i][j] = 0;
                    }

                    else {
                        map[i][j] = 1;
                    }

                }

                // 4 by 8


                if (row == 4 && col == 8) {
                    if ((i == 1 || i == 2) && (j == 0 || j == 3 || j == 4 || j == 7)) {
                            map[i][j] = 0;
                    }
                    else if ((i == 0 || i == 3) && (j == 1 || j == 2 || j == 5 || j == 6)) {
                            map[i][j] = 0;
                    }
                    else {
                        map[i][j] = 1;
                    }
                }

            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }


    // method to get the number of bricks missing due to
    // unique level
    public int bricksRemoved(int row, int col){
        dummyMap = new int[row][col];
        int bricksGone = 0;
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (row == col)  {
                    if ((i >= 1 && i <= 3) && (j >= 1 && j <= 3)) {
                        map[i][j] = 0;
                        bricksGone++;
                    }
                }
                else if(row == 3 && col == 7){
                    if (i == j) {
                        map[i][j] = 0;
                        bricksGone++;
                    }

                    else if ((j == 4 && i == 2) || (j == 5 && i == 1) || (j == 6 && i == 0) ) {
                        map[i][j] = 0;
                        bricksGone++;
                    }

                    else if ((j == 3 && i == 0) || (j == 3 && i == 1)){
                        map[i][j] = 0;
                        bricksGone++;
                    }
                }
                else if(row == 4 && col == 8){
                    if ((i == 1 || i == 2) && (j == 0 || j == 3 || j == 4 || j == 7)) {
                        map[i][j] = 0;
                        bricksGone++;
                    }
                    else if ((i == 0 || i == 3) && (j == 1 || j == 2 || j == 5 || j == 6)) {
                        map[i][j] = 0;
                        bricksGone++;
                    }
                }
            }
        }
        return bricksGone;
    }

    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBricksValue(int value, int row, int col){
        map[row][col] = value;
    }
}
