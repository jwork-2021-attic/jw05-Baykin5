package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Died...", 20,5 );
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }

    public void Refresh(){

    }
}
