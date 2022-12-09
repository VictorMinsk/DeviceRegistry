package devicesregistry.dbtools;

import java.sql.SQLException;

/**
* ���������� ����� ���������
*
* @author V.A.Nichyporuk
* @version 2.1 09.10.2022
*/
public class TypeDictionary extends Dictionary {
    /**
     * �����������
     * 
     * @throws SQLException 
     */
    public TypeDictionary() throws SQLException {
        super("Typesymbols", "typeSymb", "type");
    }
}
