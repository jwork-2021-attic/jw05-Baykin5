package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;
import network.NIOServer;

public class ServerScreen implements Screen {

    public ServerScreen() throws IOException{
        NIOServer nioServer=new NIOServer("localhost", 9093);
        nioServer.startServer();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Server Running...",1,1);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        return this;
    }

    @Override
    public void Refresh() {
        
    }
    
}
