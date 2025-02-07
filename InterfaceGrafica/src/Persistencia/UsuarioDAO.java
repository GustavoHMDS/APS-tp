package Persistencia;

import Modelo.Usuario;

public interface UsuarioDAO {
    Usuario logInUsuario(String email, String password);
    boolean registrarUsuario(Usuario usuario);
    Usuario editarUsuario(Usuario usuario);
}
