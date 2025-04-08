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

    public MainFrame() {
        setTitle("QUẢN LÝ QUÁN CAFFEE");
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
                cardLayout.show(cardPanel, "Menu");
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
        
        JLabel titleLabel = new JLabel("Hello, " + (UserSession.getInstance() != null ? UserSession.getInstance().getFullName() : ""), JLabel.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton btnLogout = new JButton("ĐĂNG XUẤT");
        btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 24));
        btnLogout.setForeground(new Color(255, 51, 51));
        btnLogout.setIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGES + "logout.png")));
        btnLogout.addActionListener(e -> {
                UserSession.clearSession();
                new Login().setVisible(true);
                dispose();
            });
        
        headerPanel.add(btnLogout, BorderLayout.EAST);
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Card Layout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Screens
        Menu menu = new Menu(cardPanel);
        cardPanel.add(menu, Constants.MENU);
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
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
                case Constants.BEVERAGES -> {
                    Beverages beverages = new Beverages();
                    cardPanel.add(beverages, screenName);
                }
                case Constants.STAFF_MANAGE -> {
                    StaffManage staffManage = new StaffManage();
                    cardPanel.add(staffManage, screenName);
                }
                default -> {
                }
            }
        }
        cardLayout.show(cardPanel, screenName);
    }
}
