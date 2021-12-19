package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;
import gamedata.Data;

public class LoadDataScreen implements Screen {


    boolean hasData;
    int state;
    Data data;

    public LoadDataScreen() throws IOException {
        hasData = false;
        state = 0;
        // 读取存档
        data=new Data();
        hasData=false;
        hasData=data.load("SaveData.txt");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        if (hasData) {
            terminal.write("New Game or Continue?", 0, 0);
            if (state == 0) {
                terminal.write((char) (26), 0, 2);
            } else
                terminal.write((char) (26), 0, 5);
            terminal.write("1: New Game", 3, 2);
            terminal.write("2: Continue", 3, 5);
        } else {
            terminal.write("There is no old game record.", 0, 0);
            terminal.write((char) (26), 0, 2);
            terminal.write("1: New Game", 3, 2);
        }


    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (state==0){
                    return new SelectScreen();
                }
                else
                    return new WorldScreen(data);
            case KeyEvent.VK_UP:
                state=0;
                return this;
            case KeyEvent.VK_DOWN:
                if (hasData)
                    state=1;
                return this;
            default:
                return this;
        }
    }

    public void Refresh() {

    }
}
