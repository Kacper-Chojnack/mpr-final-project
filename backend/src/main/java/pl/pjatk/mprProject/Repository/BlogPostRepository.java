package pl.pjatk.mprProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.mprProject.Model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {
     BlogPost findByNameOfCreatedPost(String name);
}
