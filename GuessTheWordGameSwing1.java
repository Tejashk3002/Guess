import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheWordGameSwing1 extends JFrame implements ActionListener {
    private String[] words = {"apple", "mobile", "antarctica", "astronaut", "cricket"};
    private int currentRound = 0;
    private int totalRounds = 5;
    private int score = 0;

    private JTextField guessField;
    private JButton submitButton;
    private JLabel resultLabel;
    private JLabel scrambledLabel;
    private String selectedWord;
    private String scrambledWord;

    public GuessTheWordGameSwing1() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel("Guess the word:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        scrambledLabel = new JLabel();
        scrambledLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        guessField = new JTextField(15);
        guessField.setFont(new Font("Arial", Font.PLAIN, 18));

        submitButton = new JButton("Submit Guess");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(this);

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(scrambledLabel);

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.add(guessField);
        middlePanel.add(submitButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setTitle("Guess the Word Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        playRound();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String guess = guessField.getText().toLowerCase();
    
            if (guess.equals(selectedWord)) {
                resultLabel.setText("Congratulations! You guessed the word.");
                score++;
            } else {
                resultLabel.setText("Incorrect. The correct answer was: " + selectedWord);
            }
    
            guessField.setEnabled(false);
            submitButton.setEnabled(false);
    
            Timer timer = new Timer(700, new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        if (currentRound <totalRounds-1) {
                            currentRound++;
                            playRound();
                            guessField.setEnabled(true);
                            submitButton.setEnabled(true);
                        } else {
                            displayFinalScore();
                        }
                    });
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void playRound() {
        if (currentRound < totalRounds) {
            selectedWord = words[currentRound];
            scrambledWord = scrambleWord(selectedWord);
            scrambledLabel.setText(scrambledWord);
            guessField.setText("");
            resultLabel.setText("");
        }
    }

    private void displayFinalScore() {
        getContentPane().removeAll();
        JLabel finalScoreLabel = new JLabel("Final Score: " + score + "/" + totalRounds);
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        finalScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(finalScoreLabel);
        revalidate();
        repaint();
    }

    private String scrambleWord(String word) {
        char[] characters = word.toCharArray();
        Random random = new Random();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GuessTheWordGameSwing1();
        });
    }
}
