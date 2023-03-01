package ca.cmpt354.easyNote.test;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.model.Post;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ca.cmpt354.easyNote.dao.BasicDao.search;

public class TestBasicDao {
    @Test
    public void testUpdate() throws SQLException {
        Connection connection = BasicDao.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "insert into `post`(userID, pName) values (?, ?);";
        Object[] args = {1004176378, "test1123"};
        BasicDao.update(connection, sql, args);

        BasicDao.closeResources(connection, preparedStatement, resultSet);
    }

    @Test
    public void testSearch() throws SQLException {
        Connection connection = BasicDao.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "select * from post where pName like ?;";
        Object[] args = {"%test%"};
        resultSet = search(connection, preparedStatement, sql, args);
        while(resultSet.next()) {
            Post post = new Post(resultSet.getInt("ID"),
                    resultSet.getInt("userID"), resultSet.getString("pName"), "nothing just a test??");
            System.out.println(post);
        }

        BasicDao.closeResources(connection, preparedStatement, resultSet);
    }
}
