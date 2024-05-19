package project.OOP2.f22621615.basic_filefunctions;
import project.OOP2.f22621615.interfaces.Command;

/**
 * The {@code PrintHelpCommand} class represents a command to print help information.
 * It implements the {@link Command} interface and defines behavior to execute the command.
 */
public class PrintHelpCommand implements Command {
    /**
     * Executes the print help command.
     * Prints information about the supported commands and their descriptions.
     */
    @Override
    public void execute() {
        System.out.println("|-------------------------------------------------------------------------------------------|");
        System.out.println("| THE FOLLOWING COMMANDS ARE SUPPORTED:                                                     |");
        System.out.println("|---------------|---------------------------------------------------------------------------|");
        System.out.println("| Command:      | Description:                                                              |");
        System.out.println("|---------------|---------------------------------------------------------------------------|");
        System.out.println("| open <file>   | Opens <file>.                                                             |");
        System.out.println("| load <file>   | Loads table from <file>.                                                  |");//1
        System.out.println("| showtables    | Shows names of loaded tables.                                             |");//2
        System.out.println("| describe      | Describes the types of the columns in the table.                          |");//3
        System.out.println("| print         | Displays all rows from a given table.                                     |");//4
        System.out.println("| export        | <table> <file> Exports a table to a file.                                 |");//5
        System.out.println("| select        | <column name> <value> <table name> Searches for a selected value.         |");//6
        System.out.println("| addcolumn     | <table name> <column name> <column type> Adds column with null value.     |");//7
        System.out.println("| update        | Updates a column of the table.                                            |");//8
        System.out.println("| delete        | Deletes rows of the table that have a specified value of the column.      |");//9
        System.out.println("| insert        | Inserts a new row in the table with the corresponding values.             |");//10
        System.out.println("| rename        | Renames a table.                                                          |");//11
        System.out.println("| count         | Finds the number of rows in the table which columns contain value         |");//12
        System.out.println("| aggregate     | Performs a given operation: sum, product, maximum, minimum.               |");//13
        System.out.println("| close         | Closes currently opened file.                                             |");
        System.out.println("| save          | Saves the currently open file.                                            |");
        System.out.println("| saveas <file> | Saves the currently open file in <file>.                                  |");
        System.out.println("| help          | Prints this information.                                                  |");
        System.out.println("| exit          | Exits the program.                                                        |");
        System.out.println("|-------------------------------------------------------------------------------------------|");
    }
}