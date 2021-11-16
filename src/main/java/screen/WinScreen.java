package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class WinScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Win!", 27,5 );
        terminal.write("Congratulations!", 23, 10);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }

    public void Refresh(){

    }
}
