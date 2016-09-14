package br.com.tiagods.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SMBAction {
	public static void main(String[] args){
		copiar(new File("C:/workspace/backup base de dados.rar"),new File(System.getProperty("user.dir")));
	}
	public static void copiar(File arquivoOrigem, File localDestino){
		File fileFinal = new File(localDestino.getAbsolutePath()+"/"+arquivoOrigem.getName());
   	 	Path pathI = Paths.get(arquivoOrigem.getAbsolutePath());
        Path pathO = Paths.get(fileFinal.getAbsolutePath());
        try {
			Files.copy(pathI, pathO, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Sucess");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
