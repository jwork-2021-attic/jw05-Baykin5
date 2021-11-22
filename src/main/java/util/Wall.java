package util;

import asciiPanel.AsciiPanel;

public class Wall extends Thing {

    public Wall(World world) {
        super(AsciiPanel.white, (char) 177, world);
    }

}
