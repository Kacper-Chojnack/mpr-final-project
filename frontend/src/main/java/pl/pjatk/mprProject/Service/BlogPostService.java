package pl.pjatk.mprProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.mprProject.Model.BlogPost;

import java.util.Arrays;
import java.util.List;

@Service
public class BlogPostService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080";


    public BlogPostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BlogPost> getAllBlogPosts() {
        BlogPost[] blogPosts = restTemplate.getForObject(baseUrl, BlogPost[].class);
        return Arrays.asList(blogPosts);
    }

    public BlogPost getBlogPostById(Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, BlogPost.class);
    }

    public BlogPost createBlogPost(BlogPost blogPost) {
        return restTemplate.postForObject(baseUrl, blogPost, BlogPost.class);
    }

    public BlogPost updateBlogPost(Long id, BlogPost blogPost) {
        restTemplate.put(baseUrl + "/" + id, blogPost);
        return blogPost;
    }

    public void deleteBlogPost(Long id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}
