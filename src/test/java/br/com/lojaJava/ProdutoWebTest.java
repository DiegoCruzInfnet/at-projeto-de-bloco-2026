package br.com.lojaJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoWebTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Deve cadastrar um produto pela interface web e validar na lista")
    void deveCadastrarProdutoPelaInterface() {
        driver.get("http://localhost:" + port + "/produtos/novo");

        driver.findElement(By.name("nome")).sendKeys("Funko Pop Batman");
        driver.findElement(By.name("descricao")).sendKeys("Edição limitada");
        driver.findElement(By.name("preco")).sendKeys("150.00");
        driver.findElement(By.name("quantidade")).sendKeys("5");

        // Clica no botão
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera explícita de até 10 segundos para a URL conter "/produtos"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/produtos"));

        WebElement bodyNovo = driver.findElement(By.tagName("body"));
        String corpoPagina = bodyNovo.getText();

        assertTrue(corpoPagina.contains("Funko Pop Batman"), "O produto cadastrado deve aparecer na lista");
    }
}