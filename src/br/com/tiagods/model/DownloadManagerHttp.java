package br.com.tiagods.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import interfaces.DownloadListener;

public final class DownloadManagerHttp {  
		ActionInterface action;
	
        public void addListener(DownloadListener listener) {  
            action = new ActionInterface();
            action.setListener(listener);
        }  
        public void download(URL url, File destino) {  
        	OutputStream out=null;
        	try {  
                URLConnection urlConn = url.openConnection();  
                int tamanhoTotal = urlConn.getContentLength();  
                InputStream entrada = urlConn.getInputStream();  
                out = new FileOutputStream(destino);
                int buffer = 4096;// 4KB  
                byte[] bytes = new byte[buffer];  
                action.notificaInicio(url.toString(), tamanhoTotal);  
                int bytesRecebidos = 0;  
                int bytesRestantes = tamanhoTotal;  
                int lidos = -1;  
                long tempo = System.currentTimeMillis();  
                long tempoTotal = 0;  
                while ((lidos = entrada.read(bytes, 0, buffer)) != -1) {  
                    tempo = System.currentTimeMillis() - tempo;  
                    tempoTotal += tempo;  
                    out.write(bytes, 0, lidos);//gravando arquivo
                    bytesRecebidos += lidos;  
                    bytesRestantes -= lidos;
                    action.notificaProgresso(url.toString(), bytes, lidos, tempo, action.tempoEstimado(bytesRecebidos, tempoTotal, bytesRestantes), bytesRecebidos, tamanhoTotal);  
                    tempo = System.currentTimeMillis();  
                }  
                action.notificaFim(url.toString(), bytesRecebidos);  
            } catch (IOException erro) {  
            	action.notificaErro(url.toString(), erro);  
            } finally{
            	try{
            	if(out!=null)
            		out.close();
            	}catch(IOException e){}
            }
        }  
    }  


