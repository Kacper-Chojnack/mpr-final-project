package pl.pjatk.mprProject.Service;

import org.springframework.stereotype.Service;
import pl.pjatk.mprProject.Exception.BlogPostAlreadyExistsException;
import pl.pjatk.mprProject.Exception.BlogPostNotFoundException;
import pl.pjatk.mprProject.Model.BlogPost;
import pl.pjatk.mprProject.Repository.BlogPostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) {
        BlogPost existingPost = blogPostRepository.findByNameOfCreatedPost(blogPost.getNameOfCreatedPost());
        if (existingPost != null && existingPost.getNameOfCreatedPost().equals(blogPost.getNameOfCreatedPost())) {
            throw new BlogPostAlreadyExistsException("Post o nazwie '" + existingPost.getNameOfCreatedPost() + "' już istnieje.");
        }
        blogPost.setDateOfCreatedPost(LocalDateTime.now());
        return blogPostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPost(Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new BlogPostNotFoundException("Nie znaleziono posta o id: " + id);
        }
        if(blogPostRepository.count() == 0){
            throw new BlogPostNotFoundException("Baza danych jest pusta!");
        }
        blogPostRepository.deleteById(id);
    }

    @Override
    public BlogPost getBlogPostById(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new BlogPostNotFoundException("Nie znaleziono posta o id: " + id));
    }

    @Override
    public BlogPost updateBlogPost(Long id, BlogPost blogPost) {
        BlogPost updatingBlogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new BlogPostNotFoundException("Nie znaleziono posta o id: " + id));

        if(blogPostRepository.count() == 0){
            throw new BlogPostNotFoundException("Baza danych jest pusta!");
        }

        updatingBlogPost.setNameOfCreatedPost(blogPost.getNameOfCreatedPost());
        updatingBlogPost.setContentOfCreatedPost(blogPost.getContentOfCreatedPost());
        updatingBlogPost.setAuthorOfCreatedPost(blogPost.getAuthorOfCreatedPost());

        return blogPostRepository.save(updatingBlogPost);
    }
}
