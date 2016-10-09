package test;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class MenuView extends JFrame{  
	private class Listener implements DownloadListener {  
	    public void onStarted(String url, int total) {  
        	pb.setValue(0);
        	lbStatus.setText("Contatando link do servidor => ("+url+")");
        	label.setText("Iniciando o download de " + total/1024 + " kb");
        	try {
				Thread.sleep(3000);
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
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }  
        public void onError(String url, Exception erro) {  
            label.setText(url + ": erro no download: " + erro.getMessage());  
            JOptionPane.showMessageDialog(null, url + ": erro no download: " + erro.getMessage());
        }  
    }  
	public static JProgressBar pb;
    public static JLabel label, lbStatus;
    
    public static void main(String[] args){
    	new MenuView();
    }
    
    public MenuView() {  
    	initComponents();
    	DownloadListener listener = new Listener();  
        DownloadManagerFTP manager = new DownloadManagerFTP();
        manager.addListener(listener);
        String server = "ftp.prolinkcontabil.com.br";
    	int port = 21;
    	String user = "prolinkcontabil";
    	String password = "plk*link815";
        String fileName="SFList.zip";
        manager.downloadFile(server, port, user, password, fileName, "public_html/downloads/SFList.zip");
        //DownloadManager manager = new DownloadManager();  
        //manager.addListener(listener);  
        //manager.download(new File("//plkserver/Sistemas/Roberta.rar"), new File(System.getProperty("user.dir")+"/"+"Roberta.rar")); 
    }
    public void initComponents(){
    	JPanel borderPanel = new JPanel();
        borderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        setBackground(Color.WHITE);
        setContentPane(borderPanel);
        //pb.setIndeterminate(true);
        JPanel panel = new JPanel();
        label = new JLabel();
        
        JLabel lblDonwloadDeAtualizao = new JLabel("Donwload de Atualiza\u00E7\u00E3o em Andamento...");
        JLabel titulo = new JLabel("Atualiza\u00E7\u00E3o de Software");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        pb = new JProgressBar(0, 100);
        pb.setForeground(Color.GREEN);
        pb.setMinimumSize(new Dimension(100, 0));
        pb.setStringPainted(true);
        
        JLabel lblDeveloperBy = new JLabel("Copyrigth : Tiago Dias - Open Source Project in www.github.com/tiagods/UpdateSystem");
        GroupLayout gl_borderPanel = new GroupLayout(borderPanel);
        gl_borderPanel.setHorizontalGroup(
        	gl_borderPanel.createParallelGroup(Alignment.LEADING)
        		.addComponent(panel, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
        );
        gl_borderPanel.setVerticalGroup(
        	gl_borderPanel.createParallelGroup(Alignment.LEADING)
        		.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        
        lbStatus = new JLabel("");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lbStatus, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, gl_panel.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(titulo, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(lblDeveloperBy, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(pb, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(label, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(lblDonwloadDeAtualizao, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addComponent(titulo)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lblDonwloadDeAtualizao)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lbStatus, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(label, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(lblDeveloperBy, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        );
        panel.setLayout(gl_panel);
        borderPanel.setLayout(gl_borderPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }  
    
}  
