package br.com.tiagods.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipAction {
	
	public boolean descompactar(File arquivo){
		try {
			ZipFile zipFile = new ZipFile(arquivo);
			Enumeration entries = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
				if(entry.isDirectory()) {
					System.err.println("Descompactando diretório: " + entry.getName());
					(new File(entry.getName())).mkdir();
					continue;
				}
				System.out.println("Descompactando arquivo:" + entry.getName());
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(entry.getName())));
			}
			zipFile.close();
			return true;
		} catch (IOException ioe) {
			System.err.println("Erro ao descompactar:" + ioe.getMessage());
			return false;
		}
	}
	public void copyInputStream(InputStream in, OutputStream out) throws IOException {
		try{
			byte[] buffer = new byte[4096];
			int len;
			while((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			in.close();
			out.close();
		}
	}
}
