
package salomon.engine.platform.data.common;

/**
 * 
 * Class holds value to be inserted/updated.
 */
final class SQLPair
{
    String columnName;

    String value;

    SQLPair(String columnName, String value)
    {
        this.columnName = columnName;
        this.value = value;
    }

}