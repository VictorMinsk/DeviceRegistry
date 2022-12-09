package devicesregistry.dbtools;

import devicesregistry.model.Device;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.JDBC;

/**
* ����� ������������ �������������� � ����� ������
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class Database {
    private static String mainTable="Devices";//�������� �������
    private static String dbFileName="DeviceList.db";//��� ����� ��
    private static final String CON_STR = "jdbc:sqlite:"+dbFileName;//����� ��
    /**
     * ����������� � ���� ������
     */
    public static Connection dbConnection;
    
    /**
     * ������������� ���������� � ����� ������ SQLite
     * 
     * @return true -- ����������� ����������� �������, false -- ����������� �� �����������
     * @throws SQLException 
     */
    public static boolean connect() throws SQLException {        
        DriverManager.registerDriver(new JDBC());
        File dbFile=new File(dbFileName);
        if (!dbFile.exists()) //���� ���� �� �� ����������
            return false;
        dbConnection = DriverManager.getConnection(CON_STR);
        return true;
    }
    
    /**
     * ��������� ���������� � ����� ������
     * 
     * @throws SQLException 
     */
    public static void disconnect() throws SQLException{
        dbConnection.close();
    }
    
    /**
     * ���������� ���������� � ���� ������
     * 
     * @param device ����������� ����������
     * @throws SQLException 
     */
    public static void insert(Device device) throws SQLException{
        String sql = "INSERT INTO " + mainTable
                + " (yearOfManufacturing, type, state, notes, location)"
                + " VALUES ('" + device.getYearOfManufacturing() + "', '"
                + device.getType() + "', '";
        sql += device.getState() + "', '" + device.getDescription() + "', '"
                + device.getLocation() + "')";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * �������������� ���������� � ���� ������
     * 
     * @param device ����������� ����������
     * @throws SQLException 
     */
    public static void modify(Device device) throws SQLException{
        String sql = "UPDATE " + mainTable + " SET yearOfManufacturing ='"
                + device.getYearOfManufacturing() + "',type = '" + device.getType()
                + "', state='" + device.getState() + "', location='"
                + device.getLocation() + "', notes='" + device.getDescription()
                + "' WHERE invNumber = '" + device.getInvNumber() + "'";
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    
    /**
     * ������� ���������� �� ���� ������
     * 
     * @param device ��������� ����������
     * @throws SQLException 
     */
    public static void delete(Device device) throws SQLException{
        String sql = "DELETE FROM " + mainTable + " WHERE invNumber= "
                + device.getInvNumber();
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * �������� ������ ���� ��������� �� ���� ������
     * 
     * @return ������ ���� ���������
     * @throws SQLException 
     */
    public static ArrayList <Device> getAll() throws SQLException{
        ArrayList<Device> devices = new ArrayList<Device>();
        String sql = "SELECT * FROM " + mainTable;
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
            devices.add(new Device(rs.getInt("invNumber"),
                    rs.getString("type").charAt(0), rs.getString("state").charAt(0),
                    rs.getShort("yearOfManufacturing"),
                    rs.getString("notes"), rs.getString("location")));
        return devices;
    }
    
     /**
     * ����� ��������� � ���� ������
     * 
     * @param searchFilter ������, ���������� ������� ��� ������
     * @return ������ ��������� ���������
     * @throws SQLException 
     */
    public static ArrayList<Device> select(String searchFilter)
            throws SQLException {
        if ("".equals(searchFilter)) {
            return getAll();
        }
        ArrayList<Device> devices = new ArrayList<Device>();
        String sql = "SELECT * FROM " + mainTable + "  WHERE " + searchFilter;
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            devices.add(new Device(rs.getInt("invNumber"), rs.getString("type").charAt(0),
                    rs.getString("state").charAt(0), rs.getShort("yearOfManufacturing"),
                    rs.getString("notes"), rs.getString("location")));
        }
        return devices;
    }
}
