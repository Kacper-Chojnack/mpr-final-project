package pl.pjatk.mprProject.Exception;

public class BlogPostNotFoundException extends RuntimeException{

    public BlogPostNotFoundException(String message){
        super(message);
    }
}
