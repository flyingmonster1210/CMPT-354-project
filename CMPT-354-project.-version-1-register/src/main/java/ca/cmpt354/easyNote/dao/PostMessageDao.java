package ca.cmpt354.easyNote.dao;

import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.PostMessage;
import ca.cmpt354.easyNote.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostMessageDao {

    private final String className = "PostMessageDao";

    public PostMessageDao() {
    }

    public int addPostMessage(Connection connection, Post post, String newMessage, User user) {
        if(connection == null || post == null || newMessage == null || user == null) return 0;

        String sql = "insert into postmessage(postID, userID, message, time_stamp, author) " +
                "values (?, ?, ?, UTC_TIMESTAMP(), ?);";
        Object[] args = {post.getID(), user.getID(), newMessage, user.getUsername()};

        int affectRows = 0;
        try {
            affectRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "addPostMessage(Connection connection, String newMessage, Post post, User user)");
            throwables.printStackTrace();
        }

        return affectRows;
    }

    public int deletePostMessage(Connection connection, Post post, PostMessage postMessage, User user) {
        if(connection == null || post == null || postMessage == null || user == null) return 0;

        String sql = "delete from postmessage where " +
                "postID = ? and userID = ? and message = ?;";
        Object[] args = {post.getID(), user.getID(), postMessage.getMessage()};

        int affectedRows = 0;
        if (user.getLevel() == User.ADMIN_LEVEL || post.getUserID() == user.getID()) {
            args[1] = postMessage.getUserID();
        }
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "deletePostMessage(Connection connection, Post post, PostMessage postMessage, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public List<PostMessage> findAllMessagesInOnePost(Connection connection, Post post) {
        List<PostMessage> messages = new ArrayList<>();
        if(connection == null || post == null) return messages;

        String sql = "select * from postmessage where postID = ? order by time_stamp;";
        Object[] args = {post.getID()};
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            while (resultSet.next()) {
                PostMessage postMessage =
                        new PostMessage(resultSet.getInt("postID"), resultSet.getInt("userID"),
                                resultSet.getString("message"), resultSet.getString("author"), resultSet.getTimestamp("time_stamp"));

                messages.add(postMessage);
                System.out.println(postMessage.getTime());
            }

        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "findAllMessagesInOnePost(Connection connection, Post post)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }

        return messages;
    }
}

















