package devicesregistry.model;

import devicesregistry.dbtools.Database;
import java.sql.SQLException;

/**
* ”стройство
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class Device {
    private int invNumber;//инвентарный номер
    private char type;//тип
    private short yearOfManufacturing;//год выпуска
    private char state;//состо€ние
    private String description;//описание
    private String location;//местонахождение
    
    /**
     *  онструктор
     * 
     * @param invNumber инвентарный номер
     * @param type тип 
     * @param state состо€ние
     * @param yearOfManufacturing год выпуска
     * @param description описание устройства
     * @param location местонахождение
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
     * ¬овзращает инвентарный номер
     * @return инвентарный номер
     */
    public int getInvNumber() {
        return invNumber;
    }

    /**
     * ¬озвращает тип устройства
     * @return тип устройства
     */
    public char getType() {
        return type;
    }

    /**
     * ¬озвращает год выпуска
     * @return год выпуска
     */
    public short getYearOfManufacturing() {
        return yearOfManufacturing;
    }

    /**
     * ¬озвращает состо€ние устройства
     * @return состо€ние устройства
     */
    public char getState() {
        return state;
    }

    /**
     * ¬озвращает описание устройства
     * @return описание устройства
     */
    public String getDescription() {
        return description;
    }

    /**
     * ¬овзращает местонахождение
     * @return местонахождение
     */
    public String getLocation() {
        return location;
    }

    /**
     * ”станавливает тип устройства
     * @param type тип устройства
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * ”станавливает год выпуска
     * @param yearOfManufacturing год выпуска 
     */
    public void setYearOfManufacturing(short yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }

    /**
     * ”станавливает состо€ние устройства
     * @param state состо€ние устройства
     */
    public void setState(char state) {
        this.state = state;
    }

    /**
     * ”станавливает описание устройства
     * @param description описание усройства
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * ”станавливает местонахождение
     * @param location местонахождение
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * ”станавливает инвентарный номер
     * @param invNumber инвентарный номер
     */
    public void setInvNumber(int invNumber) {
        this.invNumber=invNumber;
    }

    /**
     * ”даление устройства
     * 
     * @throws SQLException 
     */
    public void delete() throws SQLException{
        Database.delete(this);
    }
    
    /**
     * –едактирование устройства
     * 
     * @throws SQLException 
     */
    public void modify() throws SQLException{
        Database.modify(this);
    }
    
    /**
     * ƒобавление устройства 
     * 
     * @throws SQLException 
     */
    public void add() throws SQLException{
        Database.insert(this);
    }
}
