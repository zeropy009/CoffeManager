/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import DAO.Impl.DefaultStoredProcedureCaller;
import DAO.StoredProcedureCaller;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author zero
 */
public class Statistics extends javax.swing.JPanel {

    private final StoredProcedureCaller storedProcedureCaller;
    
    /**
     * Creates new form Statistics
     */
    public Statistics() {
        storedProcedureCaller = new DefaultStoredProcedureCaller();
        initComponents();
    }
    
    private void callMessageDialogNothingHasBeenSold(int month, int year) {
        JOptionPane.showMessageDialog(null, String.format("Chia buồn tháng %s/%s bạn không bán được gì cả !!!", month, year), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        spnStaffStatistics = new javax.swing.JSpinner();
        btnStaffStatistics = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblStaffName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblRevenue = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        spnBestSell = new javax.swing.JSpinner();
        btnBestSell = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblBeveragesName = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnBestCustomer = new javax.swing.JButton();
        spnBestCustomer = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblCustomerName = new javax.swing.JLabel();
        lblAmountSpent = new javax.swing.JLabel();
        lblAmountSpent1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        spnProfit = new javax.swing.JSpinner();
        btnProfit = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        lblImportCost = new javax.swing.JLabel();
        lblEmployeeSalary = new javax.swing.JLabel();
        lblProfit = new javax.swing.JLabel();

        setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 102));
        jLabel7.setText("THỐNG KÊ");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setOpaque(false);

        spnStaffStatistics.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        spnStaffStatistics.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));
        spnStaffStatistics.setEditor(new javax.swing.JSpinner.DateEditor(spnStaffStatistics, "MM/yyyy"));

        btnStaffStatistics.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnStaffStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/market_analysis1.png"))); // NOI18N
        btnStaffStatistics.setText("Nhân viên đạt doanh thu cao nhất");
        btnStaffStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffStatisticsActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Tên nhân viên:");

        lblStaffName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblStaffName.setText(" ");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Account:");

        lblUserName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblUserName.setText(" ");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 255));
        jLabel5.setText("Doanh thu tháng:");

        lblRevenue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblRevenue.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblStaffName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spnStaffStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStaffStatistics)))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStaffStatistics)
                    .addComponent(spnStaffStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblStaffName)
                    .addComponent(jLabel3)
                    .addComponent(lblUserName)
                    .addComponent(jLabel5)
                    .addComponent(lblRevenue))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel2.setOpaque(false);

        spnBestSell.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        spnBestSell.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));
        spnBestSell.setEditor(new javax.swing.JSpinner.DateEditor(spnBestSell, "MM/yyyy"));

        btnBestSell.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnBestSell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/market_analysis1.png"))); // NOI18N
        btnBestSell.setText("Nước bán chạy nhất");
        btnBestSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBestSellActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 255));
        jLabel8.setText("Tên nước:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 0, 255));
        jLabel9.setText("Số lượng bán ra trong tháng:");

        lblBeveragesName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblBeveragesName.setText(" ");

        lblQuantity.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblQuantity.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(spnBestSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBestSell, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(lblBeveragesName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBestSell)
                    .addComponent(spnBestSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(lblBeveragesName)
                    .addComponent(lblQuantity))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel3.setOpaque(false);

        btnBestCustomer.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnBestCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/market_analysis1.png"))); // NOI18N
        btnBestCustomer.setText("Khách hàng chi nhiều nhất");
        btnBestCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBestCustomerActionPerformed(evt);
            }
        });

        spnBestCustomer.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        spnBestCustomer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));
        spnBestCustomer.setEditor(new javax.swing.JSpinner.DateEditor(spnBestCustomer, "MM/yyyy"));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setText("Tên khách hàng:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 255));
        jLabel4.setText("Tổng tiền đã chi tiêu tại cửa hàng:");

        lblCustomerName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblCustomerName.setText(" ");

        lblAmountSpent.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAmountSpent.setText(" ");

        lblAmountSpent1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblAmountSpent1.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(spnBestCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBestCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lblAmountSpent, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(53, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(756, Short.MAX_VALUE)
                    .addComponent(lblAmountSpent1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBestCustomer)
                    .addComponent(spnBestCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(lblCustomerName)
                    .addComponent(lblAmountSpent))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(105, Short.MAX_VALUE)
                    .addComponent(lblAmountSpent1)
                    .addGap(4, 4, 4)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel4.setOpaque(false);

        spnProfit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        spnProfit.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));
        spnProfit.setEditor(new javax.swing.JSpinner.DateEditor(spnProfit, "MM/yyyy"));

        btnProfit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnProfit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/market_analysis1.png"))); // NOI18N
        btnProfit.setText("Tính lợi nhuận cửa hàng");
        btnProfit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfitActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 0, 255));
        jLabel10.setText("Tổng thu:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 0, 255));
        jLabel11.setText("Tiền nhập NL:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 0, 255));
        jLabel12.setText("Tiền lương:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 255));
        jLabel13.setText("Lợi nhuận:");

        lblTotalRevenue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTotalRevenue.setText(" ");

        lblImportCost.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblImportCost.setText(" ");

        lblEmployeeSalary.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblEmployeeSalary.setText(" ");

        lblProfit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblProfit.setText(" ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(spnProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImportCost, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmployeeSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProfit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(lblTotalRevenue)
                    .addComponent(lblImportCost)
                    .addComponent(lblEmployeeSalary)
                    .addComponent(lblProfit))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStaffStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffStatisticsActionPerformed
        LocalDate localDate = ((Date) spnStaffStatistics.getValue())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        List<Object> params = new ArrayList<>();
        params.add(month);
        params.add(year);
        List<Map<String, Object>> result = storedProcedureCaller.callStoredProcedure("SP_BEST_EMPLOYEE_BY_REVENUE", params);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> data = result.getFirst();
            lblStaffName.setText(data.get("FULL_NAME").toString());
            lblUserName.setText(data.get("USER_NAME").toString());
            lblRevenue.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("TOTAL_REVENUE").toString())));
        } else {
            lblStaffName.setText(Constants.STR_EMPTY);
            lblUserName.setText(Constants.STR_EMPTY);
            lblRevenue.setText(Constants.STR_EMPTY);
            callMessageDialogNothingHasBeenSold(month, year);
        }
    }//GEN-LAST:event_btnStaffStatisticsActionPerformed

    private void btnBestSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBestSellActionPerformed
        LocalDate localDate = ((Date) spnBestSell.getValue())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        List<Object> params = new ArrayList<>();
        params.add(month);
        params.add(year);
        List<Map<String, Object>> result = storedProcedureCaller.callStoredProcedure("SP_TOP_SELLING_BEVERAGE", params);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> data = result.getFirst();
            lblBeveragesName.setText(data.get("BEVERAGE_NAME").toString());
            lblQuantity.setText(data.get("TOTAL_QUANTITY").toString());
        } else {
            lblBeveragesName.setText(Constants.STR_EMPTY);
            lblQuantity.setText(Constants.STR_EMPTY);
            callMessageDialogNothingHasBeenSold(month, year);
        }
    }//GEN-LAST:event_btnBestSellActionPerformed

    private void btnBestCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBestCustomerActionPerformed
        LocalDate localDate = ((Date) spnBestCustomer.getValue())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        List<Object> params = new ArrayList<>();
        params.add(month);
        params.add(year);
        List<Map<String, Object>> result = storedProcedureCaller.callStoredProcedure("SP_TOP_SPENDING_CUSTOMER", params);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> data = result.getFirst();
            lblCustomerName.setText(data.get("CUSTOMER_NAME").toString());
            lblAmountSpent.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("TOTAL_SPENT").toString())));
        } else {
            lblCustomerName.setText(Constants.STR_EMPTY);
            lblAmountSpent.setText(Constants.STR_EMPTY);
            callMessageDialogNothingHasBeenSold(month, year);
        }
    }//GEN-LAST:event_btnBestCustomerActionPerformed

    private void btnProfitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfitActionPerformed
        LocalDate localDate = ((Date) spnProfit.getValue())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        List<Object> params = new ArrayList<>();
        params.add(month);
        params.add(year);
        List<Map<String, Object>> result = storedProcedureCaller.callStoredProcedure("SP_PROFIT_BY_MONTH", params);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> data = result.getFirst();
            lblTotalRevenue.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("TOTAL_REVENUE").toString())));
            lblImportCost.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("WAREHOUSE_COST").toString())));
            lblEmployeeSalary.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("SALARY_COST").toString())));
            lblProfit.setText(Untils.formatMoney(Untils.parseMoneyI(data.get("PROFIT").toString())));
        } else {
            lblTotalRevenue.setText(Constants.STR_EMPTY);
            lblImportCost.setText(Constants.STR_EMPTY);
            lblEmployeeSalary.setText(Constants.STR_EMPTY);
            lblProfit.setText(Constants.STR_EMPTY);
        }
    }//GEN-LAST:event_btnProfitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBestCustomer;
    private javax.swing.JButton btnBestSell;
    private javax.swing.JButton btnProfit;
    private javax.swing.JButton btnStaffStatistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lblAmountSpent;
    private javax.swing.JLabel lblAmountSpent1;
    private javax.swing.JLabel lblBeveragesName;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblEmployeeSalary;
    private javax.swing.JLabel lblImportCost;
    private javax.swing.JLabel lblProfit;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblRevenue;
    private javax.swing.JLabel lblStaffName;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JSpinner spnBestCustomer;
    private javax.swing.JSpinner spnBestSell;
    private javax.swing.JSpinner spnProfit;
    private javax.swing.JSpinner spnStaffStatistics;
    // End of variables declaration//GEN-END:variables
}
