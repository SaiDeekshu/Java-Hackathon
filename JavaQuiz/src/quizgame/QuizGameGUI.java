package quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGameGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JButton createQuizButton, takeQuizButton, leaderboardButton, exitButton;
    private UserService userService;
    private QuizService quizService;
    private Leaderboard leaderboard;

    public QuizGameGUI() {
        userService = new UserService();  
        leaderboard = new Leaderboard();
        quizService = new QuizService(userService, leaderboard);

        // Set up the frame
        setTitle("Quiz Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Add components 
        add(new JLabel("Enter your name:"));
        nameField = new JTextField(15);
        add(nameField);

        createQuizButton = new JButton("Create Quiz");
        createQuizButton.addActionListener(this);
        add(createQuizButton);

        takeQuizButton = new JButton("Take Quiz");
        takeQuizButton.addActionListener(this);
        add(takeQuizButton);

        leaderboardButton = new JButton("View Leaderboard");
        leaderboardButton.addActionListener(this);
        add(leaderboardButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0); // Exit the application
        }

        if (e.getSource() == createQuizButton) {
            quizService.createQuiz(); // Create a new quiz
        } else if (e.getSource() == takeQuizButton) {
            String userName = nameField.getText().trim();
            if (userName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            userService.setUserName(userName); // Set the user name if valid
            quizService.takeQuiz(); // Take an existing quiz
        } else if (e.getSource() == leaderboardButton) {
            quizService.viewLeaderboard(); // View the leaderboard
        }
    }
}
