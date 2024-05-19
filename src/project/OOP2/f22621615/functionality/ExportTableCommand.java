package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Command to export a table to a text file.
 */
public class ExportTableCommand implements Command {
    private Database database;
    private String tableName;
    private String fileName;

    /**
     * Constructs an ExportTableCommand with the specified database, table name, and file name.
     *
     * @param database   The database containing the table to export.
     * @param tableName  The name of the table to export.
     * @param fileName   The name of the file to export the table to.
     */
    public ExportTableCommand(Database database, String tableName, String fileName) {
        this.database = database;
        this.tableName = tableName;
        this.fileName = fileName;
    }

    /**
     * Executes the command to export the table to a text file.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            exportTable(table);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Exports the specified table to the specified file.
     *
     * @param table The table to export.
     */
    private void exportTable(Table table) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("TableName: " + table.getName() + "\n");

            for (Column column : table.getColumns()) {
                writer.write(column.getName() + " ");
            }
            writer.write("\n");

            for (Row row : table.getRows()) {
                StringBuilder rowString = new StringBuilder();
                for (Column column : table.getColumns()) {
                    String value = String.valueOf(row.getValue(column.getName()));
                    if (value != null) {
                        rowString.append(value).append(" ");
                    }
                }
                writer.write(rowString.toString().trim() + "\n");
            }

            System.out.println("Table '" + tableName + "' exported successfully to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting table: " + e.getMessage());
        }
    }

    /**
     * Sets the name of the table to export.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    /**
     * Sets the name of the file to export the table to.
     *
     * @param fileName The name of the file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}