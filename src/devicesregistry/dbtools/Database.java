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
* Класс обеспечивает взаимодействие с базой данных
*
* @author V.A.Nichyporuk
* @version 2.1 21.10.2022
*/
public class Database {
    private static String mainTable="Devices";//название таблицы
    private static String dbFileName="DeviceList.db";//имя файла БД
    private static final String CON_STR = "jdbc:sqlite:"+dbFileName;//адрес БД
    /**
     * Подключение к базе данных
     */
    public static Connection dbConnection;
    
    /**
     * Устанавливает соединение с базой данных SQLite
     * 
     * @return true -- подключение установлено успешно, false -- подключение не установлено
     * @throws SQLException 
     */
    public static boolean connect() throws SQLException {        
        DriverManager.registerDriver(new JDBC());
        File dbFile=new File(dbFileName);
        if (!dbFile.exists()) //если файл БД не существует
            return false;
        dbConnection = DriverManager.getConnection(CON_STR);
        return true;
    }
    
    /**
     * Закрывает соединение с базой данных
     * 
     * @throws SQLException 
     */
    public static void disconnect() throws SQLException{
        dbConnection.close();
    }
    
    /**
     * Добавление устройства в базу данных
     * 
     * @param device добавляемое устройство
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
     * Редактирование устройства в базе данных
     * 
     * @param device обновляемое устройство
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
     * Удаляет устройство из базы данных
     * 
     * @param device удаляемое устройство
     * @throws SQLException 
     */
    public static void delete(Device device) throws SQLException{
        String sql = "DELETE FROM " + mainTable + " WHERE invNumber= "
                + device.getInvNumber();
        PreparedStatement pstmt = Database.dbConnection.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    
    /**
     * Получает список всех устройств из базы данных
     * 
     * @return список всех устройств
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
     * Поиск устройтсв в базе данных
     * 
     * @param searchFilter строка, содержащая условия для поиска
     * @return список найденных устройств
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
