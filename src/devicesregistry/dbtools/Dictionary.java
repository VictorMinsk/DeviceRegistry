package devicesregistry.dbtools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
* Справочник
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class Dictionary {
    private String tableName;//название таблицы
    private String symbolsColumn;//имя столбца символов
    private String valuesColumn;//имя столбца значений
    private HashMap<String, String> items;//элементы словаря
    
    /**
     * Конструктор
     * 
     * @param tableName имя таблицы в базе данных
     * @param symbolsColumn имя столбца символов
     * @param valuesColumn имя столбца значений
     * @throws SQLException 
     */
    public Dictionary(String tableName, String symbolsColumn,
            String valuesColumn) throws SQLException{
        this.tableName=tableName;
        this.symbolsColumn=symbolsColumn;
        this.valuesColumn=valuesColumn;
    }
    
     /**
     * Возвращает значение по символу
     * 
     * @param c символ
     * @return значение, соответствующее символу
     * @throws SQLException
     */
    public String getValue(char c) throws SQLException{
    String sql = "SELECT "+valuesColumn+" FROM "+tableName+
                " WHERE "+symbolsColumn+" ='"+c+"'";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getString(1);
        return null;
    }
    
    /**
     * Возвращает символ по значению
     * 
     * @param value значение
     * @return символ, соответствующий значению
     * @throws SQLException
    */
    public char getSymbol(String value) throws SQLException{
        String sql = "SELECT "+symbolsColumn+" FROM "+tableName+
                " WHERE "+valuesColumn+" ='"+value+"'";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getString(1).charAt(0);
        return '0';
    }
    
    /**
     * Добавляет новое значение в справочник
     * 
     * @param symbol символ
     * @param value значение
     * @throws SQLException
    */ 
    public void addValue(char symbol, String value) throws SQLException{
        String sql = "INSERT INTO " + tableName + " VALUES ('" 
                + symbol + "', '" + value + "')";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * Изменяет элемент справочника
     * 
     * @param symbol символ
     * @param newValue новое значение, соответсвующее символу
     * @throws SQLException 
     */
    public void modify(char symbol, String newValue) throws SQLException {
        String sql = "UPDATE " + tableName + " SET " + valuesColumn
                + " = '" + newValue + "' WHERE " + symbolsColumn + " = '" + symbol + "'";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * Возвращает все элементы справочника
     * 
     * @return список всех элементов справочника
     */
    public HashMap<String, String> getItems(){
        return items;
    }
    
    /**
     * Загрузка из БД всех элементов справочника
     * 
     * @throws SQLException 
     */
    public void getAll() throws SQLException 
    {
        items = new HashMap<String, String>();
        String sql = "SELECT * FROM "+tableName;
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
            items.put(rs.getString(symbolsColumn), rs.getString(valuesColumn));     
    }
}
