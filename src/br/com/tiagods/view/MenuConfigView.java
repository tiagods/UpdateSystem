package br.com.tiagods.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.tiagods.controller.ControllerEditor;

public class MenuConfigView extends JFrame {

	private JPanel contentPane;
	public static  JTextField txZip, txExec;
	public static JCheckBox ckbFTP, ckbHttp, ckbSMB;
	public static JButton btnAvancar, btnEditFTP, btnEditSmb, btnURL, btnCaminho;
	
	ControllerEditor controller = new ControllerEditor();;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuConfigView frame = new MenuConfigView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuConfigView() {
		initComponents();
	}
	public void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 311);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblAssistenteDeConfigurao = new JLabel("Assistente de Configura\u00E7\u00E3o");
		lblAssistenteDeConfigurao.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAssistenteDeConfigurao.setHorizontalAlignment(SwingConstants.CENTER);
		lblAssistenteDeConfigurao.setBounds(10, 0, 414, 29);
		panel.add(lblAssistenteDeConfigurao);
		
		ckbFTP = new JCheckBox("FTP");
		ckbFTP.setFont(new Font("Tahoma", Font.BOLD, 12));
		ckbFTP.setBounds(10, 181, 160, 23);
		panel.add(ckbFTP);
		
		ckbHttp = new JCheckBox("HTTP");
		ckbHttp.setFont(new Font("Tahoma", Font.BOLD, 12));
		ckbHttp.setBounds(10, 207, 160, 23);
		panel.add(ckbHttp);
		
		ckbSMB = new JCheckBox("SMB (Via Rede)");
		ckbSMB.setFont(new Font("Tahoma", Font.BOLD, 12));
		ckbSMB.setBounds(10, 233, 160, 23);
		panel.add(ckbSMB);
		
		btnAvancar = new JButton("Confirmar");
		btnAvancar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAvancar.setBounds(324, 267, 100, 33);
		panel.add(btnAvancar);
		
		btnEditFTP = new JButton("Editar FTP");
		btnEditFTP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditFTP.setBounds(274, 181, 150, 23);
		panel.add(btnEditFTP);
		
		btnURL = new JButton("Editar URL");
		btnURL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnURL.setBounds(274, 207, 150, 23);
		panel.add(btnURL);
		
		btnCaminho = new JButton("Editar Caminho");
		btnCaminho.addActionListener(controller);
		btnCaminho.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCaminho.setBounds(274, 233, 150, 23);
		panel.add(btnCaminho);
		
		JLabel lblmarqueUmaOu = new JLabel("***Marque uma ou mais op\u00E7\u00F5es para habilitar a atualiza\u00E7\u00E3o***");
		lblmarqueUmaOu.setHorizontalAlignment(SwingConstants.CENTER);
		lblmarqueUmaOu.setBounds(10, 40, 414, 14);
		panel.add(lblmarqueUmaOu);
		
		JLabel lblNewLabel = new JLabel("***em seguida edite as configura\u00E7\u00F5es e clique em Salvar***");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 62, 414, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNomeDoArquivozip = new JLabel("Nome do Arquivo (*.zip)");
		lblNomeDoArquivozip.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNomeDoArquivozip.setBounds(10, 157, 160, 14);
		panel.add(lblNomeDoArquivozip);
		
		txZip = new JTextField();
		txZip.setBounds(275, 154, 149, 20);
		panel.add(txZip);
		txZip.setColumns(10);
		
		txExec = new JTextField();
		txExec.setColumns(10);
		txExec.setBounds(275, 126, 149, 20);
		panel.add(txExec);
		
		JLabel lblNomeDoExecutavel = new JLabel("Nome do Executavel (ex: Sis.exe)");
		lblNomeDoExecutavel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNomeDoExecutavel.setBounds(10, 129, 220, 14);
		panel.add(lblNomeDoExecutavel);
		setLocationRelativeTo(null);
	}
}
