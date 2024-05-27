package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Command to export a table to a text file.
 */
public class ExportTableCommand implements Command {
    private final Database database;
    private String tableName;
    private String fileName;

    /**
     * Constructs an ExportTableCommand with the specified database.
     *
     * @param database The database containing the table to export.
     */
    public ExportTableCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to export the table to a text file.
     *
     * @param parameter The parameter associated with the command.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 2) {
            setTableName(params[0]);
            setFileName(params[1]);
            exportTable();
        } else {
            System.out.println("Invalid parameters. Usage: export <tableName> <fileName>");
        }
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
     * Sets the name of the file.
     *
     * @param fileName The name of the file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Exports the specified table to the specified file.
     */
    private void exportTable() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("TableName: " + table.getName() + "\n");

                List<String> columnNames = table.getColumnNames();
                for (String columnName : columnNames) {
                    writer.write(columnName + " ");
                }
                writer.write("\n");

                List<Row> rows = table.getRows();
                for (Row row : rows) {
                    for (String columnName : columnNames) {
                        Object value = row.getValue(columnName);
                        writer.write(value + " ");
                    }
                    writer.write("\n");
                }

                System.out.println("Table '" + tableName + "' exported successfully to file: " + fileName);
            } catch (IOException e) {
                System.out.println("Error exporting table: " + e.getMessage());
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }
}