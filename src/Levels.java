import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public interface Levels {
    public void paintComponent(Graphics g);



    public void actionPerformed(ActionEvent e);



    public void keyTyped(KeyEvent e);


    public void keyPressed(KeyEvent e);

    //test :3
    public void moveRight();
    public void moveLeft();

    public void keyReleased(KeyEvent e);
}
