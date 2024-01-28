package pl.pjatk.mprProject.Model.Dto;

import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogPostDto {
    private long id;
    private String nameOfCreatedPost;
    private LocalDateTime dateOfCreatedPost;
    private String authorOfCreatedPost;
    private String contentOfCreatedPost;
}
