package pl.pjatk.mprProject.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Model.Dto.BlogPostDto;
import pl.pjatk.mprProject.Service.BlogPostServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class BlogPostControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BlogPostServiceImpl blogPostService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BlogPostController blogPostController;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(blogPostController).build();
    }

    @Test
    void testGetAllPosts() throws Exception {
        List<BlogPost> posts = Arrays.asList(new BlogPost(), new BlogPost());
        when(blogPostService.getAllBlogPosts()).thenReturn(posts);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        verify(blogPostService).getAllBlogPosts();
        verify(modelMapper, times(posts.size())).map(any(BlogPost.class), eq(BlogPostDto.class));
    }

    @Test
    void testGetBlogPostById() throws Exception {
        Long postId = 1L;
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getBlogPostById(postId)).thenReturn(blogPost);

        mockMvc.perform(get("/post/" + postId))
                .andExpect(status().isOk())
                .andExpect(view().name("getPost"));

        verify(blogPostService).getBlogPostById(postId);
        verify(modelMapper).map(blogPost, BlogPostDto.class);
    }

    @Test
    void testCreateBlogPost() throws Exception {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setNameOfCreatedPost("Test Title");
        blogPostDto.setContentOfCreatedPost("Test Content");

        BlogPost blogPost = new BlogPost();
        blogPost.setDateOfCreatedPost(LocalDateTime.now());

        when(modelMapper.map(blogPostDto, BlogPost.class)).thenReturn(blogPost);

        when(blogPostService.createBlogPost(any(BlogPost.class))).thenReturn(blogPost);

        mockMvc.perform(post("/create")
                        .flashAttr("blogPostDto", blogPostDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(blogPostService).createBlogPost(any(BlogPost.class));
        verify(modelMapper).map(blogPostDto, BlogPost.class);
    }


    @Test
    void testDeleteBlogPost() throws Exception {
        Long postId = 1L;
        doNothing().when(blogPostService).deleteBlogPost(postId);

        mockMvc.perform(get("/delete/" + postId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(blogPostService).deleteBlogPost(postId);
    }

    @Test
    void testEditBlogPost() throws Exception {
        Long postId = 1L; // Zwróć uwagę na wartość ID
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setNameOfCreatedPost("Updated Title");
        blogPostDto.setContentOfCreatedPost("Updated Content");

        BlogPost blogPost = new BlogPost(); // Utwórz instancję BlogPost
        when(modelMapper.map(blogPostDto, BlogPost.class)).thenReturn(blogPost);
        when(blogPostService.updateBlogPost(eq(postId), any(BlogPost.class))).thenReturn(blogPost);

        mockMvc.perform(post("/edit/" + postId)
                        .flashAttr("blogPostDto", blogPostDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(blogPostService).updateBlogPost(eq(postId), any(BlogPost.class));
        verify(modelMapper).map(blogPostDto, BlogPost.class);
    }



    @Test
    void testShowCreatingForm() throws Exception {
        mockMvc.perform(get("/creatingForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("creatingForm"));
    }

    @Test
    void testShowEditingForm() throws Exception {
        Long postId = 1L;
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getBlogPostById(postId)).thenReturn(blogPost);

        mockMvc.perform(get("/editingForm/" + postId))
                .andExpect(status().isOk())
                .andExpect(view().name("editingForm"));

        verify(blogPostService).getBlogPostById(postId);
        verify(modelMapper).map(blogPost, BlogPostDto.class);
    }




}
