import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main  {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
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


        JFrame obj = new JFrame();

        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Brick Breaker: SPACE", SwingConstants.CENTER);
        Font heading = new Font("Times Roman", Font.BOLD, 50);
        title.setFont(heading);
        title.setPreferredSize(new Dimension(700, 245));
        title.setOpaque(true);
        title.setBackground(Color.decode("#749cbd"));
        obj.getContentPane().add(title, BorderLayout.PAGE_START);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        textPanel.setPreferredSize(new Dimension(700,355));
        textPanel.setBackground(Color.decode("#749cbd"));
        textPanel.setOpaque(true);
        JLabel welcomeMessage = new JLabel(" Select a Level\n\n");
        welcomeMessage.setFont(new Font("Times Roman", Font.BOLD, 25));
        textPanel.add(welcomeMessage);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#749cbd"));
        buttonPanel.setOpaque(true);

        JButton Level1 = new JButton("Level 1");
        JButton Level2 = new JButton("Level 2");
        JButton Level3 = new JButton("Level 3");

        Level1.setFont(new Font("Times Roman", Font.PLAIN, 25));
        Level2.setFont(new Font("Times Roman", Font.PLAIN, 25));
        Level3.setFont(new Font("Times Roman", Font.PLAIN, 25));

        Level1.setPreferredSize(new Dimension(225, 75));
        Level1.setBackground(Color.green);
        Level1.setOpaque(true);

        Level2.setPreferredSize(new Dimension(225, 75));
        Level2.setBackground(Color.yellow);
        Level2.setOpaque(true);

        Level3.setPreferredSize(new Dimension(225, 75));
        Level3.setBackground(Color.red);
        Level3.setOpaque(true);

        buttonPanel.add(Level1);
        buttonPanel.add(Level2);
        buttonPanel.add(Level3);

        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        obj.getContentPane().add(mainPanel);

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
                clip.close();
            }
        });

        Level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameplay(obj, 10);
                clip.close();
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