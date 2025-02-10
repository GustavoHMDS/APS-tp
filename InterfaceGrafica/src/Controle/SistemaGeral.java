package Controle;

import Modelo.*;

import java.awt.*;
import java.time.LocalDate;

public class SistemaGeral {
    private Usuario usuario;
    public final String nomeApp = "AniToons";
    private static LocalDate dataAtual;
    private static Dimension screenSize;
    private Catalogo catalogo;
    private SistemaContas sistemaContas;
    private SistemaAnimes sistemaAnimes;


    private static SistemaGeral instance;

    private SistemaGeral(){}

    public static SistemaGeral getInstance() {
        if(instance == null) instance = new SistemaGeral();
        return instance;
    }

    //Usuario

    public boolean login(String email, String senha) {
        return sistemaContas.logIn(email, senha);
    }

    public boolean logOffUsuario(){
        return sistemaContas.logOut();
    }

    public String getTipoUsuario(){
        if (usuario instanceof Cliente) {
            return "Cliente";
        }
        else if(usuario instanceof Admin){
            return "Admin";
        }
        else return "Guest";
    }

    public boolean criarUsuario(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) {
        return sistemaContas.cadastrar(cpf, nome, dataNascimento, email, senha, tipo);
    }

    public boolean deletarUsuario(String email) {
        return sistemaContas.deletarUsuario(email);
    }

    public boolean adicionarCartao(String email, Cartao cartao) {
        return sistemaContas.adicionarCartao(email, cartao);
    }

    public boolean editarCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao){
        return sistemaContas.editarCartao(email, cartaoIndice, numeroCartao, codigoCartao);
    }

    public boolean removerCartao(String email, int cartaoIndice) {
        return sistemaContas.removerCartao(email, cartaoIndice);
    }

    public boolean editarUsuario(String email, String[] dados) {
        return sistemaContas.editarUsuario(email, dados);
    }

    //animes

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public boolean cadastrarAnime(String nome, int id) {
        return sistemaAnimes.cadastrarAnime(nome, id);
    }

    public boolean removerAnime(Anime anime) {
        return sistemaAnimes.removerAnime(anime);
    }

    public boolean cadastrarTemporada(Anime anime, Temporada temporada) {
        return sistemaAnimes.adicionarTemporada(anime, temporada);
    }

    public boolean removerTemporada(Anime anime, int id) {
        return sistemaAnimes.removerTemporada(anime, id);
    }

    public Temporada buscarTemporada(Anime anime, int id) {
        return sistemaAnimes.buscarTemporada(anime, id);
    }

    public boolean cadastrarEpisodio(Temporada temporada, Episodio episodio) {
        return sistemaAnimes.adicionarEpisodio(temporada, episodio);
    }

    public boolean removerEpisodio(Temporada temporada, int id) {
        return sistemaAnimes.removerEpisodio(temporada, id);
    }

    public Episodio buscarEpisodio(Temporada temporada, int id) {
        return sistemaAnimes.buscarEpisodio(temporada, id);
    }

    public void preencheCatalogo() {
        sistemaAnimes.preencheCatalogo();
    }

    public void play(String videoPath) {
        sistemaAnimes.play(videoPath);
    }

    //sistema

    public static void defineDataAtual(){
        dataAtual = LocalDate.now();
    }

    public static LocalDate verificaData(){
        return dataAtual;
    }

    public boolean fazPagamento(int cartao){
        if (usuario instanceof Cliente cliente) {
            return cliente.realizarPagamento(cartao);
        }
        return false;
    }

    public Cliente getCliente(){
        if(usuario instanceof Cliente cliente){
            return cliente;
        }
        else return null;
    }

    public Admin getAdmin(){
        if(usuario instanceof Admin admin){
            return admin;
        }
        else return null;
    }

    public void verificaAssinatura(){
        if(usuario instanceof Cliente cliente){
           if(cliente.isPremium()) {
               boolean vencido = cliente.getVencimento().isAfter(LocalDate.now());
               if(vencido) cliente.removerAssinatura();
           }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public static Dimension getScreenSize() {
        return SistemaGeral.screenSize;
    }
    public static void setScreenSize(Dimension screenSize) {
        SistemaGeral.screenSize = screenSize;
    }

    void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void inicializarSistema(SistemaContas sistemaContas, SistemaAnimes sistemaAnimes) {
        usuario = new Convidado();
        catalogo = new Catalogo();
        this.sistemaContas = sistemaContas;
        this.sistemaAnimes = sistemaAnimes;
        sistemaContas.adminFailSafe();
        preencheCatalogo();
    }
}
