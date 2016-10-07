package test;

import java.net.URL;

public interface DownloadListener {
    void onStarted(String url, int total);  
    
    void onProgress(String url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total);  
  
    void onFinished(String url, int total);  
  
    void onError(String url, Exception erro);  
}
