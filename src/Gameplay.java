import org.apache.commons.lang3.time.StopWatch;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Gamplay Class
 * Handles the brick breaker game itself and how it functions.
 * The class handles, points, progression, times, collisions,
 * and other important functions for the game to properly run
 * Play: flag to indicate if the level is being played
 * Score: the player's score
 * totalBricks: number of bricks in the level
 * timer: handles time events
 * bestTime: used for the player's best time:
 * currentTime: stores the current time in the game
 * delay: amount of delay for actionsPeformed before another can be mdade
 * playerX: Coordinate for the paddle along the X-position
 * ballPositonX: Coordinate for the ball along the X-position
 * ballPositionY: Coordinate for the ball along the Y-position
 * highScore: Highest score for the player
 * backgroundImage: background for the game
 * map: instance of MapGenerator for the levels
 * paddleIcon: image for the paddle
 * ballIcon: image for the ball
 * watch: measures how much time as passed in game
 * levels: level of the game, also used for level creation for the rows and columns
 * minusBricks: for the number of bricks gone from the bricksRemoved method and totalBricks
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play;
    private int score = 0;
    private int totalBricks = 48;
    private Timer timer;
    //default best time
    private  long bestTime = 500000;
    private long currentTime = 0;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int highScore = 0;
    private final BufferedImage backgroundImage;
    private MapGenerator map;
    private ImageIcon paddleIcon;
    private ImageIcon ballIcon;

    StopWatch watch = new StopWatch();

    private int levels = 1;

    private int minusBricks = 0;


    // int row, int col

    /**
     * Constructor
     * Starts the background music off for the levels
     * Creates the first level that the user chooses
     * Starts the timer
     * Retrieves the images for the ball, paddle, and background
     * Sets up for the Key events later
     * @param level the levels for the game itself, this is also used for the rows and columns
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public Gameplay(int level) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        levels = level;
        File song = new File(("./src/backgroundMusic.wav"));
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        //totalBricks = (levels + 1) * (levels + 5);
        map = new MapGenerator((levels + 1), (levels + 5));
        minusBricks = map.bricksRemoved((levels + 1), (levels + 5));
        totalBricks = ((levels + 1) * (levels + 5)) - minusBricks;


        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        watch.reset();
        //watch.start();
        paddleIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("paddle.png")));
        ballIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("ball.png")));
        backgroundImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("background.jpg")));// Change "background.jpg" to your image file path
    }

    /**
     * Method that handles the graphics for the game
     * Drawing the background image
     * Draws out the bricks by calling the draw method from MapGenerator
     * Borders for the game
     * The score that's being kept track of
     * The timer, and the high score
     * The ball and the paddle
     * Handles the graphics for messages such as game over and pressing
     * enter to start
     * Detects if the level has been completed to stop the watch and the time and score
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g){
        //background
        super.paintComponent(g);

        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.blue);
        g.fillRect(0, 0 ,3 ,700); //left
        g.fillRect(0, 0 ,700 ,3); // top
        g.fillRect(697, 0 ,3 ,700); //right
        g.fillRect(0, 680 ,700 ,3); // bottom

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("comic sans", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        g.setColor(Color.white);
        g.setFont(new Font("comic sans", Font.BOLD, 25));
        g.drawString("High Score: " + highScore, 30, 650);

        //Timer
        g.setColor(Color.white);
        g.setFont(new Font("comic sans",Font.BOLD, 25));
        g.drawString(formatElapsedTime(watch.getTime()), 50, 30);


        //the paddle
        Image paddleImage =  paddleIcon.getImage();
        g.drawImage(paddleImage, playerX, 500,80,80, this);

        //the ball
        Image ballImage = ballIcon.getImage();
        g.drawImage(ballImage, ballposX, ballposY, 10, 10, this);

        if(!play){
            g.setColor(Color.blue);
            g.setFont(new Font("comic sans",Font.BOLD, 25));
            g.drawString("Press Enter to Begin ", 200, 300);
        }

        if(totalBricks <= 0){
            watch.stop();
            play = false;
            currentTime = watch.getTime();
            ballXdir = 0;
            ballYdir = 0;

            if(score > highScore){
                highScore = score;
            }

            g.setColor(Color.blue);
            g.setFont(new Font("comic sans", Font.BOLD, 30));
            g.drawString("You won! Score: " + score, 190, 300);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            g.drawString("High Score: " + highScore, 280, 400);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            g.drawString("Press enter to Restart", 230, 350);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            //g.drawString("Best Time: " + formatElapsedTime(bestTime), 270, 380);
            g.drawString("Total Time: " + formatElapsedTime(currentTime), 270, 380);
        }

        if(ballposY > 570){
            currentTime = watch.getTime();
            watch.stop();
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.blue);
            g.setFont(new Font("comic sans", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            g.drawString("High Score: " + highScore, 280, 400);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            g.drawString("Press enter to Restart", 250, 350);

            g.setFont(new Font("comic sans", Font.BOLD, 20));
            g.drawString("Total Time: " + formatElapsedTime(currentTime), 270, 380);
        }
        g.dispose();
    }


    /**
     * This method handles several features,
     * Detects and handles collision between the ball and the paddle,
     * Collision with the bricks,
     * Movement of the ball,
     * Collision with the bounds of the game (the screen),
     * Repaint to draw the following level
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))){
                ballYdir = -ballYdir;
                ballXdir = -2;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))){
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
            {
                ballYdir = -ballYdir;
            }

            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int bricksX = j * map.brickWidth + 80;
                        int bricksY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(bricksX, bricksY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if(ballRect.intersects(rect)){
                            map.setBricksValue(0,i,j);
                            score += 5;
                            totalBricks--;

                            if(ballposX + 19 <= rect.x || ballposX + 1 >= rect.x + rect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }


        }
        repaint();
    }


    /**
     * Empty key typed event
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Method the process events depending on what key is pressed.
     * It gets the key pressed and puts it into a variable, then
     * depending on what it was some code will be executed such as movement,
     * level progression, and restarting a level
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_RIGHT){
            if(playerX >= 600 ){
                playerX = 600;
            } else{
                moveRight();
            }
        }
        else if(keyCode == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        else if(keyCode == KeyEvent.VK_ENTER){
            if(!play){
                if (totalBricks <= 0) {
                    levels ++;
                }
                resetGame();
            } else{
                watch.reset();
                watch.start();
            }
        }
    }

    //test :3

    /**
     * Method to move the paddle to the right
     */
    public void moveRight(){
        play = true;
        playerX+=20;
    }

    /**
     * Method to move the paddle to the left
     */
    public void moveLeft(){
        play = true;
        playerX-=20;
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Method to format the time into minutes and seconds
     * @param millis time in milliseconds
     * @return the time it took to complete the level
     */
    private String formatElapsedTime(long millis){
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Method that generates the following level,
     * Restarts time and ball position, creates the next level for the game
     */
    private void resetGame(){
        play = true;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;
        playerX = 310;
        score = 0;
        //totalBricks = (levels + 1) * (levels + 5);
        map = new MapGenerator((levels + 1), (levels + 5));
        minusBricks = map.bricksRemoved((levels + 1),(levels + 5));
        totalBricks = (levels + 1) * (levels + 5) - minusBricks;

        watch.reset();
        watch.start();
        repaint();
    }

}
