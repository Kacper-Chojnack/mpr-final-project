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
    private ModelMapper modelMapper; // Wstrzykiwanie ModelMapper

    @Autowired
    private BlogPostService blogPostService; // Wstrzykiwanie BlogPostServiceImpl

    // Endpoint do wyświetlenia wszystkich postów
    @GetMapping
    public String getAllPosts(Model model) {
        List<BlogPostDto> blogPostDtoList = blogPostService.getAllBlogPosts().stream()
                .map(post -> modelMapper.map(post, BlogPostDto.class))
                .toList();
        model.addAttribute("blogPostDtoList", blogPostDtoList);
        return "main"; // Zwraca widok "main"
    }

    // Endpoint do wyświetlenia konkretnego posta
    @GetMapping("/post/{id}")
    public String getBlogPostById(@PathVariable Long id, Model model) {
        BlogPostDto blogPostDto = modelMapper.map(blogPostService.getBlogPostById(id), BlogPostDto.class);
        model.addAttribute("blogPost", blogPostDto);
        return "getPost"; // Zwraca widok "getPost"
    }

    // Endpoint do tworzenia nowego posta
    @PostMapping("/create")
    public String createBlogPost(@ModelAttribute("blogPostDto") BlogPostDto blogPostDto, Model model) {
        try {
            // Mapowanie DTO na obiekt BlogPost
            BlogPost blogPostRequest = modelMapper.map(blogPostDto, BlogPost.class);
            blogPostRequest.setDateOfCreatedPost(LocalDateTime.now());
            // Wywołanie serwisu do stworzenia posta
            BlogPost blogPost = blogPostService.createBlogPost(blogPostRequest);
            BlogPostDto blogPostResponse = modelMapper.map(blogPost, BlogPostDto.class);
            model.addAttribute("blogPostDto", blogPostResponse);
            return "redirect:/"; // Po utworzeniu posta przekierowuje na stronę główną
        } catch (BlogPostAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage()); // Obsługa wyjątku w przypadku, gdy post o danej nazwie już istnieje
            return "creatingForm"; // Jeśli wystąpi błąd, zwraca widok "creatingForm" z informacją o błędzie
        }
    }

    // Endpoint do usuwania posta
    @GetMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id); // Wywołanie serwisu do usunięcia posta
        return "redirect:/"; // Po usunięciu posta przekierowuje na stronę główną
    }

    // Endpoint do edycji posta
    @PostMapping("/edit/{id}")
    public String editBlogPost(@PathVariable Long id, @ModelAttribute("blogPostDto") BlogPostDto blogPostDto, Model model) {
        // Mapowanie DTO na obiekt BlogPost
        BlogPost blogPostRequest = modelMapper.map(blogPostDto, BlogPost.class);
        // Wywołanie serwisu do aktualizacji posta
        BlogPost blogPost = blogPostService.updateBlogPost(id, blogPostRequest);
        BlogPostDto blogPostResponse = modelMapper.map(blogPost, BlogPostDto.class);
        model.addAttribute("blogPostDto", blogPostResponse);
        return "redirect:/"; // Po zaktualizowaniu posta przekierowuje na stronę główną
    }

    // Endpoint do wyświetlenia formularza tworzenia nowego posta
    @GetMapping("/creatingForm")
    public String showCreatingForm(Model model) {
        model.addAttribute("blogPostDto", new BlogPostDto());
        return "creatingForm"; // Zwraca widok "creatingForm"
    }

    // Endpoint do wyświetlenia formularza edycji posta
    @GetMapping("/editingForm/{id}")
    public String showEditingForm(Model model, @PathVariable long id) {
        // Mapowanie obiektu BlogPost na BlogPostDto
        BlogPostDto blogPostDto = modelMapper.map(blogPostService.getBlogPostById(id), BlogPostDto.class);
        model.addAttribute("blogPostDto", blogPostDto);
        return "editingForm"; // Zwraca widok "editingForm"
    }
}
