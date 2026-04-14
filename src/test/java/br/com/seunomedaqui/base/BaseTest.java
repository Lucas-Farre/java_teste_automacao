package br.com.seunomedaqui.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Classe base para todos os testes.
 * Responsável por iniciar e fechar o navegador automaticamente.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Baixa e configura o ChromeDriver automaticamente
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Descomente para rodar sem abrir janela

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Fecha o navegador ao final de cada teste
        }
    }
}
