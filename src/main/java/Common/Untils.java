/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author zero
 */
public class Untils {
    public static final SimpleDateFormat ft = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    
    public static void clock(JLabel lbTime) {
        Runnable runnable = () -> {
            while (true) {
                try {
                    Date t = new Date();
                    lbTime.setText(ft.format(t));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        Thread clockThread = new Thread(runnable);
        clockThread.start();
    }
    
    public static boolean validateText(JTextField txt) {
        String str = txt.getText();
        if (str == null || str.isEmpty()) {
            JOptionPane.showMessageDialog(null, txt.getName() + " không được rỗng", "Thông báo", 1);
            txt.requestFocus();
            return true;
        }
        return false;
    }
    
    public static boolean validatePhoneNumber(JTextField txt) {
        if (!txt.getText().trim().matches(Constants.REGEX_PHONE)) {
            JOptionPane.showMessageDialog(null, txt.getName() + " không hợp lệ", "Thông báo", 1);
            txt.setText(Constants.STR_EMPTY);
            txt.requestFocus();
            return false;
        }
        return true;
    }
    
    public static int writeFile(String fileName, String data) {
        try {
            File file = new File(Constants.PATH_FILES + fileName + Constants.EXTENSION_TXT);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }
    
    public static void openFile(String fileName) {
        try {
            String path = Constants.PATH_FILES + fileName + Constants.EXTENSION_TXT;
            URL resource = Untils.class.getResource(path);

            if (resource == null) {
                throw new IOException("Không tìm thấy tệp: " + path);
            }

            File file = new File(resource.toURI());

            if (!file.exists()) {
                throw new IOException("Tệp không tồn tại: " + file.getAbsolutePath());
            }

            // Mở file bằng ứng dụng mặc định của hệ điều hành
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String hashMD5(String passWord) {
        String str = Constants.STR_EMPTY;
        try {
            MessageDigest msd = MessageDigest.getInstance("MD5");
            byte[] srcTextBytes = passWord.getBytes("UTF-8");
            byte[] enrTextBytes = msd.digest(srcTextBytes);
            BigInteger bigInt = new BigInteger(1, enrTextBytes);
            str = bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public static void setImageButton(JButton button, String fileName) {
        try {
            BufferedImage image = ImageIO.read(Untils.class.getResource(Constants.PATH_IMAGES + fileName));
            int w = button.getSize().width;
            int h = button.getSize().height;
            int iw = image.getWidth();
            int ih = image.getHeight();
            int dw = 0;
            int dh = 0;
            if (w / h > iw / ih) {
                dh = h;
                dw = dh * iw / ih;
            } else {
                dw = w;
                dh = dw * ih / iw;
            }
            ImageIcon icon = new ImageIcon(image.getScaledInstance(dw, dh,
                    Image.SCALE_SMOOTH));
            button.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void setImageLabel(JLabel label, String fileName) {
        try {
            BufferedImage image = ImageIO.read(Untils.class.getResource(fileName));
            int w = label.getSize().width;
            int h = label.getSize().height;
            int iw = image.getWidth();
            int ih = image.getHeight();
            int dw = 0;
            int dh = 0;
            if ((double)w / h > (double)iw / ih) {
                dh = h;
                dw = dh * iw / ih;
            } else {
                dw = w;
                dh = dw * ih / iw;
            }
            ImageIcon icon = new ImageIcon(image.getScaledInstance(dw, dh,
                    Image.SCALE_SMOOTH));
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void offEdittingTable(JTable tb) {
        if (tb.isEditing()) {
            int row=tb.getSelectedRow();
            int col=tb.getSelectedColumn();
            tb.getCellEditor(row, col).cancelCellEditing();           
        }
    }
}
