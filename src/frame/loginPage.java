package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

import panel.PanelButtonAll;
import panel.PanelLoginGradient;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class loginPage extends JFrame {

	private JTextField UserField;
	private JPasswordField PassField;
	private PanelLoginGradient panellogingradient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
                        @Override
			public void run() {
				try {
					loginPage frame = new loginPage();
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
	public loginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		panellogingradient = new PanelLoginGradient();
		this.setContentPane(panellogingradient);
		panellogingradient.setLayout(null);
		
		JLabel imglabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/img/LogoRestoran.png")).getImage();
		imglabel.setIcon(new ImageIcon(img));
		imglabel.setBounds(10, 30, 309, 350);
		panellogingradient.add(imglabel);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(373, 93, 169, 34);
		panellogingradient.add(lblNewLabel);
		
		JPanel panel = new JPanel();	
		panel.setBackground(UIManager.getColor("Button.focus"));
		panel.setBounds(329, 329, 257, 4);
		panellogingradient.add(panel);
		
		UserField = new JTextField();
		UserField.setBounds(373, 162, 169, 20);
		panellogingradient.add(UserField);
		UserField.setColumns(10);
		
		PassField = new JPasswordField();
		PassField.setBounds(373, 219, 169, 20);
		panellogingradient.add(PassField);
		
		JLabel lblNewLabel_1 = new JLabel("User:");
		lblNewLabel_1.setBounds(373, 138, 43, 14);
		panellogingradient.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setBounds(373, 194, 62, 14);
		panellogingradient.add(lblNewLabel_2);
		
		JCheckBox rbCheck = new JCheckBox("Remember Me");
		rbCheck.setOpaque(false);
		rbCheck.setBounds(373, 246, 97, 23);
		panellogingradient.add(rbCheck);
		
		PanelButtonAll LoginButtonPanel = new PanelButtonAll(Color.ORANGE, Color.YELLOW);
		LoginButtonPanel.setBounds(373, 276, 97, 34);
		panellogingradient.add(LoginButtonPanel);
		LoginButtonPanel.setLayout(null);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.setOpaque(false);
		LoginButton.setContentAreaFilled(false);
		LoginButton.setBorderPainted(false);
		LoginButton.setBounds(0, 0, 97, 34);
		LoginButtonPanel.add(LoginButton);
                
                LoginButton.addActionListener(e -> {
                    String username = UserField.getText();
                    String password = PassField.getText();
                    
                    if (username.equals("admin") && password.equals("admin123")) {
                    stokBahan stok = new stokBahan(this); 
                    stok.setVisible(true);       // tampilkan frameB
                    stok.setLocationRelativeTo(null); // posisikan di tengah layar
                    this.dispose();     
                    }else{
                        JOptionPane.showMessageDialog(this, "Username atau Password salah");
                    }
                });
		
		PanelButtonAll GuessButtonPanel = new PanelButtonAll(Color.ORANGE, Color.YELLOW);
		GuessButtonPanel.setLayout(null);
		GuessButtonPanel.setBounds(373, 363, 169, 34);
		panellogingradient.add(GuessButtonPanel);
		
		JButton GuessButton = new JButton("Enter as Guest");
		GuessButton.setOpaque(false);
		GuessButton.setContentAreaFilled(false);
		GuessButton.setBorderPainted(false);
		GuessButton.setBounds(0, 0, 169, 34);
		GuessButtonPanel.add(GuessButton);
                
                GuessButton.addActionListener(e -> {
                    menuPage menu = new menuPage();  // buat objek frameB
                    menu.setVisible(true);       // tampilkan frameB
                    menu.setLocationRelativeTo(null); // posisikan di tengah layar
                    this.dispose();     
                });
		
	}
}
