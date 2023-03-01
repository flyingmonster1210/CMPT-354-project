package ca.cmpt354.easyNote.dao;

import ca.cmpt354.easyNote.model.CollectPost;
import ca.cmpt354.easyNote.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectPostDao {

    private final String className = "CollectPostDao";

    public CollectPostDao() {
    }

    public int addCollectPost(Connection connection, int postID, User user) {
        if(connection == null || user == null) return 0;

        String sql = "INSERT into collectpost(postID, userID, libraryID) (\n" +
                "\tselect ? as postID, ? as userID, A.ID\n" +
                "\tFrom library as A\n" +
                "\twhere A.userID = ?\n" +
                ");\n";
        Object[] args = {postID, user.getID(), user.getID()};

        int affectedRows = 0;
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "addCollectPost(Connection connection, int postID, int libraryID, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public int deleteCollectPost(Connection connection, CollectPost collectPost, User user) {
        if(connection == null || collectPost == null || user == null) return 0;

        String sql = "delete from collectpost where postID = ? and userID = ? and libraryID = ?";
        Object[] args = {collectPost.getPostID(), user.getID(), collectPost.getLibraryID()};

        int affectedRows = 0;
        if (user.getLevel() == User.ADMIN_LEVEL) {
            args[1] = collectPost.getUserID();
        }
        try {
            affectedRows = BasicDao.update(connection, sql, args);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "deleteCollectPost(Connection connection, CollectPost collectPost, User user)");
            throwables.printStackTrace();
        }

        return affectedRows;
    }

    public List<CollectPost> searchCollectPostByPostID(Connection connection, int postID) {
        List<CollectPost> res = new ArrayList<>();
        if(connection == null) return res;

        String sql = "select * from collectpost where postID = ?;";
        Object[] args = {postID};

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            while (resultSet.next()) {
                CollectPost collectPost = new CollectPost(resultSet.getInt("postID"),
                        resultSet.getInt("userID"), resultSet.getInt("libraryID"));
                res.add(collectPost);
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "searchCollectPostByPostID(Connection connection, int postID)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }

        return res;
    }

    public List<CollectPost> searchCollectPostByUserID(Connection connection, int userID) {
        List<CollectPost> res = new ArrayList<>();
        if(connection == null) return res;

        String sql = "select * from collectpost where userID = ?;";
        Object[] args = {userID};

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            while (resultSet.next()) {
                CollectPost collectPost = new CollectPost(resultSet.getInt("postID"),
                        resultSet.getInt("userID"), resultSet.getInt("libraryID"));
                res.add(collectPost);
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "searchCollectPostByUserID(Connection connection, int userID)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }

        return res;
    }

    public List<CollectPost> searchCollectPostByLibraryID(Connection connection, int libraryID) {
        List<CollectPost> res = new ArrayList<>();
        if(connection == null) return res;

        String sql = "select * from collectpost where libraryID = ?;";
        Object[] args = {libraryID};

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            while (resultSet.next()) {
                CollectPost collectPost = new CollectPost(resultSet.getInt("postID"),
                        resultSet.getInt("userID"), resultSet.getInt("libraryID"));
                res.add(collectPost);
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, "searchCollectPostByLibraryID(Connection connection, int libraryID)");
            throwables.printStackTrace();
        } finally {
            BasicDao.closeResources(null, preparedStatement, resultSet);
        }

        return res;
    }
}













