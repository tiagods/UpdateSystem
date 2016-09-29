package br.com.tiagods.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public final class DownloadManager {  
      
        private final List<DownloadListener> listeners = new LinkedList<DownloadListener>();  
      
        public void addListener(DownloadListener listener) {  
            listeners.add(listener);  
        }  
      
        public void download(URL url) {  
            try {  
                URLConnection urlConn = url.openConnection();  
                int tamanhoTotal = urlConn.getContentLength();  
                InputStream entrada = urlConn.getInputStream();  
                int buffer = 4096;// 4KB  
                byte[] bytes = new byte[buffer];  
                notificaInicio(url, tamanhoTotal);  
                int bytesRecebidos = 0;  
                int bytesRestantes = tamanhoTotal;  
                int lidos = -1;  
                long tempo = System.currentTimeMillis();  
                long tempoTotal = 0;  
                while ((lidos = entrada.read(bytes, 0, buffer)) != -1) {  
                    tempo = System.currentTimeMillis() - tempo;  
                    tempoTotal += tempo;  
                    bytesRecebidos += lidos;  
                    bytesRestantes -= lidos;  
                    notificaProgresso(url, bytes, lidos, tempo, tempoEstimado(bytesRecebidos, tempoTotal, bytesRestantes), bytesRecebidos, tamanhoTotal);  
                    tempo = System.currentTimeMillis();  
                }  
                notificaFim(url, bytesRecebidos);  
            } catch (IOException erro) {  
                notificaErro(url, erro);  
            }  
        }  
      
        private long tempoEstimado(int recebidos, long tempo, int restantes) {  
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
      
        private void notificaErro(URL url, Exception erro) {  
            for (DownloadListener listener : listeners) {  
                listener.onError(url, erro);  
            }  
        }  
      
        private void notificaFim(URL url, int total) {  
            for (DownloadListener listener : listeners) {  
                listener.onFinished(url, total);  
            }  
        }  
      
        private void notificaInicio(URL url, int total) {  
            for (DownloadListener listener : listeners) {  
                listener.onStarted(url, total);  
            }  
        }  
      
        private void notificaProgresso(URL url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total) {  
            for (DownloadListener listener : listeners) {  
                listener.onProgress(url, bytes, lidos, tempo, estimado, recebidos, total);  
            }  
        }  
    }  


