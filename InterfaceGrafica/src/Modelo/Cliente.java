package Modelo;

import Controle.Sistema;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Cliente extends Usuario{
    Cartao[] cartoes;
    private int cartoesQuantidade;
    boolean premium;
    LocalDate vencimento;

    public Cliente(String CPF, LocalDate dataNascimento, String nome, String email, String senha, boolean premium, LocalDate vencimento) {
        super(CPF, dataNascimento, nome, email, senha);
        this.cartoes = new Cartao[10];
        this.premium = premium;
        this.vencimento =vencimento;
    }

    public boolean realizarPagamento(int cartao){
        if(cartao >= this.cartoesQuantidade) return false;
        if(this.cartoes[cartao] != null && Sistema.verificaData().isBefore(this.cartoes[cartao].validadeCartao)){
            this.premium = true;
            LocalDate hoje = LocalDate.now();
            int mes = hoje.getMonthValue();
            mes = (mes+1 > 12) ? 1 : mes+1;
            int ano = hoje.getYear();
            ano = (mes == 1) ? ano+1 : ano;
            int dia = hoje.getDayOfMonth();
            this.vencimento = LocalDate.of(ano, mes, dia);
            return true;
        }
        return false;
    }

    public void removerAssinatura() {
        this.premium = false;
    }

    public void adicionarCartao(Cartao cartao){
        if(this.cartoesQuantidade < 10) {
            cartoes[cartoesQuantidade] = cartao;
            cartoesQuantidade++;
        }
    }

    public Cartao[] getCartoes() {
        return cartoes;
    }

    public Cartao getCartaoPagamento(int cartaoPosicao) {
        if(cartaoPosicao < this.cartoesQuantidade) return this.cartoes[cartaoPosicao];
        return null;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public LocalDate getVencimento() {
        return this.vencimento;
    }
}
