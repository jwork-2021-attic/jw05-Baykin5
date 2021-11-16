package screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SelectScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Please select your calabash", 0, 1);
        terminal.write("1: Dawa   Skill: Strengthen", 0, 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                return new WorldScreen(1);
            default:
                return this;
        }
    }

    public void Refresh(){

    }
}
