package Persistencia.DAOs;

import Modelo.Usuario;

public interface UsuarioDAO {
    Usuario logInUsuario(String email, String password);
    boolean registrarUsuario(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) throws Exception;
    boolean editarUsuario(Usuario usuario, String[] dados);
    boolean eliminarUsuario(String email);
    void adminFS() throws Exception;
}
