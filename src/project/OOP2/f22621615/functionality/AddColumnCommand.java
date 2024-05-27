package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Command to add a new column to a table in the database.
 */
public class AddColumnCommand implements Command {
    private Database database;
    private String tableName;
    private String columnName;
    private DataType columnType;
    private String fileName;

    /**
     * Constructs an AddColumnCommand with the specified database.
     *
     * @param database The database containing the table to add a column to.
     */
    public AddColumnCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to add a new column to a table.
     *
     * @param parameter The command parameters in the format "tableName columnName columnType".
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 3) {
            this.tableName = params[0];
            this.columnName = params[1];
            this.columnType = DataType.valueOf(params[2].toUpperCase());
            this.fileName = tableName + ".txt";
            addColumn();
        } else {
            System.out.println("Invalid parameters. Usage: addcolumn <tableName> <columnName> <columnType>");
        }
    }

    /**
     * Adds a new column to the specified table.
     */
    private void addColumn() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            boolean columnExists = table.getColumns().stream().anyMatch(c -> c.getName().equals(columnName));
            if (columnExists) {
                System.out.println("Column '" + columnName + "' already exists in table '" + tableName + "'.");
            } else {
                Column newColumn = new Column(columnName, columnType);
                table.addColumn(newColumn);

                for (Row row : table.getRows()) {
                    row.addValue(columnName, null);
                }

                updateTextFile(table);

                System.out.println("Column '" + columnName + "' added successfully to table '" + tableName + "'.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Updates the associated text file to reflect the new table structure.
     *
     * @param table The table to update in the text file.
     */
    private void updateTextFile(Table table) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("TableName: " + table.getName() + "\n");
            writer.write("Columns:\n");
            for (Column column : table.getColumns()) {
                writer.write("column: " + column.getName() + " " + column.getType() + "\n");
            }
            writer.write("Rows:\n");
            for (Row row : table.getRows()) {
                StringBuilder rowString = new StringBuilder();
                for (Column column : table.getColumns()) {
                    String value = String.valueOf(row.getValue(column.getName()));
                    rowString.append(value).append(" ");
                }
                writer.write(rowString.toString().trim() + "\n");
            }
            System.out.println("Table structure updated in the text file.");
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
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
     * Sets the name of the text file to update.
     *
     * @param fileName The name of the text file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}