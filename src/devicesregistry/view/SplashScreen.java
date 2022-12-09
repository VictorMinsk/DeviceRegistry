package devicesregistry.view;

import devicesregistry.controller.MainController;
import devicesregistry.dbtools.Database;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
* ��������� ���� ����������
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class SplashScreen extends JFrame {

    private Timer closeTimer;//������ ��� �������� ����       
    private int sec = 30;/*����� ������ �� ������� ��������� 
                 �� ��������������� �������� ���������� ����*/

    /**
     * ������� ��������� ����
     */
    public SplashScreen() {
        initComponents();
	closeTimer = new Timer();       
        closeTimer.schedule(new TimerTask() {
            public void run() {
                if (sec == 0) {
                    System.exit(0);
                }
                jbtExit.setText("����� (" + Integer.toString(sec) + ")");
                sec--;
            }
        }, 0, 1000); // ������ �������
    }
                       
    private void initComponents() {
        jbtNext = new JButton("�����");
        jbtExit = new JButton("�����");
        jlbPlaceAndYear = new JLabel();
        jlbHeader = new JLabel();
        jlbTitle = new JLabel();
        jlbAuthorAndTeacher = new JLabel();
        jlbImage = new JLabel();
        jpnHeaderAndTitle=new JPanel(new BorderLayout());
        jpnImageAndInfo=new JPanel(new GridLayout(1, 2, 10, 10));
        jpnFooter=new JPanel(new BorderLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());

        jbtNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtNextActionPerformed(evt);
            }
        });

        jbtExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtExitActionPerformed(evt);
            }
        });

        jlbPlaceAndYear.setFont(new Font("Tahoma", 0, 12));
        jlbPlaceAndYear.setHorizontalAlignment(JLabel.CENTER);
        jlbPlaceAndYear.setText("�����, 2022");
        jlbPlaceAndYear.setVerticalAlignment(JLabel.CENTER);
        
        jlbHeader.setHorizontalAlignment(JLabel.CENTER);
        jlbHeader.setText("<html><center><p>����������� ������������ ����������� �����������</p><p>��������� �������������� ���������� � �������������</p> <p>������� ������������ ����������� �������������� ������ � ����������</p></html>");
        jlbTitle.setHorizontalAlignment(JLabel.CENTER);
        jlbTitle.setText("<html>\n<center>\n<b><h2>�������� ������</h2></b>\n<p>�� ���������� \"���������������� �� ����� Java\"</p>\n<b><h3>����� ������������ �������<h3></b>\n</html>");

        jlbAuthorAndTeacher.setText("<html>\n<p>�������� ������� ������ 10702120</p>\n<p>��������� ������ �������������</p>\n<br>\n<p>�������������: �.�.-�.�., ������</p>\n<p>������� ������� ������������</p>\n</html>");

        jlbImage.setIcon(new ImageIcon(getClass().getResource("/devicesregistry/images/devicesRegistry.png"))); 
        jlbImage.setHorizontalAlignment(JLabel.CENTER);
        
        jpnImageAndInfo.add(jlbImage);
        jpnImageAndInfo.add(jlbAuthorAndTeacher);
        
        jpnFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpnFooter.add(jlbPlaceAndYear, BorderLayout.PAGE_START);
        jpnFooter.add(jbtExit, BorderLayout.WEST);
        jpnFooter.add(jbtNext, BorderLayout.EAST);
        
        jpnHeaderAndTitle.add(jlbHeader, BorderLayout.NORTH);
        jpnHeaderAndTitle.add(jlbTitle, BorderLayout.CENTER);
        
        add(jpnHeaderAndTitle, BorderLayout.NORTH);
        add(jpnImageAndInfo, BorderLayout.CENTER);
        add(jpnFooter, BorderLayout.SOUTH);

        setBounds(20, 20, 500, 400);
    }                      

    private void jbtExitActionPerformed(ActionEvent evt) {                                        
        System.exit(0);
    }                                       

    private void jbtNextActionPerformed(ActionEvent evt) {                                                                               
        try {
            Database.connect();//������������ ���������� � ��
        } catch (SQLException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "���� ������ �� �������", "Error message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (Database.dbConnection == null) {
            JOptionPane.showMessageDialog(this, "���� ������ �� �������", "Error message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        closeTimer.cancel();//���������� ������� ��������������� �������� ���������
        try {
            MainController.showMainWindow();
        } catch (SQLException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                       
                    
    private JButton jbtExit;
    private JButton jbtNext;
    private JLabel jlbAuthorAndTeacher;
    private JLabel jlbHeader;
    private JLabel jlbImage;
    private JLabel jlbPlaceAndYear;
    private JLabel jlbTitle;
    private JPanel jpnImageAndInfo;
    private JPanel jpnFooter;
    private JPanel jpnHeaderAndTitle;
}
