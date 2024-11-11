package quizgame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService {
    private List<Quiz> quizzes; // Store quizzes
    private UserService userService; // Reference to UserService
    private Leaderboard leaderboard; // Reference to Leaderboard

    public QuizService(UserService userService, Leaderboard leaderboard) {
        this.quizzes = new ArrayList<>(); // Initialize the list of quizzes
        this.userService = userService; // Initialize UserService
        this.leaderboard = leaderboard; // Initialize Leaderboard
    }

    public void createQuiz() {
        String title = JOptionPane.showInputDialog("Enter quiz title:");
        if (title == null || title.trim().isEmpty()) {
            return; // Cancel if no title is provided
        }

        Quiz quiz = new Quiz(title, "admin"); // Assuming "admin" is the creator

        while (true) {
            String questionText = JOptionPane.showInputDialog("Enter question text (or type 'exit' to finish):");
            if (questionText == null || questionText.trim().equalsIgnoreCase("exit")) {
                break; // Exit if the user types 'exit'
            }

            String options = JOptionPane.showInputDialog("Enter options separated by commas (e.g. option1,option2,option3,option4):");
            String[] optionsArray = options.split(",");

            String correctOption = JOptionPane.showInputDialog("Enter the correct option number (1-" + optionsArray.length + "):");
            int correctOptionIndex = Integer.parseInt(correctOption) - 1; // Convert to zero-based index

            quiz.addQuestion(new Question(questionText, optionsArray, correctOptionIndex));

            int addMore = JOptionPane.showConfirmDialog(null, "Add another question?", "Quiz Creation", JOptionPane.YES_NO_OPTION);
            if (addMore == JOptionPane.NO_OPTION) {
                break; // Exit if the user does not want to add more questions
            }
        }

        quizzes.add(quiz); // Store the created quiz
        JOptionPane.showMessageDialog(null, "Quiz '" + title + "' created by " + quiz.getCreator());
    }

    public void takeQuiz() {
        if (quizzes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No quizzes available. Please create a quiz first.");
            return;
        }

        StringBuilder availableQuizzes = new StringBuilder("Available quizzes:\n");
        for (int i = 0; i < quizzes.size(); i++) {
            availableQuizzes.append((i + 1)).append(". ").append(quizzes.get(i).getTitle()).append("\n");
        }

        String quizChoice = JOptionPane.showInputDialog(availableQuizzes.toString() + "Select a quiz by entering its number:");
        int quizIndex = Integer.parseInt(quizChoice) - 1;

        if (quizIndex < 0 || quizIndex >= quizzes.size()) {
            JOptionPane.showMessageDialog(null, "Invalid quiz selection. Please try again.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(quizIndex);
        String userName = userService.getUserName(); // Get username from UserService
        int score = 0;

        // Start the quiz in a dialog with radio buttons for options
        for (Question question : selectedQuiz.getQuestions()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel questionLabel = new JLabel(question.getQuestionText());
            panel.add(questionLabel);
            ButtonGroup optionsGroup = new ButtonGroup();

            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                JRadioButton optionButton = new JRadioButton(options[i]);
                optionsGroup.add(optionButton);
                panel.add(optionButton);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Question", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                for (int i = 0; i < options.length; i++) {
                    if (optionsGroup.getElements().nextElement().isSelected()) {
                        if (i == question.getCorrectOption()) {
                            score++;
                            break;
                        }
                    }
                    optionsGroup.getElements().nextElement(); // Move to the next element
                }
            }
        }

        // Add the score to the leaderboard for the specific quiz
        leaderboard.addScore(selectedQuiz.getTitle(), userName, score);

        // Display the user's score
        JOptionPane.showMessageDialog(null, userName + ", you scored " + score + " out of " + selectedQuiz.getQuestions().size());
    }

    public void viewLeaderboard() {
        StringBuilder leaderboardDisplay = new StringBuilder();
        leaderboardDisplay.append("Leaderboard:\n");
        leaderboard.display(leaderboardDisplay);
        JOptionPane.showMessageDialog(null, leaderboardDisplay.toString());
    }
}
