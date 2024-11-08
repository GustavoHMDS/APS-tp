package Modelo;

import java.time.LocalDate;

public class Assinatura {
    LocalDate vencimento;
    int id;
    boolean assinaturaPremium;

    public Assinatura(LocalDate vencimento, int id){
        this.vencimento = vencimento;
        this.id = id;
    }
    public void semPagamento(){
        assinaturaPremium = false;
    }
    public void foiPago(){
        assinaturaPremium = true;
    }
}
