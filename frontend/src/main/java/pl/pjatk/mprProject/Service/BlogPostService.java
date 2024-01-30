package pl.pjatk.mprProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.pjatk.mprProject.Model.BlogPost;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class BlogPostService {

    private final RestClient restClient;
    private final String baseUrl = "http://localhost:8080";

    @Autowired
    public BlogPostService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<BlogPost> getAllBlogPosts() {
        ResponseEntity<List<BlogPost>> response = restClient.get()
                .uri(baseUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    public BlogPost getBlogPostById(Long id) {
        return restClient.get()
                .uri(baseUrl + "/" + id)
                .retrieve()
                .body(BlogPost.class);
    }

    public BlogPost createBlogPost(BlogPost blogPost) {
        return restClient.post()
                .uri(baseUrl)
                .contentType(APPLICATION_JSON)
                .body(blogPost)
                .retrieve()
                .body(BlogPost.class);
    }

    public BlogPost updateBlogPost(Long id, BlogPost blogPost) {
        return restClient.put()
                .uri(baseUrl + "/" + id)
                .contentType(APPLICATION_JSON)
                .body(blogPost)
                .retrieve()
                .body(BlogPost.class);
    }

    public void deleteBlogPost(Long id) {
        restClient.delete()
                .uri(baseUrl + "/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}
