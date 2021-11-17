package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Calabash Adventure", 23, 10);
        terminal.write("Press Enter to continue", 22, 30);
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
