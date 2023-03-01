package ca.cmpt354.easyNote.service;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.PostMessageDao;
import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.PostMessage;
import ca.cmpt354.easyNote.model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PostMessageService {

    private final PostMessageDao postMessageDao= new PostMessageDao();

    public boolean addPostMessage(Post post, String newMessage, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = postMessageDao.addPostMessage(connection, post, newMessage, user);

        BasicDao.closeResources(connection, null, null);
        return affectedRows > 0;
    }

    public boolean deletePostMessage(Post post, PostMessage postMessage, User user) {
        Connection connection = BasicDao.getConnection();

        int affectedRows = postMessageDao.deletePostMessage(connection, post, postMessage, user);

        BasicDao.closeResources(connection, null, null);
        return affectedRows > 0;
    }

    public List<PostMessage> findAllMessagesInOnePost(Post post) {
        Connection connection = BasicDao.getConnection();

        List<PostMessage> messages = postMessageDao.findAllMessagesInOnePost(connection, post);

        BasicDao.closeResources(connection, null, null);
        return messages;
    }
}
