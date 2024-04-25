import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This is the main class which will start the brick breaker game.
 * The class itself has the title screen which will be shown when ran,
 * where the usual will be able to pick a level to start off on depending
 * on which button they press.
 * The class also generates background music for the title screen.
 */
public class Main  {

    /**
     * Main method for the class which handles the background music for the title screen,
     * creation of the title screen, JFrame for the buttons, labels, and panels,
     * and settings for all of them
     * @param args
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        /**
        Background music for the title screen is being ran through the Clip object
         where it will try to get the path/location of the audio file where it will play
         until it gets closed with a later call.
         */
        AudioInputStream audioIn = null;
        Clip clip = AudioSystem.getClip();
        try {
            File file = new File("src/donk.wav");
            audioIn = AudioSystem.getAudioInputStream(file);
            clip.open(audioIn);
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println(ex);
        }
        finally {
            try {
                if (audioIn != null) {
                    audioIn.close();
                }
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        }


        /**
         * JFrame object for the title screen which includes buttons, labels,
         * and panels
         */
        JFrame obj = new JFrame();

        /**
         * Settings for the JFrame object
         */
        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Settings for the title Label
         */
        JLabel title = new JLabel("Brick Breaker: SPACE", SwingConstants.CENTER);
        Font heading = new Font("Times Roman", Font.BOLD, 50);
        title.setFont(heading);
        title.setPreferredSize(new Dimension(700, 245));
        title.setOpaque(true);
        title.setBackground(Color.decode("#749cbd"));
        obj.getContentPane().add(title, BorderLayout.PAGE_START);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        /**
        Settings for the text Panel
         */

        textPanel.setPreferredSize(new Dimension(700,355));
        textPanel.setBackground(Color.decode("#749cbd"));
        textPanel.setOpaque(true);
        JLabel welcomeMessage = new JLabel(" Select a Level\n\n");
        welcomeMessage.setFont(new Font("Times Roman", Font.BOLD, 25));
        textPanel.add(welcomeMessage);

        /**
         * Settings for the button panel
         */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#749cbd"));
        buttonPanel.setOpaque(true);


        /**
         * What the level buttons will display on them
         */
        JButton Level1 = new JButton("Level 1");
        JButton Level2 = new JButton("Level 2");
        JButton Level3 = new JButton("Level 3");


        /**
         * Setting the fonts for the buttons
         */
        Font buttonFont = new Font("Times Roman", Font.PLAIN, 25);
        Level1.setFont(buttonFont);
        Level2.setFont(buttonFont);
        Level3.setFont(buttonFont);

        /**
         * Call to the configureButton method for the level buttons.
         */
        configureButton(Level1, Color.green);
        configureButton(Level2, Color.yellow);
        configureButton(Level3, Color.red);

        /**
         * adding the buttons to the button Panel
         */
        buttonPanel.add(Level1);
        buttonPanel.add(Level2);
        buttonPanel.add(Level3);

        /**
         * Location of the text and button panels which will
         * be on the main panel
         */
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        /**
         * Main panel is added to the Content Pane
         */
        obj.getContentPane().add(mainPanel);

        obj.pack();
        obj.setVisible(true);


        /**
         * ActionListeners for the three different buttons that will run a
         * different level depending on which button is pressed. These also
         * have a call to close for the clip object to stop the Title screen's
         * background music.
         */
        Level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameplay(obj, 1);
                clip.close();
            }
        });

        Level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //startGameplay(obj, 3, 7);
                startGameplay(obj,2);
                clip.close();
            }
        });

        Level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //startGameplay(obj, 4, 8);
                startGameplay(obj,3);
                clip.close();
            }
        });
    }
    // int row, int col

    /**
     * This method starts and creates the actual BrickBreaker game depending
     * on the button and also removes the title screen before switching
     * the the game being displayed
     * @param obj JFrame object where the buttons and labels are held in
     * @param level the initial levels for the game
     */
    public static void startGameplay(JFrame obj, int level){
        Gameplay gameplay;
        try {
            gameplay = new Gameplay(level);
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        obj.getContentPane().removeAll();
        obj.add(gameplay);
        obj.revalidate();
        obj.repaint();
        gameplay.requestFocusInWindow();
    }

    /**
     * Configurations and settings for the button such as their size,
     * background color, and opacity
     * @param button the level buttons
     * @param background the background color for the buttons
     */
    public static void configureButton(JButton button, Color background){
        button.setPreferredSize(new Dimension(225, 75));
        button.setBackground(background);
        button.setOpaque(true);
    }

}