import java.awt.*;

/**
 * Map Generator Class
 * Deals with creating the different levels for the game and includes
 * some designs for the first three levels and their colors
 * Map: 2D array for the level
 * dummyMap: holder map to be used for the bricksRemoved method
 * brickHeight: height of the brick.
 * brickWidth: width of the brick
 * color: color for the brick which will be randomly generated
 */
public class MapGenerator {
    public int map[][];

    public int dummyMap[][];
    public int brickHeight;
    public int brickWidth;
    public Color color = randomColor();

    /**
     * Constructor for the class
     * Contains unique level designs for the first three levels
     * depending on their row and col count
     * Bricks are set to 0 for some level designs
     * @param row the rows of the level
     * @param col number of columns for the level
     */
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){

                if (row == 2 && col == 6)  {
                    if ((i >= 1 && i <= 3) && (j >= 1 && j <= 3)) {
                        map[i][j] = 0;
                    }
                    else {
                        map[i][j] = 1;
                    }
                }


                // row == 3 && col == 7
                // unique layout for 3 by 7

                else if (row == 3 && col == 7) {

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


                else if (row == 4 && col == 8) {
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
                else {
                    map[i][j] = 1;
                }


            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;

    }


    // method to get the number of bricks missing due to
    // unique level

    /**
     * Method that returns the number of bricks gone.
     * Follows largely the same logic as the constructor buts
     * also increments a variable to keep track of the number of bricks removed.
     * Variable called dummyMap which has the same dimensions as the level,
     * which is used to get the number of bricks gone by following its same logic.
     * @param row number of rows
     * @param col number of columns
     * @return number of bricks gone by the method
     */
    public int bricksRemoved(int row, int col){
        dummyMap = new int[row][col];
        int bricksGone = 0;
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                if (row == 2 && col == 6)  {
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


    /**
     * Sets a random color for the bricks on the level by
     * randomly getting a number for the individual rgb values
     * @return color depending on rgb values
     */
    public Color randomColor(){
        int r = (int) (Math.random()*256);
        int g = (int) (Math.random()*256);
        int b = (int) (Math.random()*256);
        return (new Color(r,g,b));
    }

    /**
     * Method to draw the bricks and to set their settings such
     * as height, width, and color
     * @param g graphics, the graphics for the levels and the bricks
     */
    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(color);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);


                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    /**
     * Setting the value of the bricks so that they are present
     * and on screen for their levels.
     * @param value value of the bricks
     * @param row rows for the level
     * @param col columns of the level
     */
    public void setBricksValue(int value, int row, int col){
        map[row][col] = value;
    }
}

