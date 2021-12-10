package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class StartScreen implements Screen {

    int state;
    //0:singleGame 1:DoubleGame

    public StartScreen(){
        state=0;
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Calabash Adventure", 6, 5);
        terminal.write("Use Up/Down Key to select game mode", 0, 20);
        terminal.write("Single Game",2,22);
        if (state==0)
            terminal.write((char)(26),0,22);
        else
            terminal.write((char)(26),0,24);
        terminal.write("Double Game",2,24);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (state==0)
                    return new LoadDataScreen(); //Single Game
                else
                    return new SelectScreen();  
            case KeyEvent.VK_W:
                state=0;
                return this;
            case KeyEvent.VK_S:
                state=1;
                return this;
            default:
                return this;
        }
    }

    public void Refresh(){

    }


}
