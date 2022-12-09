package devicesregistry.dbtools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
* ����������
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class Dictionary {
    private String tableName;//�������� �������
    private String symbolsColumn;//��� ������� ��������
    private String valuesColumn;//��� ������� ��������
    private HashMap<String, String> items;//�������� �������
    
    /**
     * �����������
     * 
     * @param tableName ��� ������� � ���� ������
     * @param symbolsColumn ��� ������� ��������
     * @param valuesColumn ��� ������� ��������
     * @throws SQLException 
     */
    public Dictionary(String tableName, String symbolsColumn,
            String valuesColumn) throws SQLException{
        this.tableName=tableName;
        this.symbolsColumn=symbolsColumn;
        this.valuesColumn=valuesColumn;
    }
    
     /**
     * ���������� �������� �� �������
     * 
     * @param c ������
     * @return ��������, ��������������� �������
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
     * ���������� ������ �� ��������
     * 
     * @param value ��������
     * @return ������, ��������������� ��������
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
     * ��������� ����� �������� � ����������
     * 
     * @param symbol ������
     * @param value ��������
     * @throws SQLException
    */ 
    public void addValue(char symbol, String value) throws SQLException{
        String sql = "INSERT INTO " + tableName + " VALUES ('" 
                + symbol + "', '" + value + "')";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * �������� ������� �����������
     * 
     * @param symbol ������
     * @param newValue ����� ��������, �������������� �������
     * @throws SQLException 
     */
    public void modify(char symbol, String newValue) throws SQLException {
        String sql = "UPDATE " + tableName + " SET " + valuesColumn
                + " = '" + newValue + "' WHERE " + symbolsColumn + " = '" + symbol + "'";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * ���������� ��� �������� �����������
     * 
     * @return ������ ���� ��������� �����������
     */
    public HashMap<String, String> getItems(){
        return items;
    }
    
    /**
     * �������� �� �� ���� ��������� �����������
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
