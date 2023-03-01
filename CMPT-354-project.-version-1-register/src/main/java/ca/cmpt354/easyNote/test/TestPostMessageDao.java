package ca.cmpt354.easyNote.test;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.PostMessageDao;
import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.model.PostMessage;
import ca.cmpt354.easyNote.model.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class TestPostMessageDao {

    private PostMessageDao postMessageDao = new PostMessageDao();

    private Post post = new Post(400236547, 1004189630, "Newton first low", "wa ha ha ha!");
    private User userOfPost = new User(1004189630, "Hello Men", "Hello@gmail.com", "Hel99463136", 0);
    private User userOfPostMessage = new User(1004175218, "San Zhang", "afjafj@gmail.com", "Juj511956", 0);
    private String newMessage = "wahahahaha12341234123412341";

    @Test
    public void testAddPostMessage() {
        Connection connection = BasicDao.getConnection();

        System.out.println(postMessageDao.addPostMessage(connection, post, newMessage, userOfPostMessage));

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testDeletePostMessage() {
        Connection connection = BasicDao.getConnection();

        PostMessage postMessage = new PostMessage(post.getID(), userOfPostMessage.getID(), newMessage, userOfPostMessage.getUsername());
        System.out.println(postMessageDao.deletePostMessage(connection, post, postMessage, userOfPostMessage));

//        PostMessage postMessage = new PostMessage(post.getID(), userOfPostMessage.getID(), newMessage, userOfPostMessage.getUsername());
//        System.out.println(postMessageDao.deletePostMessage(connection, userOfPost, post, postMessage));

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testFindAllMessagesInOnePost() {
        Connection connection = BasicDao.getConnection();

        List<PostMessage> messages = postMessageDao.findAllMessagesInOnePost(connection, post);
        for (PostMessage message : messages) {
            System.out.println(message);
        }

        BasicDao.closeResources(connection, null, null);
    }

}
