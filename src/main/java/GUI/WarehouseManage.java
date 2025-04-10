/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.Impl.WarehouseDetailImpl;
import DAO.Impl.WarehouseImpl;
import DAO.WarehouseDAO;
import DAO.WarehouseDetailDAO;
import Model.Warehouse;
import Model.WarehouseDetail;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DZUNG
 */
public class WarehouseManage extends javax.swing.JPanel {
    
    private final WarehouseDAO warehouseDAO;
    private final WarehouseDetailDAO warehouseDetailDAO;
    private ArrayList<Warehouse> warehouseList;
    private ArrayList<WarehouseDetail> warehouseDetailList;
    private DefaultTableModel modelTableWarehouse;
    private DefaultTableModel modelTableDetail;
    private Warehouse warehouseSelected;
    private WarehouseDetail warehouseDetailSelected;

    /**
     * Creates new form GoodsReceipt
     */
    public WarehouseManage() {
        warehouseDAO = new WarehouseImpl();
        warehouseDetailDAO = new WarehouseDetailImpl();
        initComponents();
        modelTableWarehouse = (DefaultTableModel) tbWarehouse.getModel();
        modelTableDetail = (DefaultTableModel) tbWarehouseDetail.getModel();
        // Renderer để hiển thị JButton
        tbWarehouse.getColumnModel().getColumn(3).setCellRenderer((JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> (Component) value);
        // Tạo sự kiện khi SelectedRow của JTable thay đổi giá trị.
        tbWarehouse.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbWarehouse.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                    modelTableDetail.setRowCount(0);
                }
                else {
                    if (tbWarehouse.getValueAt(selectedRow, 0) instanceof Warehouse warehouse) {
                        warehouseSelected = warehouse;
                        getDataWarehouseDetail();
                        loadWarehouseDetail();
                    }
                }
            }
        });
        tbWarehouseDetail.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbWarehouseDetail.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                }
                else {
                    if (tbWarehouseDetail.getValueAt(selectedRow, 0) instanceof WarehouseDetail detail) {
                        warehouseDetailSelected = detail;
                        txtProductName.setText(detail.getProductName());
                        txtQuantity.setText(String.valueOf(detail.getQuantity()));
                        txtPrice.setText(Untils.formatMoney(detail.getPrice()));
                        lblAmount.setText(Untils.formatMoney(detail.getAmount()));
                        btnAdd.setEnabled(false);
                        btnUpdate.setEnabled(true);
                        btnDelete.setEnabled(true);
                    }
                }
            }
        });
        getDataWarehouse();
        loadWarehouse();
    }
    
    /**
    * Lấy dữ liệu Warehouse vào warehouseList.
    * 
    */
    private void getDataWarehouse(){
        warehouseList = warehouseDAO.getAllWarehouses();
    }
    
    /**
    * Lấy dữ liệu WarehouseDetail vào warehouseDetailList.
    * 
    */
    private void getDataWarehouseDetail(){
        warehouseDetailList = warehouseDetailDAO.getAllWarehouseDetailsByWarehouseId(warehouseSelected.getId());
    }
    
    /**
    * Đổ dữ liệu Warehouse cho JTable tbWarehouse.
    * 
    */
    private void loadWarehouse(){
        clear();
        modelTableWarehouse.setRowCount(0);
        for (Warehouse warehouse : warehouseList) {
            Object[] row = new Object[4];
            row[0] = warehouse;
            row[1] = warehouse.getUserName();
            row[2] = Untils.formatMoney(warehouse.getTotalAmount());
            row[3] = new JButton("Xóa");
            modelTableWarehouse.addRow(row);
        }
        modelTableDetail.setRowCount(0);
    }
    
    /**
    * Đổ dữ liệu WarehouseDetail cho JTable tbWarehouseDetail.
    * 
    */
    private void loadWarehouseDetail(){
        clear();
        modelTableDetail.setRowCount(0);
        for (WarehouseDetail detail : warehouseDetailList) {
            Object[] row = new Object[4];
            row[0] = detail;
            row[1] = detail.getQuantity();
            row[2] = Untils.formatMoney(detail.getPrice());
            row[3] = Untils.formatMoney(detail.getAmount());
            modelTableDetail.addRow(row);
        }
    }
    
    private boolean checkInputWahouseDetail(){
        if (!Untils.validateText(txtProductName)) {
            return false;
        }
        if (Untils.parseToInt(txtQuantity.getText().trim()) <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhâpj số lượng lớn hơn 0 !", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtQuantity.requestFocus();
            return false;
        }
        if (Untils.parseToInt(txtPrice.getText().trim()) <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá lớn hơn 0 !", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtPrice.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
    * Trả các thông tin trên màn hình về mặc định và bỏ chọn ở JTable tbWarehouseDetail.
    * 
    */
    private void clear(){
        warehouseDetailSelected = null;
        tbWarehouseDetail.clearSelection();
        txtProductName.setText(Constants.STR_EMPTY);
        txtQuantity.setText(Constants.STR_EMPTY);
        txtPrice.setText(Constants.STR_EMPTY);
        lblAmount.setText("0");
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbWarehouseDetail = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbWarehouse = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 255, 204));
        jLabel5.setText("Tên Sản Phẩm:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 204));
        jLabel6.setText("Số Lượng:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 255, 204));
        jLabel8.setText("Đơn Giá:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 255, 204));
        jLabel9.setText("Thành Tiền:");

        txtProductName.setName("Tên Sản Phẩm"); // NOI18N

        txtPrice.setName("Đơn Giá"); // NOI18N

        txtQuantity.setName("Số Lượng"); // NOI18N

        btnAdd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setName(""); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdate.setText("Edit");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tbWarehouseDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbWarehouseDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbWarehouseDetail);

        btnRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 102));
        jLabel2.setText("QUẢN LÝ NHẬP KHO");

        tbWarehouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ngày nhập", "Người nhập", "Tổng tiền", "Xóa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbWarehouse.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbWarehouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbWarehouseMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbWarehouse);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 204));
        jLabel1.setText("Phiếu nhập kho:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 204));
        jLabel3.setText("Chi tiết:");

        lblAmount.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAmount.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(353, 353, 353))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(86, 86, 86))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnUpdate)))
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDelete)
                        .addGap(148, 148, 148)
                        .addComponent(btnRefresh))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(lblAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblAmount))))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (warehouseDetailSelected == null) {
            return;
        }
        if (warehouseDetailDAO.deleteWarehouseDetail(warehouseDetailSelected.getId())) {
            // Cập nhật lại thông tin mới từ database.
            Warehouse newWarehouse = warehouseDAO.getWarehouseByID(warehouseDetailSelected.getWarehouseId());
            int index = warehouseList.indexOf(warehouseSelected);
            warehouseList.set(index, newWarehouse);
            loadWarehouse();
            // Cọn lại data của Warehouse.
            tbWarehouse.setRowSelectionInterval(index, 0);
            tbWarehouse.scrollRectToVisible(tbWarehouse.getCellRect(index, 0, true));
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        getDataWarehouse();
        loadWarehouse();
        modelTableDetail.setRowCount(0);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tbWarehouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbWarehouseMouseClicked
        int selectedCol = tbWarehouse.getSelectedColumn();
        int selectedRow = tbWarehouse.getSelectedRow();
        if (selectedCol == 3 && warehouseSelected != null) {
            if (JOptionPane.showConfirmDialog(null,"Bạn có chắc muốn xóa dữ liệu này không ?", "Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (warehouseDAO.deleteWarehouse(warehouseSelected.getId())) {
                    warehouseList.remove(warehouseSelected);
                    modelTableWarehouse.removeRow(selectedRow);
                }
            }
        }
    }//GEN-LAST:event_tbWarehouseMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JTable tbWarehouse;
    private javax.swing.JTable tbWarehouseDetail;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
