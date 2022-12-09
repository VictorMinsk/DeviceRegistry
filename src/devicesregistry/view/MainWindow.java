package devicesregistry.view;

import devicesregistry.model.*;
import devicesregistry.dbtools.*;
import devicesregistry.controller.MainController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.WindowConstants;

/**
* ������� ���� ����������
* <br>
* <i>�������� ������ ������������ �������</i>
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class MainWindow extends JFrame {
    private BookOfDevices bookOfDevices;
    private final String errorDB="������ ������� � ���� ������";
    private final String errorMessage="Error Message";
    private final String errorNoRowSelected="�� ������� �� ����� ������";
    
    /**
     * ������� ������� ���� ����������
     * @throws SQLException 
     */
    public MainWindow() throws SQLException {
        initComponents();
        bookOfDevices=new BookOfDevices();
        fillTable();
    }
                         
    private void initComponents() {
        jtbDevices = new JTable();
        jbtExit = new JButton("�����");
        jbtEdit = new JButton("�������������");
        jbtSearch = new JButton("�����");
        jbtAdd = new JButton("��������");
        jbtShowAll = new JButton("�������� ���");
        jbtDelete = new JButton("�������");
        jmnuMain = new JMenuBar();
        jmnDevices = new JMenu("����������");
        jmnDictionary = new JMenu("�����������");
        jmnInfo = new JMenu("����������");
        jmnExit = new JMenu("�����");
        jmniSearch = new JMenuItem("�����");
        jmniEdit = new JMenuItem("�������������");
        jmniAdd = new JMenuItem("��������");
        jmniDelete = new JMenuItem("�������");
        jmniTypeDictionary = new JMenuItem("���� ���������");
        jmniStateDictionary = new JMenuItem("����������� ���������");     
        jmniAboutProgram = new JMenuItem("� ���������");
        jmniAboutAutor = new JMenuItem("�� ������");    
        jmniExit = new JMenuItem("����� �� ���������");
        jspDevicesTable = new JScrollPane();
        jpnButtons=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        Font fntMenu=new Font("Tahoma", 0, 12);
        Dimension dmnMenu=new Dimension(100, 20);
        
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("����� ������������ �������");
        setIconImage(new ImageIcon(getClass().getResource("/devicesregistry/images/bookdev.png")).getImage());

        jbtExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtExitActionPerformed(evt);
            }
        });

        jspDevicesTable.setViewportView(jtbDevices);
        jspDevicesTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createLineBorder(SystemColor.controlShadow, 1)));

        jbtEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtEditActionPerformed(evt);
            }
        });

        jbtSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtSearchActionPerformed(evt);
            }
        });

        jbtAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });

        jbtShowAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtShowAllActionPerformed(evt);
            }
        });

        jbtDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtDeleteActionPerformed(evt);
            }
        });

        //�������� ����
        jmnDevices.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jmnDevices.setFont(fntMenu); 
        jmnDevices.setPreferredSize(dmnMenu);

        jmniSearch.setFont(fntMenu); 
        jmniSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtSearchActionPerformed(evt);
            }
        });
        jmnDevices.add(jmniSearch);

        jmniEdit.setFont(fntMenu); 
        jmniEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtEditActionPerformed(evt);
            }
        });
        jmnDevices.add(jmniEdit);

        jmniAdd.setFont(fntMenu);
        jmniAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });
        jmnDevices.add(jmniAdd);

        jmniDelete.setFont(fntMenu); 
        jmniDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtDeleteActionPerformed(evt);
            }
        });
        jmnDevices.add(jmniDelete);

        jmnuMain.add(jmnDevices);

        jmnDictionary.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jmnDictionary.setFont(fntMenu);
        jmnDictionary.setPreferredSize(dmnMenu);

        jmniTypeDictionary.setFont(fntMenu); 
        jmniTypeDictionary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jmniTypeDictionaryActionPerformed(evt);
            }
        });
        jmnDictionary.add(jmniTypeDictionary);

        jmniStateDictionary.setFont(fntMenu); 
        jmniStateDictionary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jmniStateDictionaryActionPerformed(evt);
            }
        });
        jmnDictionary.add(jmniStateDictionary);

        jmnuMain.add(jmnDictionary);

        jmnInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jmnInfo.setFont(fntMenu); 
        jmnInfo.setPreferredSize(dmnMenu);

        jmniAboutProgram.setFont(fntMenu); 
        jmniAboutProgram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtAboutProgramActionPerformed(evt);
            }
        });
        jmnInfo.add(jmniAboutProgram);

        jmniAboutAutor.setFont(fntMenu);
        jmniAboutAutor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtAboutAuthorActionPerformed(evt);
            }
        });
        jmnInfo.add(jmniAboutAutor);

        jmnuMain.add(jmnInfo);

        jmnExit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jmnExit.setFont(fntMenu); 
        jmnExit.setPreferredSize(dmnMenu);
        jmnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtExitActionPerformed(evt);
            }
        });

        jmniExit.setFont(fntMenu); 
        jmniExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtExitActionPerformed(evt);
            }
        });
        jmnExit.add(jmniExit);
        
        jmnuMain.add(jmnExit);
        setJMenuBar(jmnuMain);
        
        jpnButtons.add(jbtShowAll);
        jpnButtons.add(jbtSearch);
        jpnButtons.add(jbtAdd);
        jpnButtons.add(jbtDelete);
        jpnButtons.add(jbtEdit);
        jpnButtons.add(jbtExit);
        
        add(Box.createVerticalGlue());
        add(jspDevicesTable);
        add(Box.createVerticalGlue());
        add(jpnButtons);
        add(Box.createVerticalGlue());
        pack();
    }                     

    private void jbtExitActionPerformed(ActionEvent evt) {                                        
        System.exit(0);
    }                                       

    private void jbtAboutProgramActionPerformed(ActionEvent evt) {                                                
        MainController.showAboutProgram();
    }                                               

    private void jbtAboutAuthorActionPerformed(ActionEvent evt) {                                               
       MainController.showAboutAuthor();
    }                                              

    private void jmniTypeDictionaryActionPerformed(ActionEvent evt) {                                                   
        try {
            MainController.showTypeDictionaryView();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                  

    private void jmniStateDictionaryActionPerformed(ActionEvent evt) {                                                    
        try {
            MainController.showStateDictionaryView();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                   

    private void jbtEditActionPerformed(ActionEvent evt) {                                        
        try{
            int row=jtbDevices.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, errorNoRowSelected, errorMessage, JOptionPane.ERROR_MESSAGE);
                return;
            }
            int invNum=(int) jtbDevices.getValueAt(row, 0);
            DeviceView editForm = new DeviceView(this, bookOfDevices.getDevice(invNum));
            editForm.show();
            if (!editForm.isVisible() & !editForm.canceled) {
                bookOfDevices.modifyDevice(editForm.getDevice());
                fillTable();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                       

    private void jbtSearchActionPerformed(ActionEvent evt) {                                          
        try {
            SearchParameters findForm = new SearchParameters(this);
            findForm.show();
            if (!findForm.isVisible() & !findForm.canceled) {
                bookOfDevices.find(findForm.getFilter());
                fillTable();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                         

    private void jbtAddActionPerformed(ActionEvent evt) {                                       
        try {
            DeviceView addForm;
            addForm = new DeviceView(this);
            addForm.show();
            if (!addForm.isVisible() & !addForm.canceled) {
                bookOfDevices.addDevice(addForm.getDevice());
                fillTable();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                      

    private void jbtShowAllActionPerformed(ActionEvent evt) {                                           
        try {
            bookOfDevices=new BookOfDevices();
            fillTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                          

    private void jbtDeleteActionPerformed(ActionEvent evt) {                                          
        try {
            int row=jtbDevices.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, errorNoRowSelected, errorMessage, JOptionPane.ERROR_MESSAGE);
                return;
            }
            int invNum=(int) jtbDevices.getValueAt(row, 0);
            int reply=JOptionPane.showConfirmDialog(this, "�� ������������� ������ ������� ���������� � ������� "+invNum+"?", "�������� ����������", JOptionPane.YES_NO_OPTION);
            if (reply==JOptionPane.YES_OPTION) {
                bookOfDevices.deleteDevice(bookOfDevices.getDevice(invNum));
                fillTable();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //���������� ������� ������������ �� �����
    private void fillTable(){
         try {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"��� �", "���", "��� �������", "���������", "��������", "���������������"}, 0) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;//������ �������������� ����� �������
                }
            };
            TypeDictionary td=new TypeDictionary();
            StateDictionary sd=new StateDictionary();
            //���������� � ������� ��������� �� �����
            bookOfDevices.getDevices().forEach(device -> {
                try {
                    model.addRow(new Object[]{device.getInvNumber(),td.getValue(device.getType()),device.getYearOfManufacturing(),
                        sd.getValue(device.getState()), device.getDescription(), device.getLocation()});
                } catch (SQLException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            jtbDevices.setModel(model);
            RowSorter<TableModel> sorter = new TableRowSorter<>(model);
            jtbDevices.setRowSorter(sorter);//���������� ������������ ����� 
            jtbDevices.getColumnModel().getColumn(0).setPreferredWidth(30);
            jtbDevices.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtbDevices.getColumnModel().getColumn(2).setPreferredWidth(50);
            jtbDevices.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtbDevices.getColumnModel().getColumn(4).setPreferredWidth(170);
            jtbDevices.getColumnModel().getColumn(5).setPreferredWidth(120);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, errorDB, errorMessage, JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DictionaryView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                  
    private JScrollPane jspDevicesTable;
    private JButton jbtAdd;
    private JButton jbtDelete;
    private JButton jbtEdit;
    private JButton jbtExit;
    private JButton jbtSearch;
    private JButton jbtShowAll;
    private JMenu jmnDevices;
    private JMenu jmnDictionary;
    private JMenu jmnExit;
    private JMenu jmnInfo;
    private JMenuItem jmniAboutAutor;
    private JMenuItem jmniAboutProgram;
    private JMenuItem jmniAdd;
    private JMenuItem jmniDelete;
    private JMenuItem jmniEdit;
    private JMenuItem jmniExit;
    private JMenuItem jmniSearch;
    private JMenuItem jmniStateDictionary;
    private JMenuItem jmniTypeDictionary;
    private JMenuBar jmnuMain;
    private JTable jtbDevices;
    private JPanel jpnButtons;
}
