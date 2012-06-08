package unSkooledEngine.text.designer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

public class MainWindow
{

    private JFrame frmUnskooledTextLevel;
    private ItemPanel itemPanel;


    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try
                {
                    MainWindow window = new MainWindow();
                    window.frmUnskooledTextLevel.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public MainWindow()
    {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frmUnskooledTextLevel = new JFrame();
        frmUnskooledTextLevel.setTitle("UnSkooled Text Level Editor");
        frmUnskooledTextLevel.setBounds(100, 100, 450, 300);
        frmUnskooledTextLevel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar mainFloatBar = new JToolBar();
        frmUnskooledTextLevel.getContentPane().add(mainFloatBar, BorderLayout.NORTH);

        JMenuBar mainMenuBar = new JMenuBar();
        mainFloatBar.add(mainMenuBar);

        JMenu fileDropMenu = new JMenu("File");
        mainMenuBar.add(fileDropMenu);

        JMenuItem newLevel = new JMenuItem("New Level");
        fileDropMenu.add(newLevel);

        JMenuItem loadLevel = new JMenuItem("Load Level");
        fileDropMenu.add(loadLevel);

        JMenuItem saveLevel = new JMenuItem("Save Level");
        fileDropMenu.add(saveLevel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frmUnskooledTextLevel.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JPanel globalPanel = new JPanel();
        tabbedPane.addTab("Global Panel", null, globalPanel, null);
        globalPanel.setLayout(null);


        itemPanel = new ItemPanel();
        itemPanel.setBounds(202, 11, 217, 120);
        globalPanel.add(itemPanel);

        JPanel roomsPanel = new JPanel();
        tabbedPane.addTab("Rooms Panel", null, roomsPanel, null);
    }
}
