package screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SelectScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Please select your calabash", 0, 0);
        terminal.write("1: Dawa", 0, 2);
        terminal.write("   <Skill>: Strengthen attack 20 for some time.", 0, 3);
        terminal.write("   <CD>: 20s", 0, 4); 

        terminal.write("2: Erwa   ", 0, 5 );
        terminal.write("   <Skill>: Deals some damage to the nearest enemy.", 0,6 );
        terminal.write("   <CD>:3s", 0, 7 );


        terminal.write("3: Sanwa", 0, 8 );
        terminal.write("   <Skill>:Reduce the upcoming damage and self-heal when near-death.", 0,9 );
        terminal.write("   <CD>:20s", 0, 10 );
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                return new WorldScreen(1);
            case KeyEvent.VK_2:
                return new WorldScreen(2);
            case KeyEvent.VK_3:
                return new WorldScreen(3);

            default:
                return this;
        }
    }

    public void Refresh(){

    }
}
