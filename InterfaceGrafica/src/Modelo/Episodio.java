package Modelo;

import java.io.File;

public class Episodio {
    private String nome;
    private int codigo;
    private String path;

    Episodio(String nome, int codigo, String path) {
        this.nome = nome;
        this.codigo = codigo;
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPath() {
        return path;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
