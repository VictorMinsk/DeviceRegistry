package devicesregistry.model;

import devicesregistry.dbtools.Database;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* Книга компьютерной техники
*
* @author V.A.Nichyporuk
* @version 2.1 14.10.2022
*/
public class BookOfDevices {
    private ArrayList <Device> devices;
    
    /**
     * Конструктор
     * 
     * @throws SQLException 
     */
    public BookOfDevices() throws SQLException{
        devices=Database.getAll();
    }
    
    /**
     * Возвращает список всех устройств
     * @return список всех устройств
     */
    public ArrayList<Device>getDevices(){
        return devices;
    }
    
    /**
     * Поиск устройств в книге
     * 
     * @param searchFilter строка, содержащая условия поиска
     * @throws SQLException 
     */
    public void find(String searchFilter) throws SQLException{
        devices=Database.select(searchFilter);
    }
    
    /**
     * Удаление устройства из книги
     * 
     * @param device удаляемое устройство
     * @throws SQLException 
     */
    public void deleteDevice(Device device) throws SQLException{
        device.delete();//удаление устройства из БД
        devices.remove(device);
    }
    
    /**
     * Добавление устройства в книгу
     * 
     * @param device добавляемое устройство
     * @throws SQLException 
     */
    public void addDevice(Device device) throws SQLException{
        device.add();//добавление устройства в БД
        devices=Database.getAll();
    }
    
    /**
     * Редактирование устройства
     * 
     * @param device обновляемое устройство
     * @throws SQLException 
     */
    public void modifyDevice(Device device) throws SQLException{
        device.modify();
        /*
        Перебираем все устройства в книге, пока не найдем инвентарный номер,
        совпадающий номером редактируемого устройства.
        Изменяем значения полей найденного устройства на отредактированные.
        Выхолим из цикла.
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
     * Возвращает устройство c заданным инвентарным номером
     * 
     * @param invNum инвентарный номер
     * @return устройство
     */
    public Device getDevice(int invNum) {
        //для всех устройств в книге
        for (Device dev : devices) {
            //если объект найден
            if (dev.getInvNumber() == invNum) {
                return dev;
            }
        }
        return null;
    }
    
    /**
     * Возращает количество устройств в книге
     * 
     * @return количество устройств в книге
     */
    public int getCount(){
        return devices.size();
    }
}
