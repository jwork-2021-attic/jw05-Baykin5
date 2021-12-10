package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Died...", 10,5 );
        terminal.write("Press Enter to Restart.", 2, 10);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new SelectScreen();
            default:
                return this;
        }
    }

    public void Refresh(){

    }
}
