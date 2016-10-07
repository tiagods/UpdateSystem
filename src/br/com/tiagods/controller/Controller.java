package br.com.tiagods.controller;

import java.io.File;

import javax.swing.JOptionPane;

import br.com.tiagods.model.ActionRuntime;
import br.com.tiagods.model.Config;
import br.com.tiagods.model.DownloadManagerHttp;
import br.com.tiagods.model.DownloadManagerFTP;
import br.com.tiagods.model.Model;
import br.com.tiagods.model.DownloadManagerSMB;
import br.com.tiagods.model.ZipAction;

import java.net.MalformedURLException;
import java.net.URL;

import static br.com.tiagods.view.MenuView.*;
import interfaces.DownloadListener;

public class Controller {
	
	boolean sucess=false;
	
	public void start(){
		Model model = new Model();
		Config config = new Config();
		
		//ler arquivo
		config.readFile(model);
		//verificando se arquivo existe no diretorio pai e deletando
		File file = new File(model.getFileName());
		if(file.exists())
			file.delete();
		File exe = new File(System.getProperty("user.dir")+"/"+model.getExecutavelName());
		renameExe(exe, "Backup");
		//executar comando ao abrir
		if(executeScript(model.getOnOpen())){
			boolean unzip=false;
			for(String s : model.getUpdateType()){
				try{
					int value = Integer.parseInt(s);
					switch(value){
					case 1:
						if(executeHTTP(model))
							unzip = unzip(file);
						break;
					case 2:
						if(executeSMB(model))
							unzip=unzip(file);
						break;
					case 3:
						if(executeFTP(model))
							unzip=unzip(file);
						break;
					}
					if(sucess)break;
				}catch(NumberFormatException e){
					continue;
				}
			}//tentativa de descompactar
			if(unzip==false) {
				if(!unzip(file)){
					lbStatus.setText("Não foi possivel descompactar o conteudo baixo,");
					label.setText("Abra o sistema e tente executar a atualização novamente.");
					if(file.exists())
						file.delete();
					renameExe(exe,"Restore");
				}
			}
			else{//remover backup
				File fileBkp = new File(System.getProperty("user.dir")+"/bkp"+model.getExecutavelName());
				if(fileBkp.exists())
					fileBkp.delete();
			}
			//executar comando ao fechar
			executeScript(model.getOnClose());
		}
		System.exit(0);
	}
	//
	void renameExe(File file, String acao){
		switch(acao){
			case "Backup":
				String newName = "bkp"+file.getName();
				lbStatus.setText("Criando copia de segurança!");
				label.setText("Iniciando atualização");
				file.renameTo(new File(file.getPath()+"/"+newName));
				lbStatus.setText("Concluido cópia.");
				label.setText("Backup concluido com sucesso!");
				break;
			case "Restore":
				lbStatus.setText("Atualização impossível!");
				label.setText("Desfazendo alteração");
				String oldName = "bkp"+file.getName();
				File file2 = new File(file.getPath()+"/"+oldName);
				file2.renameTo(file);
				lbStatus.setText("Concluido...");
				label.setText("Processo de reversão concluido!");
				break;
		}
	}
	
	//executar um script
	boolean executeScript(String command){
		if(command.trim().length()>0){
			ActionRuntime bat = new ActionRuntime();
			if(!bat.executeCommand(command)){
				lbStatus.setText("Comando Encerrar interrompido ou arquivo não existe!");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	boolean executeSMB(Model model){
		File file = new File(model.getSmbDirectory());
		if(!file.exists()){
			lbStatus.setText("Diretorio não disponivel via Rede");
			return false;
		}
		DownloadManagerSMB smb = new DownloadManagerSMB();
		smb.copiar(new File(model.getSmbDirectory()+"/"+model.getFileName()),
				new File(System.getProperty("user.dir")+"/"+model.getFileName()));
		return sucess;
	}
	boolean executeHTTP(Model model){
		if(model.getHttpUrl().trim().equals("")){
			//info corrija o arquivo config
			return false;
		}
		DownloadListener listener = new Listener();  
        DownloadManagerHttp manager = new DownloadManagerHttp();  
        manager.addListener(listener);  
        URL url = null;
		try {
			url = new URL(model.getHttpUrl()+"/"+model.getFileName());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(url!=null)
			manager.download(url, new File(System.getProperty("user.dir")+"/"+model.getFileName())); 
		return sucess;
	}
	boolean executeFTP(Model model){
		DownloadManagerFTP ftp = new DownloadManagerFTP();
		if(model.getFtpHost().trim().equals("")){
			lbStatus.setText("O endereço ftp está incorreto");
			return false;
		}
		else if(model.getFtpPassword().trim().equals("")){
			lbStatus.setText("O endereço ftp está incorreto");
			return false;
		}
		else{
			try{
				Integer.parseInt(model.getFtpPort());
			}catch(NumberFormatException e){
				lbStatus.setText("A porta ftp está incorreta");
				return false;
			}
		}
		ftp.downloadFile(model.getFtpHost(), Integer.parseInt(model.getFtpPort()), 
				model.getFtpUser(), model.getFtpPassword(), model.getFileName(), 
				model.getFtpDirectory()+"/"+model.getFileName());
		return sucess;
	}
	boolean unzip(File arquivo){
		//descompactar
		ZipAction zip = new ZipAction();
		return zip.descompactar(arquivo);
	}
	private class Listener implements DownloadListener {  
	    public void onStarted(String url, int total) {  
        	pb.setValue(0);
        	lbStatus.setText("Contatando link do servidor => ("+url+")");
        	label.setText("Iniciando o download de " + total/1024 + " kb");
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }  
        public void onProgress(String url, byte[] bytes, int lidos, long tempo, long estimado, int recebidos, int total) {  
        	long minutos = 00;
        	long segundos = 00;
        	if(estimado/1000>0){
        		segundos=estimado/1000;
        		if(segundos>60){
        			minutos=segundos/60;
        			segundos=segundos % 60;
        		}
        	}
        	lbStatus.setText("Comunicação OK => ("+url+")");
        	label.setText("Tempo estimado: " + minutos+":"+segundos + " ( recebidos " + recebidos/1024
                    + " kb de " + total/1024 + " kb");
	        pb.setValue(((recebidos)/(total/100)));//this percent
        	//System.out.println(url + ": " + lidos + " bytes lidos em " + tempo + "ms - tempo estimado: " + estimado + "ms ( recebidos " + recebidos  
	        //            + " bytes de " + total + " bytes ");  
        
        }  
        public void onFinished(String url, int recebidos) {  
        	lbStatus.setText("Download de "+recebidos/1024+" kb concluido com sucesso!");
        	label.setText("Reiniciando sistema!");
        	
        	pb.setValue(100);
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	sucess=true;
        }  
        public void onError(String url, Exception erro) {  
            label.setText(url + ": erro no download: " + erro.getMessage());  
            sucess=false;
        }  
    }  
}
