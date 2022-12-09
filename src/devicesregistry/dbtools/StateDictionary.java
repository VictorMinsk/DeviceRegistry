package devicesregistry.dbtools;

import java.sql.SQLException;

/**
* Справочник возможных состояний устройств
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class StateDictionary extends Dictionary{
    /**
     * Конструктор
     * 
     * @throws SQLException 
     */
    public StateDictionary() throws SQLException {
        super("StateSymbols", "stateSymb", "state");
    }
}
