package br.com.seunomedaqui.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para leitura de dados de arquivos Excel (.xlsx).
 *
 * Permite que os testes sejam "data-driven": em vez de escrever um teste
 * por cenário, você escreve um teste só e alimenta com vários conjuntos
 * de dados vindos da planilha.
 */
public class ExcelReader {

    /**
     * Lê uma aba (sheet) do Excel e retorna os dados como lista de arrays.
     *
     * @param caminhoArquivo caminho para o .xlsx dentro do projeto
     * @param nomeAba        nome da aba na planilha (ex: "LoginTests")
     * @return lista de linhas; cada linha é um array de Strings com as colunas
     */
    public static List<String[]> lerDados(String caminhoArquivo, String nomeAba) {
        List<String[]> dados = new ArrayList<>();

        try (FileInputStream arquivo = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(arquivo)) {

            Sheet aba = workbook.getSheet(nomeAba);

            // Começa da linha 1 para pular o cabeçalho (linha 0)
            for (int i = 1; i <= aba.getLastRowNum(); i++) {
                Row linha = aba.getRow(i);
                if (linha == null) continue;

                int totalColunas = linha.getLastCellNum();
                String[] celulas = new String[totalColunas];

                for (int j = 0; j < totalColunas; j++) {
                    Cell celula = linha.getCell(j);
                    celulas[j] = celula != null ? celula.toString().trim() : "";
                }

                dados.add(celulas);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo Excel: " + caminhoArquivo, e);
        }

        return dados;
    }
}
