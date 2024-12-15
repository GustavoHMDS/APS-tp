package Controle;

import Modelo.*;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Sistema {
    public static Usuario usuario;
    public static final String nomeApp = "AniToons";
    private static LocalDate dataAtual;
    public static Dimension screenSize;

    public static boolean login(String email, String senha) {
        String basePath = "usuarios"; // Diretório base para usuários
        File pastaUsuario = new File(basePath, email);

        // Verifica se o usuário existe
        if (!pastaUsuario.exists() || !pastaUsuario.isDirectory()) {
            return false;
        }

        // Lê o arquivo de dados do usuário
        File arquivoDados = new File(pastaUsuario, "dados.txt");
        if (!arquivoDados.exists()) {
            System.out.println("Arquivo de dados para o email " + email + " não encontrado.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoDados))) {
            String linha;
            String cpf = null;
            String nome = null;
            String dataNascimento = null;
            String tipo = null;
            boolean premium = false;
            LocalDate vencimento = null;
            int id = -1;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length < 2) continue;

                String chave = partes[0].trim();
                String valor = partes[1].trim();

                switch (chave) { // Switch case para organizar os dados do usuário
                    case "CPF":
                        cpf = valor;
                        break;
                    case "Nome":
                        nome = valor;
                        break;
                    case "DataNascimento":
                        dataNascimento = valor;
                        break;
                    case "Email":
                        if (!valor.equals(email)) {
                            System.out.println("Email não bate com o nome do folder.");
                            return false;
                        }
                        break;
                    case "Senha":
                        if (!valor.equals(senha)) {
                            return false;
                        }
                        break;
                    case "Tipo":
                        tipo = valor;
                        break;
                    case "ID":
                        id = Integer.parseInt(valor);
                        break;
                    case "Assinatura":
                        premium = valor.equalsIgnoreCase("true");
                        break;
                    case "Vencimento":
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                        vencimento = LocalDate.parse(valor, formatter);
                        break;
                }
            }

            // Validação de dados obrigatórios
            if (cpf == null || nome == null || dataNascimento == null || tipo == null) {
                System.out.println("Dados do usuário incompletos.");
                return false;
            }
            LocalDate data = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("d/M/yyyy"));

            // Criação do objeto correspondente
            if ("Admin".equalsIgnoreCase(tipo)) {
                Sistema.usuario = new Admin(cpf, data, nome, email, senha, id);
                return true;
            } else if ("Cliente".equalsIgnoreCase(tipo)) {
                Cliente cliente = new Cliente(cpf, data, nome, email, senha, premium, vencimento);
                // Lê os cartões do cliente
                File pastaCartoes = new File(pastaUsuario, "cartoes");
                if (pastaCartoes.exists() && pastaCartoes.isDirectory()) {
                    File[] arquivosCartoes = pastaCartoes.listFiles((_, name) -> name.startsWith("cartao_") && name.endsWith(".txt"));
                    if (arquivosCartoes != null) {
                        for (File arquivoCartao : arquivosCartoes) {
                            try (BufferedReader cartaoReader = new BufferedReader(new FileReader(arquivoCartao))) {
                                String numero = null;
                                String codigo = null;
                                String validade = null;

                                while ((linha = cartaoReader.readLine()) != null) {
                                    String[] partes = linha.split(":");
                                    if (partes.length < 2) continue;

                                    String chave = partes[0].trim();
                                    String valor = partes[1].trim();

                                    switch (chave) {
                                        case "Numero":
                                            numero = valor;
                                            break;
                                        case "Codigo":
                                            codigo = valor;
                                            break;
                                        case "Validade":
                                            validade = valor;
                                            break;
                                    }
                                }

                                if (numero != null && codigo != null && validade != null) {
                                    String[] dataLida = validade.split("-");
                                    LocalDate dataValidade = LocalDate.of(Integer.parseInt(dataLida[0]), Integer.parseInt(dataLida[1]), Integer.parseInt(dataLida[2]));
                                    cliente.adicionarCartao(new Cartao(Integer.parseInt(numero), Integer.parseInt(codigo), dataValidade));
                                }
                            } catch (IOException e) {
                                System.out.println("Erro ao ler o cartão: " + arquivoCartao.getName());
                            }
                        }
                    }
                }
                Sistema.usuario = cliente;
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static void LogOffUsuario(){
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

    private static int buscaIdDisponivel(String tipoUsuario) {
        String basePath = "usuarios"; // Diretório base para os usuários
        File pastaBase = new File(basePath);

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
                    while ((linha = leitor.readLine()) != null) {
                        String[] partes = linha.split(":");
                        if (partes[0].trim().equals("Tipo") && partes[1].trim().equals(tipoUsuario)) {
                            // Verificar se o tipo é igual ao solicitado
                            while ((linha = leitor.readLine()) != null) {
                                partes = linha.split(":");
                                if (partes[0].trim().equals("ID")) {
                                    int id = Integer.parseInt(partes[1].trim());
                                    maxId = Math.max(maxId, id);
                                    break; // ID encontrado, parar de ler o arquivo atual
                                }
                            }
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

    public static void criarUsuario(String cpf, String nome, String dataNascimento, String email, String senha, String tipo) throws Exception {
        String basePath = "usuarios"; // Diretório base para usuários
        File pastaBase = new File(basePath);

        // Verifica se a pasta base existe, senão cria
        if (!pastaBase.exists()) {
            if (!pastaBase.mkdirs()) {
                System.out.println("Não foi possível criar a pasta base: " + basePath);
                return;
            }
        }

        // Verifica se já existe um usuário com o mesmo CPF
        File pastaUsuario = new File(pastaBase, email);
        if (pastaUsuario.exists()) {
            throw new Exception("Usuário com CPF " + email + " já existe.");
        }

        // Cria a pasta do usuário
        if (!pastaUsuario.mkdirs()) {
            throw new IOException("Não foi possível criar a pasta para o CPF: " + email);
        }

        // Criar o arquivo de dados
        File arquivoDados = new File(pastaUsuario, "dados.txt");
        try (FileWriter writer = new FileWriter(arquivoDados)) {
            writer.write("CPF: " + cpf + "\n");
            writer.write("Nome: " + nome + "\n");
            writer.write("DataNascimento: " + dataNascimento + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Senha: " + senha + "\n");
            writer.write("Tipo: " + tipo + "\n");
            if(tipo.equals("Admin")) writer.write("ID: " + buscaIdDisponivel(tipo) + "\n");
            else{
                writer.write("Assinatura: falso\n");
                LocalDate data = LocalDate.now();
                writer.write("Vencimento: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear() + "\n");
                // Criar as subpastas "cartões" e "assinaturas"
                File pastaCartoes = new File(pastaUsuario, "cartoes");
                if (!pastaCartoes.mkdirs()) {
                    throw new IOException("Não foi possível criar a pasta de cartões para o CPF: " + cpf);
                }
            }
        }

        System.out.println("Usuário criado com sucesso: " + cpf);
    }

    public static void AdminFailSafe() throws Exception {
        String nome = "AdminFS";
        String email = "AdminFS";
        String senha = "AdminFS";
        String cpf = "00000000000"; // Você pode escolher um CPF fictício
        String dataNascimento = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String tipo = "Admin"; // Tipo do usuário

        String basePath = "usuarios"; // Diretório base para usuários
        File pastaBase = new File(basePath);

        // Verifica se a pasta base de usuários existe
        if (!pastaBase.exists()) {
            if (!pastaBase.mkdirs()) {
                throw new IOException("Não foi possível criar a pasta base: " + basePath);
            }
        }

        // Verifica se o Admin já existe, baseado no email (nome da pasta)
        File pastaUsuario = new File(pastaBase, email);
        if (!pastaUsuario.exists()) {
            System.out.println("Acionando FailSafe, criando um Admin...\nEmail: AdminFS\nSenha: AdminFS");
            criarUsuario(cpf, nome, dataNascimento, email, senha, tipo);
        }
    }

    public static void deletarUsuario(String email) throws Exception {
        String basePath = "usuarios"; // Diretório base para usuários
        File pastaUsuario = new File(basePath, email);

        // Verifica se a pasta existe
        if (!pastaUsuario.exists()) {
            throw new Exception("Usuário com o email " + email + " não encontrado.");
        }

        // Deletar a pasta e todo o seu conteúdo
        if (deletarPastaRecursivamente(pastaUsuario)) {
            System.out.println("Usuário com o email " + email + " deletado com sucesso.");
        } else {
            throw new Exception("Falha ao deletar o usuário com o email " + email + ".");
        }
    }

    // Método auxiliar para deletar pastas recursivamente
    private static boolean deletarPastaRecursivamente(File pasta) {
        if (pasta.isDirectory()) {
            // Deleta todos os arquivos e subpastas
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (!deletarPastaRecursivamente(arquivo)) {
                        return false;
                    }
                }
            }
        }
        // Deleta o próprio arquivo ou pasta
        return pasta.delete();
    }

    public static void adicionarCartao(String email, Cartao cartao) throws Exception {
        String basePath = "usuarios"; // Diretório base
        File pastaCartoes = new File(basePath + "/" + email + "/cartoes/");

        // Verifica se a pasta do cliente existe
        if (!pastaCartoes.exists() || !pastaCartoes.isDirectory()) {
            throw new Exception("Usuário com o email " + email + " não encontrado ou pasta de cartões inexistente.");
        }

        // Lista os cartões existentes
        File[] arquivosCartoes = pastaCartoes.listFiles((dir, name) -> name.endsWith(".txt"));
        if (arquivosCartoes != null && arquivosCartoes.length >= 10) {
            throw new Exception("Usuário já possui o número máximo de cartões (10).");
        }

        // Cria um novo arquivo para o cartão
        String nomeArquivoCartao = "cartao_" + (arquivosCartoes != null ? arquivosCartoes.length + 1 : 1) + ".txt";
        File arquivoCartao = new File(pastaCartoes, nomeArquivoCartao);

        try (FileWriter writer = new FileWriter(arquivoCartao)) {
            writer.write("Numero: " + cartao.getNumeroCartao() + "\n");
            writer.write("Codigo: " + cartao.getCodigoCartao() + "\n");
            writer.write("Validade: " + cartao.getValidadeCartao() + "\n");
        }

        System.out.println("Cartão adicionado com sucesso para o usuário: " + email);
    }

    public static int editarCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao){
        File cartoesUsuario = new File("usuarios/" + email + "/cartoes");
        if(!cartoesUsuario.exists()) return 0;
        File[] cartoes = cartoesUsuario.listFiles();
        for(File cartao : cartoes) {
            if(cartao.getName().contains(""+cartaoIndice)) {
                try {
                    List<String> arquivoLinhas = Files.readAllLines(cartao.toPath());
                    arquivoLinhas.set(0, "Numero: " + numeroCartao);
                    arquivoLinhas.set(1, "Codigo: " + codigoCartao);
                    Files.write(cartao.toPath(), arquivoLinhas);
                    break;
                } catch (Exception e) {
                    System.out.println("Não foi possível ler arquivo " + cartao.getAbsolutePath()+"." + e);
                }
            }
        }
        return 1;
    }

    public static int removerCartao(String email, int cartaoIndice) {
        File cartoesUsuario = new File("usuarios/" + email + "/cartoes");
        if(!cartoesUsuario.exists()) return 0;
        File[] cartoes = cartoesUsuario.listFiles();
        int cartaoNumero = 0;
        for(File cartao : cartoes) {
            if(cartaoNumero > 0) {
                cartao.renameTo(new File("usuarios/" + email + "/cartoes/cartao_" + cartaoIndice + ".txt"));
                cartaoNumero++;
            }
            if(cartao.getName().contains(""+cartaoIndice)) {
                cartao.delete();
                cartaoNumero = cartaoIndice;
            }
        }
        return 1;
    }

    private static void editarArquivo(String nomeArquivo, String cpf, String dadoEditado, String novoConteudo) {
        try {
            // Lê todas as linhas do arquivo
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            boolean encontrouCPF = false;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);

                // Verifica se encontrou o CPF
                if (linha.contains("CPF: " + cpf)) {
                    encontrouCPF = true;
                }

                // Se o CPF foi encontrado, busca o dado a ser editado
                if (encontrouCPF && linha.contains(dadoEditado + ":")) {
                    // Faz o split na linha encontrada para pegar a chave e o valor
                    String[] partes = linha.split(":");
                    if (partes.length == 2) {
                        // Substitui o valor antigo pelo novo
                        String novaLinha = partes[0] + ": " + novoConteudo;
                        linhas.set(i, novaLinha);
                        System.out.println("Informação editada com sucesso!");
                    }
                    break; // Sai do loop após editar
                }
            }
            // Reescreve o arquivo com o conteúdo atualizado
            Files.write(Paths.get(nomeArquivo), linhas);
        } catch (IOException e) {
            System.out.println("Erro ao editar o arquivo: " + e.getMessage());
        }
    }

    public static int editarUsuario(String email, String[] dados) {
        File pastaUsuario = new File("usuarios/" + email);
        if(!pastaUsuario.exists()) return 0;
        File arquivo = new File(pastaUsuario, "dados.txt");
        for (String dado : dados) System.out.println(dado);
        try{
            List<String> arquivoLinhas = Files.readAllLines(arquivo.toPath());
            for(int i = 0; i < arquivoLinhas.size(); i++) {
                boolean mudancaEmail = false;
                String dadoTipo = arquivoLinhas.get(i).split(": ")[0];
                switch(dadoTipo) {
                    case "Nome":
                        arquivoLinhas.set(i, "Nome: " + dados[0]);
                        break;
                    case "Email":
                        arquivoLinhas.set(i, "Email: " + dados[1].replace(" ", ""));
                        if(Sistema.usuario.getEmail().equals(dados[1].replace(" ", ""))) mudancaEmail = true;
                        break;
                    case "Senha":
                        arquivoLinhas.set(i, "Senha: " + dados[2]);
                        break;
                }
                Files.write(arquivo.toPath(), arquivoLinhas);
                if(mudancaEmail) pastaUsuario.renameTo(new File("usuarios/" + dados[1].replace(" ", "")));
                Sistema.usuario.setNome(dados[0]);
                Sistema.usuario.setEmail(dados[1].replace(" ", ""));
                Sistema.usuario.setSenha(dados[2]);
            }
        } catch(Exception e) {
            System.out.println("Não foi possível salvar alterações." + e);
        }
        return 1;
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
}
