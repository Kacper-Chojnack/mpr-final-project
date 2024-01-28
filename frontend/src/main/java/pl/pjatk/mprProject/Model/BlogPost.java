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

    public BlogPost(long id, String nameOfCreatedPost, LocalDateTime dateOfCreatedPost, String authorOfCreatedPost, String contentOfCreatedPost) {
        this.id = id;
        this.nameOfCreatedPost = nameOfCreatedPost;
        this.dateOfCreatedPost = dateOfCreatedPost;
        this.authorOfCreatedPost = authorOfCreatedPost;
        this.contentOfCreatedPost = contentOfCreatedPost;
    }

    public BlogPost() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameOfCreatedPost() {
        return nameOfCreatedPost;
    }

    public void setNameOfCreatedPost(String nameOfCreatedPost) {
        this.nameOfCreatedPost = nameOfCreatedPost;
    }

    public LocalDateTime getDateOfCreatedPost() {
        return dateOfCreatedPost;
    }

    public void setDateOfCreatedPost(LocalDateTime dateOfCreatedPost) {
        this.dateOfCreatedPost = dateOfCreatedPost;
    }

    public String getAuthorOfCreatedPost() {
        return authorOfCreatedPost;
    }

    public void setAuthorOfCreatedPost(String authorOfCreatedPost) {
        this.authorOfCreatedPost = authorOfCreatedPost;
    }

    public String getContentOfCreatedPost() {
        return contentOfCreatedPost;
    }

    public void setContentOfCreatedPost(String contentOfCreatedPost) {
        this.contentOfCreatedPost = contentOfCreatedPost;
    }
}
