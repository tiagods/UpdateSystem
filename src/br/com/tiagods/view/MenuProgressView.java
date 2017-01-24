package br.com.tiagods.view;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import br.com.tiagods.controller.Controller;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;

public class MenuProgressView extends JFrame{  
	Controller controller = new Controller();
	private static String senhaFTP;
    public static JProgressBar pb;
    public static JLabel label, lbStatus;
    public static MenuProgressView view;
    
    public static void main(String[] args){
    	view = new MenuProgressView();
    	senhaFTP = args[0];
    }
    
    public MenuProgressView() {
    	setResizable(false);
    	addWindowListener(controller); 
    	initComponents();
    	controller.start(senhaFTP);
    }
    public void initComponents(){
    	JPanel borderPanel = new JPanel();
    	borderPanel.setBackground(Color.WHITE);
        borderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        setBackground(Color.WHITE);
        setContentPane(borderPanel);
        JPanel panel = new JPanel();
        panel.setForeground(Color.BLUE);
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        panel.setBackground(Color.WHITE);
        label = new JLabel();
        label.setFont(new Font("Tahoma", Font.PLAIN, 10));
        label.setBackground(Color.WHITE);
        
        JLabel lblDonwloadDeAtualizao = new JLabel("Processo de Atualiza\u00E7\u00E3o em Andamento...");
        lblDonwloadDeAtualizao.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("Atualiza\u00E7\u00E3o de Software");
        titulo.setBorder(UIManager.getBorder("TitledBorder.border"));
        titulo.setFont(new Font("Tahoma", Font.BOLD, 13));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        pb = new JProgressBar(0, 100);
        pb.setForeground(Color.GREEN);
        pb.setMinimumSize(new Dimension(100, 0));
        pb.setStringPainted(true);
        
        JLabel lblDeveloperBy = new JLabel("Copyrigth : Tiago Dias - Open Source Project in www.github.com/tiagods/UpdateSystem");
        lblDeveloperBy.setBackground(Color.WHITE);
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
        lbStatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lbStatus.setBackground(Color.WHITE);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lbStatus, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(titulo, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(lblDeveloperBy, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        					.addComponent(lblDonwloadDeAtualizao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addComponent(label, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        				.addComponent(pb, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
        			.addGap(10))
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
        setLocationRelativeTo(null);
        setVisible(true);
    }  
}  
