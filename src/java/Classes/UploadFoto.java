package Classes;

import java.nio.file.*;

public class UploadFoto {
    public static String salvarFoto(String diretorio, String nomeArquivo, byte[] conteudoFoto) throws Exception {
        Path path = Paths.get(diretorio, nomeArquivo);
        Files.write(path, conteudoFoto);
        return path.toString();
    }
}
