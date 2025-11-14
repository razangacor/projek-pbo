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

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtStok;
    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnClear;
    
    private JButton btnKembali;    
    private JFrame frameSebelumnya;

    private final String url = "jdbc:mysql://localhost:3306/restaurant2";
    private final String user = "root";
    private final String pass = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver MySQL tidak ditemukan!\nPastikan file JAR connector sudah ada.", "Driver Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
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
        setSize(600, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] kolom = {"ID Bahan", "Nama Bahan", "Stok"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        table.setOpaque(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    setOpaque(true);
                } else {
                    setOpaque(false);
                }

                return this; 
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); 
        scrollPane.getViewport().setOpaque(false); 

        table.getTableHeader().setOpaque(false);
        
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel(new BorderLayout(10, 10));
        JPanel panelFields = new JPanel(new GridLayout(3, 2, 5, 5));
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        txtId = new JTextField();
        txtId.setEditable(false); 
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

        tambahListeners();

        tampilkanData();
    }
    

    private void kembaliKeMenu() {
        if (frameSebelumnya != null) {
            frameSebelumnya.setVisible(true);
        }

        this.dispose();
    }
    
    private void tambahListeners() {
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
    
        
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        
        

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    
                    txtId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtNama.setText(model.getValueAt(selectedRow, 1).toString());
                    txtStok.setText(model.getValueAt(selectedRow, 2).toString());
                }
            }
        });
    }


    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtStok.setText("");
        table.clearSelection();
    }


    private void tampilkanData() {
        String sql = "SELECT * FROM bahan_makanan";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

    private void tambahData() {
        String nama = txtNama.getText();
        String stokStr = txtStok.getText();


        if (nama.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Bahan dan Stok tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int stok = Integer.parseInt(stokStr); 
            String sql = "INSERT INTO bahan_makanan (nama_bahan, stok) VALUES (?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, nama);
                pstmt.setInt(2, stok);
                
                pstmt.executeUpdate(); 
                
                JOptionPane.showMessageDialog(this, "Data baru berhasil ditambahkan!");
                tampilkanData(); 
                clearForm();     
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menambah data: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateData() {
        String idStr = txtId.getText();
        String nama = txtNama.getText();
        String stokStr = txtStok.getText();
        
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
                pstmt.setInt(3, id); 
                
                int affectedRows = pstmt.executeUpdate(); 
                
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil di-update!");
                    tampilkanData(); 
                    clearForm();    
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


    private void hapusData() {
        String idStr = txtId.getText();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

                    pstmt.setInt(1, id); 
                    
                    int affectedRows = pstmt.executeUpdate(); 
                    
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                        tampilkanData(); 
                        clearForm();     
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