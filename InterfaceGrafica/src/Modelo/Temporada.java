package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private Anime anime;
    private String nome;
    private int codigo;
    private int episodiosQuantidade;
    private String path;

    public Temporada(String nome, Anime anime, int codigo, int episodiosQuantidade, String path) {
        this.nome = nome;
        this.anime = anime;
        this.codigo = codigo;
        this.episodiosQuantidade = episodiosQuantidade;
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

    public int getEpisodiosQuantidade() {
        return episodiosQuantidade;
    }

    public void setEpisodiosQuantidade(int episodiosQuantidade) {
        this.episodiosQuantidade = episodiosQuantidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
