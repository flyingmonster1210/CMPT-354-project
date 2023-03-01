package ca.cmpt354.easyNote.model;

public class CollectPost {
    private int postID;
    private int userID;
    private int libraryID;

    public CollectPost(int postID, int userID, int libraryID) {
        this.postID = postID;
        this.userID = userID;
        this.libraryID = libraryID;
    }

    public CollectPost() {
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }

    public void setAll(int postID, int userID, int libraryID) {
        this.postID = postID;
        this.userID = userID;
        this.libraryID = libraryID;
    }

    @Override
    public String toString() {
        return "CollectPost("+postID+", "+userID+", "+libraryID+")";
    }
}
