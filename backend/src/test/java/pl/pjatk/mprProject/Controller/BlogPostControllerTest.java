package pl.pjatk.mprProject.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Service.BlogPostService;

import java.util.ArrayList;
import java.util.List;

public class BlogPostControllerTest {

    @Mock
    private BlogPostService blogPostService;

    @InjectMocks
    private BlogPostController blogPostController;

    @BeforeEach
    void setUp() {
        blogPostService = mock(BlogPostService.class);
        blogPostController = new BlogPostController(blogPostService);
    }

    @Test
    void shouldReturnAllBlogPosts() {
        List<BlogPost> posts = new ArrayList<>();
        posts.add(new BlogPost());
        posts.add(new BlogPost());

        when(blogPostService.getAllBlogPosts()).thenReturn(posts);

        ResponseEntity<List<BlogPost>> response = blogPostController.getAllBlogPosts();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(blogPostService, times(1)).getAllBlogPosts();
    }

    @Test
    void shouldReturnBlogPostById() {
        BlogPost post = new BlogPost();
        when(blogPostService.getBlogPostById(1L)).thenReturn(post);

        ResponseEntity<BlogPost> response = blogPostController.getBlogPostById(1L);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(blogPostService, times(1)).getBlogPostById(1L);
    }

    @Test
    void shouldCreateBlogPost() {
        BlogPost post = new BlogPost();
        when(blogPostService.createBlogPost(any(BlogPost.class))).thenReturn(post);

        ResponseEntity<BlogPost> response = blogPostController.createBlogPost(post);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(blogPostService, times(1)).createBlogPost(any(BlogPost.class));
    }

    @Test
    void shouldUpdateBlogPost() {
        BlogPost post = new BlogPost();
        when(blogPostService.updateBlogPost(eq(1L), any(BlogPost.class))).thenReturn(post);

        ResponseEntity<BlogPost> response = blogPostController.updateBlogPost(1L, post);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(blogPostService, times(1)).updateBlogPost(eq(1L), any(BlogPost.class));
    }

    @Test
    void shouldDeleteBlogPost() {
        doNothing().when(blogPostService).deleteBlogPost(1L);

        ResponseEntity<Void> response = blogPostController.deleteBlogPost(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(blogPostService, times(1)).deleteBlogPost(1L);
    }
}
