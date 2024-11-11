package quizgame;

public class UserService {
    private String userName; // Store the user's name

    public UserService() {
        this.userName = ""; // Initialize userName as empty
    }

    // Method to set the user's name
    public void setUserName(String name) {
        this.userName = name;
    }

    // Method to get the user's name
    public String getUserName() {
        return userName;
    }
}
