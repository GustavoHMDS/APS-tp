package Modelo;

import java.io.File;

public class Episodio {
    private String nome;
    private Temporada temporada;
    private int codigo;
    private String path;

    public Episodio(String nome, Temporada temporada, int codigo, String path) {
        this.nome = nome;
        this.temporada = temporada;
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
