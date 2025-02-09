package Persistencia.FileDAOs;

import Modelo.Admin;
import Modelo.Cliente;
import Modelo.Usuario;
import Persistencia.DAOs.UsuarioDAO;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileUsuarioDAO extends FileDAO implements UsuarioDAO {
    private static final String BASE_PATH = "usuarios";

    //Singleton
    private static FileUsuarioDAO instance;

    private FileUsuarioDAO() {}

    public static FileUsuarioDAO getInstance() {
        if (instance == null) {
            instance = new FileUsuarioDAO();
        }
        return instance;
    }

    //Métodos
    @Override
    public Usuario logInUsuario(String email, String senha) {
        File pastaUsuario = new File(BASE_PATH, email);

        if (!pastaUsuario.exists() || !pastaUsuario.isDirectory()) {
            return null;
        }

        File arquivoDados = new File(pastaUsuario, "dados.txt");
        if (!arquivoDados.exists()) {
            System.out.println("Arquivo de dados para o email " + email + " não encontrado.");
            return null;
        }

        try {
            Usuario usuario = carregarUsuario(arquivoDados, email, senha);
            if (usuario != null) return usuario;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo do usuário", e);
        }

        return null;
    }

    @Override
    public boolean registrarUsuario(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) throws Exception {
        String basePath = "usuarios"; // Diretório base para usuários
        File pastaBase = new File(basePath);

        // Garante que a pasta base exista
        verificarOuCriarPasta(pastaBase, "Não foi possível criar a pasta base: " + basePath);

        // Verifica se o usuário já existe
        File pastaUsuario = new File(pastaBase, email);
        if (pastaUsuario.exists()) {
            System.out.println("Usuário com email " + email + " já existe.");
            return false;
        }

        // Cria a pasta do usuário
        verificarOuCriarPasta(pastaUsuario, "Não foi possível criar a pasta do usuário: " + email);

        // Criar o arquivo de dados do usuário
        escreverDadosUsuario(pastaUsuario, cpf, nome, dataNascimento, email, senha, tipo);

        System.out.println("Usuário criado com sucesso: " + email);
        return true;
    }

    @Override
    public boolean editarUsuario(Usuario usuario, String[] dados) {
        File pastaUsuario = new File(BASE_PATH, usuario.getEmail());
        if (!pastaUsuario.exists()) return false;

        File arquivo = new File(pastaUsuario, "dados.txt");
        try {
            List<String> linhas = Files.readAllLines(arquivo.toPath());
            for (int i = 0; i < linhas.size(); i++) {
                String chave = linhas.get(i).split(": ")[0];
                switch (chave) {
                    case "Nome" -> linhas.set(i, "Nome: " + dados[0]);
                    case "Email" -> linhas.set(i, "Email: " + dados[1].trim());
                    case "Senha" -> linhas.set(i, "Senha: " + dados[2]);
                }
            }
            Files.write(arquivo.toPath(), linhas);

            if (!usuario.getEmail().equals(dados[1].trim())) {
                File novaPasta = new File(BASE_PATH, dados[1].trim());
                pastaUsuario.renameTo(novaPasta);
            }

            usuario.setNome(dados[0]);
            usuario.setEmail(dados[1].trim());
            usuario.setSenha(dados[2]);

        } catch (IOException e) {
            System.out.println("Erro ao salvar alterações: " + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean eliminarUsuario(String email) {
        File pastaUsuario = new File(BASE_PATH, email);

        // Verifica se a pasta existe
        if (!pastaUsuario.exists()) {
            System.out.println("Usuário com o email " + email + " não encontrado.");
            return false;
        }

        // Deletar a pasta e todo o seu conteúdo
        if (deletarPastaRecursivamente(pastaUsuario)) {
            System.out.println("Usuário com o email " + email + " deletado com sucesso.");
            return true;
        } else {
            System.out.println("Falha ao deletar o usuário com o email " + email + ".");
            return false;
        }
    }

    @Override
    public void adminFS() throws Exception {
        File pastaBase = new File(BASE_PATH);

        // Verifica se a pasta base de usuários existe
        if (!pastaBase.exists()) {
            if (!pastaBase.mkdirs()) {
                throw new IOException("Não foi possível criar a pasta base: " + pastaBase);
            }
        }

        String email = "AdminFS";
        // Verifica se o Admin já existe, baseado no email (nome da pasta)
        File pastaUsuario = new File(pastaBase, email);
        if (!pastaUsuario.exists()) {
            String nome = "AdminFS";
            String senha = "AdminFS";
            String cpf = "00000000000"; // Você pode escolher um CPF fictício
            String dataNascimento = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String tipo = "Admin"; // Tipo do usuário

            System.out.printf("Acionando FailSafe, criando um Admin...\nEmail: %s\nSenha: %s", email, senha);
            registrarUsuario(cpf, nome, dataNascimento, email, senha, tipo);
        }
    }

    //Auxiliares de Login

    private Usuario carregarUsuario(File arquivoDados, String email, String senha) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoDados))) {
            String cpf = null, nome = null, dataNascimento = null, tipo = null;
            boolean premium = false;
            LocalDate vencimento = null;
            int id = -1;

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length < 2) continue;

                String chave = partes[0].trim();
                String valor = partes[1].trim();

                switch (chave) {
                    case "CPF" -> cpf = valor;
                    case "Nome" -> nome = valor;
                    case "DataNascimento" -> dataNascimento = valor;
                    case "Email" -> {
                        if (!valor.equals(email)) {
                            System.out.println("Email não bate com o nome do folder.");
                            return null;
                        }
                    }
                    case "Senha" -> {
                        if (!valor.equals(senha)) {
                            return null;
                        }
                    }
                    case "Tipo" -> tipo = valor;
                    case "ID" -> id = Integer.parseInt(valor);
                    case "Assinatura" -> premium = valor.equalsIgnoreCase("true");
                    case "Vencimento" -> vencimento = LocalDate.parse(valor, DateTimeFormatter.ofPattern("d/M/yyyy"));
                }
            }

            return criarUsuario(cpf, nome, dataNascimento, tipo, email, senha, id, premium, vencimento);
        }
    }

    private Usuario criarUsuario(String cpf, String nome, String dataNascimento, String tipo, String email, String senha, int id, boolean premium, LocalDate vencimento) {
        if (cpf == null || nome == null || dataNascimento == null || tipo == null) {
            System.out.println("Dados do usuário incompletos.");
            return null;
        }

        LocalDate data = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("d/M/yyyy"));

        return switch (tipo.toLowerCase()) {
            case "admin" -> new Admin(cpf, data, nome, email, senha, id);
            case "cliente" -> new Cliente(cpf, data, nome, email, senha, premium, vencimento);
            default -> null;
        };
    }

    //Auxiliares de Registro

    private void escreverDadosUsuario(File pastaUsuario, String cpf, String nome, String dataNascimento, String email, String senha, String tipo) throws IOException {
        File arquivoDados = new File(pastaUsuario, "dados.txt");

        try (FileWriter writer = new FileWriter(arquivoDados)) {
            writer.write("CPF: " + cpf + "\n");
            writer.write("Nome: " + nome + "\n");
            writer.write("DataNascimento: " + dataNascimento + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Senha: " + senha + "\n");
            writer.write("Tipo: " + tipo + "\n");

            if (tipo.equalsIgnoreCase("Admin")) {
                writer.write("ID: " + buscaIdDisponivel() + "\n");
            } else {
                writer.write("Assinatura: falso\n");
                LocalDate data = LocalDate.now();
                writer.write("Vencimento: " + data.format(DateTimeFormatter.ofPattern("d/M/yyyy")) + "\n");

                // Criar a pasta de cartões
                File pastaCartoes = new File(pastaUsuario, "cartoes");
                verificarOuCriarPasta(pastaCartoes, "Não foi possível criar a pasta de cartões para: " + email);
            }
        }
    }

    private int buscaIdDisponivel() {
        File pastaBase = new File(BASE_PATH);

        if (!pastaBase.exists() || !pastaBase.isDirectory()) {
            throw new RuntimeException("Diretório de usuários não encontrado.");
        }

        int maxId = 0; // Inicializamos o ID máximo como 0

        // Iterar sobre as pastas de usuários
        File[] pastasUsuarios = pastaBase.listFiles(File::isDirectory);
        if (pastasUsuarios == null) {
            throw new RuntimeException("Erro ao listar pastas de usuários.");
        }

        for (File pastaUsuario : pastasUsuarios) {
            File arquivoDados = new File(pastaUsuario, "dados.txt");
            if (arquivoDados.exists() && arquivoDados.isFile()) {
                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoDados))) {
                    String linha;
                    boolean isAdmin = false;

                    while ((linha = leitor.readLine()) != null) {
                        String[] partes = linha.split(":");
                        if (partes.length < 2) continue;

                        String chave = partes[0].trim();
                        String valor = partes[1].trim();

                        if (chave.equals("Tipo") && valor.equalsIgnoreCase("Admin")) {
                            isAdmin = true;
                        } else if (isAdmin && chave.equals("ID")) {
                            int id = Integer.parseInt(valor);
                            maxId = Math.max(maxId, id);
                            break; // ID encontrado, pode parar de ler este arquivo
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao ler o arquivo: " + arquivoDados.getAbsolutePath(), e);
                }
            }
        }

        // Retorna o próximo ID disponível
        return maxId + 1;
    }
}
