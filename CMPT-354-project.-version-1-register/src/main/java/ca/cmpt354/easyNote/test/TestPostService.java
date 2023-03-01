package ca.cmpt354.easyNote.test;

import ca.cmpt354.easyNote.dao.BasicDao;
import ca.cmpt354.easyNote.model.Post;
import ca.cmpt354.easyNote.service.PostService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class TestPostService {
    private PostService postService = new PostService();

    @Test
    public void testSearchPostByInfo() {
        Connection connection = BasicDao.getConnection();

        List<Post> posts = postService.searchPostByInfo("");
        System.out.println(posts.size());
        for (Post post : posts) {
            System.out.println(post);
        }

        BasicDao.closeResources(connection, null, null);
    }
}
