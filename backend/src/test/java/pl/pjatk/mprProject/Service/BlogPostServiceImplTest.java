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

import java.time.LocalDateTime;
import java.util.Collections;
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
    void getAllBlogPosts_ShouldReturnAllPosts() {
        when(blogPostRepository.findAll()).thenReturn(Collections.singletonList(new BlogPost()));
        assertFalse(blogPostService.getAllBlogPosts().isEmpty());
    }

    @Test
    void createBlogPost_WhenPostExists_ShouldThrowException() {
        BlogPost existingPost = new BlogPost();
        existingPost.setNameOfCreatedPost("Test Post");

        when(blogPostRepository.findByNameOfCreatedPost("Test Post")).thenReturn(existingPost);

        BlogPost newPost = new BlogPost();
        newPost.setNameOfCreatedPost("Test Post");

        assertThrows(BlogPostAlreadyExistsException.class, () -> blogPostService.createBlogPost(newPost));
    }

    @Test
    void deleteBlogPost_WhenPostDoesNotExist_ShouldThrowException() {
        when(blogPostRepository.existsById(1L)).thenReturn(false);
        assertThrows(BlogPostNotFoundException.class, () -> blogPostService.deleteBlogPost(1L));
    }

    @Test
    void getBlogPostById_WhenPostExists_ShouldReturnPost() {
        BlogPost post = new BlogPost();
        when(blogPostRepository.findById(1L)).thenReturn(Optional.of(post));

        assertNotNull(blogPostService.getBlogPostById(1L));
    }

    @Test
    void getBlogPostById_WhenPostDoesNotExist_ShouldThrowException() {
        when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BlogPostNotFoundException.class, () -> blogPostService.getBlogPostById(1L));
    }

    @Test
    void updateBlogPost_WhenPostDoesNotExist_ShouldThrowException() {
        when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());
        BlogPost postToUpdate = new BlogPost();
        postToUpdate.setId(1L);

        assertThrows(BlogPostNotFoundException.class, () -> blogPostService.updateBlogPost(1L, postToUpdate));
    }
}
