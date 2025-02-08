package Controle;

import Modelo.*;
import Persistencia.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class Sistema {
    public static Usuario usuario;
    public static final String nomeApp = "AniToons";
    private static LocalDate dataAtual;
    public static Dimension screenSize;
    public static Catalogo catalogo;
    private static UsuarioDAO usuarioDAO;
    private static CartaoDAO cartaoDAO;
    private static AnimeDAO animeDAO;
    private static TemporadaDAO temporadaDAO;
    private static EpisodioDAO episodioDAO;

    public static boolean login(String email, String senha) {
        if((usuario = usuarioDAO.logInUsuario(email, senha)) != null) {
            if(usuario instanceof Cliente cliente) {
                List<Cartao> cartoes = cartaoDAO.buscaCartoes(cliente.getEmail());
                if(cartoes != null && !cartoes.isEmpty()) {
                    for(Cartao cartao : cartoes) {
                        cliente.adicionarCartao(cartao);
                    }
                }
            }
            return true;
        }

        return false;
    }

    public static void logOffUsuario(){
        Sistema.usuario = new Convidado();
    }

    public static String getTipoUsuario(){
        if (Sistema.usuario instanceof Cliente) {
            return "Cliente";
        }
        else if(Sistema.usuario instanceof Admin){
            return "Admin";
        }
        else return "Guest";
    }

    public static boolean criarUsuario(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) throws Exception {
        if(usuarioDAO.registrarUsuario(cpf, nome, dataNascimento, email, senha, tipo)) {
            if(usuario instanceof Convidado) {
                login(cpf, nome);
            }
            return true;
        }
        return false;
    }

    protected static void AdminFailSafe() throws Exception {
        usuarioDAO.adminFS();
    }

    public static boolean deletarUsuario(String email) throws Exception {
        if(usuarioDAO.eliminarUsuario(email)) {
            logOffUsuario();
            return true;
        }
        return false;
    }

    public static boolean adicionarCartao(String email, Cartao cartao) throws Exception {
        if(usuario instanceof Cliente cliente) {
            if(cartaoDAO.cadastrarCartao(cartao, email)) {
                cliente.adicionarCartao(cartao);
                return true;
            }
        }
        return false;
    }

    public static boolean editarCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao){
        if(usuario instanceof Cliente cliente) {
            if(cartaoDAO.editaCartao(email, cartaoIndice, numeroCartao, codigoCartao)) {
                LocalDate validadeCartao = cliente.getCartoes()[cartaoIndice].getValidadeCartao();
                cliente.removerCartao(cartaoIndice);
                cliente.adicionarCartao(new Cartao(numeroCartao, codigoCartao, validadeCartao));
                return true;
            }
        }
        return false;
    }

    public static boolean removerCartao(String email, int cartaoIndice) {
        if(usuario instanceof Cliente cliente) {
            if(cartaoDAO.excluiCartao(email, cartaoIndice)) {
                cliente.removerCartao(cartaoIndice);
                return true;
            }
        }
        return false;
    }

    public static boolean editarUsuario(String email, String[] dados) {
        if(usuarioDAO.editarUsuario(usuario, dados)) {
            Sistema.usuario.setNome(dados[0]);
            Sistema.usuario.setEmail(dados[1].replace(" ", ""));
            Sistema.usuario.setSenha(dados[2]);
            return true;
        }
        return false;
    }

    public static boolean cadastrarAnime(Anime anime) throws Exception {
        //animeDAO.adicionarAnime();
        return false;
    }

    public static void preencheCatalogo() {
        if((catalogo.animes = animeDAO.carregarAnimes()) == null) {
            System.err.println("Problema ao carregar animes");
            System.exit(1);
        }
    }

    public static void defineDataAtual(){
        dataAtual = LocalDate.now();
    }

    public static LocalDate verificaData(){
        return dataAtual;
    }

    public static boolean fazPagamento(int cartao){
        if (Sistema.usuario instanceof Cliente cliente) {
            return cliente.realizarPagamento(cartao);
        }
        return false;
    }

    public static Cliente getCliente(){
        if(Sistema.usuario instanceof Cliente cliente){
            return cliente;
        }
        else return null;
    }

    public static Admin getAdmin(){
        if(Sistema.usuario instanceof Admin admin){
            return admin;
        }
        else return null;
    }

    public static void verificaAssinatura(){
        if(Sistema.usuario instanceof Cliente cliente){
           if(cliente.isPremium()) {
               boolean vencido = cliente.getVencimento().isAfter(LocalDate.now());
               if(vencido) cliente.removerAssinatura();
           }
        }
    }

    public static void play(String videoPath) {
        try {
            // Para sistemas Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Usa o comando 'start' para abrir o arquivo com o reprodutor padrão
                Runtime.getRuntime().exec("cmd /c start " + videoPath);
            }
            // Para sistemas Unix/Linux/MacOS
            else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux") ||
                    System.getProperty("os.name").toLowerCase().contains("mac")) {
                // Usa o comando 'open' para MacOS ou 'xdg-open' para Linux
                String command = System.getProperty("os.name").toLowerCase().contains("mac") ?
                        "open " + videoPath : "xdg-open " + videoPath;
                Runtime.getRuntime().exec(command);
            }
        } catch (IOException e) {
            System.out.println("Erro ao tentar abrir o vídeo: " + e.getMessage());
        }
    }
}
