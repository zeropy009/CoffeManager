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
 * @author zero
 */
public class WarehouseManage extends javax.swing.JPanel {
    
    private final WarehouseDAO warehouseDAO;
    private final WarehouseDetailDAO warehouseDetailDAO;
    private final DefaultTableModel modelTableWarehouse;
    private final DefaultTableModel modelTableDetail;
    private ArrayList<Warehouse> warehouseList;
    private ArrayList<WarehouseDetail> warehouseDetailList;
    private Warehouse warehouseSelected;
    private WarehouseDetail warehouseDetailSelected;

    /**
     * Creates new form WarehouseManage
     */
    public WarehouseManage() {
        warehouseDAO = new WarehouseImpl();
        warehouseDetailDAO = new WarehouseDetailImpl();
        initComponents();
        modelTableWarehouse = (DefaultTableModel) tblWarehouse.getModel();
        modelTableDetail = (DefaultTableModel) tblWarehouseDetail.getModel();
        // Renderer để hiển thị JButton
        tblWarehouse.getColumnModel().getColumn(3).setCellRenderer((JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> (Component) value);
        // Tạo sự kiện khi SelectedRow của JTable thay đổi giá trị.
        tblWarehouse.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblWarehouse.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                    modelTableDetail.setRowCount(0);
                }
                else {
                    if (tblWarehouse.getValueAt(selectedRow, 0) instanceof Warehouse warehouse) {
                        warehouseSelected = warehouse;
                        getDataWarehouseDetail();
                        loadWarehouseDetail();
                        btnAdd.setEnabled(true);
                    }
                }
            }
        });
        tblWarehouseDetail.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblWarehouseDetail.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                }
                else {
                    if (tblWarehouseDetail.getValueAt(selectedRow, 0) instanceof WarehouseDetail detail) {
                        warehouseDetailSelected = detail;
                        txtProductName.setText(detail.getProductName());
                        spnQuantity.setValue(detail.getQuantity());
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
        warehouseSelected = null;
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
    
    /**
    * Kiểm tra dữ liệu người dùng nhập trước khi cập nhật vào kho dữ liệu.
    * 
    * @return Trạng thái nhập đã hơp lệ True hoặc chưa hợp lệ False.
    */
    private boolean checkInputWahouseDetail(){
        if (!Untils.validateText(txtProductName)) {
            return false;
        }
        if (Untils.parseToInt(spnQuantity.getValue().toString()) <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng lớn hơn 0 !", "Thông báo", JOptionPane.WARNING_MESSAGE);
            spnQuantity.requestFocus();
            return false;
        }
        if (Untils.parseMoneyI(txtPrice.getText().trim()) <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá lớn hơn 0 !", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtPrice.requestFocus();
            return false;
        }
        return true;
    }
    
    private int CalcAmount(){
        int quantity = Untils.parseToInt(spnQuantity.getValue().toString());
        int price = Untils.parseMoneyI(txtPrice.getText().trim());
        return quantity * price;
    }
    
    /**
    * Trả các thông tin trên màn hình về mặc định và bỏ chọn ở JTable tbWarehouseDetail.
    * 
    */
    private void clear(){
        warehouseDetailSelected = null;
        tblWarehouseDetail.clearSelection();
        txtProductName.setText(Constants.STR_EMPTY);
        spnQuantity.setValue(0);
        txtPrice.setText(Constants.STR_EMPTY);
        lblAmount.setText("0");
        btnAdd.setEnabled(false);
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
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWarehouseDetail = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWarehouse = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        spnQuantity = new javax.swing.JSpinner();

        setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 255));
        jLabel5.setText("Tên Sản Phẩm:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 0, 255));
        jLabel6.setText("Số Lượng:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 255));
        jLabel8.setText("Đơn Giá:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 0, 255));
        jLabel9.setText("Thành Tiền:");

        txtProductName.setActionCommand("<Not Set>");
        txtProductName.setName("Tên Sản Phẩm"); // NOI18N
        txtProductName.setNextFocusableComponent(spnQuantity);

        txtPrice.setName("Đơn Giá"); // NOI18N
        txtPrice.setNextFocusableComponent(btnAdd);
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPriceFocusLost(evt);
            }
        });
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setName(""); // NOI18N
        btnAdd.setNextFocusableComponent(btnUpdate);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdate.setText("Edit");
        btnUpdate.setNextFocusableComponent(btnDelete);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setNextFocusableComponent(btnRefresh);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tblWarehouseDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        tblWarehouseDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblWarehouseDetail);

        btnRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setNextFocusableComponent(txtProductName);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 102));
        jLabel2.setText("QUẢN LÝ NHẬP KHO");

        tblWarehouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        tblWarehouse.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblWarehouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblWarehouseMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblWarehouse);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Phiếu nhập kho:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Chi tiết:");

        lblAmount.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAmount.setText("0");

        spnQuantity.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        spnQuantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnQuantityStateChanged(evt);
            }
        });

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnUpdate))
                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblAmount))))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (warehouseDetailSelected != null || !checkInputWahouseDetail()) {
            return;
        }
        warehouseDetailSelected = new WarehouseDetail();
        warehouseDetailSelected.setWarehouseId((warehouseSelected != null ? warehouseSelected.getId() : 0));
        warehouseDetailSelected.setProductName(txtProductName.getText().trim());
        warehouseDetailSelected.setQuantity(Untils.parseToInt(spnQuantity.getValue().toString()));
        warehouseDetailSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        warehouseDetailSelected.setAmount(CalcAmount());
        if (warehouseDetailDAO.addWarehouseDetail(warehouseDetailSelected)) {
            // Cập nhật lại thông tin mới từ database.
            Warehouse newWarehouse = warehouseDAO.getWarehouseByID(warehouseDetailSelected.getWarehouseId());
            int index = 0;
            if (warehouseSelected != null) {
                index = warehouseList.indexOf(warehouseSelected);
                warehouseList.set(index, newWarehouse);
            } else {
                warehouseList.add(newWarehouse);
                index = warehouseList.size() - 1;
            }
            loadWarehouse();
            // Chọn lại data của Warehouse.
            tblWarehouse.setRowSelectionInterval(index, index);
            tblWarehouse.scrollRectToVisible(tblWarehouse.getCellRect(index, 0, true));
            JOptionPane.showMessageDialog(null, "Thêm thành công !", "Add", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (warehouseSelected == null || warehouseDetailSelected == null || !checkInputWahouseDetail()) {
            return;
        }
        warehouseDetailSelected.setProductName(txtProductName.getText().trim());
        warehouseDetailSelected.setQuantity(Untils.parseToInt(spnQuantity.getValue().toString()));
        warehouseDetailSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        warehouseDetailSelected.setAmount(CalcAmount());
        if (warehouseDetailDAO.updateWarehouseDetail(warehouseDetailSelected)) {
            // Cập nhật lại thông tin mới từ database.
            Warehouse newWarehouse = warehouseDAO.getWarehouseByID(warehouseDetailSelected.getWarehouseId());
            int index = warehouseList.indexOf(warehouseSelected);
            warehouseList.set(index, newWarehouse);
            loadWarehouse();
            // Cọn lại data của Warehouse.
            tblWarehouse.setRowSelectionInterval(index, index);
            tblWarehouse.scrollRectToVisible(tblWarehouse.getCellRect(index, 0, true));
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
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
            tblWarehouse.setRowSelectionInterval(index, index);
            tblWarehouse.scrollRectToVisible(tblWarehouse.getCellRect(index, 0, true));
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        getDataWarehouse();
        loadWarehouse();
        modelTableDetail.setRowCount(0);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tblWarehouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblWarehouseMouseClicked
        int selectedCol = tblWarehouse.getSelectedColumn();
        if (selectedCol == 3 && warehouseSelected != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa dữ liệu này không ?", "Xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (warehouseDAO.deleteWarehouse(warehouseSelected.getId())) {
                    warehouseList.remove(warehouseSelected);
                    loadWarehouse();
                }
            }
        }
    }//GEN-LAST:event_tblWarehouseMouseClicked

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        String text = txtPrice.getText().trim();
        if (!text.isEmpty()) {
            int value = Untils.parseMoneyI(text);
            txtPrice.setText(Untils.formatMoney(value));
        }
        lblAmount.setText(Untils.formatMoney(CalcAmount()));
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        String text = txtPrice.getText().trim();
        if (!text.isEmpty()) {
            txtPrice.setText(String.valueOf(Untils.parseMoneyI(text)));
        }
    }//GEN-LAST:event_txtPriceFocusGained

    private void spnQuantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnQuantityStateChanged
        lblAmount.setText(Untils.formatMoney(CalcAmount()));
    }//GEN-LAST:event_spnQuantityStateChanged


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
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tblWarehouse;
    private javax.swing.JTable tblWarehouseDetail;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    // End of variables declaration//GEN-END:variables
}
