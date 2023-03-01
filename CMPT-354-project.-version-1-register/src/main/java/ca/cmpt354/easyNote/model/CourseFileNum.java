package ca.cmpt354.easyNote.model;

public class CourseFileNum {
    public String name;
    public int num;

    public void setName(String name){
        this.name = name;
    }

    public void setNum(int num){
        this.num = num;
    }

    public CourseFileNum(String name, int num) {
        this.name = name;
        this.num = num;
    }
}
