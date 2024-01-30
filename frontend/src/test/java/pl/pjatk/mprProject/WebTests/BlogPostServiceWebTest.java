package pl.pjatk.mprProject.WebTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Service.BlogPostService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RestClientTest
public class BlogPostServiceWebTest {

    private MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    private RestClient.Builder builder = RestClient.builder();
    private BlogPostService service;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        customizer.customize(builder);
        service = new BlogPostService(builder.build());
        mockServer = customizer.getServer();
    }

    @Test
    public void testGetAllBlogPosts() {
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":1, \"nameOfCreatedPost\":\"Test Post\"}]", MediaType.APPLICATION_JSON));

        List<BlogPost> result = service.getAllBlogPosts();

        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getNameOfCreatedPost());
    }

    @Test
    public void testGetBlogPostById() {
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/1"))
                .andRespond(MockRestResponseCreators.withSuccess("{\"id\":1, \"nameOfCreatedPost\":\"Test Post\"}", MediaType.APPLICATION_JSON));

        BlogPost result = service.getBlogPostById(1L);

        assertNotNull(result);
        assertEquals("Test Post", result.getNameOfCreatedPost());
    }

    @Test
    public void testCreateBlogPost() {
        BlogPost newPost = new BlogPost();
        newPost.setNameOfCreatedPost("New Post");

        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess("{\"id\":2, \"nameOfCreatedPost\":\"New Post\"}", MediaType.APPLICATION_JSON));

        BlogPost result = service.createBlogPost(newPost);

        assertNotNull(result);
        assertEquals("New Post", result.getNameOfCreatedPost());
    }

    @Test
    public void testUpdateBlogPost() {
        BlogPost updatedPost = new BlogPost();
        updatedPost.setNameOfCreatedPost("Updated Post");

        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withSuccess("{\"id\":1, \"nameOfCreatedPost\":\"Updated Post\"}", MediaType.APPLICATION_JSON));

        BlogPost result = service.updateBlogPost(1L, updatedPost);

        assertNotNull(result);
        assertEquals("Updated Post", result.getNameOfCreatedPost());
    }

    @Test
    public void testDeleteBlogPost() {
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withNoContent());

        assertDoesNotThrow(() -> service.deleteBlogPost(1L));
    }
}



