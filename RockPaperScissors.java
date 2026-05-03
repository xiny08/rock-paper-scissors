import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class RockPaperScissors extends JPanel implements ActionListener {

    JFrame frame = new JFrame("Rock Paper Scissors");
    private final String ROCK = "🪨";
    private final String PAPER = "📄";
    private final String SCISSORS = "✂️";

    private final String[] OPTIONS = {ROCK, PAPER, SCISSORS};
    private String[] countdown = {"Rock...", "Paper...", "Scissors...", "Shoot!"};

    private int userWins  = 0;
    private String userChoice = "";
    private int compWins = 0;
    private String compChoice = "";

    private int step;
    
    private JLabel score;
    private JLabel status;

    private ArrayList<BufferedImage> hands = new ArrayList<BufferedImage>();

    private ArrayList<JButton> buttons = new ArrayList<JButton>();

    private boolean showImages = false;

    public RockPaperScissors() {
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel header = new JPanel(new GridLayout(1, 1));
        header.setOpaque(false);
        score = new JLabel("User: " + userWins + " Computer: " + compWins);
        score.setFont(new Font("Dialog", Font.BOLD, 12));
        header.add(score);
        frame.add(header, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());

        status = new JLabel("Select a move to play!", JLabel.CENTER);
        score.setFont(new Font("Dialog", Font.BOLD, 12));

        this.add(status, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        for (String option : OPTIONS) {
            JButton button = new JButton(option);
            button.addActionListener(this);
            button.setActionCommand(option);
            button.addActionListener(e -> {userChoice = option;
                startCountdown(option);});
            buttonsPanel.add(button);
            buttons.add(button);
        }
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        Path dir = Paths.get("images");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{jpg,jpeg,png,bmp}")) {
            for (Path entry : stream) {
                try {
                    BufferedImage img = ImageIO.read(entry.toFile());
                    if (img != null) {
                        hands.add(img);
                    }
                } catch (IOException e) {
                    System.out.println("Could not read file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(this, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void setButtonsEnabled(boolean enabled) {
        for(JButton button : buttons) {
            button.setEnabled(enabled);
        }
    }
    private void startCountdown(String move) {
        setButtonsEnabled(false);
        step = 0;
        showImages = false; 
        repaint();

        Timer timer = new Timer(600, null); 
        timer.addActionListener(e -> {
            if (step < countdown.length) {
                status.setText(countdown[step]);
                step++;
            } else {
                timer.stop();
                compChoice = getCompChoice();
                showImages = true;
                repaint();
                determineWinner(move, compChoice);
                setButtonsEnabled(true);
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(!showImages) return;

        if(userChoice.equals(ROCK)) {
            g.drawImage(hands.get(1), 0, 200, 200, 200, this);
        }
        else if(userChoice.equals(PAPER)) {
            g.drawImage(hands.get(0), 0, 200, 200, 200, this);
        }
        else {
            g.drawImage(hands.get(2), 0, 200, 200, 200, this);
        }

        if(compChoice.equals(ROCK)) {
            g.drawImage(hands.get(1), 300, 200, 200, 200, this);
        }
        else if(compChoice.equals(PAPER)) {
            g.drawImage(hands.get(0), 300, 200, 200, 200, this);
        }
        else {
            g.drawImage(hands.get(2), 300, 200, 200, 200, this);
        }
    }

    private String getCompChoice() {
        String choice = OPTIONS[new Random().nextInt(3)];
        return choice;
    }

    private void determineWinner(String user, String comp) {
        if (user.equals(comp)) {
            System.out.println(comp);
            status.setText("TIE");
        }
        else if(user.equals(ROCK) && comp.equals(SCISSORS) ||
            user.equals(SCISSORS) && comp.equals(PAPER) ||
            user.equals(PAPER) && comp.equals(ROCK)) {
            System.out.println(comp);
            userWins++;
            status.setText("USER WINS!");
        }
        else {
            System.out.println(comp);
            compWins++;
            status.setText("COMPUTER WINS!");
        }
        score.setText("User: " + userWins + " Computer: " + compWins);
    }

    public void display() {
        frame.setVisible(true);
    }
}
