package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Command to update rows in a table based on certain criteria.
 */
public class UpdateCommand implements Command {
    private Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchColumnValue;
    private String targetColumnName;
    private Object targetColumnValue;
    private String fileName;

    /**
     * Constructs an UpdateCommand with the specified parameters.
     *
     * @param database           The database containing the table to be updated.
     * @param tableName          The name of the table to be updated.
     * @param searchColumnName   The name of the column to search for.
     * @param searchColumnValue  The value to search for in the search column.
     * @param targetColumnName   The name of the column to update.
     * @param targetColumnValue  The value to set in the target column for matching rows.
     */
    public UpdateCommand(Database database, String tableName, String searchColumnName, Object searchColumnValue, String targetColumnName, Object targetColumnValue) {
        this.database = database;
        this.tableName = tableName;
        this.searchColumnName = searchColumnName;
        this.searchColumnValue = searchColumnValue;
        this.targetColumnName = targetColumnName;
        this.targetColumnValue = targetColumnValue;
    }

    /**
     * Sets the file name for saving the updated table.
     *
     * @param fileName The file name to save the updated table.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Executes the command to update rows in the table.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            boolean updated = false;
            List<Row> updatedRows = new ArrayList<>();
            for (Row row : table.getRows()) {
                Object value = row.getValue(searchColumnName);
                if (value != null && value.equals(searchColumnValue)) {
                    row.setValue(targetColumnName, targetColumnValue);
                    updatedRows.add(row);
                    updated = true;
                }
            }
            if (updated) {
                for (Row updatedRow : updatedRows) {
                    table.updateRow(updatedRow);
                }
                saveTableToFile(table, fileName);
                System.out.println("Rows updated successfully and saved to file: " + fileName);
            } else {
                System.out.println("No rows found matching the search criteria.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Saves the updated table to a file.
     *
     * @param table    The table to be saved.
     * @param fileName The name of the file to save the table.
     */
    private void saveTableToFile(Table table, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("TableName: " + table.getName());
            writer.newLine();
            writer.write("Columns:");
            writer.newLine();
            for (Column column : table.getColumns()) {
                writer.write("column: " + column);
                writer.newLine();
            }
            writer.write("Rows:");
            writer.newLine();
            for (Row row : table.getRows()) {
                writer.write(row.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving table to file: " + e.getMessage());
        }
    }

    /**
     * Sets the name of the table to be updated.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    /**
     * Sets the name of the column to search for.
     *
     * @param searchColumnName The name of the search column.
     */
    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }
    /**
     * Sets the value to search for in the search column.
     *
     * @param searchColumnValue The value to search for.
     */
    public void setSearchColumnValue(Object searchColumnValue) {
        this.searchColumnValue = searchColumnValue;
    }
    /**
     * Sets the name of the column to be updated.
     *
     * @param targetColumnName The name of the target column.
     */
    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }
    /**
     * Sets the value to be set in the target column for matching rows.
     *
     * @param targetColumnValue The value to set in the target column.
     */
    public void setTargetColumnValue(Object targetColumnValue) {
        this.targetColumnValue = targetColumnValue;
    }
}