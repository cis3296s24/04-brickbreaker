import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main  {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        JFrame obj = new JFrame();

        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Brick Breaker: SPACE", SwingConstants.CENTER);
        Font heading = new Font("Times Roman", Font.BOLD, 20);
        title.setFont(heading);
        title.setPreferredSize(new Dimension(700, 50));
        title.setOpaque(true);
        title.setBackground(Color.decode("#f0e9e9"));
        obj.getContentPane().add(title, BorderLayout.PAGE_START);

        JPanel pane = new JPanel(new FlowLayout());
        pane.setPreferredSize(new Dimension(700,550));
        pane.setBackground(Color.PINK);
        pane.setOpaque(true);
        JLabel welcomeMessage = new JLabel("Please select a level\n\n");
        pane.add(welcomeMessage);
        obj.getContentPane().add(pane, BorderLayout.CENTER);


        JButton Level1 = new JButton("Level 1");
        JButton Level2 = new JButton("Level 2");
        JButton Level3 = new JButton("Level 3");

        Level1.setPreferredSize(new Dimension(75, 25));
        Level1.setBackground(Color.green);
        Level2.setPreferredSize(new Dimension(75, 25));
        Level2.setBackground(Color.yellow);
        Level3.setPreferredSize(new Dimension(75, 25));
        Level3.setBackground(Color.red);

        pane.add(Level1);
        pane.add(Level2);
        pane.add(Level3);

        obj.pack();
        obj.setVisible(true);

            //Trying to add title screen music
//        File song = new File(("./src/titleMusic.wav"));
//        AudioInputStream audioIn = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioIn);
//        clip.loop(Clip.LOOP_CONTINUOUSLY);

        Level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameplay(obj, 1);
            }
        });

        Level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameplay(obj, 5);
            }
        });

        Level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameplay(obj, 10);
            }
        });

    }
    public static void startGameplay(JFrame obj, int level){
        Gameplay gameplay;
        try{
//            if(level == 1){
//                gameplay = new Level1();
//            }
//            else if(level == 2){
//                gameplay = new Level5();
//            }
//            else{
//                gameplay = new Level10();
//            }
            gameplay = new Gameplay(level);

        }catch (IOException ex){
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

}