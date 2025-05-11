/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.CustomerDAO;
import DAO.CustomerTierDAO;
import DAO.Impl.CustomerDAOImpl;
import DAO.Impl.CustomerTierImpl;
import Model.Customer;
import Model.CustomerTier;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DZUNG
 */
public class CustomerManage extends javax.swing.JPanel {

    private final CustomerDAO customerDAO;
    private final CustomerTierDAO customerTierDAO;
    private final DefaultTableModel modelTable;
    private ArrayList<Customer> customerList;
    private Customer customerSelected;
    
    /**
     * Creates new form Promotion
     */
    public CustomerManage() {
        customerDAO = new CustomerDAOImpl();
        customerTierDAO = new CustomerTierImpl();
        initComponents();
        Untils.setMaxLength(txtPhone, 12);
        Untils.setMaxLength(txtSearchPhone, 12);
        modelTable = (DefaultTableModel) tblCustomer.getModel();
        // Tạo sự kiện khi SelectedRow của JTable thay đổi giá trị.
        tblCustomer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblCustomer.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                }
                else {
                    if (tblCustomer.getValueAt(selectedRow, 0) instanceof Customer customer) {
                        customerSelected = customer;
                        txtName.setText(customer.getName());
                        txtPhone.setText(customer.getPhone());
                        txtEmail.setText(customer.getEmail());
                        int index = 0;
                        for (int i = 0; i < ccbTier.getItemCount(); i++) {
                            CustomerTier item = ccbTier.getItemAt(i);
                            if (item.getId() == customer.getTierId()) {
                                index = i;
                                break;
                            }
                        }
                        ccbTier.setSelectedIndex(index);
                        lblDiscountPercentage.setText(String.valueOf(customer.getDiscountPercentage()));
                        btnAdd.setEnabled(false);
                        btnUpdate.setEnabled(true);
                        btnDelete.setEnabled(true);
                    }
                }
            }
        });
        setCombobox();
        getDataCustomers();
        loadCustomer();
    }
    
    private void setCombobox(){
        ArrayList<CustomerTier> customerTierList = customerTierDAO.getAllCustomerTier();
        ccbSearchTier.removeAllItems();
        ccbTier.removeAllItems();
        ccbSearchTier.addItem(new CustomerTier(0, "", 0, 0));
        ccbTier.addItem(new CustomerTier(0, "", 0, 0));
        for (CustomerTier customerTier : customerTierList) {
            ccbSearchTier.addItem(customerTier);
            ccbTier.addItem(customerTier);
        }
    }
    
    private void getDataCustomers(){
        customerList = customerDAO.getAllCustomers();
    }
    
    private void loadCustomer(){
        clear();
        modelTable.setRowCount(0);
        for (Customer customer : customerList) {
            Object[] row = new Object[5];
            row[0] = customer;
            row[1] = customer.getPhone();
            row[2] = customer.getEmail();
            row[3] = customer.getTierName();
            row[4] = customer.getDiscountPercentage();
            modelTable.addRow(row);
        }
    }
    
    private List<Customer> search(String name, String phone, CustomerTier customerTier) {
        List<Customer> cusList = customerList.stream()
                                            .filter(cus -> name.length() == 0 || (cus.getName() != null ? cus.getName().toLowerCase().contains(name.toLowerCase()) : false))
                                            .filter(cus -> phone.length() == 0 || (cus.getPhone() != null ? cus.getPhone().contains(phone) : false))
                                            .filter(cus -> customerTier.getId() == 0 || cus.getTierId() == customerTier.getId())
                                            .toList();
        return cusList;
    }
    
    private boolean checkInputCustomer(){
        if (txtPhone.getText().trim().length() > 0 && !Untils.validatePhoneNumber(txtPhone)) {
            return false;
        }
        if (txtEmail.getText().trim().length() > 0 && !Untils.validateEmail(txtEmail)) {
            return false;
        }
        CustomerTier customerTier = (CustomerTier) ccbTier.getSelectedItem();
        if (customerTier.getId() == 0) {
            JOptionPane.showMessageDialog(null, "Vui chọn loại khách hàng !", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void clear(){
        customerSelected = null;
        tblCustomer.clearSelection();
        txtName.setText(Constants.STR_EMPTY);
        txtPhone.setText(Constants.STR_EMPTY);
        txtEmail.setText(Constants.STR_EMPTY);
        ccbTier.setSelectedIndex(0);
        lblDiscountPercentage.setText("0");
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        ccbTier = new javax.swing.JComboBox<>();
        lblDiscountPercentage = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSearchName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchPhone = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ccbSearchTier = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setText("Email:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Giảm Giá (%)");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 255));
        jLabel4.setText("Tên:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 255));
        jLabel5.setText("Loại KH:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 0, 255));
        jLabel6.setText("SĐT:");

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

        btnRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên", "SĐT", "Email", "Loại KH", "Giảm Giá (%)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCustomer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCustomer);

        txtPhone.setName("SĐT"); // NOI18N
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });

        txtEmail.setName("Email"); // NOI18N

        txtName.setName("Tên"); // NOI18N

        ccbTier.setModel(new javax.swing.DefaultComboBoxModel<>(new CustomerTier[] { new CustomerTier(0, "", 0, 0) }));
        ccbTier.setName("Loại KH"); // NOI18N
        ccbTier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbTierItemStateChanged(evt);
            }
        });

        lblDiscountPercentage.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblDiscountPercentage.setText("0");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 102));
        jLabel7.setText("QUẢN LÝ KHÁCH HÀNG");

        txtSearchName.setName("Tìm kiếm tên"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 255));
        jLabel8.setText("Tên:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 0, 255));
        jLabel9.setText("SĐT:");

        txtSearchPhone.setName("SĐT tìm kiếm"); // NOI18N
        txtSearchPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchPhoneKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 0, 255));
        jLabel10.setText("Loại KH:");

        ccbSearchTier.setModel(new javax.swing.DefaultComboBoxModel<>(new CustomerTier[] { new CustomerTier(0, "", 0, 0) }));
        ccbSearchTier.setName("Tìm kiếm loại KH"); // NOI18N

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/market_analysis.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(ccbTier, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(lblDiscountPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(372, 372, 372))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ccbSearchTier, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate)
                                .addGap(188, 188, 188)
                                .addComponent(btnDelete)
                                .addGap(164, 164, 164)
                                .addComponent(btnRefresh)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ccbSearchTier)
                    .addComponent(btnSearch))
                .addGap(18, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(ccbTier)
                    .addComponent(lblDiscountPercentage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (customerSelected != null || !checkInputCustomer()) {
            return;
        }
        customerSelected = new Customer();
        customerSelected.setName(txtName.getText().trim());
        customerSelected.setPhone(txtPhone.getText().trim());
        customerSelected.setEmail(txtEmail.getText().trim());
        customerSelected.setTierId(((CustomerTier) ccbTier.getSelectedItem()).getId());
        if (customerDAO.addCustomer(customerSelected)) {
            getDataCustomers();
            loadCustomer();
            JOptionPane.showMessageDialog(null, "Thêm thành công !", "Add", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (customerSelected == null || !checkInputCustomer()) {
            return;
        }
        customerSelected.setName(txtName.getText().trim());
        customerSelected.setPhone(txtPhone.getText().trim());
        customerSelected.setEmail(txtEmail.getText().trim());
        customerSelected.setTierId(((CustomerTier) ccbTier.getSelectedItem()).getId());
        if (customerDAO.updateCustomer(customerSelected)) {
            // Cập nhật lại thông tin mới từ database.
            Customer newCustomer = customerDAO.getCustomerByID(customerSelected.getId());
            int index = customerList.indexOf(customerSelected);
            customerList.set(index, newCustomer);
            loadCustomer();
            // Cọn lại data của Warehouse.
            tblCustomer.setRowSelectionInterval(index, index);
            tblCustomer.scrollRectToVisible(tblCustomer.getCellRect(index, 0, true));
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (customerSelected == null) {
            return;
        }
        if (customerDAO.deleteCustomer(customerSelected.getId())) {
            customerList.remove(customerSelected);
            loadCustomer();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        clear();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPhoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSearchPhoneKeyTyped

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void ccbTierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbTierItemStateChanged
        CustomerTier customerTier = (CustomerTier) ccbTier.getSelectedItem();
        if (customerTier == null || customerTier.getId() == 0) {
            lblDiscountPercentage.setText("0");
        } else {
            lblDiscountPercentage.setText(String.valueOf(customerTier.getDiscountPercantage()));
        }
    }//GEN-LAST:event_ccbTierItemStateChanged

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String name = txtSearchName.getText().trim();
        String phone = txtSearchPhone.getText().trim();
        CustomerTier customerTier = (CustomerTier) ccbSearchTier.getSelectedItem();
        List<Customer> customerListFilter = search(name, phone, customerTier);
        clear();
        modelTable.setRowCount(0);
        for (Customer customer : customerListFilter) {
            Object[] row = new Object[5];
            row[0] = customer;
            row[1] = customer.getPhone();
            row[2] = customer.getEmail();
            row[3] = customer.getTierName();
            row[4] = customer.getDiscountPercentage();
            modelTable.addRow(row);
        }
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<CustomerTier> ccbSearchTier;
    private javax.swing.JComboBox<CustomerTier> ccbTier;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiscountPercentage;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchName;
    private javax.swing.JTextField txtSearchPhone;
    // End of variables declaration//GEN-END:variables
}
