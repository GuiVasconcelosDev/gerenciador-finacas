package br.com.guilhermedev.finacas.service;

import br.com.guilhermedev.finacas.model.TipoTransacao;
import br.com.guilhermedev.finacas.model.Transacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorBanco {

    public void salvarTransacao(Transacao transacao) {
        String sql = "INSERT INTO transacoes (data, descricao, tipo, valor) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(transacao.data()));
            stmt.setString(2, transacao.descricao());
            stmt.setString(3, transacao.tipo().name());
            stmt.setDouble(4, transacao.valor());

            stmt.executeUpdate();
            System.out.println("Sua movimentação foi salva com sucesso no Banco de Dados (PostgreSQL)!");

        } catch (SQLException e) {
            System.out.println("Erro grave ao salvar dados no banco: " + e.getMessage());
        }
    }


    public List<Transacao> listarTransacoes() {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT data, descricao, tipo, valor FROM transacoes ORDER BY data ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LocalDate data = rs.getDate("data").toLocalDate();
                String descricao = rs.getString("descricao");
                TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));
                double valor = rs.getDouble("valor");

                transacoes.add(new Transacao(data, descricao, tipo, valor));
            }

        } catch (SQLException e) {
            System.out.println("Erro grave ao ler dados do banco: " + e.getMessage());
        }

        return transacoes;
    }

}