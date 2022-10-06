import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {

    /// Start Menu...
    JPanel startPanel;
    JButton startMenu_start, startMenu_exit;

    public StartFrame()
    {
        /// JPanel
        startPanel = new JPanel();
        startPanel.setBackground(Color.darkGray);

        // JButton
        startMenu_start = new JButton("Start");
        startMenu_start.setFocusable(false);
        startMenu_start.setSize(100,100);

        startMenu_exit = new JButton("Exit");
        startMenu_exit.setFocusable(false);
        startMenu_exit.setSize(100,100);

        startMenu_start.addActionListener(this);
        startMenu_exit.addActionListener(this);

        startPanel.add(startMenu_start);
        startPanel.add(startMenu_exit);

        this.setTitle("THE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(205,70);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(startPanel);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == startMenu_start)
        {
            this.dispose();
            GameFrame gameFrame = new GameFrame();
        }
        if(e.getSource() == startMenu_exit)
        {
            this.dispose();
        }
    }
}
