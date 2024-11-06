package Entidades;

import java.time.LocalDate;

public class Cliente extends Usuario{
    Assinatura assinatura;

    public Cliente(String CPF, LocalDate dataNascimento, String nome, String email, String senha, Assinatura assinatura) {
        super(CPF, dataNascimento, nome, email, senha);
        this.assinatura = assinatura;
    }
}
