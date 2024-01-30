package pl.pjatk.mprProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class BlogPost {
    @GeneratedValue
    @Id
    private long id;
    private String nameOfCreatedPost;
    private LocalDateTime dateOfCreatedPost;
    private String authorOfCreatedPost;
    private String contentOfCreatedPost;

}
