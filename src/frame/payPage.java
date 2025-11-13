package frame;

import panel.PanelBulet;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import panel.PanelBulet;
import panel.PanelButtonAll;
import panel.PanelLoginGradient;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class payPage extends JFrame {

	private PanelBulet panelCash, PanelQris;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					payPage frame = new payPage();
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
	public payPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		PanelLoginGradient panelbayar = new PanelLoginGradient();
		this.setContentPane(panelbayar);
		panelbayar.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/img/LogoRestoranKecilRemoveBackGround.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(247, 11, 80, 104);
		panelbayar.add(lblNewLabel);
		
		JLabel TextMetodePembayaram = new JLabel("Pilih Metode Pembayaran Anda");
		TextMetodePembayaram.setFont(new Font("Serif", Font.BOLD, 25));
		TextMetodePembayaram.setHorizontalAlignment(SwingConstants.CENTER);
		TextMetodePembayaram.setBounds(84, 115, 432, 71);
		panelbayar.add(TextMetodePembayaram);
		
		PanelQris = new PanelBulet(20, Color.WHITE);
		PanelQris.setBounds(40, 221, 249, 172);
		panelbayar.add(PanelQris);
		PanelQris.setLayout(null);
		
		JButton ButtonQris = new JButton("Qris");
		ButtonQris.setBounds(0, 0, 249, 172);
		ButtonQris.setOpaque(false);
		ButtonQris.setContentAreaFilled(false);
		ButtonQris.setBorderPainted(false);
		PanelQris.add(ButtonQris);
		
		panelCash = new PanelBulet(20, Color.WHITE);
		panelCash.setBounds(323, 221, 249, 172);
		panelbayar.add(panelCash);
		panelCash.setLayout(null);
		
		JButton ButtonCash = new JButton("Cash/Tunai");
		ButtonCash.setBounds(0, 0, 249, 172);
		ButtonCash.setOpaque(false);
		ButtonCash.setBorderPainted(false);
		ButtonCash.setContentAreaFilled(false);
		panelCash.add(ButtonCash);
		
		
		
		
	}
}
