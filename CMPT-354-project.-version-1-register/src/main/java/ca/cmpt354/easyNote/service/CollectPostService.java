package ca.cmpt354.easyNote.service;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.CollectPostDao;
import ca.cmpt354.easyNote.model.CollectPost;
import ca.cmpt354.easyNote.model.User;

import java.sql.Connection;
import java.util.List;

public class CollectPostService {
    private final CollectPostDao collectPostDao = new CollectPostDao();

    public boolean addCollectPost(int postID, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = collectPostDao.addCollectPost(connection, postID, user);

        BasicDao.closeResources(connection, null, null);
        return affectedRows > 0;
    }

    public boolean deleteCollectPost(CollectPost collectPost, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = collectPostDao.deleteCollectPost(connection, collectPost, user);

        BasicDao.closeResources(connection, null, null);
        return affectedRows > 0;
    }

    public List<CollectPost> searchCollectPostByPostID(int postID) {
        Connection connection = BasicDao.getConnection();

        List<CollectPost> res = collectPostDao.searchCollectPostByPostID(connection, postID);

        BasicDao.closeResources(connection, null, null);
        return res;
    }

    public List<CollectPost> searchCollectPostByUserID(int userID) {
        Connection connection = BasicDao.getConnection();

        List<CollectPost> res = collectPostDao.searchCollectPostByPostID(connection, userID);

        BasicDao.closeResources(connection, null, null);
        return res;
    }

    public List<CollectPost> searchCollectPostByLibraryID(int libraryID) {
        Connection connection = BasicDao.getConnection();

        List<CollectPost> res = collectPostDao.searchCollectPostByPostID(connection, libraryID);

        BasicDao.closeResources(connection, null, null);
        return res;
    }
}
