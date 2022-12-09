package devicesregistry.view;

import devicesregistry.controller.MainController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
* Окно "Об программе"
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class AboutProgram extends JFrame {
    /**
     * Создает окно "О программе"
     */
    public AboutProgram() {
        initComponents();
    }
                    
    private void initComponents() {
        jbtBack = new JButton("Назад");
        jspDescription = new JScrollPane();
        jtaProgramDescription = new JTextArea();
        jlbTitle =  new JLabel();
        jlbImage = new JLabel();
        jtaVersion = new JTextArea();
        jpnProgramInfo=new JPanel();
        jpnVersionAndBack = new JPanel(new BorderLayout());
    
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("О программе");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                evtAboutProgramClose(evt);
            }
        });

        jbtBack.setText("Назад");
        jbtBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtBackActionPerformed(evt);
            }
        });

        jtaProgramDescription.setEditable(false);
        jtaProgramDescription.setBackground(SystemColor.control);
        jtaProgramDescription.setFont(new Font("Tahoma", 0, 14));
        jtaProgramDescription.setLineWrap(true);
        jtaProgramDescription.setText("Программа предназначена для работы "
                + "с Книгой компьютерной техники, хранящейся в базе данных SQL "
                + "(настройки подключения к базе данных находятся в классе DataBase программы).\n"
                + "По каждому устройству в базе хранятся: тип устройства, "
                + "год выпуска, состояние, краткое описание и местонахожденией."
                + "\nПользователь имеет возможность просмотра и поиска записей,"
                + "добавления, удаления и редактирования записей об устройствах.");
        jtaProgramDescription.setWrapStyleWord(true);
        jtaProgramDescription.setAlignmentX(CENTER_ALIGNMENT);
        
        jspDescription.setViewportView(jtaProgramDescription);
        jspDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jspDescription.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(SystemColor.scrollbar, 2)));
        
        jlbTitle.setFont(new Font("Tahoma", 1, 18));
        jlbTitle.setText("Книга компьютерной техники");
        jlbTitle.setAlignmentX(CENTER_ALIGNMENT);

        jtaVersion.setEditable(false);
        jtaVersion.setFont(new Font("Tahoma", 0, 14));
        jtaVersion.setForeground(SystemColor.control);
        jtaVersion.setText("Версия 1.0.0.2022");
        jtaVersion.setDisabledTextColor(SystemColor.textText);
        jtaVersion.setEnabled(false);

        jlbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/devicesregistry/images/devices.png")));
        jlbImage.setAlignmentX(CENTER_ALIGNMENT);
        
        jpnProgramInfo.setLayout(new BoxLayout(jpnProgramInfo, BoxLayout.PAGE_AXIS));
        jpnProgramInfo.add(Box.createVerticalGlue());
        jpnProgramInfo.add(jlbTitle);
        jpnProgramInfo.add(Box.createVerticalGlue());
        jpnProgramInfo.add(jlbImage);
        jpnProgramInfo.add(Box.createVerticalGlue());
        jpnProgramInfo.add(jspDescription);
        jpnProgramInfo.add(Box.createVerticalGlue());
        
        jpnVersionAndBack.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        jpnVersionAndBack.add(jtaVersion, BorderLayout.LINE_START);
        jpnVersionAndBack.add(Box.createRigidArea(new Dimension(50, 0)),
                BorderLayout.CENTER);
        jpnVersionAndBack.add(jbtBack, BorderLayout.LINE_END);
        
        add(jpnProgramInfo, BorderLayout.CENTER);
        add(jpnVersionAndBack, BorderLayout.PAGE_END);
        
        setBounds(0, 0, 400, 480);
    }                      

    private void jbtBackActionPerformed(ActionEvent evt) {                                        
        MainController.closeAboutProgram();
    }                                       

    private void evtAboutProgramClose(WindowEvent evt) {                                      
        MainController.closeAboutProgram();
    }                                     
                  
    private JScrollPane jspDescription;
    private JLabel jlbImage;
    private JButton jbtBack;
    private JLabel jlbTitle;
    private JTextArea jtaProgramDescription;
    private JTextArea jtaVersion;
    private JPanel jpnVersionAndBack;
    private JPanel jpnProgramInfo;
}
