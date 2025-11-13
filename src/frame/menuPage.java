package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import panel.PanelLoginGradient;
import panel.PanelButtonAll;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

public class menuPage extends JFrame {

	private PanelLoginGradient contentPane, LayerMakanan;
	private JTextField textField;
	private PanelButtonAll PanelButtonBayar; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
                        @Override
			public void run() {
				try {
					menuPage frame = new menuPage();
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
	public menuPage() {
            
                setTitle("Menu Page");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(640, 480);
                setLayout(null);

                // tambahkan komponen di sini
                JLabel lbl = new JLabel("Halaman Menu");
                lbl.setBounds(250, 200, 200, 30);
                add(lbl);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		contentPane = new PanelLoginGradient();
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 170, 441);
		panel.setBackground(Color.white);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel IconMenu = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/img/LogoRestoranKecilRemoveBackGround.png")).getImage();
		IconMenu.setIcon(new ImageIcon(img));
		IconMenu.setHorizontalAlignment(SwingConstants.CENTER);
		IconMenu.setBounds(29, 11, 109, 100);
		panel.add(IconMenu);
		
		JPanel Makanan = new JPanel();
		Makanan.setBackground(new Color(248, 237, 235));
		Makanan.setBounds(0, 121, 170, 36);
		panel.add(Makanan);
		
		JLabel LabelMakanan = new JLabel("Makanan");
		LabelMakanan.setHorizontalAlignment(SwingConstants.CENTER);
		Makanan.add(LabelMakanan);
		
		JPanel Minuman = new JPanel();
		Minuman.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Minuman.setBounds(0, 168, 170, 36);
		panel.add(Minuman);
		
		JLabel LabelMinuman = new JLabel("Minuman");
		LabelMinuman.setHorizontalAlignment(SwingConstants.CENTER);
		Minuman.add(LabelMinuman);
		
		JPanel Dessert = new JPanel();
		Dessert.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Dessert.setBounds(0, 215, 170, 36);
		panel.add(Dessert);
		
		JLabel LabelDessert = new JLabel("Dessert");
		LabelDessert.setHorizontalAlignment(SwingConstants.CENTER);
		Dessert.add(LabelDessert);
		
		PanelButtonBayar = new PanelButtonAll(Color.yellow, Color.white);
		PanelButtonBayar.setBounds(79, 394, 81, 36);
		panel.add(PanelButtonBayar);
		PanelButtonBayar.setLayout(null);
		
		JButton ButtonBayar = new JButton("Bayar");
		ButtonBayar.setOpaque(false);
		ButtonBayar.setBounds(0, 0, 81, 36);
		PanelButtonBayar.add(ButtonBayar);
                
                ButtonBayar.addActionListener(e -> {
                    payPage byr = new payPage();  // buat objek frameB
                    byr.setVisible(true);       // tampilkan frameB
                    byr.setLocationRelativeTo(null); // posisikan di tengah layar
                    this.dispose();     
                });
		
		JLabel LabelHarga = new JLabel("Harga");
		LabelHarga.setBounds(10, 355, 46, 14);
		panel.add(LabelHarga);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 373, 69, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		LayerMakanan = new PanelLoginGradient();
		LayerMakanan.setBounds(168, 0, 456, 441);
		contentPane.add(LayerMakanan);
		LayerMakanan.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 21, 189, 115);
		LayerMakanan.add(panel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(10, 162, 189, 115);
		LayerMakanan.add(panel_1_1);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(10, 303, 189, 115);
		LayerMakanan.add(panel_1_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(315, 101, 71, 20);
		LayerMakanan.add(panel_2);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBounds(315, 242, 71, 20);
		LayerMakanan.add(panel_2_1);
		
		JPanel panel_2_2 = new JPanel();
		panel_2_2.setBounds(315, 384, 71, 20);
		LayerMakanan.add(panel_2_2);
		
		JLabel lblNewLabel = new JLabel("-     +");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(315, 76, 82, 14);
		LayerMakanan.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("-     +");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(304, 217, 82, 14);
		LayerMakanan.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("-     +");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(304, 359, 82, 14);
		LayerMakanan.add(lblNewLabel_2);
		
		JLabel imgMakanan1 = new JLabel();
                imgMakanan1.setBounds(0, 0, 189, 115); // sesuaikan ukuran panel
                ImageIcon icon1 = new ImageIcon(this.getClass().getResource("/img/nasigoreng.jpeg"));
                Image image1 = icon1.getImage().getScaledInstance(189, 115, Image.SCALE_SMOOTH);
                imgMakanan1.setIcon(new ImageIcon(image1));
                panel_1.add(imgMakanan1);

                // Panel kedua
                JLabel imgMakanan2 = new JLabel();
                imgMakanan2.setBounds(0, 0, 189, 115);
                ImageIcon icon2 = new ImageIcon(this.getClass().getResource("/img/ayam.jpeg"));
                Image image2 = icon2.getImage().getScaledInstance(189, 115, Image.SCALE_SMOOTH);
                imgMakanan2.setIcon(new ImageIcon(image2));
                panel_1_1.add(imgMakanan2);

                // Panel ketiga
                JLabel imgMakanan3 = new JLabel();
                imgMakanan3.setBounds(0, 0, 189, 115);
                ImageIcon icon3 = new ImageIcon(this.getClass().getResource("/img/mie.jpeg"));
                Image image3 = icon3.getImage().getScaledInstance(189, 115, Image.SCALE_SMOOTH);
                imgMakanan3.setIcon(new ImageIcon(image3));
                panel_1_2.add(imgMakanan3);

	}
}
