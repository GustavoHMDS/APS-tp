package Modelo;

import java.io.File;
import java.util.ArrayList;

public class Temporada {
    private String nome;
    private int codigo;
    public ArrayList episodios = new ArrayList<Episodio>();

    Temporada(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public int adicionarEpisodio(String nome, int codigo, String path) {
        File arquivo = new File(path);
        if(!arquivo.exists()) return 0;
        this.episodios.add(new Episodio(nome, codigo, arquivo));
        return 1;
    }

    public int removerEpisodio(int indice) {
        if(indice > episodios.size()) return 0;
        this.episodios.remove(indice);
        return 1;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
