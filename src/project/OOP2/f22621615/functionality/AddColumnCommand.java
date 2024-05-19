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
     * Constructs an AddColumnCommand.
     *
     * @param database   The database where the table exists.
     * @param tableName  The name of the table to add the column to.
     * @param columnName The name of the new column.
     * @param columnType The data type of the new column.
     * @param fileName   The name of the file to update after adding the column.
     */
    public AddColumnCommand(Database database, String tableName, String columnName, DataType columnType, String fileName) {
        this.database = database;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.fileName = fileName;
    }
    /**
     * Executes the command to add a new column to the table.
     */
    @Override
    public void execute() {
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
     * Updates the text file with the new table structure.
     *
     * @param table The table whose structure is updated.
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
     * Sets the name of the column.
     *
     * @param columnName The name of the column.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    /**
     * Sets the data type of the column.
     *
     * @param columnType The data type of the column.
     */
    public void setColumnType(DataType columnType) {
        this.columnType = columnType;
    }
    /**
     * Sets the name of the file.
     *
     * @param fileName The name of the file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}