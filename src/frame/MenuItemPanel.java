package frame;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ini adalah 'cetakan' untuk SATU item menu.
 * Panel ini mengelola logikanya sendiri (jumlah, subtotal).
 */
public class MenuItemPanel extends JPanel {

    private JLabel lblNamaMenu;
    private JLabel lblHargaSatuan;
    private JLabel lblGambar;
    private JButton btnMinus;
    private JButton btnPlus;
    private JTextField txtJumlah;

    private int hargaSatuan;
    private int jumlah;
    private String namaMenu;
    private menuPage frameMenuUtama; // Referensi ke frame utama

    // Konstruktor untuk membuat panel item menu
    public MenuItemPanel(String namaMenu, int hargaSatuan, String imgPath, menuPage frameMenuUtama) {
        this.hargaSatuan = hargaSatuan;
        this.namaMenu = namaMenu;
        this.frameMenuUtama = frameMenuUtama;
        this.jumlah = 0; // Awalnya 0
        
        // Kita gunakan null layout di sini agar sesuai dengan frame utama Anda
        setLayout(null); 
        setBounds(0, 0, 430, 115); // Ukuran contoh (Sesuaikan)

        // 1. Panel untuk Gambar
        JPanel panelGambar = new JPanel();
        panelGambar.setBounds(0, 0, 189, 115);
        add(panelGambar);
        
        lblGambar = new JLabel();
        lblGambar.setBounds(0, 0, 189, 115);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imgPath));
            Image image = icon.getImage().getScaledInstance(189, 115, Image.SCALE_SMOOTH);
            lblGambar.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            lblGambar.setText("Gbr Error");
            e.printStackTrace();
        }
        panelGambar.add(lblGambar);
        
        // 2. Label Nama Menu
        lblNamaMenu = new JLabel(namaMenu);
        lblNamaMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNamaMenu.setBounds(210, 11, 150, 20);
        add(lblNamaMenu);
        
        // 3. Label Harga Satuan
        lblHargaSatuan = new JLabel("Rp " + String.format("%,d", hargaSatuan)); // Format Rp 15.000
        lblHargaSatuan.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblHargaSatuan.setBounds(210, 35, 100, 15);
        add(lblHargaSatuan);

        // 4. Tombol Minus (-)
        btnMinus = new JButton("-");
        btnMinus.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnMinus.setBounds(210, 65, 45, 30);
        add(btnMinus);

        // 5. Text Field Jumlah
        txtJumlah = new JTextField();
        txtJumlah.setText(String.valueOf(this.jumlah));
        txtJumlah.setHorizontalAlignment(SwingConstants.CENTER);
        txtJumlah.setEditable(false);
        txtJumlah.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtJumlah.setBounds(260, 65, 40, 30);
        add(txtJumlah);

        // 6. Tombol Plus (+)
        btnPlus = new JButton("+");
        btnPlus.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnPlus.setBounds(305, 65, 45, 30);
        add(btnPlus);

        // --- LOGIKA (EVENT LISTENER) ---
        
        // Saat tombol + diklik
        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jumlah++;
                txtJumlah.setText(String.valueOf(jumlah));
                // Beri tahu frame utama bahwa ada perubahan harga
                frameMenuUtama.updateTotalHarga(); 
            }
        });

        // Saat tombol - diklik
        btnMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jumlah > 0) {
                    jumlah--;
                    txtJumlah.setText(String.valueOf(jumlah));
                    // Beri tahu frame utama bahwa ada perubahan harga
                    frameMenuUtama.updateTotalHarga();
                }
            }
        });
    }

    // --- Method Helper (agar bisa diakses dari menuPage) ---
    
    public int getJumlah() {
        return this.jumlah;
    }
    
    public String getNamaMenu() {
        return this.namaMenu;
    }

    public int getHargaSatuan() {
        return this.hargaSatuan;
    }

    // Menghitung subtotal untuk item INI SAJA
    public int getSubtotal() {
        return this.hargaSatuan * this.jumlah;
    }
}