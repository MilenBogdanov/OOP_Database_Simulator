package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Command to load a table from a text file.
 */
public class LoadTableFromTextFileCommand implements Command {
    private final Database database;
    private String fileName;

    /**
     * Constructs a LoadTableFromTextFileCommand with the specified database.
     *
     * @param database The database to load the table into.
     */
    public LoadTableFromTextFileCommand(Database database) {
        this.database = database;
    }

    /**
     * Sets the file name of the text file containing the table data.
     *
     * @param fileName The name of the text file.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Executes the command to load a table from the text file.
     *
     * @param parameter The parameter associated with the command.
     */
    @Override
    public void execute(String parameter) {
        this.fileName = parameter;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String tableName = null;
            boolean inTable = false;
            Table table = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("TableName:")) {
                    tableName = line.replace("TableName:", "").trim();
                    table = new Table(tableName);
                    inTable = true;
                    continue;
                }

                if (inTable && line.startsWith("Columns:")) {
                    continue;
                }

                if (inTable && line.startsWith("Rows:")) {
                    List<Column> columns = table.getColumns();
                    while ((line = reader.readLine()) != null && !line.startsWith("Columns:")) {
                        String[] values = line.split("\\s+");
                        Row row = new Row();
                        for (int i = 0; i < values.length && i < columns.size(); i++) {
                            row.addValue(columns.get(i).getName(), values[i]);
                        }
                        table.addRow(row);
                    }
                    break;
                }

                if (inTable) {
                    String[] columnData = line.split(":")[1].trim().split("\\s+");
                    for (int i = 0; i < columnData.length; i += 2) {
                        String columnName = columnData[i];
                        DataType dataType = DataType.valueOf(columnData[i + 1]);
                        table.addColumn(new Column(columnName, dataType));
                    }
                }
            }

            if (table != null) {
                database.addTable(table);
                System.out.println("Table '" + tableName + "' loaded successfully from text file.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}