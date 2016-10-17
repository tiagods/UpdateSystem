package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuProgressView.label;
import static br.com.tiagods.view.MenuProgressView.lbStatus;
import static br.com.tiagods.view.MenuProgressView.pb;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import br.com.tiagods.model.ActionRuntime;
import br.com.tiagods.model.Config;
import br.com.tiagods.model.DownloadListener;
import br.com.tiagods.model.DownloadManagerFTP;
import br.com.tiagods.model.DownloadManagerHttp;
import br.com.tiagods.model.DownloadManagerSMB;
import br.com.tiagods.model.Model;
import br.com.tiagods.model.ZipAction;
import br.com.tiagods.view.MenuProgressView;

import static br.com.tiagods.view.MenuProgressView.*;

public class Controller implements WindowListener{
	
	boolean sucess=false;
	boolean stop=false;
	
	public void start( ){
		Model model = new Model();
		Config config = new Config();
		//ler arquivo
		boolean configExits = config.readFile(model);
		if(configExits){
			//verificando se arquivo existe no diretorio pai e deletando
			File fileZip = new File(System.getProperty("user.dir")+"/"+model.getFileName());
			if(fileZip.exists())
				fileZip.delete();
			//executar comando ao abrir
			if(!model.getOnOpen().trim().equals(""))
				executeScript(model.getOnOpen());
			File exe = new File(System.getProperty("user.dir")+"/"+model.getExecName());
			for(String s : model.getUpdateType()){
				try{
					int value = Integer.parseInt(s);
					switch(value){
					case 1:
						if(executeHTTP(model)){
							sucess=true;
						}
						break;
					case 2:
						if(executeSMB(model)){
							sucess=true;
						}
						break;
					case 3:
						if(executeFTP(model)){
							sucess=true;
						}
						break;
					}
					if(sucess)break;
				}catch(NumberFormatException e){
					continue;
				}
			}
			//tentativa de descompactar
			if(stop){
				if(fileZip.exists())
					fileZip.delete();
			}
			else{
				renameExe(exe, "Backup");
				if(fileZip.exists()){
					if(unzip(fileZip))
						fileZip.delete();
					File fileBkp = new File(System.getProperty("user.dir")+"/bkp"+model.getExecName());
					if(fileBkp.exists())
						fileBkp.delete();
					System.exit(0);
				}
				else{
					//remover backup
					File fileBkp = new File(System.getProperty("user.dir")+"/bkp"+model.getExecName());
					if(fileBkp.exists())
						fileBkp.delete();
				}
			}
			//executar comando ao fechar
			executeScript("\""+System.getProperty("user.dir")+"\\"+model.getOnClose()+"\"");
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
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lbStatus.setText("Não foi possivel descompactar o conteudo baixo,");
				label.setText("Abra o sistema e tente executar a atualização novamente.");
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
		sucess = smb.copiar(new File(model.getSmbDirectory()+"/"+model.getFileName()),
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
			sucess = manager.download(url, new File(System.getProperty("user.dir")+"/"+model.getFileName())); 
		return sucess;
	}
	boolean executeFTP(Model model){
		DownloadManagerFTP ftp = new DownloadManagerFTP();
		if(model.getFtpHost().trim().equals("")){
			lbStatus.setText("O endereço ftp está incorreto");
			sucess = false;
			return false;
		}
		else{
			try{
				Integer.parseInt(model.getFtpPort());
			}catch(NumberFormatException e){
				lbStatus.setText("A porta ftp está incorreta");
				sucess = false;
				return false;
			}
		}
		sucess = ftp.downloadFile(model.getFtpHost(), Integer.parseInt(model.getFtpPort()), 
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
        }  
        public void onError(String url, Exception erro) {  
            label.setText(url + ": erro no download: " + erro.getMessage());  
        }  
    }
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		int valor = JOptionPane.showConfirmDialog(null, "Deseja cancelar o processo de atualização?", "Cancelar", JOptionPane.YES_NO_OPTION);
		if(valor == JOptionPane.YES_OPTION){
			if(sucess)
				stop=true;
			System.exit(0);
		}
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}  
}
