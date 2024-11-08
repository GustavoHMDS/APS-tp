package Modelo;

import java.time.LocalDate;

public abstract class Usuario {
    String CPF;
    String nome;
    LocalDate dataNascimento;
    String email;
    String senha;

    public Usuario(String CPF, LocalDate dataNascimento, String nome, String email, String senha){
        this.CPF = CPF;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    public String getCPF(){
        return CPF;
    }
    public String getNome(){
        return nome;
    }
    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    public String getEmail(){
        return email;
    }
    public String getSenha(){
        return senha;
    }
}
