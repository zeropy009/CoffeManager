/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.BeveragesDAO;
import DAO.Impl.BeveragesImpl;
import DAO.Impl.InvoiceDetailImpl;
import DAO.Impl.InvoiceImpl;
import DAO.Impl.TableImpl;
import DAO.InvoiceDAO;
import DAO.InvoiceDetailDAO;
import DAO.TableDAO;
import Model.Beverages;
import Model.Invoice;
import Model.InvoiceDetail;
import Model.Table;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zero
 */
public class InvoiceManage extends javax.swing.JPanel {

    private final TableDAO tableDAO;
    private final BeveragesDAO beveragesDAO;
    private final InvoiceDAO invoiceDAO;
    private final InvoiceDetailDAO invoiceDetailDAO;
    private final DefaultTableModel modelTable;
    private ArrayList<InvoiceDetail> invoiceDetailList;
    private Invoice invoiceSelected;
    private InvoiceDetail invoiceDetailSelected;
    private boolean isInvalid = false;
    
    /**
     * Creates new form InvoiceManage
     */
    public InvoiceManage() {
        tableDAO = new TableImpl();
        beveragesDAO = new BeveragesImpl();
        invoiceDAO = new InvoiceImpl();
        invoiceDetailDAO = new InvoiceDetailImpl();
        initComponents();
        Untils.setMaxLength(txtDate, 10);
        modelTable = (DefaultTableModel) tblInvoiceDetail.getModel();
        tblInvoiceDetail.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblInvoiceDetail.getSelectedRow();
                if (selectedRow == -1) {
                    clearDetail();
                }
                else {
                    if (tblInvoiceDetail.getValueAt(selectedRow, 0) instanceof InvoiceDetail b) {
                        invoiceDetailSelected = b;
                        ccbBeverages.setSelectedIndex(0);
                        for (int i = 0; i < ccbBeverages.getItemCount(); i++) {
                            if (ccbBeverages.getItemAt(i).getId() == b.getBeveragesId()) {
                                ccbBeverages.setSelectedIndex(i);
                                break;
                            }
                        }
                        txtPrice.setText(Untils.formatMoney(b.getPrice()));
                        spnQuantity.setValue(b.getQuantity());
                        lblAmount.setText(Untils.formatMoney(b.getAmount()));
                        btnAddDetail.setEnabled(false);
                        btnUpdateDetail.setEnabled(true);
                        btnDeleteDetail.setEnabled(true);
                    }
                }
            }
        });
        setCombobox();
    }
    
    private void setCombobox(){
        ArrayList<Table> tableList = tableDAO.getAllTable();
        ArrayList<Beverages> beveragesesList = beveragesDAO.getAllBeverages();
        for (Table table : tableList) {
            ccbTable.addItem(table);
        }
        for (Beverages beverages : beveragesesList) {
            ccbBeverages.addItem(beverages);
        }
    }
    
    private void getDetail(){
        invoiceDetailList = invoiceDetailDAO.getAllInvoiceDetailsByInvoiceId(invoiceSelected.getId());
    }
    
    private void loadAllData(){
        if (invoiceSelected == null) {
                return;
        }
        txtId.setText(String.valueOf(invoiceSelected.getId()));
        txtDate.setText(invoiceSelected.toString());
        lblUserName.setText(invoiceSelected.getUserName());
        lblCustomerName.setText(invoiceSelected.getCustomerName());
        txtDiscountPercentage.setText(String.valueOf(invoiceSelected.getDiscountPercentage()));
        ccbTable.setSelectedIndex(0);
        for (int i = 0; i < ccbTable.getItemCount(); i++) {
            if (ccbTable.getItemAt(i).getId() == invoiceSelected.getTableId()) {
                ccbTable.setSelectedIndex(i);
                break;
            }
        }
        getDetail();
        loadDetail();
    }
    
    private void loadDetail(){
        clearDetail();
        modelTable.setRowCount(0);
        for (InvoiceDetail detail : invoiceDetailList) {
            Object[] row = new Object[4];
            row[0] = detail;
            row[1] = Untils.formatMoney(detail.getPrice());
            row[2] = detail.getQuantity();
            row[3] = Untils.formatMoney(detail.getAmount());
            modelTable.addRow(row);
        }
    }
    
    private boolean checkInputInvoice(){
        if (!Untils.validateText(txtDate) || !Untils.validateDate(txtDate)) {
            txtDate.requestFocus();
            txtDate.selectAll();
            return false;
        }
        if (txtDiscountPercentage.getText().trim().length() > 0) {
            Double value = Untils.parseMoneyD(txtDiscountPercentage.getText().trim());
            if (value < 0 || value > 100) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập giảm giá trong khoảng 0 - 100 !", "Giới hạn %", JOptionPane.WARNING_MESSAGE);
            }
            txtDiscountPercentage.requestFocus();
            txtDiscountPercentage.selectAll();
            return false;
        }
        return true;
    }
    
    private boolean checkInputDetail(){
        Beverages beverages = (Beverages) ccbBeverages.getSelectedItem();
        if (beverages.getId() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 loại đồ uống !", "Giới hạn %", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!Untils.validateText(txtPrice)) {
            return false;
        }
        int price = Untils.parseMoneyI(txtPrice.getText().trim());
        int quantity = Untils.parseMoneyI(spnQuantity.getValue().toString());
        if (price <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng giá tiền đồ uống lớn hơn 0 !", "Cảnh báo giá trị", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng số lượng lớn hơn 0 !", "Cảnh báo giá trị", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private int CalcAmount(){
        int quantity = Untils.parseToInt(spnQuantity.getValue().toString());
        int price = Untils.parseMoneyI(txtPrice.getText().trim());
        return quantity * price;
    }
    
    private void clearDetail(){
        invoiceDetailSelected = null;
        tblInvoiceDetail.clearSelection();
        txtPrice.setText(Constants.STR_EMPTY);
        spnQuantity.setValue(0);
        lblAmount.setText(Constants.STR_EMPTY);
        btnAddDetail.setEnabled(true);
        btnUpdateDetail.setEnabled(false);
        btnDeleteDetail.setEnabled(false);
    }
    
    private void clearAll(){
        invoiceSelected = null;
        txtId.setText(Constants.STR_EMPTY);
        txtDate.setText(Constants.STR_EMPTY);
        lblUserName.setText(Constants.STR_EMPTY);
        lblCustomerName.setText(Constants.STR_EMPTY);
        txtDiscountPercentage.setText(Constants.STR_EMPTY);
        ccbTable.setSelectedIndex(0);
        modelTable.setRowCount(0);
        clearDetail();
    }
    
    private void actionId() {
        isInvalid = false;
        if (txtId.getText().trim().length() > 0) {
            int id = Untils.parseMoneyI(txtId.getText().trim());
            Invoice invoice = invoiceDAO.getInvoiceById(id);
            if (invoice == null) {
                isInvalid = true;
                clearAll();
                txtId.setText(String.valueOf(id));
                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin hóa đơn !", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
                txtId.requestFocus();
                txtId.selectAll();
            } else {
                invoiceSelected = invoice;
                loadAllData();
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRefresh = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoiceDetail = new javax.swing.JTable();
        btnDeleteDetail = new javax.swing.JButton();
        btnRefreshDetail = new javax.swing.JButton();
        btnAddDetail = new javax.swing.JButton();
        btnUpdateDetail = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDiscountPercentage = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        ccbTable = new javax.swing.JComboBox<>();
        lblCustomerName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ccbBeverages = new javax.swing.JComboBox<>();
        txtPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        spnQuantity = new javax.swing.JSpinner();

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
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
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

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("DANH SÁCH HÓA ĐƠN");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 204));
        jLabel2.setText("Mã hóa đơn:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 204));
        jLabel3.setText("Ngày tạo:");

        txtDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDateKeyTyped(evt);
            }
        });

        txtId.setMinimumSize(new java.awt.Dimension(80, 22));
        txtId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdFocusLost(evt);
            }
        });
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });

        tblInvoiceDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TÊN SẢN PHẨM", "ĐƠN GIÁ", "SỐ LƯỢNG", "THÀNH TIỀN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInvoiceDetail);

        btnDeleteDetail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDeleteDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDeleteDetail.setText("Delete");
        btnDeleteDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDetailActionPerformed(evt);
            }
        });

        btnRefreshDetail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefreshDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh.png"))); // NOI18N
        btnRefreshDetail.setText("Refresh");
        btnRefreshDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshDetailActionPerformed(evt);
            }
        });

        btnAddDetail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAddDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAddDetail.setText("Add");
        btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDetailActionPerformed(evt);
            }
        });

        btnUpdateDetail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdateDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hammer.png"))); // NOI18N
        btnUpdateDetail.setText("Update");
        btnUpdateDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 204));
        jLabel4.setText("Nhân viên tạo:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 255, 204));
        jLabel5.setText("Tên khách hàng:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 204));
        jLabel6.setText("Giảm giá (%):");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 255, 204));
        jLabel7.setText("Tên bàn:");

        lblUserName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblUserName.setText(" ");

        ccbTable.setModel(new javax.swing.DefaultComboBoxModel<>(new Table[] { new Table(0, "", false) }));

        lblCustomerName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblCustomerName.setText(" ");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 255, 204));
        jLabel9.setText("Tên sản phẩm:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 255, 204));
        jLabel10.setText("Đơn giá:");

        ccbBeverages.setModel(new javax.swing.DefaultComboBoxModel<>(new Beverages[] { new Beverages("") }));
        ccbBeverages.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbBeveragesItemStateChanged(evt);
            }
        });

        txtPrice.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
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

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 255, 204));
        jLabel11.setText("Số lượng:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 255, 204));
        jLabel12.setText("Thành tiền:");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDiscountPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addComponent(jLabel4))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(149, 149, 149)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(btnDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                        .addComponent(ccbTable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(lblCustomerName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnRefresh)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnAddDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(147, 147, 147)
                                .addComponent(btnUpdateDetail))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ccbBeverages, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPrice))))
                        .addGap(158, 158, 158)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeleteDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRefreshDetail)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(lblUserName))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lblCustomerName)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(ccbTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDiscountPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ccbBeverages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblAmount)
                    .addComponent(jLabel10)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteDetail)
                    .addComponent(btnRefreshDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateDetail)
                    .addComponent(btnAddDetail))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        clearAll();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (isInvalid) {
            return;
        }
        if (!checkInputInvoice()) {
            return;
        }
        invoiceSelected.setDate(Untils.parseStringToTimestamp(txtDate.getText().trim()));
        invoiceSelected.setDiscountPercentage(Untils.parseMoneyD(txtDiscountPercentage.getText().trim()));
        invoiceSelected.setTableId(((Table)ccbTable.getSelectedItem()).getId());
        if (invoiceDAO.updateInvoice(invoiceSelected)) {
            getDetail();
            loadDetail();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (isInvalid) {
            return;
        }
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof Frame parent) {
            InvoiceSearch dialog = new InvoiceSearch(parent);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(parent);
            if (dialog.getSelected() != null) {
                invoiceSelected = dialog.getSelected();
                loadAllData();
            }
        }
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (isInvalid) {
            return;
        }
        if (invoiceSelected == null) {
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa dữ liệu này không ?", "Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (invoiceDAO.deleteInvoice(invoiceSelected.getId())) {
                clearAll();
                JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDeleteDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDetailActionPerformed
        if (isInvalid) {
            return;
        }
        if (invoiceDetailSelected == null) {
            return;
        }
        if (invoiceDetailDAO.deleteInvoiceDetail(invoiceDetailSelected.getId())) {
            getDetail();
            loadDetail();
            JOptionPane.showMessageDialog(null, "Xóa thành công !", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteDetailActionPerformed

    private void btnRefreshDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshDetailActionPerformed
        clearDetail();
    }//GEN-LAST:event_btnRefreshDetailActionPerformed

    private void btnAddDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailActionPerformed
        if (isInvalid) {
            return;
        }
        if (invoiceSelected == null || invoiceDetailSelected != null) {
            return;
        }
        if (!checkInputDetail()) {
            return;
        }
        invoiceDetailSelected = new InvoiceDetail();
        invoiceDetailSelected.setInvoiceId(invoiceSelected.getId());
        invoiceDetailSelected.setBeveragesId(((Beverages)ccbBeverages.getSelectedItem()).getId());
        invoiceDetailSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        invoiceDetailSelected.setQuantity(Untils.parseMoneyI(spnQuantity.getValue().toString()));
        invoiceDetailSelected.setAmount(CalcAmount());
        if (invoiceDetailDAO.addInvoiceDetail(invoiceDetailSelected)) {
            getDetail();
            loadDetail();
            JOptionPane.showMessageDialog(null, "Thêm thành công !", "Add", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAddDetailActionPerformed

    private void btnUpdateDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailActionPerformed
        if (isInvalid) {
            return;
        }
        if (invoiceSelected == null || invoiceDetailSelected == null) {
            return;
        }
        if (!checkInputDetail()) {
            return;
        }
        invoiceDetailSelected.setBeveragesId(((Beverages)ccbBeverages.getSelectedItem()).getId());
        invoiceDetailSelected.setPrice(Untils.parseMoneyI(txtPrice.getText().trim()));
        invoiceDetailSelected.setQuantity(Untils.parseMoneyI(spnQuantity.getValue().toString()));
        invoiceDetailSelected.setAmount(invoiceDetailSelected.getPrice() * invoiceDetailSelected.getQuantity());
        if (invoiceDetailDAO.updateInvoiceDetail(invoiceDetailSelected)) {
            getDetail();
            loadDetail();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !", "Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateDetailActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        String text = txtPrice.getText().trim();
        if (!text.isEmpty()) {
            txtPrice.setText(String.valueOf(Untils.parseMoneyI(text)));
        }
    }//GEN-LAST:event_txtPriceFocusGained

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        String text = txtPrice.getText().trim();
        if (!text.isEmpty()) {
            int value = Untils.parseMoneyI(text);
            txtPrice.setText(Untils.formatMoney(value));
        }
        lblAmount.setText(Untils.formatMoney(CalcAmount()));
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        actionId();
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdFocusLost
        actionId();
    }//GEN-LAST:event_txtIdFocusLost

    private void ccbBeveragesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbBeveragesItemStateChanged
        Beverages beverages = (Beverages) ccbBeverages.getSelectedItem();
        if (beverages.getId() == 0) {
            txtPrice.setText(Constants.STR_EMPTY);
            spnQuantity.setValue(0);
            lblAmount.setText(Constants.STR_EMPTY);
        } else {
            txtPrice.setText(Untils.formatMoney(beverages.getPrice()));
            spnQuantity.setValue(1);
            lblAmount.setText(Untils.formatMoney(beverages.getPrice()));
        }
    }//GEN-LAST:event_ccbBeveragesItemStateChanged

    private void txtDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDateKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '/') {
            evt.consume();
        }
    }//GEN-LAST:event_txtDateKeyTyped

    private void spnQuantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnQuantityStateChanged
        lblAmount.setText(Untils.formatMoney(CalcAmount()));
    }//GEN-LAST:event_spnQuantityStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDetail;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteDetail;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshDetail;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateDetail;
    private javax.swing.JComboBox<Beverages> ccbBeverages;
    private javax.swing.JComboBox<Table> ccbTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tblInvoiceDetail;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDiscountPercentage;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
