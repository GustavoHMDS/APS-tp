package Entidades;

import java.time.LocalDate;

public class Assinatura {
    int numeroCartao;
    int codigoCartao;
    LocalDate validadeCartao;
    LocalDate vencimento;
    int id;

    public Assinatura(int numeroCartao, int codigoCartao, LocalDate validadeCartao, LocalDate vencimento, int id){
        this.numeroCartao = numeroCartao;
        this.codigoCartao = numeroCartao;
        this.validadeCartao = validadeCartao;
        this.vencimento = vencimento;
        this.id = id;
    }
}
