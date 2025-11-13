package frame;

import panel.PanelBulet;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// Import semua untuk SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
// ---------------------------------------------

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane; // <-- TAMBAH IMPORT INI
import javax.swing.JTextArea;   // <-- TAMBAH IMPORT INI

import panel.PanelButtonAll;
import panel.PanelLoginGradient;

public class payPage extends JFrame {

    private PanelBulet panelCash, PanelQris;
    
    // --- Variabel Data ---
    private int totalBayar;
    private HashMap<String, Integer> pesanan; // (NamaMenu -> Jumlah)
    private JFrame frameMenuUtama; // Frame menu untuk kita kembali
    
    // --- TAMBAHAN BARU ---
    // Untuk menyimpan subtotal (Harga x Jumlah) per item untuk struk
    private HashMap<String, Integer> subtotalPerItem; 
    
    // --- Variabel Database ---
    private final String url = "jdbc:mysql://localhost:3306/restaurant2";
    private final String user = "root";
    private final String pass = "";

    // --- Driver ---
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver MySQL tidak ditemukan!", "Driver Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    // -----------------------

    /**
     * Launch the application (untuk tes)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    payPage frame = new payPage(15000, new HashMap<>(), null);
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
    public payPage(int totalHarga, HashMap<String, Integer> pesanan, JFrame frameMenuUtama) {
        this.totalBayar = totalHarga;
        this.pesanan = pesanan;
        this.frameMenuUtama = frameMenuUtama;
        this.subtotalPerItem = new HashMap<>(); // <-- Inisialisasi HashMap baru

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 640, 480);
        setLocationRelativeTo(null); 

        PanelLoginGradient panelbayar = new PanelLoginGradient();
        this.setContentPane(panelbayar);
        panelbayar.setLayout(null);
        
        // ... (Semua kode GUI Anda: Logo, Label, PanelQris, PanelCash, dll.) ...
        // (Saya singkat agar fokus ke perubahan, tapi kode Anda tetap sama)
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
		
		JLabel lblTotal = new JLabel("Total: Rp " + String.format("%,d", totalBayar));
        lblTotal.setFont(new Font("Serif", Font.BOLD, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotal.setBounds(84, 180, 432, 30);
        panelbayar.add(lblTotal);

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


        // ==========================================================
        // === LOGIKA TOMBOL ===
        // ==========================================================

        // 1. Logika Tombol Cash/Tunai
        ButtonCash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String formattedTotal = String.format("Rp %,d", totalBayar);
                JOptionPane.showMessageDialog(
                    payPage.this,
                    "Total pembayaran: " + formattedTotal + "\nSilahkan lakukan pembayaran di kasir.",
                    "Pembayaran Tunai",
                    JOptionPane.INFORMATION_MESSAGE
                );
                tampilkanKonfirmasiPembayaran();
            }
        });

        // 2. Logika Tombol QRIS
        ButtonQris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIcon qrIcon = new ImageIcon(getClass().getResource("/img/qris-dummy.png"));
                    Image qrImage = qrIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon resizedQrIcon = new ImageIcon(qrImage);
                    String formattedTotal = String.format("Rp %,d", totalBayar);
                    String message = "Total: " + formattedTotal + "\nSilahkan scan QRIS di bawah ini:";
                    
                    JOptionPane.showMessageDialog(
                        payPage.this, message, "Pembayaran QRIS",
                        JOptionPane.INFORMATION_MESSAGE, resizedQrIcon
                    );
                    tampilkanKonfirmasiPembayaran();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(payPage.this, 
                        "Gagal memuat gambar QRIS.", "Error Gambar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Method untuk menampilkan konfirmasi pembayaran
     */
    private void tampilkanKonfirmasiPembayaran() {
        int pilihan = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda sudah menyelesaikan pembayaran?",
            "Konfirmasi Pembayaran",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (pilihan == JOptionPane.YES_OPTION) {
            boolean sukses = lakukanPenguranganStok();
            
            if (sukses) {
                // --- PERUBAHAN DI SINI ---
                // 1. Tampilkan Struk
                tampilkanStruk(); 
                
                // 2. Kembali ke menu (setelah user klik OK pada struk)
                if (frameMenuUtama != null) {
                    frameMenuUtama.dispose(); 
                }
                new menuPage().setVisible(true); 
                this.dispose(); 
                // --- AKHIR PERUBAHAN ---
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Pembayaran Gagal: Stok bahan tidak mencukupi.", 
                    "Error Stok", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==========================================================
    // === METHOD BARU UNTUK TAMPILKAN STRUK ===
    // ==========================================================
    private void tampilkanStruk() {
        StringBuilder struk = new StringBuilder();
        struk.append("        STRUK PEMBAYARAN\n");
        struk.append("          RESTORAN ANDA\n");
        struk.append("------------------------------------------\n");
        
        // Header Tabel
        struk.append(String.format("%-17s %-3s   %-12s\n", "Item", "Jml", "Subtotal"));
        struk.append("------------------------------------------\n");

        // Loop data pesanan
        for (String namaMenu : pesanan.keySet()) {
            int jumlah = pesanan.get(namaMenu);
            // Ambil subtotal yang sudah kita simpan
            int subtotal = subtotalPerItem.get(namaMenu); 
            
            struk.append(String.format("%-17s x%-3d   Rp %,d\n", 
                namaMenu, 
                jumlah, 
                subtotal));
        }

        struk.append("------------------------------------------\n");
        struk.append(String.format("%-23s Rp %,d\n\n", "TOTAL BAYAR:", totalBayar));
        struk.append("          Terima Kasih!\n");

        // Gunakan JTextArea agar format 'Monospaced' (rata) berfungsi
        JTextArea textArea = new JTextArea(struk.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        // Buat JScrollPane agar bisa di-scroll jika struknya panjang
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 400)); // Atur ukuran dialog
        
        JOptionPane.showMessageDialog(
            this, 
            scrollPane, // Tampilkan JScrollPane
            "Struk Pembayaran", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }


    /**
     * Method untuk proses database (Pengurangan Stok & Pencatatan Pesanan)
     */
    private boolean lakukanPenguranganStok() {
        if (pesanan.isEmpty()) {
            return true;
        }

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            conn.setAutoCommit(false); 
            boolean stokCukup = true;
            
            for (String namaMenu : pesanan.keySet()) {
                int jumlahDipesan = pesanan.get(namaMenu);

                String sqlResep = "SELECT menu_bahan.id_bahan, menu_bahan.jumlah, b.stok, m.id_menu, m.harga " +
                        "FROM menu_bahan " + // <-- Hapus alias 'r'
                        "JOIN menu_makanan m ON menu_bahan.id_menu = m.id_menu " + // <-- Ganti 'r.'
                        "JOIN bahan_makanan b ON menu_bahan.id_bahan = b.id_bahan " + // <-- Ganti 'r.'
                        "WHERE m.nama_menu = ?";
                
                int idMenu = 0;
                int hargaSatuan = 0;
                
                try (PreparedStatement psResep = conn.prepareStatement(sqlResep)) {
                    psResep.setString(1, namaMenu);
                    ResultSet rs = psResep.executeQuery();
                    
                    if (!rs.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(this, "Error: Resep untuk '" + namaMenu + "' tidak ditemukan.");
                        stokCukup = false;
                        break;
                    }
                    
                    while (rs.next()) {
                        if (idMenu == 0) {
                            idMenu = rs.getInt("id_menu");
                            hargaSatuan = rs.getInt("harga");
                        }
                        
                        int idBahan = rs.getInt("id_bahan");
                        int butuhPerPorsi = rs.getInt("jumlah");
                        int stokSekarang = rs.getInt("stok");
                        int totalBahanDibutuhkan = butuhPerPorsi * jumlahDipesan;
                        
                        if (stokSekarang < totalBahanDibutuhkan) {
                            stokCukup = false;
                            break; 
                        }
                        
                        String sqlUpdateStok = "UPDATE bahan_makanan SET stok = stok - ? WHERE id_bahan = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStok)) {
                            psUpdate.setInt(1, totalBahanDibutuhkan);
                            psUpdate.setInt(2, idBahan);
                            psUpdate.executeUpdate();
                        }
                    }
                    
                    if (!stokCukup) break; 

                    // --- PERUBAHAN DI SINI ---
                    // Hitung subtotal DAN SIMPAN untuk struk
                    int subtotal = hargaSatuan * jumlahDipesan;
                    this.subtotalPerItem.put(namaMenu, subtotal); // <-- SIMPAN SUBTOTAL
                    
                    String sqlInsertPesanan = "INSERT INTO pesanan (id_menu, jumlah, total_harga) VALUES (?, ?, ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(sqlInsertPesanan)) {
                        psInsert.setInt(1, idMenu);
                        psInsert.setInt(2, jumlahDipesan);
                        psInsert.setInt(3, subtotal);
                        psInsert.executeUpdate();
                    }
                    // --- AKHIR PERUBAHAN ---
                    
                } catch (SQLException e) {
                    stokCukup = false;
                    JOptionPane.showMessageDialog(this, "Error saat memproses resep: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            } 
            
            if (stokCukup) {
                conn.commit(); 
                return true; 
            } else {
                conn.rollback(); 
                return false; 
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}