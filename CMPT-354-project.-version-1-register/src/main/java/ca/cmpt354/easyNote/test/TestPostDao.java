package ca.cmpt354.easyNote.test;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.PostDao;
import ca.cmpt354.easyNote.model.ConnectDB;
import ca.cmpt354.easyNote.model.CourseFileNum;
import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class TestPostDao {

    private PostDao postDao = new PostDao();

    @Test
    public void testAddPost1() {
        Connection connection = BasicDao.getConnection();
        int ID = 2; // change this ID every time you use this test
        String postName = "just a test1";
        User user = new User(1004173716, "Wu Wang", "fangsi@163.com", "$ww488156", 0);
        int affectedRows = postDao.addPost(connection, ID, postName, user, "xxxx??");

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testAddPost2() {
        Connection connection = BasicDao.getConnection();
        String postName = "just a test2";
        User user = new User(1004173716, "Wu Wang", "fangsi@163.com", "$ww488156", 0);
        int affectedRows = postDao.addPost(connection, postName, user, "XXXX??");

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testUpdatePostName() {
        Connection connection = BasicDao.getConnection();
        String newPostName = "change a little bit";
        Post post = new Post(400224786, 1004173716, "wrong in lecture", "xxxx!");
        User user = new User(0, "admin", "xxx", "xxx", 1);

        postDao.updatePostName(connection, newPostName, post, user);
        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void deletePost() {
        Connection connection = BasicDao.getConnection();
        Post post = new Post(400224786, 1004173716, "wrong in lecture", "xxxx!");
        User user = new User(0, "admin", "xxx", "xxx", 1);

        System.out.println(postDao.deletePost(connection, post, user));
        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void deletePostByID() {
        Connection connection = BasicDao.getConnection();
        User user = new User(0, "admin", "xxx", "xxx", 1);
        int postID = 2;

        System.out.println(postDao.deletePostByID(connection, postID, user));
        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testSearchPostByID() {
        Connection connection = BasicDao.getConnection();

        testAddPost1();
        int postID = 2;
        System.out.println(postDao.searchPostByPostID(connection, postID));

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testSearchPostByInfo() {
        Connection connection = BasicDao.getConnection();
        String searchInfo = "%test%";

        List<Post> res = postDao.searchPostByInfo(connection, searchInfo);
        for (Post re : res) {
            System.out.println(re);
        }

        BasicDao.closeResources(connection, null, null);
    }

    //---------------------------others testing----------------------------

    @Test
    public void testIfExist(){
        System.out.println(ConnectDB.ifExist("Li Zhong","49516515"));
    }

    @Test
    public void testCourseNum(){
        List<CourseFileNum> list = ConnectDB.CourseFileNumber();
        for(int i = 0;i < list.size();i++){
            System.out.println(list.get(i).name + " " + list.get(i).num);
        }
    }
}
