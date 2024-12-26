import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {

    private Canvas _canvas;
    private boolean _control = false;

    private ColourKey[] _keys = new ColourKey[] {
        new ColourKey(new Color(255, 255, 0, 255), KeyEvent.VK_1),
        new ColourKey(new Color(255, 0, 0, 255), KeyEvent.VK_2),
        new ColourKey(new Color(0, 255, 0, 255), KeyEvent.VK_3),
        new ColourKey(new Color(0, 0, 255, 255), KeyEvent.VK_4)
    };

    private JLayeredPane _layers;

    public Frame() {
        DisplayMode dm = getGraphicsConfiguration().getDevice().getDisplayMode();

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 100));
        setAlwaysOnTop(true);
        setIgnoreRepaint(true);
        
        setLocation(new Point(0, 0));
        setPreferredSize(new Dimension(
            dm.getWidth(), dm.getHeight()
        ));

        _layers = new JLayeredPane();
        _layers.setBounds(0, 0, dm.getWidth(), dm.getHeight());
        _layers.setOpaque(false);
        _layers.setBackground(new Color(0, true));
        add(_layers, BorderLayout.CENTER);

        _canvas = new Canvas();
        _canvas.setBounds(0, 0, dm.getWidth(), dm.getHeight());
        _layers.add(_canvas, 0);

        addKeyListener(this);

        pack();
    }

    public void Run() {
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            _control = true;
        }

        if (_control) {
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                _canvas.Pop();
            }
        }

        for (int i = 0; i < _keys.length; i++) {
            if (e.getKeyCode() == _keys[i].getKey()) {
                _canvas.setColour(_keys[i].getColour());
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            _control = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setVisible(false);
            dispose();
        } else if (_control) {
            // Control keys
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
