package Persistencia;

import Modelo.Usuario;

public class FileUsuarioDAO implements UsuarioDAO {
    @Override
    public Usuario logInUsuario(String email, String password) {
        return null;
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        return false;
    }

    @Override
    public Usuario editarUsuario(Usuario usuario) {
        return null;
    }
}
