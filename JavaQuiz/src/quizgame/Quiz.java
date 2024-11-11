package quizgame;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String title;
    private String creator;
    private List<Question> questions;

    public Quiz(String title, String creator) {
        this.title = title;
        this.creator = creator;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
