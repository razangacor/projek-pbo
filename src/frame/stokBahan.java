package frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import panel.*;

public class stokBahan extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    // Komponen GUI baru untuk form input
    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtStok;
    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnClear;
    
    private JButton btnKembali;    
    private JFrame frameSebelumnya;

    // Detail koneksi (dijadikan variabel global agar mudah diakses)
    private final String url = "jdbc:mysql://localhost:3306/restaurant2";
    private final String user = "root";
    private final String pass = "";

    // **PENTING: Blok statis untuk mendaftarkan driver MySQL**
    // Ini akan mengatasi error "No suitable driver found"
    static {
        try {
            // Gunakan 'com.mysql.cj.jdbc.Driver' untuk driver modern (versi 8+)
            // Gunakan 'com.mysql.jdbc.Driver' untuk driver lama (versi 5)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver MySQL tidak ditemukan!\nPastikan file JAR connector sudah ada.", "Driver Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Keluar dari aplikasi jika driver tidak ada
        }
    }

    public stokBahan(JFrame  frameInduk) {
        
        this.frameSebelumnya = frameInduk;
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		
		PanelLoginGradient panelbayar = new PanelLoginGradient();
		this.setContentPane(panelbayar);
		panelbayar.setLayout(null);
                
 
        
        setTitle("Manajemen Stok Bahan Makanan");
        setSize(600, 600); // Perbesar ukuran window
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Gunakan BorderLayout

        // --- Bagian Tabel (CENTER) ---
        // --- Bagian Tabel (CENTER) ---
        String[] kolom = {"ID Bahan", "Nama Bahan", "Stok"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        // ===============================================
        // === TAMBAHAN UNTUK BACKGROUND TRANSPARAN ===
        // ===============================================

        // 1. Buat tabel menjadi non-opaque (tidak menggambar background-nya)
        table.setOpaque(false);

        // 2. (PENTING) Buat Cell Renderer kustom agar setiap sel transparan
        //    Tanpa ini, sel-sel akan tetap berwarna putih.
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                // Panggil method super untuk konfigurasi default (seperti teks, dll.)
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Cek apakah sel ini sedang diseleksi
                if (isSelected) {
                    // Jika diseleksi, biarkan opaque (agar warna seleksi terlihat)
                    setOpaque(true);
                } else {
                    // Jika tidak diseleksi, buat transparan
                    setOpaque(false);
                }

                /*
                // --- (SANGAT DISARANKAN) Ganti warna teks ---
                // Jika background gradient kamu gelap, teks hitam tidak akan terlihat.
                // Aktifkan baris di bawah ini dan ganti warnanya sesuai kebutuhan.
                setForeground(Color.WHITE); // Ganti ke warna yang kontras
                */

                return this; // 'this' adalah JLabel yang sudah dikonfigurasi
            }
        });

        // 3. Buat ScrollPane dan Viewport-nya juga transparan
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // Buat scrollpane non-opaque
        scrollPane.getViewport().setOpaque(false); // Buat "kaca" viewport non-opaque

        // 4. (Opsional) Buat header tabel non-opaque
        table.getTableHeader().setOpaque(false);

        // ===============================================
        // === AKHIR BAGIAN TRANSPARAN ===
        // ===============================================
        
        add(scrollPane, BorderLayout.CENTER);

        // --- Bagian Form Input (SOUTH) ---
        JPanel panelInput = new JPanel(new BorderLayout(10, 10));
        JPanel panelFields = new JPanel(new GridLayout(3, 2, 5, 5)); // 3 baris, 2 kolom
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Inisialisasi komponen form
        txtId = new JTextField();
        txtId.setEditable(false); // ID tidak boleh di-edit manual, terisi otomatis
        txtNama = new JTextField();
        txtStok = new JTextField();

        panelFields.add(new JLabel("ID Bahan:"));
        panelFields.add(txtId);
        panelFields.add(new JLabel("Nama Bahan:"));
        panelFields.add(txtNama);
        panelFields.add(new JLabel("Stok:"));
        panelFields.add(txtStok);

        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        btnKembali = new JButton("Kembali");

        panelButtons.add(btnTambah);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnHapus);
        panelButtons.add(btnClear);

        panelInput.add(panelFields, BorderLayout.CENTER);
        panelInput.add(panelButtons, BorderLayout.SOUTH);
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Beri padding
        
        
        panelButtons.add(btnKembali);
        
        
        add(panelInput, BorderLayout.SOUTH);

        // --- Tambahkan Event Listeners ---
        tambahListeners();

        // Panggil fungsi untuk ambil data saat program pertama kali jalan
        tampilkanData();
    }
    
    /**
     * Menutup frame ini dan menampilkan kembali frame sebelumnya.
     */
    private void kembaliKeMenu() {
        // Tampilkan kembali frame sebelumnya
        if (frameSebelumnya != null) {
            frameSebelumnya.setVisible(true);
        }
        
        // Tutup frame ini (stokBhan)
        this.dispose();
    }
    
    private void tambahListeners() {
        // 1. Listener untuk tombol 'Tambah'
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahData();
                
            }
            
        });

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kembaliKeMenu();
            }
        });
    
        
        // 2. Listener untuk tombol 'Update'
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        
        

        // 3. Listener untuk tombol 'Hapus'
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });

        // 4. Listener untuk tombol 'Clear'
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // 5. Listener untuk JTable (saat baris di-klik)
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Pastikan event tidak dieksekusi 2x dan ada baris yang dipilih
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    
                    // Ambil data dari model tabel dan masukkan ke text field
                    txtId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtNama.setText(model.getValueAt(selectedRow, 1).toString());
                    txtStok.setText(model.getValueAt(selectedRow, 2).toString());
                }
            }
        });
    }

    /**
     * Mengosongkan form input dan membersihkan pilihan di tabel
     */
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtStok.setText("");
        table.clearSelection();
    }

    /**
     * Mengambil data dari database dan menampilkannya di JTable
     */
    private void tampilkanData() {
        // Ganti try-with-resources agar lebih rapi
        String sql = "SELECT * FROM bahan_makanan";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Hapus isi tabel lama
            model.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id_bahan");
                String nama = rs.getString("nama_bahan");
                int stok = rs.getInt("stok");
                model.addRow(new Object[]{id, nama, stok});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data: " + e.getMessage());
        }
    }

    /**
     * Menambahkan data baru ke database
     */
    private void tambahData() {
        String nama = txtNama.getText();
        String stokStr = txtStok.getText();

        // Validasi input sederhana
        if (nama.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Bahan dan Stok tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int stok = Integer.parseInt(stokStr); // Pastikan stok adalah angka
            // ID (id_bahan) diasumsikan AUTO_INCREMENT di database
            String sql = "INSERT INTO bahan_makanan (nama_bahan, stok) VALUES (?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, nama);
                pstmt.setInt(2, stok);
                
                pstmt.executeUpdate(); // Eksekusi query INSERT
                
                JOptionPane.showMessageDialog(this, "Data baru berhasil ditambahkan!");
                tampilkanData(); // Refresh tabel
                clearForm();     // Kosongkan form
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menambah data: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Meng-update data yang ada di database
     */
    private void updateData() {
        String idStr = txtId.getText();
        String nama = txtNama.getText();
        String stokStr = txtStok.getText();

        // Validasi: pastikan ada data yang dipilih (ID terisi)
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang ingin di-update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int stok = Integer.parseInt(stokStr);
            String sql = "UPDATE bahan_makanan SET nama_bahan = ?, stok = ? WHERE id_bahan = ?";

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nama);
                pstmt.setInt(2, stok);
                pstmt.setInt(3, id); // WHERE clause
                
                int affectedRows = pstmt.executeUpdate(); // Eksekusi query UPDATE
                
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil di-update!");
                    tampilkanData(); // Refresh tabel
                    clearForm();     // Kosongkan form
                } else {
                    JOptionPane.showMessageDialog(this, "Data dengan ID tersebut tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal update data: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID dan Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menghapus data dari database
     */
    private void hapusData() {
        String idStr = txtId.getText();

        // Validasi: pastikan ada data yang dipilih (ID terisi)
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Konfirmasi penghapusan
        int pilihan = JOptionPane.showConfirmDialog(this, 
                "Anda yakin ingin menghapus data '" + txtNama.getText() + "'?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);

        if (pilihan == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                String sql = "DELETE FROM bahan_makanan WHERE id_bahan = ?";

                try (Connection conn = DriverManager.getConnection(url, user, pass);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, id); // WHERE clause
                    
                    int affectedRows = pstmt.executeUpdate(); // Eksekusi query DELETE
                    
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                        tampilkanData(); // Refresh tabel
                        clearForm();     // Kosongkan form
                    } else {
                        JOptionPane.showMessageDialog(this, "Data dengan ID tersebut tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Gagal hapus data: " + e.getMessage());
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    
}