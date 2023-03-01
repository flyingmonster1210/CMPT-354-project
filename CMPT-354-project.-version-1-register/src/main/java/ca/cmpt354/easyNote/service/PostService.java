package ca.cmpt354.easyNote.service;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.PostDao;
import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.User;
import org.graalvm.compiler.lir.BailoutAndRestartBackendException;

import java.sql.Connection;
import java.util.List;

public class PostService {

    private final PostDao postDao = new PostDao();

    public boolean addPost(int ID, String postName, User user, String postContent) {
        Connection connection = BasicDao.getConnection();

        int affectRows = postDao.addPost(connection, ID, postName, user, postContent);

        BasicDao.closeResources(connection, null, null);
        return affectRows > 0;
    }

    public boolean addPost(String postName, User user, String postContent) {
        Connection connection = BasicDao.getConnection();

        int affectRows = postDao.addPost(connection, postName, user, postContent);

        BasicDao.closeResources(connection, null, null);
        return affectRows > 0;
    }

    public boolean deletePost(Post post, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = postDao.deletePost(connection, post, user);

        return affectedRows > 0;
    }

    public boolean deletePostByID(int postID, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = postDao.deletePostByID(connection, postID, user);

        BasicDao.closeResources(connection, null, null);
        return affectedRows > 0;
    }

    public boolean updatePostName(String newPostName, Post post, User user) {
        Connection connection = BasicDao.getConnection();

        int affectRows = postDao.updatePostName(connection, newPostName, post, user);

        BasicDao.closeResources(connection, null, null);
        return affectRows > 0;
    }

    public Post searchPostByID(int postID) {
        Connection connection = BasicDao.getConnection();

        Post post = postDao.searchPostByPostID(connection, postID);

        BasicDao.closeResources(connection, null, null);
        return post;
    }

    public List<Post> searchPostByInfo(String searchInfo) {
        Connection connection = BasicDao.getConnection();

        List<Post> res = postDao.searchPostByInfo(connection, searchInfo);

        BasicDao.closeResources(connection, null, null);
        return res;
    }
}

