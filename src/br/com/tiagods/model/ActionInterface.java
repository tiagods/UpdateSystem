package br.com.tiagods.model;

import java.util.LinkedList;
import java.util.List;

import interfaces.DownloadListener;

public class ActionInterface {
	
	private final List<DownloadListener> listeners = new LinkedList<DownloadListener>();  

	public void setListener(DownloadListener listener) {  
		listeners.add(listener);  
	}  
	public long tempoEstimado(int recebidos, long tempo, int restantes) {  
        // 1 pra evitar divisao por 0  
        if (recebidos < 1) {  
            recebidos = 1;  
        }  
        if (tempo < 1) {  
            tempo = 1;  
        }  
        if (restantes < 1) {  
            restantes = 1;  
        }  
        long estimado = tempo * restantes / recebidos;  
        return estimado;  
    }  
  
    public void removeListener(DownloadListener listener) {  
        listeners.remove(listener);  
    }  
  
    public void notificaErro(String url, Exception erro) {  
        for (DownloadListener listener : listeners) {  
            listener.onError(url, erro);  
        }  
    }  
  
    public void notificaFim(String url, int total) {  
        for (DownloadListener listener : listeners) {  
            listener.onFinished(url, total);  
        }  
    }  
  
    public void notificaInicio(String url, int total) {  
        for (DownloadListener listener : listeners) {  
            listener.onStarted(url, total);  
        }  
    }  
  
    public void notificaProgresso(String url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total) {  
        for (DownloadListener listener : listeners) {  
            listener.onProgress(url, bytes, lidos, tempo, estimado, recebidos, total);  
        }  
    }
}
