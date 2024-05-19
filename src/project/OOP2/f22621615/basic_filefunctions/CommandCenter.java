package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.CommandEnum;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.functionality.*;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;
import project.OOP2.f22621615.database.Database;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code CommandCenter} class manages the execution of various commands and operations in the system.
 * It initializes commands, executes them based on user input, and handles interactions with files and databases.
 */
public class CommandCenter {
    private StringBuilder fileContent;
    private Map<CommandEnum, Command> commands;
    private OpenFileCommand openFileCommand;
    private Database database;
    private String lastLoadedFile;
    /**
     * Parses the string value into its corresponding data type.
     *
     * @param value    The string value to parse.
     * @param dataType The data type to parse the value into.
     * @return The parsed object with the appropriate data type, or null if the data type is NULL.
     */
    private Object parseColumnValue(String value, DataType dataType) {
        switch (dataType) {
            case INTEGER:
                return Integer.parseInt(value);
            case FLOAT:
                return Float.parseFloat(value);
            case STRING:
                return value;
            case NULL:
            default:
                return null;
        }
    }

    /**
     * Constructs a new {@code CommandCenter} with the given file content and database.
     *
     * @param fileContent The content of the currently opened file.
     * @param database    The database instance used for storing and managing tables.
     */
    public CommandCenter(StringBuilder fileContent, Database database) {
        this.fileContent = fileContent;
        this.database = database;
        this.openFileCommand = new OpenFileCommand(this.fileContent);
        initializeCommands();
    }
    /**
     * Initializes the commands supported by the system.
     */
    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put(CommandEnum.OPEN, openFileCommand);
        commands.put(CommandEnum.LOAD, new LoadTableFromTextFileCommand(database, null));
        commands.put(CommandEnum.CLOSE, new CloseFileCommand(this.fileContent));
        commands.put(CommandEnum.SAVE, new SaveFileCommand(openFileCommand, this.fileContent));
        commands.put(CommandEnum.SAVEAS, new SaveFileAsCommand(this.fileContent, openFileCommand));
        commands.put(CommandEnum.HELP, new PrintHelpCommand());
        commands.put(CommandEnum.EXIT, new ExitCommand());
        commands.put(CommandEnum.SHOWTABLES, new ShowTablesCommand(database));
        commands.put(CommandEnum.DESCRIBE, new DescribeTableCommand(database, null));
        commands.put(CommandEnum.PRINT, new PrintTableRowsCommand(database));
        commands.put(CommandEnum.EXPORT, new ExportTableCommand(database, null, null));
        commands.put(CommandEnum.SELECT, new SelectCommand(database, null, null, null));
        commands.put(CommandEnum.ADDCOLUMN, new AddColumnCommand(database, null, null, null, null));
        commands.put(CommandEnum.UPDATE, new UpdateCommand(database, null, null, null, null, null));
        commands.put(CommandEnum.DELETE, new DeleteCommand(database, null, null, null));
        commands.put(CommandEnum.INSERT, new InsertCommand(database, null, null));
        commands.put(CommandEnum.RENAME, new RenameTableCommand(database, null, null));
        commands.put(CommandEnum.COUNT, new CountRowsCommand(database, null, null, null));
        commands.put(CommandEnum.AGGREGATE, new AggregateCommand(database));
    }
    /**
     * Executes the specified command with the given parameter.
     *
     * @param commandName The name of the command to execute.
     * @param parameter   The parameter associated with the command.
     */
    public void executeCommand(CommandEnum commandName, String parameter) {
        if (commandName == CommandEnum.LOAD && parameter.equals(lastLoadedFile)) {
            System.out.println("File '" + parameter + "' is already loaded.");
            return;
        }

        Command command = commands.get(commandName);
        if (command != null) {
            if (command instanceof FileCommand) {
                FileCommand fileCommand = (FileCommand) command;
                if (fileCommand instanceof OpenFileCommand) {
                    OpenFileCommand openCommand = (OpenFileCommand) fileCommand;
                    openCommand.setFileName(parameter);
                    openCommand.execute();
                } else if (fileCommand instanceof SaveFileAsCommand) {
                    SaveFileAsCommand saveAsCommand = (SaveFileAsCommand) fileCommand;
                    saveAsCommand.setFileName(parameter);
                    saveAsCommand.execute();
                } else {
                    if (!openFileCommand.isFileOpened()) {
                        System.out.println("No file is currently open. Use 'open' to open a file.");
                        return;
                    }
                    fileCommand.setFileName(parameter);
                }
            }
            String[] params;
            switch (commandName) {
                case DESCRIBE:
                    DescribeTableCommand describeCommand = (DescribeTableCommand) command;
                    if (parameter != null && !parameter.isEmpty()) {
                        describeCommand.describe(parameter);
                    } else {
                        System.out.println("Invalid parameters. Usage: describe <tableName>");
                        return;
                    }
                    break;
                case PRINT:
                    PrintTableRowsCommand printCommand = (PrintTableRowsCommand) command;
                    if (parameter != null && !parameter.isEmpty()) {
                        printCommand.setTableName(parameter);
                    } else {
                        System.out.println("Invalid parameters. Usage: print <tableName>");
                        return;
                    }
                    break;
                case EXPORT:
                    ExportTableCommand exportCommand = (ExportTableCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 2) {
                        exportCommand.setTableName(params[0]);
                        exportCommand.setFileName(params[1]);
                    } else {
                        System.out.println("Invalid parameters. Usage: export <tableName> <fileName>");
                        return;
                    }
                    break;
                case ADDCOLUMN:
                    AddColumnCommand addColumnCommand = (AddColumnCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 3) {
                        addColumnCommand.setTableName(params[0]);
                        addColumnCommand.setColumnName(params[1]);
                        addColumnCommand.setColumnType(DataType.valueOf(params[2].toUpperCase()));
                        addColumnCommand.setFileName(lastLoadedFile);
                    } else {
                        System.out.println("Invalid parameters. Usage: addcolumn <tableName> <columnName> <columnType>");
                        return;
                    }
                    break;
                case LOAD:
                    LoadTableFromTextFileCommand loadCommand = (LoadTableFromTextFileCommand) command;
                    loadCommand.setFileName(parameter);
                    break;
                case SELECT:
                    SelectCommand selectCommand = (SelectCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 3) {
                        selectCommand.setColumnName(params[0]);
                        selectCommand.setValue(params[1]);
                        selectCommand.setTableName(params[2]);
                    } else {
                        System.out.println("Invalid parameters. Usage: select <column_name> <value> <table_name>");
                        return;
                    }
                    break;
                case UPDATE:
                    UpdateCommand updateCommand = (UpdateCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 5) {
                        updateCommand.setTableName(params[0]);
                        updateCommand.setSearchColumnName(params[1]);

                        Column searchColumn = database.getTableByName(params[0]).getColumn(params[1]);
                        Object searchColumnValue = parseColumnValue(params[2], searchColumn.getType());
                        if (searchColumnValue != null) {
                            updateCommand.setSearchColumnValue(searchColumnValue);
                        } else {
                            System.out.println("Invalid search column value: " + params[2]);
                            return;
                        }

                        updateCommand.setTargetColumnName(params[3]);

                        Column targetColumn = database.getTableByName(params[0]).getColumn(params[3]);
                        Object targetColumnValue = parseColumnValue(params[4], targetColumn.getType());
                        if (targetColumnValue != null) {
                            updateCommand.setTargetColumnValue(targetColumnValue);
                        } else {
                            System.out.println("Invalid target column value: " + params[4]);
                            return;
                        }

                        System.out.print("Enter the filename to save the updated table: ");
                        Scanner scanner = new Scanner(System.in);
                        String fileName = scanner.nextLine();
                        updateCommand.setFileName(fileName);

                    } else {
                        System.out.println("Invalid parameters. Usage: update <tableName> <searchColumnName> <searchColumnValue> <targetColumnName> <targetColumnValue>");
                        return;
                    }
                    break;
                case DELETE:
                    DeleteCommand deleteCommand = (DeleteCommand) command;

                    params = parameter.split("\\s+");
                    if (params.length == 3) {
                        deleteCommand.setTableName(params[0]);
                        deleteCommand.setSearchColumnName(params[1]);

                        deleteCommand.setSearchColumnValue(params[2]);
                    } else {
                        System.out.println("Invalid parameters. Usage: delete <tableName> <searchColumnName> <searchColumnValue>");
                    }
                    break;
                case INSERT:
                    InsertCommand insertCommand = (InsertCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length >= 2) {
                        String tableName = params[0];
                        Table table = database.getTableByName(tableName);
                        if (table != null) {
                            String[] values = Arrays.copyOfRange(params, 1, params.length);
                            insertCommand.setTableName(tableName);
                            insertCommand.setValues(values);
                        } else {
                            System.out.println("Table '" + tableName + "' not found.");
                        }
                    } else {
                        System.out.println("Invalid parameters. Usage: insert <tableName> <value1> <value2> ... <valueN>");
                    }
                    break;
                case RENAME:
                    RenameTableCommand renameCommand = (RenameTableCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 2) {
                        String oldTableName = params[0];
                        String newTableName = params[1];
                        if (database.tableExists(oldTableName)) {
                            renameCommand.setOldName(oldTableName);
                            renameCommand.setNewName(newTableName);
                            renameCommand.execute();
                        } else {
                            System.out.println("Table '" + oldTableName + "' not found.");
                        }
                    } else {
                        System.out.println("Invalid parameters. Usage: rename <oldTableName> <newTableName>");
                        return;
                    }
                    break;
                case COUNT:
                    CountRowsCommand countRowsCommand = (CountRowsCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 3) {
                        countRowsCommand.setTableName(params[0]);
                        countRowsCommand.setSearchColumnName(params[1]);
                        countRowsCommand.setSearchValue(params[2]);
                    } else {
                        System.out.println("Invalid parameters. Usage: count <tableName> <searchColumnName> <searchValue>");
                    }
                    break;
                case AGGREGATE:
                    AggregateCommand aggregateCommand = (AggregateCommand) command;
                    params = parameter.split("\\s+");
                    if (params.length == 5) {
                        aggregateCommand.setTableName(params[0]);
                        aggregateCommand.setSearchColumnName(params[1]);
                        aggregateCommand.setSearchValue(params[2]);
                        aggregateCommand.setTargetColumnName(params[3]);
                        aggregateCommand.setOperation(params[4]);
                    } else {
                        System.out.println("Invalid parameters. Usage: aggregate <tableName> <searchColumnName> <searchValue> <targetColumnName> <operation>");
                    }
                    break;
            }
            command.execute();

            if (commandName == CommandEnum.LOAD) {
                lastLoadedFile = parameter;
            }
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}