import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements MouseListener, ActionListener {

    /// Array list with pairs that the player has found during the game
    ArrayList<String> pairsFound  = new ArrayList<String>();

    /// The times the player have found a pair
    static int TIMES_FOUND = 0;

    /// This is the time the letters Frame has been open...
    int TIMES_PRESSED = 20; // let say for now that we will initialize this with the value 20...

    /// Letters Frame
    Letters_frame letters_frame;

    /// Game Frame
    JPanel game_panel;
    JPanel game_panelakia;

    /// Ιndexes
    JLabel index_label;

    /// Control Panel
    JFrame control_frame;
    JPanel control_panel;
    JButton check_same_sumbit;
    JTextField text_first_number, text_second_number;
    JLabel number_of_pairs; // this is the number of the pairs that the player must find... (NOTE --> is the minimum number of pairs)

    /// Numbers for the Colors of the panelakia
    int x1, x2, x3;

    public GameFrame() {
        /// Control Panel
        control_panel = new JPanel();
        control_panel.setSize(300, 90);

        /// Game panel
        game_panel = new JPanel();
        game_panel.setLayout(new GridLayout(10, 10));
        game_panel.addMouseListener(this);

        this.setTitle("THE MAZE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.add(game_panel);

        /// Control Frame
        control_frame = new JFrame("Control Panel");
        control_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        control_frame.setResizable(false);
        control_frame.setUndecorated(false);
        control_frame.setBounds(340,500, 300, 90);

        check_same_sumbit = new JButton("Sumbit");
        check_same_sumbit.setSize(100, 80);
        check_same_sumbit.setFocusable(false);
        check_same_sumbit.addActionListener(this);

        text_first_number = new JTextField();
        text_first_number.setPreferredSize(new Dimension(100, 20));

        text_second_number = new JTextField();
        text_second_number.setPreferredSize(new Dimension(100, 20));

        createLettersFrameInvisible(); /// we do that, so we can get the num of pairs before the creation of the letters frame... (NOTE --> NOW THE letters frame we will be created but it will not be visible)

        number_of_pairs = new JLabel("Number of pairs you must find --> " + String.valueOf(letters_frame.NUM_PAIRS));
        number_of_pairs.setSize(20,10);

        control_panel.add(check_same_sumbit);
        control_panel.add(text_first_number);
        control_panel.add(text_second_number);
        control_panel.add(number_of_pairs);
        control_frame.add(control_panel);


        /// create JPanelakia...
        Random rand = new Random();
        int upperBound = 255;
        x1 = -1;
        x2 = -1;
        x3 = -1;
        for (int i = 1; i <= 100; i++) {
            while (true) { // we dont want black because the number are black...
                x1 = rand.nextInt(upperBound);
                x2 = rand.nextInt(upperBound);
                x3 = rand.nextInt(upperBound);
                if (x1 != 0 && x2 != 0 && x3 != 0) {
                    break;
                }
            }
            game_panelakia = new JPanel();
            game_panelakia.setSize(50, 50);
            game_panelakia.setBackground(new Color(x1, x2, x3));

            index_label = new JLabel(String.valueOf(i));
            index_label.setBackground(Color.BLACK);

            game_panelakia.add(index_label);
            game_panel.add(game_panelakia);
        }
        control_frame.setVisible(true);
        this.setVisible(true);
    }

    /// Function to creat the letters_Frame...
    private Letters_frame createLettersFrameInvisible()
    {
        letters_frame = new Letters_frame();
        letters_frame.setVisible(false);
        return letters_frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == check_same_sumbit)
        {
            int index1 = Integer.parseInt(text_first_number.getText());
            int index2 = Integer.parseInt(text_second_number.getText());

            if((checkIfTheLetterIsFound(pairsFound, String.valueOf(letters_frame.letterLists.get(index1 - 1))) == 1)) {  // if we have already found the letter, we will stop the function...
                JOptionPane.showMessageDialog(null, "You have already found the letter");
                return;
            }
            if (letters_frame.checkForSame(index1, index2) == 1) {
                checkForWin();
                pairsFound.add(String.valueOf(letters_frame.letterLists.get(index1 - 1))); /// NOTE --> index - 1
                TIMES_FOUND++;
            }
        }
    }

    public int checkIfTheLetterIsFound(ArrayList <String> pairsFound, String temp)
    {
        for(int i = 0;i < pairsFound.size();i++)
        {
            if(temp.equals(pairsFound.get(i)))
            {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        TIMES_PRESSED--;
        System.out.println("TIMES_PRESSED --> " + TIMES_PRESSED);
        if(TIMES_PRESSED != 0)
            createLettersFrameInvisible().setVisible(true); /// make the letters Frame visible...
        else
        {
            /* Here the player has reached the minimum times that he can click the game frame and see the letters frame,
            so we are going to just RESTART the game WITH THE SAME letters frame (you can also create a new letters frame) */
            JOptionPane.showMessageDialog(null, "GAME OVER --> HIDDEN LETTERS ARE GOING TO CHANGE, you have reached the minimum number of times you can see the hidden letters");
            letters_frame.NUM_PAIRS = 0;
            pairsFound.clear();
            TIMES_FOUND = 0;
            letters_frame = new Letters_frame(); // create a new letters frame because the player lost...
            letters_frame.letterLists.clear(); // also clear the letters list, because the list will have the previous letters...
            letters_frame.check = 0; // also add the check variable to 0...
            TIMES_PRESSED = 20; // set the TIMES_FRAME again to 10...
        }
    }

    /* Here we check for win, if the player have found all the pairs, the game will restart and a new game frame will be created, also a new
       control frame will be created and the list with the letter will be empty.
     */
    public void checkForWin()
    {
        if(pairsFound.size() == letters_frame.NUM_PAIRS)
        {
            JOptionPane.showMessageDialog(this,"You WON!!!","Alert",JOptionPane.WARNING_MESSAGE);
            /// Restart all the game because the player won
            this.dispose(); // close the game panel...
            control_frame.dispose();
            pairsFound.clear();
            TIMES_FOUND = 0;
            letters_frame.letterLists.clear(); // also clear the letters list, because the list will have the previous letters...
            letters_frame.NUM_PAIRS = 0;
            letters_frame.check = 0; // also add the check variable to 0...
            TIMES_PRESSED = 20; // set the TIMES_FRAME again to 10...
            GameFrame gameFrame = new GameFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
