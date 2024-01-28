package pl.pjatk.mprProject.Exception;

public class BlogPostAlreadyExistsException extends RuntimeException{
    public BlogPostAlreadyExistsException(String name) {
        super("Post o nazwie "+name+" już istnieje. Wybierz inną!");
    }
}
