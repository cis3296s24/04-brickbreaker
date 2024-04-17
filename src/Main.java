import javax.sound.sampled.*;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;

public class Main  {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        File song = new File(this.getClass().getResource("backgroundMusic.wav"));
//        AudioInputStream audioIn = AudioSystem.getAudioInputStream(song);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioIn);
//        //clip.start();
//        clip.loop(Clip.LOOP_CONTINUOUSLY);

        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
        obj.setVisible(true);
    }
}