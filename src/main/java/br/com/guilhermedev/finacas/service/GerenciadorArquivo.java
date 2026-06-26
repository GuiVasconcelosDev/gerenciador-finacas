package br.com.guilhermedev.finacas.service;

import br.com.guilhermedev.finacas.model.TipoTransacao;
import br.com.guilhermedev.finacas.model.Transacao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivo {

    private final String nomeArquivo = "financas.csv";

    public void salvarTransacao(Transacao transacao) {
        File arquivo = new File(nomeArquivo);

        boolean arquivoExiste = arquivo.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {

            if (!arquivoExiste) {
                writer.write("data,descricao,tipo,valor");
                writer.newLine();
            }

            writer.write(transacao.tomCSV());
            writer.newLine();

            System.out.println("Sua movimentação foi salva com suceeso no arquivo CSV!");
        } catch (IOException e) {
            System.out.println("Erro grave ao manipular o arquivo: " + e.getMessage());
        }
    }

    public List<Transacao> listarTransacoes() {
        List<Transacao> transacoes = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return transacoes;
        }

        try (BufferedReader render = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = render.readLine()) != null) {

                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");

                LocalDate data = LocalDate.parse(dados[0]);
                String descricao = dados[1];
                TipoTransacao tipo = TipoTransacao.valueOf(dados[2]);
                double valor = Double.parseDouble(dados[3]);

                Transacao t = new Transacao(data, descricao, tipo, valor);
                transacoes.add(t);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de finanças: " + e.getMessage());
        }
        return transacoes;
    }
}
