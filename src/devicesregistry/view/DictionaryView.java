package devicesregistry.view;

import devicesregistry.controller.MainController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import devicesregistry.dbtools.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
* Окно для просмотра справочника
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class DictionaryView extends JFrame {
    private String title;
    private Dictionary dictionary;
 
    /**
     * Создает окно для просмотра справочника
     * 
     * @param dictionary словарь
     * @param title заголовок окна
     * @throws SQLException 
     */
    public DictionaryView(Dictionary dictionary, String title) throws SQLException {
        this.title=title;
        setTitle(title);
        initComponents();
        this.dictionary=dictionary;
        fillTable();
    }
    
    /**
     * Заполнение таблицы
     */
    private void fillTable(){
         try {
            dictionary.getAll();
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"Символ", "Значение"}, 0
            ){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            dictionary.getItems().forEach((key, value) -> {
                model.addRow(new Object[]{key, value});
            });
          jtbDictionary.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка при работе с базой данных", "Error message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DictionaryView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                      
    private void initComponents() {
        jspDictionaryTable = new JScrollPane();
        jtbDictionary = new JTable();
        jbtAdd = new JButton("Добавить");
        jbtBack = new JButton("Назад");
        jbtEdit = new JButton("Редактировать");
        jpnButtons=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jspDictionaryTable.setViewportView(jtbDictionary);
        jspDictionaryTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(java.awt.SystemColor.controlShadow, 1)));

        jbtAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });

        jbtBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtBackActionPerformed(evt);
            }
        });

        jbtEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtEditActionPerformed(evt);
            }
        });
        
        jpnButtons.add(jbtBack);
        jpnButtons.add(jbtAdd);
        jpnButtons.add(jbtEdit);
        
        add(Box.createVerticalGlue());
        add(jspDictionaryTable);
        add(Box.createVerticalGlue());
        add(jpnButtons);
        add(Box.createVerticalGlue());
        
        setBounds(0, 0, 360, 300);
    }                       

    private void jbtAddActionPerformed(ActionEvent evt) {                                       
        DictionaryItemView addForm = new DictionaryItemView(this);
        addForm.show();
        if (!addForm.isVisible() & !addForm.canceled) try {
            if (dictionary.getValue(addForm.getSymbol()) != null) {
                JOptionPane.showMessageDialog(this, "Элемент с таким символом уже присутствует в ", "Error message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dictionary.addValue(addForm.getSymbol(), addForm.getValue());
            fillTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка при работе с базой данных", "Error message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DictionaryView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                      

    private void jbtBackActionPerformed(ActionEvent evt) {                                        
        if (dictionary.getClass() == TypeDictionary.class)
            MainController.closeTypeDictionaryView();
        else
            MainController.closeStateDictionaryView();
    }                                       

    private void jbtEditActionPerformed(ActionEvent evt) {                                        
        int row=jtbDictionary.getSelectedRow();
        if (row<0) {
            JOptionPane.showMessageDialog(this, "Не выбрано ни одной строки", "Error message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        char symbol=jtbDictionary.getValueAt(row, 0).toString().charAt(0);
        String value=jtbDictionary.getValueAt(row, 1).toString();
        DictionaryItemView editForm = new DictionaryItemView(this, symbol, value);
        editForm.show();
        if (!editForm.isVisible() & !editForm.canceled) try {
            dictionary.modify(symbol, editForm.getValue());
            fillTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка при работе с базой данных", "Error message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DictionaryView.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }                                       

    private void formWindowClosing(WindowEvent evt) {                                   
        if (dictionary.getClass() == TypeDictionary.class)
            MainController.closeTypeDictionaryView();
        else
            MainController.closeStateDictionaryView();
    }                                  
                   
    private JScrollPane jspDictionaryTable;
    private JButton jbtAdd;
    private JButton jbtBack;
    private JButton jbtEdit;
    private JTable jtbDictionary;  
    private JPanel jpnButtons;
}
