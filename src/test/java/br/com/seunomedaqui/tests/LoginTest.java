package br.com.seunomedaqui.tests;

import br.com.seunomedaqui.base.BaseTest;
import br.com.seunomedaqui.pages.LoginPage;
import br.com.seunomedaqui.utils.ExcelReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de login no SauceDemo.
 *
 * Aqui ficam os cenários de teste. Cada método @Test é um caso de teste.
 * A classe herda de BaseTest, que cuida de abrir e fechar o navegador.
 */
public class LoginTest extends BaseTest {

    // Caminho para o Excel com os dados de teste (dentro de src/test/resources)
    private static final String EXCEL_PATH = "src/test/resources/dados_login.xlsx";

    // ------------------------------------------------------------------
    // CENÁRIO 1 — Login com sucesso (usuário e senha corretos)
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Login com credenciais válidas deve redirecionar para o inventário")
    public void loginValido() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrirPagina();

        loginPage.realizarLogin("standard_user", "secret_sauce");

        // Após login bem-sucedido, a URL deve conter "/inventory"
        String urlAtual = loginPage.obterUrlAtual();
        assertTrue(urlAtual.contains("/inventory"),
                "Esperava ser redirecionado para /inventory, mas a URL foi: " + urlAtual);
    }

    // ------------------------------------------------------------------
    // CENÁRIO 2 — Login com credenciais inválidas (deve mostrar erro)
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Login com senha errada deve exibir mensagem de erro")
    public void loginSenhaInvalida() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrirPagina();

        loginPage.realizarLogin("standard_user", "senha_errada");

        assertTrue(loginPage.erroEstaVisivel(),
                "Esperava mensagem de erro visível, mas não foi exibida.");

        String mensagem = loginPage.obterMensagemErro();
        assertTrue(mensagem.contains("Username and password do not match"),
                "Mensagem de erro inesperada: " + mensagem);
    }

    // ------------------------------------------------------------------
    // CENÁRIO 3 — Login com usuário bloqueado
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Usuário bloqueado deve ver mensagem de conta bloqueada")
    public void loginUsuarioBloqueado() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrirPagina();

        loginPage.realizarLogin("locked_out_user", "secret_sauce");

        assertTrue(loginPage.erroEstaVisivel());

        String mensagem = loginPage.obterMensagemErro();
        assertTrue(mensagem.contains("locked out"),
                "Mensagem de erro inesperada: " + mensagem);
    }

    // ------------------------------------------------------------------
    // CENÁRIO 4 — Login sem preencher nenhum campo
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Login sem credenciais deve pedir o usuário")
    public void loginCamposVazios() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrirPagina();

        loginPage.clicarLogin(); // Clica sem preencher nada

        assertTrue(loginPage.erroEstaVisivel());
        assertTrue(loginPage.obterMensagemErro().contains("Username is required"));
    }

    // ------------------------------------------------------------------
    // CENÁRIO 5 — Data-driven: lê vários cenários de uma planilha Excel
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Login data-driven: executa múltiplos cenários do Excel")
    public void loginDataDriven() {
        // Lê todas as linhas da aba "LoginTests" do arquivo Excel
        List<String[]> cenarios = ExcelReader.lerDados(EXCEL_PATH, "LoginTests");

        LoginPage loginPage = new LoginPage(driver);

        for (String[] cenario : cenarios) {
            // Colunas esperadas no Excel: usuario | senha | resultado_esperado | descricao
            String usuario           = cenario[0];
            String senha             = cenario[1];
            String resultadoEsperado = cenario[2]; // "sucesso" ou "erro"
            String descricao         = cenario[3];

            System.out.println("Executando cenário: " + descricao);

            loginPage.abrirPagina();
            loginPage.realizarLogin(usuario, senha);

            if ("sucesso".equalsIgnoreCase(resultadoEsperado)) {
                assertTrue(loginPage.obterUrlAtual().contains("/inventory"),
                        "Falha no cenário [" + descricao + "]: login deveria ter funcionado.");
            } else {
                assertTrue(loginPage.erroEstaVisivel(),
                        "Falha no cenário [" + descricao + "]: deveria exibir erro de login.");
            }
        }
    }
}
