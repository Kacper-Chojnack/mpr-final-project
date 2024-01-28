package pl.pjatk.mprProject.Selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BlogTest {

    private WebDriver driver;


    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
    }

    @Test
    public void openWebsite(){
        driver.get("http://localhost:8081/creatingForm");

        WebElement inputNameOfCreatedPost = driver.findElement(By.id("nameOfCreatedPost"));
        inputNameOfCreatedPost.sendKeys("Testowa nazwa posta");

        WebElement inputAuthorOfCreatedPost = driver.findElement(By.id("authorOfCreatedPost"));
        inputAuthorOfCreatedPost.sendKeys("Testowa nazwa autora");

        WebElement inputContentOfCreatedPost = driver.findElement(By.id("contentOfCreatedPost"));
        inputContentOfCreatedPost.sendKeys("Testowa treść posta");
    }
}
