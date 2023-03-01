package ca.cmpt354.easyNote.model;

public class User {
    public int ID;
    public String username;
    public String email;
    public String password;
    public int level;

    public static final int ADMIN_LEVEL = 1;
    public static final int GENERAL_USER_LEVEL = 0;

    public User() {
    }

    public User(int userID, String username, String email, String password, int level) {
        this.ID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAll(int ID, String name, String email, String password, int level) {
        this.ID = ID;
        this.username = name;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Users{" +
                "ID=" + ID +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}