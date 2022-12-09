package devicesregistry.controller;

import devicesregistry.dbtools.*;
import java.sql.SQLException;
import devicesregistry.view.*;

/**
* Класс управления
* <br>
* <i>Контроль переключения окон приложения</i>
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class MainController {
    private static SplashScreen splashScreen;//стартовое окно
    private static MainWindow mainWindow;//главное окно 
    private static AboutAuthor aboutAuthor;//окно "Об авторе"
    private static AboutProgram aboutProgram;//окно "О программе"
    private static DictionaryView stateDictionaryView;//окно словаря состояний
    private static DictionaryView typeDictionaryView;//окно словаря типов
    private static boolean isOpenedAboutAuthor;//открыто окно "Об авторе"
    private static boolean isOpenedAboutProgram;//открыто окно "О программе"
    private static boolean isOpenedStateDictionary;//открыто окно словаря состояний
    private static boolean isOpenedTypeDictionary;//открыто окно словаря типов

    /**
     * Точка входа в приложение
     * 
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        showSplashScreen();
    }
    
    /**
     * Вызывает стартовое окно приложения
     */
    public static void showSplashScreen(){
        splashScreen=new SplashScreen(); 
        splashScreen.setVisible(true);
    }
    
    /**
     * Вызывает главное окно приложения
     * 
     * @throws SQLException 
     */
    public static void showMainWindow() throws SQLException{
        splashScreen.dispose();
        mainWindow=new MainWindow(); 
        mainWindow.setVisible(true);
    }
    
    /**
     * Вызывает окно "О программе".
     * <br>
     * Если оно уже открыто, то поднимает его на передний план
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
     * Вызывает окно "Об авторе".
     * <br>
     * Если оно уже открыто, то поднимает его на передний план
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
     * Вызывает справочник состояний.
     * <br>
     * Если он уже открыт, то поднимает его на передний план
     * 
     * @throws SQLException 
     */
    public static void showStateDictionaryView() throws SQLException{
        if (!isOpenedStateDictionary) {
            Dictionary stateDictionary=new StateDictionary();
            stateDictionaryView = new DictionaryView(stateDictionary, 
                    "Состояние устройства");
            stateDictionaryView.setVisible(true);
            isOpenedStateDictionary = true;
        } else
            stateDictionaryView.toFront();
    }
    
    /**
     * Вызывает справочник типов.
     * <br>
     * Если он уже открыт, то поднимает его на передний план
     * 
     * @throws SQLException 
     */
    public static void showTypeDictionaryView() throws SQLException{
        if (!isOpenedTypeDictionary) {
            Dictionary typeDictionary=new TypeDictionary();
            typeDictionaryView = new DictionaryView(typeDictionary, "Тип устройства");
            typeDictionaryView.setVisible(true);
            isOpenedTypeDictionary = true;
        } else
            typeDictionaryView.toFront();
    }
    
    /**
     * Закрывает окно "О программе"
     */
    public static void closeAboutProgram(){
        aboutProgram.dispose();
        isOpenedAboutProgram=false;
    }
    
    /**
     * Закрывает окно "Об авторе"
     */
    public static void closeAboutAuthor(){
        aboutAuthor.dispose();
        isOpenedAboutAuthor=false;
    }
    
    /**
     * Закрывает справочник типов
     */
    public static void closeTypeDictionaryView(){
        typeDictionaryView.dispose();
        isOpenedTypeDictionary=false;
    }
    
    /**
     * Закрывает справочник состояний
     */
    public static void closeStateDictionaryView(){
        stateDictionaryView.dispose();
        isOpenedStateDictionary=false;
    }
}
