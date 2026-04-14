# 🧪 Projeto de Automação — SauceDemo

Projeto de automação de testes em Java com Selenium WebDriver, desenvolvido para demonstrar boas práticas de QA.

---

## 📁 Estrutura do Projeto

```
selenium-saucedemo/
│
├── pom.xml                          ← Dependências Maven
│
├── src/
│   ├── main/java/.../
│   │   ├── pages/
│   │   │   └── LoginPage.java       ← Page Object da tela de login
│   │   └── utils/
│   │       └── ExcelReader.java     ← Leitura de dados do Excel
│   │
│   └── test/
│       ├── java/.../
│       │   ├── base/
│       │   │   └── BaseTest.java    ← Configuração do WebDriver (antes/depois de cada teste)
│       │   └── tests/
│       │       └── LoginTest.java   ← Casos de teste
│       └── resources/
│           └── dados_login.xlsx     ← Dados para testes data-driven
```

---

## 🧩 Padrões e Técnicas Utilizados

| Técnica | O que é |
|---|---|
| **Page Object Model (POM)** | Separa os elementos HTML da lógica de teste. Facilita manutenção. |
| **Data-driven Testing** | Os dados de teste ficam numa planilha Excel. Você testa vários cenários sem duplicar código. |
| **WebDriverManager** | Baixa o ChromeDriver automaticamente, sem configuração manual. |
| **JUnit 5** | Framework de testes. Cada `@Test` é um caso de teste. |

---

## ▶️ Como Rodar

### Pré-requisitos
- Java 11+
- Maven
- Google Chrome instalado

### Executar todos os testes
```bash
mvn test
```

### Executar um teste específico
```bash
mvn test -Dtest=LoginTest#loginValido
```

---

## 📊 Planilha Excel (dados_login.xlsx)

A aba `LoginTests` deve ter este formato:

| usuario | senha | resultado_esperado | descricao |
|---|---|---|---|
| standard_user | secret_sauce | sucesso | Login válido |
| standard_user | senha_errada | erro | Senha incorreta |
| locked_out_user | secret_sauce | erro | Usuário bloqueado |
| | secret_sauce | erro | Sem usuário |

---

## 🎯 Cenários Cobertos

- ✅ Login com credenciais válidas
- ✅ Login com senha inválida
- ✅ Login com usuário bloqueado
- ✅ Login com campos vazios
- ✅ Múltiplos cenários via Excel (data-driven)

---

## 🚀 Tecnologias

- Java 11
- Selenium WebDriver 4.18
- JUnit 5
- Apache POI (leitura de Excel)
- WebDriverManager
- Maven
