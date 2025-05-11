/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Untils;
import Common.UserSession;
import DAO.BeveragesDAO;
import DAO.CustomerDAO;
import DAO.Impl.BeveragesImpl;
import DAO.Impl.CustomerDAOImpl;
import DAO.Impl.InvoiceDetailImpl;
import DAO.Impl.InvoiceImpl;
import DAO.Impl.TableImpl;
import DAO.InvoiceDAO;
import DAO.InvoiceDetailDAO;
import DAO.TableDAO;
import Model.Beverages;
import Model.Customer;
import Model.Invoice;
import Model.InvoiceDetail;
import Model.Table;
import java.awt.Frame;
import java.awt.Window;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zero
 */
public class Billing extends javax.swing.JPanel {

    private final TableDAO tableDAO;
    private final BeveragesDAO beveragesDAO;
    private final CustomerDAO customerDAO;
    private final InvoiceDAO invoiceDAO;
    private final InvoiceDetailDAO invoiceDetailDAO;
    private final DefaultTableModel modelTable;
    private final DefaultTableModel modelInvoiceDetail;
    private Invoice invoice;
    private ArrayList<Table> tableList;
    private ArrayList<InvoiceDetail> invoiceDetailList;
    private Customer customerSelected;
    private Table tableSelected;
    private InvoiceDetail invoiceDetailSelected;
    
    /**
     * Creates new form Billing
     */
    public Billing() {
        tableDAO = new TableImpl();
        beveragesDAO = new BeveragesImpl();
        customerDAO = new CustomerDAOImpl();
        invoiceDAO = new InvoiceImpl();
        invoiceDetailDAO = new InvoiceDetailImpl();
        initComponents();
        modelTable = (DefaultTableModel) tblTable.getModel();
        modelInvoiceDetail = (DefaultTableModel) tblInvoiceDetail.getModel();
        // Tạo sự kiện khi SelectedRow của JTable thay đổi giá trị.
        tblTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblTable.getSelectedRow();
                tableSelected = null;
                invoice = null;
                invoiceDetailList = null;
                modelInvoiceDetail.setRowCount(0);
                if (selectedRow != -1) {
                    if (tblTable.getValueAt(selectedRow, 0) instanceof Table table) {
                        tableSelected = table;
                        getInvoice();
                        loadInvoiceDetail();
                    }
                }
            }
        });
        tblInvoiceDetail.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblInvoiceDetail.getSelectedRow();
                if (selectedRow != -1) {
                    if (tblInvoiceDetail.getValueAt(selectedRow, 0) instanceof InvoiceDetail invoiceDetail) {
                        invoiceDetailSelected = invoiceDetail;
                        int index = 0;
                        for (int i = 0; i < ccbBeverages.getItemCount(); i++) {
                            if (ccbBeverages.getItemAt(i).getId() == invoiceDetail.getBeveragesId()) {
                                index = i;
                                break;
                            }
                        }
                        ccbBeverages.setSelectedIndex(index);
                        spnQuantity.setValue((index == 0 ? 1 : invoiceDetail.getQuantity()));
                    }
                }
            }
        });
        setDefault();
    }
    
    private void setDefault() {
        tableList = tableDAO.getAllTable();
        modelTable.setRowCount(0);
        for (Table table : tableList) {
            Object[] row = new Object[2];
            row[0] = table;
            row[1] = table.getStatus() ? "Có người" : "Trống";
            modelTable.addRow(row);
        }
        lblUserFullName.setText(UserSession.getInstance().getFullName());
        ArrayList<Beverages> beveragesList = beveragesDAO.getAllBeverages();
        for (Beverages beverages : beveragesList) {
            ccbBeverages.addItem(beverages);
        }
        customerSelected = customerDAO.getCustomerByID(1); // Lấy Khách vãn lai làm mặc định;
        lblCustomerName.setText(customerSelected.getName());
        lblPhone.setText(customerSelected.getPhone() == null ? "" : customerSelected.getPhone());
    }
    
    private void getInvoice() {
        if (tableSelected == null) {
            return;
        }
        invoice = invoiceDAO.getInvoiceByTableId(tableSelected.getId());
    }
    
    private void loadInvoiceDetail() {
        invoiceDetailSelected = null;
        invoiceDetailList = null;
        ccbBeverages.setSelectedIndex(0);
        spnQuantity.setValue(1);
        modelInvoiceDetail.setRowCount(0);
        if (invoice != null) {
            invoiceDetailList = invoiceDetailDAO.getAllInvoiceDetailsByInvoiceId(invoice.getId());
            if (invoiceDetailList != null && !invoiceDetailList.isEmpty()) {
                for (InvoiceDetail invoiceDetail : invoiceDetailList) {
                    Object[] row = new Object[4];
                    row[0] = invoiceDetail;
                    row[1] = invoiceDetail.getQuantity();
                    row[2] = Untils.formatMoney(invoiceDetail.getPrice());
                    row[3] = Untils.formatMoney(invoiceDetail.getAmount());
                    modelInvoiceDetail.addRow(row);
                }
            }
            lblTotalAmount.setText(Untils.formatMoney(invoiceDetailList.stream().mapToInt(InvoiceDetail::getAmount).sum()));
            lblDiscountPercentage.setText(String.format("%.0f%%", invoice.getDiscountPercentage()));
            lblAmountDue.setText(Untils.formatMoney(invoice.getTotalAmount()));
        } else {
            lblTotalAmount.setText("0");
            lblDiscountPercentage.setText("0");
            lblAmountDue.setText("0");
        }
    }
    
    private void calcChange() {
        int cashReceived = Untils.parseMoneyI(txtCashReceived.getText().trim());
        lblChange.setText(Untils.formatMoney(cashReceived - invoice.getTotalAmount()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInvoiceDetail = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        btnPayment = new javax.swing.JButton();
        lblTotalAmount = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        lblDiscountPercentage = new javax.swing.JLabel();
        lblAmountDue = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        txtCashReceived = new javax.swing.JTextField();
        lblUserFullName = new javax.swing.JLabel();
        ccbBeverages = new javax.swing.JComboBox<>();
        spnQuantity = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();
        lblCustomerName = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        lblChange = new javax.swing.JLabel();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Tổng cộng:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setText("Giảm giá");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Thành tiền:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 255));
        jLabel4.setText("Tiền khách đưa:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 255));
        jLabel5.setText("Tiền thừa:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 0, 255));
        jLabel6.setText("Tên nhân viên:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 255));
        jLabel7.setText("Tên nước:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 255));
        jLabel8.setText("Số lượng:");

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Bàn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblTable);

        tblInvoiceDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên nước", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoiceDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblInvoiceDetail);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("ĐẶT NƯỚC VÀ THANH TOÁN");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnPayment.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deposit.png"))); // NOI18N
        btnPayment.setText("Thanh toán & In");
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });

        lblTotalAmount.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTotalAmount.setText("0");

        btnSearch.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_a.png"))); // NOI18N
        btnSearch.setText("Chọn KH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblDiscountPercentage.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblDiscountPercentage.setText("0%");

        lblAmountDue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAmountDue.setText("0");

        lblPhone.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblPhone.setText("0123456789");

        txtCashReceived.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCashReceived.setText("0");
        txtCashReceived.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCashReceivedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCashReceivedFocusLost(evt);
            }
        });
        txtCashReceived.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCashReceivedKeyTyped(evt);
            }
        });

        lblUserFullName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblUserFullName.setText("0");

        ccbBeverages.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ccbBeverages.setModel(new javax.swing.DefaultComboBoxModel<>(new Beverages[] { new Beverages("") }));
        ccbBeverages.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbBeveragesItemStateChanged(evt);
            }
        });

        spnQuantity.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        btnAdd.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblCustomerName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblCustomerName.setText("Khách vãng lai");

        btnDelete.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnDelete.setText("Xóa nước");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblChange.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblChange.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPayment)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDiscountPercentage))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblAmountDue))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalAmount))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCashReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblChange)))
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ccbBeverages, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAdd)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPhone)
                                            .addComponent(lblCustomerName)))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addGap(304, 304, 304)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(lblTotalAmount)
                            .addComponent(lblUserFullName))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(lblDiscountPercentage))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(ccbBeverages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(lblAmountDue)
                            .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCashReceived, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(btnSearch)
                            .addComponent(lblCustomerName)
                            .addComponent(lblChange))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPayment)
                            .addComponent(lblPhone)
                            .addComponent(btnDelete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        if (invoiceDetailList == null || invoiceDetailList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có gì để thanh toán.", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cashReceived = Untils.parseMoneyI(txtCashReceived.getText().trim());
        if (cashReceived < invoice.getTotalAmount()) {
            JOptionPane.showMessageDialog(null, "Khách hàng chưa trả đủ tiền.", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(null, String.format("Bạn muốn thanh toán hoá đơn %d", invoice.getId()), "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            invoice.setPaymentStatus(true);
            if (invoiceDAO.updateInvoice(invoice)) {
                int index = tableList.indexOf(tableSelected);
                tableSelected.setStatus(false);
                tableDAO.updateTable(tableSelected);
                tableList.set(index, tableSelected);
                modelTable.setValueAt(tableSelected.getStatus() ? "Có người" : "Trống", index, 1);
                
                StringBuilder fileContent = new StringBuilder();

                fileContent.append(String.format("Mã hoá đơn: %-5d\tNgày lập: %s\n",
                        invoice.getId(),
                        invoice.getDate().toLocalDateTime().format(Untils.ft)));
                fileContent.append("Tên nhân viên: ").append(UserSession.getInstance().getFullName()).append("\n");
                fileContent.append(tableSelected.getTableName()).append("\n");
                fileContent.append("=".repeat(100)).append("\n");
                // Header bảng chi tiết hoá đơn
                fileContent.append(String.format("%-30s %10s %15s %15s\n",
                        "Tên nước", "Số lượng", "Đơn giá", "Thành tiền"));
                // Nội dung từng dòng hoá đơn
                for (InvoiceDetail invoiceDetail : invoiceDetailList) {
                    fileContent.append(String.format("%-30s %10d %15s %15s\n",
                            invoiceDetail.getBeveragesName(),
                            invoiceDetail.getQuantity(),
                            Untils.formatMoney(invoiceDetail.getPrice()),
                            Untils.formatMoney(invoiceDetail.getAmount())));
                }
                fileContent.append("=".repeat(100)).append("\n");
                fileContent.append(String.format("%57s %15s\n", "Tổng cộng:", lblTotalAmount.getText()));
                fileContent.append(String.format("%57s %15s\n", "Giảm giá:", lblDiscountPercentage.getText()));
                fileContent.append(String.format("%57s %15s\n", "Thành tiền:", lblAmountDue.getText()));
                fileContent.append(String.format("%57s %15s\n", "Tiền khách đưa:", txtCashReceived.getText().trim()));
                fileContent.append(String.format("%57s %15s\n", "Tiền thừa:", lblChange.getText()));
                
                String fileName = String.format("HD%03d", invoice.getId());
                getInvoice();
                loadInvoiceDetail();
                if (Untils.writeFile(fileName, fileContent.toString())) {
                    JOptionPane.showMessageDialog(null, "Thanh toán thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    Untils.openFile(fileName);
                }
            }
        }
    }//GEN-LAST:event_btnPaymentActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof Frame parent) {
            CustomerSearch dialog = new CustomerSearch(parent);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(parent);
            if (dialog.getSelected() != null) {
                customerSelected = dialog.getSelected();
                lblCustomerName.setText(customerSelected.getName());
                lblPhone.setText(customerSelected.getPhone());
                lblDiscountPercentage.setText(String.format("%.0f%%", customerSelected.getDiscountPercentage()));
                int total = (int) (invoiceDetailList.stream().mapToInt(InvoiceDetail::getAmount).sum() * (1 - (customerSelected.getDiscountPercentage() / 100)));
                invoice.setCustomerId(customerSelected.getId());
                invoice.setTotalAmount(total);
                if (invoiceDAO.updateInvoice(invoice)) {
                    getInvoice();
                    lblAmountDue.setText(Untils.formatMoney(total));
                    calcChange();
                }
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (tableSelected == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn để đặt nước.", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Beverages beverages = (Beverages) ccbBeverages.getSelectedItem();
        if (beverages.getId() != 0) {
            int quantity = Untils.parseMoneyI(spnQuantity.getValue().toString());
            if (invoiceDetailSelected == null) {
                if (invoice == null) {
                    int indexTable = tableList.indexOf(tableSelected);
                    tableSelected.setStatus(true);
                    tableDAO.updateTable(tableSelected);
                    tableList.set(indexTable, tableSelected);
                    modelTable.setValueAt(tableSelected.getStatus() ? "Có người" : "Trống", indexTable, 1);
                    invoice = new Invoice(0, new Timestamp(System.currentTimeMillis()), beverages.getPrice() * quantity
                                            , UserSession.getInstance().getUsername(), customerSelected.getId(), customerSelected.getDiscountPercentage()
                                            , tableSelected.getId(), false, customerSelected.getName(), tableSelected.getTableName());
                    if (invoiceDAO.addInvoice(invoice)) {
                        getInvoice();
                        invoiceDetailSelected = new InvoiceDetail(0, invoice.getId(), beverages.getId(), quantity, beverages.getPrice()
                                                                    , beverages.getPrice() * quantity, beverages.getName());
                        if (invoiceDetailDAO.addInvoiceDetail(invoiceDetailSelected)) {
                            getInvoice();
                            loadInvoiceDetail();
                            tblInvoiceDetail.setRowSelectionInterval(0, 0);
                        }
                    }
                } else {
                    invoiceDetailSelected = new InvoiceDetail(0, invoice.getId(), beverages.getId(), quantity, beverages.getPrice()
                                                                , beverages.getPrice() * quantity, beverages.getName());
                    if (invoiceDetailDAO.addInvoiceDetail(invoiceDetailSelected)) {
                        getInvoice();
                        loadInvoiceDetail();
                        tblInvoiceDetail.setRowSelectionInterval(invoiceDetailList.size() - 1, invoiceDetailList.size() - 1);
                        tblInvoiceDetail.scrollRectToVisible(tblInvoiceDetail.getCellRect(invoiceDetailList.size() - 1, 0, true));
                    }
                }
            } else {
                int indexInvoiceDetail = invoiceDetailList.indexOf(invoiceDetailSelected);
                invoiceDetailSelected.setQuantity(quantity);
                invoiceDetailSelected.setAmount(invoiceDetailSelected.getPrice() * quantity);
                if (invoiceDetailDAO.updateInvoiceDetail(invoiceDetailSelected)) {
                    getInvoice();
                    loadInvoiceDetail();
                    tblInvoiceDetail.setRowSelectionInterval(indexInvoiceDetail, indexInvoiceDetail);
                    tblInvoiceDetail.scrollRectToVisible(tblInvoiceDetail.getCellRect(indexInvoiceDetail, 0, true));
                }
            }
        } else {
            spnQuantity.setValue(1);
            tblInvoiceDetail.clearSelection();
        }
        calcChange();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (invoiceDetailSelected == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nước bạn muốn hủy.", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (invoiceDetailDAO.deleteInvoiceDetail(invoiceDetailSelected.getId())) {
            int selectedRow = tblInvoiceDetail.getSelectedRow();
            invoiceDetailList.remove(selectedRow);
            modelInvoiceDetail.removeRow(selectedRow);
            ccbBeverages.setSelectedIndex(0);
            spnQuantity.setValue(1);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtCashReceivedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashReceivedKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCashReceivedKeyTyped

    private void txtCashReceivedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashReceivedFocusGained
        String text = txtCashReceived.getText().trim();
        txtCashReceived.setText(String.valueOf(Untils.parseMoneyI(text)));
    }//GEN-LAST:event_txtCashReceivedFocusGained

    private void txtCashReceivedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashReceivedFocusLost
        try {
            String text = txtCashReceived.getText().trim();
            if (!text.isEmpty()) {
                int value = Untils.parseMoneyI(text);
                txtCashReceived.setText(Untils.formatMoney(value));
            }
        } catch (NumberFormatException ex) {
            txtCashReceived.setText("0");
        }
        calcChange();
    }//GEN-LAST:event_txtCashReceivedFocusLost

    private void ccbBeveragesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbBeveragesItemStateChanged
        if (invoice == null || invoiceDetailList == null || invoiceDetailList.isEmpty()) {
            return;
        }
        Beverages beverages = (Beverages) ccbBeverages.getSelectedItem();
        if (beverages.getId() != 0) {
            int index = IntStream.range(0, invoiceDetailList.size())
                                .filter(i -> invoiceDetailList.get(i).getBeveragesId()== beverages.getId())
                                .findFirst()
                                .orElse(-1);
            if (index >= 0) {
                tblInvoiceDetail.setRowSelectionInterval(index, index);
                tblInvoiceDetail.scrollRectToVisible(tblInvoiceDetail.getCellRect(index, 0, true));
            } else {
                spnQuantity.setValue(1);
                invoiceDetailSelected = null;
                tblInvoiceDetail.clearSelection();
            }
        } else {
            spnQuantity.setValue(1);
            invoiceDetailSelected = null;
            tblInvoiceDetail.clearSelection();
        }
    }//GEN-LAST:event_ccbBeveragesItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<Beverages> ccbBeverages;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAmountDue;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDiscountPercentage;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblUserFullName;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tblInvoiceDetail;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtCashReceived;
    // End of variables declaration//GEN-END:variables
}
