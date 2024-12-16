package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    private String nome;
    public List<Temporada> temporadas;
    private int codigo;

    Anime(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.temporadas = new ArrayList<Temporada>();
    }

    public int adicionarTemporada(String nome, int codigo) {
        this.temporadas.add(new Temporada(nome, codigo));
        return 1;
    }

    public int removerTemporada(int indice) {
        if(codigo > this.temporadas.size()) return 0;
        this.temporadas.remove(indice);
        return 1;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

}
