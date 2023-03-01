package ca.cmpt354.easyNote.test;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.dao.CollectPostDao;
import ca.cmpt354.easyNote.model.CollectPost;
import ca.cmpt354.easyNote.model.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class TestCollectPostDao {

    private CollectPostDao collectPostDao = new CollectPostDao();
    private User user1 = new User(1004176378, "Si Li", "5623478591@qq.com", "Hok47862", 0);
    private User user2 = new User(1004189630, "Hello Men", "Hello@gmail.com", "Hel99463136", 0);

    @Test
    public void testAddCollectPost() {
        Connection connection = BasicDao.getConnection();

//        System.out.println(collectPostDao.addCollectPost(connection, 400252143, user1)); // return 0, because this user does not has a library
        System.out.println(collectPostDao.addCollectPost(connection, 400252143, user2)); // return 1.

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testDeleteCollectPost() {
        Connection connection = BasicDao.getConnection();

        CollectPost collectPost = new CollectPost(400252143, user2.getID(), 320129);
        System.out.println(collectPostDao.deleteCollectPost(connection, collectPost, user2));

        BasicDao.closeResources(connection, null, null);
    }

    @Test
    public void testSearch() {
        Connection connection = BasicDao.getConnection();

        List<CollectPost> collectPosts = collectPostDao.searchCollectPostByPostID(connection, 400252143);
        for (CollectPost collectPost : collectPosts) {
            System.out.println(collectPost);
        }

        System.out.println("------------------------------------------");
        collectPosts = collectPostDao.searchCollectPostByUserID(connection, 1004175218);
        for (CollectPost collectPost : collectPosts) {
            System.out.println(collectPost);
        }

        System.out.println("------------------------------------------");
        collectPosts = collectPostDao.searchCollectPostByLibraryID(connection, 320274);
        for (CollectPost collectPost : collectPosts) {
            System.out.println(collectPost);
        }


        BasicDao.closeResources(connection, null, null);
    }
}
