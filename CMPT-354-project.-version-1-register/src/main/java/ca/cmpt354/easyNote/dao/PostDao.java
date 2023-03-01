package ca.cmpt354.easyNote.dao;

import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.User;
import org.junit.jupiter.api.Test;

import javax.naming.directory.SearchResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ca.cmpt354.easyNote.model.User.ADMIN_LEVEL;

public class PostDao {

    private final String className = "PostDao";

    public PostDao() {
    }

    public int addPost(Connection connection, int ID, String postName, User user, String postContent) {
        if(connection == null || postName == null || user == null) return 0;

        String sql = "insert into `post`(ID, userID, pName, postContent) values (?, ?, ?, ?);";
        Object[] args = {ID, user.getID(), postName, postContent};

        int affectedRows = 0;
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "addPost(Connection connection, int ID, String postName, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    // with no input's ID, the post's ID will increase automatically
    public int addPost(Connection connection, String postName, User user, String postContent) {
        if(connection == null || postName == null || user == null) return 0;

        String sql = "insert into `post`(userID, pName, postContent) values (?, ?, ?);";
        Object[] args = {user.getID(), postName, postContent};

        int affectedRows = 0;
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "addPost(Connection connection, String postName, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public int updatePostName(Connection connection, String newPostName, Post post, User user) {
        if(connection == null || newPostName == null || post == null || user == null) return 0;

        String sql = "update `post` set pName = ? where ID = ? and userID = ?;";
        Object[] args = {newPostName, post.getID(), user.getID()};

        int affectedRows = 0;
        if (user.getLevel() == User.ADMIN_LEVEL) {
            args[2] = post.getUserID();
        }
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "updatePostName(Connection connection, String newPostName, Post post, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public int deletePost(Connection connection, Post post, User user) {
        if(connection == null || post == null || user == null) return 0;

        String sql = "delete from post where ID = ? and userID = ?;";
        Object[] args = {post.getID(), user.getID()};
        int affectedRows = 0;

        if (user.getLevel() == User.ADMIN_LEVEL) {
            args[1] = post.getUserID();
        }
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "deletePost(Connection connection, Post post, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public int deletePostByID(Connection connection, int postID, User user) {
        if(connection == null || user == null || postID < 0) return 0;

        StringBuffer sql = new StringBuffer("delete from post where ID = ?");
        Object[] args = null;
        if(user.getLevel() != ADMIN_LEVEL) {
            sql.append(" and userID = ?;");
            args = new Object[2];
            args[0] = postID;
            args[1] = user.getID();
        } else {
            args = new Object[1];
            args[0] = postID;
        }

        int affectedRows = 0;
        try {
            affectedRows = BasicDao.update(connection, sql.toString(), args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "deletePostByID(Connection connection, int postID, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public Post searchPostByPostID(Connection connection, int postID) {
        if(connection == null || postID < 0) return null;

        Post post = null;
        String sql = "select * from post where ID = ?";
        Object[] args = {postID};
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            if(resultSet.next()) {
                post = new Post(postID, resultSet.getInt("userID"),
                        resultSet.getString("pName"), resultSet.getString("postContent"));
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "searchPostByPostID(Connection connection, int postID)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }


        return post;
    }

    public List<Post> searchPostByInfo(Connection connection, String searchInfo) {
        List<Post> res = new ArrayList<>();
        if(connection == null || searchInfo == null) return res;

        String sql = "select * from post where pName like ?;";
        Object[] args = {searchInfo};
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            args[0] = "%" + args[0] + "%";
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            while (resultSet.next()) {
                Post post = new Post(resultSet.getInt("ID"), resultSet.getInt("userID"),
                        resultSet.getString("pName"), resultSet.getString("postContent"));
                res.add(post);
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "searchPost(Connection connection, String searchInfo)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }

        return res;
    }
}
