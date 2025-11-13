package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap; // <-- Import ini

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

import panel.PanelLoginGradient;
import panel.PanelButtonAll;

// --- TIDAK PERLU IMPORT java.sql.* LAGI ---

public class menuPage extends JFrame {

    private PanelLoginGradient contentPane, LayerMakanan;
    private JTextField textTotalHarga;
    private PanelButtonAll PanelButtonBayar;
    
    // Ini adalah "keranjang belanja" kita
    private ArrayList<MenuItemPanel> keranjangBelanja;
    
    // --- TIDAK PERLU VARIABEL DATABASE & STATIC BLOCK LAGI ---


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
        
        // --- Inisialisasi Keranjang ---
        keranjangBelanja = new ArrayList<>();

        // --- Setup Frame Utama ---
        setTitle("Menu Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Frame utama boleh exit on close
        setBounds(100, 100, 640, 480);
        setLocationRelativeTo(null); // Tambahkan ini
        
        contentPane = new PanelLoginGradient();
        this.setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // --- Panel Sidebar Kiri ---
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
        
        // ... (Panel Minuman, Dessert, dll.) ...
        
        // --- Panel Total Harga & Tombol Bayar ---
        JLabel LabelHarga = new JLabel("Total Harga");
        LabelHarga.setBounds(10, 355, 80, 14);
        panel.add(LabelHarga);
        
        textTotalHarga = new JTextField();
        textTotalHarga.setEditable(false);
        textTotalHarga.setBounds(10, 373, 150, 20);
        panel.add(textTotalHarga);
        textTotalHarga.setColumns(10);
        
        PanelButtonBayar = new PanelButtonAll(Color.yellow, Color.white);
        PanelButtonBayar.setBounds(10, 400, 150, 36);
        panel.add(PanelButtonBayar);
        PanelButtonBayar.setLayout(null);
        
        JButton ButtonBayar = new JButton("Bayar");
        ButtonBayar.setOpaque(false);
        ButtonBayar.setBounds(0, 0, 150, 36);
        PanelButtonBayar.add(ButtonBayar);
        
        // --- Panel Kanan (Tempat Menu) ---
        LayerMakanan = new PanelLoginGradient();
        LayerMakanan.setBounds(168, 0, 456, 441);
        contentPane.add(LayerMakanan);
        LayerMakanan.setLayout(null);
        
        // =================================================================
        // --- MENGGUNAKAN MenuItemPanel (CETAKAN KITA) ---
        // =================================================================

        // 1. Nasi Goreng
        MenuItemPanel nasiGoreng = new MenuItemPanel(
            "Nasi Goreng", 15000, "/img/nasigoreng.jpeg", this);
        nasiGoreng.setBounds(10, 21, 430, 115);
        LayerMakanan.add(nasiGoreng);
        keranjangBelanja.add(nasiGoreng);

        // 2. Ayam
        MenuItemPanel ayam = new MenuItemPanel(
            "Ayam", 18000, "/img/ayam.jpeg", this);
        ayam.setBounds(10, 146, 430, 115);
        LayerMakanan.add(ayam);
        keranjangBelanja.add(ayam);

        // 3. Mie
        MenuItemPanel mie = new MenuItemPanel(
            "Mie", 12000, "/img/mie.jpeg", this);
        mie.setBounds(10, 271, 430, 115);
        LayerMakanan.add(mie);
        keranjangBelanja.add(mie);

        // --- Event Listener untuk Tombol Bayar (LOGIKA BARU) ---
        ButtonBayar.addActionListener(e -> {
            // Ambil total harga
            int totalHarga = getGrandTotal();
            
            // 1. Cek jika keranjang kosong
            if (totalHarga <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Keranjang Anda masih kosong.", 
                    "Info", 
                    JOptionPane.INFORMATION_MESSAGE);
                return; // Jangan pindah halaman
            }

            // 2. Buat "Pesanan" (HashMap) untuk dikirim ke payPage
            //    Formatnya: (NamaMenu -> Jumlah)
            HashMap<String, Integer> pesanan = new HashMap<>();
            for (MenuItemPanel item : keranjangBelanja) {
                if (item.getJumlah() > 0) {
                    // Masukkan nama menu dan jumlah yg dibeli
                    pesanan.put(item.getNamaMenu(), item.getJumlah());
                }
            }

            // 3. Buat payPage, kirim total, pesanan, dan frame ini ('this')
            payPage byr = new payPage(totalHarga, pesanan, this); 
            byr.setVisible(true);
            byr.setLocationRelativeTo(null);
            
            // 4. Sembunyikan frame menu ini (JANGAN ditutup/dispose)
            this.setVisible(false); 
        });
        
        // Inisialisasi total harga saat start
        updateTotalHarga();
    }
    
    /**
     * Method ini dipanggil oleh MenuItemPanel (tombol + dan -)
     * untuk meng-update total harga di sidebar.
     */
    public void updateTotalHarga() {
        int totalSemua = 0;
        
        // Loop semua item di keranjang
        for (MenuItemPanel item : keranjangBelanja) {
            totalSemua += item.getSubtotal(); // Ambil subtotal dari tiap item
        }
        
        // Update text field di sidebar
        textTotalHarga.setText(String.format("Rp %,d", totalSemua));
    }
    
    /**
     * Helper untuk mengambil nilai integer dari total harga
     * (Dibaca oleh tombol Bayar)
     */
    private int getGrandTotal() {
        int totalSemua = 0;
        for (MenuItemPanel item : keranjangBelanja) {
            totalSemua += item.getSubtotal();
        }
        return totalSemua;
    }
    
    // --- METHOD prosesPembayaran() SUDAH DIHAPUS ---
    // --- (Karena logikanya pindah ke payPage.java) ---
}