package ca.cmpt354.easyNote.model;

public class PostOption {
    public String name;
    public String content;

    public PostOption() {
        String s1 = new String();
        String s2 = new String();

        this.name = s1;
        this.content = s2;
    }

    public PostOption(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
