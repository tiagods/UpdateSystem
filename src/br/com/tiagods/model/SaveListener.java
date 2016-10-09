package br.com.tiagods.model;

public interface SaveListener {
	void tratarZipName(String name);
	void tratarFTP(String host, int port, String user, String password, String remoteDir);
	void tratarSMB(String url);
	void tratarHTTP(String url);
}
