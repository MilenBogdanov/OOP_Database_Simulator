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
     * Constructs a SelectCommand with the specified database.
     *
     * @param database The database containing the table.
     */
    public SelectCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to select rows from the table based on the specified column value.
     *
     * @param parameter The parameter associated with the command.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 3) {
            String columnName = params[0];
            String value = params[1];
            String tableName = params[2];
            Table table = database.getTableByName(tableName);
            if (table != null) {
                selectRows(table, columnName, value);
            } else {
                System.out.println("Table '" + tableName + "' not found.");
            }
        } else {
            System.out.println("Invalid parameters. Usage: select <column_name> <value> <table_name>");
        }
    }

    /**
     * Selects rows from the table based on the specified column value.
     *
     * @param table      The table to select rows from.
     * @param columnName The name of the column.
     * @param value      The value to search for in the column.
     */
    private void selectRows(Table table, String columnName, String value) {
        List<Row> rows = table.getRows();
        System.out.println("Rows from table " + table.getName() + " where column " + columnName + " has value '" + value + "':");
        for (Row row : rows) {
            if (rowContainsValue(row, columnName, value)) {
                System.out.println(row);
            }
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
}