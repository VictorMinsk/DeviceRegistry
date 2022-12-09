package devicesregistry.view;

import devicesregistry.model.Device;
import devicesregistry.dbtools.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
* Форма для редактирования/добавления устройства
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class DeviceView extends JDialog {
    private TypeDictionary td;
    private StateDictionary sd;
    private Device device;//устройство
    private boolean addition;//true -- добавление, false -- редактирование
    /**
     * true -- действия отменены, false -- иначе
     */
    public boolean canceled;
    private int invNum;

    /**
     * Создает форму для добавления нового устройства
     * 
     * @param parent родительское окно
     * @throws SQLException 
     */
    public DeviceView(Frame parent) throws SQLException {
        super(parent, true);
        initComponents();
        setTitle("Добавление устройства");
        addition=true;
        Calendar cal = Calendar.getInstance();
        jftfYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        invNum=0;
    }
    
     /**
     * Создает форму для редактирования устройства
     * 
     * @param parent родительское окно
     * @param device устройство, которое будет модифицироваться
     * @throws SQLException 
     */
    public DeviceView(java.awt.Frame parent, Device device) throws SQLException {
        super(parent, true);
        initComponents();
        setTitle("Редактирование устройства");
        addition=false;
        this.device=device;
        jcmbType.setSelectedItem(td.getValue(device.getType()));
        jcmbState.setSelectedItem(sd.getValue(device.getState()));
        jftfYear.setText(String.valueOf(device.getYearOfManufacturing()));
        jtxtDescription.setText(device.getDescription());
        jtxtLocation.setText(device.getLocation());
        invNum=device.getInvNumber();
    }
              
    private void initComponents() throws SQLException {
        jlbType = new JLabel("Тип:");
        jlbYear = new JLabel("Год выпуска:");
        jlbDescription = new JLabel("Описание:");
        jlbLocation = new JLabel("Местонахождение:");
        jlbState = new JLabel("Состояние:");
        jcmbType = new JComboBox<>();    
        jcmbState = new JComboBox<>();
        jtxtDescription = new JTextField();
        jtxtLocation = new JTextField();
        jftfYear = new JFormattedTextField();
        jbtCancel = new JButton("Отменить");
        jbtOK = new JButton("Принять");
        jpnDeviceInfo=new JPanel(new GridLayout(0, 1, 0, 0));
        jpnButtons=new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                deviceViewClosing(evt);
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

        td = new TypeDictionary();
        sd = new StateDictionary();
        td.getAll();
        sd.getAll();
        jcmbType.setModel(new DefaultComboBoxModel(td.getItems().values().toArray()));
        jcmbState.setModel(new DefaultComboBoxModel(sd.getItems().values().toArray()));
        
        try {
            jftfYear.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(this, "Неверный ввод", "Error message", JOptionPane.ERROR_MESSAGE);
        }
        
        jpnButtons.add(jbtCancel);
        jpnButtons.add(jbtOK);
        
        jpnDeviceInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpnDeviceInfo.add(jlbType);
        jpnDeviceInfo.add(jcmbType);
        jpnDeviceInfo.add(jlbYear);
        jpnDeviceInfo.add(jftfYear);
        jpnDeviceInfo.add(jlbState);
        jpnDeviceInfo.add(jcmbState);
        jpnDeviceInfo.add(jlbDescription);
        jpnDeviceInfo.add(jtxtDescription);
        jpnDeviceInfo.add(jlbLocation);
        jpnDeviceInfo.add(jtxtLocation);
        
        add(jpnDeviceInfo);
        add(jpnButtons);

        setBounds(0,0,270,380);
    }                       

    private void jbtCancelActionPerformed(ActionEvent evt) {                                          
        canceled=true;
        this.dispose();
    }                                         

    private void jbtOKActionPerformed(ActionEvent evt) {                                      
        try {
            Calendar cal = Calendar.getInstance();
            short year=(short)Integer.parseInt(jftfYear.getText());
            if (year > cal.get(Calendar.YEAR)) {
                JOptionPane.showMessageDialog(this, "Год не может быть больше текущего", "Error message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String location=jtxtLocation.getText();
            if ("".equals(location)) {
                JOptionPane.showMessageDialog(this, "Местоположение не может быть пустым", "Error message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String description=jtxtDescription.getText();
            if ("".equals(description)) {
                JOptionPane.showMessageDialog(this, "Описание не может быть пустым", "Error message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            char type=td.getSymbol((String) jcmbType.getSelectedItem());
            char state=sd.getSymbol((String) jcmbState.getSelectedItem());
            device=new Device(invNum, type, state, year, description, location);
            if (addition){
                int reply = JOptionPane.showConfirmDialog(this, "Вы действительно новое устройство в кингу?", "Редактирование устройства", JOptionPane.YES_NO_OPTION);
                canceled=(reply != JOptionPane.YES_OPTION);
            }
            else {
                int reply = JOptionPane.showConfirmDialog(this, "Вы действительно хотите внести изменения в запись?", "Редактирование устройства", JOptionPane.YES_NO_OPTION);
                canceled=(reply != JOptionPane.YES_OPTION);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeviceView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }
    
    private void deviceViewClosing(WindowEvent evt){
        canceled=true;
    }
    
    /**
     * Возвращает устройство с полями, заданными в форме
     * @return устройство
     */
    public Device getDevice(){
        return device;
    }
                   
    private JButton jbtCancel;
    private JButton jbtOK;
    private JComboBox<String> jcmbState;
    private JComboBox<String> jcmbType;
    private JFormattedTextField jftfYear;
    private JLabel jlbDescription;
    private JLabel jlbLocation;
    private JLabel jlbState;
    private JLabel jlbType;
    private JLabel jlbYear;
    private JTextField jtxtDescription;
    private JTextField jtxtLocation;
    private JPanel jpnButtons;
    private JPanel jpnDeviceInfo; 
}
