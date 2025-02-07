package Persistencia;

import java.io.File;
import java.io.IOException;

abstract class FileDAO {
    protected static boolean deletarPastaRecursivamente(File pasta) {
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

    protected void verificarOuCriarPasta(File pasta, String mensagemErro) throws IOException {
        if (!pasta.exists() && !pasta.mkdirs()) {
            throw new IOException(mensagemErro);
        }
    }
}
