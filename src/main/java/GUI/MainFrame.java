/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

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
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponent();
    }
    
    private void initComponent() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Xin chào,", JLabel.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton logoutButton = new JButton("ĐĂNG XUẤT");
        logoutButton.setForeground(Color.RED);
        
        headerPanel.add(logoutButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.EAST);
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.add(new JLabel("hihihi"));
        
        // Card Layout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Screens
        JPanel screen1 = createScreen("Màn hình 1", Color.CYAN);
        JPanel screen2 = createScreen("Màn hình 2", Color.LIGHT_GRAY);
        
        cardPanel.add(screen1, "Screen1");
        cardPanel.add(screen2, "Screen2");
        
        // Switch Button
        JPanel buttonPanel = new JPanel();
        JButton switchButton = new JButton("Chuyển màn hình");
        switchButton.addActionListener((ActionEvent e) -> {
            cardLayout.next(cardPanel);
        });
        buttonPanel.add(switchButton);
        
        // Main Layout
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createScreen(String text, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.add(new JLabel(text));
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
