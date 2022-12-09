package devicesregistry.controller;

import devicesregistry.dbtools.*;
import java.sql.SQLException;
import devicesregistry.view.*;

/**
* ����� ����������
* <br>
* <i>�������� ������������ ���� ����������</i>
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class MainController {
    private static SplashScreen splashScreen;//��������� ����
    private static MainWindow mainWindow;//������� ���� 
    private static AboutAuthor aboutAuthor;//���� "�� ������"
    private static AboutProgram aboutProgram;//���� "� ���������"
    private static DictionaryView stateDictionaryView;//���� ������� ���������
    private static DictionaryView typeDictionaryView;//���� ������� �����
    private static boolean isOpenedAboutAuthor;//������� ���� "�� ������"
    private static boolean isOpenedAboutProgram;//������� ���� "� ���������"
    private static boolean isOpenedStateDictionary;//������� ���� ������� ���������
    private static boolean isOpenedTypeDictionary;//������� ���� ������� �����

    /**
     * ����� ����� � ����������
     * 
     * @param args ��������� ��������� ������
     */
    public static void main(String[] args) {
        showSplashScreen();
    }
    
    /**
     * �������� ��������� ���� ����������
     */
    public static void showSplashScreen(){
        splashScreen=new SplashScreen(); 
        splashScreen.setVisible(true);
    }
    
    /**
     * �������� ������� ���� ����������
     * 
     * @throws SQLException 
     */
    public static void showMainWindow() throws SQLException{
        splashScreen.dispose();
        mainWindow=new MainWindow(); 
        mainWindow.setVisible(true);
    }
    
    /**
     * �������� ���� "� ���������".
     * <br>
     * ���� ��� ��� �������, �� ��������� ��� �� �������� ����
     */
    public static void showAboutProgram(){
        if (!isOpenedAboutProgram) {
            aboutProgram = new AboutProgram();
            aboutProgram.show();
            isOpenedAboutProgram=true;
        }
        else aboutProgram.toFront();
    }
    
    /**
     * �������� ���� "�� ������".
     * <br>
     * ���� ��� ��� �������, �� ��������� ��� �� �������� ����
     */
    public static void showAboutAuthor() {
        if (!isOpenedAboutAuthor) {
            aboutAuthor = new AboutAuthor();
            aboutAuthor.show();
            isOpenedAboutAuthor = true;
        } else
            aboutAuthor.toFront();
    }
    
    /**
     * �������� ���������� ���������.
     * <br>
     * ���� �� ��� ������, �� ��������� ��� �� �������� ����
     * 
     * @throws SQLException 
     */
    public static void showStateDictionaryView() throws SQLException{
        if (!isOpenedStateDictionary) {
            Dictionary stateDictionary=new StateDictionary();
            stateDictionaryView = new DictionaryView(stateDictionary, 
                    "��������� ����������");
            stateDictionaryView.setVisible(true);
            isOpenedStateDictionary = true;
        } else
            stateDictionaryView.toFront();
    }
    
    /**
     * �������� ���������� �����.
     * <br>
     * ���� �� ��� ������, �� ��������� ��� �� �������� ����
     * 
     * @throws SQLException 
     */
    public static void showTypeDictionaryView() throws SQLException{
        if (!isOpenedTypeDictionary) {
            Dictionary typeDictionary=new TypeDictionary();
            typeDictionaryView = new DictionaryView(typeDictionary, "��� ����������");
            typeDictionaryView.setVisible(true);
            isOpenedTypeDictionary = true;
        } else
            typeDictionaryView.toFront();
    }
    
    /**
     * ��������� ���� "� ���������"
     */
    public static void closeAboutProgram(){
        aboutProgram.dispose();
        isOpenedAboutProgram=false;
    }
    
    /**
     * ��������� ���� "�� ������"
     */
    public static void closeAboutAuthor(){
        aboutAuthor.dispose();
        isOpenedAboutAuthor=false;
    }
    
    /**
     * ��������� ���������� �����
     */
    public static void closeTypeDictionaryView(){
        typeDictionaryView.dispose();
        isOpenedTypeDictionary=false;
    }
    
    /**
     * ��������� ���������� ���������
     */
    public static void closeStateDictionaryView(){
        stateDictionaryView.dispose();
        isOpenedStateDictionary=false;
    }
}
