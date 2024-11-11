package quizgame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGameGUI().setVisible(true));
    }
}
