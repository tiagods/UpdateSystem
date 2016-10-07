package br.com.tiagods.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import interfaces.DownloadListener;

public class DownloadManagerSMB {
	ActionInterface action;
    public void addListener(DownloadListener listener) {  
        action = new ActionInterface();
        action.setListener(listener);
    } 
    public void copiar(File origem, File destino) {  
    	InputStream in=null;
    	OutputStream out=null;
    	try{
    		in = new FileInputStream(origem);
    		long tamanhoTotal= origem.length();
    		out = new FileOutputStream(destino);
    		int buffer = 4096;// 4KB  
            byte[] bytes = new byte[buffer];  
            action.notificaInicio(origem.getAbsolutePath(), (int)tamanhoTotal);  
            int bytesRecebidos = 0;  
            int bytesRestantes = (int)tamanhoTotal;  
            int lidos = -1;  
            long tempo = System.currentTimeMillis();  
            long tempoTotal = 0;  
            while ((lidos = in.read(bytes, 0, buffer)) != -1) {  
                tempo = System.currentTimeMillis() - tempo;  
                tempoTotal += tempo;  
                out.write(bytes, 0, lidos);//gravando arquivo
                bytesRecebidos += lidos;  
                bytesRestantes -= lidos;  
                action.notificaProgresso(origem.getAbsolutePath(), bytes, lidos, tempo, action.tempoEstimado(bytesRecebidos, tempoTotal, bytesRestantes), bytesRecebidos, (int) tamanhoTotal);  
                tempo = System.currentTimeMillis();  
            }  
            action.notificaFim(origem.getAbsolutePath(), bytesRecebidos); 
    	}catch(IOException e){
    		action.notificaErro(origem.getAbsolutePath(), e);
    	}finally{
    		try {
        		if(in!=null)
        			in.close();
				if(out!=null)
					out.close();
			} catch (IOException e) {
			}
    	}
    }

    
 }
