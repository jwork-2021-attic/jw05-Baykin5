package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;
import network.NIOServer;

public class ServerScreen implements Screen {
    NIOServer nioServer;
    boolean sign = false;

    public ServerScreen() throws IOException {
        nioServer = new NIOServer("localhost", 9093);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        if (sign)
            terminal.write("Server Running...", 1, 1);
        else
            terminal.write("Press any key to start Server", 1, 1);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        sign=true;
        nioServer.startServer();
        return this;
    }

    @Override
    public void Refresh() {

    }

}
