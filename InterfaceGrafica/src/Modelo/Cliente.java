package Modelo;

import Controle.SistemaGeral;

import java.time.LocalDate;

public class Cliente extends Usuario{
    public static int MAX_CARTOES = 10;

    Cartao[] cartoes;
    private int cartoesQuantidade;
    boolean premium;
    LocalDate vencimento;

    public Cliente(String CPF, LocalDate dataNascimento, String nome, String email, String senha, boolean premium, LocalDate vencimento) {
        super(CPF, dataNascimento, nome, email, senha);
        this.cartoes = new Cartao[MAX_CARTOES];
        this.cartoesQuantidade = 0;
        this.premium = premium;
        this.vencimento =vencimento;
    }

    public boolean realizarPagamento(int cartao){
        if(cartao >= this.cartoesQuantidade) return false;
        if(this.cartoes[cartao] != null && SistemaGeral.verificaData().isBefore(this.cartoes[cartao].validadeCartao)){
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

    public void removerCartao(int indice) {
        if(this.cartoesQuantidade == 0 || indice > this.cartoesQuantidade) return;
        for(int i = indice; i < this.cartoesQuantidade-1; i++) {
            this.cartoes[i] = this.cartoes[i+1];
        }
        this.cartoesQuantidade--;
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
    public int getCartoesQuantidade() {
        return cartoesQuantidade;
    }

}
