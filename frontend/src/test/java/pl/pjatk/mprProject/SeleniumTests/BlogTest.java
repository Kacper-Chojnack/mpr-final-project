package pl.pjatk.mprProject.SeleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class BlogTest {

    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void openWebsite() {
        driver.get("http://localhost:8081/creatingForm");

        WebElement inputNameOfCreatedPost = driver.findElement(By.id("nameOfCreatedPost"));
        inputNameOfCreatedPost.sendKeys("Testowa nazwa posta");

        WebElement inputAuthorOfCreatedPost = driver.findElement(By.id("authorOfCreatedPost"));
        inputAuthorOfCreatedPost.sendKeys("Testowa nazwa autora");

        WebElement inputContentOfCreatedPost = driver.findElement(By.id("contentOfCreatedPost"));
        inputContentOfCreatedPost.sendKeys("Testowa treść posta");
    }

    @Test
    public void testHomePageAndAddPostLink() {
        driver.get("http://localhost:8081");

        assertEquals("Strona główna", driver.getTitle());

        WebElement addPostLink = driver.findElement(By.linkText("Dodaj post"));
        assertNotNull(addPostLink);
        addPostLink.click();

        assertTrue(driver.getCurrentUrl().endsWith("/creatingForm"));
    }

    @Test
    public void testDisplayPosts() {
        // Otwórz stronę główną
        driver.get("http://localhost:8081");

        // Znajdź wszystkie posty
        List<WebElement> posts = driver.findElements(By.cssSelector(".grid .mb-8"));
        assertFalse(posts.isEmpty());
    }

    @Test
    public void testFooterContent() {
        // Otwórz stronę główną
        driver.get("http://localhost:8081");

        // Znajdź stopkę i sprawdź jej treść
        WebElement footer = driver.findElement(By.tagName("footer"));
        assertTrue(footer.getText().contains("© 2024 Profesjonalny blog!"));
    }

    @Test
    public void testPostDetails() {
        driver.get("http://localhost:8081/post/52");

        WebElement postName = driver.findElement(By.xpath("//p[contains(text(),'Nazwa:')]/span"));
        WebElement postDate = driver.findElement(By.xpath("//p[contains(text(),'Data utworzenia:')]/span"));
        WebElement postAuthor = driver.findElement(By.xpath("//p[contains(text(),'Autor:')]/span"));

        assertNotNull(postName.getText());
        assertNotNull(postDate.getText());
        assertNotNull(postAuthor.getText());
    }

    @Test
    public void testEditPostFormLoaded() {
        driver.get("http://localhost:8081/editingForm/52");

        assertEquals("Edytuj post", driver.getTitle());

        WebElement nameField = driver.findElement(By.id("nameOfCreatedPost"));
        WebElement authorField = driver.findElement(By.id("authorOfCreatedPost"));
        WebElement contentField = driver.findElement(By.id("contentOfCreatedPost"));

        assertTrue(nameField.isDisplayed());
        assertTrue(authorField.isDisplayed());
        assertTrue(contentField.isDisplayed());
    }

    @Test
    public void testFillingAndSubmittingEditPostForm() {
        driver.get("http://localhost:8081/editingForm/52");

        driver.findElement(By.id("nameOfCreatedPost")).sendKeys(" Zaktualizowany Tytuł");
        driver.findElement(By.id("authorOfCreatedPost")).sendKeys(" Nowy Autor");
        driver.findElement(By.id("contentOfCreatedPost")).sendKeys(" Zaktualizowana treść postu.");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

    }

    @Test
    public void testPageLoadAndTitle() {
        driver.get("http://localhost:8081/creatingForm");

        assertEquals("Nowy Post", driver.getTitle());

        WebElement pageHeader = driver.findElement(By.tagName("h1"));
        assertEquals("Nowy Post", pageHeader.getText());
    }

    @Test
    public void testFormFillingAndSubmission() {
        driver.get("http://localhost:8081/creatingForm");

        driver.findElement(By.id("nameOfCreatedPost")).sendKeys("Tytuł Postu");
        driver.findElement(By.id("authorOfCreatedPost")).sendKeys("Autor Postu");
        driver.findElement(By.id("contentOfCreatedPost")).sendKeys("Treść postu.");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

    }

}
