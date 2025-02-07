package Persistencia;

import Modelo.Cartao;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileCartaoDAO extends FileDAO implements CartaoDAO{
    private final String BASE_PATH = "usuarios";


    //Singleton
    private static FileCartaoDAO instance;

    private FileCartaoDAO(){}

    public static FileCartaoDAO getInstance(){
        if(instance == null){
            instance = new FileCartaoDAO();
        }
        return instance;
    }

    //Métodos
    @Override
    public List<Cartao> buscaCartoes(String email) {
        File pastaCartoes = new File(BASE_PATH, email + "/cartoes");
        List<Cartao> cartoes = new ArrayList<>();

        if (!pastaCartoes.exists() || !pastaCartoes.isDirectory()) {
            return null;
        }

        File[] arquivosCartoes = pastaCartoes.listFiles((_, name) -> name.startsWith("cartao_") && name.endsWith(".txt"));
        if (arquivosCartoes == null) return null;

        for (File arquivoCartao : arquivosCartoes) {
            try {
                Cartao cartao = lerCartao(arquivoCartao);
                if (cartao != null) {
                    cartoes.add(cartao);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o cartão: " + arquivoCartao.getName());
            }
        }
        return cartoes;
    }

    @Override
    public boolean cadastrarCartao(Cartao cartao, String email) throws IOException {
        String basePath = "usuarios"; // Diretório base
        File pastaCartoes = new File(basePath + "/" + email + "/cartoes/");

        // Verifica se a pasta do cliente existe
        if (!pastaCartoes.exists() || !pastaCartoes.isDirectory()) {
            System.out.println("Usuário com o email " + email + " não encontrado ou pasta de cartões inexistente.");
            return false;
        }

        // Lista os cartões existentes
        File[] arquivosCartoes = pastaCartoes.listFiles((dir, name) -> name.matches("cartao_\\d+\\.txt"));
        if (arquivosCartoes != null && arquivosCartoes.length >= 10) {
            System.out.println("Usuário já possui o número máximo de cartões (10).");
            return false;
        }

        // Encontrar o próximo número disponível para o cartão
        int proximoNumeroCartao = 1;
        if (arquivosCartoes != null) {
            for (File arquivo : arquivosCartoes) {
                String nome = arquivo.getName();
                int numero = Integer.parseInt(nome.replaceAll("[^0-9]", ""));
                proximoNumeroCartao = Math.max(proximoNumeroCartao, numero + 1);
            }
        }

        // Criar o nome do arquivo
        String nomeArquivoCartao = "cartao_" + proximoNumeroCartao + ".txt";
        File arquivoCartao = new File(pastaCartoes, nomeArquivoCartao);

        // Escrevendo os dados do cartão
        try (FileWriter writer = new FileWriter(arquivoCartao)) {
            writer.write("Numero: " + cartao.getNumeroCartao() + "\n");
            writer.write("Codigo: " + cartao.getCodigoCartao() + "\n");
            writer.write("Validade: " + cartao.getValidadeCartao() + "\n");
        }

        System.out.println("Cartão adicionado com sucesso para o usuário: " + email);
        return true;
    }

    @Override
    public boolean editaCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao) {
        File cartoesUsuario = new File(BASE_PATH + email + "/cartoes");
        if(!cartoesUsuario.exists()) return false;
        File[] cartoes = cartoesUsuario.listFiles();
        if(cartoes != null) {
            for (File cartao : cartoes) {
                if (cartao.getName().contains("" + cartaoIndice)) {
                    try {
                        List<String> arquivoLinhas = Files.readAllLines(cartao.toPath());
                        arquivoLinhas.set(0, "Numero: " + numeroCartao);
                        arquivoLinhas.set(1, "Codigo: " + codigoCartao);
                        Files.write(cartao.toPath(), arquivoLinhas);
                        break;
                    } catch (Exception e) {
                        System.out.println("Não foi possível ler arquivo " + cartao.getAbsolutePath() + "." + e);
                    }
                }
            }
        } else return false;
        return true;
    }

    @Override
    public boolean excluiCartao(String email, int cartaoIndice) throws IOException {
        File cartoesUsuario = new File(BASE_PATH + email + "/cartoes");
        if(!cartoesUsuario.exists()) return false;
        File[] cartoes = cartoesUsuario.listFiles();
        int cartaoNumero = 0;
        if(cartoes != null) {
            for (File cartao : cartoes) {
                if (cartaoNumero > 0) {
                    cartao.renameTo(new File("usuarios/" + email + "/cartoes/cartao_" + cartaoIndice + ".txt"));
                    cartaoNumero++;
                }
                if (cartao.getName().contains("" + cartaoIndice)) {
                    cartao.delete();
                    cartaoNumero = cartaoIndice;
                }
            }
        } else return false;
        return true;
    }

    //Auxiliar criar cartão
    private Cartao lerCartao(File arquivoCartao) throws IOException {
        try (BufferedReader cartaoReader = new BufferedReader(new FileReader(arquivoCartao))) {
            String numero = null, codigo = null, validade = null;

            String linha;
            while ((linha = cartaoReader.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length < 2) continue;

                String chave = partes[0].trim();
                String valor = partes[1].trim();

                switch (chave) {
                    case "Numero" -> numero = valor;
                    case "Codigo" -> codigo = valor;
                    case "Validade" -> validade = valor;
                }
            }

            if (numero != null && codigo != null && validade != null) {
                LocalDate dataValidade = LocalDate.parse(validade, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new Cartao(Integer.parseInt(numero), Integer.parseInt(codigo), dataValidade);
            }

            return null;
        }
    }
}