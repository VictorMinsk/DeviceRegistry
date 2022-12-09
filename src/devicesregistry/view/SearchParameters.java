package devicesregistry.view;

import devicesregistry.dbtools.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
* ����� ��� ����� ���������� ������ ���������
*
* @author V.A.Nichyporuk
* @version 2.1 01.11.2022
*/
public class SearchParameters extends JDialog {
     /**
     * true -- �������� ��������, false -- �����
     */
    public boolean canceled;
    private String filter;//������, ���������� ������� ������
    private TypeDictionary td;
    private StateDictionary sd;
    
    /**
     * ������� ����� ��� ����� ���������� ������
     * 
     * @param parent ������������ ����
     * @throws SQLException 
     */
    public SearchParameters(Frame parent) throws SQLException {
        super(parent, true);
        initComponents();
        td = new TypeDictionary();
        sd = new StateDictionary();
        td.getAll();
        sd.getAll();
        jcmbType.setModel(new DefaultComboBoxModel(td.getItems().values().toArray()));
        jcmbState.setModel(new DefaultComboBoxModel(sd.getItems().values().toArray()));
        Calendar cal = Calendar.getInstance();
        jftfYearTo.setText(String.valueOf(cal.get(Calendar.YEAR)));
    } 
                        
    private void initComponents() {
        jbtCancel = new JButton("��������");
        jbtOK = new JButton("�������");
        jchbState = new JCheckBox("���������");
        jchbType = new JCheckBox("���");
        jchbYear = new JCheckBox("��� �������");
        jcmbState = new JComboBox<>();
        jcmbType = new JComboBox<>();
        jlbFrom = new JLabel("��");
        jlbTo = new JLabel("��");
        jftfYearFrom = new JFormattedTextField();
        jftfYearTo = new JFormattedTextField();
        jlbPrompt = new JLabel();
        jpnYearFrom=new JPanel();
        jpnYearTo=new JPanel();
        jpnParameters=new JPanel(new GridLayout(0, 2, 10, 10));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("������ ������");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                searchParametersClosing(evt);
            }
        });

        jbtCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtCancelActionPerformed(evt);
            }
        });

        jbtOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtOKActionPerformed(evt);
            }
        });

        try {
            jftfYearFrom.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("####")));
        } catch (ParseException ex) {
        }

        try {
            jftfYearTo.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("####")));
        } catch (ParseException ex) {
        }

        jlbPrompt.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        jlbPrompt.setText("�������� ����������� ���������:");
        jlbPrompt.setAlignmentX(CENTER_ALIGNMENT);
        
        
        jpnYearFrom.setLayout(new BoxLayout(jpnYearFrom, BoxLayout.LINE_AXIS));
        jpnYearFrom.add(jlbFrom);
        jpnYearFrom.add(Box.createRigidArea(new Dimension(20, 20)));
        jpnYearFrom.add(jftfYearFrom);
        
        jpnYearTo.setLayout(new BoxLayout(jpnYearTo, BoxLayout.LINE_AXIS));
        jpnYearTo.add(jlbTo);
        jpnYearTo.add(Box.createRigidArea(new Dimension(20, 20)));
        jpnYearTo.add(jftfYearTo);
        
        jpnParameters.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpnParameters.add(jchbState);
        jpnParameters.add(jcmbState);
        jpnParameters.add(jchbType);
        jpnParameters.add(jcmbType);
        jpnParameters.add(jchbYear);
        jpnParameters.add(jpnYearFrom);
        jpnParameters.add(Box.createHorizontalBox());
        jpnParameters.add(jpnYearTo);
        jpnParameters.add(jbtCancel);
        jpnParameters.add(jbtOK);
        
        add(Box.createVerticalGlue());
        add(jlbPrompt);
        add(Box.createVerticalGlue());
        add(jpnParameters);

        setBounds(0, 0, 300, 250);
    }                       

    private void jbtCancelActionPerformed(ActionEvent evt) {                                          
        canceled = true;
        this.dispose();
    }                                         

    private void jbtOKActionPerformed(ActionEvent evt) {                                      
        try {
            filter="";
            canceled = false;
            if (jchbState.isSelected())//���� ������� ��������� ����������
                filter += "state='" + sd.getSymbol((String) jcmbState.getSelectedItem()) + "'";
            if (jchbType.isSelected()){//���� ������ ��� ���������� 
                if (!"".equals(filter))//���� ������ ������� ������ �� ������, �� ����������� AND
                    filter+=" AND ";
                filter+="type='"+td.getSymbol((String)jcmbType.getSelectedItem())+"'";
            }
            if (jchbYear.isSelected()) {//���� ������ ��� �������
                if (!"".equals(filter)) {//���� ������ ������� ������ �� ������, �� ����������� AND
                    filter += " AND ";
                }
                String yearFrom = jftfYearFrom.getText();
                if (yearFrom.equals("    ")) {//���� ������ ������� ���� ������� �� �������
                    yearFrom = "0";
                } else {
                    Calendar cal = Calendar.getInstance();
                    if (yearFrom.compareTo(Integer.toString(cal.get(Calendar.YEAR))) > 0) {
                        JOptionPane.showMessageDialog(this, "��� �� ����� ���� ������ ��������", "Error message", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                filter += "yearOfManufacturing BETWEEN '" + yearFrom + "' AND '" + jftfYearTo.getText() + "'";
            } 
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(SearchParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void searchParametersClosing(WindowEvent evt) {                                   
       canceled=true;
    } 
    
    /**
     * ���������� ������, ���������� ��������� ������������� ������� ������
     * @return filter ������, ���������� ��������� ������������� ������� ������
     */
    public String getFilter(){
        return filter;
    }     
    
    private JButton jbtCancel;
    private JButton jbtOK;
    private JCheckBox jchbState;
    private JCheckBox jchbType;
    private JCheckBox jchbYear;
    private JComboBox<String> jcmbState;
    private JComboBox<String> jcmbType;
    private JFormattedTextField jftfYearFrom;
    private JFormattedTextField jftfYearTo;
    private JLabel jlbFrom;
    private JLabel jlbPrompt;
    private JLabel jlbTo;
    private JPanel jpnYearFrom;
    private JPanel jpnYearTo;
    private JPanel jpnParameters; 
}
