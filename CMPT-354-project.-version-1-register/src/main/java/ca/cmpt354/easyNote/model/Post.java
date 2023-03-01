package ca.cmpt354.easyNote.model;

public class Post {
    public int ID;
    public int userID;
    public String pName;
    public String postContent;

    public Post() {
    }

    public Post(int postID, int userID, String pName, String postContent) {
        this.ID = postID;
        this.userID = userID;
        this.pName = pName;
        this.postContent = postContent;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setAll(int postID, int userID, String pName, String postContent) {
        this.ID = postID;
        this.userID = userID;
        this.pName = pName;
        this.postContent = postContent;
    }

    @Override
    public String toString() {
        return "Post("+ID+", "+userID+", "+pName+", "+postContent+")";
    }
}
