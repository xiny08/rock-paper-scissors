import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class RockPaperScissors implements ActionListener {

    JFrame frame = new JFrame("Rock Paper Scissors");
    private final String ROCK = "🪨";
    private final String PAPER = "📄";
    private final String SCISSORS = "✂️";

    private final String[] OPTIONS = {ROCK, PAPER, SCISSORS};

    private int userWins  = 0;
    private int compWins = 0;
    
    JLabel score;
    JLabel status;

    public RockPaperScissors() {
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel header = new JPanel(new GridLayout(2,1));
        JLabel title = new JLabel("Rock Paper Scissors", JLabel.CENTER);
        score = new JLabel("User: " + userWins + " Computer: " + compWins, JLabel.CENTER);
        status = new JLabel("Pick one", JLabel.CENTER);
        header.add(title);
        header.add(score);
        header.add(status);
        frame.add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        for (String option : OPTIONS) {
            JButton button = new JButton(option);
            button.addActionListener(this);
            button.setActionCommand(option);
            buttonPanel.add(button);
        }
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userChoice = e.getActionCommand();
        String compChoice = getCompChoice();
        determineWinner(userChoice, compChoice);
    }

    private String getCompChoice() {
        String choice = OPTIONS[new Random().nextInt(3)];
        return choice;
    }

    private void determineWinner(String user, String comp) {
        if (user.equals(comp)) {
            System.out.println(comp);
            System.out.println("Tie");
        }
        else if(user.equals(ROCK) && comp.equals(SCISSORS) ||
            user.equals(SCISSORS) && comp.equals(PAPER) ||
            user.equals(PAPER) && comp.equals(ROCK)) {
            System.out.println(comp);
            System.out.println("User wins");
            userWins++;
            updateUI();
        }
        else {
            System.out.println(comp);
            System.out.println("Comp wins");
            compWins++;
            updateUI();
        }
    }

    public void updateUI() {
        score.setText("User: " + userWins + " Computer: " + compWins);
    }

    public void display() {
        frame.setVisible(true);
    }

}
