package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;

public class SelectScreen implements Screen {


    int calabashType;
    public SelectScreen(){
        calabashType=1;
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Please select your calabash", 0, 0);
        switch(calabashType){
            case 1:
                terminal.write((char)(26),0,2);
                break;
            case 2:
                terminal.write((char)(26),0,5);
                break;
            case 3:
                terminal.write((char)(26),0,8);
                break;
            default:
                terminal.write((char)(26),0,2);
                break;
        }
        terminal.write("1: Dawa", 3, 2);
        //terminal.write("   <Skill>: Strengthen attack 20 for some time.", 0, 3);
        //terminal.write("   <CD>: 20s", 0, 4); 

        terminal.write("2: Erwa   ", 3, 5 );
        //terminal.write("   <Skill>: Deals some damage to the nearest enemy.", 0,6 );
        //terminal.write("   <CD>:3s", 0, 7 );


        terminal.write("3: Sanwa", 3, 8 );
        //terminal.write("   <Skill>:Reduce the upcoming damage and self-heal when near-death.", 0,9 );
        //terminal.write("   <CD>:20s", 0, 10 );
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new WorldScreen(calabashType);
            case KeyEvent.VK_UP:
                if (calabashType>1)
                    calabashType-=1;
                return this;
            case KeyEvent.VK_DOWN:
                if (calabashType<3)
                    calabashType+=1;
                return this;
            default:
                return this;
        }
    }

    public void Refresh(){

    }
}
