package threadController;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class RefreshRunnable implements Runnable{
    
    JFrame frame;

    public RefreshRunnable(JFrame frame){
        this.frame=frame;
    }

    public void run(){
        try{
            while(true){
                //System.out.println("x");
                TimeUnit.MILLISECONDS.sleep(30);
                frame.repaint();
            }
        }
        catch(InterruptedException e){
            System.out.println("Main Error");
        }
    }
}
