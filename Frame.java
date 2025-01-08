import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Frame extends JFrame implements KeyListener {

    private Canvas _canvas;
    private boolean _control = false;

    private List<ColourKey> _keys = new ArrayList<ColourKey>();

    private JPanel _panel;
    private JLabel _print;
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

        _panel = new JPanel();
        _panel.setBackground(new Color(0, true));
        _panel.setBounds(0, 0, dm.getWidth(), dm.getHeight());
        _panel.setLayout(new BorderLayout());
        _panel.setOpaque(false);

        _print = new JLabel("Loaded");
        _print.setFont(new Font("Consolas", Font.PLAIN, 24));
        _print.setBackground(new Color(0, 0, 0, 0));
        _print.setForeground(new Color(255, 255, 255, 255));

        _panel.add(_print, BorderLayout.PAGE_START);

        _layers.add(_panel, 1);

        addKeyListener(this);

        pack();

        loadSettings();
    }

    private void loadSettings () {
        File file = new File("settings.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();

                FileWriter write = new FileWriter("settings.txt");
                write.write("key:49:#FFFF00\nkey:50:#FF0000\nkey:51:#00FF00\nkey:52:#0000FF\nweight:5");
                write.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get("settings.txt"));

            for (String line : lines) {
                if (line.equals("")) { continue; }
                String[] parts = line.split(":");

                switch (parts[0]) {
                    case "key":
                        int key = Integer.parseInt(parts[1]);
                        Color colour = Color.decode(parts[2]);

                        _keys.add(new ColourKey(colour, key));

                        break;
                    case "weight":
                        _canvas.setWeight(Integer.parseInt(parts[1]));

                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (_keys.size() > 0) {
            SetColour(_keys.get(0).getColour());
        }
    }

    private void SetColour (Color colour) {
            _print.setText(String.format("#%02X%02X%02X%02X", 
                colour.getRed(), 
                colour.getGreen(), 
                colour.getBlue(), 
                colour.getAlpha()
            ));
            _print.setForeground(colour);
            _canvas.setColour(colour);
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

        for (int i = 0; i < _keys.size(); i++) {
            if (e.getKeyCode() == _keys.get(i).getKey()) {
                SetColour(_keys.get(i).getColour());
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
