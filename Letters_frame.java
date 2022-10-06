import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Letters_frame extends JFrame implements ActionListener {

    GameFrame gameFrame;

    /// The pairs of the same letters in the letters panel
    int NUM_PAIRS = 0;

    /// For the timer
    private static final int TIME_OUT = 1;
    private int count = TIME_OUT;
    private final Timer timer = new Timer(1000, this);

    //////
    JPanel letters_panel;
    JPanel labelakia;

    //////
    JLabel letters_label;
    static ArrayList <Character> letterLists = new ArrayList<>(); // only one time this array is going to be initialized
    static int check = 0;

    public Letters_frame() {
        letters_panel = new JPanel();
        letters_panel.setLayout(new GridLayout(10, 10));

        this.setTitle("Watch FAST!!!");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.add(letters_panel);

        Random rand = new Random();
        /// This is for the Color...
        int upperBound = 255;
        int x1 = -1;
        int x2 = -1;
        int x3 = -1;
        while (true) {
            x1 = rand.nextInt(upperBound);
            x2 = rand.nextInt(upperBound);
            x3 = rand.nextInt(upperBound);
            if (x1 != 0 && x2 != 0 && x3 != 0) {
                break;
            }
        }

        char lett1;
        char lett2;
        /* CHECK VARIABLE --> IT CHECKS IF THE LETTERS FOR THE LABELS HAS BEEN ALREADY CREATED,
           FOR EXAMPLE IN THE FIRST TIME THAT THE USER IS GONNA PRESS THE MOUSE BUTTON TO SEE THE
           HIDDEN FRAME THE VARIABLE HAS THE VALUE OF 0 AFTER THAT THE VARIABLE IS GOING TO BE 1,
           AND THE LETTERS WILL NOT BE CREATED AGAIN, (WE WANT THE LETTERS TO INITIALIZE ONLY ONE TIME
           AND BE THE SAME AS THE GAME CONTINUE TO RUN)
         */
        for (int i = 0; i < 100; i++) {

            if(check == 0) // here we will get only in the first time...
            {
                /// Here we create the letters that we add in the letters_label...
                double letter1;
                double letter2;
                double letter3;
                letter1 = Math.random() * (90 - 65 + 1) + 65;
                letter2 = Math.random() * (90 - 65 + 1) + 65;
                lett1 = (char) letter1;
                lett2 = (char) letter2;
                letterLists.add(lett1); // here we add the letter that have been generated to the list...
            }
            else
            {
                lett1 = 'A'; // i add this because it says that the value may have not initialized...
                String temp = String.valueOf(letterLists.get(i));
                for(int j = 0;j < temp.length();j++)
                    lett1 = temp.charAt(j);
            }
            labelakia = new JPanel();
            labelakia.setSize(50, 50);
            labelakia.setBorder(BorderFactory.createLineBorder(Color.black));
            labelakia.setBackground(new Color(x1, x2, x3));

            /// This is with two letters...
            //letters_label = new JLabel(String.valueOf(lett1) + String.valueOf(lett2));
            /// This is for one letter... (this is better)
            letters_label = new JLabel(String.valueOf(lett1));
            labelakia.add(letters_label);
            letters_panel.add(labelakia);
        }
        findAllThePairs(letterLists); // find all the pairs in the letters frame...
        check = 1;
        this.setVisible(true);
        timer.start();
    }

    public int checkForSame(int index1, int index2)
    {
        if(letterLists.get(index1 - 1).equals(letterLists.get(index2 - 1)))
        {
            JOptionPane.showMessageDialog(null, "Correct, pairs left --> " + (NUM_PAIRS - gameFrame.TIMES_FOUND - 1));
            return 1;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "NOT Correct");
            return 0;
        }
    }

    public void findAllThePairs(ArrayList <Character> letterLists)
    {
        for(int i = 0;i < letterLists.size();i++)
        {
            for(int k = 0;k < letterLists.size();k++)
            {
                if(letterLists.get(i).equals(letterLists.get(k)))
                {
                    NUM_PAIRS++;
                    break; /// NOTE --> WE ADD THIS, BECAUSE WE WANT THE PLAYER TO FIND AT LEAST ONE PAIR OF EACH LETTER, BECAUSE IF ASK FOR THE PLAYER TO FIND ALL THE PAIRS THAT WOULD BE AROUND 500, VERY LARGE NUMBER...
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count--;
        if (count == 0) {
            closeFrame();
        }
        timer.restart();
    }

    public void closeFrame() {
        this.dispose();
    }
}
