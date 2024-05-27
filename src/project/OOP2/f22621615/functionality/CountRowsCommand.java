package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

/**
 * Command to count the number of rows in a table where a specific column contains a specified value.
 */
public class CountRowsCommand implements Command {
    private final Database database;
    private String tableName;
    private String searchColumnName;
    private String searchValue;

    /**
     * Constructs a CountRowsCommand with the specified database.
     *
     * @param database The database containing the table to count rows from.
     */
    public CountRowsCommand(Database database) {
        this.database = database;
    }

    /**
     * Sets the name of the table.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Sets the name of the column to search in.
     *
     * @param searchColumnName The name of the column to search in.
     */
    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    /**
     * Sets the value to search for in the specified column.
     *
     * @param searchValue The value to search for.
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * Executes the command to count the number of rows.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 3) {
            setTableName(params[0]);
            setSearchColumnName(params[1]);
            setSearchValue(params[2]);
            performCount();
        } else {
            System.out.println("Invalid parameters. Usage: count <tableName> <searchColumnName> <searchValue>");
        }
    }

    /**
     * Performs the count of rows based on the specified search criteria.
     */
    private void performCount() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            int rowCount = countRows(table);
            System.out.println("Number of rows in table '" + tableName + "' where column '" + searchColumnName + "' contains value '" + searchValue + "': " + rowCount);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Counts the number of rows in the specified table where the column contains the search value.
     *
     * @param table The table to count rows in.
     * @return The number of rows matching the search criteria.
     */
    private int countRows(Table table) {
        int count = 0;
        for (Row row : table.getRows()) {
            if (rowContainsValue(row, searchColumnName, searchValue)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the specified row contains the search value in the specified column.
     *
     * @param row The row to check.
     * @param columnName The name of the column to search in.
     * @param value The value to search for.
     * @return True if the row contains the search value in the specified column, false otherwise.
     */
    private boolean rowContainsValue(Row row, String columnName, String value) {
        Object columnValue = row.getValue(columnName);
        return columnValue != null && columnValue.toString().contains(value);
    }
}