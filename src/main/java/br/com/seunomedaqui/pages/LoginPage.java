package br.com.seunomedaqui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object da tela de Login do SauceDemo.
 *
 * O padrão Page Object separa a lógica de encontrar elementos HTML
 * da lógica dos testes. Isso deixa o código mais organizado e fácil de manter.
 * Se o site mudar um campo, você ajusta só aqui, não em todos os testes.
 */
public class LoginPage {

    private WebDriver driver;

    // URL da aplicação
    private static final String URL = "https://www.saucedemo.com";

    // Localizadores dos elementos da tela (By = "como encontrar o elemento")
    private final By campoUsuario   = By.id("user-name");
    private final By campoSenha     = By.id("password");
    private final By botaoLogin     = By.id("login-button");
    private final By mensagemErro   = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /** Abre a página de login no navegador */
    public void abrirPagina() {
        driver.get(URL);
    }

    /** Preenche o campo usuário */
    public void digitarUsuario(String usuario) {
        WebElement campo = driver.findElement(campoUsuario);
        campo.clear();
        campo.sendKeys(usuario);
    }

    /** Preenche o campo senha */
    public void digitarSenha(String senha) {
        WebElement campo = driver.findElement(campoSenha);
        campo.clear();
        campo.sendKeys(senha);
    }

    /** Clica no botão Login */
    public void clicarLogin() {
        driver.findElement(botaoLogin).click();
    }

    /**
     * Ação completa de login (atalho que combina os três métodos acima).
     * Útil quando não precisamos checar cada passo individualmente.
     */
    public void realizarLogin(String usuario, String senha) {
        digitarUsuario(usuario);
        digitarSenha(senha);
        clicarLogin();
    }

    /** Retorna a URL atual do navegador (usamos para verificar se o login funcionou) */
    public String obterUrlAtual() {
        return driver.getCurrentUrl();
    }

    /** Retorna o texto da mensagem de erro (quando o login falha) */
    public String obterMensagemErro() {
        return driver.findElement(mensagemErro).getText();
    }

    /** Verifica se a mensagem de erro está visível na tela */
    public boolean erroEstaVisivel() {
        return !driver.findElements(mensagemErro).isEmpty();
    }
}
