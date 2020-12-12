package treamcode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import static org.firstinspires.ftc.teamcode.robot.WoENrobot.*;


public class keyListener extends JFrame implements KeyListener{

    double x = 0;
    double y = 0;
    double turn = 0;
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_D)
            x = 1;
        if(e.getKeyCode()== KeyEvent.VK_A)
            x = -1;
        if(e.getKeyCode()== KeyEvent.VK_S)
            y = -1;
        if(e.getKeyCode()== KeyEvent.VK_W)
            y = 1;
       if(e.getKeyCode()== KeyEvent.VK_E)
            turn = 1;
        if(e.getKeyCode()== KeyEvent.VK_Q)
            turn = -1;
        drivetrain.setRobotVelocity(y,x,turn);
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_D)
            x = 0;
        if(e.getKeyCode()== KeyEvent.VK_A)
            x = 0;
        if(e.getKeyCode()== KeyEvent.VK_S)
            y = 0;
        if(e.getKeyCode()== KeyEvent.VK_W)
            y = 0;
        if(e.getKeyCode()== KeyEvent.VK_E)
            turn = 0;
        if(e.getKeyCode()== KeyEvent.VK_Q)
            turn = 0;
        drivetrain.setRobotVelocity(y,x,turn);
    }
    public void keyTyped(KeyEvent e) {
    }

    public keyListener(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public static void main() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            keyListener frame = new keyListener();
            frame.setTitle("Square Move Practice");
            frame.setResizable(false);
            frame.setSize(600, 600);
            frame.setMinimumSize(new Dimension(600, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}