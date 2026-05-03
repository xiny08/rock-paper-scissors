import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class RockPaperScissors implements ActionListener {

    JFrame frame = new JFrame("Rock Paper Scissors");
    private final String ROCK = "🪨";
    private final String PAPER = "📄";
    private final String SCISSORS = "✂️";

    private final String[] OPTIONS = {ROCK, PAPER, SCISSORS};
    private String[] countdown = {"Rock...", "Paper...", "Scissors...", "Shoot!"};

    private int userWins  = 0;
    private int compWins = 0;

    private int step;
    
    JLabel score;
    JLabel status;

    ArrayList<JButton> buttons = new ArrayList<JButton>();

    public RockPaperScissors() {
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel header = new JPanel(new GridLayout(1, 1));
        score = new JLabel("User: " + userWins + " Computer: " + compWins);
        score.setFont(new Font("Dialog", Font.PLAIN, 12));
        header.add(score);
        frame.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 1));
        status = new JLabel("Select a move to play!", JLabel.CENTER);
        center.add(status);
        frame.add(center, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        for (String option : OPTIONS) {
            JButton button = new JButton(option);
            button.addActionListener(this);
            button.setActionCommand(option);
            button.addActionListener(e -> startCountdown(option));
            buttonsPanel.add(button);
            buttons.add(button);
        }
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setButtonsEnabled(boolean enabled) {
        for(JButton button : buttons) {
            button.setEnabled(enabled);
        }
    }
    private void startCountdown(String move) {
        setButtonsEnabled(false);
        step = 0;

        Timer timer = new Timer(600, null); 
        timer.addActionListener(e -> {
            if (step < countdown.length) {
                status.setText(countdown[step]);
                step++;
            } else {
                timer.stop();
                determineWinner(move, getCompChoice());
                setButtonsEnabled(true);
            }
        });
        timer.start();
    }

    private String getCompChoice() {
        String choice = OPTIONS[new Random().nextInt(3)];
        return choice;
    }

    private void determineWinner(String user, String comp) {
        if (user.equals(comp)) {
            System.out.println(comp);
            status.setText("Tie");
        }
        else if(user.equals(ROCK) && comp.equals(SCISSORS) ||
            user.equals(SCISSORS) && comp.equals(PAPER) ||
            user.equals(PAPER) && comp.equals(ROCK)) {
            System.out.println(comp);
            userWins++;
            updateUI();
            status.setText("User wins");
        }
        else {
            System.out.println(comp);
            compWins++;
            updateUI();
            status.setText("Comp wins");
        }
    }

    public void updateUI() {
        score.setText("User: " + userWins + " Computer: " + compWins);
    }

    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
