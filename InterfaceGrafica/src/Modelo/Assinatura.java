package Modelo;

import java.time.LocalDate;

public class Assinatura {
    LocalDate vencimento;
    int id;
    boolean assinaturaPremium;

    public Assinatura(LocalDate vencimento, int id, boolean assinaturaPremium){
        this.vencimento = vencimento;
        this.id = id;
        this.assinaturaPremium = assinaturaPremium;
    }
    public void semPagamento(LocalDate data){
        if(this.vencimento.isAfter(data)) {
            assinaturaPremium = false;
        }
    }
    public void foiPago(){
        assinaturaPremium = true;
    }
}
