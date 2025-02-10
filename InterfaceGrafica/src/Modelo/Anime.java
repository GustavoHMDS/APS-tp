package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    private String nome;
    private int codigo;
    private int temporadasQuantidade;
    private String path;

    public Anime(String nome, int codigo, int temporadasQuantidade, String path) {
        this.nome = nome;
        this.codigo = codigo;
        this.temporadasQuantidade = temporadasQuantidade;
        this.path = path;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setTemporadasQuantidade(int temporadasQuantidade) {
        this.temporadasQuantidade = temporadasQuantidade;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public int getTemporadasQuantidade() {
        return temporadasQuantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPath() {
        return path;
    }

}
