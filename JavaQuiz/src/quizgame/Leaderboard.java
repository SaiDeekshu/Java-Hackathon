package quizgame;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    // Map<QuizName, Map<UserName, Score>>
    private Map<String, Map<String, Integer>> quizScores;

    public Leaderboard() {
        quizScores = new HashMap<>(); // Initialize the quiz leaderboard
    }

    // Method to add/update a user's score for a specific quiz
    public void addScore(String quizName, String userName, int score) {
        quizScores.putIfAbsent(quizName, new HashMap<>());
        Map<String, Integer> userScores = quizScores.get(quizName);
        userScores.put(userName, score);
    }

    // Method to display the entire leaderboard
    public void display(StringBuilder display) {
        if (quizScores.isEmpty()) {
            display.append("No scores available.\n");
            return;
        }

        for (String quizName : quizScores.keySet()) {
            display.append("Quiz: ").append(quizName).append("\n");
            Map<String, Integer> userScores = quizScores.get(quizName);
            for (Map.Entry<String, Integer> entry : userScores.entrySet()) {
                display.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            display.append("\n"); // Add a blank line between quizzes
        }
    }
}
