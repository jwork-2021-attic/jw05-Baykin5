package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;
import network.NIOServer;

public class ServerScreen implements Screen {
    NIOServer nioServer;
    public ServerScreen() throws IOException{
        nioServer=new NIOServer("localhost", 9093);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Server Running...",1,1);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        nioServer.startServer();
        return this;
    }

    @Override
    public void Refresh() {
        
    }
    
}
