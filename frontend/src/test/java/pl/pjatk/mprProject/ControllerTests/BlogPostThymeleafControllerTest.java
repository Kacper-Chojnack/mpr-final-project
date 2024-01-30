package pl.pjatk.mprProject.ControllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pjatk.mprProject.Controller.BlogPostThymeleafController;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Model.Dto.BlogPostDto;
import pl.pjatk.mprProject.Service.BlogPostService;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class BlogPostThymeleafControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BlogPostService blogPostService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BlogPostThymeleafController blogPostThymeleafController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogPostThymeleafController).build();
    }

    @Test
    public void whenGetAllPosts_thenReturnsMainView() throws Exception {
        List<BlogPost> blogPosts = Arrays.asList(new BlogPost(), new BlogPost());
        when(blogPostService.getAllBlogPosts()).thenReturn(blogPosts);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("blogPostDtoList"));
    }

    @Test
    public void whenGetBlogPostById_thenReturnsGetPostView() throws Exception {
        Long id = 1L;
        BlogPost blogPost = new BlogPost();
        BlogPostDto blogPostDto = new BlogPostDto();

        when(blogPostService.getBlogPostById(id)).thenReturn(blogPost);
        when(modelMapper.map(blogPost, BlogPostDto.class)).thenReturn(blogPostDto);

        mockMvc.perform(get("/post/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("getPost"))
                .andExpect(model().attribute("blogPost", blogPostDto));
    }

    @Test
    public void whenCreateBlogPost_thenRedirects() throws Exception {
        BlogPostDto blogPostDto = new BlogPostDto();
        BlogPost blogPost = new BlogPost();

        when(modelMapper.map(blogPostDto, BlogPost.class)).thenReturn(blogPost);
        when(blogPostService.createBlogPost(any(BlogPost.class))).thenReturn(blogPost);

        mockMvc.perform(post("/create")
                        .flashAttr("blogPostDto", blogPostDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void whenDeleteBlogPost_thenRedirects() throws Exception {
        Long id = 1L;

        mockMvc.perform(get("/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(blogPostService, times(1)).deleteBlogPost(id);
    }

    @Test
    public void whenEditBlogPost_thenRedirects() throws Exception {
        Long id = 1L;
        BlogPostDto blogPostDto = new BlogPostDto();
        BlogPost blogPost = new BlogPost();

        when(modelMapper.map(blogPostDto, BlogPost.class)).thenReturn(blogPost);
        when(blogPostService.updateBlogPost(eq(id), any(BlogPost.class))).thenReturn(blogPost);

        mockMvc.perform(post("/edit/{id}", id)
                        .flashAttr("blogPostDto", blogPostDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void whenShowEditingForm_thenReturnsEditingFormView() throws Exception {
        Long id = 1L;
        BlogPost blogPost = new BlogPost();
        BlogPostDto blogPostDto = new BlogPostDto();

        when(blogPostService.getBlogPostById(id)).thenReturn(blogPost);
        when(modelMapper.map(blogPost, BlogPostDto.class)).thenReturn(blogPostDto);

        mockMvc.perform(get("/editingForm/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("editingForm"))
                .andExpect(model().attribute("blogPostDto", blogPostDto));
    }


}
