package devicesregistry.model;

import devicesregistry.dbtools.Database;
import java.sql.SQLException;

/**
* ����������
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class Device {
    private int invNumber;//����������� �����
    private char type;//���
    private short yearOfManufacturing;//��� �������
    private char state;//���������
    private String description;//��������
    private String location;//���������������
    
    /**
     * �����������
     * 
     * @param invNumber ����������� �����
     * @param type ��� 
     * @param state ���������
     * @param yearOfManufacturing ��� �������
     * @param description �������� ����������
     * @param location ���������������
     */
    public Device(int invNumber, char type, char state, short yearOfManufacturing,
            String description, String location) {
        this.invNumber = invNumber;
        this.type = type;
        this.yearOfManufacturing = yearOfManufacturing;
        this.state = state;
        this.description = description;
        this.location = location;
    }
    
    /**
     * ���������� ����������� �����
     * @return ����������� �����
     */
    public int getInvNumber() {
        return invNumber;
    }

    /**
     * ���������� ��� ����������
     * @return ��� ����������
     */
    public char getType() {
        return type;
    }

    /**
     * ���������� ��� �������
     * @return ��� �������
     */
    public short getYearOfManufacturing() {
        return yearOfManufacturing;
    }

    /**
     * ���������� ��������� ����������
     * @return ��������� ����������
     */
    public char getState() {
        return state;
    }

    /**
     * ���������� �������� ����������
     * @return �������� ����������
     */
    public String getDescription() {
        return description;
    }

    /**
     * ���������� ���������������
     * @return ���������������
     */
    public String getLocation() {
        return location;
    }

    /**
     * ������������� ��� ����������
     * @param type ��� ����������
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * ������������� ��� �������
     * @param yearOfManufacturing ��� ������� 
     */
    public void setYearOfManufacturing(short yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }

    /**
     * ������������� ��������� ����������
     * @param state ��������� ����������
     */
    public void setState(char state) {
        this.state = state;
    }

    /**
     * ������������� �������� ����������
     * @param description �������� ���������
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * ������������� ���������������
     * @param location ���������������
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * ������������� ����������� �����
     * @param invNumber ����������� �����
     */
    public void setInvNumber(int invNumber) {
        this.invNumber=invNumber;
    }

    /**
     * �������� ����������
     * 
     * @throws SQLException 
     */
    public void delete() throws SQLException{
        Database.delete(this);
    }
    
    /**
     * �������������� ����������
     * 
     * @throws SQLException 
     */
    public void modify() throws SQLException{
        Database.modify(this);
    }
    
    /**
     * ���������� ���������� 
     * 
     * @throws SQLException 
     */
    public void add() throws SQLException{
        Database.insert(this);
    }
}
