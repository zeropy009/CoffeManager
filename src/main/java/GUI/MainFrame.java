                                                   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.Untils;
import Common.UserSession;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.*;

/**
 *
 * @author zero
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private boolean isHovered = false;

    public MainFrame() {
        setTitle("QUẢN LÝ QUÁN COFFEE");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponent();
    }
    
    // <editor-fold defaultstate="collapsed" desc="initComponent">
    private void initComponent() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton btnBack = new JButton();
        btnBack.setIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGES + "back.png")));
        btnBack.addActionListener( e -> {
            cardLayout.show(cardPanel, Constants.MENU);
        });
        // Gán phím ESC cho 1 hành động
        btnBack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escPressed");

        btnBack.getActionMap().put("escPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi hành động như khi bấm nút
                btnBack.doClick();
            }
        });
        
        JLabel titleLabel = new JLabel(UserSession.getInstance() != null ? UserSession.getInstance().getFullName() : "", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isHovered) {
                    setFont(getFont().deriveFont(Font.BOLD | Font.ITALIC, 28f));
                    setForeground(Color.RED);
                } else {
                    setFont(getFont().deriveFont(Font.BOLD, 24f));
                    setForeground(Color.BLACK);
                }

                super.paintComponent(g); // Vẽ chữ mặc định

                // Vẽ gạch chân nếu đang hover
                if (isHovered) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    FontMetrics fm = g2.getFontMetrics(getFont());
                    int textWidth = fm.stringWidth(getText());
                    int textX = (getWidth() - textWidth) / 2;
                    int textY = (getHeight() + fm.getAscent()) / 2;

                    int underlineY = textY + 2;
                    g2.setColor(getForeground());
                    g2.drawLine(textX, underlineY, textX + textWidth, underlineY);
                    g2.dispose();
                }
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem screenUserInfo = new JMenuItem("Thông tin cá nhân");
        JMenuItem screenChangePassword = new JMenuItem("Đổi mật khẩu");

        screenUserInfo.addActionListener(e -> {
            cardLayout.show(cardPanel, Constants.USER_INFO);
        });
        screenChangePassword.addActionListener(e -> {
            ChangePassword dialog = new ChangePassword(this);
            dialog.setVisible(true);
        });

        popupMenu.add(screenUserInfo);
        popupMenu.add(screenChangePassword);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(titleLabel, e.getX(), e.getY());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                titleLabel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                titleLabel.repaint();
            }
        });
        
        JButton btnLogout = new JButton("ĐĂNG XUẤT");
        btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 24));
        btnLogout.setForeground(new Color(255, 51, 51));
        btnLogout.setIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGES + "logout.png")));
        btnLogout.addActionListener(e -> {
            UserSession.clearSession();
            new Login().setVisible(true);
            dispose();
        });
        
        headerPanel.setBackground(new Color(173, 216, 230));
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnLogout, BorderLayout.EAST);
        
        // Card Layout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout) {
            Image img = new ImageIcon(getClass().getResource(Constants.PATH_IMAGES + "nengo.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        // Screens
        Menu menu = new Menu(cardPanel);
        UserInfor userInfor = new UserInfor();
        cardPanel.add(menu, Constants.MENU);
        cardPanel.add(userInfor, Constants.USER_INFO);
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(173, 216, 230));
        JLabel clock = new JLabel();
        Untils.clock(clock);
        footerPanel.add(clock);
        
        // Container for button and footer
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(footerPanel, BorderLayout.CENTER);
        
        // Main Layout 
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // </editor-fold>
    
    public static void showScreen(JPanel cardPanel, String screenName){
        CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());
        Optional<Component> existingCard = Arrays.stream(cardPanel.getComponents()).filter(component -> component.getName() != null && component.getName().equals(screenName)).findFirst();

        // Nếu không tìm thấy thì tạo mới màn hình
        if (!existingCard.isPresent()) {
            switch (screenName) {
                case Constants.BEVERAGES_MANAGE -> {
                    BeveragesManage beverages = new BeveragesManage();
                    beverages.setName(screenName);
                    cardPanel.add(beverages, screenName);
                }
                case Constants.STAFF_MANAGE -> {
                    StaffManage staffManage = new StaffManage();
                    staffManage.setName(screenName);
                    cardPanel.add(staffManage, screenName);
                }
                case Constants.CUSTOMER_MANAGE -> {
                    CustomerManage customerManage = new CustomerManage();
                    customerManage.setName(screenName);
                    cardPanel.add(customerManage, screenName);
                }
                case Constants.INVOICE_MANAGE -> {
                    InvoiceManage invoiceManage = new InvoiceManage();
                    invoiceManage.setName(screenName);
                    cardPanel.add(invoiceManage, screenName);
                }
                case Constants.WAREHOUSE_MANAGE -> {
                    WarehouseManage warehouseManage = new WarehouseManage();
                    warehouseManage.setName(screenName);
                    cardPanel.add(warehouseManage, screenName);
                }
                case Constants.BILLING -> {
                    Billing billing = new Billing();
                    billing.setName(screenName);
                    cardPanel.add(billing, screenName);
                }
                case Constants.INPUT_WAREHOUSE -> {
                    InputWarehouse inputWarehouse = new InputWarehouse();
                    inputWarehouse.setName(screenName);
                    cardPanel.add(inputWarehouse, screenName);
                }
                case Constants.STATISTICS -> {
                    Statistics statistics = new Statistics();
                    statistics.setName(screenName);
                    cardPanel.add(statistics, screenName);
                }
                default -> {
                }
            }
        }
        cardLayout.show(cardPanel, screenName);
    }
}
