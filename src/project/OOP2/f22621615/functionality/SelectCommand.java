package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.util.List;
/**
 * Command to select rows from a table based on a specific column value.
 */
public class SelectCommand implements Command {
    private final Database database;
    private String columnName;
    private String value;
    private String tableName;

    /**
     * Constructs a SelectCommand with the specified database, column name, value, and table name.
     *
     * @param database   The database containing the table.
     * @param columnName The name of the column to search for the value.
     * @param value      The value to search for in the column.
     * @param tableName  The name of the table to select rows from.
     */
    public SelectCommand(Database database, String columnName, String value, String tableName) {
        this.database = database;
        this.columnName = columnName;
        this.value = value;
        this.tableName = tableName;
    }

    /**
     * Executes the command to select rows from the table based on the specified column value.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            List<Row> rows = table.getRows();
            System.out.println("Rows from table " + tableName + " where column " + columnName + " has value '" + value + "':");
            for (Row row : rows) {
                if (rowContainsValue(row, columnName, value)) {
                    System.out.println(row);
                }
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Checks if a row contains the specified value in the specified column.
     *
     * @param row        The row to check.
     * @param columnName The name of the column.
     * @param value      The value to check for.
     * @return True if the row contains the value in the column, false otherwise.
     */
    private boolean rowContainsValue(Row row, String columnName, String value) {
        Object columnValue = row.getValue(columnName);
        return columnValue != null && columnValue.equals(value);
    }

    /**
     * Sets the name of the column.
     *
     * @param columnName The name of the column.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Sets the value to search for in the column.
     *
     * @param value The value to search for.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the name of the table.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
