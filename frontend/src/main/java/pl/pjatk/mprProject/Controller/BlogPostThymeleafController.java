package pl.pjatk.mprProject.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.mprProject.Exception.BlogPostAlreadyExistsException;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Model.Dto.BlogPostDto;
import pl.pjatk.mprProject.Service.BlogPostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlogPostThymeleafController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    public String getAllPosts(Model model) {
        List<BlogPostDto> blogPostDtoList = blogPostService.getAllBlogPosts().stream()
                .map(post -> modelMapper.map(post, BlogPostDto.class))
                .toList();
        model.addAttribute("blogPostDtoList", blogPostDtoList);
        return "main";
    }

    @GetMapping("/post/{id}")
    public String getBlogPostById(@PathVariable Long id, Model model) {
        BlogPostDto blogPostDto = modelMapper.map(blogPostService.getBlogPostById(id), BlogPostDto.class);
        model.addAttribute("blogPost", blogPostDto);
        return "getPost";
    }

    @PostMapping("/create")
    public String createBlogPost(@ModelAttribute("blogPostDto") BlogPostDto blogPostDto, Model model) {
        try {
            BlogPost blogPostRequest = modelMapper.map(blogPostDto, BlogPost.class);
            blogPostRequest.setDateOfCreatedPost(LocalDateTime.now());
            BlogPost blogPost = blogPostService.createBlogPost(blogPostRequest);
            BlogPostDto blogPostResponse = modelMapper.map(blogPost, BlogPostDto.class);
            model.addAttribute("blogPostDto", blogPostResponse);
            return "redirect:/";
        } catch (BlogPostAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "creatingForm";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id);
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String editBlogPost(@PathVariable Long id, @ModelAttribute("blogPostDto") BlogPostDto blogPostDto, Model model) {
        BlogPost blogPostRequest = modelMapper.map(blogPostDto, BlogPost.class);
        BlogPost blogPost = blogPostService.updateBlogPost(id, blogPostRequest);
        BlogPostDto blogPostResponse = modelMapper.map(blogPost, BlogPostDto.class);
        model.addAttribute("blogPostDto", blogPostResponse);
        return "redirect:/";
    }

    @GetMapping("/creatingForm")
    public String showCreatingForm(Model model) {
        model.addAttribute("blogPostDto", new BlogPostDto());
        return "creatingForm";
    }

    @GetMapping("/editingForm/{id}")
    public String showEditingForm(Model model, @PathVariable long id) {
        BlogPostDto blogPostDto = modelMapper.map(blogPostService.getBlogPostById(id), BlogPostDto.class);
        model.addAttribute("blogPostDto", blogPostDto);
        return "editingForm";
    }
}
