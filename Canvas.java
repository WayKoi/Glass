import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
    
    private Image _img = null;
    private Color _draw = new Color(255, 255, 255, 255);
    private List<Path> _paths = new ArrayList<>();
    private Path _current = null;

    private float _weight = 4;

    public void setWeight (float weight) {
        _weight = (float) Math.max(0.1, weight);
    }

    public void setColour(Color colour) {
        _draw = colour;
    }
    
    public Canvas () {
        // setOpaque(false);
        setDoubleBuffered(true);
        setBackground(new Color(0, 0, 0, 0));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void Pop() {
        int size = _paths.size();
        if (size <= 0) { return; }

        _paths.get(size - 1).clear(); // janitor
        _paths.remove(size - 1);

        reRenderImage();
        repaint();
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        if (_img == null) {
            _img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        g.drawImage(_img, 0, 0, null);
    }

    private void reRenderImage() {
        if (_img == null) { return; }

        setVisible(false);

        Graphics2D graph = (Graphics2D) _img.getGraphics();
        
        // Clear the image
        graph.setComposite(AlphaComposite.Clear);
        graph.fillRect(0, 0, getWidth(), getHeight());
        graph.setComposite(AlphaComposite.SrcOver);

        for (Path path : _paths) {
            path.render(graph);
        }

        graph.dispose();

        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        _current = new Path(_weight);
        
        Graphics2D graph = _img != null ? (Graphics2D) _img.getGraphics().create() : null;
        _current.add(e.getPoint(), _draw, graph);

        _paths.add(_current);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _current = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) { 
        Point next = e.getPoint();

        Graphics2D graph = _img != null ? (Graphics2D) _img.getGraphics().create() : null;
        _current.add(next, _draw, graph);
        graph.dispose();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) { }

}