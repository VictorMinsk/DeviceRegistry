package devicesregistry.view;

import devicesregistry.controller.MainController;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
* Окно "Об авторе"
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class AboutAuthor extends JFrame {
    /**
     * Создает окно "Об авторе"
     */
    public AboutAuthor() {
        initComponents();
    }
                       
    private void initComponents() {
        jbtBack = new JButton("Назад");
        jtaAuthorInfo = new JTextArea();
        jlbPhotoAuthor = new JLabel();
        jpnInfo = new JPanel();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Об авторе");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                aboutAuthorClosed(evt);
            }
        });

        jbtBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtBackActionPerformed(evt);
            }
        });

        jtaAuthorInfo.setEditable(false);
        jtaAuthorInfo.setBackground(SystemColor.control);
        jtaAuthorInfo.setFont(new Font("Tahoma", 0, 14));
        jtaAuthorInfo.setText("Автор:\nстудент группы 10702120 БНТУ\n"
                + "Ничипорук Виктор Александрович\nvic.nic202@gmail.com");
        
        jlbPhotoAuthor.setIcon(new ImageIcon(getClass().getResource("/devicesregistry/images/author.png")));
        jlbPhotoAuthor.setAlignmentX(CENTER_ALIGNMENT);
        
        jbtBack.setAlignmentX(CENTER_ALIGNMENT);
        jbtBack.setPreferredSize(new Dimension(getWidth()/2, 30));
        
        jpnInfo.add(jtaAuthorInfo);
        
        add(Box.createVerticalGlue());
        add(jlbPhotoAuthor);
        add(Box.createVerticalGlue());
        add(jpnInfo);
        add(Box.createVerticalGlue());
        add(jbtBack);
        add(Box.createVerticalGlue()); 
        setBounds(0, 0, 330, 550);
    }                       

    private void jbtBackActionPerformed(ActionEvent evt) {                                        
        MainController.closeAboutAuthor();
    }                                       

    private void aboutAuthorClosed(WindowEvent evt) {                                   
        MainController.closeAboutAuthor();
    }                                  
                 
    private JButton jbtBack;
    private JLabel jlbPhotoAuthor;
    private JTextArea jtaAuthorInfo;
    private JPanel jpnInfo;
}
