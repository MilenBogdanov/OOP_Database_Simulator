package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
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
     * Constructs an UpdateCommand with the specified database.
     *
     * @param database The database containing the table to be updated.
     */
    public UpdateCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to update rows in the table.
     *
     * @param parameter The parameter associated with the command.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 5) {
            tableName = params[0];
            searchColumnName = params[1];
            searchColumnValue = parseColumnValue(params[2], database.getTableByName(tableName).getColumn(searchColumnName).getType());
            targetColumnName = params[3];
            targetColumnValue = parseColumnValue(params[4], database.getTableByName(tableName).getColumn(targetColumnName).getType());

            Table table = database.getTableByName(tableName);
            if (table != null) {
                updateRows(table);
            } else {
                System.out.println("Table '" + tableName + "' not found.");
            }
        } else {
            System.out.println("Invalid parameters. Usage: update <tableName> <searchColumnName> <searchColumnValue> <targetColumnName> <targetColumnValue>");
        }
    }

    /**
     * Parses the string value into its corresponding data type.
     *
     * @param value    The string value to parse.
     * @param dataType The data type to parse the value into.
     * @return The parsed object with the appropriate data type, or null if the data type is NULL.
     */
    private Object parseColumnValue(String value, DataType dataType) {
        return switch (dataType) {
            case INTEGER -> Integer.parseInt(value);
            case FLOAT -> Float.parseFloat(value);
            case STRING -> value;
            case NULL -> null;
            default -> throw new IllegalArgumentException("Unsupported data type: " + dataType);
        };
    }

    /**
     * Updates rows in the table based on the specified criteria.
     *
     * @param table The table to be updated.
     */
    private void updateRows(Table table) {
        List<Row> updatedRows = new ArrayList<>();
        for (Row row : table.getRows()) {
            Object value = row.getValue(searchColumnName);
            if (value != null && value.equals(searchColumnValue)) {
                row.setValue(targetColumnName, targetColumnValue);
                updatedRows.add(row);
            }
        }
        saveTableToFile(table, fileName);
        System.out.println("Rows updated successfully and saved to file: " + fileName);
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
     * Sets the file name for saving the updated table.
     *
     * @param fileName The file name to save the updated table.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}