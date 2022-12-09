package devicesregistry.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
* Форма для редактирования/удаления элемента справочника
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class DictionaryItemView extends JDialog {
    private char symbol;
    private String value;
    /**
     * true -- действия отменены, false -- иначе
     */
    public boolean canceled;
    
    /**
     * Создает форму для добавления нового элемента справочника
     * 
     * @param parent родительское окно
     */
    public DictionaryItemView(Frame parent) {
        super(parent, true);
        setTitle("Добавление элемента");
        initComponents();
    }
    
    /**
     * Создает форму для редактирования элемента справочника
     * 
     * @param parent родительское окно
     * @param symbol символ
     * @param value значение
     */
    public DictionaryItemView(Frame parent, char symbol, String value) {
        super(parent, true);
        setTitle("Редактирование элемента");
        this.symbol=symbol;
        this.value=value;
        initComponents();
        jtxtSymbol.setText(Character.toString(symbol));
        jtxtValue.setText(value);
        jtxtSymbol.setEditable(false);
    }
                           
    private void initComponents() {
        jlbSymbol = new JLabel("Символ:");
        jtxtSymbol = new JTextField(22);
        jlbValue = new JLabel("Значение:");
        jtxtValue = new JTextField(22);
        jbtCancel = new JButton("Отменить");
        jbtOK = new JButton("Принять");
        jpnButtons=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
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

        jpnButtons.add(jbtCancel);
        jpnButtons.add(Box.createRigidArea(new Dimension(25, 0)));
        jpnButtons.add(jbtOK);
        
        add(jlbSymbol);
        add(jtxtSymbol);
        add(jlbValue);
        add(jtxtValue);
        add(jpnButtons);
        
        setBounds(0,0, 240, 210);
    }                      

    private void jbtCancelActionPerformed(ActionEvent evt) {                                          
        canceled=true;
        this.dispose();
    }                                         

    private void jbtOKActionPerformed(ActionEvent evt) {                                      
        String s=jtxtSymbol.getText();
        if (s.length()==1) symbol=s.charAt(0);
        else {
            JOptionPane.showMessageDialog(this, "Неверный ввод", "Error message", JOptionPane.ERROR_MESSAGE);
            canceled = true;
            return;
        }
        value = jtxtValue.getText();
        canceled=false;
        this.dispose();
    }                                     

    private void formWindowClosing(WindowEvent evt) {                                   
        canceled=true;
    }
    
    /**
     * Возвращает введенное в форме значение символа
     * @return символ
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Возвращает введенное в форме значение ключа
     * @return значение
     */
    public String getValue() {
        return value;
    }
                   
    private JButton jbtCancel;
    private JButton jbtOK;
    private JLabel jlbSymbol;
    private JLabel jlbValue;
    private JTextField jtxtSymbol;
    private JTextField jtxtValue;
    private JPanel jpnButtons;
}
