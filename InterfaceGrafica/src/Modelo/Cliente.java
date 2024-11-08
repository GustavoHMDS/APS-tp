package Modelo;

import Controle.Sistema;

import java.time.LocalDate;

public class Cliente extends Usuario{
    Cartao cartaoPagamento;
    Assinatura assinatura;
    boolean premium;

    public Cliente(String CPF, LocalDate dataNascimento, String nome, String email, String senha, Assinatura assinatura, Cartao cartaoPagamento) {
        super(CPF, dataNascimento, nome, email, senha);
        this.cartaoPagamento = cartaoPagamento;
        this.assinatura = assinatura;
    }

    public void realizarPagamento(){
        if(cartaoPagamento != null && Sistema.verificaData().isBefore(cartaoPagamento.validadeCartao)){
            assinatura.foiPago();
        }
    }
}