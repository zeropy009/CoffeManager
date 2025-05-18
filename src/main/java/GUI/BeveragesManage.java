/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.BeveragesCategoryDAO;
import DAO.BeveragesDAO;
import DAO.Impl.BeveragesCategoryImpl;
import DAO.Impl.BeveragesImpl;
import Model.Beverages;
import Model.BeveragesCategory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author zero
 */
public class BeveragesManage extends javax.swing.JPanel {
    
    private final BeveragesCategoryDAO beveragesCategoryDAO;
    private final BeveragesDAO beveragesDAO;
    private final DefaultTreeModel modelTree;
    private final DefaultMutableTreeNode root;
    private final DefaultTableModel modelTable;
    private ArrayList<Beverages> beveragesList;
    private ArrayList<BeveragesCategory> beveragesCategoryList;
    private Beverages beveragesSelected;
    private BeveragesCategory beveragesCategorySelected;

    /**
     * Creates new form BeveragesManage
     */
    public BeveragesManage() {
        beveragesCategoryDAO = new BeveragesCategoryImpl();
        beveragesDAO = new BeveragesImpl();
        initComponents();
        modelTable = (DefaultTableModel) tblBeverages.getModel();
        // Tạo sự kiện khi SelectedRow của JTable thay đổi giá trị.
        tblBeverages.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblBeverages.getSelectedRow();
                if (selectedRow == -1) {
                    clearBevetages();
                }
                else {
                    if (tblBeverages.getValueAt(selectedRow, 0) instanceof Beverages b) {
                        beveragesSelected = b;
                        txtBeveragesName.setText(b.getName());
                        int index = 0;
                        for (int i = 0; i < ccbBeveragesCategory.getItemCount(); i++) {
                            BeveragesCategory item = ccbBeveragesCategory.getItemAt(i);
                            if (item.getId() == b.getBaveragesCategoryId()) {
                                index = i;
                                break;
                            }
                        }
                        ccbBeveragesCategory.setSelectedIndex(index);
                        txtPrice.setText(Untils.formatMoney(b.getPrice()));
                        btnAddBeverages.setEnabled(false);
                        btnUpdateBeverages.setEnabled(true);
                        btnDeleteBeverages.setEnabled(true);
                    }
                }
            }
        });
        root = (DefaultMutableTreeNode) categoryTree.getModel().getRoot();
        modelTree = (DefaultTreeModel) categoryTree.getModel();
        getDataBeveragesCategory();
        getDataBeverages();
        loadBeveragesCategory();
    }
    
    /**
    * Lấy dữ liệu BeveragesCategory vào beveragesCategoryList.
    * 
    */
    private void getDataBeveragesCategory(){
        beveragesCategoryList = beveragesCategoryDAO.getAllBeveragesCategory();
    }
    
    /**
    * Lấy dữ liệu BeveragesManage vào beveragesList.
    * 
    */
    private void getDataBeverages(){
        beveragesList = beveragesDAO.getAllBeverages();
    }
    
    /**
    * Đổ dữ liệu BeveragesCategory cho JTree và Combobox ccbBeveragesCategory.
    * 
    */
    private void loadBeveragesCategory (){
        clearCategory();
        ccbBeveragesCategory.removeAllItems();
        while (root.getChildCount() != 0) {
            root.remove(root.getChildCount() - 1);
        }
        //default value
        ccbBeveragesCategory.addItem(new BeveragesCategory(0, Constants.STR_EMPTY));
        for (BeveragesCategory category : beveragesCategoryList) {
            ccbBeveragesCategory.addItem(category);
            DefaultMutableTreeNode parent = new DefaultMutableTreeNode(category);
            root.add(parent);
        }
        modelTree.reload(root);
    }
    
    /**
    * Đổ dữ liệu BeveragesManage cho JTable.
    * 
    */
    private void loadBeverages(){
        clearBevetages();
        modelTable.setRowCount(0);
        for (Beverages b : beveragesList) {
            Object[] row = new Object[3];
            row[0] = b;
            row[1] = b.getBaveragesCategoryName();
            row[2] = Untils.formatMoney(b.getPrice());
            modelTable.addRow(row);
        }
    }
    
    
    /**
    * Đổ lại dữ liệu cho JTable (có lọc theo loại).
    * 
    * @param beveragesCategoryId Id của loại nước uống.
    */
    private void loadBeverages(int beveragesCategoryId){
        clearBevetages();
        modelTable.setRowCount(0);
        List<Beverages> newbeveragesList = beveragesList.stream().filter(beverage -> beverage.getBaveragesCategoryId() == beveragesCategoryId).toList();       
        for (Beverages b : newbeveragesList) {
            Object[] row = new Object[3];
            row[0] = b;
            row[1] = b.getBaveragesCategoryName();
            row[2] = Untils.formatMoney(b.getPrice());
            modelTable.addRow(row);
        }
    }
    
    /**
    * Kiểm tra dữ liệu người dùng nhập trước khi cập nhật vào kho dữ liệu.
    * 
    * @return Trạng thái nhập đã hơp lệ True hoặc chưa hợp lệ False.
    */
    private boolean checkInputBeveragesCategory(){
        return Untils.validateText(txtCategoryName);
    }
    
    private boolean checkInputBeverages(){
        if (!Untils.validateText(txtBeveragesName)) {
            return false;
        }
        if (ccbBeveragesCategory.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn loại nước !", "Bắt buộc nhập", JOptionPane.WARNING_MESSAGE);
            ccbBeveragesCategory.requestFocus();
            return false;
        }
        if (txtPrice.getText().trim().length() == 0 || Untils.parseMoneyI(txtPrice.getText().trim()) == 0) {
            JOptionPane.showMessageDialog(null, "Vui đặt giá tiền lớn hơn 0 !", "Bắt buộc nhập", JOptionPane.WARNING_MESSAGE);
            txtPrice.requestFocus();
            return false;
        } 
        return true;
    }
    
    /**
    * Trả các thông tin trên màn hình về mặc định và bỏ chọn ở JTable, JTree.
    * 
    */
    private void clearCategory(){
        beveragesCategorySelected = null;
        categoryTree.clearSelection();
        txtCategoryName.setText(Constants.STR_EMPTY);
        btnAddCategory.setEnabled(true);
        btnUpdateCategory.setEnabled(false);
        btnDeleteCategory.setEnabled(false);
        loadBeverages();
    }
    
    /**
    * Trả các thông tin của nước uống trên màn hình về mặc định và bỏ chọn ở JTable.
    * 
    */
    private void clearBevetages(){
        beveragesSelected = null;
        tblBeverages.clearSelection();
        txtBeveragesName.setText(Constants.STR_EMPTY);
        ccbBeveragesCategory.setSelectedIndex(0);
        txtPrice.setText(Constants.STR_EMPTY);
        btnAddBeverages.setEnabled(true);
        btnUpdateBeverages.setEnabled(false);
        btnDeleteBeverages.setEnabled(false);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBeverages = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        txtBeveragesName = new javax.swing.JTextField();
        btnAddBeverages = new javax.swing.JButton();
        btnUpdateBeverages = new javax.swing.JButton();
        btnDeleteBeverages = new javax.swing.JButton();
        btnRefreshBeverages = new javax.swing.JButton();
        btnAddCategory = new javax.swing.JButton();
        btnCategoryRefresh = new javax.swing.JButton();
        btnUpdateCategory = new javax.swing.JButton();
        btnDeleteCategory = new javax.swing.JButton();
        txtCategoryName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ccbBeveragesCategory = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        categoryTree = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();

        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setText("Loại nước");

        tblBeverages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN NƯỚC", "TÊN LOẠI NƯỚC", "ĐƠN GIÁ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBeverages.setToolTipText("");
        tblBeverages.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblBeverages.setOpaque(false);
        jScrollPane1.setViewportView(tblBeverages);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Tên nước");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 255));
        jLabel4.setText("Giá");

        txtPrice.setName("Giá"); // NOI18N
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

        txtBeveragesName.setName("Tên nước"); // NOI18N

        btnAddBeverages.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAddBeverages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAddBeverages.setText("Add");
        btnAddBeverages.setName(""); // NOI18N
        btnAddBeverages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBeveragesActionPerformed(evt);
            }
        });

        btnUpdateBeverages.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdateBeverages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdateBeverages.setText("Edit");
        btnUpdateBeverages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBeveragesActionPerformed(evt);
            }
        });

        btnDeleteBeverages.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDeleteBeverages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDeleteBeverages.setText("Delete");
        btnDeleteBeverages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBeveragesActionPerformed(evt);
            }
        });

        btnRefreshBeverages.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefreshBeverages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefreshBeverages.setText("Refresh");
        btnRefreshBeverages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshBeveragesActionPerformed(evt);
            }
        });

        btnAddCategory.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAddCategory.setText("Add");
        btnAddCategory.setName(""); // NOI18N
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });

        btnCategoryRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCategoryRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnCategoryRefresh.setText("Refresh");
        btnCategoryRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryRefreshActionPerformed(evt);
            }
        });

        btnUpdateCategory.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdateCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdateCategory.setText("Edit");
        btnUpdateCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCategoryActionPerformed(evt);
            }
        });

        btnDeleteCategory.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDeleteCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDeleteCategory.setText("Delete");
        btnDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCategoryActionPerformed(evt);
            }
        });

        txtCategoryName.setName("Tên loại nước"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 255));
        jLabel5.setText("Tên loại nước");

        ccbBeveragesCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new BeveragesCategory[] {new BeveragesCategory(0, "")}));
        ccbBeveragesCategory.setName("Loại nước"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Danh sách loại nước");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("colors");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sports");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("food");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("pizza");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ravioli");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("bananas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        categoryTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        categoryTree.setOpaque(false);
        categoryTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                categoryTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(categoryTree);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 102));
        jLabel1.setText("QUẢN LÝ NƯỚC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnDeleteCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnUpdateCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnCategoryRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 76, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(109, 109, 109))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBeveragesName, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(btnAddBeverages)
                                .addGap(121, 121, 121)
                                .addComponent(btnUpdateBeverages)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ccbBeveragesCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnDeleteBeverages)
                                .addGap(55, 55, 55)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRefreshBeverages)
                                .addGap(109, 109, 109))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddCategory)
                            .addComponent(btnUpdateCategory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteCategory)
                            .addComponent(btnCategoryRefresh)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBeveragesName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ccbBeveragesCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefreshBeverages)
                    .addComponent(btnAddBeverages)
                    .addComponent(btnDeleteBeverages)
                    .addComponent(btnUpdateBeverages))
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddBeveragesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBeveragesActionPerformed
        if (beveragesSelected != null || !checkInputBeverages()) {
            return;
        }
        beveragesSelected = new Beverages();
        beveragesSelected.setName(txtBeveragesName.getText().trim());
        beveragesSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        beveragesSelected.setBaveragesCategoryId(((BeveragesCategory)ccbBeveragesCategory.getSelectedItem()).getId());
        if (beveragesDAO.addBeverages(beveragesSelected)) {
            getDataBeverages();
            if (beveragesCategorySelected != null) {
                loadBeverages(beveragesCategorySelected.getId());
            } else {
                loadBeverages();
            }
            JOptionPane.showMessageDialog(null, "Thêm thành công !", "Add", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddBeveragesActionPerformed

    private void btnUpdateBeveragesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBeveragesActionPerformed
        if (beveragesSelected == null || !checkInputBeverages()) {
            return;
        }
        beveragesSelected.setName(txtBeveragesName.getText().trim());
        beveragesSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        beveragesSelected.setBaveragesCategoryId(((BeveragesCategory)ccbBeveragesCategory.getSelectedItem()).getId());
        if (beveragesDAO.updateBeverages(beveragesSelected)) {
            getDataBeverages();
            if (beveragesCategorySelected != null) {
                loadBeverages(beveragesCategorySelected.getId());
            } else {
                loadBeverages();
            }
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);  
        }
    }//GEN-LAST:event_btnUpdateBeveragesActionPerformed

    private void btnDeleteBeveragesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBeveragesActionPerformed
        if (beveragesSelected == null) {
            return;
        }
        if (beveragesDAO.deleteBeverages(beveragesSelected.getId())) {
            getDataBeverages();
            if (beveragesCategorySelected != null) {
                loadBeverages(beveragesCategorySelected.getId());
            } else {
                loadBeverages();
            }
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);  
        }
    }//GEN-LAST:event_btnDeleteBeveragesActionPerformed

    private void btnRefreshBeveragesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshBeveragesActionPerformed
        clearBevetages();
    }//GEN-LAST:event_btnRefreshBeveragesActionPerformed

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        if (beveragesCategorySelected != null || !checkInputBeveragesCategory()) {
            return;
        }
        beveragesCategorySelected = new BeveragesCategory();
        beveragesCategorySelected.setName(txtCategoryName.getText().trim());
        if (beveragesCategoryDAO.addBeveragesCategory(beveragesCategorySelected)) {
            getDataBeveragesCategory();
            loadBeveragesCategory();
            JOptionPane.showMessageDialog(null, "Thêm thành công !", "Add", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnCategoryRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryRefreshActionPerformed
        getDataBeveragesCategory();
        getDataBeverages();
        loadBeveragesCategory();
    }//GEN-LAST:event_btnCategoryRefreshActionPerformed

    private void btnUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCategoryActionPerformed
        if (beveragesCategorySelected == null || !checkInputBeveragesCategory()) {
            return;
        }
        beveragesCategorySelected.setName(txtCategoryName.getText().trim());
        if (beveragesCategoryDAO.updateBeveragesCategory(beveragesCategorySelected)) {
            getDataBeveragesCategory();
            getDataBeverages();
            loadBeveragesCategory();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateCategoryActionPerformed

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCategoryActionPerformed
        if (beveragesCategorySelected == null) {
            return;
        }
        if (modelTable.getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "Không thể xóa vì tồn tại nước của loại này trong menu.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (beveragesCategoryDAO.deleteBeveragesCategory(beveragesCategorySelected.getId())) {
            getDataBeveragesCategory();
            getDataBeverages();
            loadBeveragesCategory();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteCategoryActionPerformed

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        String text = txtPrice.getText().trim();
        txtPrice.setText(String.valueOf(Untils.parseMoneyI(text)));
    }//GEN-LAST:event_txtPriceFocusGained

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        try {
            String text = txtPrice.getText().trim();
            if (!text.isEmpty()) {
                int value = Untils.parseMoneyI(text);
                txtPrice.setText(Untils.formatMoney(value));
            }
        } catch (NumberFormatException ex) {
            txtPrice.setText("0");
        }
    }//GEN-LAST:event_txtPriceFocusLost

    private void categoryTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_categoryTreeValueChanged
        if (root.getChildCount() == 0 || categoryTree.getSelectionPath() == null) {
            clearCategory();
            return;
        }
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();
        if (selectedNode == root) {
            clearCategory();
            return;
        }
        Object selectObject = selectedNode.getUserObject();
        if (selectObject instanceof BeveragesCategory category) {
            beveragesCategorySelected = category;
            txtCategoryName.setText(category.getName());
            btnAddCategory.setEnabled(false);
            btnUpdateCategory.setEnabled(true);
            btnDeleteCategory.setEnabled(true);
            loadBeverages(category.getId());
        }
    }//GEN-LAST:event_categoryTreeValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBeverages;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnCategoryRefresh;
    private javax.swing.JButton btnDeleteBeverages;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnRefreshBeverages;
    private javax.swing.JButton btnUpdateBeverages;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JTree categoryTree;
    private javax.swing.JComboBox<BeveragesCategory> ccbBeveragesCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBeverages;
    private javax.swing.JTextField txtBeveragesName;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
