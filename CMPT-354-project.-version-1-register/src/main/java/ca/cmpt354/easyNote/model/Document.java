package ca.cmpt354.easyNote.model;

public class Document {
    public int ID;
    public int userID;
    public String course;
    public String fileName;
    public String type;
    private String URL;

    public Document() {};

    public Document(int ID, int userID, String course, String fileName, String type, String URL) {
        this.ID = ID;
        this.userID = userID;
        this.course = course;
        this.fileName = fileName;
        this.type = type;
        this.URL = URL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
