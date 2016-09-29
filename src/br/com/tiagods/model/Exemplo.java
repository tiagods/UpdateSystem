package br.com.tiagods.model;

import java.net.URL;

public class Exemplo {  
	  
    private class Listener implements DownloadListener {  
  
        public void onStarted(URL url, int total) {  
            System.out.println(url + ": iniciando o download de " + total + " bytes");  
        }  
  
        public void onProgress(URL url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total) {  
            System.out.println(url + ": " + lidos + " bytes lidos em " + tempo + "ms - tempo estimado: " + estimado + "ms ( recebidos " + recebidos  
                    + " bytes de " + total + " bytes ");  
        }  
  
        public void onFinished(URL url, int recebidos) {  
            System.out.println(url + ": finalizado o download de " + recebidos + " bytes");  
        }  
  
        public void onError(URL url, Exception erro) {  
            System.out.println(url + ": erro no download: " + erro.getMessage());  
        }  
    }  
  
    public static void main(String[] args) {  
        try {  
            URL url = new URL("http://www.prolinkcontabil.com.br/downloads/SFList.zip");  
            Exemplo exemplo = new Exemplo();  
            exemplo.executaDownload(url);  
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
    }  
  
    private void executaDownload(URL url) {  
        DownloadListener listener = new Listener();  
        DownloadManager manager = new DownloadManager();  
        manager.addListener(listener);  
        manager.download(url);  
  
    }  
}  