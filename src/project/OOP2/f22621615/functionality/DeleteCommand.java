package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Command to delete rows from a table based on a specified column value.
 */
public class DeleteCommand implements Command {
    private final Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchColumnValue;

    /**
     * Constructs a DeleteCommand with the specified database.
     *
     * @param database The database containing the table from which rows will be deleted.
     */
    public DeleteCommand(Database database) {
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
     * @param searchColumnValue The value to search for.
     */
    public void setSearchColumnValue(Object searchColumnValue) {
        this.searchColumnValue = searchColumnValue;
    }

    /**
     * Executes the command to delete rows from the table.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 3) {
            setTableName(params[0]);
            setSearchColumnName(params[1]);
            setSearchColumnValue(params[2]);
            performDelete();
        } else {
            System.out.println("Invalid parameters. Usage: delete <tableName> <searchColumnName> <searchColumnValue>");
        }
    }

    private void performDelete() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            Iterator<Row> iterator = table.getRows().iterator();
            boolean deleted = false;
            boolean noRowsFound = true;
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Object columnValue = row.getValue(searchColumnName);
                if (columnValue != null && columnValue.equals(searchColumnValue)) {
                    iterator.remove();
                    deleted = true;
                    noRowsFound = false;
                }
            }
            if (deleted) {
                System.out.println("Rows deleted successfully from table '" + tableName + "'.");
                updateFile(table);
            } else if (noRowsFound) {
                System.out.println("No rows found matching the search criteria.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void updateFile(Table table) {
        String fileName = table.getAssociatedTextFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("TableName: " + table.getName());
            writer.newLine();
            writer.write("Columns:");
            writer.newLine();
            for (Column column : table.getColumns()) {
                writer.write("column: " + column.getName() + " " + column.getType());
                writer.newLine();
            }
            writer.write("Rows:");
            writer.newLine();
            for (Row row : table.getRows()) {
                writer.write(row.toString());
                writer.newLine();
            }
            System.out.println("File updated successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}