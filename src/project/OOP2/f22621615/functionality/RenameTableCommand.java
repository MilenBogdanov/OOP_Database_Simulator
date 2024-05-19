package project.OOP2.f22621615.functionality;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.*;
/**
 * Command to rename a table.
 */
public class RenameTableCommand implements Command {
    private Database database;
    private String oldName;
    private String newName;

    /**
     * Constructs a RenameTableCommand with the specified database, old table name, and new table name.
     *
     * @param database The database containing the table.
     * @param oldName  The old name of the table.
     * @param newName  The new name of the table.
     */
    public RenameTableCommand(Database database, String oldName, String newName) {
        this.database = database;
        this.oldName = oldName;
        this.newName = newName;
    }

    /**
     * Executes the command to rename the table.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(oldName);
        if (table != null) {
            table.setName(newName);

            updateTableNameInFile(oldName, newName);

            System.out.println("Table renamed successfully from '" + oldName + "' to '" + newName + "'.");
        } else {
            System.out.println("Table '" + oldName + "' no more in the database.");
        }
    }

    /**
     * Updates the table name in the associated text file.
     *
     * @param oldName The old name of the table.
     * @param newName The new name of the table.
     */
    private void updateTableNameInFile(String oldName, String newName) {
        try {
            File file = new File(oldName + ".txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line.replaceAll(oldName, newName)).append("\n");
                }
                reader.close();

                FileWriter writer = new FileWriter(file);
                writer.write(fileContent.toString());
                writer.close();
            } else {
                System.out.println("Associated text file not found for table '" + oldName + "'.");
            }
        } catch (IOException e) {
            System.out.println("Error updating table name in the associated text file: " + e.getMessage());
        }
    }

    /**
     * Sets the old name of the table.
     *
     * @param oldName The old name of the table.
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * Sets the new name of the table.
     *
     * @param newName The new name of the table.
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }
}