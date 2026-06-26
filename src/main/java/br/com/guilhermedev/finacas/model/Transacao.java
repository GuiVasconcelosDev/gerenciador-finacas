package br.com.guilhermedev.finacas.model;

import java.time.LocalDate;

public record Transacao(
        LocalDate data,
        String descricao,
        TipoTransacao tipo,
        double valor
) {
    public String tomCSV() {
        return data + "," + descricao + "," + tipo + "," + valor;
    }
}
