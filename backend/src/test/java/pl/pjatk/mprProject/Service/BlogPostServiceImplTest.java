package pl.pjatk.mprProject.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pjatk.mprProject.Exception.BlogPostAlreadyExistsException;
import pl.pjatk.mprProject.Exception.BlogPostNotFoundException;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Repository.BlogPostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogPostServiceImplTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostServiceImpl blogPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllBlogPosts() {
        // Mockowanie zachowania i testowanie
    }

    @Test
    void shouldCreateBlogPost() {
        BlogPost newPost = new BlogPost();
        newPost.setNameOfCreatedPost("Test Post");

        when(blogPostRepository.findByNameOfCreatedPost("Test Post")).thenReturn(null);
        when(blogPostRepository.save(any(BlogPost.class))).thenReturn(newPost);

        BlogPost created = blogPostService.createBlogPost(newPost);

        assertNotNull(created);
        verify(blogPostRepository).save(newPost);
    }

    @Test
    void shouldThrowBlogPostAlreadyExistsException() {
        BlogPost existingPost = new BlogPost();
        existingPost.setNameOfCreatedPost("Existing Post");

        when(blogPostRepository.findByNameOfCreatedPost("Existing Post")).thenReturn(existingPost);

        BlogPost newPost = new BlogPost();
        newPost.setNameOfCreatedPost("Existing Post");

        assertThrows(BlogPostAlreadyExistsException.class, () -> {
            blogPostService.createBlogPost(newPost);
        });
    }

    @Test
    void shouldDeleteBlogPost() {
        Long id = 1L;
        when(blogPostRepository.existsById(id)).thenReturn(true);

        blogPostService.deleteBlogPost(id);

        verify(blogPostRepository).deleteById(id);
    }

    @Test
    void shouldThrowBlogPostNotFoundExceptionOnDelete() {
        Long id = 1L;
        when(blogPostRepository.existsById(id)).thenReturn(false);

        assertThrows(BlogPostNotFoundException.class, () -> {
            blogPostService.deleteBlogPost(id);
        });
    }

    @Test
    void shouldGetBlogPostById() {
        Long id = 1L;
        BlogPost post = new BlogPost();
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(post));

        BlogPost found = blogPostService.getBlogPostById(id);

        assertNotNull(found);
        assertEquals(post, found);
    }

    @Test
    void shouldThrowBlogPostNotFoundExceptionOnGetById() {
        Long id = 1L;
        when(blogPostRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BlogPostNotFoundException.class, () -> {
            blogPostService.getBlogPostById(id);
        });
    }

    @Test
    void shouldUpdateBlogPost() {
        Long id = 1L;
        BlogPost existingPost = new BlogPost();
        existingPost.setId(id);
        existingPost.setNameOfCreatedPost("Original Name");

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingPost));
        when(blogPostRepository.save(any(BlogPost.class))).thenReturn(existingPost);

        BlogPost updatedPost = new BlogPost();
        updatedPost.setNameOfCreatedPost("Updated Name");

        blogPostService.updateBlogPost(id, updatedPost);

        verify(blogPostRepository).save(existingPost);
        assertEquals("Updated Name", existingPost.getNameOfCreatedPost());
    }

    @Test
    void shouldThrowBlogPostNotFoundExceptionOnUpdate() {
        Long id = 1L;
        when(blogPostRepository.findById(id)).thenReturn(Optional.empty());

        BlogPost updatedPost = new BlogPost();

        assertThrows(BlogPostNotFoundException.class, () -> {
            blogPostService.updateBlogPost(id, updatedPost);
        });
    }
}
