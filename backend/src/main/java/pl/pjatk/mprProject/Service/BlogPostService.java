package pl.pjatk.mprProject.Service;

import org.springframework.stereotype.Service;
import pl.pjatk.mprProject.Model.BlogPost;

import java.util.List;

public interface BlogPostService {
    List<BlogPost> getAllBlogPosts();
    BlogPost createBlogPost(BlogPost blogPost);
    void deleteBlogPost(Long id);
    BlogPost getBlogPostById(Long id);
    BlogPost updateBlogPost(Long id, BlogPost blogPost);

}
