import java.awt.Color;

public class ColourKey {
    private Color _colour = new Color(255, 255, 255, 255);
    private int _key = 0;

    public ColourKey(Color colour, int key) {
        _colour = colour;
        _key = key;
    }

    public Color getColour() {
        return _colour;
    }

    public void setColour(Color value) {
        if (value == null) { return; }
        _colour = value;
    }

    public int getKey() {
        return _key;
    }

    public void setKey(int value) {
        _key = value;
    }
}
