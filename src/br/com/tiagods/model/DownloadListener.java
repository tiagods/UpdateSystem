package br.com.tiagods.model;

import java.net.URL;

public interface DownloadListener {
    void onStarted(URL url, int total);  
    
    void onProgress(URL url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total);  
  
    void onFinished(URL url, int total);  
  
    void onError(URL url, Exception erro);  
}
