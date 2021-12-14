import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import util.World;
import screen.Screen;
import screen.StartScreen;
import threadController.RefreshRunnable;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class Main extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public Main() {
        super();
        terminal = new AsciiPanel(50,28, AsciiFont.Baykin_15_15);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.Refresh();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {  
        try {
            screen = screen.respondToUserInput(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        new Thread(new RefreshRunnable(app)).start();
        //app.start();
    }

    /*
    void start(){
        try{
            while(true){
                //System.out.println("x");
                TimeUnit.MILLISECONDS.sleep(50);
                screen.Refresh();
                repaint();
            }
        }
        catch(InterruptedException e){
            System.out.println("Main Error");
        }
    }
    */
}
