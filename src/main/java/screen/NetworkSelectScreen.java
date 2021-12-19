package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;

public class NetworkSelectScreen implements Screen {

    int state;

    public NetworkSelectScreen() {
        state = 0;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Please choose: Server or Client?", 0, 0);
        switch (state) {
            case 0:
                terminal.write((char) (26), 0, 2);
                break;
            case 1:
                terminal.write((char) (26), 0, 5);
                break;
            case 2:
                terminal.write((char) (26), 0, 8);
                break;
            case 3:
                terminal.write((char) (26), 0, 11);
                break;
        }
        terminal.write("Server", 3, 2);
        terminal.write("Client1", 3, 5);
        terminal.write("Client2", 3, 8);
        terminal.write("Client3", 3, 11);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                switch (state) {
                    case 0:
                        return new ServerScreen();
                    case 1:
                        try {
                            return new WorldScreen("Client1");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            return new WorldScreen("Client2");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            return new WorldScreen("Client3");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            case KeyEvent.VK_UP:
                if (state > 0)
                    state--;
                return this;
            case KeyEvent.VK_DOWN:
                if (state < 3)
                    state++;
                return this;
            default:
                return this;
        }
    }

    public void Refresh() {

    }
}
