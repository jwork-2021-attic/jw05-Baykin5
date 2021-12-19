package asciiPanel;

/**
 * This class holds provides all available Fonts for the AsciiPanel. Some
 * graphics are from the Dwarf Fortress Tileset Wiki Page
 * 
 * @author zn80
 *
 */
public class AsciiFont {

    public static final AsciiFont CP437_9x16 = new AsciiFont("cp437_9x16.png", 9, 16);

    public static final AsciiFont Baykin_15_15 = new AsciiFont("Baykin_15x15.png", 15, 15);
    public static final AsciiFont Baykin_30_30 = new AsciiFont("Baykin_30x30.png", 30, 30);

    private String fontFilename;

    public String getFontFilename() {
        return fontFilename;
    }

    private int width;

    public int getWidth() {
        return width;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public AsciiFont(String filename, int width, int height) {
        this.fontFilename = filename;
        this.width = width;
        this.height = height;
    }
}