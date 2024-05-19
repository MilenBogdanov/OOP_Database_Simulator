package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;
/**
 * Command to insert a row into a table.
 */
public class InsertCommand implements Command {
    private Database database;
    private String tableName;
    private String[] values;

    /**
     * Constructs an InsertCommand with the specified database, table name, and values.
     *
     * @param database  The database containing the table to insert into.
     * @param tableName The name of the table to insert into.
     * @param values    The values to insert into the table.
     */
    public InsertCommand(Database database, String tableName, String[] values) {
        this.database = database;
        this.tableName = tableName;
        this.values = values;
    }

    /**
     * Executes the command to insert a row into the table.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            if (values.length == table.getColumns().size()) {
                Row newRow = new Row();
                for (int i = 0; i < values.length; i++) {
                    newRow.addValue(table.getColumns().get(i).getName(), values[i]);
                }
                table.addRow(newRow);
                updateTextFile(table);
                System.out.println("Row inserted successfully into table '" + tableName + "'.");
            } else {
                System.out.println("Number of values does not match the number of columns in the table.");
            }
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Updates the associated text file with the newly inserted row.
     *
     * @param table The table to update.
     */
    private void updateTextFile(Table table) {
        String fileName = table.getAssociatedTextFile();
        try {
            FileWriter writer = new FileWriter(fileName, true);

            StringBuilder newRow = new StringBuilder();
            for (String value : values) {
                newRow.append(value).append(" ");
            }
            newRow.append(System.lineSeparator());

            writer.write(newRow.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
        }
    }

    /**
     * Sets the name of the table to insert into.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Sets the values to insert into the table.
     *
     * @param values The values to insert.
     */
    public void setValues(String[] values) {
        this.values = values;
    }
}