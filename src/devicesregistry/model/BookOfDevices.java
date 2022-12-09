package devicesregistry.model;

import devicesregistry.dbtools.Database;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* ����� ������������ �������
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class BookOfDevices {
    private ArrayList <Device> devices;
    
    /**
     * �����������
     * 
     * @throws SQLException 
     */
    public BookOfDevices() throws SQLException{
        devices=Database.getAll();
    }
    
    /**
     * ���������� ������ ���� ���������
     * @return ������ ���� ���������
     */
    public ArrayList<Device>getDevices(){
        return devices;
    }
    
    /**
     * ����� ��������� � �����
     * 
     * @param searchFilter ������, ���������� ������� ������
     * @throws SQLException 
     */
    public void find(String searchFilter) throws SQLException{
        devices=Database.select(searchFilter);
    }
    
    /**
     * �������� ���������� �� �����
     * 
     * @param device ��������� ����������
     * @throws SQLException 
     */
    public void deleteDevice(Device device) throws SQLException{
        device.delete();//�������� ���������� �� ��
        devices.remove(device);
    }
    
    /**
     * ���������� ���������� � �����
     * 
     * @param device ����������� ����������
     * @throws SQLException 
     */
    public void addDevice(Device device) throws SQLException{
        device.add();//���������� ���������� � ��
        devices=Database.getAll();
    }
    
    /**
     * �������������� ����������
     * 
     * @param device ����������� ����������
     * @throws SQLException 
     */
    public void modifyDevice(Device device) throws SQLException{
        device.modify();
        /*
        ���������� ��� ���������� � �����, ���� �� ������ ����������� �����,
        ����������� ������� �������������� ����������.
        �������� �������� ����� ���������� ���������� �� �����������������.
        ������� �� �����.
        */
        for (Device dev : devices) {
            if (device.getInvNumber() == dev.getInvNumber()) {
                dev.setDescription(device.getDescription());
                dev.setLocation(device.getLocation());
                dev.setState(device.getState());
                dev.setType(device.getType());
                dev.setYearOfManufacturing(device.getYearOfManufacturing());
                return;
            }
        }
    }
    
    /**
     * ���������� ���������� c �������� ����������� �������
     * 
     * @param invNum ����������� �����
     * @return ����������
     */
    public Device getDevice(int invNum) {
        //��� ���� ��������� � �����
        for (Device dev : devices) {
            //���� ������ ������
            if (dev.getInvNumber() == invNum) {
                return dev;
            }
        }
        return null;
    }
    
    /**
     * ��������� ���������� ��������� � �����
     * 
     * @return ���������� ��������� � �����
     */
    public int getCount(){
        return devices.size();
    }
}
