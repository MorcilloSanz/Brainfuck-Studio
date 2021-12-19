package gui;

import brainfuck.Brainfuck;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class BrainfuckStudio extends JFrame {

    public static final int DEFAULT_WIDTH = 1080;
    public static final int DEFAULT_HEIGHT = 700;
    public static final String TITLE = "Brainfuck Studio";

    private JMenuBar menuBar;
    private JMenu menuFile, menuAbout;
    private JMenuItem menuItemOpen, menuItemSave;

    private JPanel upPanel, downPanel;
    private JSplitPane splitPane;

    private ToolBar toolbar;
    private Editor editor;

    private Brainfuck brainfuck;

    public BrainfuckStudio() throws IOException, URISyntaxException, FontFormatException {
        frameProperties();
    }

    private void frameProperties() throws IOException, URISyntaxException, FontFormatException {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() throws IOException, URISyntaxException, FontFormatException {
        brainfuck = new Brainfuck();
        loadFont();
        loadMenuBar();
        loadPanels();
    }

    private void loadMenuBar() {
        menuBar = new JMenuBar();

        // Menu file
        menuFile = new JMenu("File");

        menuItemOpen = new JMenuItem("Open");
        menuFile.add(menuItemOpen);

        menuItemSave = new JMenuItem("Save");
        menuFile.add(menuItemSave);

        // Menu about
        menuAbout = new JMenu("About");

        menuBar.add(menuFile);
        menuBar.add(menuAbout);

        setJMenuBar(menuBar);
    }

    private void loadPanels() {
        upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.Y_AXIS));

        toolbar = new ToolBar();
        JButton buttonRun = new JButton();
        buttonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(editor.getText());
            }
        });
        buttonRun.setIcon(new ImageIcon(this.getClass().getResource("/resources/play.png")));
        toolbar.add(buttonRun);

        JButton buttonStop = new JButton();
        buttonStop.setIcon(new ImageIcon(this.getClass().getResource("/resources/stop.png")));
        toolbar.add(buttonStop);

        upPanel.add(toolbar);

        editor = new Editor();
        TransparentScrollPane scrollPaneEditor = new TransparentScrollPane(editor);
        upPanel.add(scrollPaneEditor);

        downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel,BoxLayout.Y_AXIS));


        splitPane = new JSplitPane(SwingConstants.HORIZONTAL, upPanel, downPanel);
        splitPane.setResizeWeight(0.8);
        this.getContentPane().add(splitPane);
    }

    private static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    private void loadFont() throws IOException, FontFormatException, URISyntaxException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        URL resource = this.getClass().getResource("/fonts/Cloude_Regular_1.02.otf");
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(resource.toURI())));
        /*
        for(String fontName : ge.getAvailableFontFamilyNames())
            System.out.println(fontName);
        */
        setUIFont(new FontUIResource(new Font("Cloude Regular 1.0", 0, 30)));
    }

    public static void main(String [] args) {
        StylesLoader.loadDark();
        try {
            BrainfuckStudio brainfuckStudio = new BrainfuckStudio();
        } catch (IOException | URISyntaxException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
