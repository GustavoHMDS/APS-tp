package Controle;

import Modelo.Cartao;
import Modelo.Cliente;
import Modelo.Convidado;
import Modelo.Usuario;
import Persistencia.DAOs.CartaoDAO;
import Persistencia.DAOs.UsuarioDAO;
import Persistencia.FileDAOs.FileCartaoDAO;
import Persistencia.FileDAOs.FileUsuarioDAO;

import java.time.LocalDate;
import java.util.List;

public class FileContas implements SistemaContas{
    private final Sistema sistema;
    private final UsuarioDAO usuarioDAO;
    private final CartaoDAO cartaoDAO;

    public FileContas(Sistema sistema) {
        this.sistema = sistema;
        usuarioDAO = FileUsuarioDAO.getInstance();
        cartaoDAO = FileCartaoDAO.getInstance();
    }

    @Override
    public boolean logIn(String email, String senha) {
        Usuario novoUsuario = usuarioDAO.logInUsuario(email, senha);
        if(novoUsuario != null){
            if(novoUsuario instanceof Cliente cliente) {
                List<Cartao> cartoes = cartaoDAO.buscaCartoes(cliente.getEmail());
                if(cartoes != null && !cartoes.isEmpty()) {
                    for(Cartao cartao : cartoes) {
                        cliente.adicionarCartao(cartao);
                    }
                }
            }
            sistema.setUsuario(novoUsuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean logOut() {
        sistema.setUsuario(new Convidado());
        return true;
    }

    @Override
    public boolean cadastrar(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) {
        try {
            if (usuarioDAO.registrarUsuario(cpf, nome, dataNascimento, email, senha, tipo)) {
                if (sistema.getUsuario() instanceof Convidado) {
                    logIn(cpf, nome);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editarUsuario(String email, String[] dados) {
        if(usuarioDAO.editarUsuario(sistema.getUsuario(), dados)) {
            sistema.getUsuario().setNome(dados[0]);
            sistema.getUsuario().setEmail(dados[1].replace(" ", ""));
            sistema.getUsuario().setSenha(dados[2]);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletarUsuario(String email) {
        try {
            if(usuarioDAO.eliminarUsuario(email)) {
                logOut();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean adicionarCartao(String email, Cartao cartao) {
        try {
            if(sistema.getUsuario() instanceof Cliente cliente) {
                if(cartaoDAO.cadastrarCartao(cartao, email)) {
                    cliente.adicionarCartao(cartao);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean editarCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao){
        if(sistema.getUsuario() instanceof Cliente cliente) {
            if(cartaoDAO.editaCartao(email, cartaoIndice, numeroCartao, codigoCartao)) {
                LocalDate validadeCartao = cliente.getCartoes()[cartaoIndice].getValidadeCartao();
                cliente.removerCartao(cartaoIndice);
                cliente.adicionarCartao(new Cartao(numeroCartao, codigoCartao, validadeCartao));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removerCartao(String email, int cartaoIndice) {
        if(sistema.getUsuario() instanceof Cliente cliente) {
            if(cartaoDAO.excluiCartao(email, cartaoIndice)) {
                cliente.removerCartao(cartaoIndice);
                return true;
            }
        }
        return false;
    }

    @Override
    public void adminFailSafe() {
        try {
            usuarioDAO.adminFS();
        } catch (Exception e) {
            System.out.println("Erro ao verificar failSafe");
        }
    }

}
