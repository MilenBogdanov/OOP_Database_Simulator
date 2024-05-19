package project.OOP2.f22621615.database;

import project.OOP2.f22621615.enums.DataType;
/**
 * The {@code Column} class represents a database column with a name and a data type.
 * It provides methods to retrieve the name and type of the column.
 * @see project.OOP2.f22621615.enums.DataType
 */
public class Column {
    private String name;
    private DataType type;

    /**
     * Constructs a new {@code Column} with the specified name and data type.
     * @param name the name of the column
     * @param type the data type of the column
     */
    public Column(String name, DataType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the column.
     * @return the name of the column
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the data type of the column.
     *
     * @return the data type of the column
     */
    public DataType getType() {
        return type;
    }

    /**
     * Returns a string representation of the column.
     * The string representation consists of the column's name and data type.
     *
     * @return a string representation of the column
     */
    @Override
    public String toString() {
        return name + " " + type;
    }
}