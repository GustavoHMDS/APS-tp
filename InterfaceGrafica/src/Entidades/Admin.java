package Entidades;

import java.time.LocalDate;

public class Admin extends Usuario{
    int id;

    public Admin(String CPF, LocalDate dataNascimento, String nome, String email, String senha, int id) {
        super(CPF, dataNascimento, nome, email, senha);
        this.id = id;
    }
}
