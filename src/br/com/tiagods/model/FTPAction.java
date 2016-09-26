package br.com.tiagods.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
 
public class FTPAction {
	static String server = "ftp.prolinkcontabil.com.br";
	static int port = 21;
	static String user = "prolinkcontabil";
	static String password = "plk*link815";
    static String fileName="SFList.zip";
	public static void main(String[] args) {
    	ftpGetFile(server,port,user,password,fileName, "/public_html/downloads/"+fileName);
    }
    public static boolean ftpGetFile(String server,int port,String user,String password, String fileName, String remoteFile){    
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
            File downloadFile1 = new File("C:/home/"+fileName);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream1);
            outputStream1.close();
 
            if (success) {
                return true;
            }
 
            // APPROACH #2: using InputStream retrieveFileStream(String)
//            String remoteFile2 = "/1.html";
//            File downloadFile2 = new File(System.getProperty("user.dir")+"/1.html");
//            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
//            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
//            byte[] bytesArray = new byte[4096];
//            int bytesRead = -1;
//            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//                outputStream2.write(bytesArray, 0, bytesRead);
//            }
// 
//            boolean success = ftpClient.completePendingCommand();
//            if (success) {
//                System.out.println("File #2 has been downloaded successfully.");
//            }
//            outputStream2.close();
//            inputStream.close();
 
        }catch (IOException ex) {
        	ex.printStackTrace();
        	return false;
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    }
