package project.OOP2.f22621615.database;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Database} class represents a database containing tables.
 * It provides methods to add tables, retrieve tables, check if a table exists,
 * retrieve a table by name, and check if the database is empty.
 */
public class Database {
    private List<Table> tables;

    /**
     * Constructs a new {@code Database} with an empty list of tables.
     */
    public Database() {
        this.tables = new ArrayList<>();
    }

    /**
     * Adds a table to the database if it does not already exist.
     *
     * @param table the table to add
     */
    public void addTable(Table table) {
        if (!tables.contains(table)) {
            tables.add(table);
        }
    }

    /**
     * Returns the list of tables in the database.
     *
     * @return the list of tables
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * Checks if a table exists in the database.
     *
     * @param tableName the name of the table to check
     * @return {@code true} if the table exists, {@code false} otherwise
     */
    public boolean tableExists(String tableName) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the database.
     *
     * @return a string representation of the database
     */
    @Override
    public String toString() {
        return "Database{" +
                "tables=" + tables +
                '}';
    }

    /**
     * Returns the table with the specified name.
     *
     * @param tableName the name of the table to retrieve
     * @return the table with the specified name, or {@code null} if it does not exist
     */
    public Table getTableByName(String tableName) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    /**
     * Checks if the database is empty.
     *
     * @return {@code true} if the database is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return tables.isEmpty();
    }
}
