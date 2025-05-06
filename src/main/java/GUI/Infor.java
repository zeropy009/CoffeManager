/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.Impl.UserDaoImpl;
import DAO.UserDAO;
import Enums.Roles;
import Enums.Sex;
import Model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DZUNG
 */
public class Infor extends javax.swing.JPanel {
    
    private final UserDAO userDAO;
    private final DefaultTableModel modelTable;
    private final int currentYear = LocalDate.now().getYear();
    private ArrayList<User> userList; 
    private User userSelected;

    /**
     * Creates new form StaffManage
     */
    public Infor() {
        userDAO = new UserDaoImpl();
        initComponents();
        Untils.setMaxLength(txtYearOfBirth, 4);
        Untils.setMaxLength(txtPhone, 12);
        modelTable = (DefaultTableModel) tblUser.getModel();
        tblUser.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblUser.getSelectedRow();
                if (selectedRow == -1) {
                    clear();
                }
                else {
                    if (tblUser.getValueAt(selectedRow, 0) instanceof User u) {
                        userSelected = u;
                        txtUserName.setText(u.getUserName());
                        txtFullName.setText(u.getFullName());
                        ccbRole.setSelectedItem(u.getRole());
                        if (u.getSex() == Sex.MALE){
                            rdMale.setSelected(true);
                        }
                        else {
                            rdFemale.setSelected(true);
                        }
                        txtAddress.setText(u.getAddress());
                        txtYearOfBirth.setText(String.valueOf(u.getYearOfBirth()));
                        txtPhone.setText(u.getPhone());
                        txtEmail.setText(u.getEmail());
                        txtSalary.setText(Untils.formatMoney(u.getSalary()));
                        btnAdd.setEnabled(false);
                        btnUpdate.setEnabled(true);
                        btnDelete.setEnabled(true);
                    }
                }
            }
        });
        loadUsers();
    }
    
    /**
    * Lấy dữ liệu Beverages và đổ dữ liệu cho JTable.
    * 
    */
    private void loadUsers(){
        modelTable.setRowCount(0);
        userList = userDAO.getAllUsers();
        for (User u : userList) {
            Object[] row = new Object[9];
            row[0] = u;
            row[1] = u.getFullName();
            row[2] = u.getRole();
            row[3] = u.getSex();
            row[4] = u.getAddress();
            row[5] = u.getYearOfBirth();
            row[6] = u.getPhone();
            row[7] = u.getEmail();
            row[8] = Untils.formatMoney(u.getSalary());
            modelTable.addRow(row);
        }
        clear();
    }
    
    /**
    * Kiểm tra dữ liệu người dùng nhập trước khi cập nhật vào kho dữ liệu.
    * 
    * @return Trạng thái nhập đã hơp lệ True hoặc chưa hợp lệ False.
    */
    private boolean checkInputUser(){
        if (!Untils.validateText(txtUserName)) {
            return false;
        }
        // Kiểm tra userName đã dùng chưa với trường hợp add mới và trường hợp thay đổi userName khi update
        else if (userSelected == null || !userSelected.getUserName().equals(txtUserName.getText().trim())) {
            if (userDAO.getUserByUserName(txtUserName.getText().trim()) != null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tên tài khoản khác !", "Trùng tên tài khoản", JOptionPane.WARNING_MESSAGE);
                txtUserName.requestFocus();
                txtUserName.selectAll();
                return false;
            }
        }
        if (!rdFemale.isSelected() && !rdMale.isSelected()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính !", "Bắt buộc nhập", JOptionPane.WARNING_MESSAGE);
            rdFemale.requestFocus();
            return false;
        }
        if (txtYearOfBirth.getText().trim().length() > 0) {
            int birthYear = Untils.parseToInt(txtYearOfBirth.getText());
            if (birthYear < 1900 || birthYear >= currentYear) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập năm sinh lớn hơn 1900 và bé hơn năm hiện tại !", "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtYearOfBirth.requestFocus();
                return false;
            }
        }
        if (txtPhone.getText().trim().length() > 0 && !Untils.validatePhoneNumber(txtPhone)) {
            return false;
        }
        return !(txtEmail.getText().trim().length() > 0 && !Untils.validateEmail(txtEmail));
    }
    
    /**
    * Trả các thông tin trên màn hình về mặc định và bỏ chọn ở JTable.
    * 
    */
    private void clear(){
        userSelected = null;
        tblUser.clearSelection();
        txtUserName.setText(Constants.STR_EMPTY);
        txtFullName.setText(Constants.STR_EMPTY);
        ccbRole.setSelectedItem(Roles.STAFF);
        buttonGroup1.clearSelection();
        txtAddress.setText(Constants.STR_EMPTY);
        txtYearOfBirth.setText(Constants.STR_EMPTY);
        txtPhone.setText(Constants.STR_EMPTY);
        txtEmail.setText(Constants.STR_EMPTY);
        txtSalary.setText(Constants.STR_EMPTY);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtYearOfBirth = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        rdFemale = new javax.swing.JRadioButton();
        rdMale = new javax.swing.JRadioButton();
        lblUserName = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(967, 640));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("THÔNG TIN CÁ NHÂN");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 204));
        jLabel4.setText("Tên TK:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 204));
        jLabel6.setText("Tên:");

        txtFullName.setName("Tên"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 255, 204));
        jLabel7.setText("Giới Tính:");

        txtYearOfBirth.setName("Năm Sinh"); // NOI18N
        txtYearOfBirth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtYearOfBirthKeyTyped(evt);
            }
        });

        txtPhone.setName("SĐT"); // NOI18N
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 255, 204));
        jLabel9.setText("Năm Sinh:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 255, 204));
        jLabel10.setText("SĐT:");

        txtEmail.setName("Email"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 255, 204));
        jLabel11.setText("Email:");

        btnRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 255, 204));
        jLabel12.setText("Địa Chỉ:");

        txtAddress.setName("Địa chỉ"); // NOI18N

        buttonGroup1.add(rdFemale);
        rdFemale.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rdFemale.setForeground(new java.awt.Color(0, 255, 204));
        rdFemale.setText("Nữ");

        buttonGroup1.add(rdMale);
        rdMale.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rdMale.setForeground(new java.awt.Color(0, 255, 204));
        rdMale.setText("Nam");

        lblUserName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblUserName.setText("lblUserName");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(rdMale))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                        .addComponent(lblUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addComponent(txtYearOfBirth)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel12)
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRefresh))
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(txtYearOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdFemale)
                    .addComponent(rdMale)
                    .addComponent(jLabel11)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnRefresh))
                .addGap(186, 186, 186))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (userSelected == null || !checkInputUser()) {
            return;
        }
        userSelected.setUserName(txtUserName.getText().trim());
        userSelected.setFullName(txtFullName.getText().trim());
        userSelected.setRole((Roles) ccbRole.getSelectedItem());
        if (rdMale.isSelected()) {
            userSelected.setSex(Sex.MALE);
        } else {
            userSelected.setSex(Sex.FEMALE);
        }
        userSelected.setAddress(txtAddress.getText().trim());
        userSelected.setYearOfBirth(Untils.parseToInt(txtYearOfBirth.getText().trim()));
        userSelected.setPhone(txtPhone.getText().trim());
        userSelected.setEmail(txtEmail.getText().trim());
        userSelected.setSalary(Untils.parseMoneyI(txtSalary.getText().trim()));
        if (userDAO.updateUser(userSelected)) {
            loadUsers();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtYearOfBirthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYearOfBirthKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtYearOfBirthKeyTyped

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        clear();
    }//GEN-LAST:event_btnRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JRadioButton rdFemale;
    private javax.swing.JRadioButton rdMale;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtYearOfBirth;
    // End of variables declaration//GEN-END:variables
}