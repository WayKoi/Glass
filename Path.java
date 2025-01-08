import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Point> _points = new ArrayList<>();
    private List<Color> _colours = new ArrayList<>();

    private float _weight = 4;

    public Path (float weight) {
        _weight = weight;
    }

    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(_weight));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int i = 1; i < _points.size(); i++) {
            Point from = _points.get(i - 1);
            Point to = _points.get(i);

            g.setColor(_colours.get(i - 1));
            g.drawLine(from.x, from.y, to.x, to.y);
        }
    }

    public void add(Point point, Color colour, Graphics2D g) {
        if (_points.size() > 0 && _colours.size() > 0 && g != null) {
            Point prev = _points.get(_points.size() - 1);
            Color prevColour = _colours.get(_colours.size() - 1);

            g.setStroke(new BasicStroke(4));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(prevColour);
            g.drawLine(prev.x, prev.y, point.x, point.y);
        }
        
        _points.add(point);
        _colours.add(colour);
    }

    public void clear() {
        _points.clear();
        _colours.clear();
    }
}
