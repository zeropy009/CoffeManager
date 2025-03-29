/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Common.Constants;
import Common.UserSession;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author zero
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public MainFrame() {
        setTitle("CardLayout Example");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponent();
    }
    
    private void initComponent() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Hello, " + (UserSession.getInstance() != null ? UserSession.getInstance().getFullName() : ""), JLabel.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton logoutButton = new JButton("ĐĂNG XUẤT");
        logoutButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        logoutButton.setForeground(new Color(255, 51, 51));
        logoutButton.setIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGES + "logout.png")));
        logoutButton.addActionListener(e -> {
                UserSession.clearSession();
                new Login().setVisible(true);
                dispose();
            });
        
        headerPanel.add(logoutButton, BorderLayout.EAST);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Card Layout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Screens
        Menu menu = new Menu();
        
        cardPanel.add(menu, "Menu");
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.add(new JLabel("hihihi"));
        
        // Container for button and footer
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(footerPanel, BorderLayout.CENTER);
        
        // Main Layout
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
