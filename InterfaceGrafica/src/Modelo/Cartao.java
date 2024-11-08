package Modelo;

import java.time.LocalDate;

public class Cartao {
    long numeroCartao;
    int codigoCartao;
    LocalDate validadeCartao;

    public Cartao(long numeroCartao, int codigoCartao, LocalDate validadeCartao) {
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.validadeCartao = validadeCartao;
    }
}
