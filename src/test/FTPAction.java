package test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
 
public class FTPAction {
 
    public static void main(String[] args) {
    	String server = "ftp.prolinkcontabil.com.br";
    	int port = 21;
    	String user = "prolinkcontabil";
    	String pass = "plk*link815";
        
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
//            String fileName="SFList.zip";
//            String remoteFile ="public_html/downloads/SFList.zip";
//            File downloadFile = new File(System.getProperty("user.dir")+"/"+fileName);
//	        OutputStream out = new BufferedOutputStream(new FileOutputStream(downloadFile));
//	        InputStream inp = ftpClient.retrieveFileStream(remoteFile);
//	        
//            int buffer = 4096;// 4KB  
//            byte[] bytes = new byte[buffer];
//            
//            FTPFile f = ftpClient.mlistFile(remoteFile);
//            long tamanhoTotal = f.getSize(); 
//            
//            System.out.println("Tamanho: "+tamanhoTotal);
//            
//            int bytesRecebidos = 0;  
//            int bytesRestantes = (int)tamanhoTotal;  
//            int lidos = -1;  
//            long tempo = System.currentTimeMillis();  
//            long tempoTotal = 0;  
//            while ((lidos = inp.read(bytes, 0, buffer)) != -1) {  
//                tempo = System.currentTimeMillis() - tempo;  
//                tempoTotal += tempo;  
//                out.write(bytes, 0, lidos);//gravando arquivo
//                bytesRecebidos += lidos;  
//                bytesRestantes -= lidos;
//                tempo = System.currentTimeMillis();  
//            }  
//            boolean success = ftpClient.completePendingCommand();
//            if(success)
//            	System.out.print("Pronto");

            
            
            
//            // APPROACH #1: using retrieveFile(String, OutputStream)
//            String remoteFile1 = "public_html/downloads/SFList.zip";
//            FTPFile f = ftpClient.mlistFile(remoteFile1);
//            System.out.println("Size: "+f.getSize());
//            File downloadFile1 = new File(System.getProperty("user.dir")+"/"+"SFList.zip");
//            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
//            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
//            outputStream1.close();
//            if (success) {
//                System.out.println("File #1 has been downloaded successfully.");
//            }
 
            // APPROACH #2: using InputStream retrieveFileStream(String)
            String remoteFile2 = "public_html/downloads/SFList.zip";
            File downloadFile2 = new File(System.getProperty("user.dir")+"/SFList.zip");
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }
            boolean success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File #2 has been downloaded successfully.");
            }
            outputStream2.close();
            inputStream.close();
//            out.close();
//            inp.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    }
