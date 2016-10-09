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

 
public class DownloadManagerFTP {
	
    ActionInterface action;
    String host = "ftp.prolinkcontabil.com.br";
	int port = 21;
	String user = "prolinkcontabil";
	String password = "plk*link815";
	String remoteFile = "public_html/downloads/SFList.zip";
    String fileName = "SFList.zip";
	
//	public static void main(String[] args){
//		new DownloadManagerFTP().downloadFile();
//	}
	
    public void addListener(DownloadListener listener) {  
        action = new ActionInterface();
        action.setListener(listener);
    }  
    
  public void downloadFile(String host,int port,String user,String password, String fileName, String remoteFile){    
    //public void downloadFile(){    
    	FTPClient ftpClient = new FTPClient();
        OutputStream out = null;
        InputStream inp = null;
        try {
 
            ftpClient.connect(host, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            File downloadFile = new File(System.getProperty("user.dir")+"/SFList.zip");
            FTPFile ftpFile = ftpClient.mlistFile(remoteFile);
            long tamanhoTotal = ftpFile.getSize();
            
            action.notificaInicio(remoteFile, (int) tamanhoTotal);  

            out = new BufferedOutputStream(new FileOutputStream(downloadFile));
            inp = ftpClient.retrieveFileStream(remoteFile);
            System.out.println("Pegando tamanho do arquivo");
            System.out.println(""+tamanhoTotal);
            byte[] bytes = new byte[4096];
            int bytesRecebidos = 0;  
            int bytesRestantes = (int)tamanhoTotal;  
            int lidos = -1;  
            long tempo = System.currentTimeMillis();  
            long tempoTotal = 0;  
            while ((lidos = inp.read(bytes)) != -1) {
            	tempo = System.currentTimeMillis() - tempo;  
            	tempoTotal += tempo;  
            	out.write(bytes, 0, lidos);//gravando arquivo
            	bytesRecebidos += lidos;  
            	bytesRestantes -= lidos;
            	action.notificaProgresso(host+"/"+remoteFile, bytes, lidos, tempo, action.tempoEstimado(bytesRecebidos, tempoTotal, bytesRestantes), bytesRecebidos, (int)tamanhoTotal);  
            	tempo = System.currentTimeMillis();  
            }
            boolean success = ftpClient.completePendingCommand();
            if (success)
            	action.notificaFim(host+"/"+remoteFile, bytesRecebidos);  
        }catch (IOException ex) {
        	ex.printStackTrace();
        	action.notificaErro(host+"/"+remoteFile.toString(), ex);
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
                if(out!=null)
                	 out.close();
                if(inp!=null)
                	inp.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    }
