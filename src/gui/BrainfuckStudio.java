package gui;

import brainfuck.Brainfuck;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class BrainfuckStudio extends JFrame {

    public static final int DEFAULT_WIDTH = 1080;
    public static final int DEFAULT_HEIGHT = 700;
    public static final String TITLE = "Brainfuck Studio";

    public static final String FONT = "Pixeltype";
    public static final int FONT_SIZE = 26;

    private JMenuBar menuBar;
    private JMenu menuFile, menuAbout;
    private JMenuItem menuItemNew, menuItemOpen, menuItemSave, menuItemSaveAs, menuItemInfo;
    private String fileName, filePath; // When opening a file

    private JTabbedPane tabbedPane;
    private JPanel upPanel, downPanel;
    private JSplitPane splitPane;

    private ToolBar toolbar;

    private Brainfuck brainfuck;
    private Console console;
    private Debugger debugger;

    private ImageIcon icon, tabIcon, crossIcon;

    public BrainfuckStudio() throws IOException, URISyntaxException, FontFormatException {
        tabIcon = new ImageIcon(this.getClass().getResource("/resources/bfFile.png"));
        crossIcon = new ImageIcon(this.getClass().getResource("/resources/x.png"));
        icon = new ImageIcon(this.getClass().getResource("/resources/icon.png"));
        frameProperties();
    }

    private void frameProperties() throws IOException, URISyntaxException, FontFormatException {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() throws IOException, URISyntaxException, FontFormatException {
        brainfuck = new Brainfuck();
        console = new Console();
        console.setBrainfuck(brainfuck);
        brainfuck.setConsole(console);
        debugger = new Debugger();
        brainfuck.setDebugger(debugger);
        loadFont();
        loadMenuBar();
        loadPanels();
    }

    private void createTab(final String text) throws BadLocationException {
        EditorTab tab = new EditorTab(text);
        tabbedPane.add(tab);
        int index = tabbedPane.getTabCount() - 1;
        tabbedPane.setIconAt(index, tabIcon);

        JButton closeButton = new JButton();
        closeButton.setOpaque(false);
        closeButton.setIcon(crossIcon);
        TabComponent tabComponent = new TabComponent(index, tabIcon, "untitled", closeButton);
        tabbedPane.setTabComponentAt(index, tabComponent);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!tabComponent.isSaved()) {
                    // Ask to the user if remove

                    // if not, return;
                }
                tabbedPane.remove(tab);
            }
        });
        tab.getEditor().setTabComponent(tabComponent);
        tabbedPane.setSelectedComponent(tab);
        tab.getEditor().requestFocusInWindow();
    }

    private void open() throws IOException, BadLocationException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        File file = fileChooser.getSelectedFile();
        filePath = file.getPath();
        fileName = file.getName();
        //read file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line, code = "";
        while((line = bufferedReader.readLine()) != null) {
            code += line + "\n";
        }
        bufferedReader.close();
        // Open in editor
        createTab(code);

        EditorTab editorTab = (EditorTab)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        editorTab.setFilePath(file.getPath());
        editorTab.setFileName(file.getName());

        TabComponent tabComponent = (TabComponent) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
        tabComponent.setTitle(file.getName());
        tabComponent.setSave(true);
    }

    private void saveAs() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(fileChooser);
        File file = fileChooser.getSelectedFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        EditorTab editorTab = (EditorTab)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        editorTab.setFilePath(file.getPath());
        editorTab.setFileName(file.getName());
        bufferedWriter.write(editorTab.getEditor().getText());
        bufferedWriter.close();
        TabComponent tabComponent = (TabComponent) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
        tabComponent.setTitle(file.getName());
        tabComponent.setSave(true);
    }

    private void save() throws IOException {
        EditorTab editorTab = (EditorTab)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        if(editorTab.getFilePath() == null)
            saveAs();
        else {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(editorTab.getFilePath())));
            bufferedWriter.write(editorTab.getEditor().getText());
            bufferedWriter.close();
        }
        TabComponent tabComponent = (TabComponent) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
        tabComponent.setTitle(editorTab.getFileName());
        tabComponent.setSave(true);
    }

    private void loadMenuBar() {
        menuBar = new JMenuBar();
        // Menu file
        menuFile = new JMenu("File");

        menuItemNew = new JMenuItem("New");
        menuItemNew.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    createTab("");
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
        menuFile.add(menuItemNew);

        menuItemOpen = new JMenuItem("Open");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    open();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
        menuFile.add(menuItemOpen);

        menuItemSave = new JMenuItem("Save");
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    save();
                } catch (IOException e) {
                    console.log("Couldn't save file");
                }
            }
        });
        menuFile.add(menuItemSave);

        menuItemSaveAs = new JMenuItem("Save as");
        menuItemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    saveAs();
                } catch (IOException e) {
                    console.log("Couldn't save file");
                }
            }
        });
        menuFile.add(menuItemSaveAs);

        // Menu about
        menuAbout = new JMenu("About");

        menuItemInfo = new JMenuItem("Info");
        menuItemInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                About about = new About();
            }
        });
        menuAbout.add(menuItemInfo);

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
                EditorTab tab = (EditorTab) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
                brainfuck.execute(tab.getEditor().getText());
            }
        });
        buttonRun.setIcon(new ImageIcon(this.getClass().getResource("/resources/play.png")));
        toolbar.add(buttonRun);

        JButton buttonStop = new JButton();
        buttonStop.setIcon(new ImageIcon(this.getClass().getResource("/resources/stop.png")));
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                brainfuck.setRunning(false);
                debugger.reset();
            }
        });
        toolbar.add(buttonStop);

        upPanel.add(toolbar);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        upPanel.add(tabbedPane);

        downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel,BoxLayout.Y_AXIS));
        downPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        downPanel.add(debugger);
        TransparentScrollPane scrollPaneConsole = new TransparentScrollPane(console);
        downPanel.add(scrollPaneConsole);

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

    private void showFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for(String fontName : ge.getAvailableFontFamilyNames())
            System.out.println(fontName);
    }

    private void loadFont() throws IOException, FontFormatException, URISyntaxException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        URL resource = this.getClass().getResource("/fonts/Pixeltype.ttf");
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(resource.toURI())));
        setUIFont(new FontUIResource(new Font(FONT, 0, FONT_SIZE)));
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
