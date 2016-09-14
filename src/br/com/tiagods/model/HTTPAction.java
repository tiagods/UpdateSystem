package br.com.tiagods.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPAction {
	public static void main(String[] args){
		baixarArquivoDeUrl("file:///C:/Users/Tiago/Downloads/vigilancia.zip", new File(System.getProperty("user.dir")),"mypdf.zip");
	}
	public static boolean baixarArquivoDeUrl(String urlFile, File destino, String fileName){
		try {
			URL url = new URL(urlFile);
			//url.getPath() retona a estrutura completa de diretorios
			String nomeArquivoLocal = fileName;//url.getPath();
			InputStream stream = url.openStream();
			FileOutputStream fStream = new FileOutputStream(destino+"/"+nomeArquivoLocal);
			int iByte=0;
			while((iByte=stream.read())!=-1){
				fStream.write(iByte);
			}
			stream.close();
			fStream.close();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch(IOException e){
			return false;
		}
	}
}
